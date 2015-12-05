package basics.expressions;

import java.util.ArrayList;
import java.util.List;

public class DivExp extends Expression
{
	protected Expression		first;
	protected List<Expression>	exps	= new ArrayList<Expression>();
	
	public DivExp(Expression first, Expression... exps)
	{
		this.first = first;
		for(Expression exp : exps)
		{
			this.exps.add(exp);
		}
	}
	
	@Override
	public boolean isProcessed()
	{
		if(exps.isEmpty()) return first.isProcessed();
		return false;
	}
	
	public DivExp(Expression first, List<Expression> exps)
	{
		this.first = first;
		this.exps = exps;
	}
	
	@Override
	public Expression clone()
	{
		Expression first = this.first.clone();
		List<Expression> exps = new ArrayList<Expression>();
		for(Expression expression : this.exps)
		{
			exps.add(expression.clone());
		}
		return new DivExp(first, exps);
	}
	
	@Override
	public double getProcessedValue()
	{
		double ret = first.getProcessedValue();
		for(Expression exp : exps)
		{
			ret /= exp.getProcessedValue();
		}
		return ret;
	}
	
	@Override
	public String print()
	{
		if(exps.isEmpty())
		{
			return first.print();
		}
		StringBuilder ret = new StringBuilder();
		ret.append("(" + first);
		for(int index = 0; index < exps.size(); index++)
		{
			ret.append(" / " + exps.get(index));
		}
		ret.append(")");
		return ret.toString();
	}
	
	@Override
	public Expression process()
	{
		if(exps.isEmpty())
			return first.process();
		boolean once_over = !first.isProcessed();
		if(!once_over)
		{
			for(Expression exp : exps)
			{
				if(!exp.isProcessed())
				{
					once_over = true;
					break;
				}
			}
		}
		
		if(once_over)
		{
			first = first.process();
			for(int index = 0; index < exps.size(); index++)
			{
				exps.set(index, exps.get(index).process());
			}
			return this;
		}
		
		if(!exps.isEmpty())
		{
			first = new NumExp(first.getProcessedValue() / exps.remove(0).getProcessedValue());
		}
		return (exps.isEmpty()) ? first : this;
	}
	
}
