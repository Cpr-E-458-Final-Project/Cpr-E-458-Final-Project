package basics.expressions;

import java.util.ArrayList;
import java.util.List;

public class AddExp extends Expression
{
	protected List<Expression> exps = new ArrayList<Expression>();
	
	public AddExp(Expression... exps)
	{
		for(Expression expression : exps)
		{
			this.exps.add(expression);
		}
	}
	
	public AddExp(List<Expression> exps)
	{
		this.exps = exps;
	}
	
	@Override
	public Expression clone()
	{
		List<Expression> exps = new ArrayList<Expression>();
		for(Expression expression : this.exps)
		{
			exps.add(expression.clone());
		}
		return new AddExp(exps);
	}
	
	@Override
	public double getProcessedValue()
	{
		double ret = 0.0;
		for(Expression exp : exps)
		{
			ret += exp.getProcessedValue();
		}
		return ret;
	}
	
	@Override
	public String print()
	{
		if(exps.isEmpty())
		{
			return "0";
		}
		if(exps.size() == 1)
		{
			return exps.get(0).print();
		}
		StringBuilder ret = new StringBuilder();
		ret.append("(");
		for(int index = 0; index < exps.size(); index++)
		{
			if(0 < index)
			{
				ret.append(" + ");
			}
			ret.append(exps.get(index));
		}
		ret.append(")");
		
		return ret.toString();
	}
	
	@Override
	public Expression process()
	{
		boolean once_over = false;
		for(Expression exp : exps)
		{
			if(!exp.isProcessed())
			{
				once_over = true;
				break;
			}
		}
		if(once_over)
		{
			for(int index = 0; index < exps.size(); index++)
			{
				exps.set(index, exps.get(index).process());
			}
			return this;
		}
		
		if(1 < exps.size())
		{
			Expression fst = exps.remove(0);
			Expression snd = exps.remove(0);
			exps.add(0, new NumExp(fst.getProcessedValue() + snd.getProcessedValue()));
		}
		return (exps.isEmpty()) ? new NumExp(0.0) : (exps.size() == 1) ? exps.get(0) : this;
	}
	
}
