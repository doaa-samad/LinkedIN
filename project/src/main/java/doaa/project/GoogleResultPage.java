package doaa.project;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class GoogleResultPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public GoogleResultPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20)); 
    }

    public void clickOnFirstResult() {
        try {
            WebElement result = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='rso']//div[@class='g']//a/h3")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", result);
            result.click();
        } catch (Exception e) {
            System.out.println("Error clicking on the first result: " + e.getMessage());
        }
    }
}
