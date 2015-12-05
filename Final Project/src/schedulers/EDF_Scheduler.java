package schedulers;

import java.util.ArrayList;
import java.util.List;

import basics.Task;

public class EDF_Scheduler extends Scheduler
{
	@Override
	public double getRelevantValue(Task task, long time)
	{
		return task.getNextDeadline();
	}
	
	@Override
	public String printTaskStatus(Task task, long time)
	{
		return "Task " + task.getName() + " has a deadline at " + (long) getRelevantValue(task, time) + ".";
	}
	
	@Override
	public List<Task> prune(List<Task> list, long time)
	{
		List<Task> ans = lowestSort(list, time);
		
		if(ans.size() < 2)
			return ans;
		
		double laxity = ans.get(0).getLaxity(time);
		
		for(Task task : ans)
			laxity = Math.min(laxity, task.getLaxity(time));
		
		List<Task> ret = new ArrayList<Task>();
		
		for(Task task : ans)
			if(task.getLaxity(time) <= laxity)
				ret.add(task);
		
		return ret;
	}
}
