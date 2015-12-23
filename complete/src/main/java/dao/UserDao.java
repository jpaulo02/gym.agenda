package dao;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import beans.Routine;
import beans.RoutineList;
import beans.User;

@Repository
public class UserDao {

	private static final String getUserByEmailAndPass = "SELECT * FROM USER WHERE email = ? and CAST(password AS BINARY) = ?";
	private static final String getRoutineOrder = "SELECT * FROM `ROUTINES` r WHERE r.`user_details_id` = ? ORDER BY r.`exercise_date` ASC";
	
	public User login(User user) {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		try {
			conn = getConnection();
			ps = conn.prepareStatement(getUserByEmailAndPass);
			ps.setString(1, user.getEmail());
			ps.setString(2, user.getPassword());
			rs = ps.executeQuery();

			if (rs.next()) {
				user.setValidated(true);
			}else{
				user.setValidated(false);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		} finally{
			this.cleanUpConnections(conn, ps, stmt, rs);
		}

		return user;
	}
	
	public RoutineList getRoutineOrder(int userId){
		RoutineList list = new RoutineList();
		List<Routine> routines = new ArrayList<Routine>();
		Routine routine = new Routine();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		try {
			conn = getConnection();
			ps = conn.prepareStatement(getRoutineOrder);
			ps.setInt(1, userId);
			rs = ps.executeQuery();

			while (rs.next()) {
				routine = this.resultSetToRoutine(rs);
				routines.add(routine);
			}
			list.setRoutineList(routines);
			
		} catch (Throwable t) {
			t.printStackTrace();
		} finally{
			this.cleanUpConnections(conn, ps, stmt, rs);
		}
		
		return list;
	}
	
	private Routine resultSetToRoutine(ResultSet rs) {
		Routine routine = new Routine();
		try {
			routine.setRoutineId(rs.getInt("routine_id"));
			routine.setUserId(rs.getInt("user_details_id"));
			routine.setName(rs.getString("name"));
			routine.setDateExercises(rs.getDate("exercise_date"));
			routine.setImgLocation(rs.getString("img_src"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return routine;
	}

	private void cleanUpConnections(Connection conn, PreparedStatement ps, Statement stmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (ps != null) {
			try {
				ps.close();
				ps = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if (stmt != null) {
			try {
				stmt.close();
				stmt = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		try {
			if (conn != null && !conn.isClosed()) {
				if (!conn.getAutoCommit()) {
					conn.commit();
					conn.setAutoCommit(true);
				}
				conn.close();
				conn = null;
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws URISyntaxException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class.forName("com.mysql.jdbc.Driver");
	    
	    String serverName = "localhost:3306";
	    String mydatabase = "gymAgenda";
	    String url = "jdbc:mysql://" + serverName + "/" + mydatabase; 

	    String username = "root";
	    Connection connection = DriverManager.getConnection(url, username, null);

	    return connection;
	}
}
