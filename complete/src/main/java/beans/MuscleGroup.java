package beans;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="muscleGroup")
public class MuscleGroup {
	
	private int id;
	private String name;
	private List<Exercises> exerciseList;
	private WorkoutStats stats;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlElementWrapper(name="exercises")
	@XmlElement(name="exercise")
	public List<Exercises> getExerciseList() {
		return exerciseList;
	}
	public void setExerciseList(List<Exercises> exerciseList) {
		this.exerciseList = exerciseList;
	}

	public WorkoutStats getStats() {
		return stats;
	}

	public void setStats(WorkoutStats stats) {
		this.stats = stats;
	}
	
}
