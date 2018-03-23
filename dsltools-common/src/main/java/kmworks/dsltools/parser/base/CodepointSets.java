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
package kmworks.dsltools.parser.base;

import static com.google.common.base.Preconditions.*;
import java.util.HashMap;
import java.util.Map;
import kmworks.util.cp.CodepointBitSet;
import kmworks.util.cp.CodepointPredicate;
import kmworks.util.cp.CodepointSetUtil;

/**
 *
 * @author Christian P. Lerch
 */
public final class CodepointSets {
    
    private CodepointSets() {}
    
    private static final String MSG_MUST_NOT_BE_NULL = " must not be null";
    private static final String DEFAULT_INVALID_STRING_CHARS = "\"\\\u201c\u201d\u2028\u2029";
    
    /**  Default Invalid String Characters.
     *   A parsed string literal should not contain any control characters less \u0020 nor
     *   any of the following characters unescaped:
     *   \u0022  QUOTATION MARK (")
     *   \u005c  REVERSE SOLIDUS (\)
     *   \u201c  LEFT DOUBLE QUOTATION MARK
     *   \u201d  RIGHT DOUBLE QUOTATION MARK
     *   \u2028  LINE SEPARATOR
     *   \u2029  PARAGRAPH SEPARATOR
     *   Set size: ~1KB
     */

    private static final CodepointSets INSTANCE = new CodepointSets();
    
    private final Map<String, CodepointPredicate> codepointSet = new HashMap<>();
        
    public static final CodepointPredicate DEFAULT$INVALID_STRING_CHAR_SET = 
            ((CodepointPredicate) CodepointBitSet.of(
                    CodepointSetUtil.codepointsFrom(DEFAULT_INVALID_STRING_CHARS)))
            .or(new CodepointPredicate() {
                @Override public boolean contains(int cp) {
                    return Character.getType(cp) == Character.CONTROL;
                }
            });
    
    public static CodepointPredicate get(String name) {
        checkNotNull(name, "name" + MSG_MUST_NOT_BE_NULL);
        CodepointPredicate result = INSTANCE.codepointSet.get(name);
        checkNotNull(result, String.format("codepoint set '%s' not registered", name));
        return result;
    }
    
    public static void add(String name, CodepointPredicate set) {
        checkNotNull(name, "name" + MSG_MUST_NOT_BE_NULL);
        checkNotNull(set, "set" + MSG_MUST_NOT_BE_NULL);
        INSTANCE.codepointSet.put(name, set);
    }
    
    public static boolean isRegistered(String name) {
        return INSTANCE.codepointSet.containsKey(name);
    }
}
