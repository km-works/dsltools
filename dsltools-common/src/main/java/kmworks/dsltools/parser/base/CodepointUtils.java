/*
 *   Copyright (C) 2012-2018 Christian P. Lerch, Vienna, Austria.
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
import static kmworks.dsltools.parser.base.CodepointUtils.Predefined.*;
import static kmworks.util.base.Preconditions.checkNotEmpty;
import static org.immutables.value.Value.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import kmworks.util.strings.StringEscapeUtil;
import kmworks.util.StringPool;
import kmworks.util.ds.rng.IntPredicate;
import kmworks.util.ds.rng.IntRangeFactory;
import kmworks.util.ds.rng.IntRangeUtil;
import kmworks.util.ds.rng.impl.BitsetIntRange;
import kmworks.util.lambda.Function1;

import kmworks.util.strings.StringTransformerFactory;

import javax.annotation.Nonnull;

/**
 * @author Christian P. Lerch
 */
public final class CodepointUtils {

    // Generic character set names
    public static final String DEC_DIGITS = "DEC_DIGITS";
    public static final String HEX_DIGITS = "HEX_DIGITS";
    public static final String IDENTIFIER_HEAD_CHARS = "IDENTIFIER_HEAD_CHARS";
    public static final String IDENTIFIER_TAIL_CHARS = "IDENTIFIER_TAIL_CHARS";
    public static final String STRING_LITERAL_INVALID_CHARS = "STRING_LITERAL_INVALID_CHARS";
    public static final String STRING_LITERAL_ESCAPED_CHARS = "STRING_LITERAL_ESCAPED_CHARS";
    public static final String WHITESPACE_CHARS = "WHITESPACE_CHARS";
    public static final String OTHER_NEWLINE_CHARS = "OTHER_NEWLINE_CHARS"; // than \r or \n

    // String transformer names
    public static final String STRING_LITERAL_UNESCAPE_TRFM = "STRING_LITERAL_UNESCAPE_TRFM";
    public static final String STRING_LITERAL_ESCAPE_TRFM = "STRING_LITERAL_ESCAPE_TRFM";


    private static final CodepointUtils INSTANCE = new CodepointUtils();

    private CodepointUtils() {

        /*
         *  SyntaxType DEFAULT
         */

        // DEFAULT Digits
        regPredSupplier(mkKey(SyntaxType.DEFAULT, DEC_DIGITS), DEFAULT$DEC_DIGIT$SET);
        regPredSupplier(mkKey(SyntaxType.DEFAULT, HEX_DIGITS), DEFAULT$HEX_DIGIT$SET);

        // DEFAULT StringLiterals
        regPredSupplier(mkKey(SyntaxType.DEFAULT, STRING_LITERAL_INVALID_CHARS), JAVA$STRING_LITERAL_INVALID_CHARS$SET);
        regPredSupplier(mkKey(SyntaxType.DEFAULT, STRING_LITERAL_ESCAPED_CHARS), DEFAULT$STRING_LITERAL_ESCAPED_CHARS$SET);
        regTrfmSupplier(mkKey(SyntaxType.DEFAULT, STRING_LITERAL_ESCAPE_TRFM),
                () -> s -> StringTransformerFactory.mkLookupTransformer(
                        new String[][]{
                                {"\n", "\\n"},
                                {"\r", "\\r"},
                                {"\"", "\\\""},
                                {"\\", "\\\\"}
                        }
                ).translate(s));
        regTrfmSupplier(mkKey(SyntaxType.DEFAULT, STRING_LITERAL_UNESCAPE_TRFM),
                () -> s -> StringEscapeUtil.unescapeJava(s)); // TODO

        // DEFAULT Whitespace
        regPredSupplier(mkKey(SyntaxType.DEFAULT, OTHER_NEWLINE_CHARS), () -> IntPredicate.FALSE);
        regPredSupplier(mkKey(SyntaxType.DEFAULT, WHITESPACE_CHARS), DEFAULT$WHITESPACE_CHARS$SET);

        // DEFAULT Identifiers
        regPredSupplier(mkKey(SyntaxType.DEFAULT, IDENTIFIER_HEAD_CHARS), DEFAULT$IDENTIFIER_HEAD_CHARS$SET);
        regPredSupplier(mkKey(SyntaxType.DEFAULT, IDENTIFIER_TAIL_CHARS), DEFAULT$IDENTIFIER_TAIL_CHARS$SET);

        /*
         *  SyntaxType JAVA
         */

        // JAVA StringLiterals
        regPredSupplier(mkKey(SyntaxType.JAVA, STRING_LITERAL_INVALID_CHARS), JAVA$STRING_LITERAL_INVALID_CHARS$SET);
        regPredSupplier(mkKey(SyntaxType.JAVA, STRING_LITERAL_ESCAPED_CHARS), JAVA$STRING_LITERAL_ESCAPED_CHARS$SET);
        regTrfmSupplier(mkKey(SyntaxType.JAVA, STRING_LITERAL_ESCAPE_TRFM),
                () -> s -> StringEscapeUtil.escapeJava(s));
        regTrfmSupplier(mkKey(SyntaxType.JAVA, STRING_LITERAL_UNESCAPE_TRFM),
                () -> s -> StringEscapeUtil.unescapeJava(s));

        // JAVA Whitespace
        regPredSupplier(mkKey(SyntaxType.JAVA, OTHER_NEWLINE_CHARS), () -> IntPredicate.FALSE);
        regPredSupplier(mkKey(SyntaxType.JAVA, WHITESPACE_CHARS), () -> ch -> Character.isWhitespace(ch));

        /*
         *  SyntaxType PEG
         */

        // PEG String Literals
        regPredSupplier(mkKey(SyntaxType.PEG, STRING_LITERAL_INVALID_CHARS), () -> IntPredicate.FALSE);
        regPredSupplier(mkKey(SyntaxType.PEG, STRING_LITERAL_ESCAPED_CHARS), PEG$STRING_LITERAL_ESCAPED_CHARS$SET);

        // PEG Whitespace
        regPredSupplier(mkKey(SyntaxType.PEG, WHITESPACE_CHARS), PEG$WHITESPACE_CHARS$SET);
        regPredSupplier(mkKey(SyntaxType.PEG, OTHER_NEWLINE_CHARS), () -> IntPredicate.FALSE);

        /*
         *  SyntaxType XML
         */

        // XML Identifiers
        regPredSupplier(mkKey(SyntaxType.XML, IDENTIFIER_HEAD_CHARS), XML$IDENTIFIER_HEAD_CHARS$SET);
        regPredSupplier(mkKey(SyntaxType.XML, IDENTIFIER_TAIL_CHARS), XML$IDENTIFIER_TAIL_CHARS$SET);
    }

