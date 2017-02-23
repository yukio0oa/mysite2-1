package com.bit2017.mysite.dto;

public class JSONResult {
	private String result;  //"success" -> 통신 성공
							//"fail" -> 통신 실패
	private String message;
	private Object data;
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
