package com.abb.ventyx.saas;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.abb.ventyx.utilities.BaseTestCase;

public class SearchTC extends BaseTestCase {
	@Test
	public void searchGoogle() {
	WebDriver driver= super.driver;
	driver.get("http://google.com.vn" + "/");
    driver.findElement(By.id("lst-ib")).clear();
    driver.findElement(By.id("lst-ib")).sendKeys("khuong dai nghia");
    driver.findElement(By.id("_fZl")).click();
    driver.findElement(By.linkText("Khuong Dai Nghia | Sân Chơi Công Nghệ")).click();
	}
}
