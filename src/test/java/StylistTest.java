import org.sql2o.*;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

public class StylistTest {
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
  public void Stylist_instantiatesCorrectly_true() {
    Stylist myStylist = new Stylist("John", "Jacob", "Men's Hair", "Male", "27");
    assertEquals(true, myStylist instanceof Stylist);
  }

  @Test
  public void getFirstName_restInstantiatesWithName_String() {
    Stylist myStylist = new Stylist("John", "Jacob", "Men's Hair", "Male", "27");
    assertEquals("John", myStylist.getFirstName());
  }

  @Test
  public void getLastName_restInstantiatesWithName_String() {
    Stylist myStylist = new Stylist("John", "Jacob", "Men's Hair", "Male", "27");
    assertEquals("Jacob", myStylist.getLastName());
  }

  @Test
  public void getName_restInstantiatesWithName_String() {
    Stylist myStylist = new Stylist("John", "Jacob", "Men's Hair", "Male", "27");
    assertEquals("John Jacob", myStylist.getName());
  }

  @Test
  public void getNameUrl_restInstantiatesWithName_String() {
    Stylist myStylist = new Stylist("John", "Jacob", "Men's Hair", "Male", "27");
    assertEquals("John-Jacob", myStylist.getUrlName());
  }

  @Test
  public void getSpecialty_restInstantiatesWithCuisine_String() {
    Stylist myStylist = new Stylist("John", "Jacob", "Men's Hair", "Male", "27");
    assertEquals("Men's Hair", myStylist.getSpecialty());
  }

  @Test
  public void getGender_restInstantiatesWithDescription_String() {
    Stylist myStylist = new Stylist("John", "Jacob", "Men's Hair", "Male", "27");
    assertEquals("Male", myStylist.getGender());
  }

  @Test
  public void getAge_restInstantiatesWithDish_String() {
    Stylist myStylist = new Stylist("John", "Jacob", "Men's Hair", "Male", "27");
    assertEquals("27", myStylist.getAge());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Stylist.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAretheSame() {
    Stylist firstStylist = new Stylist("John", "Jacob", "Men's Hair", "Male", "27");
    Stylist secondStylist = new Stylist("John", "Jacob", "Men's Hair", "Male", "27");
    assertTrue(firstStylist.equals(secondStylist));
  }

  @Test
  public void save_returnsTrueIfDescriptionsAretheSame() {
    Stylist myStylist = new Stylist("John", "Jacob", "Men's Hair", "Male", "27");
    myStylist.save();
    assertTrue(Stylist.all().get(0).equals(myStylist));
  }

  @Test
  public void find_findsRestuarantInDatabase_true() {
    Stylist myStylist = new Stylist("John", "Jacob", "Men's Hair", "Male", "27");
    myStylist.save();
    Stylist savedStylist = Stylist.find(myStylist.getFirstName(), myStylist.getLastName());
    assertTrue(myStylist.equals(savedStylist));
  }

  @Test
  public void countClients_test() {
    Stylist myStylist = new Stylist("John", "Jacob", "Men's Hair", "Male", "27");
    myStylist.save();
    Client myClient = new Client("John", "Jacob", "John Jacob", "Male", "18");
    myClient.save();
    assertEquals((int)myStylist.countClients(), 1);
  }

  @Test
  public void delete_Test() {
    Stylist myStylist = new Stylist("John", "Jacob", "Men's Hair", "Male", "27");
    myStylist.save();
    myStylist.delete();
    assertEquals(Stylist.all().size(), 0);
  }

  @Test
  public void deleteClient_Test() {
    Stylist myStylist = new Stylist("John", "Jacob", "Men's Hair", "Male", "27");
    myStylist.save();
    Client myClient = new Client("John", "Jacob", "John Jacob", "Male", "18");
    myClient.save();
    myStylist.deleteClients();
    assertEquals(Client.all().size(), 0);
  }

  @Test
  public void getClients_test() {
    Stylist myStylist = new Stylist("John", "Jacob", "Men's Hair", "Male", "27");
    myStylist.save();
    Client myClient = new Client("John", "Jacob", "John Jacob", "Male", "18");
    myClient.save();
    assertEquals(myStylist.getClients().get(0).getStylist(), myStylist.getName());
  }

}
