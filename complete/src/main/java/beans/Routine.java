package beans;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="routine")
public class Routine {
	
	private int routineId;
	private int userId;
	private String name;
	private Date dateExercises;
	private String imgLocation;
	
	
	public int getRoutineId() {
		return routineId;
	}
	public void setRoutineId(int routineId) {
		this.routineId = routineId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDateExercises() {
		return dateExercises;
	}
	public void setDateExercises(Date dateExercises) {
		this.dateExercises = dateExercises;
	}
	public String getImgLocation() {
		return imgLocation;
	}
	public void setImgLocation(String imgLocation) {
		this.imgLocation = imgLocation;
	}
	
	
	
}
