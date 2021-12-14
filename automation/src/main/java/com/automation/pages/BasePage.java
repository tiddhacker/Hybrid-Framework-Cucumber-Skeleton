package com.automation.pages;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Date;

import javax.imageio.ImageIO;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.automation.common.Mappings;
import com.automation.constants.Constant;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

public class BasePage extends Mappings {
	
	//for extent report
		private static String screenshotname;

		public  void captureScreenshot() {
			waitforpageload();
			byte[] imageArray = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.BYTES);
			screenshotname = returnDateStamp(".jpg");
			ByteArrayInputStream bis = new ByteArrayInputStream(imageArray);
			try {
				
				fileutils.createFolder(Constant.IMAGE_DIRECTORY);
				BufferedImage bufferedImage = ImageIO.read(bis);
				ImageIO.write(scaleImage(bufferedImage, 2), "jpg", new File(Constant.IMAGE_DIRECTORY+screenshotname));
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				ExtentCucumberAdapter.addTestStepLog("Screenshot:");
				ExtentCucumberAdapter.addTestStepLog("<br>");
				ExtentCucumberAdapter.addTestStepLog("<a target=\"_blank\",href="+returnScreenshotName()+"><img src="
						+returnScreenshotName()+" style=\"width: 30%; height: 30%\"></img></a>");
				System.out.println("Adding Screenshot into report");
			} catch (Exception e) {
				
				log.error("Failed to add screenshot to report !");
			}
			
		}
		
		public static String returnDateStamp(String fileExtension) {
			Date d = new Date();
			String date = d.toString().replace(":", "_").replace(" ", "_")+fileExtension;
			return date;
	}
		
		public static String returnScreenshotName() {
			return ("imgs/"+screenshotname).toString();
		}
		
		private static BufferedImage scaleImage(BufferedImage bufferedImage, int size) {
			
			int origWidth = bufferedImage.getWidth();
			int oriHeight = bufferedImage.getHeight();
			int scaleWidth = origWidth/size;
			int scaleHeight = oriHeight/size;
			
			Image scaledImage = bufferedImage.getScaledInstance(scaleWidth, scaleHeight, Image.SCALE_SMOOTH);
			BufferedImage scaledBI = new BufferedImage(scaleWidth, scaleHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = scaledBI.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(scaledImage,0,0,null);
			g.dispose();
			return (scaledBI);
		}

		//for extent report ends
	
}