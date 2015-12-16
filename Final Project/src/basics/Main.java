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

/**
 * This Main class allows for a user to set parameters on a randomized Task
 * generator, such as how many Tasks per set, and the ranges of the periods,
 * deadlines, and computation times.
 *
 * @author Robert Kloster
 *		
 */
public class Main
{
	/**
	 * Simple containing class to store common information regarding an
	 * Algorithm.
	 *
	 * @author Robert Kloster
	 */
	public class Algorithm
	{
		public SchedulabilityChecker	checker;
		public Scheduler				scheduler;
		public String					name;
		
		public Algorithm(String name, SchedulabilityChecker checker, Scheduler Scheduler)
		{
			this.name = name;
			this.checker = checker;
			scheduler = Scheduler;
		}
	}
	
	private static final Main THIS = new Main();
	
	/**
	 * 1 <= number_of_tasks <= MAX_TASK_NUMBER
	 */
	private static final int MAX_TASK_NUMBER = 5;
	
	/**
	 * 1 <= computation_time <= deadline <= period <= MAX_PERIOD_SIZE
	 */
	private static final int MAX_PERIOD_SIZE = 25;
	
	/**
	 * The number of tests to run.
	 */
	private static final int NUMBER_OF_TESTS = 500;
	
	/**
	 * The seed for the random number generator.
	 */
	private static final int RANDOM_SEED = 0;
	
	/**
	 * An array of Algorithms to use on the generated Task lists.
	 */
	private static final Algorithm[] ALGORITHMS = {THIS.new Algorithm("RMS", new RMS_Checker(), new RMS_Scheduler()), THIS.new Algorithm("DMS", new DMS_Checker(), new DMS_Scheduler()), THIS.new Algorithm("EDF", new EDF_Checker(), new EDF_Scheduler()), THIS.new Algorithm("LLF", new LLF_Checker(), new LLF_Scheduler())};
	
	/**
	 * The random number generator, given RANDOM_SEED as its seed.
	 */
	private static final Random RAND = new Random(RANDOM_SEED);
	
	/**
	 * Tests a given Task list with the 4 main algorithms.
	 * 
	 * @param list
	 *            The given Task list to check and possibly schedule.
	 * @return True if there is no mismatch in the checker's result and the
	 *         scheduler's result, false otherwise.
	 */
	public static boolean customTest(List<Task> list)
	{
		System.out.println("Tasks to be Examined:\n");
		for(Task task : list)
		{
			System.out.println("Name = " + task.getName() + ",Computation time = " + task.getComputationTime() + ", Period = " + task.getPeriod() + ", Deadline = " + task.getDeadline());
		}
		
		for(Algorithm algorithm : ALGORITHMS)
		{
			boolean check, schedule;
			System.out.println("\n" + algorithm.name + " Check = " + (check = algorithm.checker.isSchedulable(list)));
			System.out.println(algorithm.name + " Schedule = " + (schedule = algorithm.scheduler.schedule(list, true)));
			if(check != schedule)
			{
				System.out.println("We have a mismatch!");
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args)
	{
		
//		For use in debugging and testing edge cases.
//		customTest(/*Your Task list here*/);
		
		test();
	}
	
	/**
	 * Randomly generates a Task with the given name.
	 * 
	 * @param name
	 *            The name of the Task.
	 * @return A randomly generated Task.
	 */
	private static Task randomTask(String name)
	{
		int period = 1 + RAND.nextInt(1 + RAND.nextInt(MAX_PERIOD_SIZE));
		int deadline = 1 + RAND.nextInt(period);
		int comp = 1 + RAND.nextInt(deadline);
		Task ret = new Task(name, comp, period, deadline);
		return ret;
	}
	
	/**
	 * Performs random Task list generation, schedulability checking, and
	 * scheduling.
	 * 
	 * @return True if there is no mismatch in the checker's result and the
	 *         scheduler's result, false otherwise.
	 */
	private static boolean test()
	{
		for(int i = 0; i < NUMBER_OF_TESTS; i++)
		{
			List<Task> list = new ArrayList<Task>();
			
			for(int j = 0, length = 1 + RAND.nextInt(MAX_TASK_NUMBER); j < length; j++)
			{
				list.add(randomTask("T" + j));
			}
			
			System.out.println("\nTrial number " + i + "\n");
			
			System.out.println("Tasks to be Examined:\n");
			
			for(Task task : list)
			{
				System.out.println("Name = " + task.getName() + ", Computation time = " + task.getComputationTime() + ", Period = " + task.getPeriod() + ", Deadline = " + task.getDeadline());
			}
			
			System.out.println();
			
			for(Algorithm algorithm : ALGORITHMS)
			{
				boolean checked = algorithm.checker.isSchedulable(list);
				
				System.out.println(algorithm.name + " Check Results: " + checked);
				
				boolean scheduled = checked;
				
				if(checked)
				{
					System.out.println("Now scheduling using " + algorithm.name + "...\n");
					scheduled = algorithm.scheduler.schedule(list, true);
					System.out.println();
				}
				else
				{
					scheduled = algorithm.scheduler.schedule(list);
				}
				
				if(checked != scheduled)
				{
					System.out.println("We have a mismatch!");
					return false;
				}
			}
			System.out.println();
		}
		
		System.out.println("All " + NUMBER_OF_TESTS + " tests complete.");
		return true;
	}
}
