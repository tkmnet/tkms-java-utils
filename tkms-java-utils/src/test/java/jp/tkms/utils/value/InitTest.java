package jp.tkms.utils.value;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InitTest {

  /*
  @BeforeAll static void beforeAll() {
  }

  @BeforeEach void beforeEach() {
  }``
   */

  @Test void success() {
    assertEquals((5 +1), new Init<Integer>().call(()-> {
      int i = 5 + 1;
      return i;
    }));

    assertEquals((5 +1), new Init<Integer>().call(new Integer(5), (o) -> { return o +1; }));
  }

  /*
  @Test void fail() {
    assertNotNull(object.get(0), "app should have a greeting");
  }

  @AfterEach void afterEach() {
  }

  @AfterAll static void afterAll() {
  }
   */
}
