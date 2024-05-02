package doaa.project;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class FilterResultPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public FilterResultPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public List<String[]> getFirstFiveResults() {
        List<String[]> results = new ArrayList<>();
        List<String> names = new ArrayList<>();

      
        List<WebElement> listItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(Locators.listFound));
        int count = 0;
        for (WebElement item : listItems) {
            if (count >= 5) break;
            try {
              
                WebElement linkElement = item.findElement(Locators.item);
                String fullText = linkElement.getText();

  
                String[] parts = fullText.split("View", 2);
                String name = parts[0].trim();

              
                String href = linkElement.getAttribute("href");
                String position = item.findElement(Locators.position).getText();
                String location = item.findElement(Locators.location).getText();

                
                results.add(new String[]{name, href, position, location});
                
   
                names.add(name);
                
                count++; 
            } catch (Exception e) {
                System.out.println("Error extracting data from result item: " + e.getMessage());
            }
        }

      
        System.out.println("Names of the users:");
        for (String userName : names) {
            System.out.println(userName);
        }

     
        return results;
    }
}
