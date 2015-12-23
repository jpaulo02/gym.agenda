package rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beans.MuscleGroup;
import beans.MuscleGroupList;
import beans.RoutineList;
import beans.WorkoutStats;
import dao.MuscleGroupDao;
import dao.StatsDao;
import dao.UserDao;

@Service
public class UserHomeService {

	@Autowired
	private StatsDao statsDao;
	
	@Autowired
	private UserDao userDao;

	public int getNumDaysWorkedOut(int numWeeksRange) {
		int numDays = statsDao.getNumDaysWorkedOut(numWeeksRange);
		return numDays;
	}

	public RoutineList getRoutineOrder(int userId) {
		RoutineList list = userDao.getRoutineOrder(userId);
		return list;
	}
}
