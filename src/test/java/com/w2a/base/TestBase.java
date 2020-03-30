package com.w2a.base;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {

    public static WebDriver driver;
    public static Properties config = new Properties();
    public static Properties OR = new Properties();
    public static FileInputStream fis;
    public static Logger log = Logger.getLogger("devpinoyLogger");


    @BeforeSuite
    public void setUp(){

        if(driver==null){

            FileInputStream fis = null;
            try {
                fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\resources\\properties\\Config.properties");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                config.load(fis);
                log.debug("Config file loaded!!");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\resources\\properties\\OR.properties");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                OR.load(fis);
                log.debug("OR file loaded!!");
            } catch (IOException e) {
                e.printStackTrace();
            }


            if(config.getProperty("browser").equals("firefox")) {
                driver = new FirefoxDriver();

            }else if(config.getProperty("browser").equals("chrome")){
                System.setProperty("webdriver.chrome.driver","D:\\QA\\AA Torrent Tutorials\\Selenium WebDriver with Java -Basics to Advanced+Frameworks\\19. Framework Part -3- Maven- Build Management tool\\DataDrivenFramework\\src\\main\\resources\\exceutables\\chromedriver.exe");
                driver = new ChromeDriver();
                log.debug("Google Chrome Launched!!");

            }
            else if(config.getProperty("browser").equals("ie")){
                System.setProperty("webdriver.ie.driver","D:\\QA\\AA Torrent Tutorials\\Selenium WebDriver with Java -Basics to Advanced+Frameworks\\19. Framework Part -3- Maven- Build Management tool\\DataDrivenFramework\\src\\main\\resources\\exceutables\\IEDriverServer.exe");
                driver = new InternetExplorerDriver();
                log.debug("IEe Launched!!");

            }

            driver.get(config.getProperty("testsiteurl"));
            log.debug("Navigated to: "+config.getProperty("testsiteurl"));
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);




        }



    }

    public boolean isElementPresent(By by){

        try{
            driver.findElement(by);
            return true;
        }catch(NoSuchElementException e){
            return false;
        }
    }

    @AfterSuite
    public void tearDown(){

        if(driver!=null) {
            driver.quit();


        }
        log.debug("Test Excution Completed!!");
    }

}
