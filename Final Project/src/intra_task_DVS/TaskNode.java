package intra_task_DVS;

import java.util.ArrayList;
import java.util.List;

import basics.Task;

public class TaskNode extends Task
{
	List<TaskNode> children = new ArrayList<TaskNode>();
	
	public long getWCET(long deadline)
	{
		long ret = _computation_time;
		for(TaskNode taskNode : children)
		{
			ret = Math.max(ret, _computation_time + taskNode.getWCET(deadline));
		}
		return ret;
	}
	
	private double probability = 1.0;
	
	public void setProbability(double probability)
	{
		if(0.0 < probability)
		{
			this.probability = probability;
		}
	}
	
	public double getIdealFrequency(long deadline, double time)
	{
		return getWCET(deadline) / (deadline - time);
	}
	
	public TaskNode(String name, long computation_time, double probability)
	{
		this(name, computation_time);
		setProbability(probability);
	}
	
	public TaskNode(String name, long computation_time)
	{
		super(name, computation_time, 0);
	}
	
	public TaskNode(String name, long computation_time, double probability, List<TaskNode> children)
	{
		this(name, computation_time, probability);
		this.children = children;
	}
	
	private TaskNode(String name, long computation_time, long period)
	{
		super(name, computation_time, period);
	}
	
	public void add(TaskNode tasknode)
	{
		children.add(tasknode);
	}
	
	public double getBoringPower(long deadline, double time)
	{
		System.out.println(getName());
		System.out.println("time " + time);
		double power = _computation_time;
		System.out.println("Frequency " + getIdealFrequency(deadline, time));
		System.out.println("Power before " + power);
		System.out.println("Power after " + power);
		time += _computation_time / getIdealFrequency(deadline, time);
		double total_prob = 0.0;
		for(TaskNode tasknode : children)
			total_prob = tasknode.probability;
		System.out.println("Children\n{");
		for(TaskNode tasknode : children)
		{
			power += (tasknode.probability / total_prob) * tasknode.getAveragePower(deadline, time);
		}
		System.out.println("\n}");
		System.out.println(getName() + " Total power: " + power);
		return power;
	}
	
	public double getAveragePower(long deadline, double time)
	{
		System.out.println(getName());
		System.out.println("time " + time);
		double power = _computation_time;
		System.out.println("Frequency " + getIdealFrequency(deadline, time));
		System.out.println("Power before " + power);
		power = _computation_time * Math.pow(getIdealFrequency(deadline, time), 2.0);
		System.out.println("Power after " + power);
		time += _computation_time / getIdealFrequency(deadline, time);
		double total_prob = 0.0;
		for(TaskNode tasknode : children)
			total_prob = tasknode.probability;
		System.out.println("Children\n{");
		for(TaskNode tasknode : children)
		{
			power += (tasknode.probability / total_prob) * tasknode.getAveragePower(deadline, time);
		}
		System.out.println("\n}");
		System.out.println(getName() + " Total power: " + power);
		return power;
	}
}
