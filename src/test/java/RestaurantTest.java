import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class RestaurantTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/nummy_test", null, null);
  }

  @After
  public void tearDown() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM restaurants *;";
      con.createQuery(sql).executeUpdate();
    }
  }

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

}
