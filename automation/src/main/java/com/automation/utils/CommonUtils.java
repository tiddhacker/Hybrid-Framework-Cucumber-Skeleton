package com.automation.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import com.automation.common.Mappings;
import com.automation.constants.Constant;

public class CommonUtils extends Mappings {
	
	public Boolean setProperties(String fileName, String key, String value) {
		
		Boolean status=false;
		
		try {
			FileInputStream fis = new FileInputStream(Constant.CONFIG_FILE_PROPERTIES_DIRECTORY+fileName+".properties");
			Properties prop = new Properties();
			prop.load(fis);
			fis.close();
			FileOutputStream out = new FileOutputStream(Constant.CONFIG_FILE_PROPERTIES_DIRECTORY+fileName+".properties");
			prop.setProperty(key, value);
			prop.store(out, null);
			out.close();
			status =true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return status;
	}
	
	public Properties getProperties(String fileName) {
		Properties prop = new Properties();
		try {
			FileInputStream in = new FileInputStream(Constant.CONFIG_FILE_PROPERTIES_DIRECTORY+fileName+".properties");
			prop.load(in);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return prop;
	}
	
}
