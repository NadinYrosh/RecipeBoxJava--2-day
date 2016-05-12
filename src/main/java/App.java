import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;


public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/recipes", (request, response) -> {
      String name = request.queryParams("name");
      String recipe = request.queryParams("recipe");
      Integer rating = Integer.parseInt(request.queryParams("rating"));
      Recipe newRecipe = new Recipe (name, recipe, rating);
      newRecipe.save();
      response.redirect("/recipes");
      return null;
    });

    get("/recipes", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      model.put("recipes", Recipe.all());
      model.put("template", "templates/recipes.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/recipes/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Recipe recipe = Recipe.find(Integer.parseInt(request.params("id")));

      model.put("recipe", recipe);
      model.put("template", "templates/recipe.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/recipes/:id/ingredient", (request, response)-> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("ingredient");
      Ingredient ingredient = new Ingredient(name);
      ingredient.save();
      Recipe recipe = Recipe.find(Integer.parseInt(request.params("id")));
      recipe.addIngredient(ingredient);
      String url = String.format("http://localhost:4567/recipes/%d", recipe.getId());
      response.redirect(url);
      return null;
    });

    get("/categories", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      model.put("categories", Category.all());
      model.put("template", "templates/categories.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/categories", (request, response) -> {
      String name = request.queryParams("category");
      Category newCategory = new Category (name);
      newCategory.save();
      response.redirect("/categories");
      return null;
    });

    get("/categories/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Category category = Category.find(Integer.parseInt(request.params("id")));

      model.put("category", category);
      model.put("template", "templates/category.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
