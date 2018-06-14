package kmworks.dsltools.ast.ml;

/**
 * Created by cpl on 18.03.2017.
 */
public final class ADTNodeWalkerImpl implements ADTNodeWalker {

    private final ADTNodeVisitor visit;

    public ADTNodeWalkerImpl(ADTNodeVisitor visitor) {
        this.visit = visitor;
    }
    
    // walk concrete classes

    @Override
    public void walkGrammar(Grammar node) {
        visit.visitGrammar(node);
        node.productions().list().stream().forEach(n -> walkProduction(n));
        visit.leaveGrammar(node);
    }

    @Override
    public void walkProduction(Production node) {
        visit.visitProduction(node);
        walkChoice(node.choice());
        visit.leaveProduction(node);
    }

    @Override
    public void walkSequence(Sequence node) {
        visit.visitSequence(node);
        node.terms().list().stream().forEach(n -> walkTerm(n));
        visit.leaveSequence(node);
    }

    @Override
    public void walkPredicate(Predicate node) {
        visit.visitPredicate(node);
        walkPredicable(node.arg());
        visit.leavePredicate(node);
    }

    @Override
    public void walkMultiple(Multiple node) {
        visit.visitMultiple(node);
        walkPrimary(node.primary());
        visit.leaveMultiple(node);
    }

    @Override
    public void walkChoice(Choice node) {
        visit.visitChoice(node);
        node.sequences().list().stream().forEach(n -> walkSequence(n));
        visit.leaveChoice(node);
    }

    @Override
    public void walkNonTerminal(NonTerminal node) {
        visit.visitNonTerminal(node);
        visit.leaveNonTerminal(node);
    }

    @Override
    public void walkAnyChar(AnyChar node) {
        visit.visitAnyChar(node);
        visit.leaveAnyChar(node);
    }
    
    @Override
    public void walkLiteral(Literal node) {
        visit.visitLiteral(node);
        visit.leaveLiteral(node);
    }

    @Override
    public void walkCharClass(CharClass node) {
        visit.visitCharClass(node);
        node.ranges().list().stream().forEach(n -> walkCharRange(n));
        visit.leaveCharClass(node);
    }

    @Override
    public void walkCharRange(CharRange node) {
        visit.visitCharRange(node);
        visit.leaveCharRange(node);
    }

    // walk abstract classes

    @Override
    public void walkTerm(Term node) {
        if (node instanceof Predicate) {
            walkPredicate((Predicate) node);
        } else if (node instanceof Predicable) {
            walkPredicable((Predicable)node);
        } else {
            throw new IllegalStateException();
        }
    }
    
    @Override
    public void walkPredicable(Predicable node) {
        if (node instanceof Multiple) {
            walkMultiple((Multiple) node);
        } else if (node instanceof Primary) {
            walkPrimary((Primary) node);
        } else {
            throw new IllegalStateException();
        }
    }
    
    @Override
    public void walkPrimary(Primary node) {
        if (node instanceof Choice) {
            walkChoice((Choice) node);
        } else if (node instanceof NonTerminal) {
            walkNonTerminal((NonTerminal) node);
        } else if (node instanceof Terminal) {
            walkTerminal((Terminal) node);
        } else {
            throw new IllegalStateException();
        }
    }
    
    @Override
    public void walkTerminal(Terminal node) {
        if (node instanceof AnyChar) {
            walkAnyChar((AnyChar) node);
        } else if (node instanceof Literal) {
            walkLiteral((Literal) node);
        } else if (node instanceof CharClass) {
            walkCharClass((CharClass) node);
        } else {
            throw new IllegalStateException();
        }
    }

}
