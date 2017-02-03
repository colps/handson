package com.example.dynamic.subsetsum;

public class SubsetSum {
	
	/**
	 * For set A= {1,3,6,... n} and any integer S, find if any susbset of A adds
	 * up to S.
	 * 
	 * 
	 * Steps -
	 */
	
	/**
	 * 	I) Try to solve recursively
	 */
	public boolean recursive(int i, int S, int[] set) {
		if(i >= set.length || S < 0) {
			return false;
		} 
		if(S == 0) {
			return true;
		}
		return recursive(i+1, S, set) || recursive(i+1, S - set[i], set);
	}
	
	/**
	 * II) Subproblems - For each suffix of i (see i+1 above), should we include
	 * a[i] in the sum or not.
	 * For DP,
	 * # Number of subproblems = nS 
	 * # time/subproblem = O(1)
	 * # running time = nS * O(1) -> O(nS) -> psuedo polynomial. since 
	 * the input number S is part of the solution. S can grow very fast.
	 * count of something is never a problem
	 *  
	 * III) Guess for both possibilities. Include and exclude
	 * 
	 * IV) A 2 dimensional array of i,S should help in memoizing the suoltions
	 * 
	 * V) Identify dependencies between subproblems. Except for the base cases, every
	 *	  recursive subproblem depends on other subproblems—which ones? Draw a picture of
	 *	  your data structure, pick a generic element, and draw arrows from each of the other
	 *	  elements it depends on, In this case this will be tree
	 * 
	 * VI) Topological order. Here, to compute (i,S) we need to compute, i+1 (suffix) and
	 *     S-a[i], So order will be 
	 *     for S in range(0:S)
	 *       for i in range(n-1:0)
	 *  
	 * VII) Solution
	 *     SS(S,i) ->   {
	 *     					False    if i >= a.length || S < 0
	 * 						True     if  S=0
	 * 						SS(S, i+1) || SS(S-a[i], i+1)
	 * 
	 * 					}
	 * 
	 * 
	 *  You may now draw a table to verify if this works
	 */						
	public static boolean dynamicProgramming(int findSum, int[] set) {
		Boolean[][] ssmemo = new Boolean [findSum+1][set.length];
		if(findSum < 0) {
			return false;
		}
		for(int sum = 0; sum <= findSum; sum++ ) {
			for(int index = set.length-1; index >= 0; index--) {
				int rem = sum - set[index];
				if(rem == 0) {
					ssmemo[sum][index] = true;
					continue;
				}
				if(index+1 >= set.length) {
					ssmemo[sum][index] = false;
					continue;
				}
				if(rem < 0) {
					ssmemo[sum][index] = ssmemo[sum][index+1];
					continue;
				} 
				
				ssmemo[sum][index] = ssmemo[sum][index+1] || ssmemo[rem][index+1];
			}
		}
		return  ssmemo[findSum][0];
	}

	public static void main(String[] args) {
		int[] set = {0, 3, 2, 7, 1};
		int sum = 19;
		System.out.println(dynamicProgramming(sum, set));
	}
}
