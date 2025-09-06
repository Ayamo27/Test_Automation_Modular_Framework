package actions;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Utility class providing common UI interactions with Selenium WebDriver.
 * Includes navigation, element interaction (click, send keys, clear text, etc.),
 * JavaScript execution, and element finding with explicit waits.
 * <p>
 * Each method uses logging and exception handling to ensure test failures
 * are properly reported instead of silently passing.
 * </p>
 */
public class UIActions {

    private final Logger logger = Logger.getLogger(UIActions.class.getName());
    private final int waitDuration; // Instance variable بدل static
    private final WebDriver driver;


    public UIActions() {
        this.driver = BrowserActions.getDriver();
        if (this.driver == null) {
            throw new IllegalStateException("WebDriver not initialized in BrowserActions");
        }
        this.waitDuration = 30; // default wait duration
    }


    // Navigation

    /**
     * Navigates to a given URL.
     *
     * @param url the URL to open
     * @throws RuntimeException if navigation fails
     */
    public void navigateToPage(String url) {
        try {
            driver.navigate().to(url);
            logger.info("Navigated to page: " + url);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to navigate to page: " + url, e);
            throw e;
        }
    }

    // basic Element interactions


    /**
     * Clicks on an element located by the given locator.
     *
     * @param locator   type of locator (e.g., id, xpath, css)
     * @param selector  the actual locator string
     * @param condition explicit wait condition before clicking
     * @throws RuntimeException if the element cannot be clicked
     */
    public void click(LocatorType locator, String selector, ExplicitWaitCondition condition) {
        try {
            WebElement element = waitToFindElement(locator, selector, condition);
            element.click();
            logger.info("Clicked element: " + selector);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to click element: " + selector, e);
            throw e;
        }
    }

    /**
     * Submits a form element located by the given locator.
     *
     * @param locator   type of locator (e.g., id, xpath, css)
     * @param selector  the actual locator string
     * @param condition explicit wait condition before submitting
     * @throws RuntimeException if the element cannot be submitted
     */
    public void submit(LocatorType locator, String selector, ExplicitWaitCondition condition) {
        try {
            WebElement element = waitToFindElement(locator, selector, condition);
            element.submit();
            logger.info("Submitted element" );
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to submit element: " + selector, e);
            throw e;
        }
    }

    /**
     * Sends keys (text) to an input element located by the given locator.
     *
     * @param locator   type of locator (like id, xpath, css...)
     * @param selector  the actual locator string
     * @param condition explicit wait condition before sending keys
     * @param text      the text to send
     * @throws RuntimeException if sending keys fails
     */
    public void sendKeys(LocatorType locator, String selector, ExplicitWaitCondition condition, String text) {
        try {
            WebElement element = waitToFindElement(locator, selector, condition);
            element.sendKeys(text);
            logger.info("Sent keys"+text+" to element ");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to send keys to element: " + selector, e);
            throw e;
        }
    }


    /**
     * Clears the text inside an input element.
     *
     * @param locator   type of locator (like id, xpath, css...)
     * @param selector  the actual locator string
     * @param condition explicit wait condition before clearing
     * @throws RuntimeException if clearing text fails
     */
    public void clearText(LocatorType locator, String selector, ExplicitWaitCondition condition) {
        try {
            WebElement element = waitToFindElement(locator, selector, condition);
            element.clear();
            logger.info("Cleared text " );
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to clear text in element: " + selector,  e);
            throw e;
        }
    }

    /**
     * Checks whether all elements located by the given locator and selector
     * contain the specified expected text (case-insensitive).
     * @param locator       the type of locator to use ( like: id, xpath...)
     * @param selector      the actual locator string used to find elements
     * @param condition     the explicit wait condition to apply before locating elements
     * @param expectedText  the text expected to be found in each element's text
     * @return {@code true} if all elements contain the expected text, otherwise {@code false}
     */
    public boolean areElementsContainText(LocatorType locator, String selector, ExplicitWaitCondition condition, String expectedText) {
        List<WebElement> elements = findAllElements(locator, selector, condition);
        if (elements.isEmpty()) return false;
        for (WebElement e : elements) {
            if (!e.getText().toLowerCase().contains(expectedText.toLowerCase())) return false;
        }
        logger.info("All elements contain the expected text: " + expectedText);
        return true;
    }

