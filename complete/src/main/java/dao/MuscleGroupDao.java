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

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

import beans.Exercises;
import beans.MuscleGroup;
import beans.MuscleGroupList;

@Repository
public class MuscleGroupDao {

	//@Autowired
	//private DaoUtil daoUtil;
	
	private static final String getAllMuscleGroups = "select * from MUSCLE_GROUPS";
	private static final String getExercisesByMuscleName = "SELECT E.ID, E.NAME FROM MUSCLE_GROUPS MG, EXERCISES E WHERE MG.id=E.muscle_group_id AND MG.name LIKE ?";
	
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
	
	public MuscleGroup getExercisesByMuscleName(String muscleName) throws MySQLSyntaxErrorException{
		MuscleGroup group = new MuscleGroup();
		List<Exercises> exerciseList = new ArrayList<Exercises>();
		Exercises exercise = new Exercises();
		try {
			PreparedStatement ps = getConnection().prepareStatement(getExercisesByMuscleName);
			ps.setString(1, muscleName);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				exercise = resultSetToExercise(rs);
				exerciseList.add(exercise);
			}
			group.setExerciseList(exerciseList);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		return group;
	}

	private MuscleGroup resultSetToMuscleGroup(ResultSet rs) throws SQLException {
		MuscleGroup muscleGroup = new MuscleGroup();
		muscleGroup.setName(rs.getString("name"));
		muscleGroup.setId(rs.getInt("id"));
		return muscleGroup;
	}
	
	private Exercises resultSetToExercise(ResultSet rs) throws SQLException {
		Exercises exercise = new Exercises();
		exercise.setId(rs.getString("ID"));
		exercise.setName(rs.getString("NAME"));
		return exercise;
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
