package bingTests;
import baseTest.BaseTest;
import dataDriven.JsonDataReader;
import io.qameta.allure.Allure;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.BingHomePage;
import pages.BingSearchResults;

import java.io.IOException;
/**
 * Test class for Bing search functionality.
 * Extends {@link BaseTest} to inherit WebDriver setup and teardown.
 */
public class BingTests extends BaseTest {
    /**
     * Verifies Bing search results using test data from "testData.json".
     * <p>
     * Test steps include:
     * <ul>
     *     <li>Navigate to Bing home page.</li>
     *     <li>Perform a search with the specified keyword.</li>
     *     <li>Verify the number of "Related searches" sections.</li>
     *     <li>Check that all related search items contain the expected text.</li>
     *     <li>Navigate through the first three result pages and verify the number of results is consistent.</li>
     * </ul>
     *
     * @throws IOException if there is an error reading the test data JSON file
     */
    @Test
    public void verifyBingTestResults() throws IOException {
//Get test data from testData.json file
        String BingUrl=JsonDataReader.getJsonData("testData","bingUrl").getAsString();
        String searchWord= JsonDataReader.getJsonData("testData","searchWord").getAsString();
        int expectedRelatedSearchesSectionNumber=JsonDataReader.getJsonData("testData","expectedRelatedSearches").getAsInt();
        String expectedTextInRelatedSearches = JsonDataReader.getJsonData("testData","expectedTextInRelatedSearches").getAsString();
// Test Steps
        BingHomePage bingHomePage=new BingHomePage();
        BingSearchResults bingSearchResults;
        SoftAssert softAssert=new SoftAssert();
        Allure.step("navigate to bing url");
        bingHomePage.navigateToBingHomePage(BingUrl);
        Allure.step("search for "+searchWord);
        bingSearchResults=bingHomePage.searchInBing(searchWord);
        int actualRelatedSearchesSectionNumber=bingSearchResults.getRelatedSearchesSections();
        boolean allContainVodafone = bingSearchResults.doAllRelatedSearchItemsContainText(expectedTextInRelatedSearches);
        Allure.step("Validate that the first results page contains 2 'Related searches for'"+searchWord+"sections, and that both of these sections contain items underneath that have the word"+searchWord+"in them");
        softAssert.assertTrue(allContainVodafone, "Bug: Not all related search items contain '" + expectedTextInRelatedSearches + "'");
        System.out.println("the expected sections: "+expectedRelatedSearchesSectionNumber);
        System.out.println("the actual sections: "+actualRelatedSearchesSectionNumber);
        softAssert.assertEquals(actualRelatedSearchesSectionNumber,expectedRelatedSearchesSectionNumber,"Bug: the expected number of section 'Related searches for' is : "+expectedRelatedSearchesSectionNumber+" but found : "+actualRelatedSearchesSectionNumber+" sections!");
        Allure.step("Scroll down and go to the next page (page number 2)");
        bingSearchResults.scrollToPaginationSection();
        bingSearchResults.clickOnNextPage();
        int numberOfSearchResultsInTheSecondPage=bingSearchResults.getNumberOfSearchResultsInThePage();
        Allure.step("Scroll down and go to the next page (page number 3)");
        bingSearchResults.scrollToPaginationSection();
        bingSearchResults.clickOnNextPage();
        int numberOfSearchResultsInTheThirdPage=bingSearchResults.getNumberOfSearchResultsInThePage();
        System.out.println("results in page 2 is : "+numberOfSearchResultsInTheSecondPage);
        System.out.println("results in page 3 is : "+numberOfSearchResultsInTheThirdPage);
        Allure.step("Validate if the number of results on page 2 is equal to page 3 or not");
        softAssert.assertEquals(numberOfSearchResultsInTheSecondPage,numberOfSearchResultsInTheThirdPage,"Bug: the number of the search results of the second and third page is not the same,\n the number of search results of the second page is : "+numberOfSearchResultsInTheSecondPage+" \nbut the number of search results of the third page is : "+numberOfSearchResultsInTheThirdPage);
        softAssert.assertAll();
    }
}
