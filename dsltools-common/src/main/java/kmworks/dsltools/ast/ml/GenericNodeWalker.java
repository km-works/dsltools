package kmworks.dsltools.ast.ml;

/**
 * Created by cpl on 01.04.2017.
 */
public interface GenericNodeWalker<T> {

    void walkGrammar(T node);

    void walkProduction(T node);

    void walkSequence(T node);

    void walkTerm(T node);

    void walkPredicate(T node);
    
    void walkPredicable(T node);

    void walkMultiple(T node);

    void walkPrimary(T node);

    void walkChoice(T node);

    void walkNonTerminal(T node);

    void walkTerminal(T node);

    void walkAnyChar(T node);

    void walkLiteral(T node);

    void walkCharClass(T node);

    void walkCharRange(T node);

}
