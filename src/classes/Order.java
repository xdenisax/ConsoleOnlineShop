package classes;

import java.util.Date;
import java.util.List;

public class Order {
	private Date date;
	private List<Product> products;
	private float totalPrice;
	
	public Order(Date date, List<Product> products, float totalPrice) {
		super();
		this.date = date;
		this.products = products;
		this.totalPrice = totalPrice;
	}
	
	public Date getDate() {
		return date;
	}
	
	public List<Product> getProducts() {
		return products;
	}
	
	public float getTotalPrice() {
		return totalPrice;
	}
}
