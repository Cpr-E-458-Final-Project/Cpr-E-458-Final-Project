package schedulers;

import basics.Task;
import basics.TaskList;

public class HVDF_Scheduler extends Scheduler
{	
	@Override
	public int getRelevantValue(Task task, int time)
	{
		return task.getValue() / task.getComputationTime();
	}
	
	@Override
	public TaskList prune(TaskList tasklist, int time)
	{
		return highestSort(tasklist, time);
	}

	@Override
	public String printTaskStatus(Task task, int time)
	{
		return "Task " + task.getName() + " has a value density of " + getRelevantValue(task, time) + ".";
	}
}
