package com.as.constant;

public interface Constant {

  

	interface Message {
	 
		String LOGIN_SUCCESS = "Login succesfully";
 		String INVALID_USER_NAME_PASSWORD = "Invalid Username or Password";
		String USER_BLOCKED = "Account is blocked, please contact to admin";
	}

   

	public enum ResponseCode {

		RECORD_CREATED(200), RECORD_NOT_CREATED(204), RECORD_REMOVED(200), RECORD_NOT_REMOVED(204), RECORD_FOUND(200),
		RECORD_NOT_FOUND(204), RECORD_UPDATE_SUCCESS(200), RECORD_UPDATE_FAIL(204), RECORD_ALREADY_EXIST(409),
		INVALID_REQUEST_PARAMETERS(400), EXCEPTION(401),INTERNAL_SERVER_ERROR(500);

		private final Integer code;

		private ResponseCode(Integer code) {
			this.code = code;
		}

		public Integer value() {
			return code;
		}
	}

	public enum ResponseMessage {

		RECORD_CREATED("Record saved successfully"),SUCCESS("Success"), RECORD_CREATED_PARTIALLY("Record saved partially"),
		RECORD_NOT_CREATED("Record saving failed"), RECORD_REMOVED("Record deleted successfully"),
		RECORD_NOT_REMOVED("Record deletion failed"), RECORD_FOUND("Record found successfully"),
		RECORD_NOT_FOUND("Record not found"), RECORD_UPDATE_SUCCESS("Record successfully updated"),
		RECORD_UPDATE_FAIL("Record updation failed"), INVALID_REQUEST_PARAMETERS("Invalid request parameters"),
		INTERNAL_SERVER_ERROR("Internal Server Error");

		private final String message;

		private ResponseMessage(String message) {
			this.message = message;
		}

		public String value() {
			return message;
		}

	}

}
