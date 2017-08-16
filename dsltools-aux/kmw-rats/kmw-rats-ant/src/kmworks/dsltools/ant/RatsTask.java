package kmworks.dsltools.ant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import xtc.parser.Rats;

/**
 * Ant task for running the Rats tool on a rats grammar file.
 *
 * @author Christian P. Lerch
 * @version 1.0
 */
public class RatsTask extends Task {

  private File _main = null;    // Rats main module file
  private File _out = null;     // output directoy
  private final List<File> _roots = new ArrayList<File>();      // list of root search paths for dependent modules
  private final List<String> _opts = new ArrayList<String>();   // list of processing options

  @Override
  public void execute() throws BuildException {
    if (_main == null) {
      throw new BuildException("Rats requires at least one <file> argument!");
    } else {
      List<String> argList = new ArrayList<String>();
      
      argList.add("-no-exit");
      argList.addAll(_opts);
      
      for (File dir : _roots) {
        argList.add("-in");
        argList.add(dir.getAbsolutePath());
      }
      
      argList.add("-out");
      if (_out == null) {
        argList.add(_roots.get(0).getAbsolutePath());
      } else {
        argList.add(_out.getAbsolutePath());
      }
      argList.add(_main.getAbsolutePath());
      
      String[] args = argList.toArray(new String[argList.size()]);
      try {
        new Rats().run(args);
      } catch (Exception e) {
        throw new BuildException(e);
      }
    }
  }

  /**
   * Set the main module file (required).
   * 
   * @param file Abstract file path to the main module file.
   */
  public void setFile(File file) {
    if (file != null && file.isFile() && file.exists() && file.canRead()) {
      _main = file;
      _roots.add(0, file.getParentFile());
    }
  }
  
  /**
   * Set root search paths for dependent modules (optional).
   * All modules imported by the main module must be located in one of these directories.
   * All module names and module pathes are to be given relative to these locations.
   * If not set the directory of the main module file is taken as default.
   * 
   * @param dir Abstract file path to the root of the module repository
   */
  public void setRoot(File dir) {
    // Note: Currently only a single module search path is supported
    _roots.add(dir);
  }
  
  /**
   * Set the output directory for the generated parser Parser.java (optional).
   * If not set the output file will be created in the same directory as the input file.
   * 
   * @param dir 
   */
  public void setOut(File dir) {
    _out = dir;
  }
  
  /**
   * Set Rats processing options (optional).
   * Option -no-exit is alway used and cannot be deactivated.
   * See doc/rats/rats-usage.txt for details on available processing options.
   * 
   * @param optList 
   */
  public void setOptions(String optList) {
    String[] opts = optList.split(",");
    for (int i=0; i<opts.length; i++) {
      _opts.add("-" + opts[i].trim());
    }
  }
}
