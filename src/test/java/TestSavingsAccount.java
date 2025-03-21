import com.inflectra.spiratest.addons.junitextension.SpiraTestCase;
import com.inflectra.spiratest.addons.junitextension.SpiraTestConfiguration;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpiraTestConfiguration(
        url = "https://rmit.spiraservice.net/",
        login = "s3898959",
        rssToken = "{DAF433CA-21D6-4B8C-9D4A-8A5045E7AAEE}",
        projectId = 493
)

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class TestSavingsAccount {
    private static ChromeDriver driver;

    @BeforeAll
    public static void setup() {
        System.out.println("SAVINGS ACCOUNT CREATION");
        System.setProperty("Webdriver.chrome.driver","chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://parabank.parasoft.com/parabank/register.htm");
        driver.manage().timeouts().implicitlyWait(11, TimeUnit.SECONDS);
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        driver.findElement(By.id("customer.firstName")).sendKeys("Savings");
        driver.findElement(By.id("customer.lastName")).sendKeys("Test");
        driver.findElement(By.id("customer.address.street")).sendKeys("123");
        driver.findElement(By.id("customer.address.city")).sendKeys("Mel");
        driver.findElement(By.id("customer.address.state")).sendKeys("Pw");
        driver.findElement(By.id("customer.address.zipCode")).sendKeys("1231");
        driver.findElement(By.id("customer.phoneNumber")).sendKeys("124354654");
        driver.findElement(By.id("customer.ssn")).sendKeys("123");
        driver.findElement(By.id("customer.username")).sendKeys("Tavings");
        driver.findElement(By.id("customer.password")).sendKeys("Test");
        driver.findElement(By.id("repeatedPassword")).sendKeys("Test");
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        driver.findElement(By.xpath("//input[@type='submit' and @value='Register']")).click();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        driver.get("https://parabank.parasoft.com/parabank/openaccount.htm");
        WebElement dropdownElement = driver.findElement(By.id("type"));
        Select accountTypeSelect = new Select(dropdownElement);

        accountTypeSelect.selectByVisibleText("SAVINGS");
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        driver.findElement(By.xpath("//*[@id=\"openAccountForm\"]/form/div/input")).click();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @Test
    @Order(1)
    @SpiraTestCase(testCaseId = 21073)
    public void check_account_by_message(){
        String expectedMessage = "Congratulations, your account is now open.";
//        String actualMessage = driver.findElement(By.xpath("//*[@id=\"openAccountResult\"]/p[1]")).getText();
        // Create an explicit wait with a timeout of 10 seconds
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait until the success message element is visible
        WebElement messageElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='openAccountResult']/p[1]"))
        );

        // Retrieve the actual message text
        String actualMessage = messageElement.getText();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @Order(2)
    @SpiraTestCase(testCaseId = 23493)
    public void check_account_via_overview_page(){
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        String accountNewNumber = driver.findElement(By.xpath("//*[@id=\"newAccountId\"]")).getText();
        driver.get("https://parabank.parasoft.com/parabank/overview.htm");
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        String xpatsh = "//table[@id='accountTable']//a[text()='" + accountNewNumber + "']";
        WebElement accountLink = driver.findElement(By.xpath(xpatsh));
        assertTrue(accountLink.isDisplayed(), "Account number " + accountNewNumber + " is not found in the table.");

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
