package com.putileaf.healthify.utils;



import org.springframework.stereotype.Component;

@Component
public class FourCode {


    //生成四位随机数
    public static String getCode(){
        String code = "";
        for (int i = 0; i < 4; i++) {
            int num = (int)(Math.random()*10);
            code += num;
        }
        return code;

    }





}
