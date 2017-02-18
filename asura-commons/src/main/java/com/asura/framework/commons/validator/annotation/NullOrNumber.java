/**
 * @FileName: NullOrNumber.java
 * @Package: com.asura.framework.commons.validator.annotation
 * @author sence
 * @created 11/18/2015 6:25 PM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asura.framework.commons.validator.annotation;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.lang.annotation.*;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sence
 * @version 1.0
 * @since 1.0
 */
@ConstraintComposition(CompositionType.OR)
@Null(message = "")
@Pattern(regexp = "[1-9]\\d*")
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@ReportAsSingleViolation
@Documented
public @interface NullOrNumber {
    String message() default "Validation for an optional numeric field failed.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
