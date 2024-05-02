package doaa.project;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;

public class MainPage {
    private WebDriver driver;
    private By searchBar = By.xpath("//input[@placeholder='Search']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void search(String searchTerm) {
        WebElement searchInput = driver.findElement(searchBar);
        searchInput.clear(); 
        searchInput.sendKeys(searchTerm + Keys.ENTER); 
    }
}
