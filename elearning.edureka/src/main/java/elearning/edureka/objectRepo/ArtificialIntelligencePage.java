
package elearning.edureka.objectRepo;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import elearning.edureka.genericUtility.WebDriverUtility;

public class ArtificialIntelligencePage 
{
    private WebDriver driver;
    private WebDriverUtility wbu = new WebDriverUtility();
    private WebDriverWait wait;
    private final String aiUrl = "https://www.edureka.co/artificial-intelligence-certification-courses";

    @FindBy(xpath = "//h1[contains(normalize-space(.),'Artificial Intelligence')] | //h2[contains(normalize-space(.),'Artificial Intelligence')]")
    private WebElement aiHeading;

    @FindBy(css = "div[class*='courselist_card']")
    private List<WebElement> courseCards;

    @FindBy(xpath = "//a[contains(normalize-space(.),'View Program')]")
    private List<WebElement> viewProgramLinks;

    public ArtificialIntelligencePage(WebDriver driver) 
    {
        this.driver = driver;
        wbu.setDriver(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public void openAIPage() 
    {
        driver.get(aiUrl);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(normalize-space(.),'Artificial Intelligence')] | //h2[contains(normalize-space(.),'Artificial Intelligence')]")));
    }

    public boolean isAIHeadingDisplayed() 
    {
        try 
        {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(normalize-space(.),'Artificial Intelligence')] | //h2[contains(normalize-space(.),'Artificial Intelligence')]"))).isDisplayed();
        } catch (Exception e) 
        {
            return false;
        }
    }

    public String getAIHeading() 
    {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(normalize-space(.),'Artificial Intelligence')] | //h2[contains(normalize-space(.),'Artificial Intelligence')]"))).getText().trim();
    }

    public void clickFirstViewProgram() 
    {
    	JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 400);");
        try 
        {
            wait.until(ExpectedConditions.elementToBeClickable(viewProgramLinks.get(0))).click();
        } catch (StaleElementReferenceException e) 
        {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(normalize-space(.),'View Program')]"))).click();
        }
    }

    public String getFirstCourseTitle() 
    {
        return wait.until(d -> 
        {
            try 
            {
                WebElement element = d.findElement(By.xpath("(//a[contains(@data-category,'Artificial Intelligence')])[1]"));
                String value = element.getAttribute("data-title");
                if (value == null || value.trim().isEmpty()) value = element.getText().trim();
                return value != null && !value.trim().isEmpty() ? value.trim() : null;
            } catch (StaleElementReferenceException e) 
            {
                return null;
            }
        });
    }

    public List<WebElement> getVisibleCourseCards() 
    {
        return wait.until(d -> 
        {
            try 
            {
                List<WebElement> visibleCards = courseCards.stream().filter(WebElement::isDisplayed).collect(Collectors.toList());
                return visibleCards.isEmpty() ? null : visibleCards;
            } catch (StaleElementReferenceException e) 
            {
                return null;
            }
        });
    }

    public boolean isCourseDetailPageLoaded() 
    {
        try 
        {
            wait.until(d -> !d.getCurrentUrl().equalsIgnoreCase(aiUrl));
            return !driver.getCurrentUrl().equalsIgnoreCase(aiUrl);
        } catch (Exception e) 
        {
            return false;
        }
    }
}
