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
		this.lots = quantity/10;
		
		costMatrix = new int[manager.getStoreCount()][lots];
	}

	public int bottumUpSuffix() {
		if(!manager.canFulfillOrder(quantity)) {
			return -1;
		}
		// we start with 0 suffix cost
		int prevMinSuffixCost = 0;
		for(int rem = lots -1 ; rem >= 0; rem--) {
			// keep track of the cur min suffix cost for the next suffix
			int currMinSuffixCost = -1;
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
					currMinSuffixCost = suffixCost;
				}
				costMatrix[s][rem] = suffixCost;
			}
			prevMinSuffixCost = currMinSuffixCost;
		}
		return prevMinSuffixCost;
	}
	
	public static void main(String[] args) {
		StoreManager manager = new StoreManager();
		
		Order o1 = new Order(manager, manager.getStore(1), 110);
		System.out.println(o1.bottumUpSuffix());
	}
	
}
