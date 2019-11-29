package qa.tests.tests;

import org.testng.annotations.Test;
import qa.tests.model.ContactData;
import qa.tests.model.Contacts;
import qa.tests.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTest extends TestBase {

    @Test
    public void testContactCreation() {

        Contacts before = app.contact().all();
        ContactData contact = new ContactData()
                .withFirstname("Task_888").withLastname("878").withMiddlename("Task7").withNickname("test7")
                .withAddress("Saint Petersburg").withCompany("wer").withGroup("Update1");
        app.contact().create(contact , true);
        assertThat(app.contact().count(), equalTo(before.size() + 1));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(before.withAdded(contact.withId(
                after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
    }
}