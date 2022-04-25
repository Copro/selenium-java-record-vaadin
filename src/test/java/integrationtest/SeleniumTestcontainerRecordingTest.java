
package integrationtest;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode;
import org.testcontainers.containers.VncRecordingContainer.VncRecordingFormat;


/**
 * Simple test class to record a session with vaadin.com.
 *
 * @author Copro
 *
 */
public class SeleniumTestcontainerRecordingTest {

	private File localDir = new java.io.File(".");

	@Rule
	public BrowserWebDriverContainer<?> firefox = new BrowserWebDriverContainer<>()
			.withCapabilities(new FirefoxOptions())
			.withRecordingMode(VncRecordingMode.RECORD_ALL, localDir, VncRecordingFormat.MP4);

	@Test
	public void testWebDriverBackedSelenium() throws InterruptedException {
		
		RemoteWebDriver driver = firefox.getWebDriver();
		System.out.println("LOG Video Output to: " + localDir.getAbsolutePath());

		driver.get("https://www.vaadin.com");
		String pageTitle = driver.getTitle();
		System.out.println("TITEL: " + pageTitle);
		assertThat(pageTitle)
				.as("the page title does not contain 'Vaadin'")
				.contains("Vaadin");
		driver.findElement(By.id("haas-login-button")).click();
		System.out.println("Waiting 2000 milliseconds.");
		Thread.sleep(2000);
		WebElement elemUser = driver.findElement(By.id("username"));
		elemUser.sendKeys("USERNAMEHERE");
		WebElement elemPass = driver.findElement(By.id("password"));
		elemPass.sendKeys("PASSWORDHERE");
		WebElement loginButt = driver.findElement(By.xpath("//button[@value='LOGIN']"));
		loginButt.click();
		System.out.println("Waiting 500 milliseconds.");
		Thread.sleep(500);
		driver.findElement(By.id("haas-profile-button")).click();

		List<WebElement> profileLinks = driver.findElements(By.className("haas-profile-menu-item"));
		profileLinks.get(2).click();
		System.out.println("Waiting 2000 milliseconds.");
		Thread.sleep(2000);

		List<WebElement> certEntries = driver.findElements(By.className("certifications-list-item"));
		assertThat(certEntries)
				.as("there are not between 3 and 7 certifications listed. (a new account should have 6)")
				.hasSizeBetween(1, 7);
		System.out.println(certEntries.get(1).getText());
		System.out.println("Waiting 2000 milliseconds.");
		Thread.sleep(2000);
	}
}
