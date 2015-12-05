package schedulability_checkers;

import java.util.List;

import basics.Task;

public interface SchedulabilityChecker
{
	public boolean isSchedulable(List<Task> list, boolean details);
	public boolean isSchedulable(List<Task> list);
    
	/*
    protected static Scheduler scheduler;
    
    protected static String name;
    
    protected abstract boolean needsConfirmation(TaskList list);
    
    protected String printExpressions(TaskList list)
    {
	StringBuilder ret = new StringBuilder();
	Expression lhs = getLeftHandSide(list), rhs = getRightHandSide(list);
	
	while(!lhs.isProcessed() || !rhs.isProcessed())
	{
	    ret.append(lhs + " <= " + rhs + "\n");
	    lhs = lhs.process();
	    rhs = rhs.process();
	}
	
	ret.append("Final Result: " + lhs + " <= " + rhs + "\n");
	return ret.toString();
    }
    
    protected Expression getRightHandSide(TaskList list)
    {
	return new MultExp(
		new NumExp(list.size()),
		new SubExp(
			new PowExp(
				new NumExp(2.0), 
				new DivExp(
					new NumExp(1.0), 
					new NumExp(list.size())
				)
			), 
			new NumExp(1.0)
		)
	);
    }

    protected Expression getLeftHandSide(TaskList list)
    {
	List<Expression> lhs_sum = new ArrayList<Expression>();
	
	for(Task task : list)
	{
	    lhs_sum.add(new DivExp(new NumExp(task.getComputationTime()), new NumExp(getMeasuredValue(task))));
	}
	
	return new AddExp(lhs_sum);
    }

    protected int getMeasuredValue(Task task)
    {
	return task.getPeriod();
    }

    public boolean checkSchedulability(TaskList list)
    {
	return getLeftHandSide(list).getProcessedValue() <= getRightHandSide(list).getProcessedValue();
    }
    
    
    protected boolean checkWarnings(TaskList list)
    {
	for(Task task : list)
	    if(!task.isReasonable())
		return true;
	return false;
    }
    
    public void properOutput(TaskList list)
    {
	System.out.print(printExpressions(list));
	
	if(checkWarnings(list))
	{
	    return;
	}
	
	if(checkSchedulability(list))
	{
	    System.out.println("The " + name + " algorithm schedulibility check has passed.");
	}
	else
	{
	    System.out.println("The " + name + " algorithm schedulibility check has failed.");
	}
	
	if(needsConfirmation(list))
	{
	    if(scheduler.schedule(list))
	    {
		System.out.println("The Tasks are schedulable by the " + name + " algorithm.");
	    }
	    else
	    {
		System.out.println("The Tasks are not schedulable by the " + name + " algorithm.");
	    }
	}
    }*/
}
