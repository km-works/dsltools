// ===========================================================================
// This file has been generated by
// Rats! Parser Generator, version 2.4.0,
// (C) 2004-2014 Robert Grimm,
// Do NOT manually edit!
// ===========================================================================

package kmworks.dsltools.parser.test;

import java.io.Reader;
import java.io.IOException;

import xtc.util.Pair;

import xtc.parser.Column;
import xtc.parser.Result;
import xtc.parser.SemanticValue;
import xtc.parser.ParseError;

import java.util.*;
import kmworks.dsltools.parser.base.*;
import kmworks.dsltools.ast.base.*;
import static kmworks.dsltools.parser.base.CodepointUtils.*;

/**
 * Packrat parser for grammar <code>GenericModulesTest</code>.
 *
 * <p>This class has been generated by the <i>Rats!</i> parser
 * generator, version 2.4.0, (C) 2004-2014 Robert Grimm.
 */
public final class GenericModulesTest_Parser extends kmworks.dsltools.parser.base.AbstractParser {

  // =========================================================================

  /** Memoization table column. */
  static final class GenericModulesTest_ParserColumn extends Column {
    Result fNBlockComment;
    Result fHexDigit;
    Result fESC;
    Result fDQ;
  }

  // =========================================================================

  /**
   * Create a new packrat parser.
   *
   * @param reader The reader.
   * @param file The file name.
   */
  public GenericModulesTest_Parser(final Reader reader, final String file) {
    super(reader, file);
  }

  /**
   * Create a new packrat parser.
   *
   * @param reader The file reader.
   * @param file The file name.
   * @param size The file size.
   */
  public GenericModulesTest_Parser(final Reader reader, final String file, final int size) {
    super(reader, file, size);
  }

  // =========================================================================

  protected Column newColumn() {
    return new GenericModulesTest_ParserColumn();
  }

  // =========================================================================

  /**
   * Parse nonterminal GenericModulesTest.Start.
   *
   * @param yyStart The index.
   * @return The result.
   * @throws IOException Signals an I/O error.
   */
  public final Result pStart(final int yyStart) throws IOException {
    Result            yyResult;
    StringLiteralNode yyValue;
    ParseError        yyError = ParseError.DUMMY;

    // Alternative 1.

    yyResult = pw(yyStart);
    yyError  = yyResult.select(yyError);
    if (yyResult.hasValue()) {

      yyResult = pStringLiteral(yyResult.index);
      yyError  = yyResult.select(yyError);
      if (yyResult.hasValue()) {
        String sl = yyResult.semanticValue();

        yyResult = pw(yyResult.index);
        yyError  = yyResult.select(yyError);
        if (yyResult.hasValue()) {

          yyResult = pEOI(yyResult.index);
          yyError  = yyResult.select(yyError);
          if (yyResult.hasValue()) {

            yyValue = factory.pin(yyStart).mkStringLiteral(sl);

            setLocation(yyValue, yyStart);
            return yyResult.createValue(yyValue, yyError);
          }
        }
      }
    }

    // Done.
    return yyError;
  }

  // =========================================================================

  /**
   * Parse nonterminal base.GenericSpacing.w.
   *
   * @param yyStart The index.
   * @return The result.
   * @throws IOException Signals an I/O error.
   */
  private Result pw(final int yyStart) throws IOException {
    Result     yyResult;
    int        yyRepetition1;
    Void       yyValue;
    ParseError yyError = ParseError.DUMMY;

    // Alternative 1.

    yyRepetition1 = yyStart;
    while (true) {

      yyResult = pSpacing(yyRepetition1);
      yyError  = yyResult.select(yyError, yyRepetition1);
      if (yyResult.hasValue()) {

        yyRepetition1 = yyResult.index;
        continue;
      }
      break;
    }

    yyValue = null;

    return new SemanticValue(yyValue, yyRepetition1, yyError);
  }

  // =========================================================================

  /**
   * Parse nonterminal base.GenericSpacing.EOI.
   *
   * @param yyStart The index.
   * @return The result.
   * @throws IOException Signals an I/O error.
   */
  private Result pEOI(final int yyStart) throws IOException {
    int        yyC;
    boolean    yyPredMatched;
    Void       yyValue;
    ParseError yyError = ParseError.DUMMY;

    // Alternative 1.

    yyPredMatched = false;

    yyC = character(yyStart);
    if (-1 != yyC) {

      yyPredMatched = true;
    }

    if (! yyPredMatched) {

      yyValue = null;

      return new SemanticValue(yyValue, yyStart, yyError);
    } else {
      yyError = yyError.select("EOI expected", yyStart);
    }

    // Done.
    return yyError;
  }

