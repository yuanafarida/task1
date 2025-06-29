package selenium.pom;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.task.base.BaseTest;

import selenium.pageobjects.CartPage;
import selenium.pageobjects.CheckoutPage;
import selenium.pageobjects.CompletionPage;
import selenium.pageobjects.LoginPage;
import selenium.pageobjects.OverviewPage;
import selenium.pageobjects.ProductPage;

public class CheckoutPOMScenario extends BaseTest {
    LoginPage loginPage;
    ProductPage productPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;
    OverviewPage overviewPage;
    CompletionPage completionPage;
    
    @BeforeClass
    public void setup() throws InterruptedException{
         // Setup WebDriver
        super.setUp();
        loginPage = new LoginPage(driver);
        productPage = new ProductPage(driver);        
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
        overviewPage = new OverviewPage(driver);
        completionPage = new CompletionPage(driver);
    }

    @Test
    public void login(){

        System.out.println("Valid credentials test is running.");

        //Insert credential
        loginPage.loginApplication("standard_user", "secret_sauce");

        // Verify login success
        Assert.assertEquals(productPage.getProductPageText(), "Products","Product page text does not match!");
    }

    @Test(dependsOnMethods = "login")
    public void addToCart() throws InterruptedException{

        System.out.println("Add to cart test is running.");
        String addProduct1 = "Sauce Labs Fleece Jacket";
        String addProduct2 = "Sauce Labs Onesie";

        // Add first item to the cart
        productPage.addToCart(addProduct1);

        // Verify first item is added to the cart
        productPage.clickOnCart();    
        Thread.sleep(2000);
        boolean isProduct1InCart = cartPage.verifyCheckoutProduct(addProduct1);        
        Assert.assertTrue(isProduct1InCart, "Product 1 was not added to the cart!");

        // Continue shopping
        cartPage.continueShopping();
        Thread.sleep(2000);

        //Add another item to the cart
         productPage.addToCart(addProduct2);

        // Verify second item is added to the cart
        productPage.clickOnCart();    
        Thread.sleep(2000);
        boolean isProduct2InCart = cartPage.verifyCheckoutProduct(addProduct1);        
        Assert.assertTrue(isProduct2InCart, "Product 2 was not added to the cart!");

        // Proceed to checkout
        cartPage.clickOnCheckout();
        Thread.sleep(2000);
    }

    @Test(dependsOnMethods = "addToCart", dataProvider= "invalidCheckoutData")
    public void invalidCheckout(String firstName, String lastName, String postalCode, String errorMessage) throws InterruptedException {

        System.out.println("Invalid Checkout test is running:"+ " " + firstName + ", " + lastName + ", " + postalCode);

        // Fill in checkout information
        checkoutPage.enterCheckoutDetails(firstName, lastName, postalCode);
        checkoutPage.clickContinue();

        //wait until the error message is visible
        Assert.assertEquals(checkoutPage.getErrorMessage(), errorMessage, "Error Message does not match!");   

        Thread.sleep(2000);
    }
    
    @Test(dependsOnMethods = "addToCart")
     public void validCheckout() throws InterruptedException {
        System.out.println("Valid Checkout test is running");

        // Fill in checkout information
        checkoutPage.enterCheckoutDetails("Customer1", "Satu", "12345"); 
        checkoutPage.clickContinue();

        // Verify overview page
        Assert.assertEquals(overviewPage.getOverviewPageText(), "Checkout: Overview", "Overview page text does not match!");
        Thread.sleep(2000);
    }

    @DataProvider(name = "invalidCheckoutData", parallel = false)
    public Object[][] invalidCheckoutData() {
        return new Object[][] {
            {"","Satu","12345","Error: First Name is required"},
            {"Customer2","","12345","Error: Last Name is required"},            
            {"Customer3","Satu","","Error: Postal Code is required"}
        };
    }

    @Test(dependsOnMethods = "validCheckout")
    public void overview() throws InterruptedException {
        System.out.println("Overview test is running.");
        // Verify overview page
        Boolean isCartItemsPresent = overviewPage.verifyCartItems();
        Assert.assertTrue(isCartItemsPresent, "No products found in the cart on the overview page!");

        // show cart items details
        overviewPage.showCartItems();

        // Finish checkout
        overviewPage.clickFinish();
        Thread.sleep(2000);

        // Verify completion page
        Assert.assertEquals(completionPage.getCompletionMessage(), "Thank you for your order!", "Completion message does not match!"); 
        
        //Back to home page
        completionPage.clickBackToHome();
    }
    
    @AfterClass
    public void tearDown() {
        // Close the browser after the test
         super.tearDown();
    }

}
