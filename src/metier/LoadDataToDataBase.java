package metier;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LoadDataToDataBase {
	
	static BddAccess access=new BddAccess();
	static Connection connection=access.getConnection();
	
	public static void readXLSXFile(String fileName) throws IOException
	{
		ArrayList<String> listHeader=new ArrayList<String>();
		ArrayList<Integer> listType=new ArrayList<Integer>();
		ArrayList<String> listData=new ArrayList<String>();

		
		InputStream ExcelFileToRead = new FileInputStream(fileName);
		XSSFWorkbook  wb = new XSSFWorkbook(ExcelFileToRead);
		
		XSSFWorkbook test = new XSSFWorkbook(); 
		
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row;
		XSSFRow row2; 
		XSSFCell cell;
		String request="CREATE TABLE IF NOT EXISTS excel(id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,";
		String insert="INSERT INTO excel VALUES (null,";

		String data="";

		Iterator rows = sheet.rowIterator();
		Iterator rows2 = sheet.rowIterator();
		
		//get header Name
		if (rows.hasNext())
		{
			row=(XSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			
			while (cells.hasNext())
			{
				cell=(XSSFCell) cells.next();
				listHeader.add(cell.getStringCellValue());
				
			}
		}
		//get header Type
		int I=0;
		if (rows.hasNext())
		{
			row=(XSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			
			while (cells.hasNext())
			{
				cell=(XSSFCell) cells.next();
				int typ=cell.getCellType();
				
				
				String t="";
				
				if(typ==0)
						t=" Varchar(255)";
				else if (typ==1)
						t=" Varchar(255)";

				data+=listHeader.get(I)+t+",";
					I++;
					
				
				
				
			}
			//System.out.println();
		}
		
		
		//System.out.println("List Header ="+listHeader);
		//System.out.println("List Type ="+listType);
		//System.out.println("List Data ="+listData);
		int len = data.length();
		 data=data.substring(0,len-1);

		data+=")";
		data=request+data;
		 
		//System.out.println("header= "+data);
		
		try {
			PreparedStatement pstatement=connection.prepareStatement(new String(data));
			pstatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//insert all data 
		
		if(rows2.hasNext())
			row2=(XSSFRow) rows2.next();

		
		while (rows2.hasNext())
		{
			data="";
			row2=(XSSFRow) rows2.next();
			Iterator cells = row2.cellIterator();
			while (cells.hasNext())
			{
				cell=(XSSFCell) cells.next();
		
				if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
				{
					data+="\""+cell.getStringCellValue()+"\",";

					
				}
				else if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC)
				{
					data+="\""+cell.getNumericCellValue()+"\",";

				}
				
			}
			//System.out.println();
			
			 len = data.length();
			 data=data.substring(0,len-1);

			
			String req=insert+data+")";
			
			System.out.println("request ="+req);
			
			try {
				PreparedStatement pstatement=connection.prepareStatement(new String(req));
				pstatement.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
	
		}
		
		
	}
	
	
	static void readCsv(String fileName) throws Exception  {
		
		File file=new File(fileName);
		
		Scanner scanner=new Scanner(file);
		String header = null;
		if(scanner.hasNextLine()) {
			// create table
			header=scanner.nextLine();
			
			StringBuffer sb=new StringBuffer();
			sb.append("CREATE TABLE IF NOT EXISTS csv(id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,");
			
			String [] columns=header.split(";");
			//System.out.println(columns.length);
			for(int i=0;i<columns.length-1;i++) {
				sb.append(columns[i]+" varchar(255),");
			}
			sb.append(columns[columns.length-1]+" varchar(255))");
			
			try {
				PreparedStatement pstatement=connection.prepareStatement(new String(sb));
				pstatement.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// insert data file
			
			while(scanner.hasNext()) {
				String row=scanner.nextLine();
				String [] rowData=row.split(";");
				//System.out.println(rowData.length);
				StringBuffer strbf=new StringBuffer();
				strbf.append("insert into csv values(null,");
				for(int i=0;i<rowData.length-1;i++) {
					//System.out.println(rowData[i]);
					strbf.append("\""+rowData[i]+"\", ");
				}
				strbf.append("\""+rowData[rowData.length-1]+"\")");
				
				System.out.println(new String(strbf));
				
				try {
					PreparedStatement pstatement=connection.prepareStatement(new String(strbf));
					pstatement.executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
