package rest;

import org.springframework.stereotype.Service;

import beans.MuscleGroupList;
import dao.MuscleGroupDao;

@Service
public class MuscleGroupService {

	//@Autowired
	private MuscleGroupDao dao;
	
	public MuscleGroupList getALlMuscleGroups() {
		MuscleGroupList list = new MuscleGroupList();
		try {
			list = dao.getAllMuscleGroups();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		System.out.println("Test " + list.getMuscleGroupList().get(0).getId());
		System.out.println("Test " + list.getMuscleGroupList().get(0).getName());
		return list;
	}
}
