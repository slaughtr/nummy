import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class DishTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/nummy_test", null, null);
  }

  @After
  public void tearDown() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM dishes *;";
      con.createQuery(sql).executeUpdate();
    }
  }

  @Test
  public void dish_InstantiatesCorrectly_true() {
    Dish testDish = new Dish("Potatoes", "Fried", 1);
    assertTrue(testDish instanceof Dish);
  }

  @Test
  public void dish_InstantiatesWithCorrectName_true() {
    Dish testDish = new Dish("Potatoes", "Fried", 1);
    assertTrue(testDish.getDishName().equals("Potatoes"));
  }

  @Test
  public void dish_InstantiatesWithCorrectFoodType_true() {
    Dish testDish = new Dish("Potatoes", "Fried", 1);
    assertTrue(testDish.getDishFoodType().equals("Fried"));
  }

  @Test
public void dish_instantiatesWithId_true() {
  Dish testDish = new Dish("Spaghetti", "Italian", 1);
  testDish.save();
  assertTrue(testDish.getDishId() > 0);
}

  @Test
  public void dish_SavesInDatabase_true() {
    Dish testDish = new Dish("Spaghetti", "Italian", 1);
    testDish.save();
    assertTrue(Dish.all().get(0).equals(testDish));
  }

  @Test
  public void all_ReturnsAllInstancesOfDish_true() {
    Dish testDish1 = new Dish("Spaghetti", "Italian", 1);
    testDish1.save();
    Dish testDish2 = new Dish("Lasagne", "Also italian", 1);
    testDish2.save();
    assertTrue(Dish.all().get(0).equals(testDish1));
    assertTrue(Dish.all().get(1).equals(testDish2));
  }

  @Test
 public void find_returnsDishWithSameId_secondDish() {
   Dish firstDish = new Dish("Spaghetti", "Italian", 1);
   firstDish.save();
   Dish secondDish = new Dish("French fries", "American", 1);
   secondDish.save();
   assertEquals(Dish.find(secondDish.getDishId()), secondDish);
 }
}
