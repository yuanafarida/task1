package selenium.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.task.components.AbstractComponent;

public class CheckoutPage extends AbstractComponent {
    WebDriver driver;  
     // Locators
    public CheckoutPage(WebDriver driver) {
        // Constructor for ProductPage
        super(driver); // Call the constructor of AbstractComponent
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
   
    @FindBy(id = "first-name")
    private WebElement firstName;
    By firstNameLocator = By.id("first-name");

    @FindBy(id = "last-name")
    private WebElement lastName;

    @FindBy(id = "postal-code")
    private WebElement postalCode;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(className = "title")
    private WebElement checkoutPageText;

    By errorMessageLocator = By.xpath("//h3[@data-test='error']");
   
    public void enterCheckoutDetails(String firstName, String lastName, String postalCode) {        
        visibilityOfElementLocated(firstNameLocator);        
        this.firstName.clear(); // Clear any existing text
        this.firstName.sendKeys(firstName);        
        this.lastName.clear(); // Clear any existing text
        this.lastName.sendKeys(lastName);
        this.postalCode.clear();
        this.postalCode.sendKeys(postalCode);
    }

    public void clickContinue() {
        // Click on the continue button
        continueButton.click();
    }

     public String getErrorMessage() {
        // Get the error message text
        visibilityOfElementLocated(errorMessageLocator);
        return driver.findElement(errorMessageLocator).getText();
    }

    public String getCheckoutPageText() {
        // Get the text of the checkout page
        visibilityOfElementLocated(By.className("title"));
        return checkoutPageText.getText();
    }
}
        