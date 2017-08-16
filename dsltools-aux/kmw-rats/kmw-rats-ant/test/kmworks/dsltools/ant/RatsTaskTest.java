package kmworks.dsltools.ant;

import java.io.File;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author cpl
 */
public class RatsTaskTest {
  
  public RatsTaskTest() {
  }

  @BeforeClass
  public static void setUpClass() throws Exception {
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
  }

  // The following test case just documents how to execute a RatsTask from Java
  // You must fill-in real file paths, before using it
  @Test @Ignore
  public void testExecute() {
    RatsTask rats = new RatsTask();
    // absolute path of Rats main module
    rats.setFile(new File("/abs/path/to/rats-root-dir/rats/main-mod/main.rats"));
    // absolute path of top-level dir for all Rats modules
    rats.setRoot(new File("/abs/path/to/rats-root-dir"));
    // any number of options
    rats.setOptions("verbose");
    rats.execute();
  }
}
