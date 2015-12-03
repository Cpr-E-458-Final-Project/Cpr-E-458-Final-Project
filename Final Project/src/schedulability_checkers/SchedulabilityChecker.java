package schedulability_checkers;

import basics.TaskList;

public interface SchedulabilityChecker
{
	/**
	 * Simply returns if the TaskList passes the check.
	 * @param tasklist The TaskList to be checked.
	 * @return Whether or not the TaskList passes the check.
	 */
	public boolean checkSchedulability(TaskList tasklist);
	
	/**
	 * Prints out the detailed results of how the check is performed.
	 * @param taskset The TaskList to be checked.
	 */
	public void properOutput(TaskList tasklist);
}