    /*
        static accessors & utils
     */

    private static Key mkDefaultKey(Key key) {
        return mkKey(SyntaxType.DEFAULT, key.setBaseName());
    }

    public static IntPredicate getPredicate(@Nonnull final Key key) {
        checkNotNull(key, "key " + StringPool.MUST_NOT_BE_NULL_MSG);
        return INSTANCE.predicateRepo.getOrElse(key, mkDefaultKey(key));
    }

    public static void addPredicate(@Nonnull final Key key, @Nonnull final Supplier<IntPredicate> supplier) {
        checkNotNull(key, "key " + StringPool.MUST_NOT_BE_NULL_MSG);
        checkNotNull(supplier, "supplier " + StringPool.MUST_NOT_BE_NULL_MSG);
        INSTANCE.regPredSupplier(key, supplier);
    }

    public static Function1<String, String> getTransformer(@Nonnull final Key key) {
        checkNotNull(key, "key " + StringPool.MUST_NOT_BE_NULL_MSG);
        return INSTANCE.transformerRepo.getOrElse(key, mkDefaultKey(key));
    }

    public static void addTransformer(@Nonnull final Key key, Supplier<Function1<String, String>> supplier) {
        checkNotNull(key, "key " + StringPool.MUST_NOT_BE_NULL_OR_EMPTY_MSG);
        checkNotNull(supplier, "supplier " + StringPool.MUST_NOT_BE_NULL_MSG);
        INSTANCE.regTrfmSupplier(key, supplier);
    }


    public static class Predefined {

        public static enum SyntaxType {
            DEFAULT,
            JAVA,
            RATS,
            PEG,
            ADT,
            XML,
        }

        static final Supplier<IntPredicate> DEFAULT$DEC_DIGIT$SET =
                () -> IntRangeFactory.withUnicodeBounds().createCompactIntRange('0', '9');

        static final Supplier<IntPredicate> DEFAULT$HEX_DIGIT$SET =
                () -> IntRangeFactory.withUnicodeBounds().builder()
                        .addRange('0', '9')
                        .addRange('a', 'f')
                        .addRange('A', 'F')
                        .build(BitsetIntRange.class);

        static final Supplier<IntPredicate> DEFAULT$STRING_LITERAL_ESCAPED_CHARS$SET =
                () -> (IntPredicate) IntRangeFactory.withUnicodeBounds()
                        .createBitsetIntRange(IntRangeUtil.codepointsFrom("nr\"\\"));

        static final Supplier<IntPredicate> DEFAULT$WHITESPACE_CHARS$SET =
                () -> IntRangeFactory.withUnicodeBounds().builder()
                        .addRange('\u0000', ' ')
                        .remove('\r')
                        .remove('\n')
                        .add('\u007f')
                        .build(BitsetIntRange.class);

