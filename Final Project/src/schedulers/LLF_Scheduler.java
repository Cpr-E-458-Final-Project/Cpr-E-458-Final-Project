package schedulers;

import java.util.ArrayList;
import java.util.List;

import basics.Task;

public class LLF_Scheduler extends Scheduler
{
	@Override
	public double getRelevantValue(Task task, long time)
	{
		return task.getLaxity(time);
	}
	
	@Override
	public String printTaskStatus(Task task, long time)
	{
		return "Task " + task.getName() + " has a laxity of " + (long) getRelevantValue(task, time) + ".";
	}
	
	@Override
	public List<Task> prune(List<Task> list, long time)
	{
		List<Task> ans = lowestSort(list, time);
		
		if(ans.size() < 2)
			return ans;
		
		long deadline = ans.get(0).getNextDeadline();
		
		for(Task task : ans)
			deadline = Math.min(deadline, task.getNextDeadline());
		
		List<Task> ret = new ArrayList<Task>();
		for(Task task : ans)
			if(task.getNextDeadline() <= deadline)
				ret.add(task);
		
		return ret;
	}
}
