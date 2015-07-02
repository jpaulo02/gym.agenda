package beans;

public class WorkoutStats {
	
	private String id;
	private String startTime;
	private String endTime;
	private String workoutDuration;
	private String totalReps;
	private String totalWeight;
	private String avgRestTime;
	private String numSets;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getWorkoutDuration() {
		return workoutDuration;
	}
	public void setWorkoutDuration(String workoutDuration) {
		this.workoutDuration = workoutDuration;
	}
	public String getTotalReps() {
		return totalReps;
	}
	public void setTotalReps(String totalReps) {
		this.totalReps = totalReps;
	}
	public String getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}
	public String getAvgRestTime() {
		return avgRestTime;
	}
	public void setAvgRestTime(String totalSetsString, String workoutDuration) {
		String stringHours = workoutDuration.substring(1, 3);
		String stringMins = workoutDuration.substring(4, 6);
		String stringSecs = workoutDuration.substring(7, 9);
		int hours = Integer.valueOf(stringHours);
		hours =  hours*3600;
		int mins = Integer.valueOf(stringMins);
		mins = mins*60;
		int secs = Integer.valueOf(stringSecs);
		int totalSets = Integer.valueOf(totalSetsString);
		double totalTime = hours + mins + secs;
		double totalDur = totalTime/totalSets;
		double temp = totalDur/60;
		temp = Math.round(temp*100)/100.0d;
		long minutes = (long)temp;
		double seconds = temp - minutes;
		seconds = Math.round(seconds*100)/100.0d;
		stringSecs = String.valueOf(seconds);
		stringSecs = stringSecs.substring(2, 4);
		avgRestTime = String.valueOf(minutes)+":"+stringSecs;
	}
	public String getNumSets() {
		return numSets;
	}
	public void setNumSets(String numSets) {
		this.numSets = numSets;
	}
	
}
