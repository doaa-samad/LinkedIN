package doaa.project;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.ArrayList;

public class GoogleResultPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public GoogleResultPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Fetch and print the first five non-null <a> tag hrefs from Google search results
    public List<String> fetchFirstFiveLinks() {
        List<WebElement> allLinks = driver.findElements(By.tagName("a"));
        List<String> validHrefs = new ArrayList<>();
        
        // Collect the first five non-null hrefs
        for (WebElement link : allLinks) {
            String href = link.getAttribute("href");
            if (href != null && !href.isEmpty()) {
                validHrefs.add(href);
//                System.out.println("URL: " + href); // Print non-null href
                if (validHrefs.size() == 5) {
                    break; // Stop after collecting five valid hrefs
                }
            }
        }
        return validHrefs;
    }
}
