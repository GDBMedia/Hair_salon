import java.util.List;
import org.sql2o.*;
import java.text.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client {
  private int id;
  private String first_name;
  private String last_name;
  private String stylist;
  private String gender;
  private String age;

  public Client(String first_name, String last_name, String stylist, String gender, String age) {
    this.first_name = first_name;
    this.last_name = last_name;
    this.stylist = stylist;
    this.gender = gender;
    this.age = age;

  }

  public String getFirstName() {
    return first_name;
  }

  public String getLastName() {
    return last_name;
  }

  public String getUrlName() {
    return first_name + "-" + last_name;
  }

  public String getStylist() {
    return stylist;
  }

  public String getGender() {
    return gender;
  }

  public String getAge() {
    return age;
  }

  public static List<Client> all() {
    String sql = "SELECT * FROM clients ORDER BY last_name ASC";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Client.class);
    }
  }


  @Override
     public boolean equals(Object otherClient) {
       if (!(otherClient instanceof Client)) {
         return false;
       } else {
         Client newClient = (Client) otherClient;
         return this.getStylist().equals(newClient.getStylist());
       }
     }


  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO clients(first_name, last_name, stylist, gender, age) VALUES (:first_name, :last_name, :stylist, :gender, :age)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("first_name", this.first_name)
      .addParameter("last_name", this.last_name)
      .addParameter("stylist", this.stylist)
      .addParameter("gender", this.gender)
      .addParameter("age", this.age)
      .executeUpdate()
      .getKey();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM clients WHERE first_name=:first_name AND last_name=:last_name";
      con.createQuery(sql, true)
      .addParameter("first_name", this.first_name)
      .addParameter("last_name", this.last_name)
      .executeUpdate();
    }
  }

  public static Client find(String first, String last) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM clients WHERE first_name=:first AND last_name=:last";
      Client patient = con.createQuery(sql)
      .addParameter("first", first)
      .addParameter("last", last)
      .executeAndFetchFirst(Client.class);
      return patient;
    }
  }
}
