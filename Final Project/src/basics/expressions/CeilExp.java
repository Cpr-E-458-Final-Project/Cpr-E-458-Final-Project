package basics.expressions;

public class CeilExp extends Expression
{
	protected Expression body;
	
	public CeilExp(Expression body)
	{
		this.body = body;
	}
	
	@Override
	public Expression clone()
	{
		return new CeilExp(body.clone());
	}
	
	@Override
	public double getProcessedValue()
	{
		return Math.ceil(body.getProcessedValue());
	}
	
	@Override
	public String print()
	{
		return "Ceil(" + body + ")";
	}
	
	@Override
	public Expression process()
	{

		if(!body.isProcessed())
		{
			body = body.process();
			return this;
		}
		
		return new NumExp(getProcessedValue());
	}
	
}
