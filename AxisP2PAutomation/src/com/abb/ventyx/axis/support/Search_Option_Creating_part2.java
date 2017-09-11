package com.abb.ventyx.axis.support;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.abb.ventyx.axis.objects.pagedefinitions.AxisConfigMenu;
import com.abb.ventyx.axis.objects.pagedefinitions.DialogBtns;
import com.abb.ventyx.axis.objects.pagedefinitions.Messages;
import com.abb.ventyx.axis.objects.pagedefinitions.ScreenObject;
import com.abb.ventyx.axis.objects.pagedefinitions.SearchOption;
import com.abb.ventyx.utilities.ALM;
import com.abb.ventyx.utilities.BaseDropDownList;
import com.abb.ventyx.utilities.BaseGrid;
import com.abb.ventyx.utilities.BaseTestCase;
import com.abb.ventyx.utilities.Credentials;

@ALM(id = "158")
@Credentials(user = "mail5@abb.com", password = "testuser")
public class Search_Option_Creating_part2 extends BaseTestCase {

	String FIELD_TYPE = "QUANTITY";
	String FILTER_SUB_TYPE = "UNIT";
	String OPTION = "TEST_SEARCH";
	BaseDropDownList list;
	int row;
	BaseGrid grid;

	@Test
	public void duplicateSearchOption1() {
		// Create Filter Field
		WebElement axisConfigParentButton = (new WebDriverWait(driver, 60))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(AxisConfigMenu.AXIS_CONFIGURATION)));
		axisConfigParentButton.click();
		WebElement axisFilterConfig = (new WebDriverWait(driver, 60))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(AxisConfigMenu.FILTER_CONFIG)));
		axisFilterConfig.click();
		WebElement axisFilterField = (new WebDriverWait(driver, 60))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(AxisConfigMenu.SEARCH_OPTION)));
		axisFilterField.click();
		WebElement searchOptionAdd = (new WebDriverWait(driver, 60))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(SearchOption.ADD)));
		searchOptionAdd.click();
	}

	@Test(dependsOnMethods = "duplicateSearchOption1")
	public void duplicateSearchOption2() {
		WebElement fieldType = (new WebDriverWait(driver, 80))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(SearchOption.FIELD_TYPE_ADD)));
		fieldType.click();
		fieldType.sendKeys(FIELD_TYPE);
		WebElement fieldSubType = (new WebDriverWait(driver, 80))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(SearchOption.FILTER_SUB_TYPE_ADD)));
		fieldSubType.click();
		fieldSubType.sendKeys(FILTER_SUB_TYPE);
		WebElement option = (new WebDriverWait(driver, 80))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(SearchOption.OPTION_ADD)));
		option.click();
		option.sendKeys(OPTION);
		WebElement save = (new WebDriverWait(driver, 80))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(SearchOption.SAVE_ADD)));
		save.click();
		(new WebDriverWait(driver, 10)).until(ExpectedConditions
				.presenceOfElementLocated(By
						.cssSelector(Messages.DUPLICATE_FIELD_TYPE_CSS)));
	}

	@Test(dependsOnMethods = "duplicateSearchOption2")
	public void catchDuplicateNotification() {
		WebElement flashMessage = (new WebDriverWait(driver, 80))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(Messages.DUPLICATE_FIELD_TYPE_CSS)));
		Assert.assertEquals(flashMessage.getText(),
				Messages.DUPLICATE_FIELD_TYPE);
		Assert.assertEquals(flashMessage.getCssValue("visibility"), "visible");
		Assert.assertEquals(flashMessage.getCssValue("display"), "block");
	}

	@Test(dependsOnMethods = "catchDuplicateNotification")
	public void catchUnsavedChange() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions
				.presenceOfElementLocated(By.id(SearchOption.CANCEL_ADD)));
		WebElement cancel = (new WebDriverWait(driver, 80))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(SearchOption.CANCEL_ADD)));
		cancel.click();
		WebElement unsavedChange = (new WebDriverWait(driver, 80))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(ScreenObject.UNSAVED_CHANGE_CSS)));
		unsavedChange.getText();
		Assert.assertEquals(unsavedChange.getText(), Messages.UNSAVED_CHANGE);
		WebElement yesBtn = (new WebDriverWait(driver, 60))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(DialogBtns.YES)));
		yesBtn.click();

	}

	@Test(dependsOnMethods = "catchUnsavedChange")
	public void testFilterOnGrid() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions
				.presenceOfElementLocated(By
						.cssSelector(SearchOption.FILTER_CSS)));
		WebElement searchOptionFilter = (new WebDriverWait(driver, 80))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(SearchOption.FILTER_CSS)));
		searchOptionFilter.click();
		WebElement field_Type_Filter = (new WebDriverWait(driver, 80))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.xpath(SearchOption.FIELD_TYPE_FILTER)));
		field_Type_Filter.click();
		WebElement field_Type_Filter1 = (new WebDriverWait(driver, 80))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id("filterField")));
		field_Type_Filter1.sendKeys(FIELD_TYPE);

	}

	@Test(dependsOnMethods = "testFilterOnGrid")
	public void checkOnGrid() {

		// grid = new BaseGrid(driver, SearchOption.GRID_CSS);
		// row = grid.findItemByColumnName("Field Type", FIELD_TYPE);
		// Assert.assertNotEquals(row, -1, "Record not found");

	}

}
