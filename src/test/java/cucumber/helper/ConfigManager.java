package cucumber.helper;

import io.github.cdimascio.dotenv.Dotenv;

public class ConfigManager {
    private static final Dotenv dotenv = Dotenv.configure().load();

    public static String getBaseUrl(){
        return dotenv.get("BASE_URL");
    }

    public static String getName(){
        return dotenv.get("USERNAME");
    }

    public static String getPassword(){
        return dotenv.get("PASSWORD");
    }

     public static String getBaseSeleniumUrl(){
        return dotenv.get("BASE_SELENIUM_URL");
    }
}
