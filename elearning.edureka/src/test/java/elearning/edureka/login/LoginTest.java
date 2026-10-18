package elearning.edureka.login;

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

    // TC_LOGIN_01_VerifyLoginPageOpensSuccessfully
    @Test(groups = {"smoke"},priority = 0)
    public void TC_LOGIN_01() {
    	
        Assert.assertTrue(homePage.isLoginFormDisplayed(), "Login form is not displayed.");
        Reporter.log("TC_LOGIN_01: Login form displayed successfully.", true);
    }

    // TC_LOGIN_02_VerifyLoginWithValidCredentials
    @Test(groups = {"functional"},priority = 5)
    public void TC_LOGIN_02() throws EncryptedDocumentException, IOException, InterruptedException {
        String email = fileUtility.getDataFromPropertyFile("username");
        String password = fileUtility.getDataFromPropertyFile("password");
        homePage.loginToApplication(email, password);
        Reporter.log("TC_LOGIN_02: Login successful.", true);
        wbu.clickElement(homePage.getAccountDropdown());
        wbu.clickElement(homePage.getLogoutButton());
        Thread.sleep(2000);
        wbu.refresh(driver);
        Reporter.log("TC_LOGIN_02: Logout successful.", true);
    }

    // TC_LOGIN_07_VerifyPasswordCharactersAreMasked
    @Test(groups = {"functional"},priority = 1)
    public void TC_LOGIN_07() throws EncryptedDocumentException, IOException {
    	homePage.enterPassword(fileUtility.getDataFromPropertyFile("password"));
        String passwordType = homePage.getPasswordFieldType();
        String expectedPasswordType = excelUtility.getDataFromExcelFile("Login", 3, 1);
        Assert.assertEquals(passwordType, expectedPasswordType, "Password field is not masked.");
        Reporter.log("TC_LOGIN_07: Password field is masked.", true);
    }

    // TC_LOGIN_09_VerifyForgotPasswordNavigation
    @Test(groups = {"functional"},priority = 2)
    public void TC_LOGIN_09() {
        homePage.clickForgotPassword();
        Assert.assertFalse(driver.getPageSource().trim().isEmpty(), "Page content is empty after clicking Forgot Password.");
        Reporter.log("TC_LOGIN_09: Forgot Password navigation verified. URL: " + wbu.getCurrentUrl(), true);
    }

    // TC_LOGIN_14_VerifyLoginAllowsNavigationToAllCourses
    @Test(groups = {"integration"},priority = 6)
    public void TC_LOGIN_14() throws EncryptedDocumentException, IOException, InterruptedException {
        String email = excelUtility.getDataFromExcelFile("Login", 1, 1);
        String password = excelUtility.getDataFromExcelFile("Login", 2, 1);
        String expectedUrl = excelUtility.getDataFromExcelFile("Login", 5, 1);
        homePage.loginToApplication(email, password);
        homePage.clickAllCourses();
        Assert.assertTrue(wbu.getCurrentUrl().contains(expectedUrl), "All Courses page did not open. Current URL: " + wbu.getCurrentUrl());
        Reporter.log("TC_LOGIN_14: All Courses page opened successfully. URL: " + wbu.getCurrentUrl(), true);
        wbu.clickElement(allCoursesPage.getProfile());
        wbu.clickElement(homePage.getLogoutButton());
        Reporter.log("TC_LOGIN_14: Logout successful after All Courses navigation.", true);
    }

    // TC_LOGIN_18_VerifyLoginPageReloadsWithoutErrors
    @Test(groups = {"system"},priority = 4)
    public void TC_LOGIN_18() {
        wbu.refresh(driver);
        homePage.clickHomeLogin();
        Assert.assertTrue(homePage.isLoginFormDisplayed(), "Login form is not displayed after refresh.");
        Reporter.log("TC_LOGIN_18: Login form is available after refresh.", true);
    }
}