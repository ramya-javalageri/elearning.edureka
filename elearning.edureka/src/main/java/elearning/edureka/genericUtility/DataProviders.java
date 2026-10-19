
package elearning.edureka.genericUtility;

import java.io.IOException;
import org.apache.poi.EncryptedDocumentException;
import org.testng.annotations.DataProvider;

public class DataProviders 
{
    ExcelUtility excel = new ExcelUtility();

    @DataProvider(name = "exactCourseTitles")
    public Object[][] exactCourseTitles() throws EncryptedDocumentException, IOException 
    {
        return excel.getDataFromExcelSheet("ExactCourseTitles");
    }

    @DataProvider(name = "partialKeywords")
    public Object[][] partialKeywords() throws EncryptedDocumentException, IOException 
    {
        return excel.getDataFromExcelSheet("PartialKeywords");
    }

    @DataProvider(name = "caseKeywords")
    public Object[][] caseKeywords() throws EncryptedDocumentException, IOException 
    {
        return excel.getDataFromExcelSheet("CaseKeywords");
    }

    @DataProvider(name = "whitespaceKeywords")
    public Object[][] whitespaceKeywords() throws EncryptedDocumentException, IOException 
    {
        return excel.getDataFromExcelSheet("WhitespaceKeywords");
    }

    @DataProvider(name = "invalidKeywords")
    public Object[][] invalidKeywords() throws EncryptedDocumentException, IOException 
    {
        return excel.getDataFromExcelSheet("InvalidKeywords");
    }

    @DataProvider(name = "longInputs")
    public Object[][] longInputs() throws EncryptedDocumentException, IOException 
    {
        return excel.getDataFromExcelSheet("LongInputs");
    }

    @DataProvider(name = "searchKeywords")
    public Object[][] searchKeywords() throws EncryptedDocumentException, IOException 
    {
        return excel.getDataFromExcelSheet("SearchKeywords");
    }

    @DataProvider(name = "autocompleteKeywords")
    public Object[][] autocompleteKeywords() throws EncryptedDocumentException, IOException 
    {
        return excel.getDataFromExcelSheet("AutocompleteKeywords");
    }

    @DataProvider(name = "popularSearchKeywords")
    public Object[][] popularSearchKeywords() throws EncryptedDocumentException, IOException 
    {
        return excel.getDataFromExcelSheet("PopularSearchKeywords");
    }

    @DataProvider(name = "courseCategories")
    public Object[][] courseCategories() throws EncryptedDocumentException, IOException 
    {
        return excel.getDataFromExcelSheet("CourseCategories");
    }

    @DataProvider(name = "courseSearchKeywords")
    public Object[][] courseSearchKeywords() throws EncryptedDocumentException, IOException 
    {
        return excel.getDataFromExcelSheet("CourseSearchKeywords");
    }

    @DataProvider(name = "rapidSearchKeywords")
    public Object[][] rapidSearchKeywords() throws EncryptedDocumentException, IOException 
    {
        return excel.getDataFromExcelSheet("RapidSearchKeywords");
    }

    @DataProvider(name = "courseCategories_ac")
    public Object[][] courseCategories_ac() throws EncryptedDocumentException, IOException 
    {
        return excel.getDataFromExcelSheet("CourseCategories_AC");
    }

    @DataProvider(name = "categoryPairs")
    public Object[][] categoryPairs() throws EncryptedDocumentException, IOException 
    {
        return excel.getDataFromExcelSheet("CategoryPairs");
    }
    
    @DataProvider(name = "emptyWhitespaceInputs")
    public Object[][] emptyWhitespaceInputs() 
    {
        return new Object[][] { {""}, {" "}, {"     "} };
    }

    @DataProvider(name = "specialCharacterInputs")
    public Object[][] specialCharacterInputs() 
    {
        return new Object[][] {{"!@#$%^&*()"}};
    }
}

    
    
    
    
    



