import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class BrokenLinksStatusNG {

	@Test
	public void brokenLinkd() throws InterruptedException, MalformedURLException, IOException, URISyntaxException{
		WebDriver driver = new ChromeDriver();
		SoftAssert softassert = new SoftAssert();

		driver.get("https://www.cbsnews.com/texas/latest/links-numbers/");

		List<WebElement> allLinks = driver.findElements(By.cssSelector("a[href]"));
		
		for(WebElement link : allLinks) {
			String url = link.getAttribute("href");
			String label = link.getText();
			HttpURLConnection conn = (HttpURLConnection)new URI(url).toURL().openConnection();
		    conn.setRequestMethod("HEAD");
		    conn.connect();
		    int status = conn.getResponseCode();	    

			softassert.assertTrue((status < 400), "\nLabel: " + label 
					+ "\nURL: " + url 
					+ "\nResponse status: " + status 
					+ "\nResponse status should be smaller than 400");
		};
		softassert.assertAll();
		
		driver.quit();
	}
}