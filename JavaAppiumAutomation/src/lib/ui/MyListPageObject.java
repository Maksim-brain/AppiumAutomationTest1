package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class MyListPageObject extends MainPageObject {

    public static final String
    MY_FOLDER = "org.wikipedia:id/item_title",
    ARTICLE_ITEM_DESCRIPTION_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_description'][@text='{DESCRIPTION}']",
    ARTICLE_TITLE_TPL = "//*[@text='{ARTICLE}']",
    SEARCH_TITLE_TEXT__TPL = "//*[@resource-id='org.wikipedia:id/view_page_title_text'][@text='{NAME}']",
    TITLE_ELEMENT = "org.wikipedia:id/view_page_title_text";

    private static String getSavedItemDescription(String item_description)
    {
        return ARTICLE_ITEM_DESCRIPTION_TPL.replace("{DESCRIPTION}", item_description);
    }


    public MyListPageObject(AppiumDriver driver)
    {
        super(driver);
    }

    public void openFolder()
    {
        this.waitForElementAndClick( // нажимаем в саму папку
                By.id(MY_FOLDER),
                "Cannot find created my folder",
                10
        );
    }

    public void swipeByArticleToDelete(String item_description)
    {
        String item_xpath = getSavedItemDescription(item_description);
        this.swipeElementToLeft( // удаляем одну статью
                By.xpath(item_xpath),
                "Cannot find saved article"
        );
    }

    private static String getArticleItemDescription(String article_title)
    {
        return ARTICLE_TITLE_TPL.replace("{ARTICLE}", article_title);
    }

    public void waitForArticleTitle(String article_title)
    {
        String search_article_xpath = getArticleItemDescription(article_title);
        this.waitForElementNotPresent( // проверяем что есть вторая
                By.xpath(search_article_xpath),
                "Cannot find remaining article",
                10
        );
    }

    public void clickOnArticle(String article_title)
    {
        String search_article_xpath = getArticleItemDescription(article_title);
        this.waitForElementAndClick( // нажимаем на остающуюся статью
                By.xpath(search_article_xpath),
                "Cannot click created folder Java",
                10
        );
    }

    private static String getResultSearchElement(String name_title)
    {
        return SEARCH_TITLE_TEXT__TPL.replace("{NAME}", name_title);
    }

    public void checkTitleCoincidence(String name_title)
    {
        String name_title_xpath = getResultSearchElement(name_title);// проверяем что title совпадает
        this.waitForElementPresent(By.xpath(name_title_xpath), "Cannot find title article " + name_title, 25);
    }

    public void checkAvailabilityTitle()//проверяем наличие элемента title
    {
        this.assertElementPresent(By.id(TITLE_ELEMENT), "Cannot find article title");
    }


}
