package com.ezraonly.spring;
/**
 * 功能描述:
 * 〈加载bean属性〉
 * @Author:lwy
 * @Date: 2021/9/5 18:32
 */

public class BeanDefinition {
	private Class type;
	//是否单例
	private String scop;

	private String lazy;

	public Class getType() {
		return type;
	}

	public void setType(Class type) {
		this.type = type;
	}

	public String getScop() {
		return scop;
	}

	public void setScop(String scop) {
		this.scop = scop;
	}

	public String getLazy() {
		return lazy;
	}

	public void setLazy(String lazy) {
		this.lazy = lazy;
	}
}
