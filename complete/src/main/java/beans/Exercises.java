package beans;

import java.util.ArrayList;
import java.util.List;

public class Exercises {

	private String id;
	private String name;
	private List<ExerciseLog> logs = new ArrayList<ExerciseLog>();
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public List<ExerciseLog> getLogs() {
		return logs;
	}

	public void setLogs(List<ExerciseLog> logs) {
		this.logs = logs;
	}
}
