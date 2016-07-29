package org.yuan.project.logger.spi;

public class LocationInfo {
	
	public LocationInfo(Throwable t, String clazz) {
		if(t == null || clazz == null) {
			return;
		}
		
		try {
			String prevClass = NA;
			StackTraceElement[] elements = t.getStackTrace();
			for(int i=elements.length - 1; i>=0; i--) {
				String thisClass = (String)elements[i].getClassName();
				if(clazz.equals(thisClass)) {
					int caller = i + 1;
					if(caller < elements.length) {
						className = prevClass;
						methodName = elements[i].getMethodName();
						fileName = elements[i].getFileName();
						if(fileName == null) {
							fileName = NA;
						}
						int line = elements[i].getLineNumber();
						if(line < 0) {
							lineNumber = NA;
						} else {
							lineNumber = String.valueOf(line);
						}
						StringBuffer buf = new StringBuffer();
						buf.append(className);
						buf.append(".");
						buf.append(methodName);
						buf.append("(");
						buf.append(fileName);
						buf.append(":");
						buf.append(lineNumber);
						buf.append(")");
						this.fullInfo = buf.toString();
					}
					return;
				}
				prevClass = thisClass;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public LocationInfo(String fileName, String className, String methodName, String lineNumber) {
		this.fileName = fileName;
		this.className = className;
		this.methodName = methodName;
		this.lineNumber = lineNumber;
		
		StringBuffer sb = new StringBuffer();
		appendFragment(sb, className);
		sb.append(".");
		appendFragment(sb, methodName);
		sb.append("(");
		appendFragment(sb, fileName);
		sb.append(":");
		appendFragment(sb, lineNumber);
		sb.append(")");
		
		this.fullInfo = sb.toString();
	}
	
	private static void appendFragment(StringBuffer sb, String val) {
		if(val == null) {
			sb.append(NA);
		} else {
			sb.append(val);
		}
	}
	
	public String getFileName() {
		return fileName;
	}

	public String getClassName() {
		return className;
	}

	public String getMethodName() {
		return methodName;
	}

	public String getLineNumber() {
		return lineNumber;
	}

	public String getFullInfo() {
		return fullInfo;
	}

	private String fileName;
	private String className;
	private String methodName;
	private String lineNumber;
	private String fullInfo;
	
	private static final String NA = "?";
}
