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

public class TestBlankCredentials {
    private static ChromeDriver driver;

    @BeforeAll
    public static void setup()
    {
        System.out.println("BLANK CREDENTIALS");
        System.setProperty("Webdriver.chrome.driver","chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
        driver.manage().timeouts().implicitlyWait(11, TimeUnit.SECONDS);
    }

    @Test
    @Order(1)
    @SpiraTestCase(testCaseId = 21072)
    public void user_stays_on_login_page() {
        String expectedUrl = "https://parabank.parasoft.com/parabank/login.htm";
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.xpath("//input[@value='Log In']")).click();
        String actualUrl = driver.getCurrentUrl();
        assertTrue(actualUrl.startsWith(expectedUrl), "User is not on the login page");
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @Test
    @Order(2)
    @SpiraTestCase(testCaseId = 23512)
    public void user_gets_error_message(){
        System.out.println("Verifying error message display");
        WebElement actualErrorMessage = driver.findElement(By.xpath("//p[@class='error']"));
        String expectedErrorMessage1 = "Please enter a username and password.";
        String expectedErrorMessage2 = "An internal error has occurred and has been logged.";

        assertTrue(actualErrorMessage.getText().equals(expectedErrorMessage1) || actualErrorMessage.getText().equals(expectedErrorMessage2),
                "Error message does not match any of the expected messages."
        );

        System.out.println(actualErrorMessage.getText());
    }

    @AfterAll
    public static void CloseBrowser()
    {
        driver.close();
    }

}
