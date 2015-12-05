package basics;

public class Task
{
	/**
	 * The total amount (in units of time) that a Task has to run for each
	 * period.
	 */
	long _computation_time = -1;
	
	/**
	 * The amount (in remaining units of time) that this Task needs to run for
	 * during this period.
	 */
	long _remaining_comp_time = -1;
	
	/**
	 * The length (in units of time) of this Task's period.
	 */
	long _period = -1;
	
	/**
	 * The deadline (relative to the start of each period) that this Task needs
	 * to meet.
	 */
	long _deadline = -1;
	
	/**
	 * The deadline (relative to the start of the current period) that this Task
	 * needs to meet.
	 */
	long _next_deadline = -1;
	
	/**
	 * The instant (represented in units of time) that the next period of this
	 * Task begins.
	 */
	long _next_period = -1;
	
	/**
	 * the relative value (used in HVDT scheduling) of this Task.
	 */
	double _value = -1;
	
	/**
	 * The name of the Task.
	 */
	String _name = null;
	
	/**
	 * Constructs a Task object.
	 * 
	 * @param name
	 *            The name of the Task.
	 * @param computation_time
	 *            The periodic computational need of the Task.
	 * @param period
	 *            The length of the period of the Task.
	 */
	public Task(String name, long computation_time, long period)
	{
		_name = name;
		_value = _remaining_comp_time = _computation_time = computation_time;
		_next_deadline = _deadline = _next_period = _period = period;
	}
	
	public Task(String name, long computation_time, long period, long deadline)
	{
		this(name, computation_time, period);
		_next_deadline = _deadline = deadline;
	}
	
	// @Override
	// public Task clone()
	// {
	// Task ret = new Task(_name, _computation_time, _period);
	// ret._deadline = _deadline;
	// ret._next_deadline = _next_deadline;
	// ret.setValue(_value);
	// return ret;
	// }
	
	/**
	 * Get the computational need of the Task.
	 * 
	 * @return The computational need of the Task.
	 */
	public long getComputationTime()
	{
		return _computation_time;
	}
	
	/**
	 * Get the deadline of the Task.
	 * 
	 * @return The deadline of the Task.
	 */
	public long getDeadline()
	{
		return _deadline;
	}
	
	/**
	 * Get the laxity of the Task at the given time.
	 * 
	 * @param time
	 *            The current unit of time.
	 * @return The laxity of the Task.
	 */
	public long getLaxity(long time)
	{
		return _next_deadline - (time + _remaining_comp_time);
	}
	
	/**
	 * Get the name of the Task.
	 * 
	 * @return The name of the Task.
	 */
	public String getName()
	{
		return _name;
	}
	
	/**
	 * Get the current deadline of the Task.
	 * 
	 * @return The current deadline of the Task.
	 */
	public long getNextDeadline()
	{
		return _next_deadline;
	}
	
	/**
	 * get the period of the Task.
	 * 
	 * @return The period of the Task.
	 */
	public long getPeriod()
	{
		return _period;
	}
	
	/**
	 * Get the remaining computaional need of the Task.
	 * 
	 * @return The remaining computaional need of the Task.
	 */
	public long getRemainingCompTime()
	{
		return _remaining_comp_time;
	}
	
	/**
	 * Get the relative value of the Task.
	 * 
	 * @return The relative value of the Task.
	 */
	public double getValue()
	{
		return _value;
	}
	
	/**
	 * Get the value density of the Task.
	 * 
	 * @return The value density of the Task.
	 */
	public double getValueDensity()
	{
		return ((double) _value) / ((double) _computation_time);
	}
	
	/**
	 * Returns of the Task currently has any computational need.
	 * 
	 * @return true if the Task can run, false otherwise.
	 */
	public boolean isReady()
	{
		return 0 < _remaining_comp_time;
	}
	
	public boolean isReasonable()
	{
		if((_computation_time < 1) || (_deadline < _computation_time) || (_period < _deadline)) return false;
		return true;
	}
	
	/**
	 * Resets the _remaining_comp_time, _next_deadline, and _next_period
	 * variables to their default starting values.
	 */
	public void reset()
	{
		_remaining_comp_time = _computation_time;
		_next_deadline = _deadline;
		_next_period = _period;
	}
	
	/**
	 * Run this Task for one unit of time.
	 */
	public void run()
	{
		_remaining_comp_time--;
	}
	
	/**
	 * Set the value of the Task.
	 * 
	 * @param value
	 *            The new value of the Task.
	 * @return The new value of the Task.
	 */
	public double setValue(double value)
	{
		return _value = value;
	}
	
	/**
	 * Update the Task as a unit of time progresses;
	 * 
	 * @param time
	 *            The current unit of time.
	 * @param details
	 * @return false if the Task misses it's deadline, true otherwise.
	 */
	public boolean update(long time)
	{
		if(_next_deadline <= time)
		{
			if(0 < _remaining_comp_time)
			{
				return false;
			}
		}
		
		if(_next_period <= time)
		{
			_next_deadline = _next_period + _deadline;
			_next_period += _period;
			_remaining_comp_time = _computation_time;
		}
		
		return true;
	}
}
