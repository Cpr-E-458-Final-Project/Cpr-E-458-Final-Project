package schedulability_checkers;

import java.util.List;

import basics.Task;
import schedulers.RMS_Scheduler;

public class RMS_Checker extends DMS_Checker
{
	public boolean isSchedulable(List<Task> list, boolean details)
	{
		boolean ret = new DMS_Checker().isSchedulable(list, details);
		
		if(!ret) return false;
		
		for(Task task : list)
			if(task.getDeadline() != task.getPeriod())
				return new RMS_Scheduler().schedule(list, details);
		return ret;
	}
}