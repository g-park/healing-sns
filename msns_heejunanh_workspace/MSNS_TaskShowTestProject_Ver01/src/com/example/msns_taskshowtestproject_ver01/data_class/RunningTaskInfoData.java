package com.example.msns_taskshowtestproject_ver01.data_class;

public class RunningTaskInfoData {

	
	public String	baseActivity;//	The component launched as the first activity in the task.
	public String	description	;//Description of the task's current state.
	public String	id	;//A unique identifier for this task.
	public String	numActivities	;//Number of activities in this task.
	public String	numRunning	;//Number of activities that are currently running (not stopped and persisted) in this task.
	public String	thumbnail	;//Thumbnail representation of the task's current state.
	public String	topActivity	;//The activity component at the top of the history stack of the task.
}
