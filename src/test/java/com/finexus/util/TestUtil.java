package com.finexus.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestUtil {

	public static Workbook workbook;
    public static XSSFSheet worksheet;
    public static DataFormatter formatter= new DataFormatter();
    public static String file_location = System.getProperty("user.dir")+"/Akeneo_product";
    static String SheetName= "Sheet1";
    public  String Res;
//    Write obj1=new Write();
    public int DataSet=-1;
	
	static Workbook book;
	static Sheet sheet;

	// public static String TESTDATA_SHEET_PATH =
	// "E:\\TestDataFile\\TestCaseData.xlsx";

	public static String TESTDATA_SHEET_PATH = "D:\\mirtalk-ws-eclipse\\EverlastTestAutomationContainer\\src\\test\\java\\com\\finexus\\testDataProvider\\TestCasesData.xlsx";

	public static Object[][] getTestData(String sheetName) {

		FileInputStream file = null;
		try {
			file = new FileInputStream(TESTDATA_SHEET_PATH);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			book = WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sheet = book.getSheet(sheetName);

		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];

		
		System.out.println("Number of rows in sheet are: " + sheet.getLastRowNum());
		System.out.println("Number of colouns in a row are: " + sheet.getRow(0).getLastCellNum());
		
		
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
				data[i][j] = formatter.formatCellValue(sheet.getRow(i + 1).getCell(j));
				System.out.println("data[i][j]: " + data[i][j]);

			}
		}

		return data;
	}

	public static Object[][] readVariant(String sheetName) throws IOException, EncryptedDocumentException, InvalidFormatException {
		FileInputStream fileInputStream = new FileInputStream(TESTDATA_SHEET_PATH); // Excel sheet file location get mentioned
																				// here
		workbook = WorkbookFactory.create(fileInputStream);//new Workbook(fileInputStream); // get my workbook
		worksheet = (XSSFSheet) workbook.getSheet(sheetName);// get my sheet from workbook
		XSSFRow Row = worksheet.getRow(0); // get my Row which start from 0

		int RowNum = worksheet.getPhysicalNumberOfRows();// count my number of Rows
		int ColNum = Row.getLastCellNum(); // get last ColNum

		Object Data[][] = new Object[RowNum - 1][ColNum]; // pass my count data in array

		for (int i = 0; i < RowNum - 1; i++) // Loop work for Rows
		{
			XSSFRow row = worksheet.getRow(i + 1);

			for (int j = 0; j < ColNum; j++) // Loop work for colNum
			{
				if (row == null)
					Data[i][j] = "";
				else {
					XSSFCell cell = row.getCell(j);
					if (cell == null)
						Data[i][j] = ""; // if it get Null value it pass no data
					else {
						String value = formatter.formatCellValue(cell);
						Data[i][j] = value; // This formatter get my all values as string i.e integer, float all type
											// data value
					}
				}
			}
		}

		return Data;
	}

	public static void main(String args[]) {
		
//			readVariant("login");
			getTestData("login");
		
	}
}
