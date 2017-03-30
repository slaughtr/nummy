import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class ReviewTest {

  @Rule
   public DatabaseRule database = new DatabaseRule();

  @Test
  public void review_InstantiatesCorrectly_true() {
    Review testReview = new Review(1, "Joe", "1999-01-01", 1);
    assertTrue(testReview instanceof Review);
  }

  @Test
  public void review_InstantiatesWithCorrectRating_true() {
    Review testReview = new Review(1, "Joe", "1999-01-01", 1);
    assertTrue(testReview.getRating() == 1);
  }

  @Test
  public void review_InstantiatesWithCorrectReviewerName_true() {
    Review testReview = new Review(1, "Joe", "1999-01-01", 1);
    assertTrue(testReview.getReviewerName().equals("Joe"));
  }

  @Test
public void review_instantiatesWithId_true() {
  Review testReview = new Review(1, "Joe", "1999-01-01", 1);
  testReview.save();
  assertTrue(testReview.getReviewId() > 0);
}

  @Test
  public void review_SavesInDatabase_true() {
    Review testReview = new Review(1, "Joe", "1999-01-01", 1);
    testReview.save();
    assertTrue(Review.all().get(0).equals(testReview));
  }

  @Test
  public void all_ReturnsAllInstancesOfReview_true() {
    Review testReview1 = new Review(1, "Joe", "1999-01-01", 1);
    testReview1.save();
    Review testReview2 = new Review(3, "Jose", "1999-01-01", 1);
    testReview2.save();
    assertTrue(Review.all().get(0).equals(testReview1));
    assertTrue(Review.all().get(1).equals(testReview2));
  }

  @Test
 public void find_returnsReviewWithSameId_secondReview() {
   Review firstReview = new Review(1, "Joe", "1999-01-01", 1);
   firstReview.save();
   Review secondReview = new Review(4, "Jose", "1999-01-01", 2);
   secondReview.save();
   assertEquals(Review.find(secondReview.getReviewId()), secondReview);
 }

 @Test
 public void find_returnsReviewForSpecificDish_true() {
     Dish myDish = new Dish("Jojos", "Fried", 1);
     myDish.save();
     int myDishId = myDish.getDishId();
     Review myReview = new Review(90, "Joe", "2017-01-02", myDish.getDishId());
     myReview.save();
     assertEquals(myDishId, Review.findDishId(myDishId));
 }

}
