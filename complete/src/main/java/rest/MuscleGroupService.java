package rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beans.MuscleGroup;
import beans.MuscleGroupList;
import beans.WorkoutStats;
import dao.MuscleGroupDao;

@Service
public class MuscleGroupService {

	@Autowired
	private MuscleGroupDao dao;
	
	public MuscleGroupList getALlMuscleGroups() {
		MuscleGroupList list = new MuscleGroupList();
		try {
			list = dao.getAllMuscleGroups();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return list;
	}

	public MuscleGroup getExercisesByMuscleName(String muscleName) {
		MuscleGroup group = new MuscleGroup();
		try{ 
			group = dao.getExercisesByMuscleName(muscleName);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return group;
	}

	public WorkoutStats getDailyStatistics() {
		WorkoutStats stats = new WorkoutStats();
		try{ 
			stats = dao.getDailyStatistics();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return stats;
	}
}
