package com.example.msns_taskshowtestproject_ver01.data_class;

public class RunningProcessInfoData {
	public String	importance;//	The relative importance level that the system places on this process.
	public String	importanceReasonCode;//	The reason for importance, if any.
	public String	importanceReasonComponent;//	For the specified values of importanceReasonCode, this is the name of the component that is being used in this process.
	public String	importanceReasonPid	;//For the specified values of importanceReasonCode, this is the process ID of the other process that is a client of this process.
	public String	lastTrimLevel	;//Last memory trim level reported to the process: corresponds to the values supplied to ComponentCallbacks2.onTrimMemory(String).
	public String	lru;//	An additional ordering within a particular importance category, providing finer-grained information about the relative utility of processes within a category.
	public String	pid	;//The pid of this process; 0 if none
	public String[]	pkgList	;//All packages that have been loaded Stringo the process.
	public String	processName;//he name of the process that this object is associated with
	public String	uid	;//The user id of this process.
}
