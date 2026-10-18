package elearning.edureka.genericUtility;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverUtility 
{
    // WebDriver Reference
    private WebDriver driver;

    // ==================== Browser Methods ====================

    public WebDriver launchBrowser(String browser) 
    {
        if (browser.equalsIgnoreCase("chrome")) 
        {
            ChromeOptions settings = new ChromeOptions();
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("profile.password_manager_leak_detection", false);
            settings.setExperimentalOption("prefs", prefs);
            driver = new ChromeDriver(settings);
        }

        else if (browser.equalsIgnoreCase("edge")) 
        {
            EdgeOptions settings = new EdgeOptions();
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("profile.password_manager_leak_detection", false);
            settings.setExperimentalOption("prefs", prefs);
            driver = new EdgeDriver(settings);
        }

        else if (browser.equalsIgnoreCase("firefox")) 
        {
            FirefoxOptions settings = new FirefoxOptions();
            FirefoxProfile profile = new FirefoxProfile();
            profile.setPreference("signon.management.page.breachAlert.enabled", false);
            settings.setProfile(profile);
            driver = new FirefoxDriver(settings);
        }

        else 
        {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }


        genericWait(driver);
        maximizeWindow(driver);

        return driver;
    }

    public void maximizeWindow(WebDriver driver) 
    {
        driver.manage().window().maximize();
    }

    public void quitBrowser(WebDriver driver) 
    {
        if (driver != null) {
            driver.quit();
        }
    }

    public void setDriver(WebDriver driver) 
    {
        this.driver = driver;
    }

    // ==================== Navigation Methods ====================

    public void navigateBack(WebDriver driver) 
    {
        driver.navigate().back();
    }

    public void refresh(WebDriver driver) 
    {
        driver.navigate().refresh();
    }

    public String getCurrentUrl() 
    {
        return driver.getCurrentUrl();
    }

    public String getPageTitle() 
    {
        return driver.getTitle();
    }

    // ==================== Wait Methods ====================

    public void genericWait(WebDriver driver) 
    {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public void conditionalWait_ElementTobePresent(WebDriver driver, WebElement ele) 
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(ele));
    }

    public void conditionalWait_ActionTobePerformedOnElement(WebDriver driver, WebElement ele) 
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(ele));
    }

    // ==================== WebElement Action Methods ====================

    public void clickElement(WebElement ele) 
    {
        conditionalWait_ActionTobePerformedOnElement(driver, ele);
        ele.click();
    }

    public void enterData(WebElement ele, String value) 
    {
        ele.clear();
        ele.sendKeys(value);
    }

    // ==================== Validation Methods ====================

    public boolean isDisplayed(WebElement ele) 
    {
        return ele.isDisplayed();
    }

    public boolean isDisplayed(List<WebElement> ele) 
    {
        return !ele.isEmpty();
    }

    public boolean isEnabled(WebElement ele) 
    {
        return ele.isEnabled();
    }

    public boolean isEmpty(String ele) 
    {
        return ele == null || ele.isEmpty();
    }

    public String getAttribute(WebElement ele) 
    {
        return ele.getAttribute("value");
    }

    // ==================== Window Handling Methods ====================

    public void switchWindow() 
    {
        String parentId = driver.getWindowHandle();
        Set<String> windowIds = driver.getWindowHandles();
        for (String id : windowIds) 
        {
            if (!id.equals(parentId)) 
            {
                driver.switchTo().window(id);
                return;
            }
        }
        throw new IllegalStateException("No child window found.");
    }
    
    public void switchToNewWindowIfOpened(String originalWindow) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(d -> d.getWindowHandles().size() > 1);

            for (String window : driver.getWindowHandles()) {
                if (!window.equals(originalWindow)) {
                    driver.switchTo().window(window);
                    return;
                }
            }
        } catch (TimeoutException e) {
            // No new tab opened; continue in the current tab.
        }
    }

    // ==================== Screenshot Methods ====================

    public void takeScreenshot(WebDriver driver, String screenshotName) throws IOException 
    {
    	TakesScreenshot tks = (TakesScreenshot)driver;	
        File source = tks.getScreenshotAs(OutputType.FILE);
        File destination = new File("./Screenshots/"+ screenshotName + ".png");
        FileHandler.copy(source, destination);
    }
}