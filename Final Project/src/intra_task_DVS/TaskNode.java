package intra_task_DVS;

import java.util.ArrayList;
import java.util.List;

import basics.Task;

/**
 * This class implements the logic necessary for intra-task dynamic voltage
 * scaling.
 * 
 * @author Robert Kloster
 * 		
 */
public class TaskNode extends Task
{
	/**
	 * Simple representation of the maximum frequency.
	 */
	public static final double MAX_FREQUENCY = 1.0;
	
	/**
	 * All Tasks that may occur immediately after this one.
	 */
	List<TaskNode> children = new ArrayList<TaskNode>();
	
	public TaskNode(String name, long computation_time)
	{
		this(name, computation_time, 0);
	}
	
	public TaskNode(String name, long computation_time, List<TaskNode> children)
	{
		this(name, computation_time);
		
		this.children = children;
	}
	
	private TaskNode(String name, long computation_time, long period)
	{
		super(name, computation_time, period);
	}
	
	/**
	 * Returns the average amount of power consumed with the specified
	 * frequency.
	 * 
	 * @param deadline
	 *            The deadline this TaskNode and all of its children.
	 * @param ideal_frequency
	 *            If true, the ideal frequency is calculated and used for each
	 *            TaskNode; otherwise, the maximum frequency is used.
	 * @return The average amount of power consumed with the specified
	 *         frequency.
	 */
	public double averageCasePower(double deadline, boolean ideal_frequency)
	{
		double frequency = ideal_frequency ? calculateIdealFrequency(deadline) : MAX_FREQUENCY;
		
//		System.out.println(getName() + "'s " + (ideal_frequency ? "ideal" : "maximum") + " frequency is " + frequency);

//		Once the frequency is calculated, we don't need to know the exact time; just the amount of time remaining.
		deadline -= calculateRuntime(frequency);
		
		double power = calculatePower(frequency);
		
//		System.out.println(getName() + "'s personal power consumption is " + power);
		
		if(children.isEmpty())
		{
			return power;
		}
		
//		Assume that each possible branch is equally likely: would be easy to implement more probabilistic customization later, if need be
		for(TaskNode tasknode : children)
		{
			power += (tasknode.averageCasePower(deadline, ideal_frequency) / (children.size()));
		}
		
//		System.out.println(getName() + "'s total power consumption is " + power);
		
		return power;
	}
	
	/**
	 * Returns the lowest possible amount of power consumed with the specified
	 * frequency.
	 * 
	 * @param deadline
	 *            The deadline this TaskNode and all of its children.
	 * @param ideal_frequency
	 *            If true, the ideal frequency is calculated and used for each
	 *            TaskNode; otherwise, the maximum frequency is used.
	 * @return The lowest possible amount of power consumed with the specified
	 *         frequency.
	 */
	public double bestCasePower(double deadline, boolean ideal_frequency)
	{
		double frequency = ideal_frequency ? calculateIdealFrequency(deadline) : MAX_FREQUENCY;
		
//		System.out.println(getName() + "'s " + (ideal_frequency ? "ideal" : "maximum") + " frequency is " + frequency);

//		Once the frequency is calculated, we don't need to know the exact time; just the amount of time remaining.
		deadline -= calculateRuntime(frequency);
		
//		System.out.println(getName() + "'s personal power consumption is " + calculatePower(frequency));
		
		if(children.isEmpty())
		{
			return calculatePower(frequency);
		}
		
		double add_to = children.get(0).bestCasePower(deadline, ideal_frequency);
		
		for(TaskNode tasknode : children)
		{
			add_to = Math.min(add_to, tasknode.bestCasePower(deadline, ideal_frequency));
		}
		
//		System.out.println(getName() + "'s total power consumption is " + calculatePower(frequency) + add_to);
		
		return calculatePower(frequency) + add_to;
	}
	
	/**
	 * Generates the ideal frequency at which this TaskNode should run.
	 * 
	 * @param deadline
	 * @return The ideal frequency at which this TaskNode should run.
	 */
	private double calculateIdealFrequency(double deadline)
	{
		return calculateWCET() / deadline;
	}
	
	/**
	 * Calculates the amount of power this one TaskNode uses at the given
	 * frequency.
	 * 
	 * @param frequency
	 *            The frequency at which this TaskNode will be run.
	 * @return The amount of power this one TaskNode uses at the given
	 *         frequency.
	 */
	private double calculatePower(double frequency)
	{
		return getComputationTime() * frequency * frequency;
	}
	
	/**
	 * Calculates the total amount of time this TaskNode will run.
	 * 
	 * @param frequency
	 *            The frequency at which this TaskNode will be run.
	 * @return The total amount of time this TaskNode will run.
	 */
	private double calculateRuntime(double frequency)
	{
		return getComputationTime() / frequency;
	}
	
	/**
	 * Calculate the Worst Case Execution Time of this TaskNode and all its
	 * children.
	 * 
	 * @return The Worst Case Execution Time of this TaskNode and all its
	 *         children.
	 */
	private double calculateWCET()
	{
		double add_to = 0.0;
		
		for(TaskNode taskNode : children)
		{
			add_to = Math.max(add_to, taskNode.calculateWCET());
		}
		
		return calculateRuntime(MAX_FREQUENCY) + add_to;
	}
	
	/**
	 * Get the direct children of this TaskNode.
	 * 
	 * @return The direct children of this TaskNode.
	 */
	public List<TaskNode> getChildren()
	{
		return children;
	}
	
	/**
	 * Returns the highest possible amount of power consumed with the specified
	 * frequency.
	 * 
	 * @param deadline
	 *            The deadline this TaskNode and all of its children.
	 * @param ideal_frequency
	 *            If true, the ideal frequency is calculated and used for each
	 *            TaskNode; otherwise, the maximum frequency is used.
	 * @return The highest possible amount of power consumed with the specified
	 *         frequency.
	 */
	public double worstCasePower(double deadline, boolean ideal_frequency)
	{
		double frequency = (ideal_frequency) ? calculateIdealFrequency(deadline) : MAX_FREQUENCY;
		
//		System.out.println(getName() + "'s " + (ideal_frequency ? "ideal" : "maximum") + " frequency is " + frequency);

//		Once the frequency is calculated, we don't need to know the exact time; just the amount of time remaining.
		deadline -= calculateRuntime(frequency);
		
//		System.out.println(getName() + "'s personal power consumption is " + calculatePower(frequency));
		
		double add_to = 0.0;
		
		for(TaskNode tasknode : children)
		{
			add_to = Math.max(add_to, tasknode.worstCasePower(deadline, ideal_frequency));
		}
		
//		System.out.println(getName() + "'s total power consumption is " + calculatePower(frequency) + add_to);
		
		return calculatePower(frequency) + add_to;
	}
}
