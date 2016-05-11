import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Ingredient {
  private int id;
  private String name;

  public Ingredient (String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public int getId() {
    return this.id;
  }

  public static List<Ingredient> all() {
    String sql = "SELECT id, name FROM ingredients";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Ingredient.class);
    }
  }

  public boolean equals(Object otherIngredient) {
   if (!(otherIngredient instanceof Ingredient)) {
      return false;
    } else {
      Ingredient newIngredient =  (Ingredient) otherIngredient;
      return this.getName().equals(newIngredient.getName()) &&
             this.getId() == newIngredient.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO ingredients (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

}
