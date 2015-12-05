package schedulers;

import java.util.List;

import basics.Task;

public class RMS_Scheduler extends Scheduler
{
	static Scheduler DMS = new DMS_Scheduler();
	
	@Override
	public int getRelevantValue(Task task, int time)
	{
		return task.getPeriod();
	}
	
	@Override
	public String printTaskStatus(Task task, int time)
	{
		return "Task " + task.getName() + " has a period of " + getRelevantValue(task, time) + ".";
	}
	
	@Override
	public List<Task> prune(List<Task> list, int time)
	{
		return DMS.prune(lowestSort(list, time), time);
	}
}
