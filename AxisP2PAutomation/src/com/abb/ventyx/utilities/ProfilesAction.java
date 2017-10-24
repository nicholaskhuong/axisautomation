package com.abb.ventyx.utilities;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.Profiles;
import com.abb.ventyx.axis.objects.pagedefinitions.Profiles;

public class ProfilesAction {

	WebDriver driver;
	public ProfilesAction(WebDriver driver) {
		this.driver = driver;
	}

	public int countRow(String tableCSS) {
		WebElement baseTable = driver.findElement(By.xpath(tableCSS));
		List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
		int sumRow = tableRows.size();
		return sumRow;
	}
	
	public void clickSystemConfigurationMenu(){
		WebElement axisConfigParentButton = (new WebDriverWait(driver, 120))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(AxisConfigMenu.CUSTOMER_MAINTENANCE)));
		axisConfigParentButton.click();
	}
	public void filterProfilebyDocumentType(String filterValue) throws InterruptedException{
		// Enter filter value
		WebElement filterProfileName = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Profiles.DOC_TYPE_FILTER)));
		filterProfileName.sendKeys(filterValue);
		Thread.sleep(2000);

	}

	public void filterProfilebyProfileName(String filterValue) throws InterruptedException{
		// Click Filter Icon
		WebElement filterButton = (new WebDriverWait(driver, 20))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#HeaderMenuBar > span:nth-child(1)")));
		filterButton.click();
		Thread.sleep(2000);
		// Enter filter value
		WebElement filterProfileName = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Profiles.PROFILE_NAME_FILTER)));
		filterProfileName.sendKeys(filterValue);
		Thread.sleep(2000);

	}
	
	public void enterValueTofilterProfile(String filterValue) throws InterruptedException{	
		// Enter filter value
//		WebElement filterProfileName = (new WebDriverWait(driver, 30))
//				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Profiles.Profile_NAME_FILTER)));
//		filterProfileName.sendKeys(filterValue);
//		Thread.sleep(2000);

	}

	public void clickProfileSubMenu() throws InterruptedException{
		WebElement axisProfilesMenu = (new WebDriverWait(driver, 20))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(AxisConfigMenu.PROFILES)));
		axisProfilesMenu.click();
		Thread.sleep(1000);

	}
	public void clickAddButton() throws InterruptedException{
		WebElement addProfileButton = (new WebDriverWait(driver, 20))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(Profiles.ADD_PROFILE)));
		addProfileButton.click();
		Thread.sleep(1000);
	}
	
	public void displayProfileWindowPopUp() throws InterruptedException{
		WebElement displayTitle = (new WebDriverWait(driver, 20))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(Profiles.TITLE_CREATE)));
		Thread.sleep(1000);
	}
	
	public void enterProfileName(String ProfileName) throws InterruptedException{
//		WebElement ProfileNameElm = (new WebDriverWait(driver, 20))
//				.until(ExpectedConditions.presenceOfElementLocated(By.id(Profiles.Profile_NAME)));
//		ProfileNameElm.sendKeys(ProfileName);
//		Thread.sleep(1000);
	}
	
	public void selectDocumentType(String documentTypeCSS) throws InterruptedException{
		WebElement ProfileDocType = (new WebDriverWait(driver, 20))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(Profiles.DOCUMENT_TYPE)));
		ProfileDocType.click();
		Thread.sleep(1000);
		WebElement POAckType = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(documentTypeCSS)));
		POAckType.click();
	}
	
	public void selectUserType(String userTypeCSS){
		WebElement adminUserType = (new WebDriverWait(driver, 20))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(userTypeCSS)));
		adminUserType.click();
	}
	public void clickSaveButtonOnAddPermisisonPopUp() throws InterruptedException{
		// Click Save button on Add Profile Window Pop Up
		Thread.sleep(500);
		WebElement saveButton = (new WebDriverWait(driver, 30))
				.until(ExpectedConditions.presenceOfElementLocated(By.id(Profiles.SAVE_BTN)));
		saveButton.click();
		Thread.sleep(3000);
		
	}

	public void clickCancelButtonOnAddPermisisonPopUp() throws InterruptedException{
		WebElement saveButton = (new WebDriverWait(driver, 30))
				.until(ExpectedConditions.presenceOfElementLocated(By.id(Profiles.CANCEL_BTN)));
		saveButton.click();
		Thread.sleep(1000);
	}

	
	public void selectDocTypebyText(String docType) throws InterruptedException{
		WebElement ProfileDocType = (new WebDriverWait(driver, 20))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(Profiles.DOCUMENT_TYPE)));
		ProfileDocType.click();
		Thread.sleep(1000);
		WebElement baseTable = driver.findElement(By.cssSelector("#VAADIN_COMBOBOX_OPTIONLIST > div > div.v-filterselect-suggestmenu > table"));
		List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
		int sumRow = tableRows.size();
		
		System.out.print(sumRow +" test");
		
		for(int i=1;i<sumRow;i++){
			WebElement POAckType = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.presenceOfElementLocated(By
							.cssSelector("#VAADIN_COMBOBOX_OPTIONLIST > div > div.v-filterselect-suggestmenu > table > tbody > tr:nth-child("+i+") > td > span")));
			if(POAckType.getText().equals(docType)){
				POAckType.click();
				break;
			}
				
		}
	
	}
}
