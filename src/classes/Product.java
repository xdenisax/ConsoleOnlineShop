package classes;

public class Product {
	private String name;
	private String description;
	private float price;
	
	public Product(String name, String description, float price) {
		super();
		this.name = name;
		this.description = description;
		this.price=price;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public float getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return name  + "\n"  + description + "\nPret:  " + price + "lei";
	}
	
	
}
