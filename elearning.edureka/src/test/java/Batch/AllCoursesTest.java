
package Batch;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import elearning.edureka.genericUtility.BaseTest;
import elearning.edureka.genericUtility.DataProviders;

public class AllCoursesTest extends BaseTest 
{

	@BeforeMethod(groups = {"smoke", "functional", "integration","negative","system"})
    public void openAllCoursesPage() throws EncryptedDocumentException, IOException 
    {
        driver.get(excelUtility.getDataFromExcelFile("Login", 4, 1));
        Reporter.log("Navigated to application successfully",true);
    }
    
    // TC_AC_001 - all course page is loaded or not
    @Test(groups = {"smoke"})
    public void TC_AC_001() 
    {
        Assert.assertTrue(allCoursesPage.isPageHeadingDisplayed(),"All Courses page heading is not displayed.");
        Assert.assertTrue(allCoursesPage.isIntroductoryTextDisplayed(),"Introductory text is not displayed.");
        Reporter.log("TC_AC_001: All Courses page loaded successfully.",true);
    }
    
 // TC_AC_018 - courses text
    @Test(groups = {"system"})
    public void TC_AC_018() 
    {
        List<String> cardTexts = allCoursesPage.getCourseCardTexts();
        Assert.assertFalse(cardTexts.isEmpty(), "No course cards were found.");
        for (String cardText : cardTexts) 
        {
            Assert.assertFalse(cardText.trim().isEmpty(), "A course card contains no readable text.");
        }
        Reporter.log("TC_AC_018: Course card text is present and readable.", true);
    }
    
 // TC_AC_020 - navigated to course
    @Test(groups = {"integration"}, dataProvider = "courseCategories_ac", dataProviderClass = DataProviders.class)
    public void TC_AC_020(String categoryName) 
    {
        allCoursesPage.clickCategory(categoryName);
        List<String> courseTitles = allCoursesPage.getCourseTitles();
        Assert.assertFalse(courseTitles.isEmpty(), "No course is available to open.");     
        String oldUrl = driver.getCurrentUrl();
        allCoursesPage.clickViewCourseDetails(courseTitles.get(0));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(oldUrl)));
        String newUrl = wbu.getCurrentUrl();
        Assert.assertNotEquals(newUrl, oldUrl, "View Course Details did not navigate to another URL. " + courseTitles.get(0));
        Reporter.log("TC_AC_020: Course details navigation successful. URL: " + newUrl, true);
     
    }


}
