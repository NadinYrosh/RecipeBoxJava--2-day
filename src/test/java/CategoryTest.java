import java.util.List;
import org.junit.*;
import java.util.ArrayList;
import org.sql2o.*;

import static org.junit.Assert.*;

public class CategoryTest {


  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Category_instantiatesCorrectly_true() {
    Category myCategory = new Category("Dinner");
    assertEquals(true, myCategory instanceof Category);
  }

  @Test
  public void getName_instantiatesWithCategory_string() {
    Category myCategory = new Category("Dinner");
    assertEquals("Dinner", myCategory.getName());
  }

}
