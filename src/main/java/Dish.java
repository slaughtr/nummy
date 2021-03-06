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

  public List<Review> getDishReviews() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM reviews WHERE dish_id = :dishId";
      return con.createQuery(sql)
      .addColumnMapping("reviewer_name", "reviewerName")
      .addColumnMapping("review_date", "reviewDate")
      .addColumnMapping("dish_id", "dishId")
      .addParameter("dishId", this.id)
      .executeAndFetch(Review.class);
    }
  }

  public double getAverageDishRating() {
    double totalAboveSixtyFive = 0;
    double totalScore = 0;
    for (Review review : getDishReviews()) {
      if (review.getRating() > 65) {
        totalAboveSixtyFive++;
      }
    }
    totalScore = ((getDishReviews().size() + totalAboveSixtyFive) /2)*10;
    return totalScore;
  }

  public List<Review> getBestReviews() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM reviews WHERE dish_id = :dishId ORDER BY rating desc";
      return con.createQuery(sql)
      .addColumnMapping("dish_id", "dishId")
      .addColumnMapping("reviewer_name", "reviewerName")
      .addColumnMapping("review_date", "reviewDate")
      .addParameter("dishId", this.id)
      .executeAndFetch(Review.class);
    }
  }

  public List<Review> getWorstReviews() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM reviews WHERE dish_id = :dishId ORDER BY rating asc";
      return con.createQuery(sql)
      .addColumnMapping("dish_id", "dishId")
      .addColumnMapping("reviewer_name", "reviewerName")
      .addColumnMapping("review_date", "reviewDate")
      .addParameter("dishId", this.id)
      .executeAndFetch(Review.class);
    }
  }

  public static List<Dish> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM dishes";
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
      return this.getDishName().equals(newDish.getDishName()) && this.getDishId() == newDish.getDishId() && this.getDishReviews().equals(newDish.getDishReviews()) && this.getBestReviews().equals(newDish.getBestReviews());
    }
  }

}
