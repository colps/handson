package com.example.java8;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.StampedLock;

public class Concurrency {

	AtomicLong largest = new AtomicLong();
	
	//largest.set(Math.max(largest.get(), observed)); // Error�race condition!
	// Instead use compare and set
	public void compareAndSet(long observed) {
		long current, newValue;
		do {
			current = largest.get();
			newValue = Math.max(observed, current);		
		} while (!largest.compareAndSet(current, newValue));
		// If another thread is also updating largest, it is possible that it has beat this thread
		// to it. Then compareAndSet will return false without setting the new value. In that
		// case, the loop tries again, reading the updated value and trying to change it.
		// Eventually, it will succeed replacing the existing value with the new one.

		// For Java 8
		largest.updateAndGet(x -> Math.max(x, observed));
		
		largest.accumulateAndGet(observed, Math::max);
	
	}

	
	// When you have a very large number of threads accessing the same atomic values,
	// performance suffers because the optimistic updates require too many retries.
	// Java 8 provides classes LongAdder and LongAccumulator to solve this problem. A
	// LongAdder is composed of multiple variables whose collective sum is the current
	// value. Multiple threads can update different summands, and new summands
	// are automatically provided when the number of threads increases. This is efficient
	// in the common situation where the value of the sum is not needed until after all
	// work has been done. 
	public void adderandAccumulator() {
		AtomicLong value = new AtomicLong();
		// can be replaced with
		LongAdder adder = new LongAdder();
		
		LongAccumulator accumulatorAsAdder = new LongAccumulator(Long::sum, 0);
		// In some thread . . .
		long test = 1l;
		accumulatorAsAdder.accumulate(test);
	}
	
	
	// You first call tryOptimisticRead, upon 	which you get a �stamp.� Read your 
	// values and check whether the stamp is still valid (i.e., no other thread 
	// has obtained a write lock). If so, you can use the values. If not, get a read 
	// lock (which blocks any writers).
	public void stampedLock() {
		StampedLock lock = new StampedLock();
		long stamp = lock.tryOptimisticRead();
		// read data
		if(!lock.validate(stamp)) {
			// stamp invalid, s data is dirty. get a read lock
			stamp = lock.readLock();
			// read data
			lock.unlockRead(stamp);
		}
		// return data
	}
	
	
	// Suppose you want to keep track of the number of times different words occurs
	// @NotThreadSafe
	// Map<String, Long> map = new ConcurrentHashMap<>();
	// Long oldValue = map.get(word);
	// Long newValue = oldValue == null ? 1 : oldValue + 1;
	// map.put(word, newValue);
	public void concurrentMapEnhanced(String word) {
		// old way
		Map<String, Long> map = new ConcurrentHashMap<>();
		Long oldCount, newCount;
		do {
			oldCount = map.get(word);
			newCount = oldCount == null ? 1 : oldCount + 1;
			} while (!map.replace(word, oldCount, newCount));
		
		// Alternate, use Map<String, AtomicLong> or Map<String, LongAdder>
		Map<String, LongAdder> newMap = new ConcurrentHashMap<>();
		newMap.putIfAbsent(word, new LongAdder());
		newMap.get(word).increment();
		
		// Java 8 provides methods that make atomic updates more convenient. The compute
		// method is called with a key and a function to compute the new value. That
		// function receives the key and the associated value, or null if there is none, and it
		// computes the new value.
		map.compute(word, (k, v) -> v == null ? 1 : v + 1);
	}
	
	
	/**
	 * 
	 * Java 8 provides bulk operations on concurrent hash maps that can safely execute 
	 * even while other threads operate on the map. The bulk operations traverse the 
	 * map and operate on the elements they find as they go along
	 * 3 types of operations for concurrent hash map
	 * search - applies a function to each key/value until it yields a non null result
	 * reduce - combine all entries
	 * forEach - applies a finction to all elements
	 * 
	 * Each op has 4 versions
	 * operationKeys = perform on keys
	 * operationValues - on values
	 * operation - on key and value
	 * operationEntry - on Map.Entry
	 */
	public void bulkOperations() {
		ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();
		//map.reduceEntries(parallelismThreshold, reducer)
		//map.reduceKeys(parallelismThreshold, reducer)
		//map.reduceValues(parallelismThreshold, reducer)
		//map.reduce(parallelismThreshold, transformer, reducer)
		
		//map.search(parallelismThreshold, searchFunction)
		//map.searchKeys(parallelismThreshold, searchFunction)
		//map.searchEntries(parallelismThreshold, searchFunction)
		//map.searchValues(parallelismThreshold, searchFunction)
		
		//map.forEach(action);
		//map.forEachEntry(parallelismThreshold, action);
		//map.forEachKey(parallelismThreshold, action);
		//map.forEachValue(parallelismThreshold, action);
	}	
	
	
	/**
	 * If you need a concurrent hash set, you can use the key set of the hash map 
	 * using the following methods
	 */
	public void concurrentSet() {
		Set<String> words = ConcurrentHashMap.<String>newKeySet();
		ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();
		words = map.keySet(1L);
		words.add("Java");
	}


	/**
	 * Future.get is a blocking call. Theres no way to tell "When the result becomes
	 * available, here is how to process it." This is the crucial feature that the new
	 * CompletableFuture<T> class provides.
	 *
	 * http://www.nurkiewicz.com/2013/05/java-8-definitive-guide-to.html
	 */
	public void completeableFutures() {
		CompletableFuture<String> contents = null; //readPage("");
		CompletableFuture<List<String>> links = contents.thenApply(this::getLinks); 
		// The thenApply method doesn't block either. It returns another future. When the
		// first future has completed, its result is fed to the getLinks method, and the return
		// value of that method becomes the final result.
		
		// creating a CompletableFuture
		contents = CompletableFuture.supplyAsync(() -> blockingReadPage(""));
		
		//Next, you can call thenApply or thenApplyAsync to run another action, either in the
		//same thread or another. With either method, you supply a function and you get
		//a CompletableFuture<U>, where U is the return type of the function
		links = CompletableFuture.supplyAsync(() -> blockingReadPage(""))
			.thenApply(this::getLinks);
		
		// Once you are done, you need to save the results, The thenAccept method takes a 
		// Consumer that is, a function with return type void. Ideally, you would never call
		// get on a future. The last step in the pipeline simply deposits the result where it belongs.
		CompletableFuture<Void> voidLinks = CompletableFuture.supplyAsync(() -> blockingReadPage(""))
				.thenApply(this::getLinks).thenAccept(System.out::println);
		
		// The thenCompose method, instead of taking a function T -> U, takes a function T ->
		// CompletableFuture<U>. 
		
		getURLInput("")
			.thenCompose((url) -> readPage(url.toString()))
			.thenApply(this::getLinks)
			.thenAccept(System.out::println);
		
	}


	
	
	public String blockingReadPage(String url) {
		return null;
	}
	
	public List<String> getLinks(String content) {
		return null;
	}
	
	
	public CompletableFuture<String> readPage(String url) {
		return null;
	}
	
	public CompletableFuture<URL> getURLInput(String prompt) {
		return null;
	}



}
