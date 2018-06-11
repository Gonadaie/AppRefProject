package dynamicloading;

public class Main {
		 
		  public static void main(String[] args) throws Exception  {
			  
			Counter counter1,counter2;
		    counter1 = CounterFactory.newInstance();
		 
		    while (true) {
		      counter2 = CounterFactory.newInstance();
		 
		      System.out.println("Counter 1) " + counter1.message() + " = " + counter1.plusPlus());
		      System.out.println("Counter 2) " + counter2.message() + " = " + counter2.plusPlus()+"\n");
		 
		      Thread.sleep(2000);
		    }
		  }
}
