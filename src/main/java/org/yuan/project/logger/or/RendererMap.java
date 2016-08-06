package org.yuan.project.logger.or;

import java.util.Map;

import org.apache.log4j.helpers.Loader;
import org.apache.log4j.helpers.OptionConverter;
import org.yuan.project.logger.helpers.LogLog;
import org.yuan.project.logger.spi.RendererSupport;

public class RendererMap {

	public String findAndRender(Object o) {
		if(o == null) {
			return null;
		}
		
		return get(o.getClass()).doRender(o);
	}
	
	public ObjectRenderer get(Object o) {
		if(o == null) {
			return null;
		}
		
		return get(o.getClass());
	}
	
	public ObjectRenderer get(Class clazz) {
		ObjectRenderer r = null;
		for(Class c=clazz; c!=null; c=c.getSuperclass()) {
			r = (ObjectRenderer)table.get(c);
			if(r != null) {
				return r;
			}
			r = searchInterfaces(c);
			if(r != null) {
				return r;
			}
		}
		return defaultRenderer;
	}
	
	public ObjectRenderer searchInterfaces(Class c) {
		ObjectRenderer r = (ObjectRenderer)table.get(c);
		if(r != null) {
			return r;
		}
		
		Class[] cs = c.getInterfaces();
		for(Class cz : cs){
			r = searchInterfaces(cz);
			if(r != null) {
				return r;
			}
		}
		return null;
	}
	
	public ObjectRenderer getDefaultRenderer() {
		return defaultRenderer;
	}
	
	public void clear() {
		table.clear();
	}
	
	public void put(Class clazz, ObjectRenderer or) {
		table.put(clazz, or);
	}

	public static void addRenderer(RendererSupport repository, String renderedClassName, String renderingClassName) {
		ObjectRenderer renderer = (ObjectRenderer) OptionConverter.instantiateByClassName(renderingClassName, ObjectRenderer.class, null);
		if(renderer == null) {
			return;
		}
		try {
			Class renderedClass = Loader.loadClass(renderedClassName);
			repository.setRenderer(renderedClass, renderer);
		} catch(ClassNotFoundException e) {
			LogLog.error(e.getMessage());
		}
	}
	
	//-------------------------------------------------------------------
	//
	//-------------------------------------------------------------------
	private ObjectRenderer defaultRenderer = new DefaultObjectRenderer();
	private Map<Class, ObjectRenderer> table;
}
