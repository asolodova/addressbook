package qa.tests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import qa.tests.model.ContactData;
import qa.tests.model.Contacts;

import java.util.List;

/**
 * Created by Lina on 26.03.2017.
 */
public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void submitNewContact() {
    click(By.cssSelector("input[name='submit']"));
  }

  public void fildContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("middlename"), contactData.getMiddlename());
    type(By.name("nickname"), contactData.getNickname());
    type(By.name("address"), contactData.getAddress());
    type(By.name("email"), contactData.getEmail());
    type(By.name("email2"), contactData.getEmail2());
    type(By.name("email3"), contactData.getEmail3());
    type(By.name("home"), contactData.getHomePhone());
    type(By.name("mobile"), contactData.getMobilePhone());
    type(By.name("work"), contactData.getWorkPhone());
    type(By.name("title"), contactData.getTitle());
    type(By.name("company"), contactData.getCompany());

    if (creation){
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
      }else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void goHome() {
    click(By.xpath("//*[@id='nav']/ul/li[1]/a"));
  }

  public void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }

  public void deleteContact() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void modifyContactInfo(int id) {
    wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
  }

  public void editContactById(int id) {
    wd.findElement(By.xpath("//a[@href='edit.php?id="+id+"']")).click();
  }

  public void lookInfoContact(){
    wd.findElement(By.xpath("//*[@title='Details']")).click();
  }

  public void updateContact() {
    click(By.name("update"));
  }

  public void create(ContactData contact, boolean b) {
    click(By.linkText("add new"));
    fildContactForm(contact, true);
    submitNewContact();
    contactCache = null;
    goHome();
  }

  public void modify(ContactData contact) {
    editContactById(contact.getId());
    fildContactForm(contact, false);
    updateContact();
    contactCache = null;
    goHome();
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteContact();
    alertContact();
    contactCache = null;
    goHome();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public int count() {
    return wd.findElements(By.name("selected[]")).size();
  }

  private Contacts contactCache = null;

  public Contacts all() {

    if (contactCache != null) {
      return new Contacts(contactCache);
    }
    contactCache = new Contacts();
    List<WebElement> elements = wd.findElements(By.name("entry"));
    for (WebElement element : elements) {
      List<WebElement> cells = element.findElements(By.tagName("td"));
      String lastname = cells.get(1).getText();
      String firstname = cells.get(2).getText();
      String address = cells.get(3).getText();
      String allEmails = cells.get(4).getText();
      String allPhones = cells.get(5).getText();

      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      contactCache.add(new ContactData().withId(id).withFirstname(firstname).withLastname(lastname)
              .withAddress(address).withAllEmails(allEmails).withAllPhones(allPhones));
    }
    return contactCache;
  }

  public ContactData infoFromEditForm(ContactData contact) {
  editContactById(contact.getId());
  String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
  String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
  String home = wd.findElement(By.name("home")).getAttribute("value");
  String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
  String work = wd.findElement(By.name("work")).getAttribute("value");
  String address = wd.findElement(By.name("address")).getAttribute("value");
  String email = wd.findElement(By.name("email")).getAttribute("value");
  String email2 = wd.findElement(By.name("email2")).getAttribute("value");
  String email3 = wd.findElement(By.name("email3")).getAttribute("value");
  wd.navigate().back();
  return new ContactData().withId(contact.getId()).withFirstname(firstname)
          .withLastname(lastname).withHomePhone(home)
          .withMobilePhone(mobile).withWorkPhone(work).withAddress(address).withEmail(email).withEmail2(email2)
          .withEmail3(email3);
  }

  public Contacts all1() {

    if (contactCache != null) {
      return new Contacts(contactCache);
    }
    contactCache = new Contacts();
    List<WebElement> elements = wd.findElements(By.id("content"));
    for (WebElement element : elements) {
      String allData = element.getText();

      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      contactCache.add(new ContactData().withId(id).withAllData(allData));
    }
    return contactCache;

  }

  public ContactData infoEditForm(ContactData contact) {
    modifyContactInfo(contact.getId());
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getAttribute("value");
    String email = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    String company = wd.findElement(By.name("company")).getAttribute("value");
    wd.navigate().back();
    return new ContactData().withId(contact.getId()).withFirstname(firstname)
            .withLastname(lastname).withHomePhone(home)
            .withMobilePhone(mobile).withWorkPhone(work).withAddress(address).withEmail(email).withEmail2(email2)
            .withEmail3(email3).withCompany(company);
  }

}
