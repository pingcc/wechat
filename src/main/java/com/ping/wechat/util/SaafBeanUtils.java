package com.ping.wechat.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Id;

/**
 * 对象的操作类
 * @author ZhangJun
 * @createTime 2018-03-14 00:19
 * @description
 */
public class SaafBeanUtils {
	/**
	 * 两个对象的属性值合并
	 * @param source 源对象
	 * @param target 目标对象
	 * @author ZhangJun
	 * @createTime 2018/3/14
	 * @description 将源对象的属性合并到目标对象中，源对象属性有值时才会将源对象的属性值复制到新对象中
	 * 				如果源对象属性值为null，则保留新对象该属性值不变
	 */
	public static void copyUnionProperties(Object source,Object target) throws Exception{
		if(source == null) {
			return;
		}
		if(target == null){
			throw new IllegalArgumentException("参数target不能为空");
		}
		Field[] fields = target.getClass().getDeclaredFields();
		if(fields != null && fields.length != 0){
			for(Field field : fields){
				String fieldName = field.getName();
				PropertyDescriptor pd = new PropertyDescriptor(fieldName,target.getClass());
				Method writeMethod = pd.getWriteMethod();
				if(writeMethod != null){
					if(pd.getReadMethod().getAnnotation(Id.class)!=null) {
						//主键的值不进行复制
						continue;
					}

					PropertyDescriptor sourcdPd = new PropertyDescriptor(fieldName,source.getClass());
					Method readMethod = sourcdPd.getReadMethod();
					if(readMethod != null){
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source);
						if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
							writeMethod.setAccessible(true);
						}
						if(value!=null) {
							writeMethod.invoke(target, value);
						}
					}
				}

			}
		}
	}

	public static String[] getNullPropertyNames (Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<String>();
		for(PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null) emptyNames.add(pd.getName());
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

	/**
	 * 拷贝对象，空值不复制
	 * @param src 源对象
	 * @param target 目标对象
	 * @author chenzijie
	 * @createTime 2018/12/25
	 * @description 将源对象的属性合并到目标对象中，源对象属性有值时才会将源对象的属性值复制到新对象中
	 * 				如果源对象属性值为null，则保留新对象该属性值不变
	 */
	public static void copyPropertiesIgnoreNull(Object src, Object target){
		BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
	}

}

