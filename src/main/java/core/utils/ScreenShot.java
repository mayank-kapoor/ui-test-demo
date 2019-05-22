package core.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenShot {

    private static WebDriver webdriver;

    public void initialize(WebDriver driver){

    this.webdriver = driver;
    }

    public static String getScreenShot(String testName ) throws IOException {

        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot screenshot= (TakesScreenshot)webdriver;
        File source= screenshot.getScreenshotAs(OutputType.FILE);
        String destination="./target/screenshots/"+testName+"_"+dateName+".png";
        File dest=new File(destination);
        try {
            FileUtils.copyFile(source, dest);
        }
        catch(IOException e)
        {
            System.out.println("Getting IO-Exception while copying Files"+e.getMessage());
        }
        return destination;
    }
}