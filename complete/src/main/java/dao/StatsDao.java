package dao;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

@Repository
public class StatsDao {

	private static final String numDaysWorkedOutByWeeks = "SELECT COUNT(DISTINCT DATE(DATE)) AS NUM_DAYS FROM EXERCISE_LOGS WHERE DATE BETWEEN date_sub(now(),INTERVAL ? WEEK) AND now() ORDER BY DATE DESC";
	
	
	public int getNumDaysWorkedOut(int numOfWeeks){
		int numOfDays = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			ps = conn.prepareStatement(numDaysWorkedOutByWeeks);
			ps.setInt(1, numOfWeeks);
			rs = ps.executeQuery();
			
			if(rs.next()){
				numOfDays = (rs.getInt("NUM_DAYS"));
				
			}
		} catch (Throwable t) {
			t.printStackTrace();
		} finally{
			this.cleanUpConnections(conn, ps, null, rs);
		}
		
		return numOfDays;
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
