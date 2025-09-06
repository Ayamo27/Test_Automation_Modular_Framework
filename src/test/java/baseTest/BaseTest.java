package baseTest;

import actions.BrowserActions;
import org.testng.annotations.*;
import utils.ConfigReader;
/**
 * BaseTest class that sets up and tears down WebDriver instances for tests.
 * All test classes should extend this class to inherit browser initialization and cleanup.
 */
public class BaseTest {

    /**
     * Initializes the WebDriver before running any tests in the class.
     * <p>
     * It determines the browser to use in the following order:
     * <ol>
     *     <li>System property "browser"</li>
     *     <li>TestNG parameter "browser"</li>
     *     <li>Default browser from config.properties</li>
     * </ol>
     *
     * @param browserParam optional TestNG parameter specifying the browser to use
     */
     @BeforeClass
     @Parameters({"browser"})
         public void setup(@Optional("") String browserParam) {
             String browserName = System.getProperty("browser");

             if (browserName == null || browserName.isEmpty()) {
                 browserName = browserParam;
             }

             if (browserName == null || browserName.isEmpty()) {
                 browserName = ConfigReader.getProperty("default.browser");
             }

             BrowserActions.setWebDriver(BrowserActions.Browsers.valueOf(browserName.toUpperCase()));
         }
    /**
     * Quits the WebDriver after all tests in the class have finished.
     *
     * @throws InterruptedException if thread interruption occurs while quitting the driver
     */
    @AfterClass
    public void tierDown() throws InterruptedException {
        BrowserActions.quitDriver();
    }

}
