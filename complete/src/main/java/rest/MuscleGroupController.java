package rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import beans.MuscleGroupList;

@RestController
@RequestMapping("/muscleGroup")
public class MuscleGroupController {
	
	@Autowired
	private MuscleGroupService muscleService;

	@RequestMapping("/allMuscles")
	public MuscleGroupList getAllMuslceGroups() throws Throwable{
		MuscleGroupList list = new MuscleGroupList();
		try{
			list = muscleService.getALlMuscleGroups();
		}catch(Throwable t){
			throw new Throwable("Error calling getExercisesById with id ", t);
		}
		return list;
	}

}
