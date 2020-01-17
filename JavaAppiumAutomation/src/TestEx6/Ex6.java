package TestEx6;

import com.sun.tools.javac.util.List;
import io.appium.java_client.AppiumDriver;
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

public class Ex6 {

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
    public void testFindTitleInArticle() {
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

        waitForElementAndClick( // находим статью
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find topic searching by " + search_line,
                10
        );

        assertElementPresent( //проверяем title
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title "
        );
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

    private void assertElementPresent(By by, String error_message)
    {
        try {
            WebElement element = driver.findElement(by);
        }
        catch (org.openqa.selenium.NoSuchElementException exception){
            throw  new AssertionError("element" + by.toString() + " not found element on page");
        }
    }
}





