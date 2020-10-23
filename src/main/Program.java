package main;

import java.util.Scanner;

public class Program {
	private final static String principalMenu[] = {"Iesire", "Magazin", "Cont personal"};
	private final static String personalAccount[] = {"Prenume", "Nume", "Adresa", "Numar de telefon", "Comenzi"};
	
	public static void main(String[] args) {
		for(int i=0; i < principalMenu.length; i++) {
			System.out.println(i + "- " + principalMenu[i]);
		}
	    System.out.println("Introduceti numarul meniului dorit.");
		Scanner scanner = new Scanner(System.in);
	    String selectedOption = scanner.nextLine();
		
		while(!selectedOption.equals("0")) {
			int selectedIndex;
			try {
				selectedIndex = Integer.parseInt(selectedOption);
				if(selectedIndex >= 0 && selectedIndex <= principalMenu.length-1) {
					System.out.println(principalMenu[selectedIndex]);	
				}else {
					throw new Exception();
				}
			} catch (Exception error) {
			    System.out.println("Introduceti o optiune valida.");
			}
			selectedOption = scanner.nextLine();
		}
	}
}
