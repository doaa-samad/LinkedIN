package doaa.project;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GooglePage {
    private WebDriver driver;
   

    public GooglePage(WebDriver driver) {
        this.driver = driver;
    }

    public void goTo() {
        driver.get(Config.GOOGLE_URL);
    }

    public void searchFor(String query) {
        WebElement searchInput = driver.findElement(Locators.searchBox);
        searchInput.clear();
        searchInput.sendKeys(query);
        searchInput.submit(); 
    }
}
