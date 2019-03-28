package hellocucumber;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.apache.http.util.Asserts;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Mass {

	private static WebDriver webdriver=null;

	@Before("@mass")
	public void beforeBrowserMass() {
		System.out.println("Webdriver instance : " + webdriver);
		if (webdriver == null)
			webdriver = new ChromeDriver();
		webdriver.manage().window().maximize();
	}

	@Given("^I am on Massportal \"([^\"]*)\" page$")
	public void i_am_on_Massportal_page(String arg1) throws Exception {
		//webdriver.navigate().to("");
		webdriver.get("");
	}

	@When("^I enter the \"([^\"]*)\" and \"([^\"]*)\"$")
	public void i_enter_the_and(String username, String password) throws Exception {
		Select domainSelect = new Select(webdriver.findElement(By.id("ldapFilterAlias")));
		domainSelect.selectByVisibleText("MCOM");

		WebElement uname = webdriver.findElement(By.id("username"));
		uname.sendKeys("");

		WebElement pword = webdriver.findElement(By.id("password"));
		pword.sendKeys("");
	}

	@When("^click log in button$")
	public void click_log_in_button() throws Exception {
		WebElement loginBtn = webdriver.findElement(By.className("btn-submit"));
		loginBtn.submit();
	}

	@Then("^I should be in massportal main page \"([^\"]*)\"$")
	public void i_should_be_in_massportal_main_page(String masspageTitle) throws Exception {
		new WebDriverWait(webdriver, 10L).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.getTitle().toUpperCase().startsWith(masspageTitle);
			}
		});
	}

	@Given("^I am in the massportal main page with pageTitle as \"([^\"]*)\"$")
	public void i_am_in_the_massportal_main_page_with_pageTitle_as(String pageTitle) throws Exception {
		if (webdriver != null) {
			System.out.println("Current URL " + webdriver.getCurrentUrl());
			assertEquals("http://ma209mlvprt010.macys.com:8080/mass/app/index", webdriver.getCurrentUrl());
			assertEquals("MASS", pageTitle);
		} else {
			System.out.println("Cannot capture since webdriver is null");
		}		
	}

	@Given("^DataCenter select should be clickable$")
	public void datacenter_select_should_be_clickable() throws Exception {
		if (webdriver != null) {
			WebElement selectElement = webdriver.findElement(By.id("changeDCSelect"));
			assertEquals(true, isClickable(webdriver, selectElement));
		} else {
			System.out.println("==> webdriver is NULL");
		}
	}

	@When("^DataCenter select having \"([^\"]*)\" and \"([^\"]*)\" value in it$")
	public void datacenter_select_haveing_and_value_in_it(String dalDC, String wdcDC) throws Exception {
		
		//Maintain the same order specified in the Dropdown box, Otherwise test will fail for assertArrayEquals 
		String eArr[] = { dalDC, "RTP", wdcDC }; 

		Select dcSelect = new Select(webdriver.findElement(By.id("changeDCSelect")));
		List<WebElement> dcvalues = dcSelect.getOptions();
		String dcnameArr[] = new String[dcvalues.size()];

		int i = 0;
		for (WebElement el : dcvalues) {
			dcnameArr[i] = el.getText();
			i++;
		}
		assertArrayEquals(eArr, dcnameArr);
	}

	@Then("^Choose \"([^\"]*)\" as a first step$")
	public void choose_as_a_first_step(String dalDC) throws Exception {
		Select dcselect = new Select(webdriver.findElement(By.id("changeDCSelect")));
		dcselect.selectByVisibleText(dalDC);
	}

	public boolean isClickable(WebDriver webdriver, WebElement webelement) {
		try {
			WebDriverWait webdriverWait = new WebDriverWait(webdriver, 10);
			webdriverWait.until(ExpectedConditions.elementToBeClickable(webelement));
			return true;
		} catch (Exception ee) {
			System.out.println("Exception ee: "+ ee);
			return false;
		}
	}

	
	//URL selection
	@When("^URL-Dropdown having the value \"([^\"]*)\"$")
	public void url_Dropdown_having_the_value(String actual) throws Exception {
	   Select urlselect = new Select(webdriver.findElement(By.id("DAL_environment")));
	   boolean urlselect_contains_fcc_cell=false;
	  	   
	  List<WebElement> lurls = urlselect.getOptions();
	   for(WebElement element: lurls) {
		   urlselect_contains_fcc_cell = element.getText().contains(actual);
		   if(urlselect_contains_fcc_cell)   break;
	   }
	   assertEquals(true,urlselect_contains_fcc_cell);
	}

	@Then("^Choose \"([^\"]*)\" and trigger change button$")
	public void choose_and_trigger_change_button(String arg1) throws Exception {
		WebElement element = isElementPresent(webdriver,"DAL_environment");
		if(element !=null) {
			 Select urlselect = new Select(element);
			 urlselect.selectByValue("FCC|DAL|scp_cell");
		}
		WebElement change_button = webdriver.findElement(By.id("changeEnvironmentSubmit"));
		change_button.click();			  
			
	}

	@Then("^Select CacheSearch$")
	public void select_CacheSearch() throws Exception {
	 WebElement cache_link = webdriver.findElement(By.id("CacheSearch"));
	 cache_link.click();
	}
	
	public WebElement isElementPresent(WebDriver webdriver,String str) {
		try {
			  WebElement element =  webdriver.findElement(By.id(str));
			  return element;
		}catch(NoSuchElementException ex) {
				return null;
		}
	}
	
	@Then("^Select StoreAttributes$")
	public void select_StoreAttributes() throws Exception {
	 Select storeAttributeSelect = new Select(webdriver.findElement(By.id("formSelect")));
	 storeAttributeSelect.selectByVisibleText("Store Attributes");	 
	}
	
	
	
	//--Input store number and verify the string ---
	@Given("^\"([^\"]*)\" selected in the dropdown$")
	public void selected_in_the_dropdown(String expectedOption) throws Exception {
		 Select storeAttributeSelect = new Select(webdriver.findElement(By.id("formSelect")));
		 String selectedOption=storeAttributeSelect.getFirstSelectedOption().getText();
		 assertEquals(expectedOption,selectedOption);
	}

	@When("^Input store_id$")
	public void input_store_id() throws Exception {
	  WebElement inputStoreId = webdriver.findElement(By.id("multiplecheckTextArea"));
	  inputStoreId.sendKeys("212");
	}

	@When("^Trigger search$")
	public void trigger_search() throws Exception {
		WebElement searchBtn = webdriver.findElement(By.id("search"));
		searchBtn.click();   
	}

	@Then("^Verify the selected store number$")
	public void verify_the_selected_store_number() throws Exception {
	    //Get the inputTextBox
		String enteredStoredId = webdriver.findElement(By.id("multiplecheckTextArea")).getText();
		String outText = webdriver.findElement(By.cssSelector(".form > b")).getText();
		System.out.println("Out Text :: "+ outText);
		assertEquals(enteredStoredId,outText);
				 
	   
	}
	
	
	
}