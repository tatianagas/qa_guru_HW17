package tests;

import api.BooksApi;
import models.BookModel;
import models.LoginResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static tests.TestData.credentials;

public class ProfileBooksListTests extends TestBase {

    BooksApi booksApi = new BooksApi();
    BookSteps bookSteps = new BookSteps(booksApi);

    private static final BookModel book = new BookModel("9781449365035", "Speaking JavaScript");

    @Test
    @DisplayName("Удаляем книгу из профиля пользователя")
    void deleteBookFromProfileTest() {
        LoginResponseModel loginResponse = authorizationApi.login(credentials);

        // Подготовка данных
        bookSteps.deleteAllBooksForUser(loginResponse);
        bookSteps.addBookForUser(loginResponse, book);

        // Установка куки
        bookSteps.setCookies(loginResponse);

        step("Открываем профиль и проверяем наличие книги", () -> {
            open("/profile");
            $("[id='see-book-" + book.getTitle() + "']").shouldBe(visible);
        });

        bookSteps.deleteBookForUser(loginResponse, book);

        step("Открываем профиль и проверяем, что книга исчезла", () -> {
            open("/profile");
            $("[id='see-book-" + book.getTitle() + "']").shouldNotBe(visible);
        });
    }
}
