package Test;

import IBFStructure.IBtreeConstruction;
import MyUtil.Show;
import QueryProcessing.QueryProcessing;
import ReadFileData.ReadFiledata;
import RideRequest.Generate_Q;
import RideResponse.Generate_Q_driver;
import RideResponse.RideResponse_Token;
import SpaceEncoding.Distance;
import com.carrotsearch.sizeof.RamUsageEstimator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static IBFStructure.IBFConstruction.CreateSecretKey;

/**
 * @Author UbiP Lab Laptop 02
 * @Date 2022/4/18 10:53
 * @Version 1.0
 */
public class testOneByFive {
    public static void main(String[] args) throws Exception {
        int bitsize = 8;
        int functionNum = 16;
        long[] TimeList = new long[100];
//        long startTime = System.nanoTime();

        //  导入文件
        String address_used_rider = "C:\\Users\\UbiP Lab Laptop 02\\Desktop\\Me\\50.txt";
        String address_used_driver = "C:\\Users\\UbiP Lab Laptop 02\\Desktop\\Me\\500.txt";

        double[][] File_rider = new ReadFiledata().readArray(address_used_rider);
        double[][] File_driver = new ReadFiledata().readArray(address_used_driver);

        long[] sizeOf = new long[3];
        //  共享密钥
        int sklength = 6;
        String[] skeylist = CreateSecretKey(sklength);
        //  Rider IBFtree
        System.out.println("start:");
        int test_rider = 0;
        int test_driver = 50;
        long rider_start_time = System.currentTimeMillis();
        IBtreeConstruction.IBtree[] treelist = new IBtreeConstruction.IBtree[1];
        int[] random = createRandom(test_driver);

        for (int i = 0; i < 1; i++) {
//        for(int i=0;i< File_rider.length;i++){

            int x = random[i];  // exclude driver id
            int[] n_order = {x};
            int[] n = OrderINT(n_order);
//            int[] n =createINT(x, File.length);

            String[][] arr = new Generate_Q().Generate_Q(bitsize, n, File_rider[test_rider][1], File_rider[test_rider][2], functionNum);
            System.out.println("insert IBF element number: " + (arr.length * arr[i].length));
            System.out.println("Rider: exclude " + x + " " + "(" + File_rider[test_rider][1] + "," + File_rider[test_rider][2] + ")");

            IBtreeConstruction.IBtree mytree = new IBtreeConstruction().CreateTree(arr, skeylist);
            mytree.ibf = IBFStructure.IBFConstruction.IndistinguishableBloomFilter(IBFStructure.IBFLength.IBFLengthOfFuctionNumber[functionNum / 2 -1], skeylist);
            IBtreeConstruction.initTreeNode_Root(mytree);
            // System.out.println("树节点初始化成功");

            treelist[i] = mytree;
//            System.out.println("Rider: "+x+" "+"("+File_rider[i][1]+","+File_rider[i][2]+")");
        }
        sizeOf[0] = RamUsageEstimator.sizeOf(treelist);
        System.out.println("Size of rider: " + sizeOf[0]);
        System.out.println(" Riders are ready to go !");
        long driver_start_time = System.currentTimeMillis();


        //  Driver Token
        String[][][] tokenlist = new String[test_driver][bitsize][sklength - 1];
        for (int i = 0; i < test_driver; i++) {
            String[][] arr_driver = new Generate_Q_driver().Generate_Q(bitsize, (int) File_driver[i][0], File_driver[i][1], File_driver[i][2], functionNum);
            System.out.println("driver: (" + File_driver[i][0] + "," + File_driver[i][1] + "," + File_driver[i][2] + ")");
//            for (int j = 0; j < arr_driver.length; j++) {
//                for (int k = 0; k < arr_driver[j].length; k++) {
//                    System.out.print(arr_driver[j][k]+" ");
//                }
//                System.out.println();
//            }
            String[] keyword = new Generate_Q().StringStringtoString(arr_driver);
            String[][] Token = new RideResponse_Token().Keyword(keyword, skeylist);
            tokenlist[i] = Token;
        }
        sizeOf[1] = RamUsageEstimator.sizeOf(tokenlist);
        System.out.println("Size of driver: " + sizeOf[1]);
        System.out.println(" Drivers are ok ! ");


        List<Double> distances = new ArrayList<Double>();
        //  RHSP Matching

        System.out.println(" RHSP starts Matching !");
        String key_rider_driver = MyUtil.AESUtil.CreateSecretKey(16);
        long startTime = System.currentTimeMillis();
        boolean[] already_matched = new boolean[tokenlist.length];
        //  false 表示未匹配
        Arrays.fill(already_matched, false);
        List<String> ciper_return = new ArrayList<>();
        int match = 0;
        for (int i = 0; i < treelist.length; i++) {
            for (int j = 0; j < tokenlist.length; j++) {
                if (already_matched[j]) {
                    continue;
                }
                boolean tag;
                String[][] Mar = new QueryProcessing().secondProcessing(tokenlist[j], treelist[i].ibf);
                tag = new QueryProcessing().Query2(Mar, treelist[i]);
//                System.out.println(" rider "+i+" driver "+j+" match "+tag);
                if (tag == true) {
//                    System.out.println(" rider "+i+" driver "+j+" match "+tag);
                    already_matched[j] = true;
                    System.out.println("Rider: " + random[i] + " " + "(" + File_rider[test_rider][1] + "," + File_rider[test_rider][2] + ")" + "driver: " + j + " " + "(" + File_driver[j][1] + "," + File_driver[j][2] + ")");
                    match++;
                    distances.add(new Distance().GetDirectDistance(File_rider[test_rider][1], File_rider[test_rider][2], File_driver[j][1], File_driver[j][2]));
                    String plaintext = "driver id:" + j + " lat: " + File_driver[j][1] + " lng: " + File_driver[j][2];
                    ciper_return.add(MyUtil.AESUtil.encryptIntoHexString(plaintext, key_rider_driver));
                    //            for (int j = 0; j < arr_driver.length; j++) {
//                for (int k = 0; k < arr_driver[j].length; k++) {
//                    System.out.print(arr_driver[j][k]+" ");
//                }
//                System.out.println();
//            }
                    //                    tokenlist[j] = tokenlist[tokenlist.length-1];
//                    tokenlist = Arrays.copyOf(tokenlist, tokenlist.length-1);
//                    break;
                }
            }
        }
        sizeOf[2] = RamUsageEstimator.sizeOf(ciper_return);
        System.out.println("Size of RHSP: " + sizeOf[2]);
        System.out.println("-------------------");
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        long[] res = new long[3];
        res[0] = driver_start_time - rider_start_time;
        res[1] = startTime - driver_start_time;
        res[2] = totalTime;


        System.out.println("fuction number : "+functionNum);

        System.out.println("Time: ");
        Show.showLong_list(res);
        System.out.println("Size: ");
        Show.showLong_list(sizeOf);


        System.out.println("match: " + match);

        double max_distance = 0;
        double min_distance = Double.MAX_VALUE;
        double ave_distance = 0;

        for (int i = 0; i < distances.size(); i++) {
            max_distance = max_distance > distances.get(i) ? max_distance : distances.get(i);
            min_distance = min_distance < distances.get(i) ? min_distance : distances.get(i);
            ave_distance += distances.get(i);
        }
        ave_distance = ave_distance / distances.size();

        System.out.println("max_distance: " + max_distance);
        System.out.println("min_distance: " + min_distance);
        System.out.println("ave_distance: " + ave_distance);
    }

