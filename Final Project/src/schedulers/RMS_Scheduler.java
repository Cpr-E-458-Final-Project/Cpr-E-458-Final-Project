package schedulers;

import java.util.List;

import basics.Task;

public class RMS_Scheduler extends Scheduler
{
	static Scheduler DMS = new DMS_Scheduler();
	
	@Override
	public double getRelevantValue(Task task, long time)
	{
		return task.getPeriod();
	}
	
	@Override
	public String printTaskStatus(Task task, long time)
	{
		return "Task " + task.getName() + " has a period of " + (long) getRelevantValue(task, time) + ".";
	}
	
	@Override
	public List<Task> prune(List<Task> list, long time)
	{
		return DMS.prune(lowestSort(list, time), time);
	}
}
