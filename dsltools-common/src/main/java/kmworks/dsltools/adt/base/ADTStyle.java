/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmworks.dsltools.adt.base;

import org.immutables.value.Value;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
/**
 *
 * @author Christian P. Lerch
 */
@Target({ElementType.PACKAGE, ElementType.TYPE})
@Retention(RetentionPolicy.CLASS) // Make it class retention for incremental compilation
@Value.Style(
    typeAbstract = {"*"}, // 'Abstract' sufix will be detected and trimmed
    typeImmutable = "*Impl", // No prefix or suffix for generated immutable type
    visibility = Value.Style.ImplementationVisibility.PUBLIC, // Generated class will be always public
    defaults = @Value.Immutable(builder = false, copy = false)) // Disable builders and copy methods by default
public @interface ADTStyle {}
