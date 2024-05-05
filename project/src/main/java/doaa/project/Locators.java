package doaa.project;

import org.openqa.selenium.By;

public interface Locators {
    By EMAIL_FIELD = By.xpath("//input[@id='session_key']");
    By PASSWORD_FIELD = By.xpath("//input[@id='session_password']");
    By SIGN_IN_BUTTON = By.xpath("//button[@type='submit']");
    By icon = By.xpath("//li-icon[@aria-label='LinkedIn']//*[name()='svg']");
    By searchBar = By.xpath("//input[@placeholder='Search']");
    By fil = By.xpath("//button[@role='link'][normalize-space()='People']"); 
    By firstElem = By.cssSelector("h3"); 
    String xpath = "//button[contains(@class, 'search-reusables__filter-pill-button') and normalize-space()='%s']";
    By searchBox= By.name("q");
    By position = By.cssSelector(".entity-result__primary-subtitle.t-14.t-black.t-normal");
    By location = By.cssSelector(".entity-result__secondary-subtitle.t-14.t-normal");
    By item = By.cssSelector(".entity-result__title-text.t-16 a");
    By listFound = By.cssSelector("li.reusable-search__result-container");
}

