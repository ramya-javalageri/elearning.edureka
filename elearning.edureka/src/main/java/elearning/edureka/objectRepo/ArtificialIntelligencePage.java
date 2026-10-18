package elearning.edureka.objectRepo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ArtificialIntelligencePage {

    private WebDriver driver;
    private WebDriverWait wait;
    private final String aiUrl = "https://www.edureka.co/artificial-intelligence-certification-courses";

    @FindBy(xpath = "//h1[contains(normalize-space(.),'Artificial Intelligence')] | //h2[contains(normalize-space(.),'Artificial Intelligence')]")
    private WebElement aiHeading;

    @FindBy(css = "div[class*='courselist_card']")
    private List<WebElement> courseCards;

    @FindBy(xpath = "//a[contains(normalize-space(.),'View Program')]")
    private List<WebElement> viewProgramLinks;

    public ArtificialIntelligencePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public void openAIPage() {
        driver.get(aiUrl);
        wait.until(ExpectedConditions.visibilityOf(aiHeading));
    }

    public boolean isAIHeadingDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(aiHeading)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getAIHeading() {
        return aiHeading.getText().trim();
    }

    public void clickFirstViewProgram() {
        By viewProgramLocator = By.xpath("//a[contains(normalize-space(.),'View Program')]");
        wait.until(ExpectedConditions.presenceOfElementLocated(viewProgramLocator));
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 400);");

        try {
            wait.until(ExpectedConditions.elementToBeClickable(viewProgramLocator)).click();
        } catch (StaleElementReferenceException e) {
            wait.until(ExpectedConditions.elementToBeClickable(viewProgramLocator)).click();
        }
    }

    public String getFirstCourseTitle() {
        By firstCourseTitle = By.xpath("(//a[contains(@data-category,'Artificial Intelligence')])[1]");

        return wait.until(driver -> {
            try {
                WebElement element = driver.findElement(firstCourseTitle);
                String value = element.getAttribute("data-title");

                if (value == null || value.trim().isEmpty()) {
                    value = element.getText().trim();
                }

                return value != null && !value.trim().isEmpty() ? value.trim() : null;
            } catch (StaleElementReferenceException e) {
                return null;
            }
        });
    }

    public List<WebElement> getVisibleCourseCards() {
        return courseCards.stream().filter(WebElement::isDisplayed).toList();
    }

    public boolean isCourseDetailPageLoaded() {
        try {
            wait.until(driver -> !driver.getCurrentUrl().equalsIgnoreCase(aiUrl));
            return !driver.getCurrentUrl().equalsIgnoreCase(aiUrl);
        } catch (Exception e) {
            return false;
        }
    }
}