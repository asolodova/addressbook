package qa.tests.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import qa.tests.model.ContactData;
import qa.tests.model.Groups;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Lina on 04.05.2017.
 */
public class ContactLookInfoTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
    Groups groups = app.db().groups();

    app.contact().goHome();
    if (app.contact().all().size() == 0) {
      app.goTo().submitclick();
      ContactData contact = new ContactData()
              .withFirstname("Work").withLastname("Practika").withMiddlename("Task7").withNickname("test7")
              .withAddress("Saint Petersburg").withEmail("gfbgknfjnb").withEmail2("https://dfkdlfj")
              .withEmail3("www@gmai.ovo").withHomePhone("0110101").withMobilePhone("+79548525646")
              .withWorkPhone("5663565").withCompany("wer").inGroup(groups.iterator().next());
      app.contact().create(contact , true);
      app.contact().goHome();
    }
  }

  @Test

  public void testContactLookInfo() {
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
    assertThat(contact.getAllData(), equalTo(mergePhones(contactInfoFromEditForm)));
  }

  private String mergePhones(ContactData contact) {
    return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone())
            .stream().filter((s -> ! s.equals("")))
            .map(ContactLookInfoTest::cleaned)
            .collect(Collectors.joining("\n"));
  }

  private String mergeDataContact(ContactData contact) {
    return Stream.of(contact.getFirstname(), contact.getLastname(), contact.getAddress()
            ,contact.getAllEmails(), contact.getAllPhones()).filter((s) -> !s.equals(""))
            .map(ContactLookInfoTest::cleaned)
            .collect(Collectors.joining("\n"));
  }

  public static String cleaned(String data) {
    return data.replaceAll("\\s", "").replaceAll("[-()]", "");
  }

}
