package rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import beans.ExerciseLog;
import beans.MuscleGroup;
import beans.MuscleGroupList;

@RestController
@RequestMapping("/gym-agenda/exerciseLog")
public class ExerciseLogController {
	
//	@Autowired
//	private MuscleGroupService muscleService;
//
	@RequestMapping(value = "/logExercise", method = RequestMethod.POST , produces={"application/xml", "application/json"})
	public @ResponseBody ExerciseLog logExercise(@RequestBody ExerciseLog log) throws Throwable{
		try{
			System.out.println(log);
			//log = muscleService.getALlMuscleGroups();
		}catch(Throwable t){
			throw new Throwable("Error calling getExercisesById with id ", t);
		}
		return log;
	}
//	
//	@RequestMapping(value = "/getExercisesByMuscleName/{muscleName}", method = RequestMethod.GET)
//	public MuscleGroup getExercisesByMuscleId(@PathVariable(value="muscleName") String muscleName) throws Throwable{
//		MuscleGroup group = new MuscleGroup();
//		try{
//			group = muscleService.getExercisesByMuscleName(muscleName);
//		}catch(Throwable t){
//			throw new Throwable("Error calling getExercisesByMuscleId with id ", t);
//		}
//		return group;
//	}

}
