package com.example;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.example.utilities.Function_Wrapper;
import com.example.utilities.Initialize_Webdriver;
public class Ops_test {
    private ExtentTest test;
    private ExtentReports report;
    private ExtentSparkReporter spark;
    // private org.apache.log4j.Logger logger;
    private WebDriver driver;
    private WebElement element;
    private Function_Wrapper f_Obj;
    private Initialize_Webdriver web_Driver;
    private String execution_time;
    private String path;
    private String report_file_name;
    // private Slack_Webhook slack_obj;

    private String report_name;
    private String final_report_link;
    private String log_File_Name;
    public static int total_execute_test = 0, total_passed_test = 0, total_failed_test = 0;
    private String failed_page_url;
    FileHandler fh;

    private static Logger logger = Logger.getLogger("My_logs_policyIssuance_test");

    @BeforeClass
    public void beforTest() throws InterruptedException {

        f_Obj = new Function_Wrapper();
        web_Driver = new Initialize_Webdriver();
        report = new ExtentReports();
        execution_time = f_Obj.get_current_dateTime();
        // slack_obj= new Slack_Webhook();
        report_name = "Partner Portal";
       // path = f_Obj.create_Folder_For_Today_Report(execution_time);
        report_file_name = "Partner_Portal_" + execution_time + ".html";
        spark = new ExtentSparkReporter(path + "/" + report_file_name);
        report.attachReporter(spark);
    }

    @DataProvider(name = "Excel_Data")
    public Object[][] get_Data() throws Exception {
        Object data[][] = f_Obj.get_Excel_Data(System.getProperty("user.dir"), "Policy_Issuance_Data", "Sheet2", f_Obj);
        return data;

    }

    @Test(dataProvider ="Excel_Data")
    public void policyIssuance()
            throws Exception {
        total_execute_test += 1;
        try {

            // creating the unique log file name for each test execution
            System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
            log_File_Name = "partner_portal_login_" + execution_time;
            fh = new FileHandler(path + "/" + log_File_Name + ".txt");
            // logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            // thread safe method to configure the log file name
            // logger=f_Obj.log_file_configuration(log_File_Name, path,
            // "com.jcg.log4j1.example1");

            // 'log_File_Name' property is configured at log4j level
            // System.setProperty("log_File_Name", log_File_Name);
            // to set the log file path
            // System.setProperty("log_path", path);

            test = report.createTest("Partner portal login");
            driver = web_Driver.getDriver();
            test.log(Status.INFO, "Polcy Issuance");
            Ops_main ops_obj = new Ops_main(driver, test, logger, f_Obj);
            ops_obj.Ops_main();
            String scr_Name = "partner_login" + execution_time;
            // String captured_SRC_Path = f_Obj.get_screenshot(driver, logger, path+"/",
            // scr_Name);
            test.log(Status.PASS, "Test case pass");    
            // test.log(Status.PASS, "<a href='http://rb-media-dev.s3.amazonaws.com/Fyntune/Automation_report/" + scr_Name
            //         + ".png" + "'>Screenshot</a>");
            // test.log(Status.INFO, "<a href='http://rb-media-dev.s3.amazonaws.com/Fyntune/Automation_report/"
            //         + "logfile_" + log_File_Name + ".txt'>Logs</a>");
            // driver.quit();
            total_passed_test += 1;
        } catch (Exception ex) {
            System.out.println("----------------");
          //  failed_page_url = driver.getCurrentUrl();
            total_failed_test += 1;
            // same configuration need to set at catch level as well in case of any test
            // case will be failed
            log_File_Name = "sleep_login_" + execution_time;
            ITestResult current_Result = Reporter.getCurrentTestResult();
            current_Result.setStatus(2);
            String scr_Name = "policyissuance_login" + execution_time;
            // String captured_SRC_Path = f_Obj.get_screenshot(driver, logger, path+ "/",
            // scr_Name);
            test.log(Status.FAIL, ex.getMessage());
            test.log(Status.FAIL, "Test case failed");
            test.log(Status.INFO, "Failed page url : " + failed_page_url);

          //  driver.quit();
            // test.log(Status.FAIL, "<a href='http://rb-media-dev.s3.amazonaws.com/Fyntune/Automation_report/" + scr_Name
            //         + ".png" + "'>Screenshot</a>");
            // test.log(Status.INFO, "<a href='http://rb-media-dev.s3.amazonaws.com/Fyntune/Automation_report/"
            //         + "logfile_" + log_File_Name + ".txt'>Logs</a>");
            logger.info(ex.getMessage());
            System.out.println(ex.getMessage());
        }

    }

    @AfterTest
    public void tear_Down() {
        driver.quit();
        // report.flush();
        /*
         * AWS_upload aws= new AWS_upload(); final_report_link=aws.aws_s3_upload(path,
         * report_file_name); slack_obj.slack_status_notification(report_name,
         * execution_time, total_execute_test, total_passed_test, total_failed_test,
         * final_report_link); }
         */
    }
}
   

