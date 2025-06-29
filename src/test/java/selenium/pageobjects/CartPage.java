package selenium.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.task.components.AbstractComponent;

public class CartPage extends AbstractComponent {
    WebDriver driver;  
     // Locators
    public CartPage(WebDriver driver) {
        // Constructor for ProductPage
        super(driver); // Call the constructor of AbstractComponent
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    @FindBy(css = ".inventory_item_name")
    private List<WebElement> cartProducts;
    By listOfcartProduct = By.cssSelector(".inventory_item_name");
    
    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    /**
     * Verify if a product is present in the checkout page.
     * @param productName The name of the product to verify.
     * @return true if the product is found, false otherwise.
     */
    public Boolean verifyCheckoutProduct(String productName){
        visibilityOfElementLocated(listOfcartProduct);
        return cartProducts.stream().anyMatch(cartProd -> cartProd.getText().equals(productName));
    }

    public void continueShopping() {
        // Click on the continue shopping button
        continueShoppingButton.click();
    }

    public void clickOnCheckout() {
        // Click on the checkout button
        checkoutButton.click();
    }
}
        