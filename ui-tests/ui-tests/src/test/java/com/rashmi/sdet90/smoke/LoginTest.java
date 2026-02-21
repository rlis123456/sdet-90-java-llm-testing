package com.rashmi.sdet90.smoke;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://the-internet.herokuapp.com/login");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    private void login(String username, String password) {
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys(username);

        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys(password);

        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }

    private String flashMessageText() {
        WebElement flash = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("flash")));
        return flash.getText();
    }

    @Test
    public void validLogin_shouldSucceed() {
        login("tomsmith", "SuperSecretPassword!");
        Assert.assertTrue(flashMessageText().contains("You logged into a secure area!"),
                "Expected success message after valid login.");
    }

    @Test
    public void invalidPassword_shouldShowError() {
        login("tomsmith", "wrongpassword");
        Assert.assertTrue(flashMessageText().contains("Your password is invalid!"),
                "Expected invalid password message.");
    }

    @Test
    public void emptyFields_shouldShowError() {
        login("", "");
        Assert.assertTrue(flashMessageText().contains("Your username is invalid!"),
                "Expected invalid username message when fields are empty.");
    }
}