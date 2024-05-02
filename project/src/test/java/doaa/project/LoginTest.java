package doaa.project;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class LoginTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private LoginPage loginPage;
    private MainPage mainPage;
    private ResultPage resultPage;
    private FilterResultPage filterResultPage;
    private GooglePage googlePage;
    private GoogleResultPage googleResultPage;
    private UserPage userPage;
   

    @BeforeClass
    public void setupClass() {
        if (Config.browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (Config.browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            options.addPreference("dom.disable_open_during_load", false);
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver(options);
        }
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        loginPage = new LoginPage(driver);
        mainPage = new MainPage(driver);
        resultPage = new ResultPage(driver);
        filterResultPage = new FilterResultPage(driver);
        googlePage = new GooglePage(driver);
        googleResultPage = new GoogleResultPage(driver);
        userPage = new UserPage(driver);
        driver.get(Config.LINKEDIN_URL);
    }


    @Test
    public void testLoginAndSearch() throws IOException {
        loginPage.loginAs(Config.EMAIL, Config.PASSWORD);
        wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.searchBar));

        String[] userData = CSVUtility.readFirstRowData(Config.CSV_FILE_PATH);
        if (userData.length > 1) {
            String userToSearch = userData[0];
            String filterType = userData[1];
            mainPage.search(userToSearch);
            wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.searchBar));

            resultPage.applyFilter(filterType);
            List<String[]> searchResults = filterResultPage.getFirstFiveResults();
            CSVUtility.writeToCSV(searchResults);

            List<String> hrefs = new ArrayList<>();
            for (String[] item : searchResults) {
                hrefs.add(item[1]);  
            }

           
            for (String[] item : searchResults) {
            	String users= item[0];
                String href = item[1]; // Assume item[1] contains the URL

                googlePage.goTo();
                googlePage.searchFor(users + " LinkedIn");
                googleResultPage.clickOnFirstResult();
                
                // Check if a new tab opened and switch if necessary
                ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
                if (tabs.size() > 1) {
                    driver.switchTo().window(tabs.get(1));
                }

                String currentUrl = userPage.getUserPageURL();
                System.out.println("LinkedIn URL: " + href);
                System.out.println("Google URL: " + currentUrl);

                Assert.assertNotEquals(href, currentUrl, "URLs should not be the same.");

                if (tabs.size() > 1) {
                    driver.close();
                    driver.switchTo().window(tabs.get(0));
                }
            }
        } else {
            Assert.fail("CSV data is incomplete.");
        }
    }


    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
