package core.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import core.utils.ScreenShot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;

public class ExtentTestNGITestListener implements ITestListener {

    private static ExtentReports extent = ExtentManager.createInstance("extent.html");
    private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal();
    ExtentTest parent, subChild;

    ITestContext context;

    @Override
    public synchronized void onStart(ITestContext context) {
        this.context = context;
        parent = extent.createTest(context.getName());
        parentTest.set(parent);

    }

    @Override
    public synchronized void onFinish(ITestContext context) {
        extent.flush();
    }

    @Override
    public synchronized void onTestStart(ITestResult result) {

    }

    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        ExtentTest child = parentTest.get().createNode(getTestName(result), result.getMethod().getDescription());
        test.set(child);
        test.get().log(Status.PASS,result.getTestName());
        try {
            appendTestInfoInReport(Status.PASS,result);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public synchronized void onTestFailure(ITestResult result) {
        ExtentTest child = parentTest.get().createNode(getTestName(result), result.getMethod().getDescription());
        test.set(child);
        try {
            appendTestInfoInReport(Status.FAIL, result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void onTestSkipped(ITestResult result) {
        test.get().skip(result.getThrowable());
    }

    @Override
    public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    private synchronized String getTestName(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        return testName;

    }


    private synchronized void appendTestInfoInReport(Status testStatus, ITestResult iTestResult) throws IOException {
        test.get().log(testStatus, iTestResult.getTestContext().getAttribute("PAGE_URL").toString());
        test.get().log(testStatus,iTestResult.getTestContext().getAttribute("TEST-DATA").toString());

        String destinationPath = ScreenShot.getScreenShot(iTestResult.getMethod().getMethodName());

        if (destinationPath != null) {
            test.get().addScreenCaptureFromPath(new File(destinationPath).getPath());
        }
        if (testStatus.equals(Status.FAIL))
            test.get().log(testStatus, "Failure Reason : " + iTestResult.getThrowable().getMessage());
        if (testStatus.equals(Status.SKIP))
            test.get().log(testStatus, "Skipped Reason: " + iTestResult.getThrowable().getMessage());
        }

}
