package basics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import schedulability_checkers.DMS_Checker;
import schedulability_checkers.EDF_Checker;
import schedulability_checkers.LLF_Checker;
import schedulability_checkers.RMS_Checker;
import schedulability_checkers.SchedulabilityChecker;
import schedulers.DMS_Scheduler;
import schedulers.EDF_Scheduler;
import schedulers.LLF_Scheduler;
import schedulers.RMS_Scheduler;
import schedulers.Scheduler;

public class Main
{
	private static Main This = new Main();
	
	public interface Judger
	{
		public boolean judge(boolean[] results);
	}
	
	public class DMS_not_RMS implements Judger
	{
		@Override
		public boolean judge(boolean[] results)
		{
			return !results[RMS] && results[DMS];
		}
	}
	
	public class All_pass implements Judger
	{
		@Override
		public boolean judge(boolean[] results)
		{
			return results[RMS];
		}
	}
	
	public class All implements Judger
	{
		@Override
		public boolean judge(boolean[] results)
		{
			return true;
		}
	}
	
	public class All_fail implements Judger
	{
		@Override
		public boolean judge(boolean[] results)
		{
			return !results[EDF];
		}
	}
	
	public class EDF_not_DMS implements Judger
	{
		@Override
		public boolean judge(boolean[] results)
		{
			return !results[DMS] && results[EDF];
		}
	}
	
	public class Algorithm
	{
		public SchedulabilityChecker	checker;
		public Scheduler				scheduler;
		public String					name;
		
		public Algorithm(String name, SchedulabilityChecker checker, Scheduler Scheduler)
		{
			this.name = name;
			this.checker = checker;
			this.scheduler = Scheduler;
		}
	}
	
	private static final int MAX_TASK_NUMBER = 5;
	
	private static final int MAX_PERIOD_SIZE = 20;
	
	private static final int NUMBER_OF_TESTS = 50;
	
	private static final int	RMS	= 0;
	private static final int	DMS	= 1;
	private static final int	EDF	= 2;
	@SuppressWarnings("unused")
	private static final int	LLF	= 3;
	
	@SuppressWarnings("unused")
	private static final boolean DETAILS = true;
	
	private static Algorithm[] algorithms = {
			This.new Algorithm("RMS", new RMS_Checker(), new RMS_Scheduler()),
			This.new Algorithm("DMS", new DMS_Checker(), new DMS_Scheduler()),
			This.new Algorithm("EDF", new EDF_Checker(), new EDF_Scheduler()),
			This.new Algorithm("LLF", new LLF_Checker(), new LLF_Scheduler())};
			
	private static Random rand = new Random(1);
	
	public static void main(String[] args)
	{
		
		test();
		
//		Task[] arr = {
//		        new Task("T0", 14, 18, 14),
//		        new Task("T1", 1, 9, 2),
//		        new Task("T2", 2, 19, 2),
//		        new Task("T3", 2, 7, 2),
//		        new Task("T4", 3, 45, 11),
//		        new Task("T5", 26, 42, 26),
//		        new Task("T6", 19, 58, 33),
//		        new Task("T7", 49, 74, 68),
//		        new Task("T8", 6, 11, 6),
//		        new Task("T9", 13, 46, 23)};
//				
//		List<Task> list = new ArrayList<Task>();
//		
//		for(int i = 0; i < arr.length; i++)
//		{
//			list.add(arr[i]);
//		}
//		
//		System.out.println("Tasks to be Examined:\n");
//		for(Task task : list)
//		{
//			System.out.println("Name = " + task.getName() + ",Computation time = " + task.getComputationTime() + ", Period = " + task.getPeriod() + ", Deadline = " + task.getDeadline());
////			System.out.println("new Task(\"" + task.getName() + "\", " + task.getComputationTime() + ", " + task.getPeriod() + ", " + task.getDeadline() + "),");
//		}
//		
//		for(int index = 0; index < algorithms.length; index++)
//		{
//			boolean check, schedule;
//			System.out.println("\n" + algorithms[index].name + " Check = " + (check = algorithms[index].checker.isSchedulable(list, DETAILS)));
//			System.out.println(algorithms[index].name + " Schedule = " + (schedule = algorithms[index].scheduler.schedule(list, DETAILS)));
//			if(check != schedule)
//			{
//				System.out.println("We have a mismatch!");
//				return;
//			}
//		}
	}
	
	private static Task randomTask(String name)
	{
		int period = 1 + rand.nextInt(1 + rand.nextInt(MAX_PERIOD_SIZE));
		int deadline = 1 + rand.nextInt(period);
		int comp = 1 + rand.nextInt(deadline);
		Task ret = new Task(name, comp, period, deadline);
		return ret;
	}

	@SuppressWarnings("unused")
	private static final Judger DMS_NOT_RMS = This.new DMS_not_RMS();
	@SuppressWarnings("unused")
	private static final Judger EDF_NOT_DMS = This.new EDF_not_DMS();
	@SuppressWarnings("unused")
	private static final Judger ALL_PASS = This.new All_pass();
	@SuppressWarnings("unused")
	private static final Judger ALL_FAIL = This.new All_fail();
	@SuppressWarnings("unused")
	private static final Judger ALL = This.new All();

	private static final Judger PARSE = ALL;
	
	private static boolean test()
	{
		boolean[] results = new boolean[4];
		
		for(int i = 0; i < NUMBER_OF_TESTS; i++)
		{
			List<Task> list = new ArrayList<Task>();
			
			for(int j = 0, length = 1 + rand.nextInt(MAX_TASK_NUMBER); j < length; j++)
			{
				list.add(randomTask("T" + j));
			}
			
			for(int index = 0; index < algorithms.length; index++)
			{
				results[index] = algorithms[index].checker.isSchedulable(list);
			}
			
			if(PARSE.judge(results))
			{
				System.out.println("\nTrial number " + i + "\n");
				
				System.out.println("Tasks to be Examined:\n");
				
				for(Task task : list)
				{
					System.out.println("Name = " + task.getName() + ", Computation time = " + task.getComputationTime() + ", Period = " + task.getPeriod() + ", Deadline = " + task.getDeadline());
//					System.out.println("new Task(\"" + task.getName() + "\", " + task.getComputationTime() + ", " + task.getPeriod() + ", " + task.getDeadline() + "),");
				}
				
				System.out.println();
				
				for(int index = 0; index < algorithms.length; index++)
				{
					boolean check, schedule;
					System.out.println(algorithms[index].name + " Check = " + (check = algorithms[index].checker.isSchedulable(list, true)));
					System.out.println(algorithms[index].name + " Schedule = " + (schedule = algorithms[index].scheduler.schedule(list, true)) + "\n");
					
					if(check != schedule)
					{
						System.out.println("We have a mismatch!");
						return false;
					}
				}
			}
		}
		System.out.println("All Tests pass.");
		return true;
	}
}
