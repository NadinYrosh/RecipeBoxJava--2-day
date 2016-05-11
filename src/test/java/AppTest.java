import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.assertj.core.api.Assertions.assertThat;
import org.sql2o.*;
import org.junit.*;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Before
    public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/recipe_box_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deleteRecipesQuery = "DELETE FROM recipes *;";
      String deleteCategoriesQuery = "DELETE FROM categories *;";
      String deleteIngredientsQuery = "DELETE FROM ingredients *;";
      String deleteRecipeIngredientQuery = "DELETE FROM recipe_ingredient *;";
      String deleteCategoryRecipeQuery = "DELETE FROM category_recipe *;";
      con.createQuery(deleteRecipesQuery).executeUpdate();
      con.createQuery(deleteCategoriesQuery).executeUpdate();
      con.createQuery(deleteIngredientsQuery).executeUpdate();
      con.createQuery(deleteRecipeIngredientQuery).executeUpdate();
      con.createQuery(deleteCategoryRecipeQuery).executeUpdate();
    }
  }

  // @Test
  // public void rootTest() {
  //   goTo("http://localhost:4567/");
  //   assertThat(pageSource()).contains("");
  // }
}
