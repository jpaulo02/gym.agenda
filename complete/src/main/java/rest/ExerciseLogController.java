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
			logs = exerciseLogService.getLogByDateAndWorkoutId(log.getWorkoutId());
			exercises.setLogs(logs);
		}catch(Throwable t){
			throw new Throwable("Error calling getExercisesById with id ", t);
		}
		return exercises;
	}
	
	@RequestMapping(value = "/updateLog", method = RequestMethod.POST , produces={"application/xml", "application/json"})
	public @ResponseBody Exercises updateLog(@RequestBody ExerciseLog log) throws Throwable{
		Exercises exercises = new Exercises();
		List<ExerciseLog> logs = new ArrayList<ExerciseLog>();
		try{
			exerciseLogService.updateLog(log);
			logs = exerciseLogService.getLogByDateAndWorkoutId(log.getWorkoutId());
			exercises.setLogs(logs);
		}catch(Throwable t){
			throw new Throwable("Error calling getExercisesById with id ", t);
		}
		return exercises;
	}
	
	@RequestMapping(value = "/getLogByDateAndWorkoutId/{workoutId}", method = RequestMethod.GET , produces={"application/xml", "application/json"})
	public @ResponseBody Exercises getLogByDateAndWorkoutId(@PathVariable(value="workoutId") String workoutId) throws Throwable{
		Exercises exercises = new Exercises();
		List<ExerciseLog> logs = new ArrayList<ExerciseLog>();
		try{
			logs = exerciseLogService.getLogByDateAndWorkoutId(workoutId);
			exercises.setLogs(logs);
		}catch(Throwable t){
			throw new Throwable("Error calling getExercisesById with id ", t);
		}
		return exercises;
	}

}