    public void OneByFive(int functionNum,long[] time,long[] sizeOf,double[] distance) throws Exception {
        int bitsize = 8;
//        int functionNum = 2;

//        long startTime = System.nanoTime();

        //  导入文件
        String address_used_rider = "C:\\Users\\UbiP Lab Laptop 02\\Desktop\\Me\\50.txt";
        String address_used_driver = "C:\\Users\\UbiP Lab Laptop 02\\Desktop\\Me\\500.txt";

        double[][] File_rider = new ReadFiledata().readArray(address_used_rider);
        double[][] File_driver = new ReadFiledata().readArray(address_used_driver);

//        long[] sizeOf = new long[3];
        //  共享密钥
        int sklength = 6;
        String[] skeylist = CreateSecretKey(sklength);
        //  Rider IBFtree
//        System.out.println("start:");
        int test_rider = 0;
        int test_driver = 50;

        long rider_start_time = System.nanoTime();

        IBtreeConstruction.IBtree[] treelist = new IBtreeConstruction.IBtree[1];
        int[] random = createRandom(test_driver);

        for (int i = 0; i < 1; i++) {
//        for(int i=0;i< File_rider.length;i++){

            int x = random[i];  // exclude driver id
            int[] n_order = {x};
            int[] n = OrderINT(n_order);
//            int[] n =createINT(x, File.length);

            String[][] arr = new Generate_Q().Generate_Q(bitsize, n, File_rider[test_rider][1], File_rider[test_rider][2], functionNum);
//            System.out.println("insert IBF element number: " + (arr.length * arr[i].length));
//            System.out.println("Rider: exclude " + x + " " + "(" + File_rider[test_rider][1] + "," + File_rider[test_rider][2] + ")");

            IBtreeConstruction.IBtree mytree = new IBtreeConstruction().CreateTree(arr, skeylist);
            mytree.ibf = IBFStructure.IBFConstruction.IndistinguishableBloomFilter(IBFStructure.IBFLength.IBFLengthOfFuctionNumber[functionNum / 2 -1], skeylist);
            IBtreeConstruction.initTreeNode_Root(mytree);
            // System.out.println("树节点初始化成功");

            treelist[i] = mytree;
//            System.out.println("Rider: "+x+" "+"("+File_rider[i][1]+","+File_rider[i][2]+")");
        }
        sizeOf[0] = RamUsageEstimator.sizeOf(treelist[0].ibf);
        System.out.println("Size of rider: " + sizeOf[0]);
        System.out.println(" Riders are ready to go !");
        long driver_start_time = System.nanoTime();


        //  Driver Token
        String[][][] tokenlist = new String[test_driver][bitsize][sklength - 1];
        for (int i = 0; i < test_driver; i++) {
            String[][] arr_driver = new Generate_Q_driver().Generate_Q(bitsize, (int) File_driver[i][0], File_driver[i][1], File_driver[i][2], functionNum);
//            System.out.println("driver: (" + File_driver[i][0] + "," + File_driver[i][1] + "," + File_driver[i][2] + ")");
//            for (int j = 0; j < arr_driver.length; j++) {
//                for (int k = 0; k < arr_driver[j].length; k++) {
//                    System.out.print(arr_driver[j][k]+" ");
//                }
//                System.out.println();
//            }
            String[] keyword = new Generate_Q().StringStringtoString(arr_driver);
            String[][] Token = new RideResponse_Token().Keyword(keyword, skeylist);
            tokenlist[i] = Token;
        }
        sizeOf[1] = RamUsageEstimator.sizeOf(tokenlist);
        System.out.println("Size of driver: " + sizeOf[1]);
        System.out.println(" Drivers are ok ! ");


        List<Double> distances = new ArrayList<Double>();
        //  RHSP Matching

        System.out.println(" RHSP starts Matching !");
        String key_rider_driver = MyUtil.AESUtil.CreateSecretKey(16);
        long startTime = System.nanoTime();
        boolean[] already_matched = new boolean[tokenlist.length];
        //  false 表示未匹配
        Arrays.fill(already_matched, false);
        List<String> ciper_return = new ArrayList<>();
        int match = 0;
        for (int i = 0; i < treelist.length; i++) {
            for (int j = 0; j < tokenlist.length; j++) {
                if (already_matched[j]) {
                    continue;
                }
                boolean tag;
                String[][] Mar = new QueryProcessing().secondProcessing(tokenlist[j], treelist[i].ibf);
                tag = new QueryProcessing().Query2(Mar, treelist[i]);

//                System.out.println(" rider "+i+" driver "+j+" match "+tag);
                if (tag == true) {
//                    System.out.println(" rider "+i+" driver "+j+" match "+tag);
                    already_matched[j] = true;
//                    System.out.println("Rider: " + random[i] + " " + "(" + File_rider[test_rider][1] + "," + File_rider[test_rider][2] + ")" + "driver: " + j + " " + "(" + File_driver[j][1] + "," + File_driver[j][2] + ")");
                    match++;
                    distances.add(new Distance().GetDirectDistance(File_rider[test_rider][1], File_rider[test_rider][2], File_driver[j][1], File_driver[j][2]));
                    String plaintext = "driver id:" + j + " lat: " + File_driver[j][1] + " lng: " + File_driver[j][2];
                    ciper_return.add(MyUtil.AESUtil.encryptIntoHexString(plaintext, key_rider_driver));
                    break;
                    //            for (int j = 0; j < arr_driver.length; j++) {
//                for (int k = 0; k < arr_driver[j].length; k++) {
//                    System.out.print(arr_driver[j][k]+" ");
//                }
//                System.out.println();
//            }
                    //                    tokenlist[j] = tokenlist[tokenlist.length-1];
//                    tokenlist = Arrays.copyOf(tokenlist, tokenlist.length-1);
//                    break;
                }
//                break;
            }
        }
        sizeOf[2] = RamUsageEstimator.sizeOf(ciper_return);
        System.out.println("Size of RHSP: " + sizeOf[2]);
        System.out.println("-------------------");
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;

//        long[] time = new long[3];
        time[0] = driver_start_time - rider_start_time;
        time[1] = startTime - driver_start_time;
        time[2] = totalTime;


        System.out.println("fuction number : "+functionNum);

        System.out.println("Time: ");
        Show.showLong_list(time);
        System.out.println("Size: ");
        Show.showLong_list(sizeOf);


        System.out.println("match: " + match);

        double max_distance = 0;
        double min_distance = Double.MAX_VALUE;
        double ave_distance = 0;

        for (int i = 0; i < distances.size(); i++) {
            max_distance = max_distance > distances.get(i) ? max_distance : distances.get(i);
            min_distance = min_distance < distances.get(i) ? min_distance : distances.get(i);
            ave_distance += distances.get(i);
        }
        ave_distance = ave_distance / distances.size();

        distance[0] = max_distance;
        distance[1] = min_distance;
        distance[2] = ave_distance;
        System.out.println("max_distance: " + max_distance);
        System.out.println("min_distance: " + min_distance);
        System.out.println("ave_distance: " + ave_distance);
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

    public static int[] OrderINT(int[] ints){
        return Arrays.stream(ints).sorted().toArray();
    }
}
