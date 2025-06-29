package selenium.cucumber.hook;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import cucumber.helper.ConfigManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {
     public static WebDriver driver;

    @Before
    public void setUp() {
        // Setup WebDriver
        System.setProperty("webdriver.chrome.drive", "C:\\Users\\lucia\\Documents\\Bootcamp Selenium\\chromedriver");

        ChromeOptions options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.password_manager_leak_detection", false); // Disable Chrome password manager
        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);
        driver.get(ConfigManager.getBaseSeleniumUrl()); // Use the base URL from ConfigManager
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Tutup browser setelah scenario selesai
        }
    }

    public static WebDriver getInitializeDriver() {
        return driver; // Method to access the WebDriver instance
    }
}
