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

}
