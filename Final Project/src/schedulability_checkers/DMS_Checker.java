package schedulability_checkers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import basics.Task;
import basics.expressions.AddExp;
import basics.expressions.CeilExp;
import basics.expressions.DivExp;
import basics.expressions.Expression;
import basics.expressions.MultExp;
import basics.expressions.NumExp;
import schedulers.DMS_Scheduler;

public class DMS_Checker implements SchedulabilityChecker
{
	protected boolean details = false;
	
	protected Comparator<Task> compare_priority = new Comparator<Task>()
	{
		public int compare(Task a, Task b)
		{
			return (a.getDeadline() == b.getDeadline()) ? 0 : ((a.getDeadline() < b.getDeadline()) ? -1 : 1);
		}
	};
	
	protected boolean exactAnalysis(List<Task> list)
	{
		
		List<Task> newlist = new ArrayList<Task>();
		
		newlist.addAll(list);
		
		Collections.sort(newlist, compare_priority);
		
		if(details)
		{
			System.out.println("Now beginning exact analysis...");
		}
		
		long deadline = -1;
		for(int index = 0, length = newlist.size(); index < length; index++)
		{
			if(deadline == (deadline = newlist.get(index).getDeadline())) continue;
			
			if(details)
			{
				System.out.println("Now considering the response times of all tasks with deadlines no greater than " + deadline + "...");
			}
			
			List<Expression> exps = new ArrayList<Expression>();
			
			for(Task task : newlist)
			{
				if(task.getDeadline() <= deadline)
				{
					exps.add(new NumExp(task.getComputationTime()));
				}
			}
			
			Expression response_exp = new AddExp(exps);
			
			long response = (long) response_exp.getProcessedValue();
			
			if(details)
			{
				System.out.println("Now computing the response time of all Tasks with deadlines no greater than " + deadline + "...");
				System.out.println(Expression.processAll(response_exp));
				System.out.println("The response time of all Tasks with deadlines no greater than " + deadline + " is " + response + ".");
			}
			
			boolean keep_going = true;
			
			while(keep_going)
			{
				if(deadline < response)
				{
					if(details)
					{
						System.out.println("All Tasks with deadlines no greater than " + deadline + " have a response time of " + response + "; the exact analysis test has failed.");
					}
					return false;
				}
				long new_response = getResponse(newlist, deadline, response);
				if(response == new_response) keep_going = false;
				
				response = new_response;
				
			}
			if(details)
			{
				System.out.println("All Tasks with deadlines no greater than " + deadline + " have a steady response time of " + response + "; the exact analysis test has passed for Tasks with deadlines of " + deadline + " or less.");
			}
		}
		if(details)
		{
			System.out.println("All Tasks pass the exact requirement test.");
		}
		return true;
	}
	
	protected Expression getDemand(Task task, long response)
	{
		return new MultExp(new NumExp(task.getComputationTime()), new CeilExp(new DivExp(new NumExp(response), new NumExp(task.getPeriod()))));
	}
	
	protected long getResponse(List<Task> list, long deadline, long response)
	{
		List<Expression> exps = new ArrayList<Expression>();
		for(Task task : list)
		{
			if(task.getDeadline() <= deadline)
			{
				exps.add(getDemand(task, response));
			}
		}
		Expression ret_exp = new AddExp(exps);
		if(details)
		{
			System.out.println("Now calculating the response time of all Tasks with deadlines no greater than " + deadline + " after time " + response + "...");
			System.out.println(Expression.processAll(ret_exp));
		}
		return (int) ret_exp.getProcessedValue();
	}
	
	protected boolean isPossible(List<Task> list)
	{
		List<Expression> exps = new ArrayList<Expression>();
		for(int index = 0; index < list.size(); index++)
		{
			exps.add(new DivExp(new NumExp(list.get(index).getComputationTime()), new NumExp(list.get(index).getPeriod())));
		}
		
		Expression ret = new AddExp(exps);
		double sum = ret.getProcessedValue();
		boolean possible = (sum <= 1.0);
		
		if(details)
		{
			System.out.println("Now calculating whether or not it is even possible to schedule the Tasks...");
			System.out.println(Expression.processAll(ret));
			System.out.println(sum + " <= 1 is " + possible);
			if(possible)
				System.out.println("The possibility test has passed.");
			else System.out.println("The possibility test has failed; the Tasks cannot be scheduled.");
		}
		return possible;
	}
	
	public boolean isSchedulable(List<Task> list)
	{
		return isSchedulable(list, false);
	}
	
	public boolean isSchedulable(List<Task> list, boolean details)
	{
		List<Task> newlist = new ArrayList<Task>();
		
		newlist.addAll(list);
		if(new DMS_Scheduler().determineScheduleLength(list) < 0)
		{
			if(details)
				System.out.println("The necessary schedule length is too long to generate a schedule for this list of Tasks; returning false.");
			return false;
		}
//		this.details = details;
		boolean ret = isPossible(newlist) && (utilizationTest(newlist) || exactAnalysis(newlist));
//		this.details = false;
		return ret;
	}
	
	protected void printExpression(Expression ret)
	{
		System.out.println(ret);
		while(!ret.isProcessed())
		{
			ret = ret.process();
			System.out.println(ret);
		}
	}
	
	protected boolean utilizationTest(List<Task> list)
	{
		double sum = 0.0;
		
		for(Task task : list)
			sum += (double) task.getComputationTime() / (double) task.getDeadline();
			
		return sum <= (list.size() * (Math.pow(2.0, (1.0 / (double) list.size())) - 1.0));
	}
}