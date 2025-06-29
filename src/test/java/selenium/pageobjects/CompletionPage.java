package selenium.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.task.components.AbstractComponent;

public class CompletionPage extends AbstractComponent {
    WebDriver driver;  
     // Locators
    public CompletionPage(WebDriver driver) {
        // Constructor for ProductPage
        super(driver); // Call the constructor of AbstractComponent
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
   
    @FindBy(className = "complete-header")
    private WebElement completionHeader;

    @FindBy(name = "back-to-products")
    private WebElement backToProductsButton;

    public String getCompletionMessage() {
        // Get the completion message text
        return completionHeader.getText();
    } 

    public void clickBackToHome() {
        // Click on the back to products button
        backToProductsButton.click();
    }
    
}
        