package selenium.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.task.components.AbstractComponent;

public class ProductPage extends AbstractComponent {
    WebDriver driver;
    // Locators
    public ProductPage(WebDriver driver) {
        // Constructor for ProductPage
        super(driver); // Call the constructor of AbstractComponent
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span[@class='title']")
    private WebElement titleText; 
    
    @FindBy(className = "shopping_cart_link")
    private WebElement cartIcon;

    By listOfProducts = By.xpath("//div[@class='inventory_list']/div[@class='inventory_item']");
    By titleProduct = By.xpath(".//div[@class='inventory_item_description']/div[@class='inventory_item_label']/a/div");
    By addToCartButton = By.xpath(".//button[text()='Add to cart']");

    public String getProductPageText(){
        return titleText.getText();
    }

     public void addToCart(String productName) throws InterruptedException {
        // Implement the logic to add a product to the cart
        System.out.println("Adding " + productName + " to cart.");

        // Add your implementation here
        visibilityOfElementLocated(listOfProducts);
        getProductByName(productName).findElement(addToCartButton).click();
        Thread.sleep(2000);
    }

    public WebElement getProductByName(String productName){
        List<WebElement> products = driver.findElements(listOfProducts);
        WebElement productToSelect = products.stream().filter(prod -> prod.findElement(titleProduct).getText().equals(productName)).findFirst().orElse(null);
        return productToSelect;
    }

    public void clickOnCart() {
        // Click on the cart icon
        cartIcon.click();
    }
}
