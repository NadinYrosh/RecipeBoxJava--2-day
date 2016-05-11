import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Category {
  private String name;
  private int id;


  public Category(String name){
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public int getId() {
    return id;
  }

  public static List<Category> all() {
    String sql = "SELECT id, name FROM categories";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Category.class);
    }
  }

  @Override
  public boolean equals(Object otherCategory) {
    if (!(otherCategory instanceof Category)) {
      return false;
    } else {
      Category newCategory =  (Category) otherCategory;
      return this.getName().equals(newCategory.getName()) &&
             this.getId() == newCategory.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO categories (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Category find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM categories WHERE id = :id";
      Category category = con.createQuery(sql)
              .addParameter("id", id)
              .executeAndFetchFirst(Category.class);
      return category;
    }
  }

  public void addRecipe(Recipe newRecipe) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO categories_recipes (recipe_id, category_id) VALUES (:recipe, :category)";
      con.createQuery(sql)
        .addParameter("category", this.id)
        .addParameter("recipe",newRecipe.getId())
        .executeUpdate();
    }
  }

  public List<Recipe> getRecipes() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT recipe_id FROM categories_recipes WHERE category_id = :category_id";

      List<Integer> recipeIds =  con.createQuery(sql)
        .addParameter("category_id", this.id)
        .executeAndFetch(Integer.class);

      List<Recipe> recipes = new ArrayList<Recipe>();

      for (Integer recipe_id : recipeIds) {
        String recipeQuery = "Select * FROM recipes WHERE id = :recipe_id";
        Recipe tempRecipe = con.createQuery(recipeQuery)
          .addParameter("recipe_id", recipe_id)
          .executeAndFetchFirst(Recipe.class);
        recipes.add(tempRecipe);
      }
      return recipes;
    }
  }
}
