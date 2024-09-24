package com.putileaf.healthify.validation;

import com.putileaf.healthify.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;



public class StateValidation implements ConstraintValidator<State,String> {
    //提供校验规则value是要校验的数据，返回false校验不通过
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value==null){
            return false;
        }
        if (value.equals("已发布")||value.equals("草稿")){
            return true;
        }
        return false;
    }
}
