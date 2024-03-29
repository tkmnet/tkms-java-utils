package jp.tkms.utils.value;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InitTest {

  /*
  @BeforeAll static void beforeAll() {
  }

  @BeforeEach void beforeEach() {
  }``
   */

    @Test
    void success() {
        assertEquals((5 + 1), new Init<Integer>().call(() -> {
            int i = 5 + 1;
            return i;
        }));

        assertEquals((5 + 2), new Init<AtomicInteger>().call(
                new AtomicInteger(5),
                (o) -> {
                    o.addAndGet(2);
                }).get()
        );

        assertEquals((5), new Init<Integer>().call(
                Integer.valueOf(5),
                (o) -> {
                    o += 3;
                })
        );
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
