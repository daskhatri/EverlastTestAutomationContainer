package com.finexus.tests;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.finexus.util.TestUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

public class T24_ProductCatalogue {
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

	@DataProvider
	public Object[][] getLoginData() {
		Object data[][] = TestUtil.getTestData("ProductCatalogueData");
		
		return data;
	}
	@Test(dataProvider = "getLoginData")
	public void testT24_ProductCatalogue(String userName, String password, String loginUrl) throws Exception {
//	@Test
//	public void testT24_ProductCatalogue() throws Exception {
//		driver.get("http://52.220.141.104:8080/BrowserWeb/servlet/BrowserServlet");
		
		driver.get(loginUrl);
		driver.findElement(By.id("signOnName")).clear();
		driver.findElement(By.id("signOnName")).sendKeys(userName);
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.name("login")).submit();
		// ERROR: Caught exception [ERROR: Unsupported command [selectFrame | index=1 |
		// ]]

//		List<WebElement> ele = driver.findElements(By.tagName("frame"));
//		System.out.println("Number of frames in a page :" + ele.size());
		Thread.sleep(10000);
		driver.switchTo().frame(1);
		
		driver.findElement(By.xpath("//*[@id=\"pane_\"]/ul[1]/li/span")).click();

//		String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL,Keys.RETURN); 
//		driver.findElement(By.linkText("Product Catalog")).sendKeys(selectLinkOpeninNewTab);
		
		// considering that there is only one tab opened in that point.
		String userMenuWindow = driver.getWindowHandle();
		driver.findElement(By.linkText("Product Catalog")).click();
		Thread.sleep(6000);
		ArrayList<String> productCatalogueWindow = new ArrayList<String>(driver.getWindowHandles());
		productCatalogueWindow.remove(userMenuWindow);
//		
		// change focus to new tab
		driver.switchTo().window(productCatalogueWindow.get(0));
		

		// ERROR: Caught exception [ERROR: Unsupported command [selectWindow | win_ser_1
		// | ]]
		// ERROR: Caught exception [ERROR: Unsupported command [selectFrame | index=2 |
		// ]]

		Thread.sleep(8000);
		driver.switchTo().frame(2);
		driver.findElement(By.xpath("//*[@id=\"treestop3\"]")).click();
		driver.findElement(
				By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Term Deposits'])[1]/following::img[1]"))
				.click();
		
		
		driver.findElement(By.xpath("//body")).click();
		// ERROR: Caught exception [ERROR: Unsupported command [selectFrame |
		// relative=parent | ]]
		// ERROR: Caught exception [ERROR: Unsupported command [selectFrame | index=4 |
		// ]]

		// Products Script
		driver.switchTo().parentFrame();
		driver.switchTo().frame(4);

		// considering that there is only one tab opened in that point.
//		String productCatWin = driver.getWindowHandle();
		driver.findElement(By.xpath("//*[@id=\"r1\"]/td[3]/a/img")).click();
		ArrayList<String> newArrangementActivityWin = new ArrayList<String>(driver.getWindowHandles());
//		newArrangementActivityWin.remove(productCatWin);
		// change focus to new tab
		driver.switchTo().window(newArrangementActivityWin.get(2));

//		driver.findElement(By.xpath("//*[@id=\"r1\"]/td[3]/a/img")).click();
		Thread.sleep(10000);

		// ERROR: Caught exception [ERROR: Unsupported command [selectWindow | win_ser_2
		// | ]]
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			Assert.fail(verificationErrorString);
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

	private void printFramesDetails(WebDriver driver) {

		List<WebElement> ele = driver.findElements(By.tagName("frame"));
		for (WebElement el : ele) {
			System.out.println("Iframe Id: " + el.getAttribute("id"));
			System.out.println("Frame name :" + el.getAttribute("name"));
		}

	}

}
