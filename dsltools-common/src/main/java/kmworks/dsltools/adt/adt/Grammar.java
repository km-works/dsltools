/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmworks.dsltools.adt.adt;

import kmworks.dsltools.adt.base.AbstractRoot;
import kmworks.dsltools.adt.base.Seq;
import org.immutables.value.Value;

/**
 *
 * @author Christian P. Lerch
 */
@Value.Immutable
public abstract class Grammar extends AbstractRoot {
    @Value.Parameter public abstract Seq<String> pkgName();
    @Value.Parameter public abstract ADT adt();
}
