package basics.expressions;

import java.util.ArrayList;
import java.util.List;

public class SubExp extends Expression
{
	protected Expression first;
	protected List<Expression> exps = new ArrayList<Expression>();
	
	public SubExp(Expression first, List<Expression> exps)
	{
		this.first = first;
		this.exps = exps;
	}
	
	public SubExp(Expression first, Expression... exps)
	{
		
		this.first = first;
		for(int index = 0; index < exps.length; index++)
			this.exps.add(exps[index]);
	}

	@Override
	public Expression process()
	{
		boolean once_over = !first.isProcessed();
		if(!once_over)
			for(Expression exp : exps)
				if(!exp.isProcessed())
				{
					once_over = true;
					break;
				}
		if(once_over)
		{
			first = first.process();
			for(int index = 0; index < exps.size(); index++)
				exps.set(index, exps.get(index).process());
			return this;
		}
		
		if(exps.isEmpty())
			return first;
		else
			first = new NumExp(first.getProcessedValue() - exps.remove(0).getProcessedValue());
		return (exps.isEmpty()) ? first : this;
	}
	
	@Override
	public String print()
	{
		if(this.exps.isEmpty())
			return first.print();
		StringBuilder ret = new StringBuilder();
		ret.append("(" + first);
		for(int index = 0; index < this.exps.size(); index++)
			ret.append(" - " + this.exps.get(index));
		ret.append(")");
		return ret.toString();
	}

	@Override
	public double getProcessedValue()
	{
		double ret = first.getProcessedValue();
		for(Expression exp : this.exps)
			ret -= exp.getProcessedValue();
		return ret;
	}

	@Override
	public Expression clone()
	{
		Expression first = this.first.clone();
		List<Expression> exps = new ArrayList<Expression>();
		for(Expression expression : this.exps)
			exps.add(expression.clone());
		return new SubExp(first, exps);
	}

}
