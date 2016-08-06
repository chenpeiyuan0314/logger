package org.yuan.project.logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.or.RendererMap;
import org.yuan.project.logger.spi.LoggerRepository;

public class Hierarchy implements LoggerRepository {
	
	public Hierarchy(Logger root) {
		this.rootLogger = root;
	}

	@Override
	public Logger getRootLogger() {
		return rootLogger;
	}

	@Override
	public Logger getLogger(String name) {
		if(name == null) {
			return null;
		}
		
		synchronized(table) {
			Object obj = table.get(name);
			
			if(obj == null) {
				Logger logger = new Logger(name);
				table.put(name, logger);
				updateParent(logger);
				return logger;
			}
			
			if(obj instanceof Logger) {
				return (Logger)obj;
			}
			
			if(obj instanceof List) {
				Logger logger = new Logger(name);
				table.put(name, logger);
				updateChildren((List<Logger>)obj, logger);
				updateParent(logger);
				return logger;
			}
		}
		
		return null;
	}

	private void updateParent(Logger logger) {
		boolean found = false;
		String name = logger.getName();
		
		for(int i = name.lastIndexOf(".", name.length() - 1);
			i >= 0; i = name.lastIndexOf(".", i - 1)) {
			String sub = name.substring(0, i);
			Object obj = table.get(sub);
			
			if(obj == null) {
				List<Logger> node = new ArrayList<Logger>();
				node.add(logger);;
				table.put(sub, node);
				continue;
			}
			
			if(obj instanceof Logger) {
				found = true;
				logger.setParent((Logger)obj);
				break;
			}
			
			if(obj instanceof List) {
				List<Logger> node = (List<Logger>)obj;
				node.add(logger);
				continue;
			}
		}
		
		if(!found) {
			logger.setParent(rootLogger);
		}
	}
	
	private void updateChildren(List<Logger> node, Logger logger) {
		for(Logger child : node) {
			if(!child.getParent().getName().startsWith(logger.getName())) {
				logger.setParent(child.getParent());
				child.setParent(logger);
			}
		}
	}
	
	public void shutdown() {
		Logger root = getRootLogger();
		root.closeNestedAppenders();
		
		synchronized(table) {
			List<Logger> list = getCurrentLoggers();
			for(Logger log : list) {
				log.closeNestedAppenders();
			}
			
			root.delAllAppenders();
			for(Logger log : list) {
				log.delAllAppenders();
			}
		}
	}
	
	public void reset() {
		rootLogger.setLevel(Level.DEBUG);
		
		synchronized(table) {
			List<Logger> list = getCurrentLoggers();
			for(Logger log : list) {
				log.setLevel(null);
			}
		}
	}
	
	public List<Logger> getCurrentLoggers() {
		List<Logger> list = new ArrayList<Logger>();
		
		for(Object obj : table.values()) {
			if(obj instanceof Logger) {
				list.add((Logger)obj);
			}
		}
		
		return list;
	}
	//--------------------------------------------------------------
	//
	//--------------------------------------------------------------
	private Logger rootLogger;
	private Map<String,Object> table = new HashMap<String,Object>();
	private Level level;
	private RendererMap rendererMap;
}
