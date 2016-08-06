package org.yuan.project.logger;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

import org.yuan.project.logger.helpers.CountingQuietWriter;
import org.yuan.project.logger.spi.LoggingEvent;


public class RollingFileAppender extends FileAppender {
	
	protected void rollOver() {
		File src = null;
		File dst = null;
		
		if(qw != null) {
			nextRollOver = ((CountingQuietWriter)qw).getCount();
			nextRollOver += fileMaxSize;
		}
		
		boolean succeed = true;
		if(fileMaxSize > 0) {
			src = new File(getFile() + "." + backupIndex);
			if(src.exists()) {
				src.delete();
			}
			
			for(int i=backupIndex-1; i>=1; i--) {
				src = new File(getFile() + "." + i);
				if(src.exists()) {
					dst = new File(getFile() + "." + (i + 1));
					succeed = src.renameTo(dst);
				}
			}
			
			if(succeed) {
				this.closeFile();
				src = new File(getFile());
				dst = new File(getFile() + ".1");
				succeed = src.renameTo(dst);
				
				if(!succeed) {
					try {
						setFile(getFile(), true, getBufferedIO(), getBufferSize());
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		if(succeed) {
			try {
				setFile(getFile(), false, getBufferedIO(), getBufferSize());
				nextRollOver = 0;
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	protected void setQWForFiles(Writer writer) {
		this.qw = new CountingQuietWriter(writer, getErrorHandler());
	}

	@Override
	protected void subAppend(LoggingEvent event) {
		super.subAppend(event);
		if(getFile() != null && qw != null) {
			long count = ((CountingQuietWriter)this.qw).getCount();
			if(count > fileMaxSize && count > nextRollOver) {
				rollOver();
			}
		}
	}
	
	@Override
	public synchronized void setFile(String fileName, boolean append, boolean bufferedIO, int bufferSize)
			throws IOException {
		super.setFile(fileName, append, bufferedIO, bufferSize);
		
		if(append) {
			File file = new File(fileName);
			((CountingQuietWriter)qw).setCount(file.length());
		}
	}

	public int getBackupIndex() {
		return backupIndex;
	}
	
	public void setBackupIndex(int backupIndex) {
		this.backupIndex = backupIndex;
	}
	
	public long getFileMaxSize() {
		return fileMaxSize;
	}
	
	public void setFileMaxSize(long fileMaxSize) {
		this.fileMaxSize = fileMaxSize;
	}
	
	//----------------------------------------------------------
	//
	//----------------------------------------------------------
	private int backupIndex = 1;
	private long fileMaxSize = 1024*1024*10;
	private long nextRollOver = 0;
}
