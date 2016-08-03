package org.yuan.project.logger.helpers;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.yuan.project.logger.FileAppender;
import org.yuan.project.logger.Layout;
import org.yuan.project.logger.spi.LoggingEvent;

public class DailyRollingFileAppender extends FileAppender {
	
	//------------------------------------------------------
	//
	//------------------------------------------------------
	public static final int TOP_OF_TROUBLE 	= 0;
	public static final int TOP_OF_MINUTE 	= 1;
	public static final int TOP_OF_HOUR 	= 2;
	public static final int TOP_OF_DAY	 	= 3;
	public static final int TOP_OF_WEEK 	= 4;
	public static final int TOP_OF_MONTH 	= 5;

	//------------------------------------------------------
	//
	//------------------------------------------------------
	
	public DailyRollingFileAppender(Layout layout, String filename, String pattern) throws IOException {
		super(layout, filename, true);
		this.pattern = pattern;
		activateOptions();
	}
	
	protected void rollOver() {
		if(pattern == null) {
			return;
		}
		
		String datedFileName = getFile() + "." + sdf.format(now);
		if(schedFileName.equals(datedFileName)) {
			return;
		}
		
		File target = new File(datedFileName);
		if(target.exists()) {
			target.delete();
		}
		
		File file = new File(getFile());
		boolean result = file.renameTo(target);
		
		try {
			this.setFile(getFile(), true, getBufferedIO(), getBufferSize());
		} catch(Exception e) {
			LogLog.error(e.getMessage());
		}
		
		schedFileName = datedFileName;
	}
	
	protected int computeCheckPeriod() {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date beg = new Date(0);
		for(int i=TOP_OF_MINUTE; i<=TOP_OF_MONTH; i++) {
			String r0 = sdf.format(beg);
			String r1 = sdf.format(new Date(getNextCheckMills(beg, i)));
			if(r0.equals(r1)) {
				return i;
			}
		}
		return TOP_OF_TROUBLE;
	}

	@Override
	public void activateOptions() {
		super.activateOptions();
		
		if(pattern != null && getFile() != null) {
			now.setTime(System.currentTimeMillis());
			sdf = new SimpleDateFormat(pattern);
			File file = new File(getFile());
			schedFileName = getFile() + sdf.format(new Date(file.lastModified()));
		}
	}

	@Override
	protected void subAppend(LoggingEvent event) {
		long ms = System.currentTimeMillis();
		if(ms > nextCheck) {
			now.setTime(ms);
			nextCheck = getNextCheckMills(now);
			try {
				rollOver();
			} catch(Exception e) {
				LogLog.error(e.getMessage());
			}
		}
		
		super.subAppend(event);
	}

	private long getNextCheckMills(Date date, int type) {
		cal.setTime(date);
		
		switch(type) {
		case TOP_OF_MINUTE:
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			cal.add(Calendar.MINUTE, 1);
			break;
		case TOP_OF_HOUR:
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			cal.add(Calendar.HOUR_OF_DAY, 1);
		case TOP_OF_DAY:
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			cal.add(Calendar.DATE, 1);
		case TOP_OF_WEEK:
			cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			cal.add(Calendar.WEEK_OF_YEAR, 1);
		case TOP_OF_MONTH:
			cal.set(Calendar.DATE, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			cal.add(Calendar.MONTH, 1);
		default:
			throw new IllegalStateException("Unknown periodicity type.");
		}
		
		return cal.getTimeInMillis();
	}

	private long getNextCheckMills(Date date) {
		return getNextCheckMills(date, checkPeriod);
	}

	//------------------------------------------------------
	//
	//------------------------------------------------------
	private String pattern = "'.'yyyy-MM-dd";
	private Date now = new Date();
	private long nextCheck = System.currentTimeMillis() - 1;
	private int checkPeriod = TOP_OF_TROUBLE;
	private SimpleDateFormat sdf;
	private Calendar cal = Calendar.getInstance();
	private String schedFileName;
}
