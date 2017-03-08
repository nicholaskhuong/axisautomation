package com.abb.ventyx.axis.customer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseTestCase;
@ALM(id = "3") 
public class SearchTC extends BaseTestCase {
	@Test
	public void searchGoogle() {
	driver.navigate().to("http://google.com.vn" + "/");
    driver.findElement(By.id("lst-ib")).clear();
    driver.findElement(By.id("lst-ib")).sendKeys("khuong dai nghia");
    driver.findElement(By.id("_fZl")).click();
    driver.findElement(By.linkText("Khuong Dai Nghia | S??n Ch??i C??ng Ngh???")).click();
	}
}
