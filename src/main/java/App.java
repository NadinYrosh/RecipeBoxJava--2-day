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
  }
}
