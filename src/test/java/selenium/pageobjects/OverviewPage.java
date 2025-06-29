package selenium.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.task.components.AbstractComponent;

public class OverviewPage extends AbstractComponent {
    WebDriver driver;  
     // Locators
    public OverviewPage(WebDriver driver) {
        // Constructor for ProductPage
        super(driver); // Call the constructor of AbstractComponent
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    @FindBy(className = "title")
    private WebElement titleText;
    By titleLocator = By.className("title");

    @FindBy(css=".cart_item")
    private List<WebElement> cartItems;
    By cartItemsLocator = By.cssSelector(".cart_item");

    @FindBy(id="finish")
    private WebElement finishButton;

    public String getOverviewPageText() {
        // Get the text of the overview page
        visibilityOfElementLocated(titleLocator);
        return titleText.getText();
    }

    public Boolean verifyCartItems() {
        // Verify if there are items in the cart
        visibilityOfElementLocated(cartItemsLocator);
        return !cartItems.isEmpty();
    }

    public void showCartItems() {
        // Print the cart items
        visibilityOfElementLocated(cartItemsLocator);
        for (WebElement product : cartItems) {
            String itemName = product.findElement(By.className("inventory_item_name")).getText();
            String itemPrice = product.findElement(By.className("inventory_item_price")).getText();

            System.out.println("Product: " + itemName + ", Price: " + itemPrice);
        }
    }

    public void clickFinish() {
        // Click on the finish button
        finishButton.click();
    }
}
        