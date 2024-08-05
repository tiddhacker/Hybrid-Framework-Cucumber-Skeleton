import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class testl {

    public static void openBrowser(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com");
    }

    public static void main(String[] args){
        openBrowser();
    }
}
