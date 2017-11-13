package com.abb.ventyx.axis.objects.pagedefinitions;

public class Profiles {
	public static final String TITLE_PROFILES = "Maintain Customer Defined Profiles";
	public static final String PROFILE_NAME_CUSTOMER_FILTER = "(//input[@id='filterField'])[1]";
	public static final String CUSTOMER_NAME1_FILTER = "(//input[@id='filterField'])[1]";
	public static final String PROFILE_NAME_FILTER = "(//input[@id='filterField'])[2]";
	public static final String TABLEBODY = "//div[@class='v-grid-tablewrapper']//table//tbody[@class='v-grid-body']";
	public static final String PROFILE_NAME_COLUMN = "#content-component > div > div.v-panel-content.v-panel-content-borderless.v-panel-content-v-common-page-panel.v-scrollable > div > div.v-slot.v-slot-v-common-page-content-layout > div > div > div > div > div > div > div.v-grid-tablewrapper > table > thead > tr > th:nth-child(2) > div.v-grid-column-header-content.v-grid-column-default-header-content";
	public static final String DOC_TYPE_FILTER = "(//input[@id='filterField'])[3]";
	public static final String ADD_PROFILE = "#HeaderMenuBar > span:nth-child(2) > span > span";
	public static final String TITLE_CREATE = "Create Profile";
	public static final String TITLE_MAINTAIN_CUSTOMER = "Maintain Customer Defined Profiles";
	public static final String PROFILE_NAME_ID = "profileName";
	public static final String CUSTOMER_CLASS = "v-filterselect-input";
	public static final String LIST_CSS = "#VAADIN_COMBOBOX_OPTIONLIST > div > div.v-filterselect-suggestmenu > table";
	public static final String SAVE_BTN = "saveBtn";
	public static final String CANCEL_BTN = "cancelBtn";
	public static final String DOCUMENT_TYPE = "documentType";
	public static final String TRASH_BIN = "deleteItemBtn";
	public static final String EDIT_BUTTON_INDEX = "editItemBtn";
	public static final String CUSTOMER_NAME_FILTER = "#filterField";
	public static final String CUSTOMER_NAME = "customerCB";
	public static final String MAINTAIN_CUSTOMER_TABLE_CSS = "#content-component > div > div.v-panel-content.v-panel-content-borderless.v-panel-content-v-common-page-panel.v-scrollable > div > div > div.v-slot.v-slot-v-common-page-content-layout > div > div > div > div > div > div > div > div.v-grid-tablewrapper";

	public static final String DELETE_YES = "#confirmation-window > div > div > div.v-window-contents > div > div > div.v-slot.v-slot-v-confirmation-window-text-and-button-layout > div > div.v-slot.v-slot-v-bottombar-button-layout.v-align-right.v-align-middle > div > div:nth-child(3)";
	public static final String DELETE_NO = "#confirmation-window > div > div > div.v-window-contents > div > div > div.v-slot.v-slot-v-confirmation-window-text-and-button-layout > div > div.v-slot.v-slot-v-bottombar-button-layout.v-align-right.v-align-middle > div > div:nth-child(1)";
	public static final String CANCEL_NO = "#confirmation-window > div > div > div.v-window-contents > div > div > div.v-slot.v-slot-v-confirmation-window-text-and-button-layout > div > div.v-slot.v-slot-v-bottombar-button-layout.v-align-right.v-align-middle > div > div:nth-child(1)";
	public static final String TABLE_AUTHORISED_DOCUMENT = "//*[@id='createcustomerprofilewindow']/div/div/div[3]/div/div/div[2]/div/div[7]/div/div";

	public static final String SORT_CUSTOMER_NAME = "//*[@id='content-component']/div/div[2]/div/div[3]/div/div/div/div/div/div/div[3]/table/thead/tr/th[1]";
}
