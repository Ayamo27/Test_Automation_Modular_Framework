package bingTests;

import actions.BrowserActions;
import io.qameta.allure.Allure;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import utils.ScreenShot;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
/**
 * TestNG listener that captures screenshots on test failure
 * and attaches them to Allure reports and TestNG HTML reports.
 */

public class ScreenshotListener implements ITestListener {


    /**
     * Called when a test fails.
     * Captures a screenshot, attaches it to Allure, and logs it in TestNG reporter.
     *
     * @param result the result of the failed test
     */
    @Override
    public void onTestFailure(ITestResult result) {

        String testName = result.getName();

        String screenshotPath = ScreenShot.captureScreenshot(BrowserActions.getDriver(), testName);
        if (screenshotPath != null) {
            try {
                attachScreenshotToAllure(screenshotPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            logScreenshotToReporter(screenshotPath);

        }
    }
    /**
     * Attaches a screenshot file to the Allure report.
     *
     * @param screenshotPath relative path to the screenshot file
     * @throws IOException if the screenshot file cannot be read
     */

    public void attachScreenshotToAllure(String screenshotPath) throws IOException {

        Path fullPath = Paths.get(System.getProperty("user.dir")).resolve(screenshotPath);
        byte[] screenshotBytes = Files.readAllBytes(fullPath);
        Allure.addAttachment("Screenshot for failed test","image/png", new ByteArrayInputStream(screenshotBytes),".png");


    }
    /**
     * Logs the screenshot to TestNG reporter as an HTML <img> tag.
     *
     * @param screenshotPath relative path to the screenshot file
     */
    private void logScreenshotToReporter(String screenshotPath) {
        String relativePath = screenshotPath.replace("test-output/", "");
        String imgTag = "<a href='./" + relativePath + "' target='_blank'>"
                + "<img src='" + relativePath + "' height='400' width='300'/></a>";
        Reporter.log("Screenshot for failed test: <br>" + imgTag, true);
    }

}
