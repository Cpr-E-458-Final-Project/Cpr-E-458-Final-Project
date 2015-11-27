package basics;

import schedulability_checkers.*;

public class Main
{
	public static void main(String[] args)
	{
		Task	a = new Task("T1", 1, 3),
				b = new Task("T2", 2, 6),
				c = new Task("T3", 2, 9);
		c.setDeadline(5);
		TaskList lst = new TaskList();
		lst.add(a);
		lst.add(b);
		lst.add(c);
		LLF_Checker.detailedCheck(lst);
		EDF_Checker.detailedCheck(lst);
		DMS_Checker.detailedCheck(lst);
		RMS_Checker.detailedCheck(lst);
	}
}
