package com.ccll.projectse_ifttt.TestUtilsClasses;

public class TestUtils {
    public static void waitCheckRule(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void waitCheckRule(int ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
