import org.sql2o.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Restaurant {
  private String name;
  private String address;
  private int phoneNumber;
  private String website;
  private Double[] rating;
  private String foodType;
  private int id;

  public Restaurant(String name, String foodType) {
    this.name = name;
    this.foodType = foodType;
  }

  public String getRestaurantName() {
    return name;
  }

  public String getRestaurantFoodType() {
    return foodType;
  }

  public String getRestaurantAddress() {
    return address;
  }

  public int getRestaurantPhoneNumber() {
    return phoneNumber;
  }

  public String getRestaurantWebsite() {
    return website;
  }

  public int getRestaurantId() {
    return id;
  }

  public List<Dish> getDishes() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM dishes where restaurant_id=:id";
      return con.createQuery(sql)
      .addParameter("id", this.id)
      .addColumnMapping("restaurant_id", "restaurantId")
      .addColumnMapping("food_type", "foodType")
      .addColumnMapping("phone_number", "phoneNumber")
      .executeAndFetch(Dish.class);
    }
  }

  public List<Review> getRestaurantReviews() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM reviews WHERE dish_id IN (SELECT id FROM dishes WHERE restaurant_id = :restaurantId)";
      return con.createQuery(sql)
      .addColumnMapping("reviewer_name", "reviewerName")
        .addColumnMapping("review_date", "reviewDate")
        .addColumnMapping("dish_id", "dishId")
      .addParameter("restaurantId", this.id)
      .executeAndFetch(Review.class);
    }
  }

  public double getAverageRestaurantRating() {
    int totalScore = 0;
    for (Review review : getRestaurantReviews()) {
      totalScore += review.getRating();
    }
    totalScore = totalScore/getRestaurantReviews().size();
    return (double) totalScore;
  }

  public List<Review> getBestReviews() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM reviews WHERE dish_id IN (SELECT id FROM dishes WHERE restaurant_id = :restaurantId) ORDER BY rating desc";
      return con.createQuery(sql)
      .addColumnMapping("dish_id", "dishId")
      .addColumnMapping("reviewer_name", "reviewerName")
      .addColumnMapping("review_date", "reviewDate")
      .addParameter("restaurantId", this.id)
      .executeAndFetch(Review.class);
    }
  }

  public List<Review> getWorstReviews() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM reviews WHERE dish_id IN (SELECT id FROM dishes WHERE restaurant_id = :restaurantId) ORDER BY rating asc";
      return con.createQuery(sql)
      .addColumnMapping("dish_id", "dishId")
      .addColumnMapping("reviewer_name", "reviewerName")
      .addColumnMapping("review_date", "reviewDate")
      .addParameter("restaurantId", this.id)
      .executeAndFetch(Review.class);
    }
  }

  public static List<Restaurant> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM restaurants";
      return con.createQuery(sql)
      .addColumnMapping("food_type", "foodType")
      .addColumnMapping("phone_number", "phoneNumber")
      .executeAndFetch(Restaurant.class);
    }
  }


  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO restaurants (name, food_type, address, phone_number, website, rating) VALUES (:name, :foodType, :address, :phoneNum, :website, :rating)";
      this.id = (int) con.createQuery(sql, true)
      .addColumnMapping("food_type", "foodType")
      .addColumnMapping("phone_number", "phoneNumber")
      .addParameter("name", this.name)
      .addParameter("foodType", this.foodType)
      .addParameter("address", this.address)
      .addParameter("phoneNum", this.phoneNumber)
      .addParameter("website", this.website)
      .addParameter("rating", this.rating)
      .executeUpdate()
      .getKey();
    }
  }

public static Restaurant find(int id) {
try(Connection con = DB.sql2o.open()) {
  String sql = "SELECT * FROM Restaurants WHERE id = :id";
  Restaurant patient = con.createQuery(sql)
    .addParameter("id", id)
    .addColumnMapping("restaurant_id", "restaurantId")
    .addColumnMapping("food_type", "foodType")
    .addColumnMapping("phone_number", "phoneNumber")
    .executeAndFetchFirst(Restaurant.class);
  return patient;
  }
}



  @Override
  public boolean equals(Object otherRestaurant){
    if (!(otherRestaurant instanceof Restaurant)){
      return false;
    } else {
      Restaurant newRestaurant = (Restaurant) otherRestaurant;
      return this.getRestaurantName().equals(newRestaurant.getRestaurantName()) && this.getRestaurantId() == newRestaurant.getRestaurantId();
    }
  }
}
