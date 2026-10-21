
package elearning.edureka.genericUtility;

import java.io.IOException;


import org.openqa.selenium.WebDriver;

import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import elearning.edureka.objectRepo.AllCoursesPage;
import elearning.edureka.objectRepo.ArtificialIntelligencePage;
import elearning.edureka.objectRepo.HomePage;

public class BaseTest
{
    public WebDriver driver = null;
    public WebDriverUtility wbu;
    public ExcelUtility excelUtility;
    public FileUtility fileUtility;
    public HomePage homePage;
    public AllCoursesPage allCoursesPage;
    public ArtificialIntelligencePage aiPage;

    @Parameters("browser")
    @BeforeClass(groups = {"smoke", "functional", "integration","negative","system"})
    public void launchBrowser(@Optional("chrome") String browser) throws IOException
    {
        Reporter.log("Launching the browser: "+browser, true);
        wbu = new WebDriverUtility();
        
        driver = wbu.launchBrowser(browser);
        wbu.setDriver(driver);

        excelUtility = new ExcelUtility();
        fileUtility = new FileUtility();
        homePage = new HomePage(driver);
        allCoursesPage = new AllCoursesPage(driver);
        aiPage = new ArtificialIntelligencePage(driver);
    }

    
    @AfterClass(groups = {"smoke", "functional", "integration","negative","system"})
    public void closeBrowser()
    {
    	Reporter.log("Closing the Browser", true);

        wbu.quitBrowser(driver);
    }
}
