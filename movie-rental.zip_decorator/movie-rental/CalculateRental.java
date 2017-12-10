
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import rentalItems.*;
import mainPackage.*;
import utilities.*;

public class CalculateRental {

	public static void main(String[] args) {

		System.out.println("This program will take input from a file that contains:");
		System.out.println("Name of the customer, number of rental items and rental days.");
		System.out.println("It will calculate and write out to an HTML file.");
		System.out.println(
				"Then an integer showing how many items. For each item, there will be three lines showing movie ID, movie title and rental days. For example: 2 means there will be six lines.");
		System.out.println("Any item ID less than 2 is considered new item.");

		Customer cust = new Customer();
		Rental_context r;
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("customer.txt"));
			cust.set_custId(Integer.parseInt(br.readLine()));
			cust.set_firstName(br.readLine());
			cust.set_midName(br.readLine());
			cust.set_lastName(br.readLine());
			cust.set_frequentRenterPoint(Integer.parseInt(br.readLine()));
			cust.set_age(Integer.parseInt(br.readLine()));
			br.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		try {
			br = new BufferedReader(new FileReader("movie.txt"));
			r = new Rental_context(cust);
			int temp = Integer.parseInt(br.readLine());

			for (int i = 0; i < temp; i++) {
				int id = Integer.parseInt(br.readLine());

				String name = br.readLine();
				Integer day = Integer.valueOf(br.readLine());
				/*
				 * Fix this part, use changeStrategy function to change the class
				 * 
				 */
				if (id < 100)
					r.setStrategy(new NewMovieDecorator(id, name));
				else
					r.setStrategy(new MovieDecorator(id, name));

				Pair<RentalItem, Integer> pairMovie = new Pair<RentalItem, Integer>(r.getStrategy(), day);

				r.addRental(pairMovie);
			}

			r.print("htmlFile.html");
			cust.saveCust("customer.txt");
			br.close();

		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
