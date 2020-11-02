package main;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import Utils.Statistics;
import Utils.Validations;
import classes.Order;
import classes.Product;
import classes.User;

public class Program {
	private final static String principalMenu[] = {"Iesire", "Magazin", "Cont personal", "Cos", "Statistici"};
	private final static String personalAccount[] = {"Inapoi" , "Prenume", "Nume", "Adresa", "Numar de telefon", "Comenzi"};
	private final static String cartOptions[] = {"Inapoi","Adauga in cos"};
	private final static String buyOptions[] = {"Inapoi", "Elimina", "Cumpara"};
	private final static String editOptions[] = {"Inapoi","Editeaza"};
	private static String[] productsMenu;
	private static ArrayList<Product> productsInfo = new ArrayList<Product>();
	private static Stack<String[]> menuNesting = new Stack<String[]>();
	private static ArrayList<Product> cart;
	private static User user;
	private static int editAtIndex;
	private static int buyAtIndex;
	private static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		readUserData();
		readProducts();
		if(user!=null) {
			menuNesting.push(principalMenu);
			showMenuOptions(menuNesting.lastElement());	
		}
		launchApp();
	}
	
	static void readProducts() {
		File file = new File("products.txt"); 
		BufferedReader br;
		String st; 
		ArrayList<String> prods = new ArrayList<String>();
		try {
			br = new BufferedReader(new FileReader(file));
			while ((st = br.readLine()) != null) {
				String name = "nume indisponibil";
				String description = "descriere indisponibila";
				float price = 0;
				
				if(st.length()>3) {
					prods.add(st);
					name = st;
				}
				st = br.readLine(); 
				if(st.length()>3) {
					description = st;
				}
				st = br.readLine();
				if(Validations.isNumeric(st)) {
					price = Float.parseFloat(st);					
				}
				productsInfo.add(new Product(name, description, price));
			}
		} catch (FileNotFoundException e1) {
			System.out.println("Eroare la incarcarea produselor.");
		} catch (IOException e) {
			System.out.println("Eroare la incarcarea produselor.");
		} 
		
		productsMenu = new String[prods.size()+1];
		productsMenu[0]="Inapoi";
		int i=1;
		for(String prod : prods) {
			productsMenu[i] = prod;
			i++;
		}
	}
	
	static void readUserData() {
		String fileName = "user.txt";
	   
		try (	FileInputStream fileReader = new FileInputStream(fileName);
				DataInputStream dis = new DataInputStream(fileReader)) {
		      if(dis.available()>0) {
					String firstName =  dis.readUTF();
					String lastName = dis.readUTF();
					String address = dis.readUTF();
					String phoneNumber = dis.readUTF();
					List<Order> orderHistory = new ArrayList<Order>();
					
					while(dis.available()>0) {	
						String date = dis.readUTF(); 
						float totalPrice = dis.readFloat();
						
						List<Product> products = new ArrayList<Product>();
						String line;
						while(dis.available()>0 && !(line = dis.readUTF()).equals("|")) {
							String name = line;
							String description = dis.readUTF();
							float price = dis.readFloat();		
							
							if(name.length() < 3 ) {
								name = "nume indisponibil";
							}
							if(description.length() < 5) { 
								description = "descriere indisponibila";
							}
							if(!Validations.isValidPrice(price)) {
								price = 0;
							}
							Product product = new Product(name, description, price);
							products.add(product);
						}
						
						if(!Validations.isValidDate(date)) {
							date = "data indisponibila";
						}
						if(!Validations.isValidPrice(totalPrice)) {
							totalPrice = 0;
						}
						Order order = new Order(date, products, totalPrice);
						orderHistory.add(order);
					}
					
					if(!Validations.isNameValid(firstName)) {
						firstName = "prenume indisponibil";
					}
					if(!Validations.isNameValid(lastName)) {
						firstName = "nume indisponibil";
					}
					if(!Validations.isAddressValid(address)) {
						address = "adresa indisponibila";
					}
					if(!Validations.isNumberValid(phoneNumber)) {
						phoneNumber = "nr. indisponibil";
					}
					user = new User(firstName, lastName, address, phoneNumber, orderHistory);
		      } 
		} catch (IOException e) {
			System.out.println("Eroare la preluarea datelor utilizatorului din fisier.");
		}    
	}
	
	static void launchApp() {
		if(user == null) {
			configureNewUser();
			storeUserInFile();
			menuNesting.push(principalMenu);
			showMenuOptions(menuNesting.lastElement());	
        	launchApp();
		}else {
			cart = new ArrayList<Product>();
			while(!menuNesting.isEmpty()) {
			    String selectedOption;
			    int index = -1;
			    while( index < 0) {
				    selectedOption = scanner.next();
				    index = checkIndex(selectedOption, menuNesting.lastElement());	
			    }
				if(	index == 0 ) {
					menuNesting.pop();
				}else {
					String[] currentMenu = getMenuForUserOption(index, menuNesting.lastElement());
					if(currentMenu.length>0) {
						menuNesting.push(currentMenu);
					}
				}
				if(!menuNesting.isEmpty()) {
					doActionsAccordingToMenuChoice();
				}
			}
//			scanner.close();
			updatePersistentData();
			System.out.println("Multumim pentru vizita!");
		}
	}
	
	static void configureNewUser() {
		String firstName = "";
		String lastName = "";
		String address = "";
		String phoneNumber = "";
		System.out.println("Nu sunteti un utilizator al aplicatiei. Pentru a continua catre magazin, va rugam sa va configurati detaliile personale.");
		Scanner scanner = new Scanner(System.in);
		
		for(int i=1; i<=4; i++) {
			System.out.println(personalAccount[i]+": ");
			String input = scanner.nextLine();
			switch(personalAccount[i].split(" ")[0]) {
				case "Prenume": {
					while(!Validations.isNameValid(input)) {
						input = scanner.nextLine();
					}
					firstName = input;
					break;
				}
				case "Nume": {
					while(!Validations.isNameValid(input)) {
						input = scanner.nextLine();
					}
					lastName = input;
					break;					
				}
				case "Adresa": {
					while(!Validations.isAddressValid(input)) {
						input = scanner.nextLine();
					}
					address = input;
					break;					
				}
				case "Numar": {
					while(!Validations.isNumberValid(input)) {
						input = scanner.nextLine();
					}
					phoneNumber = input;
					break;					
				}
			}
		}
//		scanner.close();
		user = new User(firstName, lastName, address, phoneNumber, new ArrayList<Order>());
		System.out.println("Contul dumneavoastra a fost creat:" + user.toString());
	}
	
	static void storeUserInFile() {
        try (
        		FileOutputStream fileWriter = new FileOutputStream("user.txt");
        		DataOutputStream dos = new DataOutputStream(fileWriter)){
        	dos.writeUTF(user.getFirstName());
        	dos.writeUTF(user.getLastName());
        	dos.writeUTF(user.getAddress());
        	dos.writeUTF(user.getPhoneNumber());
        	
        	if(user.getOrderHistory()!=null) {
            	for(Order order : user.getOrderHistory()) {
            		dos.writeUTF(order.getDate());
            		dos.writeFloat(order.getTotalPrice());
            		for(Product product : order.getProducts()) {
                		dos.writeUTF(product.getName());
                		dos.writeUTF(product.getDescription());
                    	dos.writeFloat(product.getPrice());
            		}
            		dos.writeUTF("|");
            	}   		
        	}
        } catch (IOException e1) {
			System.out.println("Eroare la scrierea in fisier a datelor utilizatorului.");
		}
	}
	
	static int checkIndex(String selectedOption, String[] menu) {
		int selectedIndex = -1;
		int minIndex = 0;
		int maxIndex = menu.length - 1;
		try {
			selectedIndex = Integer.parseInt(selectedOption);
			if(selectedIndex < minIndex || selectedIndex > maxIndex) {
				selectedIndex=-1;
				throw new Exception();
			}
		} catch (Exception error) {
		    System.out.println("Introduceti o optiune valida.");
		}
		return selectedIndex;
	}
	
	static int checkIndexForList(String selectedOption, ArrayList<Product> menu) {
		int selectedIndex = -1;
		int minIndex = 0;
		int maxIndex = menu.size() - 1;
		try {
			selectedIndex = Integer.parseInt(selectedOption);
			if(selectedIndex < minIndex || selectedIndex > maxIndex) {
				selectedIndex=-1;
				throw new Exception();
			}
		} catch (Exception error) {
		    System.out.println("Introduceti o optiune valida.");
		}
		return selectedIndex;
	}
	
	static String[] getMenuForUserOption(int index, String[] menu) {
		switch(menu[index].split(" ")[0]) {
			case "Magazin": {
				return productsMenu;
			}
			case "Cont": {
				return personalAccount;
			}
			case "Cos": {
				System.out.println("Aveti "+ cart.size() + " produse in cos.\n");
				float total = showCart();
				System.out.println("\nTotal: "+ total + "lei\n");
				System.out.println("\nDatele dumneavoastra pentru livrare sunt: \n" 
									+ "Telefon: "+ user.getPhoneNumber() + "\n"
									+ "Adresa: "+ user.getAddress() +"\n");
				
				return buyOptions;
			}
			case "Statistici": {
				showStatistics();
				return new String[0];				
			}
			case "Ecler":{
				System.out.println(productsInfo.get(index-1).toString());
				buyAtIndex = index-1;
				return cartOptions;
			}
			case "Cheesecake": {
				System.out.println(productsInfo.get(index-1).toString());
				buyAtIndex = index-1;
				return cartOptions;
			}
			case "Prenume": {
				System.out.println("__________________________________\n"
								+ "Prenume: " +user.getFirstName());
				editAtIndex = index;
				return editOptions;	
			}
			case "Nume": {
				System.out.println("__________________________________\n"
								+ "Nume: " + user.getLastName());
				editAtIndex = index;
				return editOptions;				
			}
			case "Adresa": {
				System.out.println("__________________________________\n" 
									+ "Adresa curenta este: " + user.getAddress());
				editAtIndex = index;
				return editOptions;				
			}
			case "Numar": {
				System.out.println("__________________________________\n" 
								+"Numarul de telefon actual este: " + user.getPhoneNumber());
				editAtIndex = index;
				return editOptions;				
			}
			case "Comenzi": {
				if(user.getOrderHistory() != null && user.getOrderHistory().size() > 0 ) {
					for(Order order : user.getOrderHistory()) {
						System.out.println(order.toString());
					}
				}else {
					System.out.println("0 comenzi plasate.");
				}
				return new String[0];
			}
			case "Editeaza": {
				menuNesting.pop();
				edit( menuNesting.lastElement()[editAtIndex]);
				return new String[0];
			}
			case "Adauga": {
				menuNesting.pop();
				addToCart(productsInfo.get(buyAtIndex));
				return new String[0];
			}
			case "Cumpara": {
				menuNesting.pop();
				if(cart.size() > 0) {
					buy();
				}else {
					System.out.println("Nu aveti nimic in cos.");
				}
				return new String[0];
			}
			case "Elimina": {
				removeFromCart();
				return buyOptions;
			}
			
			default: {
				return menu;
			}
		}
		
	}
	 
	static void edit(String field) {
		System.out.println("Actualizati " + field.toLowerCase() + ":");
		scanner.nextLine();
		String input = scanner.nextLine();

		switch(field.split(" ")[0]) {
			case "Prenume": {
				while(!Validations.isNameValid(input)) {
					input = scanner.nextLine();
				}
				user.setFirstName(input);
				break;
			}
			case "Nume": {
				while(!Validations.isNameValid(input)) {
					input = scanner.nextLine();
				}
				user.setLastName(input);
				break;					
			}
			case "Adresa": {
				while(!Validations.isAddressValid(input)) {
					input = scanner.nextLine();
				}
				user.setAddress(input);
				break;					
			}
			case "Numar": {
				while(!Validations.isNumberValid(input)) {
					input = scanner.nextLine();
				}
				user.setPhoneNumber(input);
				break;					
			}
		}
	}
	
	static void addToCart(Product productWanted) {
		cart.add(productWanted);
		System.out.println("Produs adaugat in cos!");
	}
	
	static void removeFromCart() {
		Scanner scanner = new Scanner(System.in);
		String selectedOption;
		System.out.println("Introduceti numarul produsului pe care doriti sa-l eliminati: ");
		int index = -1;
		while( index < 0) {
			selectedOption = scanner.next();
			index = checkIndexForList(selectedOption, cart);	
		}
		System.out.println("\n============================================\nAti eliminat: \n\n" + cart.get(index).toString());
		cart.remove(index);
		
		if(cart.size()>0) {
			System.out.println("\n============================================\nAcum, cosul dumneavoastra este compus din: \n");	
		} else {
			System.out.println("\n============================================\nAcum, cosul dumneavostra este gol.");
		}
		showCart();
	}
	
	static void buy() {
		float total = 0;
		for(Product product : cart ) {
			total += product.getPrice();
		}
		Order order = new Order(java.time.LocalDate.now().toString(), cart, total );
		user.getOrderHistory().add(order);
		cart = new ArrayList<Product>();
		System.out.println("Comanda plasata!\n" + order.toString());
	}
	
	static void showStatistics() {
		if(user.getOrderHistory().size() > 0) {	
			Statistics stats = new Statistics();
			stats.doStatistics(user);
			System.out.println(
					"Numarul mediu de produse/comanda: " + stats.getAverageNumberOfProducts() 
					+ "\nPretul mediu/comanda: " + stats.getAverageOrderPrice() + " lei"
					+"\nProdusul preferat: " + stats.getFavouriteProduct() 
					+ " cumparat de " +  stats.getNumberFavProduct() + " ori");
			storeStatisticsInFile(stats);
		}else {
			System.out.println("Nu exista comenzi care pot fi analizate.");
		}
	}
	
	static void storeStatisticsInFile(Statistics stats) {
		try {
		      FileWriter myWriter = new FileWriter("stats.txt");
		      myWriter.write(stats.getAverageNumberOfProducts()+"\n");
		      myWriter.write(stats.getAverageOrderPrice()+"\n");
		      myWriter.write(stats.getFavouriteProduct()+"\n");
		      myWriter.write(stats.getNumberFavProduct()+"\n");
		      myWriter.close();
		    } catch (IOException e) {
		      System.out.println("Eroare la stocarea statisticilor in fisier.");
		      e.printStackTrace();
		    }
	}
	
	static void doActionsAccordingToMenuChoice(){
		System.out.println("__________________________________");
		showMenuOptions(menuNesting.lastElement());
		System.out.println("__________________________________");
	}

	static void updatePersistentData() {
		storeUserInFile();
		
	}
	
	static void showMenuOptions(String[] menu) {
		for(int i=0; i < menu.length; i++) {
			System.out.println(i + "- " + menu[i]);
		}
	    System.out.println("Introduceti numarul meniului dorit.");
	}

	static float showCart() {
		float total = 0;
		for( int i=0; i<cart.size(); i++) {
			System.out.println(i +": " +cart.get(i).toString()+ "\n");
			total+=cart.get(i).getPrice();
		}
		return total;
	}
}
