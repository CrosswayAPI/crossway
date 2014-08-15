package com.crossway.api.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import com.crossway.api.annotations.Roadway;

public class CWFramework {

	private static CWFramework instance = null;
	private Map<String, String> waysMap = new HashMap<String, String>();

	protected CWFramework(){
	}

	public static CWFramework getInstance() {
		if(instance == null) {
			instance = new CWFramework();
		}
		return instance;
	}
	
	public void setMap(Map<String, String> map){
		this.waysMap = map;
	}
	
	public Map<String, String> getWaysMap(){
		return waysMap;
	}

	public Object call(String key) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		if(waysMap.containsKey(key)){
			Reflections reflections = new Reflections();
			Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Roadway.class);
			for(Class<?> className: annotated){
				Class<?> cls = Class.forName(className.getName());
				Roadway rw = cls.getAnnotation(Roadway.class);
				if(rw != null & rw.appId().equals( waysMap.get(key)) ){
					
					return cls.newInstance();
				
				}
				
			}
		}
		return null;
	}
	
}
