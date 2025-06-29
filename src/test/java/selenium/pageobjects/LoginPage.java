package selenium.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.task.components.AbstractComponent;

public class LoginPage extends AbstractComponent {
    WebDriver driver;  
    // Locators
    public LoginPage(WebDriver driver) {
        // Constructor for LoginPage
        super(driver);    
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    @FindBy(id = "user-name")
    private WebElement userName;

    @FindBy(id = "password")
    private WebElement userPassword;    

    @FindBy(id = "login-button")
    private WebElement loginButton; 

    public void loginApplication(String name, String password){
        userName.sendKeys(null == name ? "" : name);
        userPassword.sendKeys(null == password ? "" : password);
        loginButton.click();
    }
    
}
