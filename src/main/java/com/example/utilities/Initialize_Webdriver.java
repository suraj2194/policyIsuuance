package com.example.utilities;

import java.net.MalformedURLException;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;



public class Initialize_Webdriver {
	public WebDriver getDriver() throws MalformedURLException {
		   //String remote_browser="http://selenium-hub:4444/wd/hub";
		ChromeOptions chromeOptions = new ChromeOptions();
	    WebDriverManager.chromedriver().setup();
	    // chromeOptions.addArguments("--headless");
	     chromeOptions.addArguments("--no-sandbox");
	    chromeOptions.addArguments("--window-size=1920x1080");
	    chromeOptions.addArguments("incognito");
	    chromeOptions.setPlatformName("LINUX");

	    WebDriver driver;
		driver = new ChromeDriver(chromeOptions);
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(45));
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(45));
	    driver.manage().window().maximize();
		return driver;
		// WebDriver driver;
		// ChromeOptions chrome=new ChromeOptions();
        // WebDriverManager.chromedriver().setup();
        // driver=new ChromeDriver(chrome);
		// driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(45));
		//  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(45));
		// driver.manage().window().maximize();
		//return driver;
	}
	// public String create_folder_for_today_report(String current_execution_time) {
	// String path = System.getProperty("user.dir") +
	// "/Reports/Fyntune_Motor_Reports/"
	// + current_execution_time;
	// File file = new File(path);
	// if (!file.exists()) {
	// System.out.println("Not exist");
	// file.mkdirs();
	// } else {
	// System.out.println("Folder exist");
	// }

	// return path;
	// }
}
