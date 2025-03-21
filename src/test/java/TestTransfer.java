import com.inflectra.spiratest.addons.junitextension.SpiraTestCase;
import com.inflectra.spiratest.addons.junitextension.SpiraTestConfiguration;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
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

public class TestTransfer {
    private static ChromeDriver driver;
    private static String savingAccount;
    private static String checkingAccount;

    @BeforeAll
    public static void setup() {
        System.out.println("TRANSFER");
        System.setProperty("Webdriver.chrome.driver","chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://parabank.parasoft.com/parabank/register.htm");
        driver.manage().timeouts().implicitlyWait(11, TimeUnit.SECONDS);
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        driver.findElement(By.id("customer.firstName")).sendKeys("Transfer1");
        driver.findElement(By.id("customer.lastName")).sendKeys("Test");
        driver.findElement(By.id("customer.address.street")).sendKeys("123");
        driver.findElement(By.id("customer.address.city")).sendKeys("Mel");
        driver.findElement(By.id("customer.address.state")).sendKeys("Pw");
        driver.findElement(By.id("customer.address.zipCode")).sendKeys("1231");
        driver.findElement(By.id("customer.phoneNumber")).sendKeys("124354654");
        driver.findElement(By.id("customer.ssn")).sendKeys("123");
        driver.findElement(By.id("customer.username")).sendKeys("Transfer4");
        driver.findElement(By.id("customer.password")).sendKeys("Test");
        driver.findElement(By.id("repeatedPassword")).sendKeys("Test");
        driver.findElement(By.xpath("//input[@type='submit' and @value='Register']")).click();
        driver.get("https://parabank.parasoft.com/parabank/overview.htm");
        checkingAccount = driver.findElement(By.xpath("//*[@id=\"accountTable\"]/tbody/tr[1]/td[1]/a")).getText();

        driver.get("https://parabank.parasoft.com/parabank/openaccount.htm");
        WebElement dropdownElement = driver.findElement(By.id("type"));
        Select accountTypeSelect = new Select(dropdownElement);
        accountTypeSelect.selectByVisibleText("SAVINGS");
        driver.findElement(By.xpath("//*[@id=\"openAccountForm\"]/form/div/input")).click();

//        savingAccount = driver.findElement(By.xpath("//*[@id=\"newAccountId\"]")).getText();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement savingAccountElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"newAccountId\"]")));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        savingAccount = savingAccountElement.getText();

        driver.get("https://parabank.parasoft.com/parabank/transfer.htm");
        driver.findElement(By.id("amount")).sendKeys("100");

        WebElement dropdownElementTransfer = driver.findElement(By.id("fromAccountId"));
        Select savingsAccount = new Select(dropdownElementTransfer);
        System.out.println(savingAccount);
        savingsAccount.selectByVisibleText(savingAccount);

        driver.findElement(By.xpath("//*[@id=\"transferForm\"]/div[2]/input")).click();
    }

    @Test
    @Order(1)
    @SpiraTestCase(testCaseId = 21074)
    public void check_transfer_with_message(){
//        String expectedMessage = "$100.00 has been transferred from account #" + savingAccount + " to account #" + checkingAccount + ".";
//        String actualMessage = driver.findElement(By.xpath("//*[@id=\"showResult\"]/p[1]")).getText();
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        assertEquals(expectedMessage, actualMessage);
        String expectedMessage = "$100.00 has been transferred from account #" + savingAccount + " to account #" + checkingAccount + ".";
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='showResult']/p[1]")));

        String actualMessage = messageElement.getText();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @Order(2)
    @SpiraTestCase(testCaseId = 23523)
    public void check_overview_table_checking_amount(){
        driver.get("https://parabank.parasoft.com/parabank/overview.htm");
//        String actualValue = driver.findElement(By.xpath("//*[@id=\"accountTable\"]/tbody/tr[1]/td[2]")).getText();
//        String actualAccount = driver.findElement(By.xpath("//*[@id=\"accountTable\"]/tbody/tr[1]/td[1]/a")).getText();
//
//        assertEquals(checkingAccount, actualAccount);
//        assertEquals("$500.00", actualValue );
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement checkingAccountCell = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='accountTable']/tbody/tr[1]/td[1]/a")));
        WebElement checkingAmountCell = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='accountTable']/tbody/tr[1]/td[2]")));

        String actualAccount = checkingAccountCell.getText();
        String actualValue = checkingAmountCell.getText();

        assertEquals(checkingAccount, actualAccount);
        assertEquals("$500.00", actualValue);
    }

    @Test
    @Order(3)
    @SpiraTestCase(testCaseId = 23525)
    public void check_overview_table_saving_amount(){
        driver.get("https://parabank.parasoft.com/parabank/overview.htm");
//        String actualValue = driver.findElement(By.xpath("//*[@id=\"accountTable\"]/tbody/tr[2]/td[2]")).getText();
//        String actualAccount = driver.findElement(By.xpath("//*[@id=\"accountTable\"]/tbody/tr[2]/td[1]/a")).getText();
//
//        assertEquals(savingAccount, actualAccount);
//        assertEquals("$100.00", actualValue );
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement savingAccountCell = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='accountTable']/tbody/tr[2]/td[1]/a")));
        WebElement savingAmountCell = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='accountTable']/tbody/tr[2]/td[2]")));

        String actualAccount = savingAccountCell.getText();
        String actualValue = savingAmountCell.getText();

        assertEquals(savingAccount, actualAccount);
        assertEquals("$100.00", actualValue);
    }

    @AfterAll
    public static void CloseBrowser()
    {
        driver.get("https://parabank.parasoft.com/parabank/admin.htm");
        driver.findElement(By.xpath("//*[@id=\"rightPanel\"]/table/tbody/tr/td[1]/form/table/tbody/tr/td[2]/button")).click();
        driver.close();
    }


}
