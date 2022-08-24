package com.automation.utils;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automation.common.Mappings;

public class FileUtils extends Mappings{

	private Logger log= LoggerFactory.getLogger(FileUtils.class);
	
	public boolean createFolder(String directory) {
		boolean flag = false;
		File file = new File(directory);
		if(!file.exists()) {
			log.info("Creating Folder"+directory);
			file.mkdirs();
			flag = true;
		}
		else
			log.info("Folder is already available in directory !");
		
		return flag;
	}
	
}
