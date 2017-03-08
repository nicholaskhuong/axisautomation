package com.abb.ventyx.axis.dashboard;

import io.appium.java_client.android.AndroidDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class TestAppium {

	public static void main(String[] args) throws MalformedURLException {

		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability("deviceName", "My Phone");
		desiredCapabilities.setCapability("platformName", "Android");
		desiredCapabilities.setCapability("platformVersion", "4.3");
		desiredCapabilities.setCapability(CapabilityType.BROWSER_NAME,
				"Browser");
		// Create object of URL class and specify the appium server address
		URL url = null;
		try {
			url = new URL("http://127.0.0.1:4723/wd/hub");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		AndroidDriver androidDriver = new AndroidDriver<WebElement>(url,
				desiredCapabilities);
		androidDriver.get("http://www.facebook.com");
		androidDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		androidDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// print the title
		System.out.println("Title " + androidDriver.getTitle());

		// enter username
		androidDriver.findElement(By.name("email"))
				.sendKeys("mukesh@gmail.com");

		// enter password
		androidDriver.findElement(By.name("pass")).sendKeys("mukesh_selenium");

		// click on submit button
		androidDriver.findElement(By.id("u_0_5")).click();

		// close the browser
		androidDriver.quit();
	}

}
