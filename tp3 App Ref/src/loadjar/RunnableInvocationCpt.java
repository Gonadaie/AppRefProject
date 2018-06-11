package loadjar;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RunnableInvocationCpt implements InvocationHandler {
// unRunnableInvocationCpt compte, pour le runnable qu'il g�re,
// le nbUtisalisations, c'est-�-dire le nombre d'invocation du run() de Runnable

	private Runnable runnable;
	private int nbUtisalisations;
	
	public RunnableInvocationCpt(Runnable r) {
		this.runnable = r;
		this.nbUtisalisations = 0;
	}

	@Override
	public String toString() {
		return String.valueOf(nbUtisalisations);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object obj = method.invoke(runnable, args);
		nbUtisalisations++; //
		return toString();
	}
}