  // =========================================================================

  /**
   * Parse nonterminal base.GenericSpacing.Spacing.
   *
   * @param yyStart The index.
   * @return The result.
   * @throws IOException Signals an I/O error.
   */
  private Result pSpacing(final int yyStart) throws IOException {
    int        yyC;
    int        yyIndex;
    Result     yyResult;
    Result     yyPredResult;
    boolean    yyPredMatched;
    int        yyBase;
    int        yyRepetition1;
    Void       yyValue;
    ParseError yyError = ParseError.DUMMY;

    // Alternative 1.

    yyC = character(yyStart);
    if (-1 != yyC) {
      yyIndex = yyStart + 1;
      char c = (char)yyC;

      if (contains(c, WHITESPACE$SYNTAX_MODULE, WHITESPACE_CHARS)) {

        yyValue = null;

        return new SemanticValue(yyValue, yyIndex, yyError);
      }
    }

    // Alternative 2.

    yyC = character(yyStart);
    if ('\r' == yyC) {
      yyIndex = yyStart + 1;

      yyC = character(yyIndex);
      if ('\n' == yyC) {
        yyIndex = yyIndex + 1;

        yyValue = null;

        return new SemanticValue(yyValue, yyIndex, yyError);
      }
    }

    // Alternative 3.

    yyC = character(yyStart);
    if ('\n' == yyC) {
      yyIndex = yyStart + 1;

      yyValue = null;

      return new SemanticValue(yyValue, yyIndex, yyError);
    }

    // Alternative 4.

    yyC = character(yyStart);
    if (-1 != yyC) {
      yyIndex = yyStart + 1;
      char c = (char)yyC;

      if (contains(c, WHITESPACE$SYNTAX_MODULE, OTHER_NEWLINE_CHARS)) {

        yyValue = null;

        return new SemanticValue(yyValue, yyIndex, yyError);
      }
    }

    // Alternative 5.

    yyC = character(yyStart);
    if ('/' == yyC) {
      yyIndex = yyStart + 1;

      yyC = character(yyIndex);
      if ('*' == yyC) {
        yyIndex = yyIndex + 1;

        yyResult = pTBCommentContents(yyIndex);
        yyError  = yyResult.select(yyError);
        if (yyResult.hasValue()) {

          yyBase = yyResult.index;
          yyC    = character(yyBase);
          if ('*' == yyC) {
            yyIndex = yyResult.index + 1;

            yyC = character(yyIndex);
            if ('/' == yyC) {
              yyIndex = yyIndex + 1;

              yyValue = null;

              return new SemanticValue(yyValue, yyIndex, yyError);
            } else {
              yyError = yyError.select("'*/' expected", yyBase);
            }
          } else {
            yyError = yyError.select("'*/' expected", yyBase);
          }
        }
      }
    }

    // Alternative 6.

    yyResult = pNBlockComment(yyStart);
    yyError  = yyResult.select(yyError);
    if (yyResult.hasValue()) {

      yyValue = null;

      return yyResult.createValue(yyValue, yyError);
    }

    // Alternative 7.

    yyC = character(yyStart);
    if ('/' == yyC) {
      yyIndex = yyStart + 1;

      yyC = character(yyIndex);
      if ('/' == yyC) {
        yyIndex = yyIndex + 1;

        yyRepetition1 = yyIndex;
        while (true) {

          yyPredMatched = false;

          yyPredResult = pNewline(yyRepetition1);
          if (yyPredResult.hasValue()) {

            yyPredMatched = true;
          }

          if (! yyPredMatched) {

            yyC = character(yyRepetition1);
            if (-1 != yyC) {
              yyIndex = yyRepetition1 + 1;

              yyRepetition1 = yyIndex;
              continue;
            }
          } else {
            yyError = yyError.select("spacing expected", yyStart);
          }
          break;
        }

        final int yyChoice1 = yyRepetition1;

        // Nested alternative 1.

        yyBase = yyChoice1;
        yyC    = character(yyBase);
        if ('\r' == yyC) {
          yyIndex = yyChoice1 + 1;

          yyC = character(yyIndex);
          if ('\n' == yyC) {
            yyIndex = yyIndex + 1;

            yyValue = null;

            return new SemanticValue(yyValue, yyIndex, yyError);
          } else {
            yyError = yyError.select("'\\r\\n' expected", yyBase);
          }
        } else {
          yyError = yyError.select("'\\r\\n' expected", yyBase);
        }

        // Nested alternative 2.

        yyC = character(yyChoice1);
        if ('\n' == yyC) {
          yyIndex = yyChoice1 + 1;

          yyValue = null;

          return new SemanticValue(yyValue, yyIndex, yyError);
        }

        // Nested alternative 3.

        yyC = character(yyChoice1);
        if (-1 != yyC) {
          yyIndex = yyChoice1 + 1;
          char c = (char)yyC;

          if (contains(c, WHITESPACE$SYNTAX_MODULE, OTHER_NEWLINE_CHARS)) {

            yyValue = null;

            return new SemanticValue(yyValue, yyIndex, yyError);
          }
        }

        // Nested alternative 4.

        yyPredResult = pEOI(yyChoice1);
        yyError      = yyPredResult.select(yyError);
        if (yyPredResult.hasValue()) {

          yyValue = null;

          return new SemanticValue(yyValue, yyChoice1, yyError);
        }
      }
    }

    // Done.
    yyError = yyError.select("spacing expected", yyStart);
    return yyError;
  }

