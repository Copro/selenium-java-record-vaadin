
package integrationtest;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode;

/**
 * Simple example of plain Selenium usage skipping recording and using the Chrome browser.
 *
 * @author Copro
 *
 */
public class SeleniumContainerTest {

	private final static String NETCUP_FORUM_URL = "https://forum.netcup.de/";
	private final static String SEARCH_BUTTON_ID = "userPanelSearchButton";
	private final static String SEARCH_INPUT_ID = "pageHeaderSearchInput";

	@Rule
	@SuppressWarnings("rawtypes")
	public BrowserWebDriverContainer chrome = new BrowserWebDriverContainer()
			.withCapabilities(new ChromeOptions())
			.withRecordingMode(VncRecordingMode.RECORD_ALL, new File("target"));

	/**
	 * Simple test opening the netcup Forum and searching for 'Selenium'.
	 */
	@Test
	public void simplePlainSeleniumTest() throws InterruptedException {
		RemoteWebDriver driver = chrome.getWebDriver();

		System.out.println("Start Chrome and open URL: " + NETCUP_FORUM_URL);
		driver.get(NETCUP_FORUM_URL);

		System.out.println("Click the Search button with ID: " + SEARCH_BUTTON_ID);
		WebElement searchButton = driver.findElementById(SEARCH_BUTTON_ID);
		searchButton.click();

		System.out.println("Fill in the search input element: " + SEARCH_INPUT_ID);
		WebElement searchInput = driver.findElementById(SEARCH_INPUT_ID);
		searchInput.sendKeys("Selenium");
		searchInput.submit();

		System.out.println("Waiting 200 milliseconds.");
		Thread.sleep(200);

		System.out.println("Verifying that the Thread 'Windows auf VPS 200 ?' shows up in the search results.");
		assertThat(driver.getPageSource())
				.as("The Thread 'Windows auf VPS 200 ?' shows up in the search results.")
				.contains("Windows auf VPS 200 ?");
	}

	@AfterAll
	public void tearDown() {
		System.out.println("End. We are Done here and quitting WebDriver by stopping container");
		chrome.stop();
	}
}
