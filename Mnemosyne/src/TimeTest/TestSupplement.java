package TimeTest;

import IBFStructure.IBtreeConstruction;
import QueryProcessing.QueryProcessing;
import RideRequest.Generate_Q;
import ReadFileData.ReadFiledata;
import RideResponse.Generate_Q_driver;
import RideResponse.RideResponse_Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static IBFStructure.IBFConstruction.CreateSecretKey;

public class TestSupplement {
    public static void main(String[] args) throws Exception {
        //  导入文件
        String address_used = "C:\\Users\\UbiP Lab Laptop 02\\Desktop\\Me\\100.txt";
        double[][] File = ReadFiledata.readArray(address_used);

        int test_time = 500;
        long[] RHSPTime = new long[test_time];
        long count = 0;
        List<Long> max_rider = new ArrayList<>();
        List<Long> min_rider = new ArrayList<>();
        List<Long> all_rider = new ArrayList<>();

        List<Integer> count_rider = new ArrayList<>();
        for (int i = 0; i < test_time; i++) {
            System.out.println("RHSP Matching Normal");
            int bitsize =8;
            //  共享密钥
            int sklength = 6;
            String[] skeylist = CreateSecretKey(sklength);
            //  Rider IBFtree
            IBtreeConstruction.IBtree[] treelist = new IBtreeConstruction.IBtree[File.length];
            long[] rider_time = new long[File.length];
//            List<Long> which = new ArrayList<>();
            //  产生随机数集合
            int[] random = createRandom(File.length);
            for(int j=0;j< File.length;j++){
                long one = System.currentTimeMillis();
                int x=random[j];  // exclude driver id
                int y,z,u;
                //  离散

//                if (j == File.length-1){
//                    y = random[0];
//                }else {
//                    y = random[j+1];
//                }

//                if (j== File.length - 2){
//                    y = random[j+1];
//                    z = random[0];
//                    u = random[1];
//                }else if (j == File.length - 1) {
//                    y = random[0];
//                    z = random[1];
//                    u = random[2];

//                }else {
//                    y = random[j+1];
//                    z = random[j+2];
//                }
//                if (x != y-1){
//                    if (y == File.length){
//                        z = 1;
//                    }else {
//                        z = y + 1;
//                    }
//                }
//                }else if (j == File.length - 3){
//                    y = random[j+1];
//                    z = random[j+2];
//                    u = random[0];
//                }else {
//                    y = random[j+1];
//                    z = random[j+2];
//                    u = random[j+3];
//                }

                //  连续
//                if (x == File.length){
//                    z = x - 1;
//                }else {
//                    z = x + 1;
//                }

//                if (x == File.length){
//                    y = x - 1;
//                    z = x - 2;
//                }else if (x == File.length - 1){
//                    y = x + 1;
//                    z = x - 1;
//                }else {
//                    y = x + 1;
//                    z = x + 2;
//                }

                // 离散 + 离散
//                if (j == File.length-1){
//                    y = random[0];
//                }else {
//                    y = random[j+1];
//                }

                // 离散 + 连续
//                if (x == File.length){
//                    y = x - 1;
//                }else {
//                    y = x + 1;
//                }

                // 离散 + 离散 + 离散
//                if (j== File.length - 2){
//                    y = random[j+1];
//                    z = random[0];
//                }else if (j == File.length - 1) {
//                    y = random[0];
//                    z = random[1];
//                }else {
//                    y = random[j+1];
//                    z = random[j+2];
//                }

                // 离散 + 离散 + 连续
                if (j == File.length-1){
                    y = random[0];
                }else {
                    y = random[j+1];
                }
                if (x == File.length){
                    z = x - 1;
                }else {
                    z = x + 1;
                }

                // 离散 + 连续 + 连续
//                if (x == File.length){
//                    y = x - 1;
//                    z = x - 2;
//                }else if (x == File.length - 1){
//                    y = x + 1;
//                    z = x - 1;
//                }else {
//                    y = x + 1;
//                    z = x + 2;
//                }


                //  离散 + 离散 + 离散 + 连续
//                if (j== File.length - 2){
//                    y = random[j+1];
//                    z = random[0];
//                }else if (j == File.length - 1) {
//                    y = random[0];
//                    z = random[1];
//                }else {
//                    y = random[j+1];
//                    z = random[j+2];
//                }
//
//                if (x == File.length){
//                    u = x - 1;
//                }else {
//                    u = x + 1;
//                }

                //  离散 + 离散 + 离散 + 离散
//                if (j== File.length - 2){
//                    y = random[j+1];
//                    z = random[0];
//                    u = random[1];
//                }else if (j == File.length - 1) {
//                    y = random[0];
//                    z = random[1];
//                    u = random[2];
//                }else if (j == File.length - 3){
//                    y = random[j+1];
//                    z = random[j+2];
//                    u = random[0];
//                }else {
//                    y = random[j+1];
//                    z = random[j+2];
//                    u = random[j+3];
//                }


                //  离散 + 离散 + 连续 + 连续
//                if (j == File.length-1){
//                    u = random[0];
//                }else {
//                    u = random[j+1];
//                }
//                if (x == File.length){
//                    y = x - 1;
//                    z = x - 2;
//                }else if (x == File.length - 1){
//                    y = x + 1;
//                    z = x - 1;
//                }else {
//                    y = x + 1;
//                    z = x + 2;
//                }

                //  离散 + 连续 + 连续 + 连续
//                if (j == File.length-1){
//                    y = random[0];
//                }else {
//                    y = random[j+1];
//                }
//
//                if (x == File.length){
//                    z = x - 1;
//                    u = x - 2;
//                }else if (x == File.length - 1){
//                    z = x + 1;
//                    u = x - 1;
//                }else {
//                    z = x + 1;
//                    u = x + 2;
//                }



                //  排除司机集合
                int[] n_order = {x,y,z};
                //  排序
                int[] n = OrderINT(n_order);
//                new MyUtil.Show().showString(n);

                String[][] arr = new RideRequest.Generate_Q().Generate_Q(bitsize, n, File[j][1],File[j][2]);
                IBtreeConstruction.IBtree mytree = new IBtreeConstruction().CreateTree(arr, skeylist);
                mytree.ibf = IBFStructure.IBFConstruction.IndistinguishableBloomFilter(10000, skeylist);
                IBtreeConstruction.initTreeNode_Root(mytree);
                // System.out.println("树节点初始化成功");
                long two = System.currentTimeMillis();
                rider_time[j] = two-one;
//                which.add(two-one);
                treelist[j] = mytree;
            }
            long min = 8000000;
            long max = 0;
            long all = 0;
            int count_time = 0;
            for (int j = 0; j < rider_time.length; j++) {
                if (rider_time[j]<min){
                    min = rider_time[j];
                }
                if (rider_time[j]>max && rider_time[j] < 50){
                    max = rider_time[j];
                }
                if (rider_time[j] < 50){
                    all = all + rider_time[j];
                    System.out.print(rider_time[j]+",");
                    count_time = count_time + 1;
                }
            }
            System.out.println();
//            int index = which.indexOf(max);
//            System.out.println("index : "+ random[index]);

            min_rider.add(min);
            max_rider.add(max);
            all_rider.add(all);
            count_rider.add(count_time);

            System.out.println(" Riders are ready to go !");


            //  Driver Token
            String[][][] tokenlist = new String[File.length][bitsize][sklength-1];
            for (int j=0;j< File.length;j++){
                String[][] arr_driver = new Generate_Q_driver().Generate_Q(bitsize,j+1,File[j][1],File[j][2]);
                String[] keyword = new Generate_Q().StringStringtoString(arr_driver);
                String[][] Token = new RideResponse_Token().Keyword(keyword,skeylist);
                tokenlist[j] = Token;
            }
            System.out.println(" Drivers are ok ! ");

            //  RHSP Matching

            System.out.println(" RHSP starts Matching !");
            long startTime = System.currentTimeMillis();

            for (int k=0;k<treelist.length;k++){
                for (int j=0;j<tokenlist.length;j++){
                    boolean tag ;
                    String[][] Mar = new QueryProcessing().secondProcessing(tokenlist[j],treelist[k].ibf);
                    tag = new QueryProcessing().Query2(Mar,treelist[k]);
//                System.out.println(" rider "+i+" driver "+j+" match "+tag);
                    if (tag == true){
//                        System.out.println(" rider "+k+" driver "+j+" match "+tag);
                        tokenlist[j] = tokenlist[tokenlist.length-1];
                        tokenlist = Arrays.copyOf(tokenlist, tokenlist.length-1);
                        break;
                    }
                }
            }

            long endTime = System.currentTimeMillis();
            long totalTime = endTime-startTime;
            RHSPTime[i] = totalTime;
            System.out.println(RHSPTime[i]+" "+i);
            count = count + RHSPTime[i];

        }
        System.out.println("------------");
        System.out.println("RHSPTime:");
        ShowOne(RHSPTime);
        System.out.println("total: "+count);
        long time_max = 0;
        long time_min = 0;
        long time_all = 0;
        int time_count = 0;
        System.out.println("min: ");
        for (int i = 0; i < min_rider.size(); i++) {
            System.out.print(min_rider.get(i)+",");
            time_min = time_min + min_rider.get(i);
        }
        System.out.println();
        System.out.println("max: ");
        for (int i = 0; i < max_rider.size(); i++) {
            time_max = time_max + max_rider.get(i);
            System.out.print(max_rider.get(i)+",");
        }
        System.out.println();
        System.out.println("all: ");
        for (int i = 0; i < all_rider.size(); i++) {
            time_all = time_all + all_rider.get(i);
            System.out.print(all_rider.get(i)+",");
        }
        System.out.println();
        System.out.println("count: ");
        for (int i = 0; i < count_rider.size(); i++) {
            time_count = time_count + count_rider.get(i);
            System.out.print(count_rider.get(i)+",");
        }
        System.out.println();
        System.out.println("total: ");
        System.out.println("min: "+time_min+" ,max: "+time_max+",all: "+time_all+" ,count = "+time_count);
    }


    public static void ShowOne(long[] arr){
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]+" ");
        }
        System.out.println();
    }
    public static int[] OrderINT(int[] ints){
        return Arrays.stream(ints).sorted().toArray();
    }
    public static int[] createRandom(int all){
        int[] arr = new int[all];
        for (int i = 0; i < all; i++) {
            arr[i] = (int) (Math.random() * all) + 1;
            for (int j = 0; j < i; j++) {
                if (arr[j] == arr[i]) {
                    i--;
                    break;
                }
            }
        }
        return arr;
    }

    public static int[] createRandom(int count,int all){
        int[] arr = new int[count];
        for (int i = 0; i < count; i++) {
            arr[i] = (int) (Math.random() * all) + 1;
            for (int j = 0; j < i; j++) {
                if (arr[j] == arr[i]) {
                    i--;
                    break;
                }
            }
        }
        return arr;
    }
}
