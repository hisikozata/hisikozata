/*
 * COMMON FUNCTIONS CLASS
 * Hatice ISIK OZATA
 * [Case Study]- Keytorc
 * 19.10.2016
 * (Ceren Ozbagci)
 */

/*
 * !!!Teste baslamadan once setUp()'in altindan gecko.driver pathini guncelleyiniz.!!!
 */

package UnitTest_Selenium;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

public class CommonFunctions {
	 public static WebDriver driver=null; 
	 public boolean browserAlreadyOpen=false;
	 public boolean isAlreadyLogIn=false;
	 public JavascriptExecutor jse = (JavascriptExecutor) driver;
	 WebElement element,parentElement,childElement;
	 String productId;
	 
	 //Belirtilen web sayfasini acar.
	 public void initBrowser(String webAddr){
		  System.setProperty("webdriver.gecko.driver", "C:\\Users\\neco\\Downloads\\Compressed\\geckodriver-v0.11.1-win64\\geckodriver.exe");
		  driver = new FirefoxDriver();
		  //Check If browser Is already opened during previous test execution.
		  if(!browserAlreadyOpen){
		  //driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		  driver.get(webAddr);
		  driver.manage().window().maximize();
		  browserAlreadyOpen=true;
		  }
		 } 
	 
	//To Close Browser
	 public void closeBrowser(){
	  driver.quit();
	  browserAlreadyOpen=false;
	 } 
	 
	//Sayfaya log-in olur.
    public void logIn(String userID, String password){
    	//Onceden log-in olunmussa bu kisim calismaz.
    	   if(!isAlreadyLogIn){
    	    //Onceden log-in olunmamissa, log-in olunur.
    		    driver.findElement(By.cssSelector("a[href*='https://www.n11.com/giris-yap']")).click();
    		    sleep(10000);
    		    driver.findElement(By.id("email")).sendKeys(userID);
    		    driver.findElement(By.id("password")).sendKeys(password);
    		    driver.findElement(By.id("loginButton")).click();
    		    sleep(10000);
                isAlreadyLogIn=true;
    	   }    	
    }
    
   //istenilen kelimeyi aratir.
   public void search(String keyword) throws Exception{
	   driver.findElement(By.id("searchData")).sendKeys(keyword);
	   driver.findElement(By.xpath("//*[@title='ARA']")).click();
	   sleep(10000);
   }

   //Istenilen sayfaya gider.
   public void goToPage(String pageNumber) throws Exception{
	   //Belirtilen sayfa numarasine gider
	   String hrefDetails;
	   String hrefDetails_ = "a[href*='http://www.n11.com/arama?q=samsung&pg=";
	   hrefDetails = hrefDetails_.concat(pageNumber+"']");
//       jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
//       sleep(10000);
	   driver.findElement(By.cssSelector(hrefDetails)).click();
	   sleep(10000);
 }
   
  //Arama sonuclarini gonderir.
  public String searchResult() throws Exception{	
	  //Samsung kelimesinin ekranda gorulebildigini gosterir.
	  parentElement = driver.findElement(By.xpath("//a[@href='http://www.n11.com/arama?q=Samsung']"));
	  parentElement.findElement(By.xpath("//a[@itemprop='item']"));
	  childElement = parentElement.findElement(By.xpath("//span[@itemprop='name']"));
	  String value = childElement.getText();
	  System.out.println("Arama sonucu: " + value);
	  return value;
  }
  
  //3. siradaki urunu fav. liste ekler.
  public void addToFavourites() throws Exception{
	  //3. siradaki urunu bulmak icin 
	  parentElement = driver.findElement(By.className("columnContent "));
	  childElement = parentElement.findElement(By.xpath("//div[@data-position='31']"));
	  productId=childElement.getAttribute("id");
	  	
	  //Ekranda 3. siradaki urunun favorilere ekle butonunu gormek icin
//	  element = driver.findElement(By.id(productId));
//	  jse.executeScript("arguments[0].scrollIntoView(true);",element);
	  sleep(10000);
	  		  	
	  //3. siradaki urunun favorilere ekle butonunu calistirir
	  productId = productId.substring(2);
	  System.out.println("Urun Numarasi: " + productId);
	  parentElement = driver.findElement(By.xpath("//span[@class='textImg followBtn']"));
	  childElement = parentElement.findElement(By.xpath("//span[@data-productid='" + productId + "']"));
	  childElement.click();
	  sleep(10000);
	  System.out.println("Urun Favorilerime Eklendi.");
  }
  
  //Favorilerimi acar.
  public void clickOnFavourites(){
	  //Ekranin uzerindeki favorilerim linkine tiklar
	  
	  //Ekranin en ustune gider
	  //jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
      //sleep(10000);
      
      //hesabim-favorilerime tiklar
	  parentElement = driver.findElement(By.className("myAccount"));
	  Actions action = new Actions(driver);
      action.moveToElement(parentElement).build().perform();
      parentElement = driver.findElement(By.className("hOpenMenuContent"));
      childElement = parentElement.findElement(By.xpath("//a[@title='Favorilerim']"));
      childElement.click();
	  sleep(10000);
	  System.out.println("Favorilerim sayfasi acildi."); 
  }
  
  //Fav.a eklenen urunun, favorilerim altinda olup olmadigini kontrol eder.
  //Favorilerimde birden fazla urun old. durumlari da kontrol eder.
  public int isProductAddedtoFav(){
	  int result = 0; //check the result
	  
	  driver.get("https://www.n11.com/hesabim/favorilerim");
	  sleep(10000);
	  
	  //Eklenilen tum fav urunlerini alir
	  List<WebElement> linkElements = driver.findElements(By.xpath("//td[@class='productTitle']/p[@class='bold']/a[contains(@href,'http://urun.n11.com')]"));
	  String[] linkTexts = new String[linkElements.size()];
      int i = 0;
      System.out.println("Fav urun adedi = " + linkElements.size() );

      //extract the link texts of each link element
      for (WebElement e : linkElements) {
          linkTexts[i] = e.getAttribute("href");
          //System.out.println(linkTexts[i] );
          i++;
      }

      //test each link, productId is in the fav list?
      for (String t : linkTexts) {
         
          if (t.contains(productId)) {
              System.out.println("Urun fav listte var." );
              result++;
          } else {
              System.out.println("Urun fav listte yok.");
          }
      }
      return result; //0'a esit deilse urun bulunmustur
   }
  
  //Son eklenilen urunu favorilerden cikarir.
  public void removeFavourites(){
	  sleep(5000);
	  driver.findElement(By.xpath("//a[@class='removeSelectedProduct']")).click();
	  sleep(5000);
	  System.out.println("Urun fav listesinden silindi." );
  }
  
  private void sleep(int time){
	  try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }

}
