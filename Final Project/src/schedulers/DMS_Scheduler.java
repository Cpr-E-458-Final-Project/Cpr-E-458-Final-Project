package schedulers;

import java.util.Collections;
import java.util.Comparator;
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
		List<Task> ret = lowestSort(list, time);
		Collections.sort(ret, new Comparator<Task>()
		{

			@Override
			public int compare(Task a, Task b)
			{
				return (a.getPeriod() == b.getPeriod()) ? 0 : (a.getPeriod() < b.getPeriod()) ? -1 : 1;
			}
		});
		return ret;
	}
}
