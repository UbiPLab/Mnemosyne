package TimeTest;



import RideRequest.Generate_Q;
import RideResponse.Generate_Q_driver;
import RideResponse.RideResponse_Token;

import java.security.NoSuchAlgorithmException;
import java.util.Random;


import static IBFStructure.IBFConstruction.CreateSecretKey;
import static RideResponse.RideResponse_Token.show;

public class DriverTime {

        public String getDriverResponse() throws Exception {
            System.out.println("Driver Response");
            StringBuffer stringBuffer=new StringBuffer();

            int bitsize =11;
            int[] n ={8,65,127,256,313,564,714,865,911,984};
            double lat_driver =23.9999;
            double lng_driver =96.3967;
            long[] TimeList = new long[20];
//        long startTime = System.nanoTime();
            int[] IDList = new int[20];
            int n3 = 787;
            int sklength = 6;
            String[] skeylist = new String[sklength];
            skeylist = CreateSecretKey(sklength);

            for (int i=0;i<20;i++) {
                long startTime = System.currentTimeMillis();

                Random random = new Random();

                int n2 = random.nextInt(1024);
//
//                String temp = Integer.toBinaryString(n3);
//                System.out.println("temp :"+temp);
                String[][] arr_driver = new Generate_Q_driver().Generate_Q(bitsize,n2,lat_driver,lng_driver);
                String[] keyword = new Generate_Q().StringStringtoString(arr_driver);

                String[][] Token = new RideResponse_Token().Keyword(keyword,skeylist);
                IDList[i] = n2;
//                System.out.println();
//                System.out.println("show Token :");
//                show(Token);
//                System.out.println();
                long endTime = System.currentTimeMillis();
                TimeList[i] = endTime - startTime;     //ms-- s
            }

            System.out.println();
            long totalTime = 0;
            long maxTime =0;
            long minTime =TimeList[0];
            for (int i=0;i< IDList.length;i++){
                System.out.print(" "+IDList[i]);
            }
            System.out.println();
            for (int i=0;i< TimeList.length;i++){
                if (TimeList[i]<minTime){
                    minTime = TimeList[i];
                }
                if (TimeList[i]>maxTime){
                    maxTime = TimeList[i];
                }
                totalTime = totalTime + TimeList[i];
                System.out.print(" "+TimeList[i]);
            }
            System.out.println();
            System.out.println("max time is :"+maxTime);
            System.out.println("min time is "+minTime);
            stringBuffer.append("Time Cost of 20"+" : "+totalTime+" ms ");

            return stringBuffer.toString();
        }
    }

