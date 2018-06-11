package dynamicloading;

public class MyCounter implements Counter {
	  private int counter;
	  public String message() {
		    return "------------------";
	  }
	  public int plusPlus() {
		    return counter++;
	  }
	  
}
