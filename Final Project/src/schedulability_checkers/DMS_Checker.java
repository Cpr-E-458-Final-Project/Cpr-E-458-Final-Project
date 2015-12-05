package schedulability_checkers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import basics.Task;
import basics.expressions.AddExp;
import basics.expressions.DivExp;
import basics.expressions.Expression;
import basics.expressions.NumExp;

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
		
		for(int index = 0, length = newlist.size(); index < length; index++)
		{
			int deadline = newlist.get(index).getDeadline();
			
			List<Expression> exps = new ArrayList<Expression>();
			
			for(Task task : newlist)
			{
				if(task.getDeadline() <= newlist.get(index).getDeadline())
				{
					exps.add(new NumExp(task.getComputationTime()));
				}
			}
			
			Expression response_exp = new AddExp(exps);
			
			int response = (int) response_exp.getProcessedValue();
			
			boolean keep_going = true;
			
			while(keep_going)
			{
				if(deadline < response) return false;
				
				if((response <= deadline) && (response == getResponse(newlist, newlist.get(index).getDeadline(), response))) keep_going = false;
				
				response = getResponse(newlist, newlist.get(index).getDeadline(), response);
			}
		}
		
		return true;
	}
	
	protected int getDemand(Task task, int time)
	{
		return(task.getComputationTime() * (int) Math.ceil(((double) time) / ((double) task.getPeriod())));
	}
	
	protected int getResponse(List<Task> list, int value, int response)
	{
		int ret = 0;
		for(Task task : list)
			if(task.getDeadline() <= value) ret += getDemand(task, response);
		return ret;
	}
	
	/**
	 * / protected boolean isHarmonic(List<Task> list) { if(list.size() <
	 * 2) return true;
	 * 
	 * int[] arr = new int[list.size()];
	 * 
	 * for(int index = 0; index < arr.length; index++) arr[index] =
	 * list.get(index).getPeriod();
	 * 
	 * Arrays.sort(arr);
	 * 
	 * for(int greater = 1; greater < arr.length; greater++) for(int lesser =
	 * greater - 1; -1 < lesser; lesser--) if(arr[greater] == arr[lesser])
	 * continue; else if(arr[greater] % arr[lesser] != 0) return false;
	 * 
	 * return true; } /
	 **/
	
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
			System.out.println("Now calculating whether or not it is even possible to schedule the List<Task>...\n{");
			printExpression(ret);
			System.out.println(sum + " <= 1.0 is " + possible);
			if(possible)
				System.out.println("The possibility test has passed.");
			else System.out.println("The possibility test has failed; the List<Task> cannot be scheduled.");
			System.out.println("}");
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
		this.details = details;
		boolean ret = isPossible(newlist) && (utilizationTest(newlist) || exactAnalysis(newlist));
		this.details = false;
		return ret;
	}
	
	protected void printExpression(Expression ret)
	{
		System.out.println(ret.print());
		while(!ret.isProcessed())
		{
			ret = ret.process();
			System.out.println(ret.print());
		}
	}
	
	protected boolean utilizationTest(List<Task> list)
	{
		double sum = 0.0;
		
		for(int index = 0; index < list.size(); index++)
			sum += (double) list.get(index).getComputationTime() / (double) list.get(index).getDeadline();
			
		return sum <= (list.size() * (Math.pow(2.0, (1.0 / (double) list.size())) - 1.0));
	}
}