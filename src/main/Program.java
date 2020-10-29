package main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import Utils.Validations;
import classes.Order;
import classes.Product;
import classes.User;

public class Program {
	private final static String principalMenu[] = {"Iesire", "Magazin", "Cont personal"};
	private final static String personalAccount[] = {"Inapoi" , "Prenume", "Nume", "Adresa", "Numar de telefon", "Comenzi"};
	private final static String buyOptions[] = {"Inapoi","Cumpara"};
	private final static String editOptions[] = {"Inapoi","Editeaza"};
	private static String[] products;
	private static Stack<String[]> menuNesting = new Stack<String[]>();
	private static User user;
	private static int editAtIndex;
	private static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		readUserData();
		readProducts();
		launchApp();
	}
	
	static void readProducts() {
		File file = new File("E:\\MASTER\\PPOO\\ConsoleOnlineShop\\src\\products.txt"); 
		BufferedReader br;
		String st; 
		ArrayList<String> prods = new ArrayList<String>();
		try {
			br = new BufferedReader(new FileReader(file));
			while ((st = br.readLine()) != null) {
				prods.add(st);
			}
		} catch (FileNotFoundException e1) {
			System.out.println("Eroare la incarcarea produselor.");
		} catch (IOException e) {
			System.out.println("Eroare la incarcarea produselor.");
		} 
		products = new String[prods.size()+1];
		products[0]="Inapoi";
		int i=1;
		for(String prod : prods) {
			products[i] = prod;
			i++;
		}
	}
	
	static void readUserData() {
		String fileName = "E:\\MASTER\\PPOO\\ConsoleOnlineShop\\src\\user.txt";
	   
		try (	FileInputStream fileReader = new FileInputStream(fileName);
				DataInputStream dis = new DataInputStream(fileReader)) {
		      String line;
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
						
						while((line = dis.readUTF()) != null) {
							String name = line;
							String description = dis.readUTF();
							String[] pricesStrings = dis.readUTF().split(" ");
							float[] prices = new float[pricesStrings.length];
							for(int i=0; i<pricesStrings.length; i++) {
								prices[i] = Float.parseFloat(pricesStrings[i]);
							}
							Product product = new Product(name, description, prices);
							products.add(product);
						}
						Order order = new Order(date, products, totalPrice);
						orderHistory.add(order);
					}
					user = new User(firstName, lastName, address, phoneNumber, orderHistory);
		      } 
		} catch (IOException e) {
			System.out.println("Eroare la preluarea datelor utilizatorului din fisier.");
			e.printStackTrace();
		}    
	}
	
	static void launchApp() {
		if(user == null) {
			configureNewUser();
			storeUserInFile();
		}else {
			menuNesting.push(principalMenu);
			showMenuOptions(menuNesting.lastElement());
			while(!menuNesting.isEmpty()) {
			    String selectedOption;
			    int index = -1;
			    while( index < 0) {
				    selectedOption = scanner.nextLine();
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
			scanner.close();
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
		scanner.close();
		user = new User(firstName, lastName, address, phoneNumber, null);
		System.out.println("Contul dumneavoastra a fost creat:" + user.toString());
	}
	
	static void storeUserInFile() {
        try (
        		FileOutputStream fileWriter = new FileOutputStream("E:\\MASTER\\PPOO\\ConsoleOnlineShop\\src\\user.txt");
        		DataOutputStream dos = new DataOutputStream(fileWriter)){
        	dos.writeUTF(user.getFirstName()+"\n");
        	dos.writeUTF(user.getLastName()+"\n");
        	dos.writeUTF(user.getAddress()+"\n");
        	dos.writeUTF(user.getPhoneNumber()+"\n");
        	
        	for(Order order : user.getOrderHistory()) {
        		dos.writeUTF(order.getDate()+"\n");
        		dos.writeFloat(order.getTotalPrice());
        		for(Product product : order.getProducts()) {
            		dos.writeUTF("\n"+product.getName()+"\n");
            		dos.writeUTF(product.getDescription()+"\n");
            		for(int i=0; i<product.getPrices().length; i++) {
                		dos.writeFloat(product.getPrices()[i]);
            		}
            		
        		}
        	}
        	launchApp();
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
				return products;
			}
			case "Cont": {
				return personalAccount;
			}
			case "Ecler":{
				return buyOptions;
			}
			case "Cheesecake": {
				return buyOptions;
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
				
			}
			case "Editeaza": {
				menuNesting.pop();
				edit( menuNesting.lastElement()[editAtIndex]);
				return new String[0];
			}
			default: {
				return menu;
			}
		}
		
	}
	
	static void edit(String field) {
		System.out.println("Actualizati " + field.toLowerCase() + ":");
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
	
	static void doActionsAccordingToMenuChoice(){
		System.out.println("__________________________________");
		showMenuOptions(menuNesting.lastElement());
		System.out.println("__________________________________");
	}
	
//	static void handlePersonalAccountChoice(Scanner scanner) {
//		showMenuOptions(personalAccount);
//	    String selectedOption = scanner.nextLine();
//		int selectedIndex = checkIndex(0, personalAccount.length, selectedOption);
//		
//		switch(selectedIndex) {
//			case -1: 
//				showMenuOptions(personalAccount);
//				break;
//			case  0: 
//				System.out.println(personalAccount[0]);
//				break;
//			case 1:
//				System.out.println("Magazin");
//				break;
//			case 2:
//				handlePersonalAccountChoice(scanner);
//				break;
//		}
//		
		
		
//	}
	
	static void showMenuOptions(String[] menu) {
		for(int i=0; i < menu.length; i++) {
			System.out.println(i + "- " + menu[i]);
		}
	    System.out.println("Introduceti numarul meniului dorit.");
	}
	
	
	
//	boolean isAppOpen = true;
//	Scanner scanner = new Scanner(System.in);
//    String selectedOption;
//    
//	while(isAppOpen) {
//		showMenuOptions(principalMenu);
//		selectedOption = scanner.nextLine();
//		int selectedIndex = checkIndex(0, principalMenu.length, selectedOption);
//		
//		switch(selectedIndex) {
//			case -1: 
//				showMenuOptions(principalMenu);
//				break;
//			case  0: 
//				isAppOpen = false;
//				System.out.println("Aplicatie inchisa..");
//				break;
//			case 1:
//				System.out.println("Magazin");
//				break;
//			case 2:
//				handlePersonalAccountChoice(scanner);
//				break;
//		}
//		
//	}
//	scanner.close();
}
