package qa.tests.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import qa.tests.appmanager.ApplicationManager;

/**
 * Created by Lina on 25.03.2017.
 */
public class TestBase {


  protected static final ApplicationManager app = new ApplicationManager(BrowserType.FIREFOX);

  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
  }

  @AfterSuite
  public void tearDown() {
    app.stop();
  }

}
