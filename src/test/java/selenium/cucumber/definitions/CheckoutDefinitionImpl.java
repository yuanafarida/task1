package selenium.cucumber.definitions;

import org.testng.Assert;

import com.task.base.BaseTest;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import selenium.cucumber.hook.Hooks;
import selenium.pageobjects.CartPage;
import selenium.pageobjects.CheckoutPage;
import selenium.pageobjects.CompletionPage;
import selenium.pageobjects.LoginPage;
import selenium.pageobjects.OverviewPage;
import selenium.pageobjects.ProductPage;

public class CheckoutDefinitionImpl extends BaseTest {
    LoginPage loginPage;
    ProductPage productPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;
    OverviewPage overviewPage;
    CompletionPage completionPage;

    @Given("User landing to logged ecommerce and checkout")
    public void setup() throws InterruptedException{
         // Setup WebDriver
        driver = Hooks.getInitializeDriver();
        loginPage = new LoginPage(driver);
        productPage = new ProductPage(driver); 
        cartPage = new CartPage(driver);           
        checkoutPage = new CheckoutPage(driver);
        overviewPage = new OverviewPage(driver);
        completionPage = new CompletionPage(driver);
    }

    @When("User input username {string} and password {string}")
    public void login(String username, String password) {
        System.out.println("Valid credentials test is running.");

        //Insert credential
        loginPage.loginApplication(username, password);
    }

    @Then("^User redirect to product page and see page title (.+)$")
    public void userOnProductPage(String pageTitle) {
        // Verify user is on product page
        Assert.assertEquals(productPage.getProductPageText(), pageTitle,"Product page text does not match!");
    }

    @When("User add first product to Cart {string}")
    public void addFirstProductToCart(String productName) throws InterruptedException {
        // Add first item to the cart
        productPage.addToCart(productName);
        }

    @Then("Verify first product successfully added on cart page {string}")
    public void verifyFirstProductAddedToCart(String productName) throws InterruptedException {
        productPage.clickOnCart();    
        Thread.sleep(2000);
        boolean isProduct1InCart = cartPage.verifyCheckoutProduct(productName);        
        Assert.assertTrue(isProduct1InCart, "Product 1 was not added to the cart!");
    }

    @When("User continue shopping and add second product to Cart {string}")
    public void addSecondProductToCart(String productName) throws InterruptedException {
        // Continue shopping
        cartPage.continueShopping();
        Thread.sleep(2000);
        // Add second item to the cart
        productPage.addToCart(productName);
    }   

    @Then("Verify second product successfully added on cart page {string}")
    public void verifySecondProductAddedToCart(String productName) throws InterruptedException {
        // Verify second item is added to the cart
        productPage.clickOnCart();
        Thread.sleep(2000);
        boolean isProduct2InCart = cartPage.verifyCheckoutProduct(productName);        
        Assert.assertTrue(isProduct2InCart, "Product 2 was not added to the cart!");
    }

    @When("User click on checkout button")
    public void clickOnCheckout() throws InterruptedException {
        // Proceed to checkout
        cartPage.clickOnCheckout();
        Thread.sleep(2000);
    } 

    @Then("^User redirect to checkout page and see page title (.+)$")
    public void userOnCheckoutPage(String pageTitle) {
        // Verify user is on checkout page
        Assert.assertEquals(checkoutPage.getCheckoutPageText(), pageTitle, "Checkout page text does not match!");
    }

    @When("User input first name {string} and last name {string} and zip code {string}")
    public void inputCheckoutDetails(String firstName, String lastName, String postalCode) {
       // Fill in checkout information
        checkoutPage.enterCheckoutDetails(firstName, lastName, postalCode);
        checkoutPage.clickContinue();
    }
    
    @Then("Verify error message {string} is displayed")
    public void verifyErrorMessage(String errorMessage) {
        // Verify error message is displayed
        Assert.assertEquals(checkoutPage.getErrorMessage(), errorMessage, "Error Message does not match!");   
    }
    
    @Then("^User redirect to overview page and see page title (.+)$")
    public void userOnOverviewPage(String pageTitle) {
    // Verify user is on overview page
       Assert.assertEquals(overviewPage.getOverviewPageText(), pageTitle, "Overview page text does not match!");
    }

    @When("User on overview page")
    public void userOnOverviewPage() {
        // User is on overview page, no action needed
        System.out.println("User is on Overview page.");
    }

    @Then("Verify the order summary is displayed")
    public void verifyOrderSummary() {
        // Verify the order summary is displayed
        Boolean isCartItemsPresent = overviewPage.verifyCartItems();
        Assert.assertTrue(isCartItemsPresent, "No products found in the cart on the overview page!");
        
        // show cart items details
        overviewPage.showCartItems();
    }

    @When("User click finish button")
    public void clickFinishButton() throws InterruptedException {
        // Verify overview page
        System.out.println("Clicking on finish button.");
        Thread.sleep(2000); 
        overviewPage.clickFinish();
    } 

    @Then("^User redirect to completion page and see message displayed (.+)$")
    public void userOnCompletionPage(String message) {    
         // Verify completion page
        Assert.assertEquals(completionPage.getCompletionMessage(), message, "Completion message does not match!"); 
    }

    @And("User click back to home button")
    public void clickBackToHomeButton() throws InterruptedException {
        // Click on the back to products button
        Thread.sleep(2000);
        completionPage.clickBackToHome();
    }   
}
