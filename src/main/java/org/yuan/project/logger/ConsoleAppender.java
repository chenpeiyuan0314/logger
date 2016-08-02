package org.yuan.project.logger;

import org.yuan.project.logger.helpers.LogLog;

public class ConsoleAppender extends WriterAppender {
	
	public static final String SYSTEM_OUT = "System.out";
	public static final String SYSTEM_ERR = "System.err";
	
	public ConsoleAppender() {
		
	}
	
	public ConsoleAppender(Layout layout) {
		this(layout, SYSTEM_OUT);
	}

	public ConsoleAppender(Layout layout, String target) {
		setLayout(layout);
		setTarget(target);
		activateOptions();
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		String v = target.trim();
		
		if(SYSTEM_OUT.equalsIgnoreCase(v)) {
			this.target = SYSTEM_OUT;
		} else if(SYSTEM_ERR.equalsIgnoreCase(v)) {
			this.target = SYSTEM_ERR;
		} else {
			targetWarn(target);
		}
	}
	
	private void targetWarn(String val) {
		LogLog.warn("[" + val + "] should be System.out or System.err.");
		LogLog.warn("Using previously set target, System.out by default.");
	}

	public boolean getFollow() {
		return follow;
	}

	public void setFollow(boolean follow) {
		this.follow = follow;
	}
	
	protected final void closeWriter() {
		if(follow) {
			super.closeWriter();
		}
	}
	
	@Override
	public void activateOptions() {
		if(target.equals(SYSTEM_ERR)) {
			setWriter(createWriter(System.err));
		} else {
			setWriter(createWriter(System.out));
		}
		super.activateOptions();
	}

	//------------------------------------------------
	//
	//------------------------------------------------
	private String target = SYSTEM_OUT;
	private boolean follow = false;
}
