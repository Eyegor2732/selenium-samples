import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class SmartFlight2 {

	public static void main(String[] args) {
		WebDriver driver = new ChromeDriver();
		Wait<WebDriver> wait;
		Random rand = new Random();

		driver.get("https://smartflight.com/"); 
			
		WebElement dropdownFrom = driver.findElement(By.className("_2TTw-Bfv"));
		dropdownFrom.click();
		
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));	
		List<WebElement> dropdownFromList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("[id*='react-select-2-option-'] ._3_hrlgCc")));
		int randFrom = rand.nextInt(dropdownFromList.size());
		
		////
		System.out.println("\nDeparture airports codes list:");
		for (WebElement item : dropdownFromList) {
			System.out.println(item.getText());
		}
		////
		
		WebElement dropdownFromListSelect = dropdownFromList.get(randFrom);
		String dropdownFromListSelectText = dropdownFromListSelect.getText();
		dropdownFromListSelect.click();
		
		WebElement fromPort = driver.findElement(By.cssSelector("[class='_2TTw-Bfv'] ._3_hrlgCc"));
		String fromPortText = fromPort.getText();
		
		// verify selected item in From dropdown is shown as result
		Assert.assertEquals(fromPortText, dropdownFromListSelectText);
		System.out.println("\nDeparture airport code: " + fromPortText);
		
		List<WebElement> dropdownToList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("[id*='react-select-3-option-'] ._3_hrlgCc")));
		int randTo = rand.nextInt(dropdownToList.size());
		
		////
		System.out.println("\nArrival airports codes list:");
		for (WebElement item : dropdownToList) {
			System.out.println(item.getText());
		}
		////

		// verify selected departure airport is not shown in arrival dropdown list
		verifyItemNotThere(dropdownToList, fromPortText);
		
		WebElement dropdownToListSelect = dropdownToList.get(randTo);
		String dropdownToListSelectText = dropdownToListSelect.getText();
		dropdownToListSelect.click();

		List<WebElement> fromToPort = driver.findElements(By.cssSelector("._2TTw-Bfv ._3_hrlgCc"));
		WebElement toPort = fromToPort.getLast();
		String toPortText = toPort.getText();
		
		// verify selected item in To dropdown is shown as result
		Assert.assertEquals(toPortText, dropdownToListSelectText);
		System.out.println("\nArrival airport code: " + toPortText);
		// verify selected results in To and From are not equal
		Assert.assertFalse(fromPortText.equals(toPortText));
		
		System.out.println("\nNo errors. Test Passed.");
		driver.quit();
		
	}

	private static void verifyItemNotThere(List<WebElement> list, String text) {
		for(WebElement item : list) {
			Assert.assertFalse(item.getText().equals(text));
		}
	}

}