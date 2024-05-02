package doaa.project;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FilterPage {
    private WebDriver driver;

    public FilterPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isFilterButtonDisplayed(String filterType) {
        // Constructing XPath based on the filter type provided
        String xpath = String.format("//button[@aria-label='Filter by: %s']", filterType);
        return driver.findElement(By.xpath(xpath)).isDisplayed();
    }
}
