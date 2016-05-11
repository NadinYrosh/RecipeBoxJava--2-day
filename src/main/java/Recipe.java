import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Recipe {
  private int id;
  private String title;
  private String instructions;
  private int rating;

  public Recipe (String title, String instructions, int rating) {
    this.title = title;
    this.instructions = instructions;
    this.rating = rating;
  }

  public String getTitle() {
    return this.title;
  }

  public String getInstructions() {
    return this.instructions;
  }
  public int getRating() {
    return this.rating;
  }

  public int getId() {
    return this.id;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO recipes (title, instructions, rating) VALUES (:title, :instructions, :rating)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("title", this.title)
        .addParameter("instructions", this.instructions)
        .addParameter("rating", this.rating)

        .executeUpdate()
        .getKey();
    }
  }
  public void update(String newTitle, String newInstructions, int newRating) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE recipes SET title = :title, instructions = :instructions, rating =:rating WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .addParameter("title", newTitle)
        .addParameter("instructions", newInstructions)
        .addParameter("rating", newRating)
        .executeUpdate();
    }
  }

  public void addCategory(Category newCategory) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO categories_recipes (recipe_id, category_id) VALUES (:recipe, :category)";
      con.createQuery(sql)
        .addParameter("category", newCategory.getId())
        .addParameter("recipe", this.id)
        .executeUpdate();
    }
  }

  public List<Category> getCategories() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT category_id FROM categories_recipes WHERE recipe_id = :recipe_id";

      List<Integer> categoryIds =  con.createQuery(sql)
        .addParameter("recipe_id", this.id)
        .executeAndFetch(Integer.class);

      List<Category> categories = new ArrayList<Category>();

      for (Integer category_id : categoryIds) {
        String categoryQuery = "Select * FROM categories WHERE id = :category_id";
        Category tempCategory = con.createQuery(categoryQuery)
          .addParameter("category_id", category_id)
          .executeAndFetchFirst(Category.class);
        categories.add(tempCategory);
      }
      return categories;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM recipes WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();

      String joinTableDelete = "DELETE FROM categories_recipes WHERE recipe_id = :recipe_id";
      con.createQuery(joinTableDelete)
        .addParameter("recipe_id", this.id)
        .executeUpdate();

        String otherJoinTableDelete = "DELETE FROM recipes_ingredients WHERE recipe_id = :recipe_id";
        con.createQuery(otherJoinTableDelete)
          .addParameter("recipe_id", this.id)
          .executeUpdate();
    }
  }

  public static Recipe find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM recipes WHERE id = :id";
      Recipe recipe = con.createQuery(sql)
              .addParameter("id", id)
              .executeAndFetchFirst(Recipe.class);
      return recipe;
    }
  }

  public static List<Recipe> all() {
    String sql = "SELECT id, title, instructions, rating FROM recipes";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Recipe.class);
    }
  }

  @Override
  public boolean equals(Object otherRecipe) {
    if (!(otherRecipe instanceof Recipe)) {
      return false;
    } else {
      Recipe newRecipe =  (Recipe) otherRecipe;
      return this.getTitle().equals(newRecipe.getTitle()) &&
             this.getInstructions().equals(newRecipe.getInstructions()) &&
             this.getRating() == newRecipe.getRating() &&
             this.getId() == newRecipe.getId();
    }
  }
}
