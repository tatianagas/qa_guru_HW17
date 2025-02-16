package tests;


import models.BookModel;
import models.CredentialsModel;

public class TestData {

    public static final String login = System.getProperty("login"),
            password = System.getProperty("password");

    public static CredentialsModel credentials = new CredentialsModel(login, password);

    private static String isbn = "9781449365035",
            title = "Speaking JavaScript";

    public static final BookModel book = new BookModel(isbn, title);

}
