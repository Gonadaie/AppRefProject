package dynamicloading;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class CounterFactory {
	  public static  Counter newInstance() 
			  throws InstantiationException, IllegalAccessException, ClassNotFoundException, MalformedURLException {
		  URLClassLoader tmp =
	      new URLClassLoader(new URL[] 
	    		  {new URL("file:///F:/workspace IUT/AppRef3 ClassLoader/")}) {
	        public Class<?> loadClass(String name) throws ClassNotFoundException {
	          if ("dynamicloading.MyCounter".equals(name))
	            return findClass(name);
	          return super.loadClass(name);
	        }
	      };
	 
	    return (Counter)
	      tmp.loadClass("dynamicloading.MyCounter").newInstance();
	  }
}
