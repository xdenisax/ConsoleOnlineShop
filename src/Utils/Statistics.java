package Utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;


import classes.Order;
import classes.Product;
import classes.User;

public class Statistics {
	private HashMap<String, Integer> topProducts = new HashMap<String, Integer>();
	private String favouriteProduct;
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
					topProducts.merge(product.getName(), 1, Integer::sum);
				}
			}
			System.out.println();
			Stream<Map.Entry<String, Integer>> sorted =
					topProducts.entrySet().stream()
				       .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));
			java.util.Map.Entry<String, Integer> entry = sorted.findFirst().get();
			this.favouriteProduct = entry.getKey();
			this.numberFavProduct = entry.getValue();
			this.averageNumberOfProducts = totalProducts / user.getOrderHistory().size();
			this.averageOrderPrice = totalValue / user.getOrderHistory().size();
	}

	public String getFavouriteProduct() {
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
