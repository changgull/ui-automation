package com.stonecress.core.ui;

import com.stonecress.core.Base;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class UiBase<T> extends Base {
    private WebDriver driver;
    private String baseUrl;
    public static final int _pageAnimationMs = 1000;
    public static final int _javascriptMs = 200;
    public static final int _splashModal = 3000;

    protected void initDriver() {
        initChromeDriver();
    }

    protected void initChromeDriver() {
        setDriverPath();
        ChromeOptions options = new ChromeOptions().addArguments("no-sandbox", "window-size=1400,900");
        options.addArguments(Arrays.asList(getPropStr("browserOptions").split("[$#@]")));
        getLogger().info("browserOptions: " + options.toJson().toString());
        setDriver(new ChromeDriver(options));
        driver.manage().timeouts().implicitlyWait(getPropInt("uibase.implicitTimeoutSec"), TimeUnit.SECONDS);
    }

    void setDriverPath() {
        String osName = System.getProperty("os.name");
        getLogger().info(osName);

        if (osName.equals("Mac OS X")) {
            System.setProperty("webdriver.chrome.driver", "src/test/resources/webdriver/macos/chromedriver");
        } else if (osName.equals("Linux")) {
            System.setProperty("webdriver.chrome.driver", "src/test/resources/webdriver/linux/chromedriver");
        } else {
            Assert.fail("We didn't prepare for this operating system: " + osName);
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver givenDriver) {
        driver = givenDriver;
    }

    public T continueFrom(UiBase page) {
        setDriver(page.getDriver());
        PageFactory.initElements(getDriver(), this);
        return (T) this;
    }

    public T openFrom(UiBase page) {
        setDriver(page.getDriver());
        openPage();
        return (T) this;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String givenBaseUrl) {
        baseUrl = givenBaseUrl;
    }

    public T openPage() {
        return open(getBaseUrl());
    }

    public T open(String url) {
        if (getDriver() == null) initDriver();
        getLogger().info("Opening url: " + url);
        getDriver().get(url);
        PageFactory.initElements(getDriver(), this);
        return (T) this;
    }

    public T refreshPage() {
        getDriver().navigate().refresh();
        return (T) this;
    }

    public void close() {
        if (getDriver() != null) getDriver().quit();
    }

    public WebDriverWait getWait() {
        return new WebDriverWait(getDriver(), getPropInt("uibase.implicitTimeoutSec"));
    }

    public T sleep(int millsec) {
        try {
            Thread.sleep(millsec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return (T) this;
    }

    public boolean listHasItemWithText(List<WebElement> list, String expectedValue) {
        Boolean found = false;
        for (WebElement el : list) {
            if (el.getText().equalsIgnoreCase(expectedValue)) {
                getLogger().info("Item found: " + el.getText());
                found = true;
                break;
            }
        }
        return found;
    }

    public List<WebElement> findRowWithText(List<WebElement> rows, Integer column, String value) {
        for (WebElement el : rows) {
            if (el.findElement(By.xpath(String.format("td[%d]", column))).getText().equalsIgnoreCase(value)) {
                getLogger().info("Row found for: " + value);
                return el.findElements(By.tagName("td"));
            }
        }
        getLogger().info("Row not found for: " + value);
        return null;
    }

    public WebElement findItemWithText(List<WebElement> elements, By by, String value) {
        for (WebElement el : elements) {
            if (el.findElement(by).getText().equalsIgnoreCase(value)) {
                getLogger().info("Item found for: " + value);
                return el;
            }
        }
        getLogger().info("Item in list not found for: " + value);
        return null;
    }

    public boolean ifVisible(WebElement parent, By by) {
        boolean visible = false;
        getDriver().manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        visible = parent.findElements(by).size() > 0;
        getDriver().manage().timeouts().implicitlyWait(getPropInt("uibase.implicitTimeoutSec"), TimeUnit.SECONDS);
        return visible;
    }

    public T takeScreenshot() {
        try {
            String filename = "target/screenshots/" + getClass().getName() + "-" + randomString() + ".png";
            File screenshotFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshotFile, new File(filename));
            getLogger().info("Screenshot saved to: " + filename);
        } catch (IOException e) {
            getLogger().info("hey, failed to save screenshot");
            e.printStackTrace();
        }
        return (T) this;
    }
}
