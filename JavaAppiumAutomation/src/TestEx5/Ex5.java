package TestEx5;

import com.sun.tools.javac.util.List;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.ArrayList;

public class Ex5 {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","AndroidTestDevice");
        capabilities.setCapability("platformVersion","6.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","/Users/romanvoblik/Desktop/Tests/JavaAppiumAutomation/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);


        /*waitForElementAndClick(  // пропускаем страницу с настройками
                By.xpath("//*[contains(@text,'Skip')]"),
                "Cannot find Skip input",
                10
        );*/
    }
    @After
    public void tearDown()
    {

        driver.quit();
    }

    @Test
    public void testSaveTwoItems()
    {
        waitForElementAndClick( // вызываем поиск
            By.id("org.wikipedia:id/search_container"),
            "Cannot find search 'Search Wikipedia' input",
            10
    );

        String search_line = "Java";
        waitForElementAndSendKeys(  // вставляем слово для поиска
                By.id("org.wikipedia:id/search_src_text"),
                search_line,
                "Cannot find input",
                10
        );

        waitForElementAndClick( // находим статью и переходим
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find topic searching by " + search_line,
                20
        );

        waitForElementAndClick( // нажимаем на сохранение статьи
                By.xpath("//android.widget.ImageView[@content-desc='Add this article to a reading list']"),
                "Cannot find option to add article to reading list",
                25
        );

        waitForElementAndClick( // находим статью и переходим
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                25
        );

        waitForElementAndClear(  //очищаем поле сохраненной статьи
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of articles folder",
                25
        );
     waitForElementAndSendKeys(  // вставляем слово сохр статьи
                By.id("org.wikipedia:id/text_input"),
                search_line,
                "Cannot put text into article folder input",
                25
        );

        waitForElementAndClick( // нажимаем на кнопку опции
                By.xpath("//*[@text='OK']"),
                "Cannot press OK button",
                5
        );

        waitForElementAndClick( // закрываем статью
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5
        );

        waitForElementAndClick( // вызываем поиск
                By.id("org.wikipedia:id/search_container"),
                "Cannot find search 'Search Wikipedia' input",
                10
        );

        waitForElementAndSendKeys(  // вставляем слово для поиска
                By.id("org.wikipedia:id/search_src_text"),
                search_line,
                "Cannot find input",
                10
        );

        waitForElementAndClick( // находим статью и переходим
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description'][@text='Island of Indonesia']"),
                "Cannot find new item",
                10
        );

        waitForElementAndClick( // нажимаем на сохранение статьи
                By.xpath("//android.widget.ImageView[@content-desc='Add this article to a reading list']"),
                "Cannot find option to add article to reading list",
                10
        );

        waitForElementAndClick( // нажимаем на сохранение статьи ??
                By.xpath(("//*[@resource-id='org.wikipedia:id/item_title'][@text='"+search_line+"']")),
                "Cannot find option to add article to reading list",
                10
        );

        waitForElementAndClick( // закрываем статью
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                10
        );

        waitForElementAndClick( // переходим в my lists
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to My lists",
                10
        );

        waitForElementAndClick( // нажимаем в саму папку
                By.id("org.wikipedia:id/item_title"),
                "Cannot find created folder Java",
                10
        );


        swipeElementToLeft( // удаляем одну статью
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description'][@text='island of Indonesia']"),
                "Cannot find saved article"
        );

        waitForElementNotPresent( // проверяем что есть вторая
                By.xpath("//*[@text='Object-oriented programming language']"),
                "Cannot find remaining article",
                10
        );

        waitForElementAndClick( // нажимаем на остающуюся статью
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description'][@text='object-oriented programming language']"),
                "Cannot click created folder Java",
                10
        );

        waitForElementPresent( // проверяем что title совпадает
                By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text'][@text='Java (programming language)']"),
                "Cannot find title article",
                10
        );


    }



    protected void swipeElementToLeft(By by, String error_message)
    {
        WebElement element = waitForElementPresent(
                by,
                error_message,
                10);

        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middel_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(right_x, middel_y)
                .waitAction(300)
                .moveTo(left_x, middel_y)
                .release()
                .perform();
    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;

    }


    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

}





