package com.vzaar.examples.console;

import com.vzaar.Vzaar;

public class Whoami {
	
	public static void main(String args[]) throws Exception{
		Vzaar vzaarApi;
		if (args.length == 2) {
			vzaarApi = new Vzaar(args[0], args[1]);
			String whoAmI = vzaarApi.whoAmI();
			if (whoAmI.length() != 0) {
				System.out.println("WhoAmI - " + whoAmI + "\n");
			} else {
				System.out.println("Error\n");
			}
		} else {
			printPrompt();
		}
	}
	
	public static void printPrompt() {
		System.out.print("Usage:\njava whoami username token\n");
		System.out.print("username  -  Username of the vzaar account\n");
		System.out.print("token -  API application token available at http://vzaar.com/settings/api\n");
	}
}
