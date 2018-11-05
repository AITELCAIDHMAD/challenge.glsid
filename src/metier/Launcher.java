package metier;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class Launcher {

	public static void main(String[] args) throws Exception {
		
		graphic graphic=new graphic();
		graphic.main(null);
		
		// load data to database
		System.out.println("launcher");
		LoadDataToDataBase loadDataToDataBase=new LoadDataToDataBase();
		LoadDataToDataBase.readCsv("src/dataSet/input1.csv");
		loadDataToDataBase.readXLSXFile("src/dataSet/EA_result_1351.xlsx");
		
		
	}

}
