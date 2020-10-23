package classes;

public class Product {
	private String name;
	private String description;
	private int[] prices;

	public Product(String name, String description, int[] prices) {
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
	
	public int[] getPrices() {
		return prices;
	}
}
