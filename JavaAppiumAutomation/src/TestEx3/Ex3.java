package TestEx3;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class Ex3 {

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
    public void testEx3()
    {
        waitForElementAndClick( // вызываем поиск
                By.id("org.wikipedia:id/search_container"),
                "Cannot find search 'Search Wikipedia' input",
                10
        );

        waitForElementAndSendKeys(  // вставляем слово для поиска
                By.id("org.wikipedia:id/search_src_text"),
                "Apple",
                "Cannot find input",
                10
        );

        WebElement word1 = waitForElementPresent(   // ищем фразу при поиске
                By.xpath("//*[contains(@text,'Home media receiver device made by Apple')]"),
                "Cannot find 'Home media receiver device made by Apple' topic searching by 'Apple'",
                20
        );

        String expectedWord1 = word1.getAttribute("text");
        Assert.assertEquals(
                "unexpected word1",
                "Home media receiver device made by Apple",
                expectedWord1
        );
        WebElement word2 = waitForElementPresent(   // ищем фразу при поиске
                By.xpath("//*[contains(@text,'Apple Watch')]"),
                "Cannot find 'Apple Watch' topic searching by 'Apple'",
                20
        );

        String expectedWord2 = word2.getAttribute("text");
        Assert.assertEquals(
                "unexpected word1",
                "Apple Watch",
                expectedWord2
        );

        waitForElementAndClear(  //очищаем поле поиска
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field",
                10
        );

        /*WebElement word2 = waitForElementNotPresent(   // проверяем результаты пропали, поиск пропал
                By.xpath("//*[contains(@text, 'Apple')]"),
                "Cannot find input",
                10
        );

        String expectedWord2 = word2.getAttribute("text");
        Assert.assertEquals(
                "unexpected word2",
                "Apple",
                expectedWord2
        );*/

        waitForElementAndClick(    // кликаем на крестик
              By.id("org.wikipedia:id/search_close_btn"),
            "Cannot find X to cancel search",
          10
         );

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "button X is still on the page",
                10
        );

    }

    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds)
    {
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
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;

    }
}
