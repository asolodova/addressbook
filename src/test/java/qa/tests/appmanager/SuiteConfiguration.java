package qa.tests.appmanager;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created by Lina on 01.12.2019.
 */
/**
 * Loads test suite configuration from resource files.
 * https://github.com/barancev/webdriver-testng-archetype/tree/master/src/main/resources/archetype-resources/src/test/java
 */
public class SuiteConfiguration {
  private static final String DEBUG_PROPERTIES = "/debug.properties";
  private Properties properties;
  WebDriver wd;

  private ContactHelper contactHelper;
  private SessionHelper sessionHelper;
  private NavigationHelper navigationHelper;
  private GroupHelper groupHelper;
  private DbHelper dbHelper;

  public SuiteConfiguration() throws IOException {
    this(System.getProperty("application.properties", DEBUG_PROPERTIES));
  }
  public SuiteConfiguration(String fromResource) throws IOException {
    properties = new Properties();
    properties.load(SuiteConfiguration.class.getResourceAsStream(fromResource));
  }
  public Capabilities getCapabilities() throws IOException {
    String capabilitiesFile = properties.getProperty("capabilities");
    Properties capsProps = new Properties();
    capsProps.load(SuiteConfiguration.class.getResourceAsStream(capabilitiesFile));
    DesiredCapabilities capabilities = new DesiredCapabilities();
    for (String name : capsProps.stringPropertyNames()) {
      String value = capsProps.getProperty(name);
      if (value.toLowerCase().equals("true") || value.toLowerCase().equals("false")) {
        capabilities.setCapability(name, Boolean.valueOf(value));
      } else if (value.startsWith("file:")) {
        capabilities.setCapability(name, new File(".", value.substring(5)).getCanonicalFile().getAbsolutePath());
      } else {
        capabilities.setCapability(name, value);
      }
    }
    return capabilities;
  }
  public boolean hasProperty(String name) {
    return properties.containsKey(name);
  }
  public String getProperty(String name) {
    return properties.getProperty(name);
  }

  public void init() throws IOException {

    dbHelper = new DbHelper();

    String target = System.getProperty("target", "application");
    properties.load(new FileReader(new File(String.format("src/main/resources/%s.properties", target))));


    wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    wd.get(properties.getProperty("web.baseUrl"));
    groupHelper = new GroupHelper(wd);
    navigationHelper = new NavigationHelper(wd);
    sessionHelper = new SessionHelper(wd);
    contactHelper = new ContactHelper(wd);
    sessionHelper.login(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPass"));
  }

}