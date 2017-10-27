package com.abb.ventyx.axis.objects.pagedefinitions;

public class Messages {

	public static final String UNSAVED_CHANGE = "Are you sure you want to exit without saving your changes?";
	public static final String DEL_CONFIRM = "Are you sure you want to delete this document type?";
	public static final String DELETE_CONFIRM = "Are you sure you want to delete this permission?";
	public static final String DELETE_CONFIRM_FILTER_FIELD = "Are you sure you want to delete this Document Filter Field?";
	public static final String DELETE_CONFIRM_SEARCH_OPTION = "Are you sure you want to delete this Document Filter Type Search Option?";
	public static final String DOCUMENT_DELETE_SUCCESSFULLY = "The Document Type successfully deleted";
	public static final String DOCUMENT_CREATE_SUCCESSFULLY_UPDATED = "Document filter type search option successfully updated";
	public static final String DOCUMENT_CREATE_SUCCESSFULLY_UPDATED_CSS = "#SupplierPortal-1227837064-overlays > div.v-Notification.v-success-notification.v-Notification-v-success-notification";
	public static final String DOCUMENT_CREATE_SUCCESSFULLY = "The Document Type successfully created";
	public static final String DOCUMENT_UPDATED_SUCCESSFULLY = "The Document Type successfully updated";
	public static final String SEARCH_OPTION_SUCCESSFULLY = "#SupplierPortal-1227837064-overlays > div.v-Notification.v-success-notification.v-Notification-v-success-notification";
	public static final String DOC_TYPE_SEARCH_OPT_SUCCESSFULLY = "Document filter type search option successfully created";
	public static final String DUPLICATE_FIELD_TYPE_CSS = "#SupplierPortal-1227837064-overlays > div.v-Notification.v-error-notification-no-icon.v-Notification-v-error-notification-no-icon";
	public static final String DUPLICATE_FIELD_TYPE = "Duplicate Field Type and Option, please use different values";
	public static final String DOC_TYPES_EXIST = "This Document Type already exists";
	public static final String ENTER_MANDATORY_FIELDS = "One or more fields are in error. Please correct.";
	public static final String EMPTY_PERMISSION = "Please select permissions for user group";

	// User group page
	public static final String USERGROUP_CREATE_SUCCESSFULLY = "User group successfully created";
	public static final String USERGROUP_UPDATE_SUCCESSFULLY = "User group successfully updated";
	public static final String USERGROUP_DELETE_SUCCESSFULLY = "User group successfully deleted";
	public static final String USERGROUP_EXISTING = "User group name exist";
	public static final String DELETE_USERGROUP_CONFIRM = "Are you sure you want to delete the selected user group?";
	public static final String USERGROUP_SELECT_CUSTOMER = "Please select a customer";

	public static final String DOCUMENT_FILTER_FIELD_SUCCESSFULLY = "Document filter field successfully created";

	public static final String DOCUMENT_FILTER_FIELD_SUCCESSFULLY_UPDATED = "Document filter field successfully updated";
	public static final String DEL_FILTER_FIELD_SUCCESS = "Document Filter Field successfully deleted";

	// Users page
	public static final String USER_SELECT_USERGROUP = "Please select at least one user group";
	public static final String USER_CREATE_SUCCESSFULLY = "User successfully created";
	public static final String USER_UPDATE_SUCCESSFULLY = "User successfully updated";
	public static final String USER_DELETE_SUCCESSFULLY = "User successfully deleted";
	public static final String DELETE_USER_CONFIRM = "Are you sure you want to delete this user?";
	public static final String INVALID_EMAIL = "Invalid email address";
	public static final String SAME_EMAIL = "Unable to use email address, please contact your Support admin";
	public static final String UPDATE_SAME_EMAIL = "Unable to change email address, please contact your Support admin";
	public static final String INVALID_PWD = "Password must be 6-16 characters. Password must have at least one uppercase letter, one lowercase letter, and one number. This is not a valid password string";
	public static final String INVALID_PWD1 = "Password must have at least one uppercase letter, one lowercase letter, and one number. This is not a valid password string";
	public static final String INVALID_PWD2 = "Password must have at least one uppercase letter, one lowercase letter, and one number.";

	public static final String UNMATCHED_CONFIRM_PWD = "Entered Password is not the same, please verify.";

	public static final String INVALID_CONFIRM_PWD = "Entered Password is not the same, please verify.";
	public static final String USERNOTFOUND = "Unable to login. Please check your email address and password.";