    /**
     * Executes a given JavaScript code on an element.
     *
     * @param locator   type of locator (like id, xpath, css...)
     * @param selector  the actual locator string
     * @param condition explicit wait condition before executing script
     * @param javaScript the JavaScript code to execute
     * @throws RuntimeException if execution fails
     */
    public void executeJavaScriptCode(LocatorType locator, String selector, ExplicitWaitCondition condition, String javaScript) {
        try {
            WebElement element = waitToFindElement(locator, selector, condition);
            ((JavascriptExecutor) driver).executeScript(javaScript, element);
            logger.info("Executed JavaScript on element: " + selector + " -> " + javaScript);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to execute JavaScript on element: " + selector, e);
            throw e;
        }
    }


    // Find elements

    /**
     * Finds all elements matching the given locator and condition.
     *
     * @param locator   type of locator (like id, xpath, css...)
     * @param selector  the actual locator string
     * @param condition explicit wait condition
     * @return a list of matching elements
     * @throws RuntimeException if no elements found or wait fails
     */
    public List<WebElement> findAllElements(LocatorType locator, String selector, ExplicitWaitCondition condition) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitDuration));
            switch (condition) {
                case presenceOfElement:
                    return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locateElement(locator, selector)));
                case visibilityOfElement:
                    return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locateElement(locator, selector)));
                default:
                    throw new IllegalArgumentException("Unsupported wait condition: " + condition);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to find elements : " + selector,   e);
            throw e;
        }
    }
    /**
     * Waits until a single element is located with the given condition.
     *
     * @param locator   type of locator (like id, xpath, css...)
     * @param selector  the actual locator string
     * @param condition explicit wait condition
     * @return the located WebElement
     * @throws RuntimeException if element is not found
     */
    private WebElement waitToFindElement(LocatorType locator, String selector, ExplicitWaitCondition condition) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitDuration));
        switch (condition) {
            case presenceOfElement:
                return wait.until(ExpectedConditions.presenceOfElementLocated(locateElement(locator, selector)));
            case elementToBeClickable:
                return wait.until(ExpectedConditions.elementToBeClickable(locateElement(locator, selector)));
            case visibilityOfElement:
                return wait.until(ExpectedConditions.visibilityOfElementLocated(locateElement(locator, selector)));
            default:
                throw new IllegalArgumentException("Unsupported wait condition: " + condition);
        }
    }
    /**
     * Converts a {@link LocatorType} and selector string into a Selenium {@link By} object.
     *
     * @param locator  type of locator
     * @param selector the actual locator string
     * @return a {@link By} instance
     * @throws IllegalArgumentException if the locator type is unsupported
     */
    private By locateElement(LocatorType locator, String selector) {
        switch (locator) {
            case id: return By.id(selector);
            case css: return By.cssSelector(selector);
            case xpath: return By.xpath(selector);
            case tagname: return By.tagName(selector);
            case linktext: return By.linkText(selector);
            case classname: return By.className(selector);
            case name: return By.name(selector);
            case partialLinkText: return By.partialLinkText(selector);
            default: throw new IllegalArgumentException("Unsupported LocatorType: " + locator);
        }
    }

    // Enums
    /**
     * Supported explicit wait conditions for element interactions.
     */
    public enum ExplicitWaitCondition {
        elementToBeClickable,
        presenceOfElement,
        visibilityOfElement
    }

    /**
     * Supported locator strategies for finding elements.
     */
    public enum LocatorType {
        xpath,
        id,
        classname,
        name,
        css,
        tagname,
        linktext,
        partialLinkText
    }




}