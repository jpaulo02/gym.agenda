package beans;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="userInfo")
public class UserInfo {
	
	private String firstName;
	private String lastName;
	private Date birthDate;
	private int weight;
	private int feet;
	private int inches;
	private String city;
	private String gymName;
	private String experienceLevel;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getFeet() {
		return feet;
	}
	public void setFeet(int feet) {
		this.feet = feet;
	}
	public int getInches() {
		return inches;
	}
	public void setInches(int inches) {
		this.inches = inches;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getGymName() {
		return gymName;
	}
	public void setGymName(String gymName) {
		this.gymName = gymName;
	}
	public String getExperienceLevel() {
		return experienceLevel;
	}
	public void setExperienceLevel(String experienceLevel) {
		this.experienceLevel = experienceLevel;
	}

	
}
