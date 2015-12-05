package basics.expressions;

public class PowExp extends Expression
{
    protected Expression base;
    protected Expression exponent;

    public PowExp(Expression base, Expression exponent)
    {
	this.base = base;
	this.exponent = exponent;
    }

    @Override
    public Expression clone()
    {
	return new PowExp(base.clone(), exponent.clone());
    }

    @Override
    public double getProcessedValue()
    {
	return Math.pow(base.getProcessedValue(), exponent.getProcessedValue());
    }

    @Override
    public String print()
    {
	return "(" + base + " ^ " + exponent + ")";
    }

    @Override
    public Expression process()
    {
	if(!base.isProcessed() || !exponent.isProcessed())
	{
	    base = base.process();
	    exponent = exponent.process();
	    return this;
	}
	return new NumExp(getProcessedValue());
    }

}
