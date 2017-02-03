package com.example.dynamic.ipodstore;

import com.example.dynamic.ipodstore.StoreManager.Store;

public class Order {
	
	private StoreManager manager;
	private final Store placedAt;
	private final int quantity;
	private final int lots;
	private final int[][] costMatrix;
	
	public Order(StoreManager manager, Store placedAt, int quantity) {
		if(quantity % 10 != 0) {
			throw new IllegalStateException();
		}
		this.manager = manager;
		this.placedAt = placedAt;
		this.quantity = quantity;
		// calculate the lots
		this.lots = quantity/10;
		
		costMatrix = new int[manager.getStoreCount()][lots];
	}

	
	/**
	 * 
	 * Sub problems - Using suffix here means we take the first lot and calculate its min value
	 * min cost of the current (nth) lot is given by, min cost of the prev suffix lot (n + 1).
	 * n + 1 because we calculate from rigt to left
	 * 
	 * Guess = we guess the cost for all stores keeping track of the min cost and the index of the
	 * store that gives the min cost for lot n
	 * 
	 * For all Stores = S and all order lots = N where lots = quantity /10
	 * if n = current lot and s = current store 
	 * 
	 * DP[n,s] = min { DP[n+1][s] } for all S
	 * 				+ cost(10,s)
	 *
	 * # number of subproblems = ns
	 * # time/subproblem = O(1)
	 * # Time complexity = O(ns)
	 * 
	 * Topological order
	 * 	 for lot in lots[n:]
	 * 		for store in stores[s:]
	 * 
	 * We keep track of the min value in the prev suffix as well as the store 
	 * which provides this min value
	 * 
	 *			A     B
	 * 		A   50    140
	 * 		B   90    100
	 * 
	 * 		
	 *     Assuming Initial min suffix cost = 0 and order is placed at B
	 * 
	 * 				10		20 		 30    ( <- lots remaining)
	 * 		A	   270		180		 90
	 * 		B      290      190      100  (At each node, we get the min cost from prev column + cost of ordering the lot from the current store.
	 * 									   Assuming 	 So DP[B, 20] = min(DP[s][30]) for all s + cost [1, B] => 90 + 1== = 190 )
	 * 		^
	 * 		|
	 * 	  stores
	 * 
	 *
	 * 
	 * @return
	 */
	public int bottumUpSuffix() {
		if(!manager.canFulfillOrder(quantity)) {
			return -1;
		}
		// we start with 0 suffix cost
		int prevMinTotalLotPrice = 0;
		for(int rem = lots -1 ; rem >= 0; rem--) {
			// keep track of the cur min suffix cost for the next suffix
			int currMinTotalLotPrice = -1, minStoreindex = -1;
			for(int s= costMatrix.length-1; s >= 0; s--) {
				if(manager.isStoreStockEmpty(s)) {
					costMatrix[s][rem] = -1;
					continue;
				}
				int lotPrice = 0;
				if(placedAt.equals(manager.getStore(s))) {
					lotPrice = manager.getLocalPrice(s);
				} else {
					lotPrice = manager.getExportPrice(s);
				}
				int currTotalLotPrice = prevMinTotalLotPrice + lotPrice;
				if(currMinTotalLotPrice < 0 || currMinTotalLotPrice > currTotalLotPrice) {
					minStoreindex = s;
					currMinTotalLotPrice = currTotalLotPrice;
				}
				costMatrix[s][rem] = currTotalLotPrice;
			}
			manager.decrementStock(minStoreindex);
			prevMinTotalLotPrice = currMinTotalLotPrice;
		}
		return prevMinTotalLotPrice;
	}
	
	/**
	 * Sub problems -
	 *  We look at *suffixes of lots* DP[l:]. The Suffix of lots here represents the remaining lots.
	 * 
	 * Guess - We (guess) look at all stores to find the minimum value
	 * 
	 * # number of subproblems = LS
	 * # time/subproblem = O(1)
	 * # Time complexity = O(LS)
	 * 
	 * Recurrence - DP of suffixes -> DP[i:] -> DP of remaining lots
	 * 
	 * Given stores S, and lots L
	 * DP[l,s] = min { DP[l+1] } for all S + Cost(1,s)  
	 * DP of remaining lot 3 equals, DP of remaining lots 4 and cost of buying 1 lot from store s
	 *  
	 * Topological order - 
	 *  for remaining lots from l-1 to 0  
	 *     for stores from S to 0
	 * 
	 * Given cost table -
	 *  		A     B    <- buying at
	 * 		A   50    140
	 * 		B   90    100
	 *    order
	 *      at
	 * 		
	 *     Given L lots that are ordered, assume cost of lot L+1 = 0 since this lot will never be bought
	 * 
	 * 				0		1 		 2    ( <- lots remaining)
	 * 		A	   270		180		 90
	 * 		B      290      190      100  (DP[1,B] = min { DP[2] } for all S + Cost(1,B) ) -> 90 + 100
	 * 		^
	 * 		|
	 * 	  stores
	 * 
	 * 
	 * @return
	 */
	public int bottumUpSuffix1() {
		if(!manager.canFulfillOrder(quantity)) {
			return -1;
		}
		// we start with 0 suffix cost
		int totalMinLotPrice = 0;
		for(int rem = lots -1 ; rem >= 0; rem--) {
			// keep track of the cur min suffix cost for the next suffix
			int minLotPrice = -1, minStoreIndex = -1;
			for(int s= costMatrix.length-1; s >= 0; s--) {
				if(manager.isStoreStockEmpty(s)) {
					costMatrix[s][rem] = -1;
					continue;
				}
				int lotPrice = 0;
				if(placedAt.equals(manager.getStore(s))) {
					lotPrice = manager.getLocalPrice(s);
				} else {
					lotPrice = manager.getExportPrice(s);
				}
				
				costMatrix[s][rem] = totalMinLotPrice + lotPrice; 
				
				if(minLotPrice < 0 || minLotPrice > lotPrice) {
					minLotPrice = lotPrice;
					minStoreIndex = s;
				}
			}
			manager.decrementStock(minStoreIndex);
			totalMinLotPrice = totalMinLotPrice + minLotPrice;
		}
		return totalMinLotPrice;
	}
	
	public static void main(String[] args) {
		StoreManager manager = new StoreManager();
		
		Order o1 = new Order(manager, manager.getStore(1), 120);
		System.out.println(o1.bottumUpSuffix());
		
		manager = new StoreManager();
		o1 = new Order(manager, manager.getStore(1), 120);
		System.out.println(o1.bottumUpSuffix1());
	}
	
}
