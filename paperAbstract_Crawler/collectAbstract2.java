
package com.test.paperAbstract;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class collectAbstract2 {

	private WebDriver driver;
	private String address;

	public collectAbstract2() {
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\HoJun\\Desktop\\경희대\\연구실\\내 공부\\Selenium\\geckodriver-v0.16.0-win64\\geckodriver.exe");
		this.driver = new FirefoxDriver();
		this.address = "http://ieeexplore.ieee.org/search/searchresult.jsp?queryText=";
	}

	public String searchAbstract(String title) {
		
		title = title.replaceAll(" ", "%20");
		// And now use this to visit web
		driver.get(address + title);

		try {
			// Google's search is rendered dynamically with JavaScript.
			// Wait for the page to load, timeout after 10 seconds
			(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {

					return d.getTitle().toLowerCase().startsWith("cheese!");
				}
			});
		} catch (org.openqa.selenium.TimeoutException e) {
			List<WebElement> element = driver.findElements(By.tagName("a"));
			for (WebElement e2 : element) {

				if (e2.getAttribute("href") != null) {
					if (e2.getAttribute("href").indexOf("document/") != -1) {
						System.out.println(e2.getAttribute("href"));
						String add = e2.getAttribute("href");
						driver.get(add);
						String paperAbstract = getDescription(driver);
						return paperAbstract;
					}
				}

			}
			// return 논문 정보 없음

		}

		// Close the browser
		//driver.quit();

		return new String();
	}

	public String getDescription(WebDriver driver) {

		try {
			(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
					return d.getTitle().toLowerCase().startsWith("cheese!");
				}
			});
		} catch (org.openqa.selenium.TimeoutException error) {
			System.out.println("==============================================================");
			List<WebElement> element = driver.findElements(By.cssSelector("div[class='abstract-text ng-binding']"));
			for (WebElement e : element) {
				return e.getText();
			}
		}

		return null;

	}
}
