package com.googlenews.pages;

import com.stonecress.core.UiBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class TopStoriesPage extends UiBase<TopStoriesPage> {
    @FindBy(xpath = "//a[text()='Headlines']")
    WebElement linkHeadlines;

    public TopStoriesPage() {
        setBaseUrl(getPropStr("googleNews.baseUrl") + "/topstories");
    }

    public TopStoriesPage verifyHeadlinesHasLink() {
        getWait().until(ExpectedConditions.visibilityOf(linkHeadlines));
        String href = linkHeadlines.getAttribute("href");
        getLogger().info(href);
        Assert.assertTrue(href.contains("/topics/"), "link has topics");
        return this;
    }
}
