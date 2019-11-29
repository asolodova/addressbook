package qa.tests.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import qa.tests.model.ContactData;
import qa.tests.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

/**
 * Created by Lina on 27.03.2017.
 */
public class ContactDeleteTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
    app.contact().goHome();
    if (app.contact().all().size() == 0) {
      app.goTo().submitclick();
      app.contact().create(new ContactData()
                      .withFirstname("Task_7").withLastname("Task_7").withMiddlename("Task7").withNickname("test7")
                      .withAddress("Saint Petersburg").withHomePhone("444141").withMobilePhone("+79548525646")
                      .withWorkPhone("23332223").withCompany("wer").withGroup("Test2"),true);
    }
  }

  @Test

  public void testContactDelete(){
    Contacts before = app.contact().all();
    ContactData deleteContact = before.iterator().next();
    app.contact().delete(deleteContact);
    assertEquals(app.contact().count(), before.size() - 1);
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before.without(deleteContact)));

  }

}
