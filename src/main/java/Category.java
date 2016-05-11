import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Category {
  private String name;


  public Category(String name){
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

}
