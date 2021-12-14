package com.automation.utils;

import java.io.File;

import com.automation.common.Mappings;

public class FileUtils extends Mappings{

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
