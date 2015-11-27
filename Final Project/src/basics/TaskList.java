package basics;

import java.util.ArrayList;

public class TaskList extends ArrayList<Task>
{

	/**
	 * The unique serial version identification number.
	 */
	private static final long serialVersionUID = 5743712444275418557L;

	public void reset()
	{
		for(Task task : this)
			task.reset();
	}
	
	public TaskList clone()
	{
		TaskList ret = new TaskList();
		for(Task task : this)
			ret.add(task.clone());
		return ret;
	}
}
