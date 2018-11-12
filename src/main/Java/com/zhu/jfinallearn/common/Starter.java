package com.zhu.jfinallearn.common;

import com.jfinal.core.JFinal;
/*
* Jfinal 启动类，在此启动jfinal
* */
public class Starter {
    public static void main(String[] args) {
        JFinal.start("src/main/webapp",80,"/",5);
    }
}
