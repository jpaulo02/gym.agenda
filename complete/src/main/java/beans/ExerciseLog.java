package beans;

import java.util.Date;

public class ExerciseLog {
	
	private Date date;
	private String reps;
	private String weight;
	private String notes;
	
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getReps() {
		return reps;
	}
	
	public void setReps(String reps) {
		this.reps = reps;
	}
	
	public String getWeight() {
		return weight;
	}
	
	public void setWeight(String weight) {
		this.weight = weight;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
}
