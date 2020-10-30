package classes;

import java.util.List;

public class Order {
	private String date;
	private List<Product> products;
	private float totalPrice;
	
	public Order(String date, List<Product> products, float totalPrice) {
		super();
		this.date = date;
		this.products = products;
		this.totalPrice = totalPrice;
	}
	
	public String getDate() {
		return date;
	}
	
	public List<Product> getProducts() {
		return products;
	}
	
	public float getTotalPrice() {
		return totalPrice;
	}

	@Override
	public String toString() {
		return "Comanda din " + date + " contine " + products.size() + " produse in valoare de " + totalPrice + " lei.";
	}
	
	
}
