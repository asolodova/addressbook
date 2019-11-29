package qa.tests.tests;

import org.testng.annotations.Test;
import qa.tests.model.GroupData;
import qa.tests.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTest extends TestBase {


  @Test
  public void testGroupCreation() {

    app.goTo().groupPage();
    Groups before = app.group().all();
    GroupData group = new GroupData().withName("Test2");
    app.group().create(group);
    assertThat(app.group().count(), equalTo(before.size() + 1));
    Groups after = app.group().all();
    assertThat(after, equalTo(before.withAdded(group.withId(
            after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }

  @Test
  public void testadGroupCreation() {

    // Проверка на невозможность создания группы с именем содержащм апостроф
    app.goTo().groupPage();
    Groups before = app.group().all();
    GroupData group = new GroupData().withName("Test2'");
    app.group().create(group);
    assertThat(app.group().count(), equalTo(before.size()));
    Groups after = app.group().all();
    assertThat(after, equalTo(before));
  }

}
