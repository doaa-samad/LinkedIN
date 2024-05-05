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

        // Read user and filter from CSV
        String[] userData = CSVUtility.readFirstRowData(Config.CSV_FILE_PATH);
        if (userData.length > 1) {
            String userToSearch = userData[0];
            String filterType = userData[1];

            // LinkedIn search and result saving
            mainPage.search(userToSearch);
            resultPage.applyFilter(filterType);
            List<String[]> linkedInResults = filterResultPage.getFirstFiveResults();
            CSVUtility.writeToCSV(linkedInResults);

            List<String> linkedInHrefs = new ArrayList<>();
            for (String[] item : linkedInResults) {
                linkedInHrefs.add(item[1]); // Collect LinkedIn hrefs
            }

            // Google search and result fetching using JavaScript
            googlePage.goTo();
            googlePage.searchFor(userToSearch + " " + filterType + " LinkedIn");
            List<String> googleHrefs = googleResultPage.fetchFirstFiveLinks();
            CSVUtility.appendGoogleHrefsToCSV(googleHrefs);

            // Print LinkedIn and Google hrefs alternately and perform assertions
            int maxIndex = Math.min(linkedInHrefs.size(), googleHrefs.size());
            for (int i = 0; i < maxIndex; i++) {
                System.out.println("LinkedIn URL: " + linkedInHrefs.get(i));
                System.out.println("Google URL: " + googleHrefs.get(i));
                Assert.assertNotEquals(linkedInHrefs.get(i), googleHrefs.get(i), "URLs should not be the same.");
            }
        } else {
            System.out.println("CSV data is incomplete.");
        }
    }



    @AfterClass
    public void tearDown() throws InterruptedException {
        if (driver != null) {
            driver.quit();
        }
    }
}
