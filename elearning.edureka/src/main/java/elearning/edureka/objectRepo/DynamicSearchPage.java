package elearning.edureka.objectRepo;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import elearning.edureka.genericUtility.WebDriverUtility;

public class DynamicSearchPage 
{
    WebDriver driver;
    WebDriverUtility wbu = new WebDriverUtility();

    @FindBy(name = "search")
    private WebElement searchInput;

    @FindBy(xpath = "//ul[contains(@class,'searchelasticfilterdata_pop_search_ul')]//li")
    private List<WebElement> popularSearches;

    @FindBy(xpath = "//ul[contains(@class,'searchelasticfilterdata_pop_course_cat_ul__3VeFY')]//li")
    private List<WebElement> popularCourseCategories;

    @FindBy(xpath = "//div[contains(@class,'searchelasticfilterdata_search_pop_course')]")
    private List<WebElement> searchResults;

    public DynamicSearchPage(WebDriver driver) 
    {
        this.driver = driver;
        wbu.setDriver(driver);
        PageFactory.initElements(driver, this);
    }

    public WebElement getSearchInput() 
    {
        return searchInput;
    }

    public void enterSearchKeyword(String keyword)
    {
        wbu.enterData(searchInput, keyword);
    }

    public String getSearchInputValue()
    {
        return wbu.getAttribute(searchInput);
    }

    public void clickPopularSearch(String searchName)
    {
        By locator = By.xpath("//ul[contains(@class,'searchelasticfilterdata_pop_search_ul')]//a[normalize-space()='" + searchName.toLowerCase() + "']");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        wbu.clickElement(element);
    }

    public void clickCourseCategory(String categoryName)
    {
        By locator = By.xpath("//ul[contains(@class,'searchelasticfilterdata_pop_course_cat_ul__3VeFY')]//li[normalize-space()='" + categoryName + "']");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        wbu.clickElement(element);
    }

    public int getPopularSearchCount() 
    {
        return popularSearches.size();
    }

    public int getCourseCategoryCount() 
    {
        return popularCourseCategories.size();
    }

    public int getSearchResultCount() 
    {
        return driver.findElements(By.xpath("//div[contains(@class,'searchelasticfilterdata_search_pop_course')]")).size();
    }

    public String clickCourseResult(String courseTitle)
    {
        By courseLocator = By.xpath("//div[contains(@class,'searchelasticfilterdata_search_pop_course')]//a[.//h4[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'" + courseTitle.toLowerCase() + "')]]");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement course = wait.until(ExpectedConditions.elementToBeClickable(courseLocator));
        String actualCourseTitle = course.findElement(By.tagName("h4")).getText().trim();
        wbu.clickElement(course);
        return actualCourseTitle;
    }

    public String getCourseDetailsTitle() 
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4")));
        return title.getText().trim();
    }

    public boolean verifySearchResultsContainKeyword(String keyword) 
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        return wait.until(d -> 
        {
            List<WebElement> results = d.findElements(By.xpath("//div[contains(@class,'searchelasticfilterdata_search_pop_course')]//h4"));

            for (WebElement result : results) 
            {
                String title = result.getText().trim().toLowerCase();

                if (title.contains(keyword.toLowerCase())) 
                {
                    return true;
                }
            }

            return false;
        });
    }

    public WebElement getNoResultsMessage() 
    {
        By locator = By.xpath("//*[contains(text(),'We did not understand your query')]");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public List<String> getSearchResultTitles() 
    {
        List<String> titles = new ArrayList<>();
        List<WebElement> results = driver.findElements(By.xpath("//div[contains(@class,'searchelasticfilterdata_search_pop_course')]//h4"));

        for (WebElement result : results) 
        {
            titles.add(result.getText().trim());
        }

        return titles;
    }
}