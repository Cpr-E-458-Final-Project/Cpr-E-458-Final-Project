package schedulers;

import java.util.ArrayList;
import java.util.List;

import basics.Task;

public class LLF_Scheduler extends Scheduler
{
	@Override
	public int getRelevantValue(Task task, int time)
	{
		return task.getLaxity(time);
	}
	
	@Override
	public String printTaskStatus(Task task, int time)
	{
		return "Task " + task.getName() + " has a laxity of " + getRelevantValue(task, time) + ".";
	}
	
	@Override
	public List<Task> prune(List<Task> list, int time)
	{
		List<Task> ans = lowestSort(list, time);
		
		if(ans.size() < 2)
			return ans;
		
		int deadline = ans.get(0).getNextDeadline();
		
		for(Task task : ans)
			deadline = Math.min(deadline, task.getNextDeadline());
		
		List<Task> ret = new ArrayList<Task>();
		for(Task task : ans)
			if(task.getNextDeadline() <= deadline)
				ret.add(task);
		
		return ret;
	}
}
