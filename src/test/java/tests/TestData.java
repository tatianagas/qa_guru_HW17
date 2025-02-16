package tests;

import models.CredentialsModel;

public class TestData {

    private static String login = System.getProperty("login", System.getenv("LOGIN"));
    private static String password = System.getProperty("password", System.getenv("PASSWORD"));

    public static CredentialsModel credentials = new CredentialsModel(login, password);


}
