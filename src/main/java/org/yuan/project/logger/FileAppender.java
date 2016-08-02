package org.yuan.project.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;

import org.apache.log4j.helpers.LogLog;
import org.yuan.project.logger.helpers.QuietWriter;

public class FileAppender extends WriterAppender {
	
	public FileAppender() {
	}
	
	public FileAppender(Layout layout, String fileName, 
		boolean append, boolean bufferedIO, int bufferSize) throws IOException {
		setLayout(layout);
		setFile(fileName, append, bufferedIO, bufferSize);
	}

	@Override
	public void activateOptions() {
		if(fileName != null) {
			try {
				setFile(fileName, fileAppend, bufferedIO, bufferSize);
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			LogLog.warn("File option not set for appender [" + getName() + "].");
		}
	}

	public String getFile() {
		return fileName;
	}

	public void setFile(String fileName) {
		this.fileName = fileName;
	}
	
	public boolean getBufferedIO() {
		return bufferedIO;
	}

	public void setBufferedIO(boolean bufferedIO) {
		this.bufferedIO = bufferedIO;
	}

	public boolean getAppend() {
		return fileAppend;
	}

	public void setAppend(boolean fileAppend) {
		this.fileAppend = fileAppend;
	}

	public int getBufferSize() {
		return bufferSize;
	}

	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}

	public synchronized void setFile(String fileName, 
		boolean append, boolean bufferedIO, int bufferSize) throws IOException {
		if(bufferedIO) {
			setFlush(false);
		}
		
		reset();
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fileName, append);
		} catch(FileNotFoundException e) {
			String dir = new File(fileName).getParent();
			if(dir != null) {
				File theDir = new File(dir);
				if(!theDir.exists() && theDir.mkdirs()) {
					fos = new FileOutputStream(fileName, append);
				} else {
					throw e;
				}
			} else {
				throw e;
			}
		}
		
		Writer writer = createWriter(fos);
		if(bufferedIO) {
			writer = new BufferedWriter(writer);
		}
		this.setQWForFiles(writer);
		this.fileName = fileName;
		this.fileAppend = append;
		this.bufferedIO = bufferedIO;
		this.bufferSize = bufferSize;
	}
	
	protected void setQWForFiles(Writer writer) {
		this.qw = new QuietWriter(writer, getErrorHandler());
	}

	protected void closeFile() {
		if(qw != null) {
			try {
				this.qw.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	protected void reset() {
		closeFile();
		this.fileName = null;
		super.reset();
	}
	
	//-----------------------------------------------------------
	//
	//-----------------------------------------------------------
	private String fileName = null;
	private boolean bufferedIO = false;
	private boolean fileAppend = true;
	private int bufferSize = 8 * 1024;	
}
