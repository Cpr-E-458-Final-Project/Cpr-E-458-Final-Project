package basics;

public class Task
{
	private int _computation_time = -1;
	private int _period = -1;
	private int _priority = -1;
	
	private boolean _is_periodic = true;
	
	public Task(int computation_time, int period, int priority)
	{
		_computation_time = computation_time;
		_period = period;
		_priority = priority;
	}
	
	public boolean setIsPeriodic(boolean is_periodic)
	{
		return _is_periodic = is_periodic;
	}
	
	public boolean getIsPeriodic()
	{
		return _is_periodic;
	}
}
