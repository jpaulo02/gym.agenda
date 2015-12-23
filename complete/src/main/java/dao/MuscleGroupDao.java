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
	private static final String insertIntoExerciseLogs = "INSERT INTO EXERCISE_LOGS (EXERCISES_ID, USER_ID, REPS, WEIGHT, NOTES, DATE) VALUES (?, 1, ?, ?, ?, NOW());";
	private static final String updateExerciseLogs = "UPDATE EXERCISE_LOGS SET REPS = ?, WEIGHT = ?, NOTES = ? WHERE id = ?";
	private static final String getLogByDateAndWorkoutId = "SELECT * FROM EXERCISE_LOGS WHERE DATE(DATE) = ? AND EXERCISES_ID = ?";
	private static final String getExerciseLogsByIdDate = "SELECT * FROM EXERCISE_LOGS WHERE exercises_id = ? AND DATE(DATE) = CURDATE()";
	private static final String getPreviousExerciseLogs = "SELECT DISTINCT(DATE(DATE)) AS DATE FROM EXERCISE_LOGS WHERE exercises_id = ? AND DATE(DATE) != CURDATE() ORDER BY DATE DESC";	
	private static final String getWorkoutStartTime = "SELECT DATE FROM EXERCISE_LOGS WHERE DATE(DATE) = CURDATE() ORDER BY DATE ASC LIMIT 1";
	private static final String getDailyStats = "SELECT * FROM (SELECT DATE(DATE) AS DATE1, MIN(DATE) AS START_TIME, MAX(DATE) AS END_TIME, SUM(REPS) AS TOTAL_REPS, TIMEDIFF(MAX(DATE), MIN(DATE)) AS DURATION, SUM(WEIGHT) AS TOTAL_WEIGHT FROM EXERCISE_LOGS WHERE DATE(DATE) = CURDATE()) A, (SELECT DATE, SUM(SETS) AS NUM_SETS FROM (SELECT DATE(DATE) AS DATE, COUNT(EXERCISES_ID) AS SETS FROM EXERCISE_LOGS WHERE DATE(DATE) = CURDATE() GROUP BY DATE(DATE), EXERCISES_ID) AS TEST) B";
	private static final String getTotalWeightByExerciseId = "SELECT DATE(DATE) AS DATE, sum(TOTAL_WEIGHT) AS TOTAL_WEIGHT, sum(REPS) AS TOTAL_REPS FROM (SELECT e.`NAME`, el.`REPS`, el.`WEIGHT`, el.`DATE`, (el.`REPS`*el.`WEIGHT`) AS TOTAL_WEIGHT FROM `EXERCISES` e, `EXERCISE_LOGS` el WHERE e.`id` = ? AND el.`exercises_id` = e.`id` GROUP BY el.date) AS test GROUP BY DATE(DATE) ORDER BY DATE ASC";
	
	
	public MuscleGroupList getAllMuscleGroups() {
		MuscleGroupList list = new MuscleGroupList();
		List<MuscleGroup> muscleGroupList = new ArrayList<MuscleGroup>();
		MuscleGroup muscleGroup = new MuscleGroup();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(getAllMuscleGroups);

			while (rs.next()) {
				muscleGroup = resultSetToMuscleGroup(rs);
				muscleGroupList.add(muscleGroup);
			}
			list.setMuscleGroupList(muscleGroupList);
		} catch (Throwable t) {
			t.printStackTrace();
		} finally{
			this.cleanUpConnections(conn, ps, stmt, rs);
		}

		return list;
	}
	
	public MuscleGroup getExercisesByMuscleName(String muscleName) {
		MuscleGroup group = new MuscleGroup();
		List<Exercises> exerciseList = new ArrayList<Exercises>();
		Exercises exercise = new Exercises();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			ps = conn.prepareStatement(getExercisesByMuscleName);
			ps.setString(1, muscleName);
			rs = ps.executeQuery();
			
			while(rs.next()){
				exercise = resultSetToExercise(rs);
				exercise.setLogs(this.getExerciseLogsByIdDate(exercise.getId()));
				exerciseList.add(exercise);
			}
			group.setExerciseList(exerciseList);
		} catch (Throwable t) {
			t.printStackTrace();
		} finally{
			this.cleanUpConnections(conn, ps, null, rs);
		}
		
		return group;
	}
	
	public WorkoutStats getDailyStatistics() {
		WorkoutStats stats = new WorkoutStats();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			conn = getConnection();
			ps = conn.prepareStatement(getDailyStats);
			rs = ps.executeQuery();
			
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
		} finally{
			this.cleanUpConnections(conn, ps, null, rs);
		}
		
		return stats;
	}
	
	public String getFirstLogTime() {
		Timestamp startTime = null;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			conn = getConnection();
			ps = conn.prepareStatement(getWorkoutStartTime);
			rs = ps.executeQuery();
			while(rs.next()){
				startTime = rs.getTimestamp("DATE");
			}
		} catch (Throwable t) {
			t.printStackTrace();
		} finally{
			this.cleanUpConnections(conn, ps, null, rs);
		}
		return startTime.toString();
	}
	
	private List<ExerciseLog> getExerciseLogsByIdDate(String exerciseId) {
		List<ExerciseLog> logs = new ArrayList<ExerciseLog>();
		ExerciseLog log = new ExerciseLog();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			conn = getConnection();
			ps = conn.prepareStatement(getExerciseLogsByIdDate);
			ps.setString(1, exerciseId);
			rs = ps.executeQuery();
			
			while(rs.next()){
				log = resultSetToExerciseLog(rs);
				logs.add(log);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		} finally{
			this.cleanUpConnections(conn, ps, null, rs);
		}
		return logs;
	}

	public ExerciseLog logExercise(ExerciseLog log) {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = getConnection();
			ps = conn.prepareStatement(insertIntoExerciseLogs);
			ps.setString(1, log.getWorkoutId());
			ps.setString(2, log.getReps());
			ps.setString(3, log.getWeight());
			ps.setString(4, log.getNotes());
			ps.execute();
		} catch (Throwable t) {
			t.printStackTrace();
		} finally{
			this.cleanUpConnections(conn, ps, null, null);
		}
		return log;
	}
	
	public ExerciseLog updateExercise(ExerciseLog log) {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = getConnection();
			ps = conn.prepareStatement(updateExerciseLogs);
			ps.setString(1, log.getReps());
			ps.setString(2, log.getWeight());
			ps.setString(3, log.getNotes());
			ps.setString(4, log.getId());
			ps.executeUpdate();
		} catch (Throwable t) {
			t.printStackTrace();
		} finally{
			this.cleanUpConnections(conn, ps, null, null);
		}
		return log;
	}
	
	public List<ExerciseLog> getLogByDateAndWorkoutId(String workoutId, String date) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<ExerciseLog> logs = new ArrayList<ExerciseLog>();
		ExerciseLog log = new ExerciseLog();
		
		try {
			conn = getConnection();
			ps = conn.prepareStatement(getLogByDateAndWorkoutId);
			if(date == null || date.equalsIgnoreCase("null")){
				Date timeNow = new Date(Calendar.getInstance().getTimeInMillis());
				ps.setDate(1, timeNow);
			}else{
				ps.setString(1, date);
			}
			ps.setString(2, workoutId);
			rs = ps.executeQuery();
			
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
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<String> dates = new ArrayList<String>();
		String date = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(getPreviousExerciseLogs);
			ps.setString(1, exercisesId);
			rs = ps.executeQuery();
			
			while(rs.next()){
				date = resultSetToDateList(rs);
				dates.add(date);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		} finally{
			this.cleanUpConnections(conn, ps, null, rs);
		}
		
		return dates;
	}
	
	public List<ExerciseLog> getTotalWeightByExerciseId(String exerciseId){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<ExerciseLog> logs = new ArrayList<ExerciseLog>();
		ExerciseLog log = new ExerciseLog();
		
		try{
			conn = getConnection();
			ps = conn.prepareStatement(getTotalWeightByExerciseId);
			ps.setString(1, exerciseId);
			rs = ps.executeQuery();
			
			while(rs.next()){
				log = resultSetToExerciseLogs(rs);
				logs.add(log);
			}
			
		} catch (Throwable t) {
			t.printStackTrace();
		} finally{
			this.cleanUpConnections(conn, ps, null, rs);
		}
		
		return logs;
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
			log.setDate(rs.getDate("DATE"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return log;
	}
	
	private ExerciseLog resultSetToExerciseLogs(ResultSet rs) {
		ExerciseLog log = new ExerciseLog();
		try {
			log.setWeight(rs.getString("TOTAL_WEIGHT"));
			log.setReps(rs.getString("TOTAL_REPS"));
			log.setDate(rs.getDate("DATE"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return log;
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
