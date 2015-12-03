package basics;

public class Task
{
	private int _computation_time = -1;
	private int _remaining_comp_time = -1;
	private int _period = -1;
	private int _deadline = -1;
	private int _next_deadline = -1;
	private int _next_period = -1;
	private int _value = -1;
	private String _name = null;
	
	public Task clone()
	{
		Task ret = new Task(_name, _computation_time, _period);
		ret.setDeadline(_deadline);
		ret.setValue(_value);
		return ret;
	}
	
	public Task(String name, int computation_time, int period)
	{
		_name = name;
		_value = _remaining_comp_time = _computation_time = computation_time;
		_next_deadline = _deadline =  _next_period = _period = period;
		reset();
	}
	
	public int setValue(int value)
	{
		return _value = value;
	}
	
	public int setDeadline(int deadline)
	{
		return _next_deadline = _deadline = deadline;
	}
	
	public int getComputationTime()
	{
		return _computation_time;
	}
	
	public String getName()
	{
		return _name;
	}

	public int getPeriod()
	{
		return _period ;
	}

	public int getDeadline()
	{
		return _deadline;
	}
	
	public boolean isReady()
	{
		return 0 <_remaining_comp_time;
	}

	public void reset()
	{
		_remaining_comp_time = _computation_time;
		_next_deadline = _deadline;
		_next_period = _period;
	}

	public void run()
	{
		_remaining_comp_time--;
	}

	public boolean update(int time)
	{
		if(_next_deadline <= time)
		{
			if(0 < _remaining_comp_time)
			{
				System.out.println("Task " + _name + " has failed to meet its deadline at time " + time + " with " + _remaining_comp_time + " remaining time unit" + ((_remaining_comp_time != 1) ? "s" : "") + " of computational demand left unfulfilled.");
				return true;
			}
		}
		if(_next_period <= time)
		{
			_next_deadline = _next_period + _deadline;
			_next_period += _period;
			_remaining_comp_time = _computation_time;
		}
		return false;
	}

	public int getNextDeadline()
	{
		return _next_deadline;
	}

	public int getRemainingCompTime()
	{
		return _remaining_comp_time;
	}

	public int getValue()
	{
		return _value;
	}
}