  // =========================================================================

  /**
   * Parse nonterminal base.GenericSpacing.Newline.
   *
   * @param yyStart The index.
   * @return The result.
   * @throws IOException Signals an I/O error.
   */
  private Result pNewline(final int yyStart) throws IOException {
    int        yyC;
    int        yyIndex;
    Void       yyValue;
    ParseError yyError = ParseError.DUMMY;

    // Alternative 1.

    yyC = character(yyStart);
    if ('\r' == yyC) {
      yyIndex = yyStart + 1;

      yyC = character(yyIndex);
      if ('\n' == yyC) {
        yyIndex = yyIndex + 1;

        yyValue = null;

        return new SemanticValue(yyValue, yyIndex, yyError);
      }
    }

    // Alternative 2.

    yyC = character(yyStart);
    if ('\n' == yyC) {
      yyIndex = yyStart + 1;

      yyValue = null;

      return new SemanticValue(yyValue, yyIndex, yyError);
    }

    // Alternative 3.

    yyC = character(yyStart);
    if (-1 != yyC) {
      yyIndex = yyStart + 1;
      char c = (char)yyC;

      if (contains(c, WHITESPACE$SYNTAX_MODULE, OTHER_NEWLINE_CHARS)) {

        yyValue = null;

        return new SemanticValue(yyValue, yyIndex, yyError);
      }
    }

    // Done.
    yyError = yyError.select("newline expected", yyStart);
    return yyError;
  }

  // =========================================================================

  /**
   * Parse nonterminal base.GenericSpacing.TBCommentContents.
   *
   * @param yyStart The index.
   * @return The result.
   * @throws IOException Signals an I/O error.
   */
  private Result pTBCommentContents(final int yyStart) throws IOException {
    Result     yyResult;
    int        yyRepetition1;
    Void       yyValue;
    ParseError yyError = ParseError.DUMMY;

    // Alternative 1.

    yyRepetition1 = yyStart;
    while (true) {

      yyResult = pTBCommentContent(yyRepetition1);
      yyError  = yyResult.select(yyError, yyRepetition1);
      if (yyResult.hasValue()) {

        yyRepetition1 = yyResult.index;
        continue;
      }
      break;
    }

    yyValue = null;

    return new SemanticValue(yyValue, yyRepetition1, yyError);
  }

  // =========================================================================

