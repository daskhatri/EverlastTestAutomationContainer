package com.finexus.tests;

import java.util.regex.Pattern;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.testng.ITestResult;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;

import static org.testng.Assert.*;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hwpf.usermodel.DateAndTime;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class T24AuthorizationFailCase {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@BeforeClass
	public static void setupClass() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeMethod
	public void setUp() throws Exception {
		driver = new ChromeDriver();
		baseUrl = "https://www.katalon.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testT24AuthorizationFailCase() throws Exception {

		

			driver.get("http://52.220.141.104:8080/BrowserWeb/servlet/BrowserServlet");
			driver.findElement(By.id("signOnName")).click();
			driver.findElement(By.id("signOnName")).clear();
			driver.findElement(By.id("signOnName")).sendKeys("ALI001");
			driver.findElement(By.id("password")).clear();
			driver.findElement(By.id("password")).sendKeys("demotest");
			driver.findElement(By.id("sign-in")).click();
			// ERROR: Caught exception [ERROR: Unsupported command [selectFrame | index=1 |
			// ]]

			Thread.sleep(10000);
			driver.switchTo().frame(1);

			driver.findElement(By.xpath("//*[@id=\"pane_\"]/ul[1]/li/span")).click();

			driver.findElement(By.xpath(
					"(.//*[normalize-space(text()) and normalize-space(.)='Product Catalog'])[1]/following::span[1]"))
					.click();

			// considering that there is only one tab opened in that point.
			String userMenuWindow = driver.getWindowHandle();
			driver.findElement(By.linkText("Find Account")).click();
			Thread.sleep(3000);
			ArrayList<String> findAccountWindow = new ArrayList<String>(driver.getWindowHandles());
			findAccountWindow.remove(userMenuWindow);
			// change focus to new tab
			driver.switchTo().window(findAccountWindow.get(0));

			driver.findElement(By
					.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Authorised'])[1]/following::span[1]"))
					.click();

//			driver.findElement(By.linkText("Find")).click();
//			Thread.sleep(5000);
//			String unAthorizedWindow = driver.getWindowHandle();
//			// UN AUTHORIZED OVERVIEW image button
////		/html/body/table/tbody/tr/td/table/tbody/tr[2]/td/div[3]/div/form/div/table/tbody/tr[2]/td[2]/div[2]/div/table[1]/tbody/tr/td[7]/a/img
//			driver.findElement(By.xpath("//*[(@title='Overview')]")).click();
//			ArrayList<String> arrangementOverviewWindow = new ArrayList<String>(driver.getWindowHandles());
//			arrangementOverviewWindow.remove(unAthorizedWindow);
			
			driver.findElement(By.linkText("Find")).click();
			Thread.sleep(5000);
			String unAthorizedWindow = driver.getWindowHandle();

			// ********** UN AUTHORIZED OVERVIEW image button *******************

			driver.findElement(By.xpath("//*[(@title='Overview')]")).click();
			ArrayList<String> arrangementOverviewWindow = new ArrayList<String>(driver.getWindowHandles());
			arrangementOverviewWindow.remove(unAthorizedWindow);

			
			// change focus to new tab
			driver.switchTo().window(arrangementOverviewWindow.get(1));
			// Approve button click
			String arrangementWin = driver.getWindowHandle();

			driver.findElement(By.xpath("//*[(@class='iconLink')]")).click();
			ArrayList<String> allWindowsList = new ArrayList<String>(driver.getWindowHandles());
			allWindowsList.remove(arrangementWin);
			driver.switchTo().window(allWindowsList.get(2));

			Thread.sleep(5000);
			// Approve window i.e opening in new window
			driver.findElement(By.xpath(
					"(.//*[normalize-space(text()) and normalize-space(.)='AA.ARRANGEMENT.ACTIVITY,INPUT.CALL.ACCT.F2B'])[1]/preceding::img[8]"))
					.click();

			Thread.sleep(5000);
			String txnMessage = driver
					.findElement(By.xpath("//*[@id=\"messages\"]/tbody/tr[2]/td[2]/table[2]/tbody/tr/td")).getText();

			if (txnMessage.toLowerCase().indexOf("Txn Complete".toLowerCase()) == -1) {
				// Call take screenshot function
//				T24AuthorizationFailCase.takeSnapShot(driver, "D://test.png");
				throw new Exception("Transaction failed");
			}
		
	}

	/**
	 * This function will take screenshot
	 * 
	 * @param webdriver
	 * @param fileWithPath
	 * @throws Exception
	 */
	public static void takeSnapShot(WebDriver webdriver, String fileWithPath) throws Exception {

		// Convert web driver object to TakeScreenshot
		TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
		// Call getScreenshotAs method to create image file
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
		// Move image file to new destination
		File DestFile = new File(fileWithPath);
		// Copy file at destination
		FileUtils.copyFile(SrcFile, DestFile);
	}

	@AfterMethod
	public void takeScreenShotOnFailure(ITestResult testResult) throws IOException {
		if (testResult.getStatus() == ITestResult.FAILURE) {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File("errorScreenshots\\" + testResult.getName() + "-"
					+ Arrays.toString(testResult.getParameters()) + ".jpg"));
		}
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		T24AuthorizationFailCase.takeSnapShot(driver, "errorScreenshots\\T24AuthorizationFailCase-" + driver.getTitle() + ".png");
		driver.getTitle();
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}