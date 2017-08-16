/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xtc.parser;

import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Christian P. Lerch
 */
public class RatsTest {
  
  public RatsTest() {
  }

  // The following test case just documents how to execute the Rats parser generator tool
  // You must fill-in real file paths, before using it
  @Test @Ignore
  public void testMain() {
    String rootPath = "/abs/path/to/rats-root-dir";
    String srcPath = rootPath + "/rats/main-module-dir";
    File modDir = new File(rootPath);   // top-level dir for all rats modules
    File srcDir = new File(srcPath);    // dir for main rats module - somehwere below rootPath
    String[] args = new String[] { 
      "-no-exit",     // MUST use this!
      "-verbose",     // optional
      "-in",          // main module dir
      srcDir.getAbsolutePath(),
      "-in",          // top-level dir
      modDir.getAbsolutePath(),
      "-out",         // where to write the generated parser; must be consistent with package name
      srcDir.getAbsolutePath(),
      srcPath + "main.rats"   // file name of main Rats module
    };
    Rats.main(args);
  }
}
