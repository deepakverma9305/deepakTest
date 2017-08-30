package com.ilabquality.gtaf.guitests.helpers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.By;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class SeleniumObjectMapper {

	public static ArrayList<Page> getPages(String omPath){
		ArrayList<Page> returnList = new ArrayList<Page>();
		Workbook _workBook = null;
		int debug=0;
		try {
			System.out.println("Object Management File ::::::" + omPath);
			_workBook = Workbook.getWorkbook(new File(omPath));
			Sheet sheet = _workBook.getSheet(0);
			int recordCounter = 1; //First row is headers
			Page currentPage = null;
			while (recordCounter <= (sheet.getRows() -1)) { 
				
				//Determine if we are on a page, or page object. 
				if ( sheet.getCell(1,recordCounter).getContents().equalsIgnoreCase("page") ){
					//We are on a page.
							Page pg = new Page(sheet.getCell(0,recordCounter).getContents(),sheet.getCell(1,recordCounter).getContents());
							returnList.add(pg);
							currentPage = pg;
							recordCounter++;
							continue;
				}else{
					int d=0;
					PageObject po = new PageObject();
					po.setId(sheet.getCell(0,recordCounter).getContents());
					po.setLocator(sheet.getCell(2,recordCounter).getContents());
					po.setBy(getBy(sheet.getCell(1,recordCounter).getContents(),sheet.getCell(2,recordCounter).getContents()));
					try{
						Cell c = sheet.getCell(3,recordCounter);
						String type = c.getContents();
						po.setType(type);
						String width = sheet.getCell(4,recordCounter).getContents();
						po.setMaxLength(width);
					}catch(ArrayIndexOutOfBoundsException aioob){
						// Do nothing, no data.
						System.out.println(po.toString());
						aioob.printStackTrace();
					}
					
					currentPage.getPageObjects().add(po);
					recordCounter++;
				}
			}
			System.out.println("ObjectMap Loaded");
			_workBook.close();
			}catch(BiffException be){
				be.printStackTrace();
			}catch(IOException ioe){
				ioe.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}
		return returnList;
	}
	
	private static By getBy(String cond,String crit){
		switch (cond.toLowerCase()){
			case("linktext"):{
				return new By.ByLinkText(crit);
			}
			case("css"):{
				return new By.ByCssSelector(crit);
			}
			case("xpath"):{
				return new By.ByXPath(crit);
			}
			default:{
				return new By.ById(crit);
			}
		}
	}
	
	public static void main(String[] args) {
		SeleniumObjectMapper.getPages("D:\\pangea.poc.priv.autotest\\GTAF\\src\\com\\ilabquality\\gtaf\\objectmap\\Object.xls");
	}
}
