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

import beans.ExerciseLog;
import beans.Exercises;
import beans.MuscleGroup;
import beans.MuscleGroupList;

@Repository
public class MuscleGroupDao {

	//@Autowired
	//private DaoUtil daoUtil;
	
	private static final String getAllMuscleGroups = "select * from MUSCLE_GROUPS";
	private static final String getExercisesByMuscleName = "SELECT E.ID, E.NAME FROM MUSCLE_GROUPS MG, EXERCISES E WHERE MG.id=E.muscle_group_id AND MG.name LIKE ?";
	private static final String insertIntoExerciseLogs = "INSERT INTO EXERCISE_LOGS (EXERCISES_ID, REPS, WEIGHT, NOTES, DATE) VALUES (?, ?, ?, ?, NOW());";
	private static final String updateExerciseLogs = "UPDATE EXERCISE_LOGS SET REPS = ?, WEIGHT = ?, NOTES = ? WHERE id = ?";
	private static final String getLogByDateAndWorkoutId = "SELECT * FROM EXERCISE_LOGS WHERE DATE(DATE) = CURDATE() AND EXERCISES_ID = ?";
	private static final String getExerciseLogsByIdDate = "SELECT * FROM EXERCISE_LOGS WHERE exercises_id = ? AND DATE(DATE) = CURDATE()";
	
	
	
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
				exercise.setLogs(this.getExerciseLogsByIdDate(exercise.getId()));
				exerciseList.add(exercise);
			}
			group.setExerciseList(exerciseList);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		return group;
	}
	
	private List<ExerciseLog> getExerciseLogsByIdDate(String exerciseId) {
		// TODO Auto-generated method stub
		List<ExerciseLog> logs = new ArrayList<ExerciseLog>();
		ExerciseLog log = new ExerciseLog();
		try{
			PreparedStatement ps = getConnection().prepareStatement(getExerciseLogsByIdDate);
			ps.setString(1, exerciseId);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				log = resultSetToExerciseLog(rs);
				logs.add(log);
			}
			
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return logs;
	}

	public ExerciseLog logExercise(ExerciseLog log) {
		try {
			PreparedStatement ps = getConnection().prepareStatement(insertIntoExerciseLogs);
			ps.setString(1, log.getWorkoutId());
			ps.setString(2, log.getReps());
			ps.setString(3, log.getWeight());
			ps.setString(4, log.getNotes());
			ps.execute();
			
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return log;
	}
	
	public ExerciseLog updateExercise(ExerciseLog log) {
		try {		
			System.out.println("reps " + log.getReps());
			System.out.println("weight " + log.getWeight());
			System.out.println("notes " + log.getNotes());
			System.out.println("id " + log.getId());
			PreparedStatement ps = getConnection().prepareStatement(updateExerciseLogs);
			ps.setString(1, log.getReps());
			ps.setString(2, log.getWeight());
			ps.setString(3, log.getNotes());
			ps.setString(4, log.getId());
			ps.executeUpdate();
			
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return log;
	}
	
	public List<ExerciseLog> getLogByDateAndWorkoutId(String workoutId) {
		List<ExerciseLog> logs = new ArrayList<ExerciseLog>();
		ExerciseLog log = new ExerciseLog();
		
		try {
			PreparedStatement ps = getConnection().prepareStatement(getLogByDateAndWorkoutId);
			ps.setString(1, workoutId);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				log = resultSetToExerciseLog(rs);
				logs.add(log);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return logs;
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
	
	private ExerciseLog resultSetToExerciseLog(ResultSet rs) throws SQLException {
		ExerciseLog log = new ExerciseLog();
		log.setId(rs.getString("id"));
		log.setWorkoutId(rs.getString("EXERCISES_ID"));
		log.setReps(rs.getString("REPS"));
		log.setWeight(rs.getString("WEIGHT"));
		log.setNotes(rs.getString("NOTES"));
		return log;
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
