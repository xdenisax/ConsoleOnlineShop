package classes;
import java.util.List;

public class User {
	private String firstName;
	private String lastName;
	private String address;
	private String phoneNumber;
	private List<Order> orderHistory;
	
	public User(String firstName, String lastName, String address, String phoneNumber, List<Order> orderHistory) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.orderHistory = orderHistory;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getAddress() {
		return address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public List<Order> getOrderHistory() {
		return orderHistory;
	}
	
	
	
}
