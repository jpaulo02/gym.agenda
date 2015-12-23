package rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import beans.MuscleGroup;
import beans.MuscleGroupList;
import beans.RoutineList;
import beans.WorkoutStats;

@RestController
@RequestMapping("/gym-agenda/home")
public class UserHomeController {
	
	@Autowired
	private UserHomeService homeService;

	@RequestMapping(value = "/getNumDaysWorkedOut/{numWeeksRange}", method = RequestMethod.GET)
	public int getNumDaysWorkedOut(@PathVariable(value="numWeeksRange") int numWeeksRange) throws Throwable{
		int numDays = 0;
		try{
			numDays = homeService.getNumDaysWorkedOut(numWeeksRange);
		}catch(Throwable t){
			throw new Throwable("Error calling getNumDaysWorkedOut", t);
		}
		return numDays;
	}
	
	@RequestMapping(value = "/getRoutineOrder/{userId}", method = RequestMethod.GET)
	public RoutineList getRoutineOrder(@PathVariable(value="userId") int userId) throws Throwable{
		RoutineList list = null;
		try{
			list = homeService.getRoutineOrder(userId);
		}catch(Throwable t){
			throw new Throwable("Error calling getNumDaysWorkedOut", t);
		}
		return list;
	}
	
}
