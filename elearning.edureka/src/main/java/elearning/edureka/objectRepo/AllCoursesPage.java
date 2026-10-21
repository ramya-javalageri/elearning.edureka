
package elearning.edureka.objectRepo;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import elearning.edureka.genericUtility.WebDriverUtility;

public class AllCoursesPage 
{

    WebDriver driver;
    WebDriverUtility wbu;
    WebDriverWait wait;

    @FindBy(xpath = "//div[@class='page_top_title']")
    private WebElement pageHeading;

    @FindBy(xpath = "//div[@class='page_top_sub_title']")
    private WebElement introductoryText;
    
    @FindBy(xpath = "//button[@id='navbarDropdown_user']")
    private WebElement profile;

    public AllCoursesPage(WebDriver driver) {
        this.driver = driver;
        wbu = new WebDriverUtility();
        wbu.setDriver(driver);
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public boolean isPageHeadingDisplayed() 
    {
        try 
        {
            return wait.until(ExpectedConditions.visibilityOf(pageHeading)).isDisplayed();
        } 
        catch (Exception e) 
        {
            return false;
        }
    }

    public boolean isIntroductoryTextDisplayed() 
    {
        try 
        {
            return wait.until(ExpectedConditions.visibilityOf(introductoryText)).isDisplayed();
        } 
        catch (Exception e) 
        {
            return false;
        }
    }

    public void clickCategory(String categoryName) 
    {
        By category = By.xpath("//a[contains(text(),'"+categoryName+"') and @class='category_nav_link']");
        
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(category));
        
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].scrollIntoView({block:'center'});",element);
  
        WebElement categoryElement = wait.until(ExpectedConditions.elementToBeClickable(category));

        wbu.clickElement(categoryElement);
    }

    public boolean isCategoryDisplayed(String categoryName) {
        By category = By.xpath("//a[contains(text(),'"+categoryName+"') and @class='category_nav_link']");
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(category)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCategorySelected(String categoryName) 
    {
        By selectedCategory = By.xpath("//li[contains(@class,'category_slide_detail') and contains(@class,'active')]//a[contains(text(),'"+categoryName+"')]");

        boolean isSelected = !driver.findElements(selectedCategory).isEmpty();
        
        return isSelected;

    }

    public List<String> getCourseTitles() {
        List<String> courseTitles = new ArrayList<>();

        By titles = By.xpath("//h2[@class='title']");

        List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(titles));

        for (WebElement element : elements) 
        {
            String title = element.getText().trim();

            if (!title.isEmpty() && !courseTitles.contains(title)) 
            {
                courseTitles.add(title);
            }
        }

        return courseTitles;
    }

    public List<String> getCourseCardTexts() 
    {
        List<String> cardTexts = new ArrayList<>();

        By cards = By.xpath("//button[contains(text(),'View Course Details')]/ancestor::div[//h2]");

        List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(cards));

        for (WebElement element : elements) 
        {
            cardTexts.add(element.getText().trim());
        }

        return cardTexts;
    }

    public void clickViewCourseDetails(String courseName) 
    {
    	By detailsButton = By.xpath("//h2[contains(text(),'"+courseName+"')]/..//button");
        
    	WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(detailsButton));

    	JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].scrollIntoView({block:'center'});",button);
    	
    	WebElement button1 = wait.until(ExpectedConditions.elementToBeClickable(detailsButton));
        wbu.clickElement(button1);
    }

    public boolean isPageNotFoundDisplayed() 
    {
        By errorMessage = By.xpath("//h1");

        return !driver.findElements(errorMessage).isEmpty();
    }
    
    public WebElement getProfile()
    {
    	return profile;
    }
}
