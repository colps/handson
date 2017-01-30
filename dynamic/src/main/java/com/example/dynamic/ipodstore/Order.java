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
		int prevMinSuffixCost = 0;
		for(int rem = lots -1 ; rem >= 0; rem--) {
			// keep track of the cur min suffix cost for the next suffix
			int currMinSuffixCost = -1, currMinStoreindex = -1;
			for(int s= costMatrix.length-1; s >= 0; s--) {
				if(manager.isStoreStockEmpty(s)) {
					costMatrix[s][rem] = -1;
					continue;
				}
				int itemPrice = 0;
				if(placedAt.equals(manager.getStore(s))) {
					itemPrice = manager.getLocalPrice(s);
				} else {
					itemPrice = manager.getExportPrice(s);
				}
				int suffixCost = prevMinSuffixCost + itemPrice;
				if(currMinSuffixCost < 0 || currMinSuffixCost > suffixCost) {
					currMinStoreindex = s;
					currMinSuffixCost = suffixCost;
				}
				costMatrix[s][rem] = suffixCost;
			}
			manager.decrementStock(currMinStoreindex);
			prevMinSuffixCost = currMinSuffixCost;
		}
		return prevMinSuffixCost;
	}
	
	public static void main(String[] args) {
		StoreManager manager = new StoreManager();
		
		Order o1 = new Order(manager, manager.getStore(0), 110);
		System.out.println(o1.bottumUpSuffix());
	}
	
}
