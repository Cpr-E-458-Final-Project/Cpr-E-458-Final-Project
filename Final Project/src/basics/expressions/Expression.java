package basics.expressions;

public abstract class Expression
{
	@Override
	public abstract Expression clone();
	
	public static String processAll(Expression exp)
	{
		StringBuilder ret = new StringBuilder();
		Expression current = exp.clone();
		ret.append("{\n" + current);
		while(!current.isProcessed())
		{
			current = current.process();
			ret.append("\n" + current);
			
		}
		return ret.append("\n}").toString();
	}
	
	public abstract double getProcessedValue();
	
	public boolean isProcessed()
	{
		return this instanceof NumExp;
	}
	
	public abstract String print();
	
	public abstract Expression process();
	
	@Override
	public String toString()
	{
		return print();
	}
}
