import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM clients *;";
      con.createQuery(sql).executeUpdate();
      sql = "DELETE FROM stylists *;";
      con.createQuery(sql).executeUpdate();
    }
  }

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Add New Stylist");
    assertThat(pageSource()).contains("Add New Client");
    assertThat(pageSource()).contains("View Stylists");
    assertThat(pageSource()).contains("View Clients");
  }

  @Test
  public void addnewStylistTest() {
    goTo("http://localhost:4567/");
    click("a", withText("Add New Stylist"));
    fill("#firstName").with("John");
    fill("#lastName").with("Jacobs");
    fill("#specialty").with("mens hair");
    click("option", withText("Male"));
    fill("#age").with("20");
    submit(".btn");
    assertThat(pageSource()).contains("mens hair");
    assertThat(pageSource()).contains("Male");
    assertThat(pageSource()).contains("20");
  }

  @Test
  public void checknewClientTest() {
    goTo("http://localhost:4567/");
    click("a", withText("Add New Stylist"));
    fill("#firstName").with("John");
    fill("#lastName").with("Jacobs");
    fill("#specialty").with("mens hair");
    click("option", withText("Male"));
    fill("#age").with("20");
    submit(".btn");
    click("a", withText("Home Page"));
    click("a", withText("Add New Client"));
    fill("#firstName").with("Garrett");
    fill("#lastName").with("Biernat");
    click("option", withText("Jacobs, John (mens hair), Male"));
    click("option", withText("Male"));
    fill("#age").with("20");
    submit(".btn");
    click("a", withText("Home Page"));
    click("a", withText("View Clients"));
    assertThat(pageSource()).contains("Biernat, Garrett");
    assertThat(pageSource()).contains("Male");
    assertThat(pageSource()).contains("20");
  }

  @Test
  public void viewnewStylistTest() {
    goTo("http://localhost:4567/");
    click("a", withText("Add New Stylist"));
    fill("#firstName").with("John");
    fill("#lastName").with("Jacobs");
    fill("#specialty").with("mens hair");
    click("option", withText("Male"));
    fill("#age").with("20");
    submit(".btn");
    click("a", withText("Home Page"));
    click("a", withText("View Stylists"));
    assertThat(pageSource()).contains("Jacobs, John");
    assertThat(pageSource()).contains("20");
  }

  @Test
  public void addnewClientTest() {
    goTo("http://localhost:4567/");
    click("a", withText("Add New Stylist"));
    fill("#firstName").with("John");
    fill("#lastName").with("Jacobs");
    fill("#specialty").with("mens hair");
    click("option", withText("Male"));
    fill("#age").with("20");
    submit(".btn");
    click("a", withText("Home Page"));
    click("a", withText("Add New Client"));
    fill("#firstName").with("Garrett");
    fill("#lastName").with("Biernat");
    click("option", withText("Jacobs, John (mens hair), Male"));
    click("option", withText("Male"));
    fill("#age").with("20");
    submit(".btn");
    assertThat(pageSource()).contains("Biernat, Garrett");
    assertThat(pageSource()).contains("Male");
    assertThat(pageSource()).contains("20");
  }

}
