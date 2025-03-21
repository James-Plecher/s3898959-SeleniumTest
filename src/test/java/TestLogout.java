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

public class TestLogout {
    private static ChromeDriver driver;

    @BeforeAll
    public static void setup() {
        System.setProperty("Webdriver.chrome.driver","chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
        driver.manage().timeouts().implicitlyWait(11, TimeUnit.SECONDS);

        driver.get("https://parabank.parasoft.com/parabank/register.htm");
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        driver.findElement(By.id("customer.firstName")).sendKeys("Logout");
        driver.findElement(By.id("customer.lastName")).sendKeys("Test");
        driver.findElement(By.id("customer.address.street")).sendKeys("123");
        driver.findElement(By.id("customer.address.city")).sendKeys("Mel");
        driver.findElement(By.id("customer.address.state")).sendKeys("Pw");
        driver.findElement(By.id("customer.address.zipCode")).sendKeys("1231");
        driver.findElement(By.id("customer.phoneNumber")).sendKeys("124354654");
        driver.findElement(By.id("customer.ssn")).sendKeys("123");
        driver.findElement(By.id("customer.username")).sendKeys("Logout");
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

        driver.findElement(By.xpath("//a[text()='Log Out']")).click();
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
    }

    @Test
    @Order(1)
    @SpiraTestCase(testCaseId = 20984)
    public void check_logout_with_url(){
        String expectedUrl = "https://parabank.parasoft.com/parabank/index.htm";
        String actualUrl = driver.getCurrentUrl();
        assertTrue(actualUrl.startsWith(expectedUrl), "Not logged out");
    }

    @Test
    @Order(2)
    @SpiraTestCase(testCaseId = 23521)
    public void check_logout_with_login(){
        String expectedMessage = "Customer Login";
        String actualMessage = driver.findElement(By.xpath("//*[@id=\"leftPanel\"]/h2")).getText();
        assertEquals(expectedMessage, actualMessage);
    }

    @AfterAll
    public static void CloseBrowser()
    {
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        driver.close();
    }
}
