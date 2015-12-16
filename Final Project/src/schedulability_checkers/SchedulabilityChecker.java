package schedulability_checkers;

import java.util.List;

import basics.Task;

public interface SchedulabilityChecker
{
	public boolean isSchedulable(List<Task> list);
}
