package com.example;

import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.example.utilities.Function_Wrapper;

public class Ops_main {
	private Logger logger;
	public WebDriver driver;
	public WebElement element;
	public Function_Wrapper f_obj;
	public ExtentTest test;
	public String property_File_Name = "data_file_ops.properties";
	public Object partnerPortal;
	int sum;

	public Ops_main(WebDriver driver, ExtentTest test, Logger logger, Function_Wrapper f_obj) {
		this.driver = driver;
		this.logger = logger;
		this.test = test;
		this.f_obj = f_obj;
	}

	public void Ops_main()
			throws Exception {
			

		logger.info("------------------Test case started------------");
		System.out.println("Start");
		driver.get(f_obj.get_test_data("url_prod"));
		 test.log(Status.INFO, "Performing test on :" + driver.getCurrentUrl());
		 logger.info("Performing test on : " + driver.getCurrentUrl());
		 f_obj.enable_implicit(driver, 120);
		 Thread.sleep(10000);
			f_obj.click_on(driver, logger, test, f_obj.get_xpath("Click_on_sign"), "Click_on_Click_on_sign", f_obj);
		f_obj.send_keys(driver, logger, test, f_obj.get_xpath("Email"), f_obj.get_test_data("email"), "Enter_email",
				f_obj);
	//

}
}
