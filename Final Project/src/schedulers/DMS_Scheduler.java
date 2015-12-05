package schedulers;

import java.util.List;

import basics.Task;

public class DMS_Scheduler extends Scheduler
{
	@Override
	public double getRelevantValue(Task task, long time)
	{
		return task.getDeadline();
	}
	
	@Override
	public String printTaskStatus(Task task, long time)
	{
		return "Task " + task.getName() + " has a relative deadline of " + (long) getRelevantValue(task, time) + ".";
	}
	
	@Override
	public List<Task> prune(List<Task> list, long time)
	{
		return lowestSort(list, time);
	}
}
