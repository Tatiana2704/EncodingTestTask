import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.http.HttpStatus;
import org.hamcrest.Matcher;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.json.Json;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Tests {

    private WebDriver driver;
    private DriverManager driverManager;

    @BeforeSuite
    public void initialize() throws IOException, InterruptedException {
        driverManager = new DriverManager();
        driver = driverManager.getDriver();
    }

    @Test
    public void Test1() throws InterruptedException {

        //step 1
        MainPage mainPage = new MainPage(driver);

        //step 2
        mainPage.enterSearch("getStatus");
        //step 3
        mainPage.selectSearchResult("GetStatus (extended)");

        //step 4
        Assert.assertEquals(driver.getCurrentUrl(),"https://api.encoding.com/reference#responses-getstatus-extended");

        //step 5
        Assert.assertEquals(mainPage.getJsonResponseValueFromGetStatExt("job/processor"),"[AMAZON | RACKSPACE]");
        Assert.assertEquals(mainPage.getJsonResponseValueFromGetStatExt("job/format/status"),"[Status]");
    }

    @AfterSuite
    public void quit() {
        driverManager.quitDriver();
    }



    @Test
    public void Test2(){

        String path = "https://status.encoding.com/status.php?format=";

        //to check xml format
        String uptimeSecondsXml = given().when().get(path + "xml")
                .then().statusCode(HttpStatus.SC_OK).assertThat()
                .body("response.status",equalTo("Ok"))
                .body("response.incident_count.lastYear",equalTo("1"))
                .extract().path("response.uptime");
        Assert.assertTrue(TimeUnit.SECONDS.toDays(Integer.parseInt(uptimeSecondsXml))>1,"Uptime for format xml is less or equal one day");

        //to check json format
        Response response = get(path + "json");

        Assert.assertEquals(response.jsonPath().get("status"),"Ok");
        Assert.assertEquals(response.jsonPath().get("incident_count.lastYear"),"1");
        Integer uptimeSecondsJson = Integer.parseInt(response.jsonPath().get("uptime").toString());
        Assert.assertTrue(TimeUnit.SECONDS.toDays(uptimeSecondsJson)>1,"Uptime for format json is less or equal one day");
    }
}
