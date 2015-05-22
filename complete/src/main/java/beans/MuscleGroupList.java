package beans;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="muscleGroupList")
public class MuscleGroupList {

	private List<MuscleGroup> muscleGroupList;

	@XmlElementWrapper(name="muscleGroups")
	@XmlElement(name="muscleGroup")
	public List<MuscleGroup> getMuscleGroupList() {
		return muscleGroupList;
	}

	public void setMuscleGroupList(List<MuscleGroup> muscleGroupList) {
		this.muscleGroupList = muscleGroupList;
	}
}
