package qa.tests.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import qa.tests.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by Lina on 02.05.2017.
 */
public class ContactInfoTest extends TestBase {


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

  public void testContactPhones() {
    app.contact().lookContact();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact) ;

    assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
    assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
    assertThat(contact.getAddress(), equalTo(mergeAddress(contactInfoFromEditForm)));


   }

  private String mergePhones(ContactData contact) {
     return Stream.of(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone()).filter((s) -> !s.equals(""))
            .map(ContactInfoTest::cleaned)
            .collect(Collectors.joining("\n"));

  }

  private String mergeEmails(ContactData contact) {
    return Stream.of( contact.getEmail(), contact.getEmail2(), contact.getEmail3())
            .filter((s) -> !s.equals(""))
            .collect(Collectors.joining("\n"));

  }

  private String mergeAddress(ContactData contact) {
    return Stream.of(contact.getAddress())
            .filter((s) -> !s.equals(""))
            .collect(Collectors.joining("\n"));

  }

  public static String cleaned(String phone) {
    return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
  }
}
