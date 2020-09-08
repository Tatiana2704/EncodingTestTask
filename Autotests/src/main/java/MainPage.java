import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

public class MainPage {

    public String mainURL = "https://api.encoding.com/";
    public static WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        driver.get(mainURL);
    }

    //to enter text in search field
    public void enterSearch(String strSearchInput) {
        driver.findElement(By.className("searchbox")).click();
        driver.findElement(By.xpath("//input[@type='search']")).sendKeys("getStatus");
    }

    //to select result form list (value should be in list mandatory)
    public void selectSearchResult(String strSearchResult) throws InterruptedException {
        driver.findElement(By.xpath("//div[@class='SearchResults-list']/a/header[@title='(GET) " + strSearchResult + "']")).click();
        TimeUnit.SECONDS.sleep(3);
    }

    //to get Json from section Response
    //need to do tab Json (response) active, the find json content and parse it
    public String getJsonResponseValueFromGetStatExt(String strJsonPath) {

        WebElement btnJsonForGetStatExt = driver.findElement(By.id("page-responses-getstatus-extended"))
                .findElement(By.xpath("./descendant::div[@class='markdown-body']/descendant::div[@id='response']/parent::h2/following-sibling::div/descendant::button[contains(text(),'JSON')]"));
        if (!btnJsonForGetStatExt.getAttribute("class").contains("active"))
            btnJsonForGetStatExt.click();

        String strJson = driver.findElement(By.id("page-responses-getstatus-extended")).findElement(By.xpath("./descendant::pre[@class='CodeTabs_active']")).getText();

        JSONObject json = new JSONObject(strJson).getJSONObject("response");
        return new ParseJson().getJsonValue(json, strJsonPath);
    }

}
