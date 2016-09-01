package application;

public abstract class DoubleValue {
	
	public double value;
	
	public DoubleValue(){
		value = 0;
	}
	public DoubleValue(double a){
		value = a;
	}
	
	public abstract double getValue();
	public abstract void setValue(double a);
}

abstract class EffectiveAds extends DoubleValue{
	
	public static int percent(int a){
		double value = a / 20.0;
		
		int integer = (int)(value * 100);
		
		return integer;
	}
}
