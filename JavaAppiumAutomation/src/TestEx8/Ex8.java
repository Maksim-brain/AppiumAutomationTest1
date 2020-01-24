package TestEx8;

import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;

public class Ex8 extends CoreTestCase {

    private lib.ui.MainPageObject MainPageObject;

    protected void setUp() throws Exception {
        super.setUp();

        MainPageObject = new MainPageObject(driver);
    }
    @Test
    public void testCancelSearch() // Test Ex3
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();                   // вызываем поиск
        SearchPageObject.typeSearchLine("Apple");  // вставляем слово для поиска
        SearchPageObject.waitForSearchResult("Home media receiver device made by Apple");// ищем фразу при поиске
        SearchPageObject.waitForSearchResult("Apple Watch");
    }


    @Test
    public void testSaveTwoItemsRefaktoring()  // Test Ex5
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);

        ArticlePageObject.clickSaveArticle();
        ArticlePageObject.addToMyListOverlay();
        ArticlePageObject.clearListName();
        ArticlePageObject.addArticleToMyList("Java");
        ArticlePageObject.clickOkSaveMyList();
        ArticlePageObject.closeArticle();

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Island of Indonesia");

        ArticlePageObject.clickSaveArticle();
        ArticlePageObject.saveArticleInExistingFolder("Java");
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = new NavigationUI(driver);

        NavigationUI.clickMyList();

        MyListPageObject MyListPageObject = new MyListPageObject(driver);

        MyListPageObject.openFolder();
        MyListPageObject.swipeByArticleToDelete("island of Indonesia");
        MyListPageObject.waitForArticleTitle("Object-oriented programming language");
        MyListPageObject.clickOnArticle("object-oriented programming language");
        MyListPageObject.checkTitleCoincidence("Java (programming language)");

    }

    @Test
    public void testFindTitleInArticleRefaktoring() // Test Ex6
    {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();// вызываем поиск
        SearchPageObject.typeSearchLine("Java");// вставляем слово для поиска
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");// находим статью

        MyListPageObject MyListPageObject = new MyListPageObject(driver);
        MyListPageObject.checkAvailabilityTitle();//проверяем наличие элемента title

    }
}
