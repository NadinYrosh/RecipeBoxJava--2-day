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

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Welcome to Recipe Box!");
  }

  @Test
  public void recipeIsCreatedTest() {
    goTo("http://localhost:4567/");
    fill("#recipe").with("this is the best recipe");
    fill("#name").with("Big Burger");
    fill("#rating").with("5");
    submit("#save-recipe");
    assertThat(pageSource()).contains("Big Burger");
  }

  @Test
  public void recipeIsDisplayedTest() {
    Recipe newRecipe = new Recipe("Pie", "Bake a pie", 5);
    newRecipe.save();
    String url = String.format("http://localhost:4567/recipes/%d", newRecipe.getId());
    goTo(url);
    assertThat(pageSource()).contains("Bake a pie");
  }

  @Test
  public void ingredientAddedToRecipePage() {
    Recipe newRecipe = new Recipe("Pie", "Bake a pie", 5);
    newRecipe.save();
    Ingredient newIngredient = new Ingredient("Flour");
    newIngredient.save();
    String url = String.format("http://localhost:4567/recipes/%d", newRecipe.getId());
    goTo(url);
    assertThat(pageSource()).contains("Flour");
  }

  @Test
  public void createCategory() {
    goTo("http://localhost:4567/");
    fill("#category").with("Dinner");
    submit("#save-category");
    assertThat(pageSource()).contains("Dinner");
  }

  @Test
  public void displayCategoryPage() {
    Category newCategory = new Category ("Dinner");
    newCategory.save();
    String url = String.format("http://localhost:4567/categories/%d", newCategory.getId());
    goTo(url);
    assertThat(pageSource()).contains("Dinner");
  }

  @Test
  public void deleteCategory(){
    Category newCategory = new Category ("Dinner");
    newCategory.save();
    String url = String.format("http://localhost:4567/categories/%d", newCategory.getId());
    goTo(url);
    submit("#delete");
    goTo(url);
    assertThat(pageSource()).contains("$category.getName()");
  }

  @Test
  public void deleteRecipe(){
    Recipe newRecipe = new Recipe("Pirojki", "Bake a pirojki", 5);
    newRecipe.save();
    String url = String.format("http://localhost:4567/recipes/%d", newRecipe.getId());
    goTo(url);
    submit("#delete");
    goTo(url);
    assertThat(pageSource()).contains("$recipe.getTitle()");
  }

  @Test
  public void updateRecipe () {
    Recipe newRecipe = new Recipe("Pirojki", "Bake a pirojki", 5);
    newRecipe.save();
    String url = String.format("http://localhost:4567/recipes/%d", newRecipe.getId());
    goTo(url);
    fill("#name").with("Potato");
    fill("#recipe").with("Buy one");
    fill("#rating").with("10");
    submit("#update");
    goTo(url);
    assertThat(pageSource()).contains("Potato");
  }
}
