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


}
