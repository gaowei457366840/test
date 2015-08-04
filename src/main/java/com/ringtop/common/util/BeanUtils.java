package com.ringtop.common.util;

import java.lang.reflect.Field;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;
/**
 * 扩展Apache Commons BeanUtils, 提供�?些反射方面缺失的封装.
 * @author dz.wang
 */
public class BeanUtils extends org.apache.commons.beanutils.BeanUtils{
	protected static final Log logger = LogFactory.getLog(BeanUtils.class);

	private BeanUtils() {
	}

	/**
	 * 暴力获取当前类声明的private/protected变量
	 */
	static public Object getDeclaredProperty(Object object, String propertyName)
			throws IllegalAccessException, NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);
		Field field = object.getClass().getDeclaredField(propertyName);
		return getDeclaredProperty(object, field);
	}

	/**
	 * 暴力获取当前类声明的private/protected变量
	 */
	static public Object getDeclaredProperty(Object object, Field field)
			throws IllegalAccessException {
		Assert.notNull(object);
		Assert.notNull(field);
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		Object result = field.get(object);
		field.setAccessible(accessible);
		return result;
	}

	/**
	 * 暴力设置当前类声明的private/protected变量
	 */
	static public void setDeclaredProperty(Object object, String propertyName,
			Object newValue) throws IllegalAccessException,
			NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);

		Field field = object.getClass().getDeclaredField(propertyName);
		setDeclaredProperty(object, field, newValue);
	}

	/**
	 * 暴力设置当前类声明的private/protected变量
	 */
	static public void setDeclaredProperty(Object object, Field field,
			Object newValue) throws IllegalAccessException {
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		field.set(object, newValue);
		field.setAccessible(accessible);
	}

	/**
	 * 暴力调用当前类声明的private/protected函数
	 */
	static public Object invokePrivateMethod(Object object, String methodName,
			Object[] params) throws NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		Assert.notNull(object);
		Assert.hasText(methodName);
		Class<?>[] types = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			types[i] = params[i].getClass();
		}
		Method method = object.getClass().getDeclaredMethod(methodName, types);

		boolean accessible = method.isAccessible();
		method.setAccessible(true);
		Object result = method.invoke(object, params);
		method.setAccessible(accessible);
		return result;
	}

	/**
	 * 按Filed的类型取得Field列表
	 */
	static public List<Object> getFieldsByType(Object object, Class<?> type) {
		ArrayList<Object> list = new ArrayList<Object>();
		Field[] fields = object.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getType().isAssignableFrom(type)) {
				list.add(fields[i]);
			}
		}
		return list;
	}

	/**
	 * 获得field的getter名称
	 */
	public static String getAccessorName(Class<?> type, String fieldName) {
		Assert.hasText(fieldName, "FieldName required");
		Assert.notNull(type, "Type required");

		if (type.getName().equals("boolean")) {
			return "is" + StringUtils.capitalize(fieldName);
		} else {
			return "get" + StringUtils.capitalize(fieldName);
		}
	}
	
	/**
	 * 获得field的setter名称
	 */
	public static String getSetterName(Class<?> type, String fieldName) {
		Assert.hasText(fieldName, "FieldName required");
		Assert.notNull(type, "Type required");

		return "set" + StringUtils.capitalize(fieldName);
	}
	
	/**
	 * 获得field的setter
	 */
	 public static Method getSetter(Class<?> type, String fieldName) {
	 try {
	 return type.getMethod(getSetterName(type, fieldName),new Class[]{type});
	 } catch (NoSuchMethodException e) {
	 logger.error(e.getMessage(), e);
			}
			return null;
	}

	/**
	 * 获得field的getter名称
	 */
	 public static Method getAccessor(Class<?> type, String fieldName) {
	 try {
	 return type.getMethod(getAccessorName(type, fieldName),new Class[]{type});
	 } catch (NoSuchMethodException e) {
	 logger.error(e.getMessage(), e);
			}
			return null;
		}
	 
	 /**
	  * 复制不为空的�?,�?要getter和setter
	  * @param dest 目标对象
	  * @param orgin 源对�?
	  * @author bm.he
	  * @date 2013-1-4 下午02:08:32
	  */
	 public static void copyPropertiesNotNull(Object dest,Object orgin){
		 Assert.notNull(dest, "dest required");
		 Assert.notNull(orgin, "orgin required");
		 if(dest.getClass() != orgin.getClass()){
			 throw new RuntimeException("Not the same type");
		 }
		 
		 for(Field field : orgin.getClass().getDeclaredFields()){
			 try {
				 if(field.getName().equals("serialVersionUID")){
					 continue;
				 }
				 //取�??
				Object value = getDeclaredProperty(orgin , field);
				if(value == null){
					continue;
				}
				//设置值到目标对象
				Method setter = getSetter(field.getType(), field.getName());
				try {
					setter.invoke(dest, new Object[]{value});
				} catch (IllegalArgumentException e) {
					logger.error(e.getMessage(), e);
				} catch (InvocationTargetException e) {
					logger.error(e.getMessage(), e);
				}
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage(), e);
			}
		 }
		 
	 }
}