        /* JAVA String Literals
         * Java-type string literal invalid characters.
         * An unparsed Java string literal must not contain any of the following characters unescaped:
         * \u000a   LINE FEED (LF) must be escaped as \n
         * \u000d   CARRIAGE RETURN (CR) must be escaped as \r
         * \u0022   QUOTATION MARK (") must be escaped as \"
         * \u005c   REVERSE SOLIDUS (\) must be escaped as \\
         * BitsetIntRange size: 10 Byte
         */
        static final Supplier<IntPredicate> JAVA$STRING_LITERAL_INVALID_CHARS$SET =
                () -> (IntPredicate) IntRangeFactory.withUnicodeBounds()
                        .createBitsetIntRange(IntRangeUtil.codepointsFrom("\n\r\"\\"));

        static final Supplier<IntPredicate> JAVA$STRING_LITERAL_ESCAPED_CHARS$SET =
                () -> (IntPredicate) IntRangeFactory.withUnicodeBounds()
                        .createBitsetIntRange(IntRangeUtil.codepointsFrom("nr\"\\tbf'"));

        /*
         *  PEG String Literals
         */
        static final Supplier<IntPredicate> PEG$STRING_LITERAL_ESCAPED_CHARS$SET =
                () -> (IntPredicate) IntRangeFactory.withUnicodeBounds()
                        .createBitsetIntRange(IntRangeUtil.codepointsFrom("nrt'\"[]\\"));


        static final Supplier<IntPredicate> PEG$WHITESPACE_CHARS$SET =
                () -> IntRangeFactory.withUnicodeBounds().builder()
                        .add(' ')
                        .add('\t')
                        .build(BitsetIntRange.class);
        /*
         *  DEFAULT Identifiers
         */
        static final Supplier<IntPredicate> DEFAULT$IDENTIFIER_HEAD_CHARS$SET =
                () -> {
                    final IntRangeFactory factory = IntRangeFactory.withUnicodeBounds();
                    return (IntPredicate) factory.builder()
                            .add('_')
                            .addRange('A', 'Z')
                            .addRange('a', 'z')
                            .build(BitsetIntRange.class);
                };

        static final Supplier<IntPredicate> DEFAULT$IDENTIFIER_TAIL_CHARS$SET =
                () -> DEFAULT$IDENTIFIER_HEAD_CHARS$SET.get()
                        .or(DEFAULT$DEC_DIGIT$SET.get());

        /*
         * XML Identifiers (NCNames)
         */
        static final Supplier<IntPredicate> XML$IDENTIFIER_HEAD_CHARS$SET =
                () -> {
                    final IntRangeFactory factory = IntRangeFactory.withUnicodeBounds();
                    return DEFAULT$IDENTIFIER_HEAD_CHARS$SET.get()
                            .or(factory.createSegmentedIntRange(
                                    factory.builder()
                                            .addRange(0xC0, 0xD6)
                                            .addRange(0xD8, 0xF6)
                                            .addRange(0xF8, 0x2FF)
                                            .build(BitsetIntRange.class),
                                    factory.createCompactIntRange(0x370, 0x37D),
                                    factory.createCompactIntRange(0x37F, 0x1FFF),
                                    factory.builder()
                                            .addRange(0x200C, 0x200D)
                                            .addRange(0x2070, 0x218F)
                                            .addRange(0x2C00, 0x2FEF)
                                            .build(BitsetIntRange.class),
                                    factory.createCompactIntRange(0x3001, 0xD7FF),
                                    factory.builder()
                                            .addRange(0xF900, 0xFDCF)
                                            .addRange(0xFDF0, 0xFFFD)
                                            .build(BitsetIntRange.class),
                                    factory.createCompactIntRange(0x10000, 0xEFFFF)
                            ));
                };

        static final Supplier<IntPredicate> XML$IDENTIFIER_TAIL_CHARS$SET =
                () -> {
                    final IntRangeFactory factory = IntRangeFactory.withUnicodeBounds();
                    return XML$IDENTIFIER_HEAD_CHARS$SET.get()
                            .or(DEFAULT$DEC_DIGIT$SET.get())
                            .or(factory.createSegmentedIntRange(
                                    factory.builder()
                                            .add('-')
                                            .add('.')
                                            .add(0xB7)
                                            .build(BitsetIntRange.class),
                                    factory.createCompactIntRange(0x0300, 0x036F),
                                    factory.createCompactIntRange(0x203F, 0x2040)
                            ));
                };

    }

