package cn.qqtextwar.tests;

import cn.qqtextwar.utils.JavaUtils;

import java.util.Random;

public class Test11 {

    public static void main(String[] args) {
        Integer[][] arr = new Integer[1000][1000];

        Random random = new Random();
        for(int i = 0;i<arr.length;i++){
            for(int j = 0;j<arr[i].length;j++){
                arr[i][j] = random.nextInt(1000000);
            }
        }

        long l = System.currentTimeMillis();

        JavaUtils.verticalFlip(arr);

        System.out.println("TIME: "+ (System.currentTimeMillis() - l));


//        for(int i = 0;i<arr.length;i++) {
//            System.out.println(Arrays.toString(arr[i]));
//        }



    }


}
