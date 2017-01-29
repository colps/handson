package com.example.java8;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class Lambdas {

	private String label = "test";

	// Lambdas act like method local inner class
	public void variableScopes(String text, int count) {
		String local = "local";
		Runnable r = () -> {
			for (int i = 0; i < count; i++) {
				// can access local and class level variables and method parameters
				System.out.println(text);
				System.out.println(label);
				System.out.println(local);
				// can change class level variables
				label = "aaa";
				// cannot change the value of local variable or method parameter (behaves like
				// final variables) since 
				// local = "bbb";
				// text ="ccc";
				Thread.yield();
			}
		};
		new Thread(r).start();
	}


	public void methodReferencing(){
		// Sometimes, a lambda expression does nothing but call an existing method. 
		// In those cases, it's often clearer to refer to the existing method by name
		// This is method referencing
		
		// This is...
		Consumer<String> consumer = (String text) -> System.out.println(text);
		// same as ... since the type of text can be inferred from Consumer<String> ...
		consumer = text -> System.out.println(text);
		// same as.. This method referencing of type
		// instance::method
		consumer = System.out::println;
		
		
		// This is
		BiFunction<Double, Double, Double> dbiFunction = (a, b) -> Math.pow(a, b);
		// same as.. This method referencing of type
		// classname::method
		dbiFunction = Math::pow;
		
		
		// This is
		BiFunction<String, String, Integer> sbiFunction = (a, b) -> a.compareTo(b);
		// same as.. This method referencing of type
		// classname::method (Here the first parameter becomes the target of the method)
		sbiFunction = String::compareTo;
	}
}
