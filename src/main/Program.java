package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

import classes.User;

public class Program {
	private final static String principalMenu[] = {"Iesire", "Magazin", "Cont personal"};
	private final static String personalAccount[] = {"Inapoi" , "Nume", "Adresa", "Numar de telefon", "Comenzi"};
	private final static String buyOptions[] = {"Inapoi","Cumpara"};
	private final static String editOptions[] = {"Inapoi","Editeaza"};
	private static String[] products;
	private static Stack<String[]> menuNesting = new Stack<String[]>();
	private static User user;
	
	public static void main(String[] args) {
		readProducts();
		menuNesting.push(principalMenu);
		Scanner scanner = new Scanner(System.in);
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
				menuNesting.push(getMenuForUserOption(index, menuNesting.lastElement()));
			}
			if(!menuNesting.isEmpty()) {
				showMenuOptions(menuNesting.lastElement());
			}
//			doActionsAccordingToMenuChoice();
		}
		scanner.close();
		System.out.println("Multumim pentru vizita!");
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
		
	}
	
	static int checkIndex(String selectedOption, String[] menu) {
		int selectedIndex = -1;
		int minIndex = 0;
		int maxIndex = menu.length - 1;
		try {
			selectedIndex = Integer.parseInt(selectedOption);
			if(selectedIndex < minIndex && selectedIndex > maxIndex) {
				throw new Exception();
			}
		} catch (Exception error) {
		    System.out.println("Introduceti o optiune valida.");
		}
		return selectedIndex;
	}
	
	static String[] getMenuForUserOption(int index, String[] menu) {
		System.out.println(menu[index].split(" ")[0]);
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
			case "Nume": {
				
			}
			case "Adresa": {
				
			}
			case "Numar": {
				
			}
			case "Comenzi": {
				
			}
			default: {
				return menu;
			}
		}
		
	}
	
	static void doActionsAccordingToMenuChoice(String[] menu){
		showMenuOptions(menu);
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
