package schedulers;

import basics.Task;
import basics.TaskList;

public abstract class Scheduler
{
	public abstract int getRelevantValue(Task task, int time);
	
	public abstract String printTaskStatus(Task task, int time);
	
	public abstract TaskList prune(TaskList tasklist, int time);
	
	private static int gcd(int a, int b)
	{
	    while(b > 0)
	    {
	        int c = b;
	        b = a % b;
	        a = c;
	    }
	    
	    return a;
	}
	
	private static int lcm(int a, int b)
	{
	    return a * (b / gcd(a, b));
	}
	
	public int determineScheduleLength(TaskList tasklist)
	{
		int ret = tasklist.get(0).getPeriod();
	    for(int index = 1; index < tasklist.size(); index++)
	    	ret = lcm(ret, tasklist.get(index).getPeriod());
	    return ret;
	}
	
	public boolean schedule(TaskList tasklist)
	{
		tasklist = tasklist.clone();
		int length = determineScheduleLength(tasklist);
		for(int time = 0; time < length;)
		{
			TaskList temp_list = new TaskList();
			for(Task task : tasklist)
				if(task.isReady())
					temp_list.add(task);
			
			for(Task task : temp_list)
				System.out.println(printTaskStatus(task, time));
			
			if(!temp_list.isEmpty())
				temp_list = prune(temp_list, time);
			
			Task selected = (!temp_list.isEmpty()) ? temp_list.get(0) : null;
			
			if(selected != null)
			{
				System.out.println("Task " + selected.getName() + " runs from Time " + time + " to " + (time + 1) + ".");
				selected.run();
			}
			else
				System.out.println("No Task is able to run from Time " + time + " to " + (time + 1) + ".");
			
			time++;
			
			for(Task task : tasklist)
				if(task.update(time))
					return false;
		}
		return true;
	}
	
	protected TaskList lowestSort(TaskList tasklist, int time)
	{
		TaskList ret = new TaskList();
		int min = getRelevantValue(tasklist.get(0), time);
		for(Task task : tasklist)
			min = Math.min(min, getRelevantValue(task, time));
		for(Task task : tasklist)
			if(getRelevantValue(task, time) <= min)
				ret.add(task);
		return ret;
	}
	
	protected TaskList highestSort(TaskList tasklist, int time)
	{
		TaskList ret = new TaskList();
		int max = getRelevantValue(tasklist.get(0), time);
		for(Task task : tasklist)
			max = Math.max(max, getRelevantValue(task, time));
		for(Task task : tasklist)
			if(max <= getRelevantValue(task, time))
				ret.add(task);
		return ret;
	}
}
