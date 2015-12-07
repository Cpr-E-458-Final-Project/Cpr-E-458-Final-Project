package schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import basics.Task;
import schedulability_checkers.DMS_Checker;
import schedulability_checkers.EDF_Checker;
import schedulability_checkers.RMS_Checker;

public abstract class Scheduler
{
	protected boolean details = false;
	
	private static long gcd(long ret, long b)
	{
		while(b > 0)
		{
			long c = b;
			b = ret % b;
			ret = c;
		}
		
		return ret;
	}
	
	private static long lcm(long ret, long b)
	{
		return ret * (b / gcd(ret, b));
	}
	
	public long determineScheduleLength(List<Task> list)
	{
		long ret = list.get(0).getPeriod();
		
		for(int index = 1; index < list.size(); index++)
		{
			ret = lcm(ret, list.get(index).getPeriod());
		}
		
		return ret;
	}
	
	public abstract double getRelevantValue(Task task, long time);
	
	protected List<Task> highestSort(List<Task> list, long time)
	{
		List<Task> ret = new ArrayList<Task>();
		double max = getRelevantValue(list.get(0), time);
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
	
	protected List<Task> lowestSort(List<Task> list, long time)
	{
		List<Task> ret = new ArrayList<Task>();
		double min = getRelevantValue(list.get(0), time);
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
	
	public abstract String printTaskStatus(Task task, long time);
	
	public abstract List<Task> prune(List<Task> list, long time);
	
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
		long length = determineScheduleLength(list);
		if(length < 1)
		{
			if(details)
				System.out.println("The necessary schedule length is too long to generate a schedule for this list of Tasks;");
			if(this instanceof RMS_Scheduler || this instanceof DMS_Scheduler)
				return new DMS_Checker().isSchedulable(list, details);
			else
				return new EDF_Checker().isSchedulable(list, details);
		}
		Task selected = null;
		for(long time = 0; time < length;)
		{
			List<Task> temp_list = new ArrayList<Task>();
			for(Task task : list)
			{
				if(task.isReady())
				{
					temp_list.add(task);
				}
			}
			
//			if(details)
//			{
//				for(Task task : temp_list)
//				{
//					System.out.println(printTaskStatus(task, time));
//				}
//			}
			
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
