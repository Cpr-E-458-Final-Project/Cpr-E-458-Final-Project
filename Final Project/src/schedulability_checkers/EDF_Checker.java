package schedulability_checkers;

import java.util.List;

import basics.Task;
import basics.Util;

public class EDF_Checker implements SchedulabilityChecker
{
	boolean deadline_less_than(List<Task> list)
	{
		double sum = 0.0;
		
		for(Task task : list)
			sum += ((double) task.getComputationTime()) / ((double) task.getDeadline());
		return sum <= 1.0;
	}
	
	long getProcessorDemand(Task task, long time)
	{
		return(((long) (Math.floor(((double) (time - task.getDeadline())) / (double) task.getPeriod())) + 1) * task.getComputationTime());
	}
	
	long getProcessorDemand(List<Task> list, int time)
	{
		long ret = 0;
		
		for(Task task : list)
			ret += getProcessorDemand(task, time);
			
		return ret;
	}
	
	@Override
	public boolean isSchedulable(List<Task> list)
	{
		if(Util.determineScheduleLength(list) < 0)
		{
			System.out.println("The necessary schedule length is too long to generate a schedule for this list of Tasks; returning false.");
			return false;
		}
		return (isSimple(list)) ? period_less_than(list) : (deadline_less_than(list) ? true : processorDemandTest(list));
	}
	
	boolean isSimple(List<Task> list)
	{
		for(Task task : list)
			if(task.getDeadline() != task.getPeriod()) return false;
			
		return true;
	}
	
	boolean period_less_than(List<Task> list)
	{
		double sum = 0.0;
		
		for(Task task : list)
			sum += ((double) task.getComputationTime()) / ((double) task.getPeriod());
			
		return sum <= 1.0;
	}
	
	boolean processorDemandTest(List<Task> list)
	{
		long length = Util.determineScheduleLength(list);
		for(int time = 0; time <= length; time++)
			if(time < getProcessorDemand(list, time)) return false;
		return true;
	}
}