import org.sql2o.*;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

public class ClientTest {
  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM clients *;";
      con.createQuery(sql).executeUpdate();
      sql = "DELETE FROM stylists *;";
      con.createQuery(sql).executeUpdate();
    }
  }

  @Test
  public void Client_instantiatesCorrectly_true() {
    Client myClient = new Client("John", "Jacob", "Bernie Mac", "Male", "18");
    assertEquals(true, myClient instanceof Client);
  }

  @Test
  public void getFirstName_Test() {
    Client myClient = new Client("John", "Jacob", "Bernie Mac", "Male", "18");
    assertEquals(myClient.getFirstName(), "John");
  }

  @Test
  public void getLastName_Test() {
    Client myClient = new Client("John", "Jacob", "Bernie Mac", "Male", "18");
    assertEquals(myClient.getLastName(), "Jacob");
  }

  @Test
  public void getUrlName_Test() {
    Client myClient = new Client("John", "Jacob", "Bernie Mac", "Male", "18");
    assertEquals(myClient.getUrlName(), "John-Jacob");
  }

  @Test
  public void getStylist_Test() {
    Client myClient = new Client("John", "Jacob", "Bernie Mac", "Male", "18");
    assertEquals(myClient.getStylist(), "Bernie Mac");
  }

  @Test
  public void getGender_Test() {
    Client myClient = new Client("John", "Jacob", "Bernie Mac", "Male", "18");
    assertEquals(myClient.getGender(), "Male");
  }

  @Test
  public void getAge_Test() {
    Client myClient = new Client("John", "Jacob", "Bernie Mac", "Male", "18");
    assertEquals(myClient.getAge(), "18");
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Client.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAretheSame() {
    Client myClient1 = new Client("John", "Jacob", "Bernie Mac", "Male", "18");
    Client myClient2 = new Client("John", "Jacob", "Bernie Mac", "Male", "18");
    assertTrue(myClient1.equals(myClient2));
  }

  @Test
  public void save_Test() {
    Client myClient = new Client("John", "Jacob", "Bernie Mac", "Male", "18");
    myClient.save();
    assertTrue(Client.all().get(0).equals(myClient));
  }

  @Test
  public void delete_Test() {
    Client myClient = new Client("John", "Jacob", "Bernie Mac", "Male", "18");
    myClient.save();
    myClient.delete();
    assertEquals(Client.all().size(), 0);
  }

  @Test
  public void find_findsClientInDatabase_true() {
    Client myClient = new Client("John", "Jacob", "Bernie Mac", "Male", "18");
    myClient.save();
    Client savedClient = Client.find(myClient.getFirstName(), myClient.getLastName());
    assertTrue(myClient.equals(savedClient));
  }
}
