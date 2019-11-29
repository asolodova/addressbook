package qa.tests.tests;

import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import qa.tests.model.ContactData;
import qa.tests.model.Contacts;
import qa.tests.model.GroupData;
import qa.tests.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Created by Lina on 27.03.2017.
 */
public class ContactModificationTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
    app.contact().goHome();
    if (app.contact().all().size() == 0) {
      app.goTo().submitclick();
      app.contact().create(new ContactData()
              .withFirstname("Task_7").withLastname("Task_7").withMiddlename("Task7").withNickname("test7")
              .withAddress("Saint Petersburg").withCompany("wer").withGroup("test2"), true);
    }
  }

  @Test

  public void testContactModification(){
    Contacts before = app.contact().all();
    ContactData modifedContact = before.iterator().next();
    ContactData contact = new ContactData().withId(modifedContact.getId())
            .withFirstname("Test8").withLastname("Test8").withAddress("888");
    app.contact().modify(contact);
    Assert.assertEquals(app.contact().count(), before.size());
    Contacts after = app.contact().all();
    MatcherAssert.assertThat(after, equalTo(before.without(modifedContact).withAdded(contact)));
  }

}
