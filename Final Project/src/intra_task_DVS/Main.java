package intra_task_DVS;

public class Main
{
	
	public static void main(String[] args)
	{
		TaskNode head = new TaskNode("B1", 20);
		TaskNode ret = head;
		TaskNode temp = new TaskNode("B2", 20);
		head.add(temp);
		head.add(temp = new TaskNode("B3", 10));
		head = temp;
		head.add(new TaskNode("B4", 10));
		head.add(new TaskNode("B5", 150));

		double normal = ret.getMaxFreqPower(200, 0);
		System.out.println("\n\n");
		double ideal = ret.getIdealFreqPower(200, 0);
		System.out.println("\n\nIdeal frequency power consumption is only %" + ((100.0 * ideal) / normal) + " of the maximum frequency power consumption.");
	}
	
}
