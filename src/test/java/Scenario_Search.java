import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class Scenario_Search{
    private WebDriver driver;

    @BeforeTest
    public void setup() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        Thread.sleep(2000);
        driver.get("https://");
        Thread.sleep(3000);
        WebElement email = driver.findElement(By.name("email"));
        email.sendKeys("admin@gmail.com");
        WebElement pass = driver.findElement(By.name("pass"));
        pass.sendKeys("123456");
        driver.findElement(By.name("login")).click();
        Thread.sleep(3000);
    }

    @Test
    public void testSearchAndOpenChat() throws InterruptedException {
        // Navigate to the chat screen
        driver.findElement(By.xpath("//a[contains(text(),'Chat')]")).click();
        Thread.sleep(2000);

        // Perform search
        WebElement searchBox = driver.findElement(By.xpath("//*[@title='Tìm kiếm']"));
        searchBox.click();
        searchBox.sendKeys("Ngoc Kien");
        searchBox.sendKeys(Keys.RETURN);
        Thread.sleep(3000); // Wait for search results to load

        // Verify search results
        List<WebElement> results = driver.findElements(By.xpath("//*[@class='result-title']"));
        boolean found = false;
        for (WebElement result : results) {
            if (result.getText().contains("Ngoc Kien")) {
                found = true;
                result.click(); // Click on the search result to open chat
                break;
            }
        }
        Assert.assertTrue(found, "Search result should include 'Nguyễn Văn A'");

        // Verify chat window is opened
        WebElement chatHeader = driver.findElement(By.xpath("//div[@class='chat-header']"));
        Assert.assertTrue(chatHeader.getText().contains("Ngoc Kien"), "Chat window should open with 'Ngoc Kien'");

        // Send a message
        WebElement chatInput = driver.findElement(By.xpath("//input[@class='chat-input']"));
        chatInput.sendKeys("Hello ");
        chatInput.sendKeys(Keys.RETURN);

        // Verify message is sent
        WebElement lastMessage = driver.findElement(By.xpath("//div[@class='message' and contains(text(),'Hello')]"));
        Assert.assertNotNull(lastMessage, "The sent message should be displayed in the chat window");

        // Close chat window
        driver.findElement(By.xpath("//button[@class='close-chat']")).click();
    }
    @Test
    public void testNoResultsFound() throws InterruptedException {
        WebElement searchBox = driver.findElement(By.xpath("//*[@title='Tìm kiếm']"));
        searchBox.click();
        searchBox.sendKeys("abcdef");
        searchBox.sendKeys(Keys.RETURN);
        Thread.sleep(3000);

        WebElement noResultsMessage = driver.findElement(By.xpath("//div[@role='alert' and contains(@class, 'alert-info')]"));
        String alertText = noResultsMessage.getText().replace("×", "").trim();
        Assert.assertEquals(alertText, "Không có kết quả phù hợp", "Text of the alert should be 'Không có kết quả phù hợp'");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
