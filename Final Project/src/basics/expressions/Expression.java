package basics.expressions;

public abstract class Expression
{
    @Override
    public abstract Expression clone();

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
