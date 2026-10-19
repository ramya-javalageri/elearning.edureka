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

public class ArtificialIntelligenceTest extends BaseTest {
    private String originalWindow;

    @BeforeMethod(alwaysRun = true)
    public void openAICategoryPage() throws EncryptedDocumentException, IOException {
        originalWindow = driver.getWindowHandle();
        driver.get(excelUtility.getDataFromExcelFile("AI", 0, 1));
        Reporter.log("AI category page opened successfully", true);
    }

    //TC_AI_01
    @Test(groups = {"smoke", "functional"})
    public void TC_AI_01_VerifyAICategoryPageOpens() throws EncryptedDocumentException, IOException {
        Reporter.log("TC_AI_01: Test execution started", true);
        String expectedHeading = excelUtility.getDataFromExcelFile("AI", 1, 1);
        String actualHeading = aiPage.getAIHeading();
        Assert.assertTrue(actualHeading.contains(expectedHeading), "Incorrect category heading. Actual: " + actualHeading);
        Reporter.log("TC_AI_01: Test passed. Heading: " + actualHeading, true);
    }

    //TC_AI_03
    @Test(groups = {"functional"})
    public void TC_AI_03_VerifySelectedCourseOpensDetails() throws EncryptedDocumentException, IOException {
        Reporter.log("TC_AI_03: Test execution started", true);
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
    }



    //TC_AI_012

	@Test(groups = {"system"})
	public void TC_AI_12_VerifyAICategoryReloadsCorrectly() {
	    Reporter.log("TC_AI_12: Test execution started", true);
	    wbu.refresh(driver);
	    Assert.assertTrue(aiPage.isAIHeadingDisplayed(), "AI category heading is missing after refresh.");
	    Assert.assertFalse(aiPage.getVisibleCourseCards().isEmpty(), "Course cards are missing after refresh.");
	    Reporter.log("TC_AI_12: AI category page reloaded successfully", true);
	}

    
    //TC_AI_015
    @Test(groups = {"system", "integration"})
    public void TC_AI_15_VerifyEndToEndAICourseDiscovery() throws EncryptedDocumentException, IOException {
        Reporter.log("TC_AI_15: Test execution started", true);
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
    }

    @AfterMethod(alwaysRun = true)
    public void closeExtraTabs() {
        if (driver == null) return;
        Set<String> handles = driver.getWindowHandles();
        if (handles.isEmpty()) return;
        List<String> windowList = new ArrayList<>(handles);
        for (String window : windowList) {
            if (!window.equals(originalWindow) && driver.getWindowHandles().contains(window)) {
                driver.switchTo().window(window);
                driver.close();
            }
        }
        if (driver.getWindowHandles().contains(originalWindow)) driver.switchTo().window(originalWindow);
    }
}