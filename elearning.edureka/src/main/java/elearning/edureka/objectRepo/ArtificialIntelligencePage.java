
package elearning.edureka.objectRepo;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ArtificialIntelligencePage {
    private WebDriver driver;
    private WebDriverWait wait;
    private final String aiUrl = "https://www.edureka.co/artificial-intelligence-certification-courses";
    private final By aiHeading = By.xpath("//h1[contains(normalize-space(.),'Artificial Intelligence')] | //h2[contains(normalize-space(.),'Artificial Intelligence')]");
    private final By courseCards = By.cssSelector("div[class*='courselist_card']");
    private final By viewProgramLinks = By.xpath("//a[contains(normalize-space(.),'View Program')]");

    public ArtificialIntelligencePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public void openAIPage() {
        driver.get(aiUrl);
        wait.until(ExpectedConditions.visibilityOfElementLocated(aiHeading));
    }

    public boolean isAIHeadingDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(aiHeading)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getAIHeading() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(aiHeading)).getText().trim();
    }

    public void clickFirstViewProgram() {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 400);");
        try {
            wait.until(ExpectedConditions.elementToBeClickable(viewProgramLinks)).click();
        } catch (StaleElementReferenceException e) {
            wait.until(ExpectedConditions.elementToBeClickable(viewProgramLinks)).click();
        }
    }

    public String getFirstCourseTitle() {
        By firstCourseTitle = By.xpath("(//a[contains(@data-category,'Artificial Intelligence')])[1]");
        return wait.until(d -> {
            try {
                WebElement element = d.findElement(firstCourseTitle);
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
        return wait.until(d -> {
            try {
                List<WebElement> cards = d.findElements(courseCards);
                List<WebElement> visibleCards = cards.stream().filter(WebElement::isDisplayed).collect(Collectors.toList());
                return visibleCards.isEmpty() ? null : visibleCards;
            } catch (StaleElementReferenceException e) {
                return null;
            }
        });
    }

    public boolean isCourseDetailPageLoaded() {
        try {
            wait.until(d -> !d.getCurrentUrl().equalsIgnoreCase(aiUrl));
            return !driver.getCurrentUrl().equalsIgnoreCase(aiUrl);
        } catch (Exception e) {
            return false;
        }
    }
}
