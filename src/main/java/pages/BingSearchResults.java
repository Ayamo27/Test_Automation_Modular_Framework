package pages;

import actions.UIActions;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import utils.ConfigReader;

import java.util.List;
/**
 * Represents the Bing Search Results page and provides actions to interact with it.
 * Uses UIActions for performing web element interactions.
 */
public class BingSearchResults {
    /**
     * Locator for the "Related searches for" section.
     * <p>
     * The value is read from the configuration file using {@link ConfigReader#getProperty(String)}.
     * Example XPath: "//h2[contains(text(),'Related searches for')]"
     */
    String relatedSearchSection = ConfigReader.getProperty("relatedSearchSection");

    /**
     * Locator for the "Next page" pagination icon.
     * <p>
     * The value is read from the configuration file using {@link ConfigReader#getProperty(String)}.
     * Example CSS selector: "a[title='Next page']"
     */
    String nextPageIcon = ConfigReader.getProperty("nextPageIcon");

    /**
     * Locator for search result items on the page.
     * <p>
     * The value is read from the configuration file using {@link ConfigReader#getProperty(String)}.
     * Example XPath: "//ol[@id='b_results']//cite"
     */
    String searchResultsLocator = ConfigReader.getProperty("searchResultsLocator");

    /**
     * Locator for related search items under the "Related searches for" section.
     * <p>
     * The value is read from the configuration file using {@link ConfigReader#getProperty(String)}.
     * Example XPath: "//h2[contains(text(),'Related searches for')]/following-sibling::ul//li"
     */
    String relatedSearchItemsLocator = ConfigReader.getProperty("relatedSearchItems");


    /** UIActions instance to perform element interactions. */
    UIActions uiActions;

    /** Default constructor that initializes the UIActions instance. */
    public BingSearchResults(){uiActions=new UIActions();}

    /**
     * Returns the number of "Related searches" sections found on the page.
     *
     * @return number of related search sections
     */
    public int getRelatedSearchesSections(){
        List<WebElement> sections=uiActions.findAllElements(UIActions.LocatorType.xpath,relatedSearchSection, UIActions.ExplicitWaitCondition.visibilityOfElement);
        return sections.size();

    }

    /**
     * Scrolls the page to the pagination section where the "Next page" icon is located.
     */
    public void scrollToPaginationSection(){
        uiActions.executeJavaScriptCode(UIActions.LocatorType.css,nextPageIcon, UIActions.ExplicitWaitCondition.elementToBeClickable,"arguments[0].scrollIntoView(true);");
    }
    /**
     * Clicks on the "Next page" icon to navigate to the next page of search results.
     */
    public void clickOnNextPage(){
        uiActions.click(UIActions.LocatorType.css,nextPageIcon, UIActions.ExplicitWaitCondition.elementToBeClickable);
    }

    /**
     * Returns the number of search results displayed on the current page.
     *
     * @return number of search results
     */
    public int getNumberOfSearchResultsInThePage(){
        List<WebElement> results=uiActions.findAllElements(UIActions.LocatorType.xpath,searchResultsLocator, UIActions.ExplicitWaitCondition.visibilityOfElement);
        return results.size();
    }
    /**
     * Checks if all related search items contain the specified text.
     *
     * @param expectedText the text expected to be present in all related search items
     * @return {@code true} if all related search items contain the expected text, {@code false} otherwise
     */
    public boolean doAllRelatedSearchItemsContainText(String expectedText) {
        return uiActions.areElementsContainText(
                UIActions.LocatorType.xpath,
                relatedSearchItemsLocator,
                UIActions.ExplicitWaitCondition.visibilityOfElement,
                expectedText
        );
    }
}
