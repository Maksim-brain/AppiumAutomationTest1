package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject {

    private static final String
    SEARCH_INIT_ELEMENT = "org.wikipedia:id/search_container",
    SEARCH_INPUT = "org.wikipedia:id/search_src_text",
    SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[contains(@text,'{SUBSTRING}')]",
    CLOSE_SEARCH_BUTTON = "org.wikipedia:id/search_close_btn";

    public SearchPageObject(AppiumDriver driver)
    {
        super(driver);
    }

    public void initSearchInput()
    {
        this.waitForElementAndClick(By.id(SEARCH_INIT_ELEMENT), "Cannot find and click search init element", 10);
        this.waitForElementPresent(By.id(SEARCH_INIT_ELEMENT), "Cannot find search input after clicking search init element", 10);
    }

    public void typeSearchLine(String search_line)
    {
        this.waitForElementAndSendKeys(By.id(SEARCH_INPUT), search_line, "Cannot find and type into search input", 10);
    }

    private static String getResultSearchElement(String substring)
    {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    public void waitForSearchResult(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(By.xpath(search_result_xpath), "Cannot find search result with substring "  + substring, 20);
    }



    public void closeSearchButton ()
    {
        this.waitForElementAndClick(    // кликаем на крестик
                By.id(CLOSE_SEARCH_BUTTON),
                "Cannot find X to cancel search",
                10
        );
    }

    public void searchCloseButton()
    {
        this.waitForElementNotPresent( // ищем крестик
                By.id(CLOSE_SEARCH_BUTTON),
                "button X is still on the page",
                10
        );
    }

    public void clearSearchField()
    {
        this.waitForElementAndClear(By.id(SEARCH_INPUT), "Cannot find and type into search input",10);
    }

    public void clickByArticleWithSubstring(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(By.xpath(search_result_xpath), "Cannot find and click search result with substring" + substring, 10);
    }

}
