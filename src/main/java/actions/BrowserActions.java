package actions;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import utils.ConfigReader;

import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Utility class for managing WebDriver instances and browser actions.
 * Supports multiple browsers (Chrome, Firefox, Edge) with ThreadLocal
 * to allow parallel execution in multi-threaded tests.
 */

public class BrowserActions {
    private static ThreadLocal<WebDriver> drivers = new ThreadLocal<>();
    private static final Logger logger = Logger.getLogger(BrowserActions.class.getName());

    //Returns the WebDriver instance associated with the current thread.
    /**
     * Returns the WebDriver instance associated with the current thread.
     *
     * @return the WebDriver for the current thread, or null if not initialized
     */
    public static WebDriver getDriver() {
        return drivers.get();
    }
//Navigation:
    // initializing WebDriver instance based on specific browser type
    /**
     * Supported browser types.
     */
    public enum Browsers {
        CHROME,
        FIREFOX,
        EDGE
    }

    /**
     * Initializes the WebDriver for the specified browser.
     * If a driver already exists, it will be quit before initialization.
     *
     * @param browser the browser type to initialize
     * @throws RuntimeException if the driver cannot be initialized
     */

    public static void setWebDriver(Browsers browser) {
        if (getDriver() != null) {
            quitDriver();
        }
        try {
            switch (browser) {
                case CHROME:
                    ChromeOptions chromeOptions = new ChromeOptions();
                    String chromeArgs = ConfigReader.getProperty("chrome.options");
                    if (chromeArgs != null && !chromeArgs.isEmpty()) {
                        chromeOptions.addArguments(chromeArgs.split(","));
                    }
                    drivers.set(WebDriverManager.chromedriver().capabilities(chromeOptions).create());
                    logger.info(" Chrome WebDriver initialized.");
                    break;

                case FIREFOX:
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    String firefoxArgs = ConfigReader.getProperty("firefox.options");
                    if (firefoxArgs != null && !firefoxArgs.isEmpty()) {
                        firefoxOptions.addArguments(firefoxArgs.split(","));
                    }
                    drivers.set(WebDriverManager.firefoxdriver().capabilities(firefoxOptions).create());
                    logger.info("Firefox WebDriver initialized.");
                    break;

                case EDGE:
                    EdgeOptions edgeOptions = new EdgeOptions();
                    String edgeArgs = ConfigReader.getProperty("edge.options");
                    if (edgeArgs != null && !edgeArgs.isEmpty()) {
                        edgeOptions.addArguments(edgeArgs.split(","));
                    }
                    drivers.set(WebDriverManager.edgedriver().capabilities(edgeOptions).create());
                    logger.info("Edge WebDriver initialized.");
                    break;
                default:
                    throw new IllegalArgumentException("Browser not supported: " + browser);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to initialize WebDriver for " + browser, e);
            throw new RuntimeException("Failed to initialize WebDriver for " + browser, e);
        }
    }

 // Quits the WebDriver for the current thread(close all open tabs/windows in current session and kill driver and browser
// Cleans up the ThreadLocal storage
    /**
     * Quits the WebDriver for the current thread, closes all open tabs/windows,
     * and removes the driver from ThreadLocal storage.
     */
    public static void quitDriver(){
        checkDriverExistence().quit();
        drivers.remove();
        logger.info("WebDriver quit and ThreadLocal cleaned.");
    }

   //Helper function to check if the driver is not exist
    /**
     * Checks if the WebDriver has been initialized for the current thread.
     *
     * @return the current thread's WebDriver
     * @throws IllegalStateException if the WebDriver has not been initialized
     */
    private static WebDriver checkDriverExistence() {
        WebDriver driver = getDriver();
        if (driver == null) {
            throw new IllegalStateException("WebDriver has not been initialized for this thread.");
        }
        return driver;
    }
}

