package com.ui.base;

import com.google.gson.JsonObject;
import lib.constants.Constants;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;

import java.util.HashMap;
import static java.util.concurrent.TimeUnit.MILLISECONDS;



public class TestBase {

    public static WebDriver driver;

    public HashMap<String,Object> getConfigDetails(){
        HashMap<String,Object> config = new HashMap<>();
        config.put(Constants.OS_NAME,System.getProperty("os.name").toLowerCase());
        config.put("browser",System.getProperty(Constants.BROWSER));
        return config;
    }

    public  WebDriver initialize(){

       if(!getConfigDetails().isEmpty()){
           if(getConfigDetails().containsValue(Constants.MAC)){

               switch(getConfigDetails().get(Constants.BROWSER).toString()){

                   case "FF":      System.setProperty("webdriver.gecko.driver", "src/test/resources/mac-driver/geckodriver");
                       driver = new FirefoxDriver();
                       break;
                   case  "Chrome":

                   default:       System.setProperty("webdriver.chrome.driver", "src/test/resources/mac-driver/chromedriver");
                       driver = new ChromeDriver();
                       break;
               }

           }
           else if(getConfigDetails().containsValue(Constants.WINDOWS))

           {
               switch(getConfigDetails().get(Constants.BROWSER).toString()){
                   case "FF":      System.setProperty("webdriver.gecko.driver", "src/test/resources/windows-driver/geckodriver.exe");
                   driver = new FirefoxDriver();
                   break;
               case  "Chrome":

               default:       System.setProperty("webdriver.chrome.driver", "src/test/resources/windows-driver/chromedriver.exe");
                   driver = new ChromeDriver();
                   break;
           }
           }
       }



        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(2000, MILLISECONDS);
        driver.get(Constants.PAGE_URL);
        return driver;
    }


    public ITestContext getiTextContextAttribs(ITestContext testContext, JsonObject testDataObj) {
        testContext.setAttribute("PAGE_URL",Constants.PAGE_URL);
        testContext.setAttribute("TEST-DATA",testDataObj);

        return testContext;
    }

    public void waitForElementVisibility(WebElement element){

        WebDriverWait wait=new WebDriverWait(driver,50);
        wait.until(ExpectedConditions.visibilityOf(element));


    }
}
