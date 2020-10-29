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
	

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setOrderHistory(List<Order> orderHistory) {
		this.orderHistory = orderHistory;
	}

	@Override
	public String toString() {
		int ordersNo = 0;
		if(orderHistory!=null) {
			ordersNo = orderHistory.size();
		}
		return "\nPrenume: " + firstName 
				+ "\nNume: " + lastName 
				+ "\nAdresa: " + address 
				+ "\nNumar de telefon: "+ phoneNumber 
				+ "\nComenzi: " + ordersNo;
	}
	
	
}
