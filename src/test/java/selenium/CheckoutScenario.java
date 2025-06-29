package selenium;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CheckoutScenario {
    
    WebDriver driver;
    
    @BeforeClass
    public void setup() throws InterruptedException{
         // Setup WebDriver
        System.setProperty("webdriver.chrome.drive", "C:\\Users\\lucia\\Documents\\Bootcamp Selenium\\chromedriver");

        ChromeOptions options = new ChromeOptions();

        // Nonaktifkan password manager dan leak detection
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false); // Disable "save password" service
        prefs.put("profile.password_manager_leak_detection", false); // Disable Chrome password manager
        prefs.put("safebrowsing.enabled", false); // (opsional) nonaktifkan safe browsing

        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);
        driver.get("https://www.saucedemo.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));
    }

    @Test
    public void login(){

        System.out.println("Valid credentials test is running.");

        //Insert credential
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");

        driver.findElement(By.id("login-button")).click();

        // Verify login success
        String productPage = driver.findElement(By.xpath("//span[@class='title']")).getText();
        Assert.assertEquals(productPage, "Products","Product page text does not match!");

    }

    @Test(dependsOnMethods = "login")
    public void addToCart() throws InterruptedException{

        System.out.println("Add to cart test is running.");
        String addProduct1 = "Sauce Labs Fleece Jacket";
        String addProduct2 = "Sauce Labs Onesie";

        // Add items to the cart
        List<WebElement> listOfProduct = driver.findElements(By.xpath("//div[@class='inventory_list']/div[@class='inventory_item']"));
        String checkProduct;
        for (WebElement product : listOfProduct) {
            checkProduct =  product.findElement(By.xpath(".//div[@class='inventory_item_description']/div[@class='inventory_item_label']/a/div")).getText();
            if(checkProduct.equals(addProduct1)){
                product.findElement(By.xpath(".//button[text()='Add to cart']")).click();
                System.out.println("Added to cart: " + checkProduct);
                break;
            }
        }
        Thread.sleep(2000);

        // Verify item is added to the cart
        driver.findElement(By.className("shopping_cart_link")).click();
        Thread.sleep(2000);
        List<WebElement> cartProducts = driver.findElements(By.cssSelector(".inventory_item_name"));
        boolean isProductInCart = cartProducts.stream().anyMatch(cartProd -> cartProd.getText().equals(addProduct1));
       
        Assert.assertTrue(isProductInCart, "Product 1 was not added to the cart!");

        driver.findElement(By.id("continue-shopping")).click();

        //Add another item to the cart
        listOfProduct = driver.findElements(By.xpath("//div[@class='inventory_list']/div[@class='inventory_item']"));
        WebElement product2ToAdd = listOfProduct.stream().filter(prod -> prod.findElement(By.className("inventory_item_name")).getText().equals(addProduct2)).findFirst().orElse(null);

        if (product2ToAdd != null) {
            product2ToAdd.findElement(By.cssSelector(".pricebar button:last-of-type")).click();
            System.out.println("Added to cart: " + addProduct2);
        } else {
            Assert.fail("Product not found: " + addProduct2);
        }

        // Verify item is added to the cart
        driver.findElement(By.className("shopping_cart_link")).click();
        Thread.sleep(2000);
        cartProducts = driver.findElements(By.cssSelector(".inventory_item_name"));
        isProductInCart = cartProducts.stream().anyMatch(cartProd -> cartProd.getText().equals(addProduct2));

        Assert.assertTrue(isProductInCart, "Product 2 was not added to the cart!");

        // Proceed to checkout
        driver.findElement(By.id("checkout")).click();
        Thread.sleep(2000);
    }

    @Test(dependsOnMethods = "addToCart", dataProvider= "invalidCheckoutData")
    public void invalidCheckout(String firstName, String lastName, String postalCode, String errorMessage) throws InterruptedException {

        System.out.println("Invalid Checkout test is running:"+ " " + firstName + ", " + lastName + ", " + postalCode);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name")));
        // Fill in checkout information
        driver.findElement(By.id("first-name")).clear();
        driver.findElement(By.id("first-name")).sendKeys(firstName);
        driver.findElement(By.id("last-name")).clear();
        driver.findElement(By.id("last-name")).sendKeys(lastName);
        driver.findElement(By.id("postal-code")).clear();   
        driver.findElement(By.id("postal-code")).sendKeys(postalCode);
        driver.findElement(By.id("continue")).click();

        //wait until the error message is visible
        System.out.println("Expected error message: " + errorMessage);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[@data-test='error']")));
        String checkErrorMessage = driver.findElement(By.xpath("//h3[@data-test='error']")).getText();
        Assert.assertEquals(checkErrorMessage, errorMessage, "Error Message does not match!");   

        Thread.sleep(2000);
    }
    
    @Test(dependsOnMethods = "addToCart")
     public void validCheckout() throws InterruptedException {
        System.out.println("Valid Checkout test is running");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name")));
        // Fill in checkout information
        driver.findElement(By.id("first-name")).clear();
        driver.findElement(By.id("first-name")).sendKeys("Customer");
        driver.findElement(By.id("last-name")).clear();
        driver.findElement(By.id("last-name")).sendKeys("Satu");
        driver.findElement(By.id("postal-code")).clear();   
        driver.findElement(By.id("postal-code")).sendKeys("123456");
        driver.findElement(By.id("continue")).click();

        System.out.println("No error message expected, proceeding to overview page.");         
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("title")));
        String overviewPage = driver.findElement(By.className("title")).getText();
        Assert.assertEquals(overviewPage, "Checkout: Overview", "Overview page text does not match!");
        
        Thread.sleep(2000);
    }

    @DataProvider(name = "invalidCheckoutData", parallel = false)
    public Object[][] invalidCheckoutData() {
        return new Object[][] {
            
            {"Customer3","Satu","","Error: Postal Code is required"},
            {"","Satu","12345","Error: First Name is required"},
            {"Customer2","","12345","Error: Last Name is required"}
        };
    }

    public Boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Test(dependsOnMethods = "validCheckout")
    public void overview() throws InterruptedException {
        System.out.println("Overview test is running.");
        // Verify overview page
       List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cart_item"));
       Assert.assertTrue(cartProducts.size() > 0, "No products found in the cart on the overview page!");

        // Verify product details
        for (WebElement product : cartProducts) {
            String productName = product.findElement(By.className("inventory_item_name")).getText();
            String productPrice = product.findElement(By.className("inventory_item_price")).getText();
            System.out.println("Product: " + productName + ", Price: " + productPrice);
        }
        // Finish checkout
        driver.findElement(By.id("finish")).click();
        Thread.sleep(2000);

        // Verify completion page
        String completionMessage = driver.findElement(By.className("complete-header")).getText();
        Assert.assertEquals(completionMessage, "Thank you for your order!", "Completion message does not match!"); 
    }
    
    // @AfterClass
    public void tearDown() {
        // Close the browser after the test
        if (driver != null) {
            driver.quit();
        }
    }

}
