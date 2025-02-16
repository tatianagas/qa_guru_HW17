package tests;

import api.BooksApi;
import models.AddBooksListModel;
import models.IsbnModel;
import models.LoginResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static tests.TestData.book;
import static tests.TestData.credentials;

public class ProfileBooksListTests extends TestBase {

    BooksApi booksApi = new BooksApi();

    @Test
    @DisplayName("Удаляем книгу из профиля пользователя")
    void deleteBookFromProfileTest() {
        LoginResponseModel loginResponse = authorizationApi.login(credentials);


        booksApi.deleteAllBooks(loginResponse);

        IsbnModel isbnModel = new IsbnModel();
        isbnModel.setIsbn(book.getIsbn());
        List<IsbnModel> isbnList = new ArrayList<>();
        isbnList.add(isbnModel);

        AddBooksListModel booksList = new AddBooksListModel();
        booksList.setUserId(loginResponse.getUserId());
        booksList.setCollectionOfIsbns(isbnList);

        booksApi.addBook(loginResponse, booksList);

        step("Куки для авторизации в браузере", () -> {
            open("/favicon.ico");
            getWebDriver().manage().addCookie(new Cookie("userID", loginResponse.getUserId()));
            getWebDriver().manage().addCookie(new Cookie("token", loginResponse.getToken()));
            getWebDriver().manage().addCookie(new Cookie("expires", loginResponse.getExpires()));
        });

        step("Открываем профиль и проверяем наличие книги", () -> {
            open("/profile");
            $("[id='see-book-" + book.getTitle() + "']").shouldBe(visible);
        });

        booksApi.deleteBook(loginResponse, book.getIsbn());


        step("Открываем профиль и проверяем, что книга исчезла", () -> {
            open("/profile");
            $("[id='see-book-" + book.getTitle() + "']").shouldNotBe(visible);
        });

    }

}
