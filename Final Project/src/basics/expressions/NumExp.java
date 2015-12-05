package basics.expressions;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class NumExp extends Expression
{
    private static DecimalFormat df = new DecimalFormat("#.###");

    static
    {
	df.setRoundingMode(RoundingMode.CEILING);
    }

    protected double value;

    public NumExp(double value)
    {
	this.value = value;
    }

    @Override
    public Expression clone()
    {
	return new NumExp(value);
    }

    @Override
    public double getProcessedValue()
    {
	return value;
    }

    @Override
    public String print()
    {
	return df.format(value);
    }

    @Override
    public Expression process()
    {
		return this;
	}
}
