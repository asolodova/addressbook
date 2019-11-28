package qa.tests.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import qa.tests.model.ContactData;

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
    app.contact().lookContact();
    if (app.contact().all().size() == 0) {
      app.goTo().submitclick();
      app.contact().create(new ContactData()
              .withFirstname("Work").withLastname("Practika").withMiddlename("Task7").withNickname("test7")
              .withAddress("Saint Petersburg").withEmail("gfbgknfjnb").withEmail2("https://dfkdlfj")
              .withEmail3("www@gmai.ovo").withHomePhone("0110101").withMobilePhone("+79548525646")
              .withWorkPhone("5663565").withCompany("wer").withGroup("Test2"), true);
    }
  }

  @Test

  public void testContactLookInfo() {
    app.contact().lookContact();
    app.contact().lookInfoContact(0);
    ContactData contact = app.contact().all1().iterator().next();
    ContactData contactLookInfo = app.contact().infoEditForm(contact);

   // ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact) ;

    assertThat(contact.getAllData(), equalTo(mergeDataContact(contactLookInfo)));



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
