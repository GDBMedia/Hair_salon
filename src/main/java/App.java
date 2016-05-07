import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.ArrayList;

public class App {

  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";


    //READ
    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/new-stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/clients/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("stylists", Stylist.all());
      model.put("template", "templates/new-client.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/clients", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("clients", Client.all());
      model.put("template", "templates/clients.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("stylists" ,Stylist.all());
      model.put("template", "templates/stylists.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:stylist", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String stylist = request.params(":stylist");
      String[] arrayOfName = stylist.split("-");
      model.put("stylist", Stylist.find(arrayOfName[0], arrayOfName[1]));
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //READ


    //CREATE
    post("/stylists", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      String firstName = request.queryParams("firstName");
      String lastName = request.queryParams("lastName");
      String specialty = request.queryParams("specialty");
      String gender = request.queryParams("gender");
      String age = request.queryParams("age");

      Stylist newStylist = new Stylist(firstName, lastName, specialty, gender, age);
      newStylist.save();
      model.put("stylists", Stylist.all());
      model.put("template", "templates/stylists.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/clients", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      String firstName = request.queryParams("firstName");
      String lastName = request.queryParams("lastName");
      String stylist = request.queryParams("stylist");
      String gender = request.queryParams("gender");
      String age = request.queryParams("age");

      Client newClient = new Client(firstName, lastName, stylist, gender, age);
      newClient.save();
      model.put("clients", Client.all());
      model.put("template", "templates/clients.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //CREATE

    //DELETE
    post("/delete/:client", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      String client = request.params(":client");
      String[] arrayOfName = client.split("-");

      Client newClient = Client.find(arrayOfName[0], arrayOfName[1]);
      newClient.delete();
      model.put("template", "templates/client-deleted.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/fire/:stylist", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      String stylist = request.params(":stylist");
      String[] arrayOfName = stylist.split("-");

      Stylist newStylist = Stylist.find(arrayOfName[0], arrayOfName[1]);
      newStylist.delete();
      newStylist.deleteClients();
      model.put("template", "templates/stylist-deleted.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //DELETE


  }
}
