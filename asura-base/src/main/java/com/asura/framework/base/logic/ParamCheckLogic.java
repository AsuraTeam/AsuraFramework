/**
 * @FileName: ParamCheckLogic.java
 * @Package: com.asura.framework.base.logic
 * @author sence
 * @created 7/9/2015 7:59 PM
 * <p/>
 * Copyright 2017 Asura
 */
package com.asura.framework.base.logic;

import com.asura.framework.base.exception.ValidatorException;
import com.asura.framework.base.util.BusinessAssert;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.commons.json.Json;
import com.asura.framework.commons.util.Check;
import org.springframework.context.MessageSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.Iterator;
import java.util.Set;

/**
 * <p>
 *    结合JSR303 Bean Validator规范实现，对bean进行validator
 *    支持 1、json 反序列化对象后校验
 *        2、对象validator校验
 * </p>
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
public class ParamCheckLogic {

    private MessageSource messageSource;

    private Validator validator;

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    /**
     * 校验参数，并依据validator校验
     *
     * @param jsonStr
     * @param clazz
     * @return
     */
    public <T> T checkParamValidate(String jsonStr, Class<T> clazz) {
        return checkParamValidate(jsonStr, clazz, null);
    }

    /**
     * 校验参数，并依据validator校验
     *
     * @param jsonStr
     * @param clazz
     * @param groups
     * @param <T>
     * @return
     */
    public <T> T checkParamValidate(@NotNull String jsonStr, Class<T> clazz, Class<?>... groups) {
        BusinessAssert.requireNonEmpty(jsonStr, MessageSourceUtil.getChinese(messageSource, "param.null"));
        T t = Json.parseObject(jsonStr, clazz);
        //valiator校验
        checkObjParamValidate(t, groups);
        return t;
    }

    /**
     * 对象校验，依据validator
     * use case
     * 派单前做的工单校验
     *
     * @param t
     * @param <T>
     * @return
     */
    public <T> T checkObjParamValidate(@NotNull T t) {
        return checkObjParamValidate(t, null);
    }

    /**
     * 对象校验，依据validator
     * use case
     * 派单前做的工单校验
     *
     * @param t
     * @param <T>
     * @return
     */
    public <T> T checkObjParamValidate(@NotNull T t, Class<?>... groups) {
        BusinessAssert.requireNonEmpty(t, MessageSourceUtil.getChinese(messageSource, "param.null"));
        //是否符合validator校验
        StringBuilder errorMsg = new StringBuilder();
        Set<ConstraintViolation<T>> constraintViolationSet = null;
        if (!Check.isNullObjects(groups)) {
            constraintViolationSet = validator.validate(t, groups);
        } else {
            constraintViolationSet = validator.validate(t);
        }
        if (!Check.isNullOrEmpty(constraintViolationSet)) {
            Iterator<ConstraintViolation<T>> iterator = constraintViolationSet.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation<T> constraint = iterator.next();
                errorMsg.append(constraint.getMessage()).append(",");
            }
            //设置错误信息
            if (errorMsg.length() != 0) {
                errorMsg.deleteCharAt(errorMsg.length() - 1);
                throw new ValidatorException(errorMsg.toString());
            }
        }
        return t;
    }
}
