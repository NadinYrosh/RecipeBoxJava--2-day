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

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Category.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAreTheSame_true() {
    Category firstCategory = new Category("Dinner");
    Category secondCategory = new Category("Dinner");
    assertTrue(firstCategory.equals(secondCategory));
  }

  @Test
  public void save_savesObjectIntoDatabase_true() {
    Category myCategory = new Category("Dinner");
    myCategory.save();
    assertTrue(Category.all().get(0).equals(myCategory));
  }

  @Test
  public void save_assignsIdToObject() {
    Category myCategory = new Category("Dinner");
    myCategory.save();
    Category savedCategory = Category.all().get(0);
    assertEquals(myCategory.getId(), savedCategory.getId());
  }

  @Test
  public void find_findsCategoriesInDatabase_True() {
    Category myCategory = new Category("Dinner");
    myCategory.save();
    Category savedCategory = Category.find(myCategory.getId());
    assertTrue(myCategory.equals(savedCategory));
  }

  // @Test
  // public void addBook_addsBookToAuthor() {
  //   Book myBook = new Book("Tom Sawyer");
  //   myBook.save();
  //   Author myAuthor = new Author("Dave", "Smith");
  //   myAuthor.save();
  //   myAuthor.addBook(myBook);
  //   Book savedBook = myAuthor.getBooks().get(0);
  //   assertTrue(myBook.equals(savedBook));
  // }
  //
  // @Test
  // public void getBooks_returnsAllBooks_List() {
  //   Book myBook = new Book("Tom Sawyer");
  //   myBook.save();
  //   Author myAuthor = new Author("Dave", "Smith");
  //   myAuthor.save();
  //   myAuthor.addBook(myBook);
  //   List savedBooks = myAuthor.getBooks();
  //   assertEquals(1, savedBooks.size());
  // }
  //
  // @Test
  // public void delete_deletesAllAuthorAndBookAssociations() {
  //   Book myBook = new Book("Tom Sawyer");
  //   myBook.save();
  //   Author myAuthor = new Author("Dave", "Smith");
  //   myAuthor.save();
  //   myAuthor.addBook(myBook);
  //   myAuthor.delete();
  //   assertEquals(0, myBook.getAuthors().size());
  // }
}
