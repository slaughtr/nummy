import org.sql2o.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Review {
  private int id;
  private int rating;
  private String reviewerName;
  private String reviewDate;
  private int dishId;

  public Review(int rating, String reviewerName, String reviewDate, int dishId) {
    this.rating = rating;
    this.reviewerName = reviewerName;
    this.reviewDate = reviewDate;
    this.dishId = dishId;
  }

  public int getRating() {
    return rating;
  }

  public String getReviewerName() {
    return reviewerName;
  }

  public String reviewDate() {
    return reviewDate;
  }

  public int getReviewId() {
    return id;
  }

  public int getDishId() {
    return dishId;
  }


  public static List<Review> all() {
    String sql = "SELECT * FROM reviews";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
      .addColumnMapping("reviewer_name", "reviewerName")
      .addColumnMapping("review_date", "reviewDate")

      .addColumnMapping("dish_id", "dishId")
      .executeAndFetch(Review.class);
    }
  }
  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO reviews (rating, reviewer_name, review_date, dish_id) VALUES (:rating, :reviewerName, CAST(:review_date AS DATE), :dishId)";
      this.id = (int) con.createQuery(sql, true)
      .addColumnMapping("reviewer_name", "reviewerName")
        .addColumnMapping("review_date", "reviewDate")
        .addColumnMapping("dish_id", "dishId")
        .addParameter("rating", this.rating)
        .addParameter("reviewerName", this.reviewerName)
        .addParameter("review_date", this.reviewDate)
        .addParameter("dishId", this.dishId)
        .executeUpdate()
        .getKey();
    }
  }

  public static Review find(int id) {
  try(Connection con = DB.sql2o.open()) {
    String sql = "SELECT * FROM reviews WHERE id = :id";
    Review review = con.createQuery(sql)
      .addParameter("id", id)
      .addColumnMapping("dish_id", "dishId")
      .addColumnMapping("reviewer_name", "reviewerName")
      .addColumnMapping("review_date", "reviewDate")
      .executeAndFetchFirst(Review.class);
    return review;
  }
}

public static int findDishId(int dishId) {
try(Connection con = DB.sql2o.open()) {
  String sql = "SELECT * FROM reviews WHERE dish_id = :dishId";
  Review review = con.createQuery(sql)
    .addParameter("dishId", dishId)
    .addColumnMapping("dish_id", "dishId")
    .addColumnMapping("reviewer_name", "reviewerName")
    .addColumnMapping("review_date", "reviewDate")
    .executeAndFetchFirst(Review.class);
  return review.getDishId();
}
}


  @Override
  public boolean equals(Object otherReview){
    if (!(otherReview instanceof Review)){
      return false;
    } else {
      Review newReview = (Review) otherReview;
      return this.getReviewerName().equals(newReview.getReviewerName()) && this.getReviewId() == newReview.getReviewId() && this.getRating() == newReview.getRating();
    }
  }

}
