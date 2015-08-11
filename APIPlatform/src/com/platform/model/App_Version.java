package com.platform.model;
/** * @author  作者 E-mail: * @date 创建时间：2015年8月9日 下午2:33:16 * @version 1.0 * @parameter  * @since  * @return  */

public class App_Version {
	
	private Integer version_id;
	public void  setVersion_id(Integer version_id) {
		this.version_id=version_id;
	}
	public Integer getVersion_id() {
		return version_id;
	}
	
	
	private Integer app_type;
	public void  setApp_type(Integer app_type) {
		this.app_type=app_type;
	}
	public Integer getApp_type() {
		return app_type;
	}
	
	
	
	private String app_url;
	public void  setApp_url(String app_url) {
		this.app_url=app_url;
	}
	public String getApp_url() {
		return app_url;
	}
	
	
	
	private String version_code;
	public void  setVersion_code(String version_code) {
		this.version_code=version_code;
	}
	public String getVersion_code() {
		return version_code;
	}
	
	
	
	private String version_name;
	public void  setVersion_name(String version_name) {
		this.version_name=version_name;
	}
	public String getVersion_name() {
		return version_name;
	}
	
	
	

}
