import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class e2eSpiceJet {

	public static void main(String[] args) throws InterruptedException {
		WebDriver driver = new ChromeDriver();
		Wait<WebDriver> wait;
		
		driver.get("https://www.spicejet.com/"); 
		
		// Select "Round Trip" radio button
		WebElement roundTripRadio = driver.findElement(By.cssSelector("[data-testid='round-trip-radio-button']"));
		roundTripRadio.click(); 

		// Select "One Way" radio button
		WebElement oneWayRadio = driver.findElement(By.cssSelector("[data-testid='one-way-radio-button']")); 
		oneWayRadio.click();
		
		// Verify "Return Date" datepicker is dimmed
		WebElement returnPicker = driver.findElement(By.cssSelector("[data-testid='return-date-dropdown-label-test-id']"));
		String returnPickerBgColor = returnPicker.getCssValue("background-color").toString();
		Assert.assertTrue(returnPickerBgColor.equals("rgba(238, 238, 238, 1)"));
		
		// Select "Senior Citizen" discount checkbox
		WebElement seniorCheckbox = driver.findElement(By.xpath("//*[text()='Senior Citizen']"));
		if(!seniorCheckbox.isSelected()) {
			seniorCheckbox.click();
		}
		
		// Verify there are 5 discount checkboxes
		List<WebElement> discountCheckboxes = driver.findElements(By.cssSelector(".css-1dbjc4n.r-1awozwy.r-1loqt21.r-18u37iz.r-15d164r.r-1otgn73"));
		int discountCheckboxesSize = discountCheckboxes.size();
		Assert.assertEquals(discountCheckboxesSize, 6);
		
		// Select "Round Trip" radio button
		roundTripRadio.click();
		
		// Tap on "From" dropdown
		WebElement dropdownDeparture = driver.findElement(By.xpath("//*[text()='From']")); 
		dropdownDeparture.click();
		
		// Select airport code "BLR"
		WebElement departureChoice = driver.findElement(By.xpath("//*[text()='BLR']"));
		departureChoice.click();
		
		Thread.sleep(1000);
		
//		// Tap on "To" dropdown
//		WebElement dropdownTo = driver.findElement(By.xpath("//*[text()='Select Destination']")); 
//		dropdownTo.click();
		
		// Select airport code "MAA". Dropdown "To" has been open automaticallly after selecting "From" destination.
		wait = new WebDriverWait(driver, Duration.ofSeconds(2));
		WebElement arravileChoice = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='MAA']")));
		arravileChoice.click();

		// Select date in "Depart Date" datepicker
		String dDay = "25";
		String dMonth = "October";
		String dYear = "2024";
		WebElement departureDayCell = driver.findElement(By.xpath("//*[@data-testid='undefined-month-" + dMonth + "-" + dYear + "']//*[@data-testid='undefined-calendar-day-" + dDay + "']"));
		departureDayCell.click();
		
		String rDay = "13";
		String rMonth = "November";
		String rYear = "2024";
		WebElement returnDayCell = driver.findElement(By.xpath("//*[@data-testid='undefined-month-" + rMonth + "-" + rYear + "']//*[@data-testid='undefined-calendar-day-" + rDay + "']"));
		
		returnDayCell.click();

		// Select number of adult passengers in "Passengers" dynamic dropdown"
		
		WebElement dropdownPax = driver.findElement(By.cssSelector("[data-testid='home-page-travellers']"));
		dropdownPax.click();
		WebElement plusAdult = driver.findElement(By.cssSelector("[data-testid='Adult-testID-plus-one-cta']"));
		int adultNumber = 4;
		WebElement adultTitle = driver.findElement(By.xpath("//*[@data-testid='Adult-testID-minus-one-cta']/following-sibling::div"));
		while(Integer.parseInt(adultTitle.getText()) < adultNumber) {
			plusAdult.click();
		}

		
		WebElement buttonDone = driver.findElement(By.cssSelector("[data-testid='home-page-travellers-done-cta']"));
		buttonDone.click();
		
		String dropdownPaxText = driver.findElement(By.xpath("//*[text()='Passengers']/following-sibling::*")).getText();
		Assert.assertTrue(dropdownPaxText.contains(adultNumber + " Adult"));

		// Select currency code in "Currency" static dropdown
		WebElement dropdownCurrency = driver.findElement(By.xpath("//*[text()='Currency']"));
		dropdownCurrency.click();
		List<WebElement> currencyList = driver.findElements(By.cssSelector(".r-1yt7n81.r-1otgn73"));
		for(WebElement currency : currencyList) {
			if (currency.getText().equals("EUR")) {
				currency.click();
				break;
			}
		}	

		// Tap on "Search Flight" button
		WebElement searchButton = driver.findElement(By.cssSelector("[data-testid='home-page-flight-cta']"));
		searchButton.click();
		
		System.out.println("\nNo errors. Test Passed.");
		driver.quit();
		
	}
}