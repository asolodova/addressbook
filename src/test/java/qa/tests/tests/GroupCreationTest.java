package qa.tests.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import qa.tests.model.GroupData;
import qa.tests.model.Groups;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTest extends TestBase {


  @DataProvider
  public Iterator <Object[]> validGroups(){
    List<Object[]> list = new ArrayList<Object[]>();
    list.add(new Object[]{new GroupData().withName("test1").withHeader("header 01").withFooter("footer 01")});
    list.add(new Object[]{new GroupData().withName("test2").withHeader("header 02").withFooter("footer 02")});
    list.add(new Object[]{new GroupData().withName("test3").withHeader("header 03").withFooter("footer 03")});
    return list.iterator();
  }
  @Test(dataProvider = "validGroups")
  public void testGroupCreation(GroupData group) {
    app.goTo().groupPage();
    Groups before = app.group().all();
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
