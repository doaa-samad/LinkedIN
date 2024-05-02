package doaa.project;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;  // Import the Duration class

public class ResultPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public ResultPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 
    }

    public void applyFilter(String filterType) {
    
        String xpath = String.format(Locators.xpath, filterType);
        WebElement filterButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        filterButton.click();
    }
}
