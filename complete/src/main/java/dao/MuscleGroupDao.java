package dao;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

import beans.ExerciseLog;
import beans.Exercises;
import beans.MuscleGroup;
import beans.MuscleGroupList;
import beans.WorkoutStats;

@Repository
public class MuscleGroupDao {

	//@Autowired
	//private DaoUtil daoUtil;
	
	private static final String getAllMuscleGroups = "select * from MUSCLE_GROUPS";
	private static final String getExercisesByMuscleName = "SELECT E.ID, E.NAME FROM MUSCLE_GROUPS MG, EXERCISES E WHERE MG.id=E.muscle_group_id AND MG.name LIKE ?";
	private static final String insertIntoExerciseLogs = "INSERT INTO EXERCISE_LOGS (EXERCISES_ID, REPS, WEIGHT, NOTES, DATE) VALUES (?, ?, ?, ?, NOW());";
	private static final String updateExerciseLogs = "UPDATE EXERCISE_LOGS SET REPS = ?, WEIGHT = ?, NOTES = ? WHERE id = ?";
	private static final String getLogByDateAndWorkoutId = "SELECT * FROM EXERCISE_LOGS WHERE DATE(DATE) = ? AND EXERCISES_ID = ?";
	private static final String getExerciseLogsByIdDate = "SELECT * FROM EXERCISE_LOGS WHERE exercises_id = ? AND DATE(DATE) = CURDATE()";
	private static final String getPreviousExerciseLogs = "SELECT DISTINCT(DATE(DATE)) AS DATE FROM EXERCISE_LOGS WHERE exercises_id = ? AND DATE(DATE) != CURDATE() ORDER BY DATE DESC";	
	private static final String getWorkoutStartTime = "SELECT DATE FROM EXERCISE_LOGS WHERE DATE(DATE) = CURDATE() ORDER BY DATE ASC LIMIT 1";
	private static final String getDailyStats = "SELECT * FROM (SELECT DATE(DATE) AS DATE1, MIN(DATE) AS START_TIME, MAX(DATE) AS END_TIME, SUM(REPS) AS TOTAL_REPS, TIMEDIFF(MAX(DATE), MIN(DATE)) AS DURATION, SUM(WEIGHT) AS TOTAL_WEIGHT FROM EXERCISE_LOGS WHERE DATE(DATE) = CURDATE()) A, (SELECT DATE, SUM(SETS) AS NUM_SETS FROM (SELECT DATE(DATE) AS DATE, COUNT(EXERCISES_ID) AS SETS FROM EXERCISE_LOGS WHERE DATE(DATE) = CURDATE() GROUP BY DATE(DATE), EXERCISES_ID) AS TEST) B";
	
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
	
	public MuscleGroup getExercisesByMuscleName(String muscleName) {
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
	
	public WorkoutStats getDailyStatistics() {
		WorkoutStats stats = new WorkoutStats();
		try{
			PreparedStatement ps = getConnection().prepareStatement(getDailyStats);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				stats.setStartTime(rs.getTimestamp("START_TIME").toString());
				stats.setEndTime(rs.getTimestamp("END_TIME").toString());
				stats.setTotalReps(rs.getString("TOTAL_REPS"));
				stats.setWorkoutDuration(rs.getTimestamp("DURATION").toString().substring(10, 19));
				stats.setNumSets(rs.getString("NUM_SETS"));
				stats.setTotalWeight(rs.getString("TOTAL_WEIGHT"));
				stats.setAvgRestTime(stats.getNumSets(), stats.getWorkoutDuration());
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		return stats;
	}
	
	public String getFirstLogTime() {
		Timestamp startTime = null;
		try{
			PreparedStatement ps = getConnection().prepareStatement(getWorkoutStartTime);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				startTime = rs.getTimestamp("DATE");
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return startTime.toString();
	}
	
	private List<ExerciseLog> getExerciseLogsByIdDate(String exerciseId) {
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
	
	public List<ExerciseLog> getLogByDateAndWorkoutId(String workoutId, String date) {
		List<ExerciseLog> logs = new ArrayList<ExerciseLog>();
		ExerciseLog log = new ExerciseLog();
		try {
			PreparedStatement ps = getConnection().prepareStatement(getLogByDateAndWorkoutId);
			if(date == null || date.equalsIgnoreCase("null")){
				Date timeNow = new Date(Calendar.getInstance().getTimeInMillis());
				ps.setDate(1, timeNow);
			}else{
				ps.setString(1, date);
			}
			ps.setString(2, workoutId);
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
	
	public List<String> getLogDatesByExercisesId(String exercisesId) {
		List<String> dates = new ArrayList<String>();
		String date = null;
		try {
			PreparedStatement ps = getConnection().prepareStatement(getPreviousExerciseLogs);
			ps.setString(1, exercisesId);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				date = resultSetToDateList(rs);
				dates.add(date);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		return dates;
	}

	private String resultSetToDateList(ResultSet rs) {
		String date = null;
		try {
			date = (rs.getDate("DATE")).toString();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return date;
	}

	private MuscleGroup resultSetToMuscleGroup(ResultSet rs) {
		MuscleGroup muscleGroup = new MuscleGroup();
		try {
			muscleGroup.setName(rs.getString("name"));
			muscleGroup.setId(rs.getInt("id"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return muscleGroup;
	}
	
	private Exercises resultSetToExercise(ResultSet rs) {
		Exercises exercise = new Exercises();
		try {
			exercise.setId(rs.getString("ID"));
			exercise.setName(rs.getString("NAME"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exercise;
	}
	
	private ExerciseLog resultSetToExerciseLog(ResultSet rs) {
		ExerciseLog log = new ExerciseLog();
		try {
			log.setId(rs.getString("id"));
			log.setWorkoutId(rs.getString("EXERCISES_ID"));
			log.setReps(rs.getString("REPS"));
			log.setWeight(rs.getString("WEIGHT"));
			log.setNotes(rs.getString("NOTES"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
