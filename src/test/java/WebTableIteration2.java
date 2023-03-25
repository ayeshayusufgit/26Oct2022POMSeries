

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebTableIteration2 {
	static WebDriver driver;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.w3schools.com/html/html_tables.asp");
		List<WebElement> eleList = driver.findElements(By.xpath("//table[@id='customers']//tr"));
		System.out.println(eleList.size());
		
		for (int i = 2; i <= eleList.size(); i++) {
			List<WebElement> rowList = driver.findElements(By.xpath("//table[@id='customers']//tr[" + i + "]//td"));
			for (WebElement element : rowList) {
				System.out.print(element.getText()+"->");
			}
			System.out.println();
		}
	}
}
