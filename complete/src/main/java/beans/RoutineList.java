package beans;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="routineList")
public class RoutineList {

	private List<Routine> routineList;

	@XmlElementWrapper(name="routines")
	@XmlElement(name="routine")
	public List<Routine> getRoutineList() {
		return routineList;
	}

	public void setRoutineList(List<Routine> routineList) {
		this.routineList = routineList;
	}

	
}
