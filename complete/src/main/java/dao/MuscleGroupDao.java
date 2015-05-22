package dao;

import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import beans.MuscleGroup;
import beans.MuscleGroupList;

public class MuscleGroupDao {

	@Autowired
	private DaoUtil daoUtil;
	
	private static final String getAllMuscleGroups = "select * from MUSCLE_GROUPS";
	
	public MuscleGroupList getAllMuscleGroups() {
		MuscleGroupList list = new MuscleGroupList();
		List<MuscleGroup> muscleGroupList = new ArrayList<MuscleGroup>();
		MuscleGroup muscleGroup = new MuscleGroup();
		try {
			Statement stmt = DaoUtil.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(getAllMuscleGroups);

			while (rs.next()) {
				muscleGroup = resultSetToMuscleGroup(rs);
				muscleGroupList.add(muscleGroup);
			}
			list.setMuscleGroupList(muscleGroupList);
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return list;
	}

	private MuscleGroup resultSetToMuscleGroup(ResultSet rs) throws SQLException {
		MuscleGroup muscleGroup = new MuscleGroup();
		muscleGroup.setName(rs.getString("name"));
		muscleGroup.setId(rs.getInt("id"));
		return muscleGroup;
	}

}
