import org.sql2o.*;
import org.junit.*;
import java.util.List;
import static org.junit.Assert.*;
import static org.junit.Assert.*;

public class RecipeTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Recipe_instantiatesCorrectly_true() {
    Recipe myRecipe = new Recipe("Pie", "Bake a pie", 5);
    assertEquals(true, myRecipe instanceof Recipe);
  }

  @Test
  public void getTitle_instantiatesWithTitleName_string() {
    Recipe myRecipe = new Recipe("Pie", "Bake a pie", 5);
    assertEquals("Pie", myRecipe.getTitle());
  }

  @Test
  public void getInstructions_instantiatesWithInstructions_string() {
    Recipe myRecipe = new Recipe("Pie", "Bake a pie", 5);
    assertEquals("Bake a pie", myRecipe.getInstructions());
  }

  @Test
  public void getRating_instantiatesWithRating_int() {
    Recipe myRecipe = new Recipe("Pie", "Bake a pie", 5);
    assertTrue(5 == myRecipe.getRating());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Recipe.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfTitleAreTheSame_true() {
    Recipe myRecipe = new Recipe("Pie", "Bake a pie", 5);
    Recipe mySecondRecipe = new Recipe("Pie", "Bake a pie", 5);
    assertTrue(myRecipe.equals(mySecondRecipe));
  }

  @Test
  public void save_savesObjectIntoDatabase_true() {
    Recipe myRecipe = new Recipe("Pie", "Bake a pie", 5);
    myRecipe.save();
    assertTrue(Recipe.all().get(0).equals(myRecipe));
  }

  @Test
  public void save_assignsIdToObject() {
    Recipe myRecipe = new Recipe("Pie", "Bake a pie", 5);
    myRecipe.save();
    Recipe savedRecipe = Recipe.all().get(0);
    assertEquals(myRecipe.getId(), savedRecipe.getId());
  }

  @Test
  public void find_findsRecipesInDatabase_True() {
    Recipe myRecipe = new Recipe("Pie", "Bake a pie", 5);
    myRecipe.save();
    Recipe savedRecipe = Recipe.find(myRecipe.getId());
    assertTrue(myRecipe.equals(savedRecipe));
  }

  @Test
  public void update_updatesRecipes_true() {
    Recipe myRecipe = new Recipe("Pie", "Bake a pie", 5);
    myRecipe.save();
    myRecipe.update("Scone", "Bake a scone", 10);
    assertEquals("Scone", Recipe.find(myRecipe.getId()).getTitle());
    assertEquals("Bake a scone", Recipe.find(myRecipe.getId()).getInstructions());
    assertTrue(10 == Recipe.find(myRecipe.getId()).getRating());
  }

  @Test
  public void delete_deletesRecipe_true() {
    Recipe myRecipe = new Recipe("Pie", "Bake a pie", 5);
    myRecipe.save();
    int myRecipeId = myRecipe.getId();
    myRecipe.delete();
    assertEquals(null, Recipe.find(myRecipeId));
  }

  @Test
  public void addCategory_addsCategoryToAuthor() {
    Category myCategory = new Category("Dinner");
    myCategory.save();
    Recipe myRecipe = new Recipe("Pie", "Bake a pie", 5);
    myRecipe.save();
    myRecipe.addCategory(myCategory);
    Category savedCategory = myRecipe.getCategories().get(0);
    assertTrue(myCategory.equals(savedCategory));
  }

  @Test
  public void getCategories_returnsAllCategories_List() {
    Category myCategory = new Category("Dinner");
    myCategory.save();
    Recipe myRecipe = new Recipe("Pie", "Bake a pie", 5);
    myRecipe.save();
    myRecipe.addCategory(myCategory);
    List savedCategories = myRecipe.getCategories();
    assertEquals(1, savedCategories.size());
  }

  @Test
  public void delete_deletesAllRecipeAndCategoryAssociations() {
    Category myCategory = new Category("Dinner");
    myCategory.save();
    Recipe myRecipe = new Recipe("Pie", "Bake a pie", 5);
    myRecipe.save();
    myRecipe.addCategory(myCategory);
    myRecipe.delete();
    assertEquals(0, myRecipe.getCategories().size());
  }
//----------------------------------------//
  @Test
  public void addIngridient_addsIngredientToARecipe() {
    Ingredient myIngredient = new Ingredient("Peach");
    myIngredient.save();
    Recipe myRecipe = new Recipe("Pie", "Bake a pie", 5);
    myRecipe.save();
    myRecipe.addIngredient(myIngredient);
    Ingredient savedIngredient = myRecipe.getIngredients().get(0);
    assertTrue(myIngredient.equals(savedIngredient));
  }
}
