package schedulability_checkers;

import java.util.List;

import basics.Task;
import basics.Util;
import schedulers.RMS_Scheduler;

public class RMS_Checker implements SchedulabilityChecker
{
	private static final RMS_Scheduler RMS_SCHEDULER = new RMS_Scheduler();
	private static final DMS_Checker DMS_CHECKER = new DMS_Checker();
	
	public boolean isSchedulable(List<Task> list)
	{
		if(Util.determineScheduleLength(list) < 0)
		{
			System.out.println("The necessary schedule length is too long to generate a schedule for this list of Tasks; returning false.");
			return false;
		}
		
		if(!DMS_CHECKER.isSchedulable(list)) return false;
		
		for(Task task : list)
			if(task.getDeadline() != task.getPeriod()) return RMS_SCHEDULER.schedule(list);
		
		return true;
	}
}