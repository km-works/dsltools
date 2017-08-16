/*
 * xtc - The eXTensible Compiler
 * Copyright (C) 2005 Robert Grimm
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 2 as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301,
 * USA.
 */
package xtc.parser;

import java.util.Objects;
import xtc.tree.Node;

/**
 * The superclass of all names.
 *
 * @author Robert Grimm
 * @version $Revision: 1.2 $
 */
public class Name extends Node {

  /** The name. */
  public final String name;

  /**
   * Create a new name.
   *
   * @param name The name.
   */
  public Name(String name) {
    this.name = name;
  }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Name other = (Name) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

  public int hashCode() {
    return name.hashCode();
  }

  public String toString() {
    return name;
  }

}
