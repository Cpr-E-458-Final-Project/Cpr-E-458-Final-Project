package schedulability_checkers;

import java.util.List;

import basics.Task;
import schedulers.EDF_Scheduler;

public class EDF_Checker implements SchedulabilityChecker
{
	@SuppressWarnings("unused")
	private boolean details = false;
	
	boolean deadline_less_than(List<Task> list)
	{
		double sum = 0.0;
		
		for(Task task : list)
			sum += ((double) task.getComputationTime()) / ((double) task.getDeadline());
		return sum <= 1.0;
	}
	
	long getProcessorDemand(Task task, long time)
	{
		return(((long) (Math.floor(((double) (time - task.getDeadline())) / (double) task.getPeriod())) + 1) * task.getComputationTime());
	}
	
	long getProcessorDemand(List<Task> list, int time)
	{
		long ret = 0;
		
		for(Task task : list)
			ret += getProcessorDemand(task, time);
			
		return ret;
	}
	
	@Override
	public boolean isSchedulable(List<Task> list)
	{
		return isSchedulable(list, false);
	}
	
	@Override
	public boolean isSchedulable(List<Task> list, boolean details)
	{
//		this.details = details;
		if(new EDF_Scheduler().determineScheduleLength(list) < 0)
		{
			if(details)
				System.out.println("The necessary schedule length is too long to generate a schedule for this list of Tasks; returning false.");
			return false;
		}
		
		if(isSimple(list))
		{
			return period_less_than(list);
		}
		else if(deadline_less_than(list))
		{
			return true;
		}
		return processorDemandTest(list);
	}
	
	boolean isSimple(List<Task> list)
	{
		for(Task task : list)
			if(task.getDeadline() != task.getPeriod()) return false;
			
		return true;
	}
	
	boolean period_less_than(List<Task> list)
	{
		double sum = 0.0;
		
		for(Task task : list)
			sum += ((double) task.getComputationTime()) / ((double) task.getPeriod());
			
		return sum <= 1.0;
	}
	
	static long gcd(long ret, long l)
	{
		while(l > 0)
		{
			long c = l;
			l = ret % l;
			ret = c;
		}
		
		return ret;
	}
	
	static long lcm(long ret, long l)
	{
		return ret * (l / gcd(ret, l));
	}
	
	long determineScheduleLength(List<Task> list)
	{
		long ret = list.get(0).getPeriod();
		
		for(int index = 1; index < list.size(); index++)
		{
			ret = lcm(ret, list.get(index).getPeriod());
		}
		
		return ret;
	}
	
	boolean processorDemandTest(List<Task> list)
	{
		long length = determineScheduleLength(list);
		for(int time = 0; time <= length; time++)
			if(time < getProcessorDemand(list, time)) return false;
		return true;
	}
	
	/**
	 * static { name = "EDF"; scheduler = new EDF_Scheduler(); }
	 * 
	 * @Override public void properOutput(List<Task> list) { boolean
	 *           manual_check = false;
	 * 			
	 *           for(int index = 0; index < list.size(); index++) {
	 *           if(list.get(index).getDeadline() < list.get(index).getPeriod())
	 *           { System.out.println(
	 *           "Be warned, the EDF and LLF schedulability checks do not account for deadlines shorter than the period; manual testing may be required to be certain in those cases..."
	 *           ); manual_check = true; } }
	 * 			
	 *           List<Expression> lhs_sum = new ArrayList<Expression>();
	 *           
	 *           for(Task task : list) { lhs_sum.add(new DivExp(new
	 *           NumExp(task.getComputationTime()), new
	 *           NumExp(task.getPeriod()))); }
	 * 			
	 *           Expression lhs = new AddExp(lhs_sum), rhs = new NumExp(1.0);
	 *           getClass();
	 *           
	 *           while(!lhs.isProcessed() || !rhs.isProcessed()) {
	 *           System.out.println(lhs + " <= " + rhs); lhs = lhs.process();
	 *           rhs = rhs.process(); }
	 * 			
	 *           System.out.println("Final Result: " + lhs + " <= " + rhs);
	 *           
	 *           if(lhs.getProcessedValue() <= rhs.getProcessedValue()) {
	 *           System.out.println(
	 *           "The given List<Task> is schedulable by the " + name +
	 *           " algorithm."); } else { System.out.println(
	 *           "The given List<Task> is not schedulable by the " + name +
	 *           " algorithm."); }
	 * 			
	 *           if(manual_check) { System.out.println(
	 *           "HOWEVER, because there are deadlines shorter than the period in the given List<Task>, manual checking will be required.  Now scheduling using "
	 *           + name + "..."); if(scheduler.schedule(list)) {
	 *           System.out.println(
	 *           "The given List<Task> is indeed schedulable by the " + name +
	 *           " algorithm." ); } else { System.out.println(
	 *           "The given List<Task> is not schedulable by the " + name +
	 *           " algorithm."); } }
	 * 			
	 *           }
	 * 
	 * @Override protected Expression getRightHandSide(List<Task> list) { return
	 *           new NumExp(1.0); }
	 */
}