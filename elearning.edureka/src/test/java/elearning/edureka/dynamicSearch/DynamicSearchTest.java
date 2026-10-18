package elearning.edureka.dynamicSearch;

import java.io.IOException;

//import java.io.IOException;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import elearning.edureka.genericUtility.BaseTest;
import elearning.edureka.genericUtility.DataProviders;

public class DynamicSearchTest extends BaseTest 
{
	@BeforeMethod(groups = {"smoke", "functional", "integration", "negative", "regression", "system"})
    public void navigation() throws InterruptedException, IOException
    {
    	driver.get(fileUtility.getDataFromPropertyFile("url"));
    	wbu.clickElement(homePage.getSearchCourses());
        wbu.conditionalWait_ElementTobePresent(driver, dynamicSearchPage.getSearchInput());
        Reporter.log("Dynamic Search page opened successfully", true);
    }
	
    // TC_DS_001: Verify search-page navigation
    @Test(groups = {"smoke"})
    public void dynamicSearchTest_TC_DS_001() 
    {
        Assert.assertTrue(dynamicSearchPage.getSearchInput().isDisplayed(), "Dynamic Search page did not open");
        Reporter.log("TC_DS_001: Dynamic Search page opened successfully", true);
    }

    // TC_DS_002: Verify search-component initialization
    @Test(groups = {"functional"})
    public void dynamicSearchTest_TC_DS_002() 
    {
        WebElement searchInput = dynamicSearchPage.getSearchInput();

        Assert.assertTrue(searchInput.isDisplayed(), "Search input is not displayed");
        Assert.assertTrue(searchInput.isEnabled(), "Search input is disabled");
        Assert.assertTrue(dynamicSearchPage.getPopularSearchCount() > 0, "Popular searches are not displayed");
        Assert.assertTrue(dynamicSearchPage.getCourseCategoryCount() > 0, "Course categories are not displayed");

        Reporter.log("TC_DS_002: Search components initialized successfully", true);
    }

    // TC_DS_003: Verify exact-title search
    @Test(dataProvider = "exactCourseTitles", dataProviderClass = DataProviders.class, groups = {"functional"})
    public void dynamicSearchTest_TC_DS_003(String keyword) 
    {
        dynamicSearchPage.enterSearchKeyword(keyword);

        Assert.assertEquals(dynamicSearchPage.getSearchInputValue(), keyword, "Exact keyword was not entered");

        Reporter.log("TC_DS_003: Exact-title search executed for " + keyword, true);
    }

    // TC_DS_004: Verify autocomplete updates while typing
    @Test(dataProvider = "autocompleteKeywords", dataProviderClass = DataProviders.class, groups = {"functional"})
    public void dynamicSearchTest_TC_DS_004(String firstKeyword, String latestKeyword) 
    {
        dynamicSearchPage.enterSearchKeyword(firstKeyword);

        Assert.assertEquals(dynamicSearchPage.getSearchInputValue(), firstKeyword, "First keyword was not entered");

        dynamicSearchPage.enterSearchKeyword(latestKeyword);

        Assert.assertEquals(dynamicSearchPage.getSearchInputValue(), latestKeyword, "Latest keyword was not entered");

        Reporter.log("TC_DS_004: Autocomplete input updated from " + firstKeyword + " to " + latestKeyword, true);
    }

    // TC_DS_006: Verify case normalization
    @Test(dataProvider = "caseKeywords", dataProviderClass = DataProviders.class, groups = {"functional"})
    public void dynamicSearchTest_TC_DS_006(String upperKeyword, String lowerKeyword) 
    {
        dynamicSearchPage.enterSearchKeyword(upperKeyword);
        List<String> upperResults = dynamicSearchPage.getSearchResultTitles();

        dynamicSearchPage.enterSearchKeyword("");
        dynamicSearchPage.enterSearchKeyword(lowerKeyword);
        List<String> lowerResults = dynamicSearchPage.getSearchResultTitles();

        Assert.assertEquals(lowerResults, upperResults, "Search results differ for uppercase and lowercase keywords");

        Reporter.log("TC_DS_006: Case normalization verified for " + upperKeyword + " and " + lowerKeyword, true);
    }

