import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";
    List<String> foodTypes = new ArrayList<>(Arrays.asList("Burgers", "Pasta", "Pizza", "Sushi", "Tacos", "Greasy"));


    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("Restaurants", Restaurant.all());
      model.put("template", "templates/index.vtl");
<<<<<<< HEAD
      model.put("dishes", Dish.all());
      model.put("foodTypes", foodTypes);
      model.put("restaurants", Restaurant.all());
=======
>>>>>>> da4db8d4ff9f78b2b9e8910c3e846fb645e5e125
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      String foodType = request.queryParams("foodType");
      Restaurant newRestaurant = new Restaurant(name, foodType);
      newRestaurant.save();
      model.put("restaurants", Restaurant.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/restaurants/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/restaurants-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    // get("/restaurants/delete", (request, response) -> {
    //   Map<String, Object> model = new HashMap<String, Object>();
    //   model.put("restaurants", Restaurant.all());
    //   model.put("template", "templates/restaurant-delete.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());

    get("/restaurants/:restaurantId", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Restaurant newRestaurant = Restaurant.find(Integer.parseInt(request.params("restaurantId")));
      model.put("Restaurant", newRestaurant);
      model.put("template", "templates/restaurant.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // get("/restaurants/:restaurantsId/delete", (request, response) -> {
    //   Map<String, Object> model = new HashMap<String, Object>();
    //   Restaurant newRestaurant = Restaurant.find(Integer.parseInt(request.params("restaurantId")));
    //   newRestaurant.delete();
    //   model.put("Restaurant", newRestaurant.all());
    //   model.put("template", "templates/index.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());

    // get("/dishUser", (request, response) -> {
    //   Map<String, Object> model = new HashMap<String, Object>();
    //   String name = request.queryParams("name");
    //   String foodType = request.queryParams("foodType");
    //   Dish newDish = new Dish(name, foodType);
    //   model.put("dishes", Dish.all());
    //   model.put("template", "templates/dishUser.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());


    // get("/form-login", (request, response) -> {
    //   Map<String, Object> model = new HashMap<String, Object>();
    //   String type = request.queryParams("type");
    //   Dish newDish = new Dish(name, foodType);
    //   model.put("dishes", Dish.all());
    //   model.put("template", "templates/form-login.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());



  }
}
