package schedulability_checkers;

import java.util.ArrayList;
import java.util.List;

import basics.Task;
import basics.TaskList;
import basics.expressions.AddExp;
import basics.expressions.DivExp;
import basics.expressions.Expression;
import basics.expressions.NumExp;
import schedulers.LLF_Scheduler;

public class LLF_Checker implements SchedulabilityChecker
{
	public boolean checkSchedulability(TaskList tasklist)
	{
		List<Expression> lhs_sum = new ArrayList<Expression>();
		for(Task task : tasklist)
			lhs_sum.add(new DivExp(new NumExp(task.getComputationTime()), new NumExp(task.getPeriod())));
		Expression lhs = new AddExp(lhs_sum), rhs = new NumExp(1.0);
		return lhs.getProcessedValue() <= rhs.getProcessedValue();
	}
	
	public static boolean check(TaskList tasklist)
	{
		return new LLF_Checker().checkSchedulability(tasklist);
	}
	
	public static void detailedCheck(TaskList tasklist)
	{
		new LLF_Checker().properOutput(tasklist);
	}
	
	public void properOutput(TaskList tasklist)
	{
		boolean manual_check = false;
		
		for(int index = 0; index < tasklist.size(); index++)
		{
			if(tasklist.get(index).getDeadline() < tasklist.get(index).getPeriod())
			{
				System.out.println("Be warned, the EDF and LLF schedulability checks do not account for deadlines shorter than the period; manual testing will be required to be certain in those cases...");
				manual_check = true;
			}
		}
		
		List<Expression> lhs_sum = new ArrayList<Expression>();
		
		for(Task task : tasklist)
			lhs_sum.add(new DivExp(new NumExp(task.getComputationTime()), new NumExp(task.getPeriod())));
		
		Expression lhs = new AddExp(lhs_sum), rhs = new NumExp(1.0);getClass();
		
		while(!lhs.isProcessed() || !rhs.isProcessed())
		{
			System.out.println(lhs + " <= " + rhs);
			lhs = lhs.process();
			rhs = rhs.process();
		}
		
		System.out.println("Final Result: " + lhs + " <= " + rhs);
		
		if(lhs.getProcessedValue() <= rhs.getProcessedValue())
			System.out.println("The given TaskList is schedulable by the LLF algorithm.");
		else
			System.out.println("The given TaskList is not schedulable by the LLF algorithm.");
		
		if(manual_check)
		{
			System.out.println("HOWEVER, because there are deadlines shorter than the period in the given TaskList, manual checking will be required.  Now scheduling using LLF...");
			if(new LLF_Scheduler().schedule(tasklist))
				System.out.println("The given TaskList is indeed schedulable by the LLF algorithm.");
			else
				System.out.println("The given TaskList is not schedulable by the LLF algorithm.");
		}
		
	}
}