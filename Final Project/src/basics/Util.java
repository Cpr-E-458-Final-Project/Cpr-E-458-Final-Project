package basics;

import java.util.List;

public class Util
{
	public static long gcd(long ret, long b)
	{
		while(b > 0)
		{
			long c = b;
			b = ret % b;
			ret = c;
		}
		
		return ret;
	}
	
	public static long lcm(long ret, long b)
	{
		return ret * (b / gcd(ret, b));
	}
	
	public static long determineScheduleLength(List<Task> list)
	{
		if(list.isEmpty())
		{
			return 0;
		}
		
		long ret = list.get(0).getPeriod();
		
		for(int index = 1; index < list.size(); index++)
		{
			ret = lcm(ret, list.get(index).getPeriod());
		}
		
		return ret;
	}
}
