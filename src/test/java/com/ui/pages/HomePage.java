package com.ui.pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    private WebDriver driver;


    @FindBy(xpath = "//input[@id='edit-contains']")
    private WebElement searchInputBox;

    @FindBy(xpath = "//input[@id='edit-submit-site-search']")
    private WebElement searchButton;

    @FindBy(xpath = "//a[contains(text(),'Locate us')]")
    private WebElement locateUS;


    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public String verifyHomePageTitle(){
        return driver.getTitle();
    }

    public void searchForQueryTerm(String searchQuery){
        searchInputBox.sendKeys(searchQuery);
        searchButton.click();
    }

    public void clickOnLocateUs(){

        locateUS.click();
    }


}
