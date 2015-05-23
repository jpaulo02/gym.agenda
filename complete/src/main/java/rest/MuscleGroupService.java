package rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beans.MuscleGroupList;
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
}