	public static final String USERS_EXISTING = "User ID exists";
	// Permissions
	public static final String EMPTYPERMISSIONNAME = "Please enter permission name";
	public static final String EMPTYUSERTYPE = "Please select the User Type";
	public static final String PERMISSION_CREATED_SUCCESSFULLY = "Permission successfully created";
	public static final String PERMISSION_CREATED_SUCCESSFULLY_CSS = "#SupplierPortal-1227837064-overlays > div.v-Notification.v-success-notification.v-Notification-v-success-notification > div > div > h1 > div > div > div.v-slot.v-align-center.v-align-middle.v-notification-inline-caption";
	public static final String PERMISSION_UPADTED_SUCCESSFULLY = "Permission successfully updated";
	public static final String PERMISSION_DELETED_SUCCESSFULLY = "Permission successfully deleted";

	// Users under Customer Maintenance menu in Customer view
	public static final String ADDRESS_CONTACT_SUCCESSFULLY_UPDATED = "Address & Details successfully updated";
	public static final String INVALIED_EMAIL_MESSAGE = "Please enter valid email addresses with comma separator";
	public static final String NO_SPECIAL_CHARACTER_ALLOWED = "No special character allowed";
	public static final String SUPPLIER_UPDATED_SUCCESSFULLY = "Supplier successfully updated";
	public static final String ADDRESS_SUCCESSFULLY_UPDATED = "Address successfully updated";

	// Address And contact
	public static final String ADDRESSCONTACTUPDATE_SUCCESSFULLY = "Address & Details successfully updated";

	// Business Code Sets
	public static final String TAXCODEADD_SUCCESSFULLY = "Code Set successfully added";

	// Supplier List in Customer View
	public static final String DUPLICATEDEMAIL = "Email Address already exists";
	public static final String DUPLICATECOMPANYREGISTRATIONNO = "Company Registration Number already exists";
	public static final String DUPLICATEDTAXREGISTRATIONNO = "Tax Registration Number already exists";
	public static final String SUPPLIER_CREATED_SUCCESSFULLY = "Supplier successfully created";
	public static final String DELETE_ADDRESS_MESSAGE = "Are you sure you want to delete the selected address?";
	public static final String DELETE_CONTACT_MESSAGE = "Are you sure you want to delete the selected contact?";

	public static final String INVALID_EMAIL_2 = "Please enter a valid email address";
	public static final String ACTIVATE_DEACTIVE_WITHOUTSUPPLIER = "Please select at least one supplier";
	public static final String DEACTIVATE_SUPPLIER = "Are you sure you want to deactivate selected supplier?";
	public static final String ACTIVATE_SUPPLIER = "Are you sure you want to activate selected supplier?";
	public static final String DEACTIVATE_SUPPLIER_SUCCESSFULLY = "Supplier successfully deactivated";
	public static final String ACTIVATE_SUPPLIER_SUCCESSFULLY = "Supplier successfully activated";
	public static final String SUPPLIER_ALREADY_ACTIVE = "Supplier is already active";
	public static final String SUPPLIER_ALREADY_INACTIVE = "Supplier is already inactive";
	public static final String SUPPLIER_INACTIVE = "Supplier inactive";

	// User Preferences
	public static final String USERPREFERENCES_UPDATED_SUCCESSFULLY = "Profile successfully updated";
	public static final String DELIVERY_CODE_LESS_15CHARACTER = "Delivery Code must be exactly 15 characters";
	public static final String DELIVERY_CODE_EQUAL_15CHARACTER = "Code Set successfully added";

	// Profiles
	public static final String ERROR_MESSAGE = "Please select at least one business document beside System";
	public static final String MESSAGE_SUCCESSFULLY = "Customer Defined Profile successfully created";
	public static final String MESSAGE_MISSING_ALL_FIELD = "Please enter Profile Name";
	public static final String MESSAGE_MISSING_CUSTOMER_NAME = "Please select a Customer Name";
	public static final String MESSAGE_MISSING_PROFILE_FIELD = "Please enter Profile Name";
	public static final String MESSAGE_DUPLICATED_PROFILE_NAME = "Profile Name already exists";
	public static final String MESSAGE_EDIT_PROFILE_NAME_SUCCESSFULLY = "Customer Defined Profile successfully updated";
	public static final String MESSAGE_DELETE_SUCCESSFULLY = "Customer Defined Profile successfully deleted";

	//Customers
	public static final String CREATE_CUSTOMER_SUCCESSFULLY ="Customer successfully created";

	
}	
