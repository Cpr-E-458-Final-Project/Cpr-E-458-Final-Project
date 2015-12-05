package schedulers;

import java.util.List;

import basics.Task;

public class HVDF_Scheduler extends Scheduler
{
    @Override
    public int getRelevantValue(Task task, int time)
    {
	return task.getValueDensity();
    }

    @Override
    public String printTaskStatus(Task task, int time)
    {
	return "Task " + task.getName() + " has a value density of " + getRelevantValue(task, time) + ".";
    }

    @Override
    public List<Task> prune(List<Task> list, int time)
    {
	return highestSort(list, time);
    }
}
