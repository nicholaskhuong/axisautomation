package com.abb.ventyx.utilities;

import org.openqa.selenium.WebElement;

public class InputController {
	public static void inputToTextFiled(WebElement theTextField, String input) {
		theTextField.clear();
		theTextField.sendKeys(input);
	}
}
