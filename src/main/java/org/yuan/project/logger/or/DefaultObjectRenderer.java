package org.yuan.project.logger.or;

public class DefaultObjectRenderer implements ObjectRenderer {

	@Override
	public String doRender(Object o) {
		try {
			return o.toString();
		} catch(Exception e) {
			return e.toString();
		}
	}

}
