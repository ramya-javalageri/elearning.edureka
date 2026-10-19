package elearning.edureka.categories;

import java.io.IOException;


import java.time.Duration;
import java.util.List;
import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import elearning.edureka.genericUtility.BaseTest;

public class ArtificialIntelligenceTest extends BaseTest 
{
    @BeforeMethod(groups = {"smoke", "functional", "integration","negative","system"})
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

    //TC_AI_07
    @Test(groups = {"integration"})
    public void TC_AI_07_VerifyAllCoursesToAICategoryNavigation() throws EncryptedDocumentException, IOException 
    {
        String allCoursesUrl = excelUtility.getDataFromExcelFile("AI", 3, 1);
        String expectedDestination = excelUtility.getDataFromExcelFile("AI", 5, 1);
        driver.get(allCoursesUrl);
        allCoursesPage.clickCategory(excelUtility.getDataFromExcelFile("AI", 1, 1));
        List<String> titles = allCoursesPage.getCourseTitles();
        Assert.assertFalse(titles.isEmpty(), "No AI courses found.");
        allCoursesPage.clickViewCourseDetails(titles.get(0));
        Assert.assertTrue(wbu.getCurrentUrl().contains(expectedDestination), "Unexpected destination URL: " + wbu.getCurrentUrl());
        Reporter.log("TC_AI_07: Successfully navigated to AI course details", true);
        Reporter.log("TC_AI_07: Destination URL: " + driver.getCurrentUrl(), true);
        wbu.navigateBack(driver);
    }

    //TC_AI_011
    @Test(groups = {"negative"})
    public void TC_AI_11_VerifyInvalidAIPageUrl() throws EncryptedDocumentException, IOException 
    {
        String invalidUrl = excelUtility.getDataFromExcelFile("AI", 6, 1);
        String expectedUrlPart = excelUtility.getDataFromExcelFile("AI", 7, 1);
        driver.get(invalidUrl);
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> d.getCurrentUrl().contains(expectedUrlPart));
        Assert.assertTrue(wbu.getCurrentUrl().contains(expectedUrlPart), "Expected invalid URL was not opened.");
        wbu.takeScreenshot(driver, "TC_AI_11_VerifyInvalidAIPageUrl");
        Reporter.log("TC_AI_11: Invalid URL navigation checked: " + driver.getCurrentUrl(), true);
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
    
    //TC_AI_015
    @Test(groups = {"system"})
    public void TC_AI_15_VerifyEndToEndAICourseDiscovery() throws EncryptedDocumentException, IOException 
    {
        String categoryUrl = excelUtility.getDataFromExcelFile("AI", 2, 1);
        String selectedCourse = aiPage.getFirstCourseTitle();
        Assert.assertNotNull(selectedCourse, "Course title should not be null.");
        Assert.assertFalse(selectedCourse.trim().isEmpty(), "No course title was found.");
        Reporter.log("TC_AI_15: Course selected: " + selectedCourse, true);
        String currentWindow = driver.getWindowHandle();
        aiPage.clickFirstViewProgram();
        wbu.switchToNewWindowIfOpened(currentWindow);
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(d -> !d.getCurrentUrl().contains(categoryUrl));
        Assert.assertFalse(driver.getTitle().trim().isEmpty(), "Course detail page title is blank.");
        Reporter.log("TC_AI_15: End-to-end course discovery completed", true);
        Reporter.log("TC_AI_15: Course details URL: " + driver.getCurrentUrl(), true);
       if (driver.getWindowHandles().size() > 1) 
       {
           driver.close();
       }
       driver.switchTo().window(currentWindow);

    }
}