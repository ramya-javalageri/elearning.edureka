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
	

	public Object[][] getDataFromExcelSheet(String sheetName)
	        throws EncryptedDocumentException, IOException {
	
	    int rows = totalNumOfRows(sheetName);
	    int cols = totalNumOfCol(sheetName);
	
	    // Row 0 contains headers, so skip it
	    Object[][] data = new Object[rows - 1][cols];
	
	    for (int i = 1; i < rows; i++) {
	        for (int j = 0; j < cols; j++) {
	            data[i - 1][j] = getDataFromExcelFile(
	                    sheetName, i, j);
	        }
	    }
	
	    return data;
	}

}
