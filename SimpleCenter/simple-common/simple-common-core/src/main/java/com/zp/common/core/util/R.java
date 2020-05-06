package com.zp.common.core.util;




/**
 * 返回数据
 * 
 * @author zhaipan
 * 
 * @date 2016年10月27日 下午9:59:27
 */ 
public class R<T> implements java.io.Serializable{
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = -849055313089371221L;

	private Integer code ;
	
	private String msg;
	
	private T data ;
	
	public R<T> setData(T data) {
		this.data = data;
		return this ;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public T getData() {
		return data;
	}
	
	public String getMsg() {
		return msg;
	}
	
	
	public R() {
		setCode(0);
		setMsg( "操作成功");
	}
	
	public int getCode() { 
		return code != null ? code : 500;
	}
	
	public static R<Object> error() {
		return error(500, "网络异常,请稍后再试");
	}
	
	public static R<Object> error(String msg) {
		return error(500, msg);
	}
	
	public static <T> R<T> error(Class<T> c) {
		
		R<T> r = new R<T>();
		r.setCode(500);
		return r ;
		 
	}
	
	public static R<Object> error(int code, String msg) {
		R<Object> r = new R<Object>();
		r.setCode(code);
		r.setMsg(msg);
		return r;
	}

	public static R<Object> ok(String msg) {
		R<Object> r = R.ok();
		r.setMsg(msg);
		return r;
	}
	

	
	public static R<Object> ok() {
		return new R<Object>();
	}
 
	public static <T>  R<T> ok(Class<T> c) {
		return new R<T>();
	}
}
