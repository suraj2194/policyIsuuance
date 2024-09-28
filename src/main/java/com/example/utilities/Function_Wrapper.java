package com.example.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;


public class Function_Wrapper{


	public void click_on(WebDriver driver, Logger logger, ExtentTest test, String xpath, String name,
			Function_Wrapper obj) throws Exception {
		try {
			Boolean elm = obj.wait_for_element(driver, logger, test, xpath, name);
			boolean is_elm_in_view_point = obj.elm_in_view_point(driver, xpath);
			if (!is_elm_in_view_point) {
				logger.info("Element is not in view point, scrolling to element...");
				obj.scroll_into_view(driver, xpath, name);
			}
			if (elm == true) {
				driver.findElement(By.xpath(xpath)).click();
				logger.info("Clicked on " + name);
			} else {
				logger.info(name + " not displayed in web page");
				throw new Exception(name + " not displayed in web page");
			}
			;
		} catch (Exception e) {
			logger.info("Unable to click on:" + name);
			throw new Exception(name + " not displayed in web page");
		}

	}
	public void send_keys(WebDriver driver, Logger logger, ExtentTest test, String xpath, String keys, String name,
			Function_Wrapper f_obj) throws Exception {
		try {
			f_obj.wait_for_element(driver, logger, test, xpath, name);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(45));
			WebElement Element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
			f_obj.scroll_into_view(driver, xpath, name);
			Element.sendKeys(keys);
			logger.info("Keys sent to " + name + " " + keys);
		} catch (Exception e) {
			logger.info("Unable to send keys " + name + " " + keys);
			throw new Exception("Unable to send keys " + name);
		}
	}
	

	public WebElement find_element(WebDriver driver, Logger logger, String xpath, String name) throws Exception {
		try {
			WebElement f_Elm = driver.findElement(By.xpath(xpath));
			return f_Elm;
		} catch (Exception e) {
			throw new Exception("Unable to find the " + name);
		}

	}
	public boolean elm_in_view_point(WebDriver driver, String xpath) {
		WebElement elm = driver.findElement(By.xpath(xpath));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return (boolean) js.executeScript(
				"var elem = arguments[0], box = elem.getBoundingClientRect(), cx = box.left + box.width / 2,  cy = box.top + box.height / 2,e = document.elementFromPoint(cx, cy);for (; e; e = e.parentElement) { if (e === elem) return true;} return false;",
				elm);
	}
	public void scroll_into_view(WebDriver driver, String xpath, String name) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement elm = driver.findElement(By.xpath(xpath));
		String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
				+ "var elementTop = arguments[0].getBoundingClientRect().top;"
				+ "window.scrollBy(0, elementTop-(viewPortHeight/2));";
		js.executeScript(scrollElementIntoMiddle, elm);
		Thread.sleep(500);
	}








	public String get_current_dateTime() {
		SimpleDateFormat dateFormatreport = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss.SSS");
		String execution_time_report = dateFormatreport.format(new Date());
		// System.setProperty("current.date.time.report", execution_time_report);
		return execution_time_report;
	}
	public int get_row_count_of_excel(String path, String excel_Name, String sheet_Name) throws IOException {
		File file = new File(path + "/" + excel_Name + ".xlsx");
		FileInputStream excel_File = new FileInputStream(file);
		XSSFWorkbook xssf_Workbook = new XSSFWorkbook(excel_File);
		XSSFSheet sheet = xssf_Workbook.getSheet(sheet_Name);
		int row_Count = 0;
		try {

			row_Count = sheet.getPhysicalNumberOfRows();
			excel_File.close();
			xssf_Workbook.close();

		} catch (Exception e) {
			excel_File.close();
			xssf_Workbook.close();
			throw e;

		}
		excel_File.close();
		xssf_Workbook.close();
		return row_Count;
	}

	public int get_col_count_of_excel(String path, String excel_Name, String sheet_Name) throws IOException {
		File file = new File(path + "/" + excel_Name + ".xlsx");
		FileInputStream excel_File = new FileInputStream(file);
		XSSFWorkbook xssf_Workbook = new XSSFWorkbook(excel_File);
		XSSFSheet sheet = xssf_Workbook.getSheet(sheet_Name);
		int cell_Count = 0;
		try {

			cell_Count = sheet.getRow(0).getPhysicalNumberOfCells();
			excel_File.close();
			xssf_Workbook.close();

		} catch (Exception e) {
			excel_File.close();
			xssf_Workbook.close();
			throw e;

		}
		excel_File.close();
		xssf_Workbook.close();
		return cell_Count;
	}

	public Object[][] get_Excel_Data(String path, String excel_Name, String sheet_Name, Function_Wrapper obj)
			throws Exception {
		File file = new File(path + "/" + excel_Name + ".xlsx");
		FileInputStream excel_File = new FileInputStream(file);
		XSSFWorkbook workbook = new XSSFWorkbook(excel_File);
		XSSFSheet sheet = workbook.getSheet(sheet_Name);
		int row_Count = obj.get_row_count_of_excel(path, excel_Name, sheet_Name);
		int col_Count = obj.get_col_count_of_excel(path, excel_Name, sheet_Name);
		Object obj_Data[][] = new Object[row_Count - 1][col_Count];
		try {

			for (int i = 1; i < row_Count; i++) {
				for (int j = 0; j < col_Count; j++) {
					DataFormatter formatter = new DataFormatter();
					String cell_Data = formatter.formatCellValue(sheet.getRow(i).getCell(j));
					// String cell_Data = sheet.getRow(i).getCell(j).toString();
					obj_Data[i - 1][j] = cell_Data;
				}
			}
	s	} catch (Exception e) {
			excel_File.close();
			workbook.close();
			return null;
		}
		excel_File.close();
		workbook.close();
		return obj_Data;

		
	}
	public String get_xpath(String key_Name) throws IOException {
		try {
			FileInputStream file = new FileInputStream(
					System.getProperty("user.dir") + "/test_data/Xpath_ops.properties");
			Properties pro = new Properties();
			pro.load(file);
			String xPath = pro.getProperty(key_Name);
			file.close();
			return xPath;
		} catch (Exception e) {
			throw e;}
		}
		public WebElement explicitly_Wait(WebDriver driver, WebElement elm) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
			WebElement elemennt = wait.until(ExpectedConditions.elementToBeClickable(elm));
			return elemennt;
		}
		public Boolean wait_for_element(WebDriver driver, Logger logger, ExtentTest test, String xpath, String name)
		throws Exception {
	try {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		Boolean visible = wait
				.ignoring(StaleElementReferenceException.class)
				.until(ExpectedConditions.and(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)),
						ExpectedConditions.elementToBeClickable(By.xpath(xpath)),
						ExpectedConditions.presenceOfElementLocated(By.xpath(xpath))));
		return visible;

	} catch (Exception e) {
		System.out.println(e);
		logger.info("Unabled to find the " + name);
		throw new Exception("Unabled to find the " + name);
	}

}

	
		public void click_using_mouse(WebDriver driver, Logger logger, ExtentTest test, String xpath, String name,
				Function_Wrapper obj) throws Exception 
				{
			try {
				Boolean elm = obj.wait_for_element(driver, logger, test, xpath, name);
				if (elm == true)
				 {
					WebElement element = driver.findElement(By.xpath(xpath));
					Actions builder = new Actions(driver);
					builder.moveToElement(element).click(element);
					builder.perform();
					logger.info("Clicked on " + name);
				}
	
				else 
				{
					logger.info(name + " not displayed in web page");
				}
			} catch (Exception e) {
				logger.info("Unable to click on:" + name);
				throw new Exception("Unable to click on:" + name);
			}
			
		}
		
				

	

	public String get_test_data(String property_Name) throws IOException {
		FileInputStream file = new FileInputStream(
				System.getProperty("user.dir") + "/test_data/data_file_ops.properties");
		Properties pro = new Properties();
		pro.load(file);
		String property = pro.getProperty(property_Name);
		file.close();
		return property;
	}
	public void disable_implicit(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
	}

	public void enable_implicit(WebDriver driver, int time_In_Second) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(time_In_Second));
	}
	
		public void select_from_dropdown(WebDriver driver, Logger logger, ExtentTest test,String xpath,String excel_key,String name, Function_Wrapper obj) throws Exception {
			try {
				// Click on the dropdown to open it
				//driver.findElement(By.xpath("xpath")).click();
	
				// Format and select the insurer from the dropdown
				String selectInsurer = obj.get_xpath("xpath");
				obj.click_on(driver, logger, test, String.format(selectInsurer, excel_key), excel_key + excel_key+"selected", obj);
			} catch (Exception e) {
				logger.info("Unable to select: " + name);
				throw new Exception("Unable to click on : " + name, e);
			}
		}
	}
	
	
	



