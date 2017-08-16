/*
 *   Copyright (C) 2012-2017 Christian P. Lerch, Vienna, Austria.
 *
 *   This file is part of DSLtools - a suite of software tools for effective
 *   DSL implementations.
 *
 *   DSLtools is free software: you can use, modify and redistribute it under
 *   the terms of the GNU General Public License version 3 as published by
 *   the Free Software Foundation, Inc. <http://fsf.org/>
 *
 *   DSLtools is distributed in the hope that it will be useful, but WITHOUT
 *   ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 *   FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 *   version 3 for details.
 *
 *   You should have received a copy of the GNU General Public License along
 *   with this distribution. If not, see <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package kmworks.dsltools.adt.base;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Christian P. Lerch
 */
public final class Annotations {
    
    private final Map<String, Object> annotations = new HashMap<>();
    
    public void add(String key, Object value) {
        annotations.put(key, value);
    }
    
    public boolean isEmpty() {
        return annotations.isEmpty();
    }
    
    public boolean has(String key) {
        return annotations.containsKey(key);
    }
    
    public Map<String, Object> contents() {
        return annotations;
    }
    
    public Object get(String key) {
        return annotations.get(key);
    }
    
}
