package elearning.edureka.objectRepo;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import elearning.edureka.genericUtility.FileUtility;
import elearning.edureka.genericUtility.WebDriverUtility;

public class HomePage 
{
    WebDriver driver;
    WebDriverWait wait;

    WebDriverUtility wbu;
    FileUtility fu;

    // Home Page elements
    @FindBy(xpath = "//div[contains(@class,'navbar_search_click_bx') and .//span[normalize-space()='Search courses']]")
    private WebElement searchCourses;

    @FindBy(xpath = "//button[normalize-space()='Log in']")
    private WebElement loginButton;

    @FindBy(id = "dropdown-user_drop")
    private WebElement accountDropdown;

    @FindBy(xpath = "//a[normalize-space()='Log Out']")
    private WebElement logout;

    @FindBy(xpath = "//a[normalize-space()='All Courses']")
    private WebElement allCourses;

    @FindBy(xpath = "//span[@class='duq_chevron_icon__3KJrO duq_closed__1CYAa']")
    private WebElement query;

    // Login form elements
    @FindBy(id = "loginFormEmail")
    private WebElement emailField;

    @FindBy(id = "loginPassword")
    private WebElement passwordField;

    @FindBy(xpath = "//form[@class='form_form__3O5ey']//button")
    private WebElement submitLoginButton;

    @FindBy(xpath = "//span[normalize-space()='Forgot?']")
    private WebElement forgotPasswordLink;

    // Constructor
    public HomePage(WebDriver driver) 
    {
        this.driver = driver;
        fu = new FileUtility();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wbu = new WebDriverUtility();
        wbu.setDriver(driver);
        PageFactory.initElements(driver, this);
    }

    // Login operations
    public void clickHomeLogin() 
    {
        wbu.clickElement(loginButton);
    }

    public void enterEmail(String email) 
    {
        wbu.conditionalWait_ElementTobePresent(driver, emailField);
        wbu.enterData(emailField, email);
    }

    public void enterPassword(String password) 
    {
    	wbu.conditionalWait_ElementTobePresent(driver, passwordField);
        wbu.enterData(passwordField, password);
    }

    public void clickLogin() 
    {
        wbu.clickElement(submitLoginButton);
    }

    public void loginToApplication(String email, String password) 
    {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
    }

    public void login() throws IOException 
    {
        clickHomeLogin();
        enterEmail(fu.getDataFromPropertyFile("username"));
        enterPassword(fu.getDataFromPropertyFile("password"));
        clickLogin();
    }

    public boolean isLoginFormDisplayed() 
    {
        return wait.until(ExpectedConditions.visibilityOf(emailField)).isDisplayed() && passwordField.isDisplayed() && submitLoginButton.isDisplayed();
    }

    public String getPasswordFieldType() 
    {
    	wbu.conditionalWait_ElementTobePresent(driver, passwordField);
        return passwordField.getAttribute("type");
    }

    public void clickForgotPassword() 
    {
        wbu.clickElement(forgotPasswordLink);
    }

    // All Courses operations
    public void clickAllCourses() 
    {

        By loader = By.cssSelector("div[class*='loader_loader_div']");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loader));
        wbu.clickElement(allCourses);
    }
       
    public WebElement getallCourses() 
    {
        return allCourses;
    }

    // Search operations
    public WebElement getSearchCourses() 
    {
        return searchCourses;
    }

    // Account and logout operations
    public WebElement getAccountDropdown() 
    {
        return accountDropdown;
    }

    public void clickAccountDropdown() 
    {
        wbu.clickElement(accountDropdown);
    }
    
    public void clickLogout() throws InterruptedException
    {
    	wbu.clickElement(logout);
        Thread.sleep(2000);
    }

    public WebElement getLogoutButton() 
    {
        return logout;
    }

    public boolean isLogoutDisplayed() 
    {
        return logout.isDisplayed();
    }

    // Other operations
    public WebElement getLoginButton() 
    {
        return loginButton;
    }

    public WebElement getQuery() 
    {
        return query;
    }
}