  /**
   * Parse nonterminal base.GenericSpacing.TBCommentContent.
   *
   * @param yyStart The index.
   * @return The result.
   * @throws IOException Signals an I/O error.
   */
  private Result pTBCommentContent(final int yyStart) throws IOException {
    int        yyC;
    int        yyIndex;
    boolean    yyPredMatched;
    Void       yyValue;
    ParseError yyError = ParseError.DUMMY;

    // Alternative 1.

    yyC = character(yyStart);
    if (-1 != yyC) {
      yyIndex = yyStart + 1;

      switch (yyC) {
      case '*':
        {
          yyPredMatched = false;

          yyC = character(yyIndex);
          if ('/' == yyC) {

            yyPredMatched = true;
          }

          if (! yyPredMatched) {

            yyValue = null;

            return new SemanticValue(yyValue, yyIndex, yyError);
          } else {
            yyError = yyError.select("t b comment content expected", yyStart);
          }
        }
        break;

      default:
        {
          yyValue = null;

          return new SemanticValue(yyValue, yyIndex, yyError);
        }
      }
    }

    // Done.
    yyError = yyError.select("t b comment content expected", yyStart);
    return yyError;
  }

  // =========================================================================

  /**
   * Parse nonterminal base.GenericSpacing.NBlockComment.
   *
   * @param yyStart The index.
   * @return The result.
   * @throws IOException Signals an I/O error.
   */
  private Result pNBlockComment(final int yyStart) throws IOException {
    GenericModulesTest_ParserColumn yyColumn = (GenericModulesTest_ParserColumn)column(yyStart);
    if (null == yyColumn.fNBlockComment) 
      yyColumn.fNBlockComment = pNBlockComment$1(yyStart);
    return yyColumn.fNBlockComment;
  }

  /** Actually parse base.GenericSpacing.NBlockComment. */
  private Result pNBlockComment$1(final int yyStart) throws IOException {
    int        yyC;
    int        yyIndex;
    Result     yyResult;
    int        yyBase;
    Void       yyValue;
    ParseError yyError = ParseError.DUMMY;

    // Alternative 1.

    yyC = character(yyStart);
    if ('(' == yyC) {
      yyIndex = yyStart + 1;

      yyC = character(yyIndex);
      if ('*' == yyC) {
        yyIndex = yyIndex + 1;

        yyResult = pNBCommentContents(yyIndex);
        yyError  = yyResult.select(yyError);
        if (yyResult.hasValue()) {

          yyBase = yyResult.index;
          yyC    = character(yyBase);
          if ('*' == yyC) {
            yyIndex = yyResult.index + 1;

            yyC = character(yyIndex);
            if (')' == yyC) {
              yyIndex = yyIndex + 1;

              yyValue = null;

              return new SemanticValue(yyValue, yyIndex, yyError);
            } else {
              yyError = yyError.select("'*)' expected", yyBase);
            }
          } else {
            yyError = yyError.select("'*)' expected", yyBase);
          }
        }
      }
    }

    // Done.
    yyError = yyError.select("n block comment expected", yyStart);
    return yyError;
  }

  // =========================================================================

  /**
   * Parse nonterminal base.GenericSpacing.NBCommentContents.
   *
   * @param yyStart The index.
   * @return The result.
   * @throws IOException Signals an I/O error.
   */
  private Result pNBCommentContents(final int yyStart) throws IOException {
    Result     yyResult;
    int        yyRepetition1;
    Void       yyValue;
    ParseError yyError = ParseError.DUMMY;

    // Alternative 1.

    yyRepetition1 = yyStart;
    while (true) {

      yyResult = pNBCommentContent(yyRepetition1);
      yyError  = yyResult.select(yyError, yyRepetition1);
      if (yyResult.hasValue()) {

        yyRepetition1 = yyResult.index;
        continue;
      }
      break;
    }

    yyValue = null;

    return new SemanticValue(yyValue, yyRepetition1, yyError);
  }

  // =========================================================================

