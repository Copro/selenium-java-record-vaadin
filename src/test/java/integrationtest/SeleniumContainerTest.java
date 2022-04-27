
package integrationtest;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Simple example of plain Selenium usage skipping recording and using the Chrome browser.
 *
 * @author Copro
 *
 */
@Testcontainers
public class SeleniumContainerTest {

	private final static String NETCUP_FORUM_URL = "https://forum.netcup.de/";
	private final static String SEARCH_BUTTON_ID = "userPanelSearchButton";
	private final static String SEARCH_INPUT_ID = "pageHeaderSearchInput";

	@Container
	@SuppressWarnings("rcawtypes")
	private static final BrowserWebDriverContainer BROWSER_CONTAINER = new BrowserWebDriverContainer()
			.withCapabilities(new ChromeOptions())
			.withRecordingMode(VncRecordingMode.RECORD_ALL, new File("target"));

	private static RemoteWebDriver browser;

	@BeforeAll
	static void configureBrowser() {
		browser = BROWSER_CONTAINER.getWebDriver();
	}

	/**
	 * Simple test opening the netcup Forum and searching for 'Selenium'.
	 */
	@Test
	public void simplePlainSeleniumTest() throws InterruptedException {

		System.out.println("Start Chrome and open URL: " + NETCUP_FORUM_URL);
		browser.get(NETCUP_FORUM_URL);

		System.out.println("Click the Search button with ID: " + SEARCH_BUTTON_ID);
		WebElement searchButton = browser.findElementById(SEARCH_BUTTON_ID);
		searchButton.click();

		System.out.println("Fill in the search input element: " + SEARCH_INPUT_ID);
		WebElement searchInput = browser.findElementById(SEARCH_INPUT_ID);
		searchInput.sendKeys("Selenium");
		searchInput.submit();

		System.out.println("Waiting 200 milliseconds.");
		Thread.sleep(200);

		System.out.println("Verifying that the Thread 'Windows auf VPS 200 ?' shows up in the search results.");
		assertThat(browser.getPageSource())
				.as("The Thread 'Windows auf VPS 200 ?' shows up in the search results.")
				.contains("Windows auf VPS 200 ?");

		System.out.println("End. We are Done here and quitting WebDriver / Browser");
		browser.quit();
	}
}
