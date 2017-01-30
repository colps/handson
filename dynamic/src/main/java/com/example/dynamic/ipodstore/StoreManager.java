package com.example.dynamic.ipodstore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class StoreManager {
	
	private final List<Store> stores;
	
	public StoreManager() {
		stores = new ArrayList<>();
		stores.add(new Store("Argentina", 0, 100, 50, 40));
		stores.add(new Store("Brazil", 1, 100, 100, 40));
		
	}
	
	public Store getStore(int index) {
		return stores.get(index);
	}
	
	public int getLocalPrice(int index) {
		return stores.get(index).getLocalPrice();
	}
	
	public int getExportPrice(int index) {
		return stores.get(index).getExportPrice();
	}
	
	public boolean isStoreStockEmpty(int index){
		return stores.get(index).isStockEmpty();
	}
	
	public void decrementStock(int index){
		stores.get(index).decrementStock();
	}
	
	public boolean canFulfillOrder(int quantity) {
		int totalStock = stores.stream().mapToInt(Store::getStock).sum();
		return quantity <= totalStock;
	}
	
	public List<Store> getAllStores() {
		return stores;
	}
	
	public int getStoreCount() {
		return stores.size();
	}
	
	public class Store {
		
		private final String name;
		private final int index;
		private final AtomicInteger stock;
		private final int localPrice;
		private final int exportPrice;
		
		private Store(String name, int index, int initStock, int localPrice, int shippingCost) {
			this.index = index;
			this.stock = new AtomicInteger(initStock);
			this.localPrice = localPrice;
			this.exportPrice = localPrice + shippingCost;
			this.name = name;
		
		}
		
		public int getIndex() {
			return index;
		}
		
		private int getLocalPrice() {
			return localPrice;
		}
		
		private int getExportPrice() {
			return exportPrice;
		}
		
		private int getStock() {
			return stock.get();
		}
		
		private void decrementStock() {
			stock.addAndGet(-10);
		}
		
		private boolean isStockEmpty() {
			return stock.get() <= 0;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Store other = (Store) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}
		
		
	}
	
	
	

}
