package kmworks.dsltools.parser.adt;

import com.google.common.base.Optional;
import kmworks.dsltools.adt.base.Multiplicity;
import javax.annotation.Nullable;
import kmworks.dsltools.adt.adt.*;
import kmworks.dsltools.adt.base.*;
import xtc.util.Pair;

/**
 * Created by cpl on 22.03.2017.
 */
public final class NodeFactory {

    private NodeFactory() {}
    
    public static Grammar mkGrammar(Pair<String> pkgName, ADT adt) {
        return GrammarImpl.of(Seq.of(pkgName), adt);
    }

    public static ADT mkADT(String typeName, Pair<TypeDef> typeDefs, Pair<TypeDef> auxDefs) {
        return ADTImpl.of(
                typeName, 
                Seq.of(typeDefs),
                Seq.of(auxDefs)
        );
    }

    public static TypeDef mkTypeDef(String typeName, @Nullable String kind, Pair<Parameter> parameters, Pair<TypeDef> typeDefs) {
        return TypeDefImpl.of(
                typeName, 
                Optional.fromNullable(kind), 
                Seq.of(parameters), 
                Seq.of(typeDefs));
    }

    public static Parameter mkParameter(String name, TypeRef typeRef) {
        return ParameterImpl.of(name, typeRef);
    }

    public static TypeRef mkTypeRef(String name, @Nullable Character multi) {
        if (multi == null) {
            return TypeRefImpl.of(name, Optional.fromNullable(null));
        }
        switch (multi) {
            case '?':
                return TypeRefImpl.of(name, Optional.of(Multiplicity.ZeroOrOne));
            case '*':
                return TypeRefImpl.of(name, Optional.of(Multiplicity.ZeroOrMore));
            case '+':
                return TypeRefImpl.of(name, Optional.of(Multiplicity.OneOrMore));
            default:
                throw new IllegalArgumentException("Illegal multiplicity char: " + multi);
        }
    }

}
