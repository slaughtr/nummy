import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";
    List<String> foodTypes = new ArrayList<>(Arrays.asList("Burgers", "Pasta", "Pizza", "Sushi", "Tacos", "Greasy"));


    Dish firstDish = new Dish("Spaghetti", "Italian", 1);
    firstDish.save();
    Dish secondDish = new Dish("LASAGNE", "Italian", 1);
    secondDish.save();
    Dish thirdDish = new Dish("Rotini", "Italian", 1);
    thirdDish.save();
    Dish fourthDish = new Dish("Fettucininini", "Italian", 1);
    fourthDish.save();

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      model.put("dishes", Dish.all());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/restaurants", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      String foodType = request.queryParams("foodType");
      Restaurant newRestaurant = new Restaurant(name, foodType);
      newRestaurant.save();
      response.redirect("/");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/dishes", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      String foodType = request.queryParams("foodType");
      int restaurantId = Integer.parseInt(request.queryParams("restaurantId"));
      Dish newDish = new Dish(name, foodType, restaurantId);
      newDish.save();
      response.redirect("/");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
