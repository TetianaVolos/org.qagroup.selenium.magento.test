import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CSSDinner {
    private WebDriver driver;
    private String[] answers = new String[]
            {
                    "plate",                        // 01
                    "bento",                        // 02
                    "plate#fancy",                  // 03
                    "plate apple",                  // 04
                    "plate#fancy pickle",           // 05
                    "apple.small",                  // 06
                    "orange.small",                 // 07
                    "bento orange.small",           // 08
                    "plate, bento",                 // 09
                    "*",                            // 10
                    "plate *",                      // 11
                    "plate + apple",                // 12
                    "bento ~ pickle",               // 13
                    "plate > apple",                // 14
                    "plate > orange:first-child",   // 15
                    "plate apple:only-child, plate pickle:only-child",  // 16
                    "apple:last-child, pickle:last-child",              // 17
                    "plate:nth-child(3)",           // 18
                    "bento:nth-last-child(3)",      // 19
                    "apple:first-of-type",          // 20
                    "plate:nth-of-type(even)",      // 21
                    "plate:nth-of-type(2n+3)",      // 22
                    "plate apple:only-of-type",     // 23
                    "orange:last-of-type, apple:last-of-type",         // 24
                    "bento:empty",                  // 25
                    "apple:not(.small)",            // 26
                    "apple[for='Ethan'], plate[for='Alice'], bento[for='Clara']", // 27
                    "plate[for='Sarah'], plate[for='Luke']",           // 28
                    "bento[for='Vitaly']",          // 29
                    "*[for^='Sa']",                 // 30
                    "*[for$='ato']",                // 31
                    "*[for*='obb']",                // 32

            };



    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "src/main/java/geckodriver");
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        driver = new FirefoxDriver(options);
        driver.get("https://flukeout.github.io/");
        driver.manage().window().setSize(new Dimension(1024, 768));
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void letsPlay() {
        System.out.println("Now I'm gonna play the game: " + driver.getTitle());
        System.out.println("===========================");

        //define some locators for this game
        WebElement currentGameTask = driver.findElement(By.className("order"));
        WebElement gameLevel = driver.findElement(By.className("level-text"));
        WebElement rowForAnswer = driver.findElement(By.xpath("//*[@class='input-strobe' and @type='text' and @placeholder='Type in a CSS selector']"));
        WebElement enterButton = driver.findElement(By.xpath("//div[contains(@class, 'enter-button') and contains(text(), 'enter')]"));

        // define an explicit waits
        WebDriverWait waitBeforeInputAnswer = (new WebDriverWait(driver, 8));
        WebDriverWait waitAfterInputAnswer = (new WebDriverWait(driver, 8));


        for (int i = 0; i < answers.length-1; i++) {
            // Display current level and task
            System.out.println("I am on " + gameLevel.getText());
            System.out.println("My task is " + currentGameTask.getText());
            waitBeforeInputAnswer.until(ExpectedConditions.elementToBeClickable(rowForAnswer));


            // Type the answer
            rowForAnswer.sendKeys(answers[i]);
            enterButton.click();
            if (i < answers.length) {
                waitAfterInputAnswer.until(ExpectedConditions.textToBe(new By.ByClassName("level-text"), "Level " + (i + 2) + " of 32"));
                System.out.println("OK, I win level " + (i + 1));
            } else {
                waitAfterInputAnswer.until(ExpectedConditions.presenceOfElementLocated(new By.ByClassName("winner")));
                System.out.println("I winner");
                break;
            }
        }
        // check if all level have been won

        WebElement menu = driver.findElement(new By.ByClassName("level-menu-toggle-wrapper"));
        menu.click();
        Assert.assertEquals(driver.findElements(new By.ByClassName("completed")).size(), 31);
    }
}
