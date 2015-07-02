package rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.MuscleGroupDao;
import beans.ExerciseLog;

@Service
public class ExerciseLogService {
	
	@Autowired
	private MuscleGroupDao dao;

	public void logExercise(ExerciseLog log) {
		try{
			log = dao.logExercise(log);
		} catch (Throwable t){
			t.printStackTrace();
		}
	}

	public List<ExerciseLog> getLogByDateAndWorkoutId(String workoutId, String date) {
		List<ExerciseLog> logs = new ArrayList<ExerciseLog>();
		try{
			logs = dao.getLogByDateAndWorkoutId(workoutId, date);
		} catch (Throwable t){
			t.printStackTrace();
		}
		return logs;
	}

	public void updateLog(ExerciseLog log) {
		try{
			log = dao.updateExercise(log);
		} catch (Throwable t){
			t.printStackTrace();
		}
	}

	public List<String> getLogDates(String exercisesId) {
		List<String> dates = new ArrayList<String>();
		try{
			dates = dao.getLogDatesByExercisesId(exercisesId);
		} catch (Throwable t){
			t.printStackTrace();
		}
		return dates;
	}

}
