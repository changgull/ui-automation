package com.googlenews;

import com.googlenews.pages.TopStoriesPage;
import com.stonecress.core.BaseTest;
import com.stonecress.core.UiBase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test(groups = "ui")
public class GoogleNewsSmokeTest extends BaseTest {
    TopStoriesPage page;

    @BeforeClass(alwaysRun = true)
    void initPageInstance() {
        page = new TopStoriesPage();
    }

    @Test
    void verifyGoogleHeadlinesLink() {
        page.openPage().verifyHeadlinesHasLink();
    }

    @AfterClass(alwaysRun = true)
    void closePage() {
        page.close();
    }
}
