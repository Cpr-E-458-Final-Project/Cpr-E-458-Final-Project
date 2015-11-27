package schedulers;

import basics.Task;
import basics.TaskList;

public class LLF_Scheduler extends Scheduler
{
	@Override
	public int getRelevantValue(Task task, int time)
	{
		return task.getNextDeadline() - (time + task.getRemainingCompTime());
	}
	
	@Override
	public TaskList prune(TaskList tasklist, int time)
	{
		return lowestSort(tasklist, time);
	}
	
	@Override
	public String printTaskStatus(Task task, int time)
	{
		return "Task " + task.getName() + " has a laxity of " + getRelevantValue(task, time) + ".";
	}
}
