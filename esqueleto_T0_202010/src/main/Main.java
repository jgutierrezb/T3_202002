package main;
import java.io.IOException;

import com.opencsv.exceptions.CsvException;

import controller.Controller;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class Main {
	
	
	// GUARDAR COMO Reto1_202020_sec2_team4
	
	public static void main(String[] args) throws IOException, CsvException 
	{
		Controller controler = new Controller();
		controler.run();
	}
}