  /**
   * Parse nonterminal base.GenericSpacing.NBCommentContent.
   *
   * @param yyStart The index.
   * @return The result.
   * @throws IOException Signals an I/O error.
   */
  private Result pNBCommentContent(final int yyStart) throws IOException {
    int        yyC;
    int        yyIndex;
    Result     yyResult;
    boolean    yyPredMatched;
    Void       yyValue;
    ParseError yyError = ParseError.DUMMY;

    // Alternative 1.

    yyC = character(yyStart);
    if ('(' == yyC) {
      yyIndex = yyStart + 1;

      yyC = character(yyIndex);
      if ('*' == yyC) {
        yyIndex = yyIndex + 1;

        yyC = character(yyIndex);
        if (')' == yyC) {
          yyIndex = yyIndex + 1;

          yyValue = null;

          return new SemanticValue(yyValue, yyIndex, yyError);
        }
      }
    }

    // Alternative 2.

    yyResult = pNBlockComment(yyStart);
    yyError  = yyResult.select(yyError);
    if (yyResult.hasValue()) {

      yyValue = null;

      return yyResult.createValue(yyValue, yyError);
    }

    // Alternative 3.

    yyC = character(yyStart);
    if (-1 != yyC) {
      yyIndex = yyStart + 1;

      switch (yyC) {
      case '*':
        {
          yyPredMatched = false;

          yyC = character(yyIndex);
          if (')' == yyC) {

            yyPredMatched = true;
          }

          if (! yyPredMatched) {

            yyValue = null;

            return new SemanticValue(yyValue, yyIndex, yyError);
          } else {
            yyError = yyError.select("n b comment content expected", yyStart);
          }
        }
        break;

      default:
        {
          yyValue = null;

          return new SemanticValue(yyValue, yyIndex, yyError);
        }
      }
    }

    // Done.
    yyError = yyError.select("n b comment content expected", yyStart);
    return yyError;
  }

  // =========================================================================

  /**
   * Parse nonterminal base.GenericStringLiteral.StringLiteral.
   *
   * @param yyStart The index.
   * @return The result.
   * @throws IOException Signals an I/O error.
   */
  private Result pStringLiteral(final int yyStart) throws IOException {
    Result          yyResult;
    int             yyRepetition1;
    Pair<Character> yyRepValue1;
    String          yyValue;
    ParseError      yyError = ParseError.DUMMY;

    // Alternative 1.

    yyResult = pDQ(yyStart);
    yyError  = yyResult.select(yyError);
    if (yyResult.hasValue()) {

      yyRepetition1 = yyResult.index;
      yyRepValue1   = Pair.empty();
      while (true) {

        yyResult = pStringLiteral$$Choice1(yyRepetition1);
        yyError  = yyResult.select(yyError, yyRepetition1);
        if (yyResult.hasValue()) {
          Character v$el$1 = yyResult.semanticValue();

          yyRepetition1 = yyResult.index;
          yyRepValue1   = new Pair<Character>(v$el$1, yyRepValue1);
          continue;
        }
        break;
      }
      { // Start scope for cl.
        Pair<Character> cl = yyRepValue1.reverse();

        yyResult = pDQ(yyRepetition1);
        yyError  = yyResult.select(yyError);
        if (yyResult.hasValue()) {

          yyValue = asString(cl);

          return yyResult.createValue(yyValue, yyError);
        }
      } // End scope for cl.
    }

    // Done.
    return yyError;
  }

  // =========================================================================

  /**
   * Parse synthetic nonterminal GenericModulesTest.StringLiteral$$Choice1.
   *
   * @param yyStart The index.
   * @return The result.
   * @throws IOException Signals an I/O error.
   */
  private Result pStringLiteral$$Choice1(final int yyStart) 
    throws IOException {

    Result     yyResult;
    Result     yyPredResult;
    boolean    yyPredMatched;
    Character  yyValue;
    ParseError yyError = ParseError.DUMMY;

    // Alternative 1.

    yyResult = pEscapeSequence(yyStart);
    yyError  = yyResult.select(yyError);
    if (yyResult.hasValue()) {
      yyValue = yyResult.semanticValue();

      return yyResult.createValue(yyValue, yyError);
    }

    // Alternative 2.

    yyPredMatched = false;

    yyPredResult = pDQ(yyStart);
    if (yyPredResult.hasValue()) {

      yyPredMatched = true;
    }

    if (! yyPredMatched) {

      yyResult = pStringContent(yyStart);
      yyError  = yyResult.select(yyError);
      if (yyResult.hasValue()) {
        yyValue = yyResult.semanticValue();

        return yyResult.createValue(yyValue, yyError);
      }
    } else {
      yyError = yyError.select("string literal expected", yyStart);
    }

    // Done.
    return yyError;
  }

  // =========================================================================

