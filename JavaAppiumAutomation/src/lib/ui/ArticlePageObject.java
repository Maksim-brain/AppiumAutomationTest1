package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class ArticlePageObject extends MainPageObject {

    private static final String
    SAVE_ARTICLE = "//android.widget.ImageView[@content-desc='Add this article to a reading list']",
    ADD_TO_MY_LIST_OVERLAY = "org.wikipedia:id/onboarding_button",
    MY_LIST_NAME_INPUT = "org.wikipedia:id/text_input",
    MY_LIST_OK_BUTTON = "//*[@text='OK']",
    CLOSE_ARTICLE = "//android.widget.ImageButton[@content-desc='Navigate up']";


    public ArticlePageObject(AppiumDriver driver)
    {
        super(driver);
    }

    public void clickSaveArticle()
    {
        this.waitForElementAndClick( // нажимаем на сохранение статьи
                By.xpath(SAVE_ARTICLE),
                "Cannot find option to add article to reading list",
                25
        );
    }

    public void addToMyListOverlay()
    {

        this.waitForElementAndClick( // находим статью и переходим
                By.id(ADD_TO_MY_LIST_OVERLAY),
                "Cannot find 'Got it' tip overlay",
                25
        );
    }

    public void clearListName() {

        this.waitForElementAndClear(  //очищаем поле сохраненной статьи
                By.id(MY_LIST_NAME_INPUT),
                "Cannot find input to set name of articles folder",
                25
        );
    }

    public void addArticleToMyList(String search_line)
    {
        this.waitForElementAndSendKeys(  // вставляем слово сохр статьи
                    By.id(MY_LIST_NAME_INPUT),
                    search_line,
                    "Cannot put text into article folder input",
                    25
        );
    }

    public void clickOkSaveMyList()
    {
        this.waitForElementAndClick( // нажимаем на кнопку опции
                By.xpath(MY_LIST_OK_BUTTON),
                "Cannot press OK button",
                5
        );
    }

    public void closeArticle()
    {
        this.waitForElementAndClick( // закрываем статью
                By.xpath(CLOSE_ARTICLE),
                "Cannot close article, cannot find X link",
                5
        );
    }

    public void saveArticleInExistingFolder(String search_line)
    {
        this.waitForElementAndClick( // нажимаем на сохранение статьи ??
                By.xpath(("//*[@resource-id='org.wikipedia:id/item_title'][@text='" + search_line + "']")),
                "Cannot find option to add article to reading list",
                10
        );
    }


}
