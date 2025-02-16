package tests;

import api.BooksApi;
import models.AddBooksListModel;
import models.IsbnModel;
import models.LoginResponseModel;
import org.openqa.selenium.Cookie;

import java.util.Collections;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static tests.TestData.book;

public class BookSteps {

    private final BooksApi booksApi;

    public BookSteps(BooksApi booksApi) {
        this.booksApi = booksApi;
    }

    public void deleteAllBooksForUser(LoginResponseModel loginResponse) {
        booksApi.deleteAllBooks(loginResponse);
    }

    public void addBookForUser(LoginResponseModel loginResponse) {
        IsbnModel isbnModel = new IsbnModel();
        isbnModel.setIsbn(book.getIsbn());

        AddBooksListModel booksList = new AddBooksListModel();
        booksList.setUserId(loginResponse.getUserId());
        booksList.setCollectionOfIsbns(Collections.singletonList(isbnModel));

        booksApi.addBook(loginResponse, booksList);
    }

    public void deleteBookForUser(LoginResponseModel loginResponse) {
        booksApi.deleteBook(loginResponse, book.getIsbn());
    }

    public void setCookies(LoginResponseModel loginResponse) {
        step("Куки для авторизации в браузере", () -> {
            open("/favicon.ico");
            getWebDriver().manage().addCookie(new Cookie("userID", loginResponse.getUserId()));
            getWebDriver().manage().addCookie(new Cookie("token", loginResponse.getToken()));
            getWebDriver().manage().addCookie(new Cookie("expires", loginResponse.getExpires()));
        });
    }
}
