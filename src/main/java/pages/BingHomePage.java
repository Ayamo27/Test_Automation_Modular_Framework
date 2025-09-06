package pages;

import actions.UIActions;
import utils.ConfigReader;

/**
 * Represents the Bing Home Page and provides actions that can be performed on it.
 * Uses UIActions for interacting with page elements.
 */

public class BingHomePage {
    private String searchBarLocator= ConfigReader.getProperty("searchBarLocator");
    private String searchIcon=ConfigReader.getProperty("searchIcon");

    /** UIActions instance to perform web element interactions. */
    UIActions uiActions;

    /**
     * Default constructor that initializes the UIActions instance.
     */
    public BingHomePage(){ uiActions=new UIActions();}


    /**
     * Navigates to the Bing home page using the specified URL.
     *
     * @param url the Bing home page URL
     */
    public void navigateToBingHomePage(String url){
        uiActions.navigateToPage(url);
    }

    /**
     * Performs a search on the Bing home page for the given search term.
     * Clears the search bar, enters the search word, and submits the search.
     *
     * @param searchWord the search term to enter
     * @return a new BingSearchResults instance representing the search results page
     */
    public BingSearchResults searchInBing(String searchWord)  {

        uiActions.clearText(UIActions.LocatorType.css,searchBarLocator, UIActions.ExplicitWaitCondition.visibilityOfElement);

        uiActions.sendKeys(UIActions.LocatorType.css,searchBarLocator, UIActions.ExplicitWaitCondition.visibilityOfElement,searchWord);

        uiActions.submit(UIActions.LocatorType.xpath,searchIcon, UIActions.ExplicitWaitCondition.elementToBeClickable);

        return new BingSearchResults();
    }

}
