import org.sql2o.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dish {
  private String name;
  private Double[] rating;
  private String foodType;
  private int id;
  private int restaurantId;

  public Dish(String name, String foodType, int restaurantId) {
    this.name = name;
    this.foodType = foodType;
    this.restaurantId = restaurantId;
  }

  public String getDishName() {
    return name;
  }

  public String getDishFoodType() {
    return foodType;
  }

  public int getDishId() {
    return id;
  }

  public int getRestaurantId() {
  return restaurantId;
}

public static List<Dish> all() {
  String sql = "SELECT * FROM dishes";
  try(Connection con = DB.sql2o.open()) {
    return con.createQuery(sql)
    .addColumnMapping("restaurant_id", "restaurantId")
    .addColumnMapping("food_type", "foodType")
    .executeAndFetch(Dish.class);
  }
}

public void save() {
  try(Connection con = DB.sql2o.open()) {
    String sql = "INSERT INTO dishes (name, rating, food_type, restaurant_id) VALUES (:name, :rating, :foodType, :restaurantId)";
    // String date = ""
    this.id = (int) con.createQuery(sql, true)
    .addColumnMapping("restaurant_id", "restaurantId")
      .addColumnMapping("food_type", "foodType")
      .addParameter("name", this.name)
      .addParameter("rating", this.rating)
      .addParameter("restaurantId", this.restaurantId)
      .addParameter("foodType", this.foodType)
      .executeUpdate()
      .getKey();
  }
}

public static Dish find(int id) {
try(Connection con = DB.sql2o.open()) {
  String sql = "SELECT * FROM dishes WHERE id = :id";
  Dish patient = con.createQuery(sql)
    .addParameter("id", id)
    .addColumnMapping("restaurant_id", "restaurantId")
    .addColumnMapping("food_type", "foodType")
    .executeAndFetchFirst(Dish.class);
  return patient;
}
}


@Override
public boolean equals(Object otherDish){
  if (!(otherDish instanceof Dish)){
    return false;
  } else {
    Dish newDish = (Dish) otherDish;
    return this.getDishName().equals(newDish.getDishName());
    // && this.getDishId() == newDish.getDishId();
  }
}

}
