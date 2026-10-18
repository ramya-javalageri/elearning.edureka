package elearning.edureka.dynamicSearch;

import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import elearning.edureka.genericUtility.BaseTest;

public class DynamicSearchNegativeTest extends BaseTest 
{
	@BeforeMethod(groups = {"smoke", "functional", "integration", "negative", "regression", "system"})
    public void navigation() throws InterruptedException, IOException
    {
    	driver.get(fileUtility.getDataFromPropertyFile("url"));
    	wbu.clickElement(homePage.getSearchCourses());
        wbu.conditionalWait_ElementTobePresent(driver, dynamicSearchPage.getSearchInput());
//        Assert.assertTrue(dynamicSearchPage.getSearchInput().isDisplayed(),"Search input is not displayed");
        Reporter.log("Dynamic Search page opened successfully", true);
    }
	
    // TC_DS_008: Verify no-results state
    @Test(groups = {"negative"})
    public void dynamicSearchNegativeTest_TC_DS_008() 
    {
    	
        String keyword = "qzxv-98765";

        dynamicSearchPage.enterSearchKeyword(keyword);

        Assert.assertEquals(dynamicSearchPage.getSearchInputValue(), keyword, "Invalid keyword was not entered");

        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        WebElement message = wait.until(ExpectedConditions.visibilityOf(dynamicSearchPage.getNoResultsMessage()));

        Assert.assertTrue(message.isDisplayed(), "No-results message is not displayed");
        Assert.assertTrue(message.getText().contains("We did not understand your query"), "Unexpected no-results message");

        Reporter.log("TC_DS_008: No-results message displayed for invalid keyword", true);
    }

    // TC_DS_009: Verify empty and whitespace-only searches
    @Test(groups = {"negative"})
    public void dynamicSearchNegativeTest_TC_DS_009() 
    {
        WebElement searchInput = dynamicSearchPage.getSearchInput();

        dynamicSearchPage.enterSearchKeyword("");

        Assert.assertEquals(dynamicSearchPage.getSearchInputValue(), "", "Search input was not cleared");

        dynamicSearchPage.enterSearchKeyword("     ");

        Assert.assertTrue(searchInput.isDisplayed(), "Search page became unusable after whitespace-only input");

        Reporter.log("TC_DS_009: Empty and whitespace-only inputs handled", true);
    }
}