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
		ret.getAveragePower(200, 0);
	}
	
}
