package tests;


import models.BookModel;
import models.CredentialsModel;

public class TestData {

    private static String login = System.getProperty("login", System.getenv("LOGIN"));
    private static String password = System.getProperty("password", System.getenv("PASSWORD"));

    public static CredentialsModel credentials = new CredentialsModel(login, password);

    private static String isbn = "9781449365035",
            title = "Speaking JavaScript";

    public static final BookModel book = new BookModel(isbn, title);

}
