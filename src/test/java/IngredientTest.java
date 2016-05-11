import org.sql2o.*;
import org.junit.*;
import java.util.List;
import static org.junit.Assert.*;
import static org.junit.Assert.*;

public class IngredientTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Ingredients_instantiatesCorrectly_true() {
    Ingredient myIngredient = new Ingredient("Peach");
    assertEquals(true, myIngredient instanceof Ingredient);
  }

  @Test
  public void getName_instantiatesWithIngedientName_string() {
    Ingredient myIngredient = new Ingredient("Peach");
    assertEquals("Peach", myIngredient.getName());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Ingredient.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAreTheSame_true() {
    Ingredient firstIngredient = new Ingredient("Peach");
    Ingredient secondIngredient = new Ingredient("Peach");
    assertTrue(firstIngredient.equals(secondIngredient));
  }

  @Test
  public void save_savesObjectIntoDatabase_true() {
    Ingredient myIngredient = new Ingredient("Peach");
    myIngredient.save();
    assertTrue(Ingredient.all().get(0).equals(myIngredient));
  }

  @Test
  public void save_assignsIdToObject() {
    Ingredient myIngredient = new Ingredient("Peach");
    myIngredient.save();
    Ingredient savedIngredient = Ingredient.all().get(0);
    assertEquals(myIngredient.getId(), savedIngredient.getId());
  }

  @Test
  public void find_findsIngredientsInDatabase_True() {
    Ingredient myIngredient = new Ingredient("Peach");
    myIngredient.save();
    Ingredient savedIngredient = Ingredient.find(myIngredient.getId());
    assertTrue(myIngredient.equals(savedIngredient));
  }

  // @Test
  // public void addRecipe_addsRecipeToIngredient() {
  //   Recipe myRecipe = new Recipe("Pie", "Back a pie", 5);
  //   myRecipe.save();
  //   Ingredient myIngredient = new Ingredient("Peach");
  //   myIngredient.save();
  //   myIngredient.addRecipe(myRecipe);
  //   Recipe savedRecipe = myIngredient.getRecipes().get(0);
  //   assertTrue(myRecipe.equals(savedRecipe));
  // }

}
