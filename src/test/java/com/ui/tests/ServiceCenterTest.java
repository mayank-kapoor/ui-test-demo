package com.ui.tests;

import com.google.gson.JsonObject;
import com.ui.base.TestBase;
import com.ui.pages.HomePage;
import com.ui.pages.SearchPage;
import core.utils.ResourceLoader;
import core.utils.ScreenShot;
import lib.annotation.FileToTest;
import lib.constants.Constants;
import org.testng.ITestContext;
import org.testng.annotations.*;


public class ServiceCenterTest extends TestBase {

    HomePage homePage;
    SearchPage searchPage;
    ScreenShot screenShot = new ScreenShot();

    @BeforeMethod(alwaysRun = true)
    public void init() {
        initialize();
        homePage = new HomePage(driver);
        searchPage = new SearchPage(driver);
        screenShot.initialize(driver);

    }

    @FileToTest("query.json")
    @Test(priority = 1, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void verifySearchWithQueryTerm(ITestContext testContext, JsonObject testDataObj) {
        String queryToTest = testDataObj.get(Constants.QUERY).getAsString();
        homePage.verifyHomePageTitle();
        homePage.searchForQueryTerm(queryToTest);
        searchPage.verifySearchTermIsPresentInSearchResults(queryToTest);
        testContext = getiTextContextAttribs(testContext, testDataObj);
    }

    @FileToTest("service-centre.json")
    @Test(priority = 2, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void verifySearchWithLocateUs(ITestContext testContext, JsonObject testDataObj) {
        homePage.clickOnLocateUs();
        searchPage.searchBySuburbOrLocation(testDataObj.get(Constants.SUBURB).getAsString());
        searchPage.assertForPresenceOfDesiredServiceCenter(testDataObj.get(Constants.SERVICE_CENTER_NAME).getAsString());
        testContext = getiTextContextAttribs(testContext, testDataObj);
    }

    @AfterMethod(alwaysRun = true)
    public void cleanup() {
        driver.navigate().refresh();
        driver.manage().deleteAllCookies();
        driver.quit();
    }

    @AfterClass(alwaysRun = true)
    public void quit() {
        if (driver != null)
            driver.quit();
    }

}
