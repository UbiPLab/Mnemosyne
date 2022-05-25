package TimeTest;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class TestRider {
    public static void main(String[] args) throws Exception {
//        String RiderTime;
//        RiderTime = new RiderTime().getRiderRequest();
//        System.out.println(RiderTime);
        int test_time = 100;
        long[] RHSPTime3 = new long[test_time];
        long count_min = 0;
        long count_max = 0;
        for (int i = 0; i < test_time; i++) {
            Map<Integer,Long> map = new HashMap<>();
            map = new RiderTime_add().getRHSPMatching_Normal();
            long min = map.get(0);
            long max = map.get(1);
            System.out.println(min+" "+ max +" "+i);
            count_min = count_min + min;
            count_max = count_max + max;
        }
        System.out.println();
//        ShowOne(RHSPTime);
//        ShowOne(RHSPTime2);
//        ShowOne(RHSPTime3);
        System.out.println("total: "+count_min+","+count_max);
    }
    public static void ShowOne(long[] arr){
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]+" ");
        }
        System.out.println();
    }
}
