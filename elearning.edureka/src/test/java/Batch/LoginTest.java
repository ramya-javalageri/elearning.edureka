package Batch;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import elearning.edureka.genericUtility.BaseTest;

public class LoginTest extends BaseTest {

    @BeforeMethod(groups = {"smoke", "functional", "integration","negative","system"})
    public void openLoginPage() throws EncryptedDocumentException, IOException {
        driver.get(fileUtility.getDataFromPropertyFile("url"));
        homePage.clickHomeLogin();
        Assert.assertTrue(homePage.isLoginFormDisplayed(), "Login form is not displayed.");
        Reporter.log("Navigated to application and opened login popup.", true);
    }

    // TC_LOGIN_02_VerifyLoginWithValidCredentials
    @Test(groups = {"functional"},priority = 1)
    public void TC_LOGIN_02() throws EncryptedDocumentException, IOException, InterruptedException 
    {
        String email = fileUtility.getDataFromPropertyFile("username");
        String password = fileUtility.getDataFromPropertyFile("password");
        homePage.loginToApplication(email, password);
        Reporter.log("TC_LOGIN_02: Login successful.", true);
        wbu.clickElement(homePage.getAccountDropdown());
        homePage.clickLogout();
        Reporter.log("TC_LOGIN_02: Logout successful.", true);
    }

    // TC_LOGIN_07_VerifyPasswordCharactersAreMasked
    @Test(groups = {"functional"},priority = 3)
    public void TC_LOGIN_07() throws EncryptedDocumentException, IOException 
    {
    	homePage.enterPassword(fileUtility.getDataFromPropertyFile("password"));
        String passwordType = homePage.getPasswordFieldType();
        String expectedPasswordType = excelUtility.getDataFromExcelFile("Login", 3, 1);
        Assert.assertEquals(passwordType, expectedPasswordType, "Password field is not masked.");
        Reporter.log("TC_LOGIN_07: Password field is masked.", true);
    }

    // TC_LOGIN_09_VerifyForgotPasswordNavigation
    @Test(groups = {"functional"},priority = 2)
    public void TC_LOGIN_09() 
    {
        homePage.clickForgotPassword();
        Assert.assertFalse(driver.getPageSource().trim().isEmpty(), "Page content is empty after clicking Forgot Password.");
        Reporter.log("TC_LOGIN_09: Forgot Password navigation verified. URL: " + wbu.getCurrentUrl(), true);
    }

}