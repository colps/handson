package com.example.java8;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Lambdas {

	private String label = "test";


	public void lambdas() {
		// A functional interface (interface with one  method) can always be replaced with a lambda
		// e.g. Instead of a comparator, we can use  lamdas
		Collections.sort(new ArrayList<String>(), (String first, String second) -> {
			return Integer.compare(first.length(), second.length());
		});

		// For a singe statement we may omit the parenthesis {} as well as the return keyword
		Collections.sort(new ArrayList<String>(), (String first, String second) -> Integer.compare(first.length(), second.length()));

		// Multiple statements can be executed inside the {} if an expression is not enough
		Collections.sort(new ArrayList<String>(), (String first, String second) -> {
			if (first.length() < second.length()) return -1;
			else if (first.length() > second.length()) return 1;
			else return 0;
		});

		// If the type of the parameters can be inferred we can remove the type as well
		Comparator<String> comp
				= (first, second) -> Integer.compare(first.length(), second.length());

		// lambda with no parameters
		Runnable r = () -> {
			for (int i = 0; i < 1000; i++) System.out.println();
		};

		// For a single parameter you can even omit the round brackets
		EventHandler<ActionEvent> listener = event ->
				System.out.println("Thanks for clicking!");

	}

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


	public void methodReferencing() {
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


	public void constructorReferencing() {
		// Constructor references are just like method references, except that the name of the method is new
		// The constructor being called depends on the context
		Function<Integer, int[]> f = int[]::new ;
		Supplier<String> s = String::new;
	}
}