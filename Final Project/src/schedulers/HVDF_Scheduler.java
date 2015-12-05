package schedulers;

import java.util.List;

import basics.Task;

public class HVDF_Scheduler extends Scheduler
{
	@Override
	public double getRelevantValue(Task task, long time)
	{
		return task.getValueDensity();
	}
	
	@Override
	public String printTaskStatus(Task task, long time)
	{
		return "Task " + task.getName() + " has a value density of " + getRelevantValue(task, time) + ".";
	}
	
	@Override
	public List<Task> prune(List<Task> list, long time)
	{
		return highestSort(list, time);
	}
}
