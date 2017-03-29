import org.sql2o.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.LocalDate;

public class Review {
  private int id;
  private int rating;
  private String reviewerName;
  private LocalDate reviewDate;
  private int dishId;

  public Review(int rating, String reviwerName, LocalDate reviewDate, int dishId) {
    this.rating = rating;
    this.reviwerName = reviwerName;
    this.reviewDate = reviewDate;
    this.dishId = dishId;
  }

  public int getRating() {
    return rating;
  }

  public String getReviewerName() {
    return reviewerName;
  }

  public LocalDate reviewDate() {
    return reviewDate;
  }

  public int getDishId() {
    return dishId;
  }


  public static List<Review> all() {
    String sql = "SELECT * FROM reviews";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
      .addColumnMapping("reviewer_name", "reviewerName")
      .addColumnMapping("dish_id", "dishId")
      .executeAndFetch(Review.class);
    }
  }
  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO reviews (rating, reviewer_name, review_date, dish_id) VALUES (:rating, :reviewerName CAST(:review_date AS DATE), :reviewDate, :dishId)";
      // String date = ""
      this.id = (int) con.createQuery(sql, true)
      .addColumnMapping("reviewer_name", "reviewerName")
        .addColumnMapping("review_date", "reviewDate")
        .addColumnMapping("dish_id", "dishId")
        .addParameter("name", this.reviewerName)
        .addParameter("birthdate", this.birthdate)
        .addParameter("dishId", this.dishId)
        .executeUpdate()
        .getKey();
    }
  }

  public static Review find(int id) {
  try(Connection con = DB.sql2o.open()) {
    String sql = "SELECT * FROM reviews WHERE id = :id";
    Review patient = con.createQuery(sql)
      .addParameter("id", id)
      //pulling information from the database, maps info from column called "name" to class variable "heroName"
      .addColumnMapping("dish_id", "dishId")
      .addColumnMapping("reviewer_name", "reviewerName")
      .executeAndFetchFirst(Review.class);
    return patient;
  }
}


  @Override
  public boolean equals(Object otherReview){
    if (!(otherReview instanceof Review)){
      return false;
    } else {
      Review newReview = (Review) otherReview;
      return this.getReviewName().equals(newReview.getReviewName());
      // && this.getReviewId() == newReview.getReviewId();
    }
  }

}
