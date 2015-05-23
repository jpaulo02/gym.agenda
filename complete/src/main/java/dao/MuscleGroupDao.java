package dao;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import beans.MuscleGroup;
import beans.MuscleGroupList;

@Repository
public class MuscleGroupDao {

	//@Autowired
	//private DaoUtil daoUtil;
	
	private static final String getAllMuscleGroups = "select * from MUSCLE_GROUPS";
	
	public MuscleGroupList getAllMuscleGroups() {
		MuscleGroupList list = new MuscleGroupList();
		List<MuscleGroup> muscleGroupList = new ArrayList<MuscleGroup>();
		MuscleGroup muscleGroup = new MuscleGroup();
		try {
			Statement stmt = getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(getAllMuscleGroups);

			while (rs.next()) {
				muscleGroup = resultSetToMuscleGroup(rs);
				muscleGroupList.add(muscleGroup);
			}
			list.setMuscleGroupList(muscleGroupList);
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return list;
	}

	private MuscleGroup resultSetToMuscleGroup(ResultSet rs) throws SQLException {
		MuscleGroup muscleGroup = new MuscleGroup();
		muscleGroup.setName(rs.getString("name"));
		muscleGroup.setId(rs.getInt("id"));
		return muscleGroup;
	}
	
	public static Connection getConnection() throws URISyntaxException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class.forName("com.mysql.jdbc.Driver");
	    
	    String serverName = "localhost";
	    String mydatabase = "gymAgenda";
	    String url = "jdbc:mysql://" + serverName + "/" + mydatabase; 

	    String username = "root";
	    Connection connection = DriverManager.getConnection(url, username, null);

	    return connection;
	}

}
