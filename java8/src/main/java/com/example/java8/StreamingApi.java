package com.example.java8;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamingApi {
	
	class Person {
		private String id;
		private String name;
		private int age;
		
		public Person(String id, String name) {
			this.id = id;
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
		
		public String getId() {
			return id;
		}
		
		public int getAge() {
			return age;
		}
	}
	
	List<String> words = Arrays.asList("Will", "will", "find", "a", "way");
	List<Integer> numbers = Arrays.asList(12, 4, 6, 77, 23, 92, 1, 3);
	List<Person> people = Arrays.asList(new Person("a", "aname"), new Person("b", "bname"));
	List<Locale> locales = Arrays.asList(Locale.CANADA, Locale.CANADA_FRENCH, Locale.UK);
	
	/**
	 * 
	 * Step 1 - Stream generation
	 * Step 2 - Filter/Transfromation
	 * Step 2 - Reduction
	 * 
	 */
	
	
	public void streamCreation(){
		// from list
		Stream<String> wStream = words.stream();
		Stream<String> wParallelStream = words.parallelStream();
		
		// Stream static methods
		Stream<String> song = Stream.of("gently", "down", "the", "stream");
		Stream<String> silence = Stream.empty();
		
		Stream<String> echo = Stream.generate(() -> "Echo");
		// have a look at the method call syntax !!!!
		Stream<Double> randoms = Stream.<Double>generate(Math::random);
		
		Stream<BigInteger> integers
		= Stream.iterate(BigInteger.ZERO, n -> n.add(BigInteger.ONE));
	}
	
	
	public void streamFiltering(){
		Stream<String> wStream = words.stream();
		// All such methods return a Stream
		
		// filter
		Stream<String> longWords = wStream.filter((word) -> word.length() > 12);
		
		// map
		Stream<String> upperCase = wStream.map((word) -> word.toUpperCase());
		
		// flatMap
		Stream<Stream<String>> streamOfStreams = words.stream().map((word) -> {
			// each word will be a stream
			List<String> wList =   Arrays.asList(word);
			return wList.stream();
		});
		Stream<String> flatStream = streamOfStreams.flatMap((stream) -> stream);
	}
	
	public void streamLimitAndSkip() {
		// linit
		Stream<String> song = Stream.of("gently", "down", "the", "stream").limit(3);
		
		// limits an infinite stream
		Stream<String> echo = Stream.generate(() -> "Echo").limit(100);
		
		// skip
		song = Stream.of("gently", "down", "the", "stream").skip(1); 
	}
	
	public void statefulTransformation() {
		// distinct
		Stream<String> uniqueWords = 
				Stream.of("merrily", "merrily", "merrily", "gently").distinct();
		
		// sorting
		Stream<String> wStream = words.stream();
		Stream<String> longestFirst =
				wStream.sorted(Comparator.comparing(String::length).reversed());
	}
	
	
	
	public void simpleReductions() {
		long count = words.stream().count();
		
		//some reductions return Optional which encapsulates null or result
		Optional<String> result = words.stream().max(String::compareToIgnoreCase);
		
		result = words.stream().filter(word -> word.startsWith("Q")).findFirst();
		
		result = words.parallelStream().filter(word -> word.startsWith("Q")).findAny();
	}
	
	
	public void parsingOptional() {
		// get the 
		Optional<Integer> result = numbers.stream().filter(num -> num > 12).findFirst();	
		if(result.isPresent()) {
			Integer value = result.get();
			value += 11;
			
		}
		
		// above can also be done using
		result.ifPresent(val -> { val += 11;} );
	
		// if some default value nneds to be returned
		result.orElse(0);
		
		// if you need any function to return the vaue if optional is emoty
		result.orElseGet(() -> Integer.parseInt("222"));
		
		// if you want an exception to be thrown
		result.orElseThrow(IllegalArgumentException :: new);
	}
	
	public void createOptional() {
		Integer value = 1;
		Optional<Integer> result = value == null ? Optional.<Integer>of(value) : Optional.empty();
		// OR
		result = Optional.ofNullable(value);
	}
	
	public void reduceOperations() {
		// reduce the elements of the list
		// add all numbers
		Optional<Integer> result = numbers.stream().reduce((x, y) -> x + y);
		// map and add
		result = words.stream().map((word) -> word.length()).reduce((x, y) -> x + y);
	}
	
	
	public void collectResults() {
		// collect the elements into another collection
		// Collectors have 3 parts
		// supplier to make new instances of the target collection, 
		// accumulator that adds an element to the target collection,
		// combiner that merges two objects of the target collection into one
		HashSet<String> resultHashSet = words.stream().collect(HashSet::new, HashSet::add, HashSet::addAll);
		
		// Predefined collectors Collectors.toSet, Collectors.toList, Collectors.joining
		Set<String> resultSet = words.stream().collect(Collectors.toSet());
		// concat string
		String result = words.stream().collect(Collectors.joining());
		// csv
		result = words.stream().collect(Collectors.joining(", "));
		
		
		// collect to maps... Provide a key mapper and value mapper
		Map<String, String> nameMap = people.stream().collect(Collectors.toMap(Person::getId, Person::getName));
		// Function.identity returns a function that always returns its input value.
		// In this case, its the person object
		Map<String, Person> personMap = people.stream().collect(Collectors.toMap(Person::getId, Function.identity()));
		// duplicate keys throws exception by default...Overide using
		personMap = people.stream().collect(Collectors.toMap(
				Person::getId, 
				Function.identity(),
				(existingValue, newValue) -> newValue));
		
		// grouping on duplicate keys.. can also be done using grouping
		Map<String, Set<Person>> personGrouping = people.stream().collect(Collectors.toMap(
				Person::getName, 
				// can also use below as key mapper
				// (person) -> Collections.singleton(person),
				Collections::singleton,
				(existingValue, newValue) -> {
					Set<Person> pSet = new HashSet<>(existingValue);
					pSet.addAll(newValue);
					return pSet;
				}));
		
	}
	

	public void groupingAndPartitioningCollectors() {
		
		// grouping by country
		Map<String, List<Locale>> countryToLocales = locales.stream().collect(
				Collectors.groupingBy(Locale::getCountry));

		// personGrouping in the prev method can also be done using
		Map<String, Set<Person>> personGrouping = people.stream().collect(
				Collectors.groupingBy(Person::getId, Collectors.toSet()));
		
		// group by counting
		Map<String, Long> countryToLocalesCount = locales.stream().collect(
				Collectors.groupingBy(Locale::getCountry, Collectors.counting()));
		
		// group by summation
		Map<String, Long> personAgeSumByName = people.stream().collect(
				Collectors.groupingBy(Person::getName, Collectors.summingLong(Person::getAge)));
		
		// max/min in a group
		Map<String, Optional<Person>> personMaxAgeByName = people.stream().collect(
				Collectors.groupingBy(Person::getName, Collectors.maxBy(Comparator.comparing(Person::getAge))));
		
		// use partitioning when there are fewer values
		Map<Boolean, List<Locale>> englishAndOtherLocales = locales.stream().collect(
				Collectors.partitioningBy(l -> l.getLanguage().equals("en")));


	}
	
	public void primitiveStreams() {
		// See more mapTo* for types IntStream, LongStream, and DoubleStream 
		// If you want to store short, char, byte, and boolean, use an IntStream, 
		// and for float, use a DoubleStream
		IntStream lengths = words.stream().mapToInt(String::length);
		
		// primitive to boxed stream
		Stream<Integer> integers = IntStream.range(0, 100).boxed();
	}


	

}