    public static Key mkKey(@Nonnull final SyntaxType syntaxType, @Nonnull final String baseKey) {
        checkNotNull(syntaxType, "syntaxType " + StringPool.MUST_NOT_BE_NULL_MSG);
        checkNotEmpty(baseKey, "baseKey " + StringPool.MUST_NOT_BE_NULL_OR_EMPTY_MSG);
        return mkKey(syntaxType.toString(), baseKey);
    }

    public static Key mkKey(@Nonnull final String syntaxType, @Nonnull final String baseKey) {
        checkNotEmpty(syntaxType, "syntaxType " + StringPool.MUST_NOT_BE_NULL_OR_EMPTY_MSG);
        checkNotEmpty(baseKey, "baseKey " + StringPool.MUST_NOT_BE_NULL_OR_EMPTY_MSG);
        return KeyImpl.of(syntaxType.toUpperCase(), baseKey);
    }

    @org.immutables.value.Value.Immutable(builder = false, copy = false)
    @org.immutables.value.Value.Style(typeImmutable = "*Impl")
    static abstract class Key {

        @Parameter
        abstract String syntaxType();

        @Parameter
        abstract String setBaseName();

        public String toString() {
            return syntaxType() + "$" + setBaseName();
        }

    }


    private final class SupplierRegistry<K, T> {

        private final Map<K, Supplier<T>> registry = new HashMap<>();

        public void register(K key, Supplier<T> supplier) {
            registry.put(key, supplier);
        }

        public boolean contains(K key) {
            return registry.containsKey(key);
        }

        public Supplier<T> getUnchecked(K key) {
            return registry.get(key);
        }

        public Supplier<T> get(K key) {
            Supplier<T> result = getUnchecked(key);
            if (result != null) {
                return result;
            } else {
                throw new IllegalArgumentException(
                        String.format("Supplier %s not registered", key));
            }
        }

    }

    private final class CachedRepository<K, T> {

        private final SupplierRegistry<K, T> registry = new SupplierRegistry<>();

        public void registerSupplier(K key, Supplier<T> predSupplier) {
            registry.register(key, predSupplier);
        }

        public boolean hasSupplier(K key) {
            return registry.contains(key);
        }

        public T getUnchecked(K key) {
            return cache.getUnchecked(key);
        }

        public T get(K key) {
            if (hasSupplier(key)) {
                return getUnchecked(key);
            } else {
                throw new IllegalArgumentException(String.format("Supplier %s not registered", key));
            }
        }

        public T getOrElse(K key, K defaultKey) {
            if (hasSupplier(key)) {
                return getUnchecked(key);
            } else {
                return getUnchecked(defaultKey);
            }
        }

        private final LoadingCache<K, T> cache = CacheBuilder.newBuilder()
                .build(new CacheLoader<K, T>() {
                           @Override
                           public T load(K key) throws Exception {
                               return registry.get(key).get();
                           }
                       }
                );
    }

    private final CachedRepository<Key, IntPredicate> predicateRepo = new CachedRepository<>();

    private void regPredSupplier(Key key, Supplier<IntPredicate> predSupplier) {
        predicateRepo.registerSupplier(key, predSupplier);
    }

    private final CachedRepository<Key, Function1<String, String>> transformerRepo = new CachedRepository<>();

    private void regTrfmSupplier(Key key, Supplier<Function1<String, String>> trfmSupplier) {
        transformerRepo.registerSupplier(key, trfmSupplier);
    }

    /*
     *  Predicate repository
     *
    private final SupplierRegistry<Key, IntPredicate> predicateRegistry = new SupplierRegistry<>();

    private void regPredSupplier(Key key, Supplier<IntPredicate> predSupplier) {
        predicateRegistry.register(key, predSupplier);
    }

    private boolean hasPredSupplier(Key key) {
        return predicateRegistry.contains(key);
    }

    private final LoadingCache<Key, IntPredicate> predicateCache = CacheBuilder.newBuilder()
            .build(new CacheLoader<Key, IntPredicate>() {
                       @Override
                       public IntPredicate load(Key key) throws Exception {
                           return predicateRegistry.get(key).get();
                       }
                   }
            );

    /*
     *  Transformer repository
     *
    private final SupplierRegistry<Key, Function1<String, String>> transformerRegistry = new SupplierRegistry<>();

    private void regTrfmSupplier(Key key, Supplier<Function1<String, String>> trfmSupplier) {
        transformerRegistry.register(key, trfmSupplier);
    }

    private boolean hasTrfmSupplier(Key key) {
        return transformerRegistry.contains(key);
    }

    private final LoadingCache<Key, Function1<String, String>> transformerCache = CacheBuilder.newBuilder()
            .build(new CacheLoader<Key, Function1<String, String>>() {
                       @Override
                       public Function1<String, String> load(Key key) throws Exception {
                           return transformerRegistry.get(key).get();
                       }
                   }
            );
    */
}
