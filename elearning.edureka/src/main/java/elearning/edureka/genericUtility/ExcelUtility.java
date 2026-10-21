package elearning.edureka.genericUtility;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtility {
	
	public int totalNumOfRows(String sheetName) throws EncryptedDocumentException, IOException
	{
		FileInputStream fis = new FileInputStream("./src/test/resources/TestData.xlsx");
		Workbook wb = WorkbookFactory.create(fis);
		
		int row = wb.getSheet(sheetName).getPhysicalNumberOfRows();
		
		return row;
	}
	
	public int totalNumOfCol(String sheetName) throws EncryptedDocumentException, IOException
	{
		FileInputStream fis = new FileInputStream("./src/test/resources/TestData.xlsx");
		Workbook wb = WorkbookFactory.create(fis);
		
		int col = wb.getSheet(sheetName).getRow(0).getPhysicalNumberOfCells();
		
		return col;
	}
	
	public String getDataFromExcelFile(String sheetName,int row,int cell) throws EncryptedDocumentException, IOException
	{
		FileInputStream fis = new FileInputStream("./src/test/resources/TestData.xlsx");
		Workbook wb = WorkbookFactory.create(fis);
		
		return wb.getSheet(sheetName).getRow(row).getCell(cell).toString();
	}
}
