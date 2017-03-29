import org.sql2o.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Restaurant {
  private String name;
  private String address;
  private String phoneNumber;
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

  public String getRestaurantAddress() {
    return address;
  }

  public String getRestaurantPhoneNumber() {
    return phoneNumber;
  }

  public String getRestaurantWebsite() {
    return website;
  }


}
