import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FriendListTest {

    private WebDriver driver;
    private List<String> expectedFriends;

    @Before
    public void setUp() throws Exception {
        // Thiết lập WebDriver
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        Thread.sleep(2000);
        driver.get("https://https://dev-admin.kelick.io/");
        Thread.sleep(3000);
        WebElement email = driver.findElement(By.name("email"));
        email.sendKeys("admin@gmail.com");
        WebElement pass = driver.findElement(By.name("pass"));
        pass.sendKeys("123456");
        driver.findElement(By.name("login")).click();
        Thread.sleep(3000);
        expectedFriends = fetchFriendsFromDatabase();
    }

    @Test
    public void testDisplayAllFriends() {
        // Lấy tất cả các phần tử bạn bè từ giao diện người dùng
        List<WebElement> friendItems = driver.findElements(By.cssSelector("#friend-list .friend-item"));

        // Kiểm tra số lượng phần tử
        assertEquals(expectedFriends.size(), friendItems.size());

        // Kiểm tra từng phần tử
        for (String expectedFriend : expectedFriends) {
            boolean found = false;
            for (WebElement item : friendItems) {
                if (item.getText().equals(expectedFriend)) {
                    found = true;
                    break;
                }
            }
            assertTrue("Friend '" + expectedFriend + "' should be displayed in the list.", found);
        }
    }

    private List<String> fetchFriendsFromDatabase() throws Exception {
        List<String> friends = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/your_database"; // Thay đổi URL cơ sở dữ liệu cho phù hợp
        String user = "your_username";
        String password = "your_password";

        // Kết nối đến cơ sở dữ liệu
        Connection conn = DriverManager.getConnection(url, user, password);
        Statement stmt = conn.createStatement();
        String sql = "SELECT name FROM friends"; // Thay đổi truy vấn SQL cho phù hợp
        ResultSet rs = stmt.executeQuery(sql);

        // Lấy dữ liệu từ ResultSet
        while (rs.next()) {
            String name = rs.getString("name");
            friends.add(name);
        }

        // Đóng kết nối
        rs.close();
        stmt.close();
        conn.close();

        return friends;
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
