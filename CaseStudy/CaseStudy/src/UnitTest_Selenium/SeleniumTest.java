/*
 * JUNIT CLASS
 * Hatice ISIK OZATA
 * [Case Study]- Keytorc
 * 19.10.2016
 * (Ceren Ozbagci)
 */

/*
 * !!!Teste baslamadan once setUp()'in altindan gecko.driver pathini guncelleyiniz.!!!
 */
package UnitTest_Selenium;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumTest extends CommonFunctions{
	
	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\neco\\Downloads\\Compressed\\geckodriver-v0.11.1-win64\\geckodriver.exe");
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void test() throws Exception  {
		String pageTitle;  //Sayfanin acilip acilmadigini kontrol etmek icin
		/*
		 * 1.  http://www.n11.com <http://www.n11.com/> sitesine gelecek ve 
		 * anasayfanin acildigini onaylayacak
		 */
		//Open the URL in firefox browser
        initBrowser("http://www.n11.com");
	    
	    // Verify the page title to check if the correct page is launched
        pageTitle = driver.getTitle();
        if (!pageTitle.equals("n11.com - Alýþveriþin Uðurlu Adresi"))
        {
        	closeBrowser();
            fail("Ana Sayfa Acilmadi!");
            System.out.println("Ana Sayfa Acilmadi!");
            }
        else
        {
        	assertTrue(true);
            System.out.println("Ana Sayfa Acildi.");
        }
	     
		
        /*
         * 3. Login ekranini acip, bir kullanici ile login olacak
         *  ( daha once siteye uyeligin varsa o olabilir )
         */
        logIn("blues.cec@mynet.com", "a123456");
        
        /*
         * 4. Ekranin ustundeki Search alanina 'samsung' yazip Ara butonuna tiklayacak
         */
        search("samsung");
        
        /*
         * 5. Gelen sayfada samsung icin sonuc bulundugunu onaylayacak
         */
		String searchresult = searchResult();
		if (!searchresult.equals("Samsung"))
	     {
	       fail("Samsung icin sonuc bulunamadi!");
	       System.out.println("Samsung icin sonuc bulunamadi!");
	      }
	       else
	       {
	        assertTrue(true);
	        System.out.println("Samsung icin sonuc bulundu.");
	       }
		
        
        /*
         * 6. Arama sonuclarindan 2. sayfaya tiklayacak ve 
         * acilan sayfada 2. sayfanin su an gosterimde oldugunu onaylayacak
         */
		Thread.sleep(10000);
        goToPage("2");
        pageTitle = driver.getTitle();
        if (pageTitle.contains("Samsung - n11.com - 2"))
         {
          assertTrue(true);
          System.out.println("2. Sayfa Gosterimde");
         }
        else{
        	fail("2. Sayfa Acilmadi!");
            System.out.println("2. Sayfa Acilmadi!");
        }
        
        /*
         * 7. Ustten 3. urunun icindeki 'favorilere ekle' butonuna tiklayacak
         */
        addToFavourites();
        
        /*
         * 8. Ekranin en ustundeki 'favorilerim' linkine tiklayacak
         */
        //clickOnFavourites();
        
        /*
         * 9. Acilan sayfada bir onceki sayfada izlemeye alinmis urunun bulundugunu onaylayacak
         */
        if (isProductAddedtoFav() > 0 )
        {
        	assertTrue(true);
            System.out.println("Eklenilen urun favori listesinde");
        }
        else {
        	fail("Eklenilen urun favori listesinde DEGIL!");
            System.out.println("Eklenilen urun favori listesinde DEGIL!");
        }
        
        /*
         * 10. Favorilere alinan bu urunun yanindaki 'Kaldir' butonuna basarak, favorilerimden cikaracak
         */
        removeFavourites();
        
        /*
         * 11. Sayfada bu urunun artik favorilere alinmadigini onaylayacak.
         */
        if (isProductAddedtoFav() == 0 )
        {
        	assertTrue(true);
            System.out.println("Urun Favori Listesinden Cikarildi");
        }
        else {
        	fail("Urun Favori Listesinden Cikarilmadi!");
            System.out.println("Urun Favori Listesinden Cikarilmadi!");
        }
	}

}
