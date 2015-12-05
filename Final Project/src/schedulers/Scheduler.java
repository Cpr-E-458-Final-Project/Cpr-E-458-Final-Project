package schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import basics.Task;

public abstract class Scheduler
{
	protected boolean details = false;
	
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
	
	public int determineScheduleLength(List<Task> list)
	{
		int ret = list.get(0).getPeriod();
		
		for(int index = 1; index < list.size(); index++)
		{
			ret = lcm(ret, list.get(index).getPeriod());
		}
		
		return ret;
	}
	
	public abstract int getRelevantValue(Task task, int time);
	
	protected List<Task> highestSort(List<Task> list, int time)
	{
		List<Task> ret = new ArrayList<Task>();
		int max = getRelevantValue(list.get(0), time);
		for(Task task : list)
		{
			max = Math.max(max, getRelevantValue(task, time));
		}
		for(Task task : list)
		{
			if(max <= getRelevantValue(task, time))
			{
				ret.add(task);
			}
		}
		return ret;
	}
	
	protected List<Task> lowestSort(List<Task> list, int time)
	{
		List<Task> ret = new ArrayList<Task>();
		int min = getRelevantValue(list.get(0), time);
		for(Task task : list)
		{
			min = Math.min(min, getRelevantValue(task, time));
		}
		for(Task task : list)
		{
			if(getRelevantValue(task, time) <= min)
			{
				ret.add(task);
			}
		}
		return ret;
	}
	
	public abstract String printTaskStatus(Task task, int time);
	
	public abstract List<Task> prune(List<Task> list, int time);
	
	public boolean schedule(List<Task> list, boolean details)
	{
		this.details = details;
		list.forEach(new Consumer<Task>()
		{

			@Override
			public void accept(Task t)
			{
				t.reset();
			}
		});
		int length = determineScheduleLength(list);
		Task selected = null;
		for(int time = 0; time < length;)
		{
			List<Task> temp_list = new ArrayList<Task>();
			for(Task task : list)
			{
				if(task.isReady())
				{
					temp_list.add(task);
				}
			}
			
			if(details)
			{
				for(Task task : temp_list)
				{
					System.out.println(printTaskStatus(task, time));
				}
			}
			
			if(!temp_list.isEmpty())
			{
				temp_list = prune(temp_list, time);
			}
			
			if(temp_list.isEmpty())
			{
				selected = null;
			}
			else if(!temp_list.contains(selected))
			{
				selected = temp_list.get(0);
			}
			
			if(selected != null)
			{
				if(details)
				{
					System.out.println("Task " + selected.getName() + " runs from Time " + time + " to " + (time + 1) + ".");
				}
				selected.run();
			}
			else if(details)
			{
				System.out.println("No Task is able to run from Time " + time + " to " + (time + 1) + ".");
			}
			
			time++;
			
			for(Task task : list)
			{
				if(!task.update(time))
				{
					if(details) System.out.println("Task " + task.getName() + " has failed to meet its deadline at time " + time);
					this.details = false;
					return false;
				}
			}
		}
		this.details = false;
		return true;
	}
	
	public boolean schedule(List<Task> list)
	{
		return schedule(list, false);
	}
}
