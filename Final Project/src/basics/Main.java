package basics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import schedulability_checkers.EDF_Checker;
import schedulability_checkers.SchedulabilityChecker;
import schedulers.EDF_Scheduler;
import schedulers.Scheduler;

public class Main
{
	
	static Scheduler scheduler = new EDF_Scheduler();
	
	static SchedulabilityChecker checker = new EDF_Checker();
	
	private static Random rand = new Random();
	
	private static Task randomTask(String name)
	{
		int period = 1 + rand.nextInt(10);
		int deadline = 1 + rand.nextInt(period);
		int comp = 1 + rand.nextInt(deadline);
		Task ret = new Task(name, comp, period, deadline);
		return ret;
	}
	
	@SuppressWarnings("unused")
	private static boolean test(int limit)
	{
		Task a, b, c;
		for(int i = 0; i < limit; i++)
		{
			a = randomTask("a");
			b = randomTask("b");
			c = randomTask("c");
			
			ArrayList<Task> list = new ArrayList<Task>();
			
			list.add(a);
			list.add(b);
			list.add(c);
			
			System.out.println("Trial number " + i);
			System.out.println("a.comp = " + a.getComputationTime() + " a.p = " + a.getPeriod() + " a.d = " + a.getDeadline());
			System.out.println("b.comp = " + b.getComputationTime() + " b.p = " + b.getPeriod() + " b.d = " + b.getDeadline());
			System.out.println("c.comp = " + c.getComputationTime() + " c.p = " + c.getPeriod() + " c.d = " + c.getDeadline());
			
			boolean schedulable = scheduler.schedule(list);
			boolean checked = checker.isSchedulable(list);
			
			System.out.println("checker " + checked);
			System.out.println("scheduler " + schedulable);
			
			if(schedulable != checked)
			{
				System.out.println("FAIL!");
				return false;
			}
		}
		System.out.println("We win!");
		return true;
	}
	
	public static void main(String[] args)
	{
		
//		test(10000);
		
		 Task a = new Task("T1", 1, 2, 2);
		 Task b = new Task("T2", 6, 8, 8);
		 Task c = new Task("T3", 1, 8, 7);
		
		 List<Task> list = new ArrayList<Task>();
		
		 list.add(a);
		 list.add(b);
		 list.add(c);
		
		 boolean checked = checker.isSchedulable(list, true);
		 boolean scheduled = scheduler.schedule(list, true);
		
		 System.out.println("Check " + checked);
		 System.out.println("Schedule " + scheduled);
		
	}
}
