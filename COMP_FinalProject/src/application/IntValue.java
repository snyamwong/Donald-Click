package application;

public abstract class IntValue{
	
	private int value;
	
	public IntValue(int i){
		value = i;
	}
	public IntValue(){
		value = 0;
	}
	
	public abstract void clearValue();
	public abstract int getValue();
	public abstract void clicked();
}

class Ads extends IntValue{
	
	private int value;
	
	public Ads(){
		value = 0;
	}
	
	public Ads(int a){
		value = a;
	}
	public void moreAds(){
		value = 20;
	}
	public void clearValue(){
		value = 0;
	}
	public int getValue(){
		return value;
	}
	public void clicked(){
		value--;
	}
}

class Score extends IntValue{
	private int value;
	
	public Score(){
		value = 0;
	}
	
	public Score(int a){
		value = a;
	}
	public void clearValue(){
		value = 0;
	}
	public int getValue(){
		return value;
	}
	public void clicked(){
		value++;
	}
	
}