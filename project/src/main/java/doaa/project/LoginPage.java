package doaa.project;

import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterEmail(String email) {
        driver.findElement(Locators.EMAIL_FIELD).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(Locators.PASSWORD_FIELD).sendKeys(password);
    }

    public void clickSignIn() {
        driver.findElement(Locators.SIGN_IN_BUTTON).click();
    }

    public void loginAs(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSignIn();
    }
}
