package ProxyPattern.jdk;

import java.util.Arrays;

class Test {
    private int args1;
    private int args2;
    public Test(int args1,int args2){
        this.args1 = args1;
        this.args2 = args2;
    }
    public Test(int args2){

    }

    public static void main(String[] args) {
        Class<Test> clazz = Test.class;
        System.out.println("clazz.getConstructors() = " + Arrays.toString(clazz.getConstructors()));
    }

}
