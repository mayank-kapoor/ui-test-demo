package com.ui.pages;

import com.ui.base.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertTrue;

import java.util.List;

public class SearchPage extends TestBase {

    private WebDriver driver;


    @FindBy(xpath = "//div[@class='view-content']//a")
    private List<WebElement> searchResultList;

    @FindBy(xpath = "//a[contains(text(),'Apply for a number plate')]")
    private WebElement searchQueryTerm;


    @FindBy(xpath = "//input[@id='locatorTextSearch']")
    private WebElement searchByLocation;


    @FindBy(xpath = "//button[@class='button button--primary button--xxlarge']")
    private WebElement searchButton;


    @FindBy(xpath = "//div[@class='locator__results-list']//a")
    private List<WebElement> serviceCenterList;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void verifySearchTermIsPresentInSearchResults(String searchQuery) {

        if (!searchResultList.isEmpty())

            assertThat("query term is not present in search results", searchResultList.get(0).getText(), is(equalTo(searchQuery)));

    }

    public void searchBySuburbOrLocation(String location){
        waitForElementVisibility(searchByLocation);
        searchByLocation.sendKeys(location);
        searchButton.click();
    }
    public void assertForPresenceOfDesiredServiceCenter(String serviceCenterName) {
        waitForElementVisibility(searchByLocation);
        if (!serviceCenterList.isEmpty())
            for(WebElement serviceCenter:serviceCenterList)
                if(serviceCenter.getText().equalsIgnoreCase(serviceCenterName))
                    Assert.assertTrue(true,"service center exists in search results");

    }


}
