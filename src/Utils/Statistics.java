package Utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;


import classes.Order;
import classes.Product;
import classes.User;

public class Statistics {
	private HashMap<Product, Integer> topProducts = new HashMap<Product, Integer>();
	private Product favouriteProduct;
	private int numberFavProduct;
	private float averageOrderPrice;
	private float averageNumberOfProducts;
	
	public void doStatistics(User user) {
			float totalValue = 0;
			float totalProducts = 0;
			for(Order order : user.getOrderHistory()) {
				totalValue += order.getTotalPrice();
				totalProducts += order.getProducts().size();
				for(Product product : order.getProducts()) {
					if(this.topProducts.containsKey(product)) {
						this.topProducts.put(product, this.topProducts.get(product) + 1);
					}else {
						this.topProducts.put(product, 1);
					}
				}
			}
			Stream<Map.Entry<Product, Integer>> sorted =
					topProducts.entrySet().stream()
				       .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));
			java.util.Map.Entry<Product, Integer> entry = sorted.findFirst().get();
			this.favouriteProduct = entry.getKey();
			this.numberFavProduct = entry.getValue();
			this.averageNumberOfProducts = totalProducts / user.getOrderHistory().size();
			this.averageOrderPrice = totalValue / user.getOrderHistory().size();
	}

	public Product getFavouriteProduct() {
		return favouriteProduct;
	}

	public int getNumberFavProduct() {
		return numberFavProduct;
	}

	public float getAverageOrderPrice() {
		return averageOrderPrice;
	}

	public float getAverageNumberOfProducts() {
		return averageNumberOfProducts;
	}

}
