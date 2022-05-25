package TimeTest;

import java.security.NoSuchAlgorithmException;

public class TestALL {
    public static void main(String[] args) throws Exception {
//        String RiderTime;
//        RiderTime = new RiderTime().getRiderRequest();
//        System.out.println(RiderTime);
//        String DriverTime;
//        DriverTime = new DriverTime().getDriverResponse();
//        System.out.println(DriverTime);
//        String[] RHSPTime = new String[5];
//        String[] RHSPTime2 = new String[5];
        int test_time = 100;
        long[] RHSPTime3 = new long[test_time];
        long count = 0;
        for (int i = 0; i < test_time; i++) {

//            RHSPTime[i] = new RHSPTime().getRHSPMatching_OneOnOne();
//            System.out.println(RHSPTime[i]+" "+i);
//
//            RHSPTime2[i] = new RHSPTime().getRHSPMatching_Best();
//            System.out.println(RHSPTime2[i]+" "+i);

            RHSPTime3[i] = new RHSPTime_add().getRHSPMatching_Normal();
            System.out.println(RHSPTime3[i]+" "+i);
            count = count + RHSPTime3[i];

        }
        System.out.println();
//        ShowOne(RHSPTime);
//        ShowOne(RHSPTime2);
        ShowOne(RHSPTime3);
        System.out.println("total: "+count);
    }
    public static void ShowOne(long[] arr){
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]+" ");
        }
        System.out.println();
    }
}
