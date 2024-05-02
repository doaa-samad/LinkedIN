package doaa.project;

import org.openqa.selenium.WebDriver;

public class UserPage {
    private WebDriver driver;

    public UserPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getUserPageURL() {
        return driver.getCurrentUrl(); 
    }
}
