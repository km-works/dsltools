package kmworks.dsltools.adt.ml;

/**
 * Created by cpl on 01.04.2017.
 * @param <T> node type of homogeneous AST
 */
public interface GenericNodeVisitor<T> {

    void visitGrammar(T node);
    void leaveGrammar(T node);

    void visitProduction(T node);
    void leaveProduction(T node);

    void visitSequence(T node);
    void leaveSequence(T node);

    void visitPredicate(T node);
    void leavePredicate(T node);

    void visitMultiple(T node);
    void leaveMultiple(T node);

    void visitChoice(T node);
    void leaveChoice(T node);

    void visitNonTerminal(T node);
    void leaveNonTerminal(T node);

    void visitAnyChar(T node);
    void leaveAnyChar(T node);

    void visitLiteral(T node);
    void leaveLiteral(T node);

    void visitCharClass(T node);
    void leaveCharClass(T node);

    void visitCharRange(T node);
    void leaveCharRange(T node);

}
