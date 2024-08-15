import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class BrokenLinksStatusMultiFile {

	public static void main(String[] args) throws InterruptedException, MalformedURLException, IOException, URISyntaxException{
		WebDriver driver = new ChromeDriver();
		
		driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30)); 

		driver.get("https://www.cbsnews.com/texas/latest/links-numbers/");

		List<WebElement> allLinks = driver.findElements(By.cssSelector("a[href]"));
		
		boolean isBroken = false;
		// format pattern can be chosen depends on how often this test should be run 
//    	String timestamp = timeStamp(LocalDateTime.now(), "dd-MM-yyyy");
    	String timestamp = timeStamp(LocalDateTime.now(), "dd-MM-yyyy HH:mm:ss");
    	
		for(WebElement link : allLinks) {
			String url = link.getAttribute("href");
			String label = link.getText();
			HttpURLConnection conn = (HttpURLConnection)new URI(url).toURL().openConnection();
		    conn.setRequestMethod("HEAD");
		    conn.connect();
		    
		    int res = conn.getResponseCode();
		    
		    String text = "";
		    
		    if(res > 399) {
		    	if(isBroken == false) {
		    		text = text + "Broken links are:\n\n";
		    	}
		    	text = text + "Label: " + label + "\n";
		    	text = text + "URL: " + url + "\n";
		    	text = text + "http response code: " + res + "\n\n";
		    	writeToFile(text, timestamp);
				isBroken=true;
		    }
		};
		
		if (!isBroken) writeToFile("No broken links have been found in this test run.", timestamp);
		
		System.out.println("\nNo errors. Test Passed.");
		driver.quit();
	}

	private static void writeToFile(String text, String timestamp) throws IOException {
		 FileWriter myWriter = new FileWriter("BrokenLinks_" + timestamp + ".txt", true);
		 myWriter.write(text);
		 myWriter.close();
	}
	
	private static String timeStamp(LocalDateTime ldt, String pattern) {
		LocalDateTime timestampDate = ldt;
		DateTimeFormatter timestampDateF = DateTimeFormatter.ofPattern(pattern);
    	return timestampDate.format(timestampDateF);
	}
	
}