package tests;

import api.BooksApi;
import models.LoginResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static tests.TestData.book;
import static tests.TestData.credentials;

public class ProfileBooksListTests extends TestBase {

    BooksApi booksApi = new BooksApi();
    BookSteps bookSteps = new BookSteps(booksApi);

    @Test
    @DisplayName("Удаляем книгу из профиля пользователя")
    void deleteBookFromProfileTest() {
        LoginResponseModel loginResponse = authorizationApi.login(credentials);

        bookSteps.deleteAllBooksForUser(loginResponse);
        bookSteps.addBookForUser(loginResponse);

        bookSteps.setCookies(loginResponse);

        step("Открываем профиль и проверяем наличие книги", () -> {
            open("/profile");
            $("[id='see-book-" + book.getTitle() + "']").shouldBe(visible);
        });

        bookSteps.deleteBookForUser(loginResponse);

        step("Открываем профиль и проверяем, что книга исчезла", () -> {
            open("/profile");
            $("[id='see-book-" + book.getTitle() + "']").shouldNotBe(visible);
        });
    }
}