package schedulers;

import java.util.ArrayList;
import java.util.List;

import basics.Task;

public class EDF_Scheduler extends Scheduler
{
	@Override
	public int getRelevantValue(Task task, int time)
	{
		return task.getNextDeadline();
	}
	
	@Override
	public String printTaskStatus(Task task, int time)
	{
		return "Task " + task.getName() + " has a deadline at " + getRelevantValue(task, time) + ".";
	}
	
	@Override
	public List<Task> prune(List<Task> list, int time)
	{
		List<Task> ans = lowestSort(list, time);
		
		if(ans.size() < 2)
			return ans;
		
		int laxity = ans.get(0).getLaxity(time);
		
		for(Task task : ans)
			laxity = Math.min(laxity, task.getLaxity(time));
		
		List<Task> ret = new ArrayList<Task>();
		
		for(Task task : ans)
			if(task.getLaxity(time) <= laxity)
				ret.add(task);
		
		return ret;
	}
}
