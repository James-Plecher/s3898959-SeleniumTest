import com.inflectra.spiratest.addons.junitextension.SpiraTestCase;
import com.inflectra.spiratest.addons.junitextension.SpiraTestConfiguration;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpiraTestConfiguration(
        url = "https://rmit.spiraservice.net/",
        login = "s3898959",
        rssToken = "{DAF433CA-21D6-4B8C-9D4A-8A5045E7AAEE}",
        projectId = 493
)

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class TestRegistration {
    private static ChromeDriver driver;

    @BeforeAll
    public static void setup() {
        System.out.println("REGISTRATION");

        System.setProperty("Webdriver.chrome.driver","chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get("https://parabank.parasoft.com/parabank/register.htm");
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        driver.findElement(By.id("customer.firstName")).sendKeys("Register");
        driver.findElement(By.id("customer.lastName")).sendKeys("Test");
        driver.findElement(By.id("customer.address.street")).sendKeys("123");
        driver.findElement(By.id("customer.address.city")).sendKeys("Mel");
        driver.findElement(By.id("customer.address.state")).sendKeys("Pw");
        driver.findElement(By.id("customer.address.zipCode")).sendKeys("1231");
        driver.findElement(By.id("customer.phoneNumber")).sendKeys("124354654");
        driver.findElement(By.id("customer.ssn")).sendKeys("123");
        driver.findElement(By.id("customer.username")).sendKeys("Register");
        driver.findElement(By.id("customer.password")).sendKeys("Test");
        driver.findElement(By.id("repeatedPassword")).sendKeys("Test");
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        driver.findElement(By.xpath("//input[@type='submit' and @value='Register']")).click();
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @Test
    @Order(1)
    @SpiraTestCase(testCaseId = 23518)
    public void check_register_with_url(){
        String expectedUrl = "https://parabank.parasoft.com/parabank/register.htm";
        String actualUrl = driver.getCurrentUrl();
        assertTrue(actualUrl.startsWith(expectedUrl), "Not Registered");
    }

    @Test
    @Order(2)
    @SpiraTestCase(testCaseId = 23519)
    public void check_register_with_message_on_screen(){
        String expectedMessage = "Your account was created successfully. You are now logged in.";
        WebElement element = driver.findElement(By.xpath("//*[@id=\"rightPanel\"]/p"));
        String actualMessage = element.getText();
        assertEquals(expectedMessage, actualMessage);
    }
    @Test
    @Order(3)
    @SpiraTestCase(testCaseId = 23520)
    public void check_register_with_name_on_screen(){
        //welcome XXX
        String expectedMessage = "Welcome Register";
        WebElement element = driver.findElement(By.xpath("//*[@id=\"rightPanel\"]/h1"));
        String actualMessage = element.getText();
        assertEquals(expectedMessage, actualMessage);
    }

    @AfterAll
    public static void CloseBrowser() {
        driver.get("https://parabank.parasoft.com/parabank/admin.htm");
        driver.findElement(By.xpath("//*[@id=\"rightPanel\"]/table/tbody/tr/td[1]/form/table/tbody/tr/td[2]/button")).click();

//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        driver.close();
    }


}
