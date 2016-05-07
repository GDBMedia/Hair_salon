import java.util.List;
import org.sql2o.*;

public class Stylist {
  private int id;
  private String first_name;
  private String last_name;
  private String specialty;
  private String gender;
  private String name;
  private String age;

  public Stylist(String first_name, String last_name, String specialty, String gender, String age) {
    this.first_name = first_name;
    this.last_name = last_name;
    this.specialty = specialty;
    this.gender = gender;
    this.age = age;
    this.name = first_name + " " + last_name;
  }

  public String getFirstName() {
    return first_name;
  }

  public String getLastName() {
    return last_name;
  }

  public String getName() {
    return name;
  }

  public String getUrlName(){
    return first_name + "-" + last_name;
  }

  public String getSpecialty() {
    return specialty;
  }

  public String getGender() {
    return gender;
  }

  public String getAge() {
    return age;
  }

  public static List<Stylist> all() {
    String sql = "SELECT * FROM stylists ORDER BY last_name ASC";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Stylist.class);
    }
  }

  public Integer countClients() {
    String sql = "SELECT COUNT(stylist) FROM clients WHERE stylist =:stylist";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
      .addParameter("stylist", this.name)
      .executeAndFetchFirst(Integer.class);
    }
  }

  public List<Client> getClients() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM clients where stylist=:stylist";
      return con.createQuery(sql)
      .addParameter("stylist", this.name)
      .executeAndFetch(Client.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO stylists(first_name, last_name, name, specialty, gender, age) VALUES (:first_name, :last_name, :name, :specialty, :gender, :age)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("first_name", this.first_name)
      .addParameter("last_name", this.last_name)
      .addParameter("name", this.name)
      .addParameter("specialty", this.specialty)
      .addParameter("gender", this.gender)
      .addParameter("age", this.age)
      .executeUpdate()
      .getKey();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM stylists WHERE first_name=:first_name AND last_name=:last_name";
      con.createQuery(sql, true)
      .addParameter("first_name", this.first_name)
      .addParameter("last_name", this.last_name)
      .executeUpdate();
    }
  }

  public void deleteClients() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM clients WHERE stylist=:stylist";
      con.createQuery(sql, true)
      .addParameter("stylist", this.name)
      .executeUpdate();
    }
  }

  @Override
     public boolean equals(Object otherStylist) {
       if (!(otherStylist instanceof Stylist)) {
         return false;
       } else {
         Stylist newStylist = (Stylist) otherStylist;
         return this.getSpecialty().equals(newStylist.getSpecialty());
       }
     }

  public static Stylist find(String first, String last) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM stylists WHERE first_name=:first AND last_name=:last";
      return con.createQuery(sql)
      .addParameter("first", first)
      .addParameter("last", last)
      .executeAndFetchFirst(Stylist.class);
    }
  }


}
