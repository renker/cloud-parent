package com.renker.cloud.common.mvc;

public class BaseController {
	public AjaxResult ajaxResult(Status status){
		return new AjaxResult(status);
	}
	
	public AjaxResult ajaxResult(Status status,String msg){
		return new AjaxResult(status, msg);
	}
	
	public AjaxResult ajaxResult(Status status,Object result){
		return new AjaxResult(status, result);
	}
	
	public AjaxResult ajaxResult(Status status,String msg,Object result){
		return new AjaxResult(status, msg, result);
	}
	
	
	public enum Status{
		SUCCESS("success"),ERROR("error"),WARN("warn"),UNAUTHORIZED("unauthorized");
		
		private String value;
		private Status(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
	}
}