  /**
   * Parse nonterminal base.GenericStringLiteral.EscapeSequence.
   *
   * @param yyStart The index.
   * @return The result.
   * @throws IOException Signals an I/O error.
   */
  private Result pEscapeSequence(final int yyStart) throws IOException {
    Result     yyResult;
    Character  yyValue;
    ParseError yyError = ParseError.DUMMY;

    // Alternative 1.

    yyResult = pCharEscape(yyStart);
    yyError  = yyResult.select(yyError);
    if (yyResult.hasValue()) {
      yyValue = yyResult.semanticValue();

      return yyResult.createValue(yyValue, yyError);
    }

    // Alternative 2.

    yyResult = pUnicodeEscape(yyStart);
    yyError  = yyResult.select(yyError);
    if (yyResult.hasValue()) {
      yyValue = yyResult.semanticValue();

      return yyResult.createValue(yyValue, yyError);
    }

    // Done.
    return yyError;
  }

  // =========================================================================

  /**
   * Parse nonterminal base.GenericStringLiteral.StringContent.
   *
   * @param yyStart The index.
   * @return The result.
   * @throws IOException Signals an I/O error.
   */
  private Result pStringContent(final int yyStart) throws IOException {
    int        yyC;
    int        yyIndex;
    Character  yyValue;
    ParseError yyError = ParseError.DUMMY;

    // Alternative 1.

    yyC = character(yyStart);
    if (-1 != yyC) {
      yyIndex = yyStart + 1;
      yyValue = Character.valueOf((char)yyC);

      if (!isStringLiteralInvalidChar(yyValue)) {

        return new SemanticValue(yyValue, yyIndex, yyError);
      }
    }

    // Done.
    yyError = yyError.select("string content expected", yyStart);
    return yyError;
  }

  // =========================================================================

  /**
   * Parse nonterminal base.GenericStringLiteral.UnicodeEscape.
   *
   * @param yyStart The index.
   * @return The result.
   * @throws IOException Signals an I/O error.
   */
  private Result pUnicodeEscape(final int yyStart) throws IOException {
    int        yyC;
    int        yyIndex;
    Result     yyResult;
    Character  yyValue;
    ParseError yyError = ParseError.DUMMY;

    // Alternative 1.

    yyResult = pESC(yyStart);
    yyError  = yyResult.select(yyError);
    if (yyResult.hasValue()) {

      yyC = character(yyResult.index);
      if ('u' == yyC) {
        yyIndex = yyResult.index + 1;

        yyResult = pHexQuad(yyIndex);
        yyError  = yyResult.select(yyError);
        if (yyResult.hasValue()) {
          String hq = yyResult.semanticValue();

          yyValue = (char) Integer.parseInt(hq, 16);

          return yyResult.createValue(yyValue, yyError);
        }
      }
    }

    // Done.
    yyError = yyError.select("unicode escape expected", yyStart);
    return yyError;
  }

  // =========================================================================

  /**
   * Parse nonterminal base.GenericStringLiteral.CharEscape.
   *
   * @param yyStart The index.
   * @return The result.
   * @throws IOException Signals an I/O error.
   */
  private Result pCharEscape(final int yyStart) throws IOException {
    int        yyC;
    int        yyIndex;
    Result     yyResult;
    Character  yyValue;
    ParseError yyError = ParseError.DUMMY;

    // Alternative 1.

    yyResult = pESC(yyStart);
    yyError  = yyResult.select(yyError);
    if (yyResult.hasValue()) {

      yyC = character(yyResult.index);
      if (-1 != yyC) {
        yyIndex = yyResult.index + 1;
        char c = (char)yyC;

        if (isStringLiteralEscapedChar(c)) {

          yyValue = unescapeStringLiteral("\\" + c).charAt(0);

          return new SemanticValue(yyValue, yyIndex, yyError);
        }
      }
    }

    // Done.
    yyError = yyError.select("char escape expected", yyStart);
    return yyError;
  }

  // =========================================================================

