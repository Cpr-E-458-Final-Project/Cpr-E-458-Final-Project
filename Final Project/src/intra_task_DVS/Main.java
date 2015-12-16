package intra_task_DVS;

/**
 * This Main class performs intra-task DVS on pre-specified TaskNodes.
 *
 * @author Robert Kloster
 * 		
 */
public class Main
{
	public static void main(String[] args)
	{
		TaskNode root;

		/* BEGIN TASKNODE(S) CREATION */

		root = new TaskNode("B1", 20);

		TaskNode head = root;

		TaskNode temp = new TaskNode("B2", 20);

		head.getChildren().add(temp);

		head.getChildren().add(temp = new TaskNode("B3", 10));

		head = temp;

		head.getChildren().add(new TaskNode("B4", 10));

		head.getChildren().add(new TaskNode("B5", 150));

		/* END TASKNODE(S) CREATION */

		/* SET DEADLINE */

		double deadline = 200.0;

		/* DEADLINE SET */

		/* BEGIN POWER ANALYSIS OF "ROOT" TASKNODE */
		double max_best = root.bestCasePower(deadline, false), max_worst = root.worstCasePower(deadline, false),
		        max_average = root.averageCasePower(deadline, false), ideal_best = root.bestCasePower(deadline, true),
		        ideal_worst = root.worstCasePower(deadline, true),
		        ideal_average = root.averageCasePower(deadline, true);

		System.out.println("Power consumption for " + root.getName() + " and all children at maximum frequency:");
		System.out.println("Best Case Scenario: " + max_best);
		System.out.println("Worst Case Scenario: " + max_worst);
		System.out.println("Average Case Scenario: " + max_average);

		System.out.println("\nPower consumption for " + root.getName() + " and all children at ideal frequencies:");
		System.out.println("Best Case Scenario: " + ideal_best);
		System.out.println("Worst Case Scenario: " + ideal_worst);
		System.out.println("Average Case Scenario: " + ideal_average);

		System.out.println("\nPerformance comparisons:");
		System.out.println("Best Case: Ideal frequencies are only " + (100.0 * (ideal_best / max_best)) + "% of maximum frequency.");
		System.out.println("Worst Case: Ideal frequencies are only " + (100.0 * (ideal_worst / max_worst)) + "% of maximum frequency.");
		System.out.println("Average Case: Ideal frequencies are only " + (100.0 * (ideal_average / max_average)) + "% of maximum frequency.");
	}
}
