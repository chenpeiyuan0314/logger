package org.yuan.project.logger.spi;

import org.yuan.project.logger.or.ObjectRenderer;
import org.yuan.project.logger.or.RendererMap;

public interface RendererSupport {

	RendererMap getRendererMap();
	
	void setRenderer(Class renderedClass, ObjectRenderer renderer);
	
}
