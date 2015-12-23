package rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import beans.MuscleGroup;
import beans.MuscleGroupList;
import beans.WorkoutStats;

@RestController
@RequestMapping("/gym-agenda/muscleGroup")
public class MuscleGroupController {
	
	@Autowired
	private MuscleGroupService muscleService;

	@RequestMapping(value = "/allMuscles", method = RequestMethod.GET)
	public MuscleGroupList getAllMuslceGroups() throws Throwable{
		MuscleGroupList list = new MuscleGroupList();
		try{
			list = muscleService.getALlMuscleGroups();
		}catch(Throwable t){
			throw new Throwable("Error calling allMuscles", t);
		}
		return list;
	}
	
	@RequestMapping(value = "/getDailyStatistics", method = RequestMethod.GET)
	public WorkoutStats getDailyStatistics() throws Throwable{
		WorkoutStats dailyStats = new WorkoutStats();
		try{
			dailyStats = muscleService.getDailyStatistics();
		}catch(Throwable t){
			throw new Throwable("Error calling getFirstLogTime", t);
		}
		return dailyStats;
	}
	
	@RequestMapping(value = "/getExercisesByMuscleName/{muscleName}", method = RequestMethod.GET)
	public MuscleGroup getExercisesByMuscleId(@PathVariable(value="muscleName") String muscleName) throws Throwable{
		MuscleGroup group = new MuscleGroup();
		try{
			group = muscleService.getExercisesByMuscleName(muscleName);
		}catch(Throwable t){
			throw new Throwable("Error calling getExercisesByMuscleName with name " + muscleName, t);
		}
		return group;
	}

}
