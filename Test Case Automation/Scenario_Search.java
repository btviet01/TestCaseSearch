import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class Scenario_Search {
    private WebDriver driver;

    @BeforeTest
    public void setup() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        Thread.sleep(2000);
        driver.get("https://www.messenger.com/e2ee/t/26099064556351384");
        Thread.sleep(3000);
        WebElement email = driver.findElement(By.name("email"));
        email.sendKeys("vietbui.300103@gmail.com");
        WebElement pass = driver.findElement(By.name("pass"));
        pass.sendKeys("Buithiviet173003");
        driver.findElement(By.name("login")).click();
        Thread.sleep(3000);
    }
    //    @Test
//    public void testMultipleInputs() throws InterruptedException {
//        // Thêm khoảng thời gian chờ để đảm bảo trang được tải đầy đủ
//        Thread.sleep(5000);
//
//        for (int i = 1; i < 10; i++) {
//            WebElement ib = driver.findElement(By.xpath("//*[@aria-placeholder='Aa']"));
//            ib.clear(); // Xóa nội dung trước khi nhập mới
//            ib.sendKeys("em yêu anh nhiều lắm");
//            ib.sendKeys(Keys.RETURN);
//            // Thêm khoảng thời gian chờ để đảm bảo trang xử lý và cập nhật kết quả
//            Thread.sleep(2000);
//        }
//    }
    @Test
    public void testSearchBoxPresence() throws InterruptedException {
        WebElement searchBox = driver.findElement(By.xpath("//*[@aria-autocomplete='list']"));
        Assert.assertNotNull(searchBox, "Search box should be present on the page.");
        Thread.sleep(2000);
    }
    @Test
    public void testSearchBoxPlaceholder() throws InterruptedException {
        driver.findElement(By.xpath("//div[@aria-label='Xóa']")).click();
        Thread.sleep(2000);
        WebElement searchBox = driver.findElement(By.xpath("//*[@aria-autocomplete='list']"));
        String placeholderText = searchBox.getAttribute("placeholder");
        Thread.sleep(1000);
        Assert.assertEquals(placeholderText, "Tìm kiếm trên Messenger", "Placeholder text should be 'Tìm kiếm trên Messenger'.");
    }
    @Test
    public void testSearchWithFullValidInput() throws InterruptedException {
        WebElement searchBox = driver.findElement(By.xpath("//*[@aria-autocomplete='list']"));

        searchBox.sendKeys("Ngoc Kien");

        // Wait for results to load
        Thread.sleep(3000);

        WebElement result = driver.findElement(By.xpath("(//span[contains(text(),'Ngoc Kien')])[2]"));
        Assert.assertTrue(result.getText().contains("Ngoc Kien"), "Search result should include Ngoc Kien.");

        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@aria-label='Xóa']")).click();

    }
    @Test
    public void testSearchWithFirstCharValidInput() throws InterruptedException {

        WebElement searchBox = driver.findElement(By.xpath("//*[@aria-autocomplete='list']"));
        searchBox.sendKeys("N");
        // Wait for results to load
        Thread.sleep(2000);
        WebElement result = driver.findElement(By.xpath("//div[contains(text(),'N')]"));
        Assert.assertTrue(result.getText().contains("N"), "Search result should include N.");
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[@aria-label='Xóa']")).click();
    }
    @Test
    public void testShowValueValid() throws InterruptedException {
        WebElement searchBox = driver.findElement(By.xpath("//*[@aria-autocomplete='list']"));
        searchBox.clear(); // Ensure the search box is empty before entering new text
        searchBox.sendKeys("N");

        // Wait for the results to load (use Thread.sleep() or other waiting mechanism if necessary)
        Thread.sleep(3000); // Adjust the sleep duration as necessary

        // Change the selector to match the user list items (assuming the user names are within <li> elements inside a <ul> list)
        List<WebElement> recentUsers = driver.findElements(By.xpath("//ul[@role='listbox']/li"));

        // Assert that there are exactly 10 users displayed
        Assert.assertEquals(recentUsers.size(), 10, "Exactly 10 recent users whose names start with 'N' should be displayed.");

        // Optionally, you can check if all displayed users' names start with "N"
        for (WebElement user : recentUsers) {
            Assert.assertTrue(user.getText().startsWith("N"), "User name should start with 'N'.");
        }

        // Clear the search box
        driver.findElement(By.xpath("//div[@aria-label='Xóa']")).click();
    }

    @Test
    public void testSearchWithFirstPartValidInput() throws InterruptedException {

        WebElement searchBox = driver.findElement(By.xpath("//*[@aria-autocomplete='list']"));
        Thread.sleep(2000);
        searchBox.sendKeys("Ngoc");
        // Wait for results to load
        Thread.sleep(3000);
        WebElement result = driver.findElement(By.xpath("//div[contains(text(),'Ngoc')]"));
        Assert.assertTrue(result.getText().contains("Ngoc"), "Search result should include N.");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@aria-label='Xóa']")).click();
    }
    @Test
    public void testSearchValidInputandInvalidInput() throws InterruptedException {

        WebElement searchBox = driver.findElement(By.xpath("//*[@aria-autocomplete='list']"));
        Thread.sleep(2000);
        searchBox.sendKeys("NgocAha*");
        // Wait for results to load
        Thread.sleep(3000);
        WebElement result = driver.findElement(By.xpath("//div[contains(text(),'Ngoc')]"));
        Assert.assertTrue(result.getText().contains("Ngoc"), "Search result should include N.");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@aria-label='Xóa']")).click();
    }
    @Test
    public void testSearchFirstValidInputandInvalidInput() throws InterruptedException {

        WebElement searchBox = driver.findElement(By.xpath("//*[@aria-autocomplete='list']"));
        Thread.sleep(2000);
        searchBox.sendKeys("Ngoc Aha*");
        Thread.sleep(3000);
        WebElement result = driver.findElement(By.xpath("//div[contains(text(),'Ngoc')]"));
        Assert.assertTrue(result.getText().contains("Ngoc"), "Search result should include N.");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@aria-label='Xóa']")).click();
    }
    @Test
    public void testSearchWithFullValidlowerInput() throws InterruptedException {
        WebElement searchBox = driver.findElement(By.xpath("//*[@aria-autocomplete='list']"));

        searchBox.sendKeys("ngoc kien");

        // Wait for results to load
        Thread.sleep(3000);

        WebElement result = driver.findElement(By.xpath("(//span[contains(text(),'Ngoc Kien')])[2]"));
        Assert.assertTrue(result.getText().contains("Ngoc Kien"), "Search result should include Ngoc Kien.");

        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@aria-label='Xóa']")).click();

    }
    @Test
    public void testSearchWithFullValidUpperInput() throws InterruptedException {
        WebElement searchBox = driver.findElement(By.xpath("//*[@aria-autocomplete='list']"));

        searchBox.sendKeys("NGOC KIEN");

        // Wait for results to load
        Thread.sleep(3000);

        WebElement result = driver.findElement(By.xpath("(//span[contains(text(),'Ngoc Kien')])[2]"));
        Assert.assertTrue(result.getText().contains("Ngoc Kien"), "Search result should include Ngoc Kien.");

        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@aria-label='Xóa']")).click();

    }
    @Test
    public void testSearchClickEnter() throws InterruptedException {
        WebElement searchBox = driver.findElement(By.xpath("//*[@aria-autocomplete='list']"));
        Thread.sleep(2000);
        searchBox.sendKeys("Ngoc Kien");
        searchBox.sendKeys(Keys.RETURN);
        Thread.sleep(3000);
        WebElement result = driver.findElement(By.xpath("(//span[contains(text(),'Ngoc Kien')])[2]"));
        Assert.assertTrue(result.getText().contains("Ngoc Kien"), "Search result should include Ngoc Kien.");

        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@aria-label='Xóa']")).click();
    }
    @Test
    public void testEmptySearch() throws InterruptedException {
        WebElement searchBox = driver.findElement(By.xpath("//*[@aria-autocomplete='list']"));
        searchBox.sendKeys("");
        List<WebElement> recentUsers = driver.findElements(By.cssSelector("//ul[@role='listbox']")); // Change the selector to match the user list items
        // Assert that there are exactly 10 users displayed
        Assert.assertEquals(recentUsers.size(), 10, "Exactly 10 recent users should be displayed when search query is empty.");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@aria-label='Xóa']")).click();
    }
    @Test
    public void testSearchRecent() throws InterruptedException {
        driver.findElement(By.xpath("//*[@aria-autocomplete='list']")).click();
        WebElement allUsers = driver.findElement(By.xpath("//ul[@role='listbox']"));
        List<WebElement> recentUsers = driver.findElements(By.cssSelector(".user-list-item")); // Change the selector to match the user list items
        // Assert that there are exactly 10 users displayed
        Assert.assertEquals(recentUsers.size(), 10, "Exactly 10 recent users should be displayed when search query is empty.");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@aria-label='Xóa']")).click();
        Assert.assertNotNull(allUsers, "All users should be displayed when search query is empty.");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@aria-label='Xóa']")).click();
    }
    @Test
    public void testSpaceSearch() throws InterruptedException {
        WebElement searchBox = driver.findElement(By.xpath("//*[@aria-autocomplete='list']"));
        searchBox.sendKeys(" ");
        WebElement allUsers = driver.findElement(By.id("all-users"));
        Assert.assertNotNull(allUsers, "All users should be displayed when search query is empty.");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@aria-label='Xóa']")).click();
    }
    @Test
    public void testSearchWithInvalidInput() throws InterruptedException {
        WebElement searchBox = driver.findElement(By.xpath("//*[@aria-autocomplete='list']"));
        searchBox.sendKeys(" ");
        Thread.sleep(2000);

        // Locate the no-results message element
        WebElement noResultsMessage = driver.findElement(By.xpath("(//span[text()='Không tìm thấy kết quả']")); // Change this if needed
        Thread.sleep(3000);
        // Assert that the no-results message is displayed and contains the expected text
        Assert.assertNotNull(noResultsMessage, "No results message should be displayed.");
        Assert.assertEquals(noResultsMessage.getText().trim(), "Không tìm thấy kết quả", "The no results message should be 'Không tìm thấy kết quả'.");

        // Clear the search box
        driver.findElement(By.xpath("//div[@aria-label='Xóa']")).click();
    }


    @Test
    public void testAutoSuggestionFeature() {
        WebElement searchBox = driver.findElement(By.xpath("//*[@aria-autocomplete='list']"));
        searchBox.sendKeys("Ng");
        WebElement suggestion = driver.findElement(By.xpath("//ul[@role='listbox']")); // Thay bằng class hoặc locator của gợi ý tự động
        Assert.assertTrue(suggestion.isDisplayed(), "Auto suggestions should be displayed.");
        driver.findElement(By.xpath("//div[@aria-label='Xóa']")).click();

    }



    @Test
    public void testSearchWhileTyping() {
        WebElement searchBox = driver.findElement(By.xpath("//*[@aria-autocomplete='list']"));
        searchBox.sendKeys("Ng");
        WebElement partialResult = driver.findElement(By.xpath("//ul[@role='listbox']")); // Thay bằng id hoặc locator của kết quả tìm kiếm khi đang nhập
        Assert.assertTrue(partialResult.getText().contains("Ngoc"), "Partial search results should include 'Jane'.");
    }


}
