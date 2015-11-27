package basics.expressions;

public abstract class Expression
{
	public String toString()
	{
		return print();
	}
	
	public abstract Expression clone();
	
	public abstract Expression process();
	
	public abstract String print();
	
	public boolean isProcessed()
	{
		return this instanceof NumExp;
	}
	
	public abstract double getProcessedValue();
}
