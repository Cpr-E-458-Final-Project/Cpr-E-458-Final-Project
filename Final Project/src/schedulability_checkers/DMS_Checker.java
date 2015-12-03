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
import schedulers.DMS_Scheduler;

public class DMS_Checker implements SchedulabilityChecker
{
	public boolean checkSchedulability(TaskList tasklist)
	{
		List<Expression> lhs_sum = new ArrayList<Expression>();
		for(Task task : tasklist)
			lhs_sum.add(new DivExp(new NumExp(task.getComputationTime()), new NumExp(task.getDeadline())));
		Expression lhs = new AddExp(lhs_sum), rhs = new MultExp(new NumExp(tasklist.size()), new SubExp(new PowExp(new NumExp(2.0), new DivExp(new NumExp(1.0), new NumExp(tasklist.size()))), new NumExp(1.0)));
		return lhs.getProcessedValue() <= rhs.getProcessedValue();
	}
	
	public static boolean check(TaskList tasklist)
	{
		return new DMS_Checker().checkSchedulability(tasklist);
	}
	
	public static void detailedCheck(TaskList tasklist)
	{
		new DMS_Checker().properOutput(tasklist);
	}
	
	public void properOutput(TaskList tasklist)
	{
		List<Expression> lhs_sum = new ArrayList<Expression>();
		for(Task task : tasklist)
			lhs_sum.add(new DivExp(new NumExp(task.getComputationTime()), new NumExp(task.getDeadline())));
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
			System.out.println("The given TaskList is schedulable by the DMS algorithm.");
		}
		else
		{
			System.out.println("The schedulability check alone cannot determine whether or not this task is schedulable by the DMS algorithm; manually scheduling using DMS now...");
			if(new DMS_Scheduler().schedule(tasklist))
				System.out.println("The given TaskList is indeed schedulable by the DMS algorithm.");
			else
				System.out.println("The given TaskList is not schedulable by the DMS algorithm.");
		}
	}
}