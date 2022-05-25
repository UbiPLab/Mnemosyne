package Test;

import java.util.Arrays;

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
        long[][] RHSPTime3 = new long[test_time][3];
        /*
        RHSPTime3[] 为一组，RHSPTime3[i][0] rider_time ,RHSPTime3[i][1] driver_time, RHSPTime3[i][2] RHSP_time
         */
        long[] count = new long[3];
        Arrays.fill(count,0);
        for (int i = 0; i < test_time; i++) {

//            RHSPTime[i] = new RHSPTime().getRHSPMatching_OneOnOne();
//            System.out.println(RHSPTime[i]+" "+i);
//
//            RHSPTime2[i] = new RHSPTime().getRHSPMatching_Best();
//            System.out.println(RHSPTime2[i]+" "+i);

            RHSPTime3[i] = new RHSPTime_add().getRHSPMatching_Normal();
            System.out.println("No."+i+Arrays.toString(RHSPTime3[i]));
            count[0] = count[0] + RHSPTime3[i][0];
            count[1] = count[1] + RHSPTime3[i][1];
            count[2] = count[2] + RHSPTime3[i][2];

        }
        System.out.println();
//        ShowOne(RHSPTime);
//        ShowOne(RHSPTime2);
        System.out.println("Rider_time:");
//        ShowOne(RHSPTime3[0]);
        System.out.println(count[0]);
        System.out.println("Driver_time:");
//        ShowOne(RHSPTime3[1]);
        System.out.println(count[1]);
        System.out.println("RHSP_time:");
//        ShowOne(RHSPTime3[2]);
        System.out.println(count[2]);
//        System.out.println("total: "+count);
    }
    public static void ShowOne(long[] arr){
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]+" ");
        }
        System.out.println();
    }
}
