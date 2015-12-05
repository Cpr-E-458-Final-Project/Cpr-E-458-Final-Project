package schedulability_checkers;

public class LLF_Checker extends EDF_Checker
{
    /*
    static
    {
	name = "LLF";
	scheduler = new LLF_Scheduler();
    }

    @Override
    public void properOutput(TaskList list)
    {
	boolean manual_check = false;

	for(int index = 0; index < list.size(); index++)
	{
	    if(list.get(index).getDeadline() < list.get(index).getPeriod())
	    {
		System.out.println(
			"Be warned, the EDF and LLF schedulability checks do not account for deadlines shorter than the period; manual testing may be required to be certain in those cases...");
		manual_check = true;
	    }
	}
	System.out.print(printExpressions(list));
	
	if(checkSchedulability(list))
	{
	    System.out.println("The given TaskList is schedulable by the " + name + " algorithm.");
	}
	else
	{
	    System.out.println("The given TaskList is not schedulable by the " + name + " algorithm.");
	}

	if(manual_check)
	{
	    System.out.println(
		    "HOWEVER, because there are deadlines shorter than the period in the given TaskList, manual checking will be required.  Now scheduling using " + name + "...");
	    if(scheduler.schedule(list))
	    {
		System.out.println("The given TaskList is indeed schedulable by the " + name + " algorithm.");
	    }
	    else
	    {
		System.out.println("The given TaskList is not schedulable by the " + name + " algorithm.");
	    }
	}
    }
    */
}