package schedulability_checkers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import basics.Task;
import basics.Util;

public class DMS_Checker implements SchedulabilityChecker
{
	protected Comparator<Task> compare_priority = new Comparator<Task>()
	{
		public int compare(Task a, Task b)
		{
			return (a.getDeadline() == b.getDeadline()) ? 0 : ((a.getDeadline() < b.getDeadline()) ? -1 : 1);
		}
	};
	
	protected boolean exactAnalysis(List<Task> list)
	{
		List<Task> working_list = new ArrayList<Task>();
		
		working_list.addAll(list);
		
		Collections.sort(working_list, compare_priority);
		
		long deadline = -1;
		
		for(int index = 0, length = working_list.size(); index < length; index++)
		{
			if(deadline == (deadline = working_list.get(index).getDeadline())) continue;
			long response = 0;
			
			for(Task task : working_list)
				if(task.getDeadline() <= deadline)
					response += task.getComputationTime();
			
			boolean keep_going = true;
			
			while(keep_going)
			{
				if(deadline < response)
				{
					return false;
				}
				long new_response = getResponse(working_list, deadline, response);
				if(response == new_response) keep_going = false;
				
				response = new_response;
				
			}
		}
		return true;
	}
	
	protected long getDemand(Task task, long response)
	{
		return(task.getComputationTime() * (long) Math.ceil(((double) response) / ((double) task.getPeriod())));
	}
	
	protected long getResponse(List<Task> list, long deadline, long response)
	{
		long ret = 0;
		
		for(Task task : list)
			if(task.getDeadline() <= deadline)
				ret += getDemand(task, response);
		
		return ret;
	}
	
	protected boolean isPossible(List<Task> list)
	{
		double sum = 0.0;
		
		for(Task task : list)
			sum += (((double) task.getComputationTime()) / ((double) task.getPeriod()));
		
		return sum <= 1.0;
	}
	
	public boolean isSchedulable(List<Task> list)
	{
		if(Util.determineScheduleLength(list) < 0)
		{
			System.out.println("The necessary schedule length is too long to generate a schedule for this list of Tasks; returning false.");
			return false;
		}
		
		List<Task> newlist = new ArrayList<Task>();
		
		newlist.addAll(list);
		
		return isPossible(newlist) && (utilizationTest(newlist) || exactAnalysis(newlist));
	}
	
	protected boolean utilizationTest(List<Task> list)
	{
		double sum = 0.0;
		
		for(Task task : list)
			sum += (double) task.getComputationTime() / (double) task.getDeadline();
			
		return sum <= (list.size() * (Math.pow(2.0, (1.0 / (double) list.size())) - 1.0));
	}
}