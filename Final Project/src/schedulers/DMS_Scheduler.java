package schedulers;

import java.util.List;

import basics.Task;

public class DMS_Scheduler extends Scheduler
{
	@Override
	public int getRelevantValue(Task task, int time)
	{
		return task.getDeadline();
	}
	
	@Override
	public String printTaskStatus(Task task, int time)
	{
		return "Task " + task.getName() + " has a relative deadline of " + getRelevantValue(task, time) + ".";
	}
	
	@Override
	public List<Task> prune(List<Task> list, int time)
	{
		return lowestSort(list, time);
	}
}
