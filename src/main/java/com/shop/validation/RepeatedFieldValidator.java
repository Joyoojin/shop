/*
 *    Copyright 2016-2019 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.shop.validation;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * ( 회원가입 시 비밀번호 재입력)
 */

public class RepeatedFieldValidator implements ConstraintValidator<RepeatedField, Object> {

    private String field;
    private String repeatedField;
    private String message;

    @Override
    public void initialize(RepeatedField constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.repeatedField = "repeated" + StringUtils.capitalize(field);
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {        // 재입력값 일치하는지 유효성 체크
        BeanWrapper beanWrapper = new BeanWrapperImpl(value);
        Object fieldValue = beanWrapper.getPropertyValue(field);
        Object repeatedFieldValue = beanWrapper.getPropertyValue(repeatedField);
        boolean matched = Objects.equals(fieldValue, repeatedFieldValue);
        if (matched) {                                                        // 일치할경우 true
            return true;
        } else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(repeatedField).addConstraintViolation();
            return false;                                                    // 불일치하면 false
        }
    }
}
