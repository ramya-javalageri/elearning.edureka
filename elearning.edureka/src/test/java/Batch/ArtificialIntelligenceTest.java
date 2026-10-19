package Batch;

import java.io.IOException;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import elearning.edureka.genericUtility.BaseTest;

public class ArtificialIntelligenceTest extends BaseTest 
{

    @BeforeMethod(alwaysRun = true)
    public void openAICategoryPage() throws EncryptedDocumentException, IOException 
    {
        driver.get(excelUtility.getDataFromExcelFile("AI", 0, 1));
        Reporter.log("AI category page opened successfully", true);
    }

    //TC_AI_01
    @Test(groups = {"smoke", "functional"})
    public void TC_AI_01_VerifyAICategoryPageOpens() throws EncryptedDocumentException, IOException 
    {
        String expectedHeading = excelUtility.getDataFromExcelFile("AI", 1, 1);
        String actualHeading = aiPage.getAIHeading();
        Assert.assertTrue(actualHeading.contains(expectedHeading), "Incorrect category heading. Actual: " + actualHeading);
        Reporter.log("TC_AI_01: Test passed. Heading: " + actualHeading, true);
    }

    //TC_AI_03
    @Test(groups = {"functional"})
    public void TC_AI_03_VerifySelectedCourseOpensDetails() throws EncryptedDocumentException, IOException 
    {
        String categoryUrl = excelUtility.getDataFromExcelFile("AI", 2, 1);
        String selectedCourse = aiPage.getFirstCourseTitle();
        Assert.assertNotNull(selectedCourse, "Course title should not be null.");
        Assert.assertFalse(selectedCourse.trim().isEmpty(), "Course title should not be blank.");
        String currentWindow = driver.getWindowHandle();
        aiPage.clickFirstViewProgram();
        wbu.switchToNewWindowIfOpened(currentWindow);
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(d -> !d.getCurrentUrl().contains(categoryUrl));
        Assert.assertFalse(driver.getTitle().trim().isEmpty(), "Course detail page title is blank.");
        Reporter.log("TC_AI_03: Selected course: " + selectedCourse, true);
        Reporter.log("TC_AI_03: Course details opened: " + wbu.getCurrentUrl(), true);

       if (driver.getWindowHandles().size() > 1) 
       {
           driver.close();
       }
       driver.switchTo().window(currentWindow);

    }

    //TC_AI_012
    @Test(groups = {"system"})
    public void TC_AI_12_VerifyAICategoryReloadsCorrectly() 
    {
        wbu.refresh(driver);
        Assert.assertTrue(aiPage.isAIHeadingDisplayed(), "AI category heading is missing after refresh.");
        Assert.assertFalse(aiPage.getVisibleCourseCards().isEmpty(), "Course cards are missing after refresh.");
        Reporter.log("TC_AI_12: AI category page reloaded successfully", true);
    }  
    
}