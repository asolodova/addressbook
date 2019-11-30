package qa.tests.tests;

import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import qa.tests.model.GroupData;
import qa.tests.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTestCsv extends TestBase {
  @DataProvider
  public Iterator<Object[]> validGroupsCsv() throws IOException {
// Использование данных из файла с форматом csv
//
    List<Object[]> list = new ArrayList<Object[]>();
    BufferedReader reader = new BufferedReader(new FileReader(new File("src/main/resources/groups.csv")));
    String line = reader.readLine();
    while (line != null){
      String[] split =  line.split(";");
      list.add(new Object[] {new GroupData().withName(split[0]).withHeader(split[1]).withFooter(split[2])});
      line = reader.readLine();
    }
    return list.iterator();

  }
  @Test(dataProvider = "validGroupsCsv")
  public void testGroupCreationCsv(GroupData group) {
    app.goTo().groupPage();
    Groups before = app.group().all();
    app.group().create(group);
    assertThat(app.group().count(), equalTo(before.size() + 1));
    Groups after = app.group().all();
    assertThat(after, equalTo(before.withAdded(group.withId(
            after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }
}
