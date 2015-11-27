package schedulability_checkers;

import java.util.ArrayList;
import java.util.List;

import basics.Task;
import basics.TaskList;
import basics.expressions.AddExp;
import basics.expressions.DivExp;
import basics.expressions.Expression;
import basics.expressions.MultExp;
import basics.expressions.NumExp;
import basics.expressions.PowExp;
import basics.expressions.SubExp;
import schedulers.RMS_Scheduler;

public class RMS_Checker implements SchedulabilityChecker
{
	public boolean checkSchedulability(TaskList tasklist)
	{
		List<Expression> lhs_sum = new ArrayList<Expression>();
		for(Task task : tasklist)
			lhs_sum.add(new DivExp(new NumExp(task.getComputationTime()), new NumExp(task.getPeriod())));
		Expression lhs = new AddExp(lhs_sum), rhs = new MultExp(new NumExp(tasklist.size()), new SubExp(new PowExp(new NumExp(2.0), new DivExp(new NumExp(1.0), new NumExp(tasklist.size()))), new NumExp(1.0)));
		return lhs.getProcessedValue() <= rhs.getProcessedValue();
	}
	
	public static boolean check(TaskList tasklist)
	{
		return new RMS_Checker().checkSchedulability(tasklist);
	}
	
	public static void detailedCheck(TaskList tasklist)
	{
		new RMS_Checker().properOutput(tasklist);
	}
	
	public void properOutput(TaskList tasklist)
	{
		for(int index = 0; index < tasklist.size(); index++)
		{
			if(tasklist.get(index).getDeadline() < tasklist.get(index).getPeriod())
			{
				System.out.println("Be warned, the deadline of at least one of the Tasks in the given TaskList is shorter than its period; you want DMS, not RMS.  Now loading DMS...");
				DMS_Checker.detailedCheck(tasklist);
				return;
			}
		}
		
		List<Expression> lhs_sum = new ArrayList<Expression>();
		for(Task task : tasklist)
			lhs_sum.add(new DivExp(new NumExp(task.getComputationTime()), new NumExp(task.getPeriod())));
		Expression lhs = new AddExp(lhs_sum), rhs = new MultExp(new NumExp(tasklist.size()), new SubExp(new PowExp(new NumExp(2.0), new DivExp(new NumExp(1.0), new NumExp(tasklist.size()))), new NumExp(1.0)));
		while(!lhs.isProcessed() || !rhs.isProcessed())
		{
			System.out.println(lhs + " <= " + rhs);
			lhs = lhs.process();
			rhs = rhs.process();
		}
		System.out.println("Final Result: " + lhs + " <= " + rhs);
		if(lhs.getProcessedValue() <= rhs.getProcessedValue())
		{
			System.out.println("The given TaskList is schedulable by the RMS algorithm.");
		}
		else
		{
			System.out.println("The schedulability check alone cannot determine whether or not this task is schedulable by the RMS algorithm; manually scheduling using RMS now...");
			if(new RMS_Scheduler().schedule(tasklist))
				System.out.println("The given TaskList is indeed schedulable by the RMS algorithm.");
			else
				System.out.println("The given TaskList is not schedulable by the RMS algorithm.");
		}
		
	}
}