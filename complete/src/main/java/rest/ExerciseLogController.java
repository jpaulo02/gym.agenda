package rest;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import rest.ExerciseLogService;
import beans.ExerciseLog;
import beans.Exercises;

@RestController
@RequestMapping("/gym-agenda/exerciseLog")
public class ExerciseLogController {
	
	@Autowired
	private ExerciseLogService exerciseLogService;

	@RequestMapping(value = "/logExercise", method = RequestMethod.POST , produces={"application/xml", "application/json"})
	public @ResponseBody Exercises logExercise(@RequestBody ExerciseLog log) throws Throwable{
		Exercises exercises = new Exercises();
		List<ExerciseLog> logs = new ArrayList<ExerciseLog>();
		try{
			exerciseLogService.logExercise(log);
			logs = exerciseLogService.getLogByDateAndWorkoutId(log.getWorkoutId(), null);
			exercises.setLogs(logs);
		}catch(Throwable t){
			throw new Throwable("Error calling logExercise", t);
		}
		return exercises;
	}
	
	@RequestMapping(value = "/updateLog", method = RequestMethod.POST , produces={"application/xml", "application/json"})
	public @ResponseBody Exercises updateLog(@RequestBody ExerciseLog log) throws Throwable{
		Exercises exercises = new Exercises();
		List<ExerciseLog> logs = new ArrayList<ExerciseLog>();
		try{
			exerciseLogService.updateLog(log);
			logs = exerciseLogService.getLogByDateAndWorkoutId(log.getWorkoutId(), null);
			exercises.setLogs(logs);
		}catch(Throwable t){
			throw new Throwable("Error calling updateLog", t);
		}
		return exercises;
	}
	
	@RequestMapping(value = "/getLogByDateAndWorkoutId/workoutId/{workoutId}/date/{date}", method = RequestMethod.GET , produces={"application/xml", "application/json"})
	public @ResponseBody Exercises getLogByDateAndWorkoutId(@PathVariable(value="workoutId") String workoutId, @PathVariable(value="date") String date) throws Throwable{
		Exercises exercises = new Exercises();
		List<ExerciseLog> logs = new ArrayList<ExerciseLog>();
		try{
			logs = exerciseLogService.getLogByDateAndWorkoutId(workoutId, date);
			exercises.setLogs(logs);
		}catch(Throwable t){
			throw new Throwable("Error calling getLogByDateAndWorkoutId with id " + workoutId, t);
		}
		return exercises;
	}
	
	@RequestMapping(value = "/getLogDates/{workoutId}", method = RequestMethod.GET , produces={"application/xml", "application/json"})
	public @ResponseBody List<String> getLogDates(@PathVariable(value="workoutId") String workoutId) throws Throwable{
		List<String> dates = new ArrayList<String>();
		try{
			dates = exerciseLogService.getLogDates(workoutId);
		}catch(Throwable t){
			throw new Throwable("Error calling getExercisesById with id " + workoutId, t);
		}
		return dates;
	}
	
	@RequestMapping(value = "/getGraphData/{exerciseId}", method = RequestMethod.GET , produces={"application/xml", "application/json"})
	public @ResponseBody Exercises getTotalWeightByExerciseId(@PathVariable(value="exerciseId") String exerciseId) throws Throwable{
		Exercises exercises = new Exercises();
		try{
			exercises = exerciseLogService.getTotalWeightByExerciseId(exerciseId);
		}catch(Throwable t){
			throw new Throwable("Error calling getTotalWeightByExerciseId with id " + exerciseId, t);
		}
		return exercises;
	}
}
