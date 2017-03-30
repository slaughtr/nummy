import org.sql2o.*;
import org.junit.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;

public class RestaurantTest {

  @Rule
   public DatabaseRule database = new DatabaseRule();

  @Test
  public void restaurant_InstantiatesCorrectly_True() {
    Restaurant testRestaurant = new Restaurant("Eatery", "American");
    assertTrue(testRestaurant instanceof Restaurant);
  }

  @Test
  public void restaurant_InstantiatesWithName_True() {
    Restaurant testRestaurant = new Restaurant("Eatery", "American");
    assertTrue(testRestaurant.getRestaurantName().equals("Eatery"));
  }

  @Test
  public void restaurant_InstantiatesWithFoodType_True() {
    Restaurant testRestaurant = new Restaurant("Eatery", "American");
    assertTrue(testRestaurant.getRestaurantFoodType().equals("American"));
  }

  @Test
public void restaurant_instantiatesWithId_true() {
  Restaurant testRestaurant = new Restaurant("Eatery", "American");
  testRestaurant.save();
  assertTrue(testRestaurant.getRestaurantId() > 0);
}

  @Test
  public void restaurant_SavesInDatabase_true() {
    Restaurant testRestaurant = new Restaurant("Eatery", "American");
    testRestaurant.save();
    assertTrue(Restaurant.all().get(0).equals(testRestaurant));
  }

  @Test
  public void all_ReturnsAllInstancesOfRestaurant_true() {
    Restaurant testRestaurant1 = new Restaurant("Eatery", "American");
    testRestaurant1.save();
    Restaurant testRestaurant2 = new Restaurant("Gastropub", "Smoozy");
    testRestaurant2.save();
    assertTrue(Restaurant.all().get(0).equals(testRestaurant1));
    assertTrue(Restaurant.all().get(1).equals(testRestaurant2));
  }

  @Test
  public void save_savesRestaurantIdIntoDB_true() {
    Restaurant myRestaurant = new Restaurant("Household", "Chores");
    myRestaurant.save();
    Dish myDish = new Dish("Jojos", "Fried", myRestaurant.getRestaurantId());
    myDish.save();
    Dish savedDish = Dish.find(myDish.getRestaurantId());
    assertEquals(myDish.getRestaurantId(), myRestaurant.getRestaurantId());
  }

  @Test
  public void getRestaurantReviews_returnsProperReviews_true() {
    Restaurant myRestaurant = new Restaurant("Household", "Chores");
    myRestaurant.save();
    Dish myDish = new Dish("Jojos", "Fried", myRestaurant.getRestaurantId());
    myDish.save();
    Review myReview = new Review(90, "Joe", "2017-01-02", myDish.getDishId());
    myReview.save();
    assertEquals(90, myRestaurant.getRestaurantReviews().get(0).getRating());
  }

  @Test
  public void getAverageRestaurantRating_returnsAverageProperly_true() {
    Restaurant myRestaurant = new Restaurant("Household", "Chores");
    myRestaurant.save();
    Dish myDish = new Dish("Jojos", "Fried", myRestaurant.getRestaurantId());
    myDish.save();
    Review myReview = new Review(100, "Joe", "2017-01-02", myDish.getDishId());
    myReview.save();
    Review myReview2 = new Review(10, "Joe", "2017-01-02", myDish.getDishId());
    myReview2.save();
    Review myReview3 = new Review(50, "Joe", "2017-01-02", myDish.getDishId());
    myReview3.save();
    Review myReview4 = new Review(40, "Joe", "2017-01-02", myDish.getDishId());
    myReview4.save();
    assertEquals(50, myRestaurant.getAverageRestaurantRating(), 0.01);
  }
  @Test
  public void getBestReviews_returnsReviewsInDescendingOrder_true() {
    Restaurant myRestaurant = new Restaurant("The Jojo Place", "Fried");
    myRestaurant.save();
    Dish myDish = new Dish("Jojos", "Fried", myRestaurant.getRestaurantId());
    myDish.save();
    Review myReview = new Review(100, "Joe", "2017-01-02", myDish.getDishId());
    myReview.save();
    Review myReview2 = new Review(90, "Joe", "2017-01-02", myDish.getDishId());
    myReview2.save();
    Review myReview3 = new Review(50, "Joe", "2017-01-02", myDish.getDishId());
    myReview3.save();
    Review myReview4 = new Review(80, "Joe", "2017-01-02", myDish.getDishId());
    myReview4.save();
    assertEquals(myRestaurant.getBestReviews().get(0), myReview);
    assertEquals(myRestaurant.getBestReviews().get(1), myReview2);
    assertEquals(myRestaurant.getBestReviews().get(2), myReview4);
    assertEquals(myRestaurant.getBestReviews().get(3), myReview3);
  }

  @Test
  public void getBestReviews_returnsReviewsInAscendingOrder_true() {
    Restaurant myRestaurant = new Restaurant("The Jojo Place", "Fried");
    myRestaurant.save();
    Dish myDish = new Dish("Jojos", "Fried", myRestaurant.getRestaurantId());
    myDish.save();
    Review myReview = new Review(100, "Joe", "2017-01-02", myDish.getDishId());
    myReview.save();
    Review myReview2 = new Review(10, "Joe", "2017-01-02", myDish.getDishId());
    myReview2.save();
    Review myReview3 = new Review(50, "Joe", "2017-01-02", myDish.getDishId());
    myReview3.save();
    Review myReview4 = new Review(40, "Joe", "2017-01-02", myDish.getDishId());
    myReview4.save();
    System.out.println(myRestaurant.getWorstReviews().get(0).getRating());
    assertEquals(myRestaurant.getWorstReviews().get(0), myReview2);
    assertEquals(myRestaurant.getWorstReviews().get(1), myReview4);
    assertEquals(myRestaurant.getWorstReviews().get(2), myReview3);
    assertEquals(myRestaurant.getWorstReviews().get(3), myReview);
  }

}