  /**
   * Parse nonterminal base.GenericStringLiteral.HexQuad.
   *
   * @param yyStart The index.
   * @return The result.
   * @throws IOException Signals an I/O error.
   */
  private Result pHexQuad(final int yyStart) throws IOException {
    Result     yyResult;
    String     yyValue;
    ParseError yyError = ParseError.DUMMY;

    // Alternative 1.

    yyResult = pHexDigit(yyStart);
    yyError  = yyResult.select(yyError);
    if (yyResult.hasValue()) {
      Character h1 = yyResult.semanticValue();

      yyResult = pHexDigit(yyResult.index);
      yyError  = yyResult.select(yyError);
      if (yyResult.hasValue()) {
        Character h2 = yyResult.semanticValue();

        yyResult = pHexDigit(yyResult.index);
        yyError  = yyResult.select(yyError);
        if (yyResult.hasValue()) {
          Character h3 = yyResult.semanticValue();

          yyResult = pHexDigit(yyResult.index);
          yyError  = yyResult.select(yyError);
          if (yyResult.hasValue()) {
            Character h4 = yyResult.semanticValue();

            yyValue = asString(h1, h2, h3, h4);

            return yyResult.createValue(yyValue, yyError);
          }
        }
      }
    }

    // Done.
    return yyError;
  }

  // =========================================================================

  /**
   * Parse nonterminal base.GenericStringLiteral.HexDigit.
   *
   * @param yyStart The index.
   * @return The result.
   * @throws IOException Signals an I/O error.
   */
  private Result pHexDigit(final int yyStart) throws IOException {
    GenericModulesTest_ParserColumn yyColumn = (GenericModulesTest_ParserColumn)column(yyStart);
    if (null == yyColumn.fHexDigit) yyColumn.fHexDigit = pHexDigit$1(yyStart);
    return yyColumn.fHexDigit;
  }

  /** Actually parse base.GenericStringLiteral.HexDigit. */
  private Result pHexDigit$1(final int yyStart) throws IOException {
    int        yyC;
    int        yyIndex;
    Character  yyValue;
    ParseError yyError = ParseError.DUMMY;

    // Alternative 1.

    yyC = character(yyStart);
    if (-1 != yyC) {
      yyIndex = yyStart + 1;
      yyValue = Character.valueOf((char)yyC);

      if (isHexDigit(yyValue)) {

        return new SemanticValue(yyValue, yyIndex, yyError);
      }
    }

    // Done.
    yyError = yyError.select("hex digit expected", yyStart);
    return yyError;
  }

  // =========================================================================

  /**
   * Parse nonterminal base.GenericStringLiteral.ESC.
   *
   * @param yyStart The index.
   * @return The result.
   * @throws IOException Signals an I/O error.
   */
  private Result pESC(final int yyStart) throws IOException {
    GenericModulesTest_ParserColumn yyColumn = (GenericModulesTest_ParserColumn)column(yyStart);
    if (null == yyColumn.fESC) yyColumn.fESC = pESC$1(yyStart);
    return yyColumn.fESC;
  }

  /** Actually parse base.GenericStringLiteral.ESC. */
  private Result pESC$1(final int yyStart) throws IOException {
    int        yyC;
    int        yyIndex;
    Void       yyValue;
    ParseError yyError = ParseError.DUMMY;

    // Alternative 1.

    yyC = character(yyStart);
    if ('\\' == yyC) {
      yyIndex = yyStart + 1;

      yyValue = null;

      return new SemanticValue(yyValue, yyIndex, yyError);
    }

    // Done.
    yyError = yyError.select("ESC expected", yyStart);
    return yyError;
  }

  // =========================================================================

  /**
   * Parse nonterminal base.GenericStringLiteral.DQ.
   *
   * @param yyStart The index.
   * @return The result.
   * @throws IOException Signals an I/O error.
   */
  private Result pDQ(final int yyStart) throws IOException {
    GenericModulesTest_ParserColumn yyColumn = (GenericModulesTest_ParserColumn)column(yyStart);
    if (null == yyColumn.fDQ) yyColumn.fDQ = pDQ$1(yyStart);
    return yyColumn.fDQ;
  }

  /** Actually parse base.GenericStringLiteral.DQ. */
  private Result pDQ$1(final int yyStart) throws IOException {
    int        yyC;
    int        yyIndex;
    Void       yyValue;
    ParseError yyError = ParseError.DUMMY;

    // Alternative 1.

    yyC = character(yyStart);
    if ('\"' == yyC) {
      yyIndex = yyStart + 1;

      yyValue = null;

      return new SemanticValue(yyValue, yyIndex, yyError);
    }

    // Done.
    yyError = yyError.select("DQ expected", yyStart);
    return yyError;
  }

  // =========================================================================

  private NodeFactory factory = new NodeFactory(this);

}
