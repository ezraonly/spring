package com.ezraonly.spring;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class EzraonlyApplicationContext {
	private Class configClass;
	private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
	private Map<String, Object> singletonBeanS = new HashMap<>();
	public EzraonlyApplicationContext(Class configClass){

		this.configClass = configClass;
		scan(configClass);
		//将单例bean添加当单例池中
		for (Map.Entry<String, BeanDefinition> stringBeanDefinitionEntry : beanDefinitionMap.entrySet()) {
			BeanDefinition beanDefinition = stringBeanDefinitionEntry.getValue();
			if(beanDefinition.getScop().equals("singleton")){
				Object bean = createBean(stringBeanDefinitionEntry.getKey(), stringBeanDefinitionEntry.getValue());
				singletonBeanS.put(stringBeanDefinitionEntry.getKey(), bean);
			}
		}

	}

	private Object createBean(String beanName , BeanDefinition beanDefinition){
		Class aClass = beanDefinition.getType();
		Object o = null;
		try {
			o= aClass.getConstructor().newInstance();
			//属性赋值
			for (Field declaredField : aClass.getDeclaredFields()) {
				if (declaredField.isAnnotationPresent(Autowired.class)) {
					declaredField.setAccessible(true);
					declaredField.set(o,getBean(declaredField.getName()));
				}

			}

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return o;
	}

	public Object getBean(String beanName){
		if (!beanDefinitionMap.containsKey(beanName)) {
			throw new NullPointerException();
		}
		BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
		if(beanDefinition.getScop().equals("singleton")){
			Object singletonBean = singletonBeanS.get(beanName);
			if(singletonBean == null){
				singletonBean = createBean(beanName, beanDefinition);
			}
			return singletonBean;

		}
		return	createBean(beanName, beanDefinition);
	}
	/**
	 * 扫描
	 * @param configClass
	 */
	private void scan(Class configClass) {
		if(configClass.isAnnotationPresent(ComponentScan.class)){
			ComponentScan componentScan = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
			String path = componentScan.value();
			path = path.replace(".", "/");
//			System.out.println(path);
			//获取文件目录
			ClassLoader classLoader = EzraonlyApplicationContext.class.getClassLoader();
			URL resource = classLoader.getResource(path);
			File file = new File(resource.getFile());
			if(file.isDirectory()){
				for (File listFile : file.listFiles()) {
					String absolutePath = listFile.getAbsolutePath();
					absolutePath = absolutePath.substring(absolutePath.indexOf("com"), absolutePath.indexOf(".class"));
					absolutePath = absolutePath.replace("\\", ".");
					//判断当前类是否有 Component注解
					try {
						Class<?> aClass = classLoader.loadClass(absolutePath);
						if (aClass.isAnnotationPresent(Component.class)) {
							Component annotation = aClass.getAnnotation(Component.class);
							String beanName = annotation.value();

							BeanDefinition beanDefinition = new BeanDefinition();
							//判断是否单例
							beanDefinition.setType(aClass);
							if (aClass.isAnnotationPresent(Scope.class)) {
								Scope scope = aClass.getAnnotation(Scope.class);
								String scopevalue = scope.value();
								beanDefinition.setScop(scopevalue);
							}else {
								//单例
								beanDefinition.setScop("singleton");
							}

							beanDefinitionMap.put(beanName, beanDefinition);
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}

				}
			}
		}
	}

}
