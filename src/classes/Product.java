package classes;

public class Product {
	private String name;
	private String description;
	private float[] prices;

	public Product(String name, String description, float[] prices) {
		super();
		this.name = name;
		this.description = description;
		this.prices = prices;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public float[] getPrices() {
		return prices;
	}
}