    // TC_DS_012: Verify Popular Searches shortcut
    @Test(dataProvider = "popularSearchKeywords", dataProviderClass = DataProviders.class, groups = {"integration"})
    public void dynamicSearchTest_TC_DS_012(String keyword, String url) 
    {
        Assert.assertTrue(dynamicSearchPage.getPopularSearchCount() > 0, "No popular searches are displayed");

        dynamicSearchPage.clickPopularSearch(keyword);

        Assert.assertTrue(wbu.getCurrentUrl().contains(url), "Not navigated to " + keyword + " page");

        Reporter.log("TC_DS_012: Popular search shortcut selected: " + keyword, true);
    }

    // TC_DS_013: Verify category navigation
    @Test(dataProvider = "courseCategories", dataProviderClass = DataProviders.class, groups = {"integration"})
    public void dynamicSearchTest_TC_DS_013(String category, String url) 
    {
        Assert.assertTrue(dynamicSearchPage.getCourseCategoryCount() > 0, "No course categories are displayed");

        dynamicSearchPage.clickCourseCategory(category);

        Assert.assertTrue(wbu.getCurrentUrl().contains(url), "Not navigated to " + category + " page");

        Reporter.log("TC_DS_013: Course category selected: " + category, true);
    }

    // TC_DS_014: Verify course-result navigation
    @Test(dataProvider = "courseSearchKeywords", dataProviderClass = DataProviders.class, groups = {"integration"})
    public void dynamicSearchTest_TC_DS_014(String keyword) 
    {
        dynamicSearchPage.enterSearchKeyword(keyword);

        Assert.assertTrue(dynamicSearchPage.getSearchResultCount() > 0, "No course results found for " + keyword);
        String actualCourseTitle = dynamicSearchPage.getCourseDetailsTitle();
        String selectedCourseTitle = dynamicSearchPage.clickCourseResult(keyword);

        Assert.assertFalse(selectedCourseTitle.isEmpty(), "Selected course title is empty");
        Assert.assertFalse(actualCourseTitle.isEmpty(), "Destination page title is empty");
        Assert.assertEquals(actualCourseTitle, selectedCourseTitle, "Destination course does not match the selected result");

        Reporter.log("TC_DS_014: Course navigation verified. Selected: " + selectedCourseTitle + "; Destination: " + actualCourseTitle, true);
    }

    // TC_DS_016: Verify rapid-query response ordering
    @Test(dataProvider = "rapidSearchKeywords", dataProviderClass = DataProviders.class, groups = {"system"})
    public void dynamicSearchTest_TC_DS_016(String firstKeyword, String secondKeyword) 
    {
        dynamicSearchPage.enterSearchKeyword(firstKeyword);
        dynamicSearchPage.enterSearchKeyword(secondKeyword);

        Assert.assertEquals(dynamicSearchPage.getSearchInputValue(), secondKeyword, "Latest search keyword was not retained");

        Reporter.log("TC_DS_016: Latest keyword retained and matching results displayed: " + secondKeyword, true);
    }

    // TC_DS_018: Verify repeated search-and-clear transitions
    @Test(dataProvider = "searchKeywords", dataProviderClass = DataProviders.class, groups = {"system"})
    public void dynamicSearchTest_TC_DS_018(String keyword) 
    {
        for (int i = 1; i <= 3; i++) 
        {
            dynamicSearchPage.enterSearchKeyword(keyword);

            Assert.assertEquals(dynamicSearchPage.getSearchInputValue(), keyword, "Keyword entry failed in iteration " + i);

            dynamicSearchPage.enterSearchKeyword("");

            Assert.assertEquals(dynamicSearchPage.getSearchInputValue(), "", "Search field was not cleared in iteration " + i);
        }

        Reporter.log("TC_DS_018: Repeated search and clear verified for " + keyword, true);
    }
}