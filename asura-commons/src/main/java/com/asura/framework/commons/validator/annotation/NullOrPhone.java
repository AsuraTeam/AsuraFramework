/**
 * @FileName: NullOrPhone.java
 * @Package: com.asura.framework.commons.validator.annotation
 * @author sence
 * @created 11/18/2015 6:16 PM
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
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <p></p>
 * <p/>
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
@Null
@Pattern(regexp = "^1[34578]\\d{9}$")
@ReportAsSingleViolation
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
public @interface NullOrPhone {
    String message() default "{sms.NullOrNotBlank.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
