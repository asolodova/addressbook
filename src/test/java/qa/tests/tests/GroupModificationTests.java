package qa.tests.tests;

import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import qa.tests.model.GroupData;
import qa.tests.model.Groups;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Created by Lina on 26.03.2017.
 */
public class GroupModificationTests extends TestBase {

  @BeforeMethod

  public void ensurePreconditions(){
    if(app.db().groups().size() ==0){
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("Test2"));
    }
  }

  @Test

  public void testGroupModification(){
    Groups before = app.db().groups();
    GroupData modifiedGroup = before.iterator().next();
    GroupData group = new GroupData()
            .withId(modifiedGroup.getId()).withName("Update1").withHeader(null).withFooter("3");
    app.goTo().groupPage();
    app.group().modify(group);
    Assert.assertEquals(app.group().count(), before.size());
    Groups after = app.db().groups();
    MatcherAssert.assertThat(after, equalTo(before.without(modifiedGroup).withAdded(group)));
    verifyGroupListInUI();
  }
}
