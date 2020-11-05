import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class magento_login_test {
    private WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.get("https://magento.nublue.co.uk/customer/account/login");
        driver.manage().window().maximize();
    }

    @Test(priority = 1, description = "sign in to customer account and sign out")
    public void testsign() {
        // define elements
        WebElement loginElement = driver.findElement(By.xpath("//input[@name='login[username]']"));
        WebElement passElement = driver.findElement(By.xpath("//input[@name='login[password]']"));
        WebElement submitButton = driver.findElement(By.xpath("//button[@class='action login primary']"));


        // sign in
        Assert.assertTrue(driver.getCurrentUrl().contains("login"));
        loginElement.sendKeys("roni_cost@example.com");
        passElement.sendKeys("roni_cost3@example.com");
        submitButton.click();


        // check result
        driver.get("https://magento.nublue.co.uk/sales/order/history/");
        driver.getTitle();
        Assert.assertEquals(driver.getTitle(),"My Orders");


        // sign out
        WebElement dropMenu = driver.findElement(By.xpath("//li[@class='customer-welcome']"));
        dropMenu.click();
        WebElement signOut = driver.findElement(By.xpath("//li[@class='authorization-link']"));
        signOut.click();

        // check sign out
        Assert.assertTrue(driver.getCurrentUrl().contains("/logoutSuccess"));

        // back to previous page and check 
        driver.navigate().back();
        driver.navigate().refresh();
        Assert.assertTrue(driver.getCurrentUrl().contains("/account/login"));
    }

    @AfterMethod
    public void teardown(){
        driver.quit();
    }
}


