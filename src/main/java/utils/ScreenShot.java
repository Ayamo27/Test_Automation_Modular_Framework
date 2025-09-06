package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Utility class for capturing screenshots during Selenium tests.
 * Screenshots are saved in the "test-output/screenshots/" directory with a timestamp.
 */
public class ScreenShot {


    /**
     * Captures a screenshot of the current browser window.
     *
     * @param driver the WebDriver instance used to take the screenshot
     * @param screenshotName a custom name for the screenshot file
     * @return the path of the saved screenshot, or {@code null} if saving failed
     */
    public static String captureScreenshot(WebDriver driver, String screenshotName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        String screenshotPath="test-output/screenshots/" + screenshotName + timestamp + ".png";
        File destination = new File(screenshotPath);

        try {
            Files.createDirectories(destination.getParentFile().toPath());
            Files.copy(source.toPath(), destination.toPath());

            System.out.println("Screenshot saved: " + destination.getAbsolutePath());
            return screenshotPath;
        } catch (IOException e) {
            System.out.println("Failed to save screenshot: " + e.getMessage());
            return null;
        }

    }


}
