package qa.tests.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import qa.tests.model.ContactData;
import qa.tests.model.Contacts;
import qa.tests.model.Groups;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTest extends TestBase {

    @Test
    public void testContactCreation() {
        Contacts before = app.contact().all();
        File photo = new File("src/main/resources/123.png");
        ContactData contact = new ContactData()
                .withFirstname("Task_888").withLastname("878").withMiddlename("Task7").withNickname("test7")
                .withAddress("Saint Petersburg").withCompany("wer").withGroup("Update1").withPhoto(photo);
        app.contact().create(contact , true);
        assertThat(app.contact().count(), equalTo(before.size() + 1));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(before.withAdded(contact.withId(
                after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
    }

    @Test
    public void testCurrent(){
        File currentNew = new File(".");
        System.out.println(currentNew.getAbsolutePath());
        File current = new File("src/main/resources/123.png");
        System.out.println(current.getAbsolutePath());
        System.out.println(current.exists());
        System.out.println(current.isFile());
        System.out.println(current.canRead());
        System.out.println(current.canWrite());
    }
}