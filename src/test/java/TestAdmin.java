import com.inflectra.spiratest.addons.junitextension.SpiraTestCase;
import com.inflectra.spiratest.addons.junitextension.SpiraTestConfiguration;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.junit.jupiter.api.Test;

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

public class TestAdmin {
    private static ChromeDriver driver;
    private static String radioButtonRecorderID;

    @BeforeAll
    public static void setup() {
        System.out.println("ADMIN");

        System.setProperty("Webdriver.chrome.driver","chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://parabank.parasoft.com/parabank/register.htm");
        driver.manage().timeouts().implicitlyWait(11, TimeUnit.SECONDS);
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        driver.findElement(By.id("customer.firstName")).sendKeys("Admin");
        driver.findElement(By.id("customer.lastName")).sendKeys("Test");
        driver.findElement(By.id("customer.address.street")).sendKeys("123");
        driver.findElement(By.id("customer.address.city")).sendKeys("Mel");
        driver.findElement(By.id("customer.address.state")).sendKeys("Pw");
        driver.findElement(By.id("customer.address.zipCode")).sendKeys("1231");
        driver.findElement(By.id("customer.phoneNumber")).sendKeys("124354654");
        driver.findElement(By.id("customer.ssn")).sendKeys("123");
        driver.findElement(By.id("customer.username")).sendKeys("Admin");
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
        driver.get("https://parabank.parasoft.com/parabank/admin.htm");
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        WebElement soapRadioButton = driver.findElement(By.id("accessMode1"));
        WebElement restXmlRadioButton = driver.findElement(By.id("accessMode2"));

//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        // Check which radio button is selected
        if (soapRadioButton.isSelected()) {
            restXmlRadioButton.click();
            radioButtonRecorderID = "accessMode2";
        }

        else {
            soapRadioButton.click();
            radioButtonRecorderID = "accessMode1";
        }


//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        //______________________________________________________________________________________________
        driver.findElement(By.id("initialBalance")).clear();
        driver.findElement(By.id("initialBalance")).sendKeys("600");
        driver.findElement(By.id("minimumBalance")).clear();
        driver.findElement(By.id("minimumBalance")).sendKeys("200");


        WebElement dropdownElement = driver.findElement(By.id("loanProvider"));
        Select loanProvider = new Select(dropdownElement);
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        loanProvider.selectByVisibleText("Local");

        dropdownElement = driver.findElement(By.id("loanProcessor"));
        Select loanProcessor = new Select(dropdownElement);
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        loanProcessor.selectByVisibleText("Combined");


        driver.findElement(By.id("loanProcessorThreshold")).clear();
        driver.findElement(By.id("loanProcessorThreshold")).sendKeys("10");

//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        driver.findElement(By.xpath("//*[@id=\"adminForm\"]/input")).click();
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }


    @Test
    @Order(1)
    @SpiraTestCase(testCaseId = 20988)
    public void check_account_by_message(){
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        String expectedMessage = "Settings saved successfully.";
        String actualMessage = driver.findElement(By.xpath("//*[@id=\"rightPanel\"]/p/b")).getText();;
        assertEquals(expectedMessage, actualMessage);
        driver.get("https://parabank.parasoft.com/parabank/admin.htm");
    }

    @Test
    @Order(2)
    @SpiraTestCase(testCaseId = 23494)
    public void check_radio_button() {
        WebElement selectedButton = driver.findElement(By.id(radioButtonRecorderID));
        assertTrue(selectedButton.isSelected());
    }

    @Test
    @Order(3)
    @SpiraTestCase(testCaseId = 23495)
    public void check_initial_balance() {
        String initialBalance = driver.findElement(By.id("initialBalance")).getAttribute("value");
        assertEquals("600", initialBalance);
    }

    @Test
    @Order(4)
    @SpiraTestCase(testCaseId = 23498)
    public void check_minimum_balance() {
        String minimumBalance = driver.findElement(By.id("minimumBalance")).getAttribute("value");
        assertEquals("200", minimumBalance);
    }
    @Test
    @Order(5)
    @SpiraTestCase(testCaseId = 23501)
    public void check_loan_provider() {
        Select loanProvider = new Select(driver.findElement(By.id("loanProvider")));
        String selectedLoanProvider = loanProvider.getFirstSelectedOption().getText();
        assertEquals("Local", selectedLoanProvider);
    }
    @Test
    @Order(6)
    @SpiraTestCase(testCaseId = 23502)
    public void check_loan_processor() {
        Select loanProcessor = new Select(driver.findElement(By.id("loanProcessor")));
        String selectedLoanProcessor = loanProcessor.getFirstSelectedOption().getText();
        assertEquals("Combined", selectedLoanProcessor);
    }
    @Test
    @Order(7)
    @SpiraTestCase(testCaseId = 23505)
    public void check_loan_processor_threshold() {
        String loanProcessorThreshold = driver.findElement(By.id("loanProcessorThreshold")).getAttribute("value");
        assertEquals("10", loanProcessorThreshold);
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
