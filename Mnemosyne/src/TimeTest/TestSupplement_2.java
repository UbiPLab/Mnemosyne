package TimeTest;

import IBFStructure.IBtreeConstruction;
import QueryProcessing.QueryProcessing;
import RideRequest.Generate_Q;
import RideResponse.Generate_Q_driver;
import RideResponse.RideResponse_Token;
import ReadFileData.ReadFiledata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static IBFStructure.IBFConstruction.CreateSecretKey;

public class TestSupplement_2 {
    public static void main(String[] args) throws Exception {
        //  导入文件
        String address_used = "E:\\Gowalla\\Gowlla-lat-lng-100.txt";
        double[][] File = new ReadFiledata().readArray(address_used);

        int test_time = 500;
        long[] RHSPTime = new long[test_time];
        long count = 0;

        List<Integer> count_rider = new ArrayList<>();
        for (int i = 0; i < test_time; i++) {
            System.out.println("RHSP Matching Normal");
            int bitsize =8;
            //  共享密钥
            int sklength = 6;
            String[] skeylist = CreateSecretKey(sklength);
            //  Rider IBFtree
            IBtreeConstruction.IBtree[] treelist = new IBtreeConstruction.IBtree[File.length];
            //  产生随机数集合
            int[] random = createRandom(File.length);
            int split_location_1 = File.length / 2;
            int split_location_2 = 2 * File.length / 3;
//            int split_location_3 = File.length - File.length/4;

            for(int j=0;j< split_location_1;j++){
                int x=random[j];  // exclude driver id
                int y,z,u;
                //  离散
//                if (j == File.length-1){
//                    y = random[0];
//                }else {
//                    y = random[j+1];
//                }

                //  连续
//                if (x == File.length){
//                    z = x - 1;
//                }else {
//                    z = x + 1;
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
//                if (j == File.length-1){
//                    y = random[0];
//                }else {
//                    y = random[j+1];
//                }
//                if (x == File.length){
//                    z = x - 1;
//                }else {
//                    z = x + 1;
//                }

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
                int[] n_order = {x};
                //  排序
                int[] n = OrderINT(n_order);
//                new MyUtil.Show().showString(n);
                String[][] arr = new Generate_Q().Generate_Q(bitsize, n, File[j][1],File[j][2]);
                IBtreeConstruction.IBtree mytree = new IBtreeConstruction().CreateTree(arr, skeylist);
                mytree.ibf = IBFStructure.IBFConstruction.IndistinguishableBloomFilter(10000, skeylist);
                IBtreeConstruction.initTreeNode_Root(mytree);
                // System.out.println("树节点初始化成功");
                treelist[j] = mytree;
            }

            for(int j=split_location_1;j< split_location_2;j++){
                int x=random[j];  // exclude driver id
                int y,z,u;

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
                if (j== File.length - 2){
                    y = random[j+1];
                    z = random[0];
                }else if (j == File.length - 1) {
                    y = random[0];
                    z = random[1];
                }else {
                    y = random[j+1];
                    z = random[j+2];
                }

                // 离散 + 离散 + 连续
//                if (j == File.length-1){
//                    y = random[0];
//                }else {
//                    y = random[j+1];
//                }
//                if (x == File.length){
//                    z = x - 1;
//                }else {
//                    z = x + 1;
//                }

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
                String[][] arr = new Generate_Q().Generate_Q(bitsize, n, File[j][1],File[j][2]);
                IBtreeConstruction.IBtree mytree = new IBtreeConstruction().CreateTree(arr, skeylist);
                mytree.ibf = IBFStructure.IBFConstruction.IndistinguishableBloomFilter(10000, skeylist);
                IBtreeConstruction.initTreeNode_Root(mytree);
                // System.out.println("树节点初始化成功");
                treelist[j] = mytree;
            }


            for(int j=split_location_2;j< File.length;j++){
                int x=random[j];  // exclude driver id
                int y,z,u;

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
//                if (j == File.length-1){
//                    y = random[0];
//                }else {
//                    y = random[j+1];
//                }
//                if (x == File.length){
//                    z = x - 1;
//                }else {
//                    z = x + 1;
//                }

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

                //  离散 + 离散 + 离散 + 离散
                if (j== File.length - 2){
                    y = random[j+1];
                    z = random[0];
                    u = random[1];
                }else if (j == File.length - 1) {
                    y = random[0];
                    z = random[1];
                    u = random[2];
                }else if (j == File.length - 3){
                    y = random[j+1];
                    z = random[j+2];
                    u = random[0];
                }else {
                    y = random[j+1];
                    z = random[j+2];
                    u = random[j+3];
                }

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
                int[] n_order = {x,y,z,u};
                //  排序
                int[] n = OrderINT(n_order);
//                new MyUtil.Show().showString(n);
                String[][] arr = new Generate_Q().Generate_Q(bitsize, n, File[j][1],File[j][2]);
                IBtreeConstruction.IBtree mytree = new IBtreeConstruction().CreateTree(arr, skeylist);
                mytree.ibf = IBFStructure.IBFConstruction.IndistinguishableBloomFilter(10000, skeylist);
                IBtreeConstruction.initTreeNode_Root(mytree);
                // System.out.println("树节点初始化成功");
                treelist[j] = mytree;
            }

//            for(int j=split_location_3;j< File.length;j++){
//                int x=random[j];  // exclude driver id
//                int y,z,u;
//
//                // 离散 + 离散
////                if (j == File.length-1){
////                    y = random[0];
////                }else {
////                    y = random[j+1];
////                }
//
//                // 离散 + 连续
////                if (x == File.length){
////                    y = x - 1;
////                }else {
////                    y = x + 1;
////                }
//
//                // 离散 + 离散 + 离散
////                if (j== File.length - 2){
////                    y = random[j+1];
////                    z = random[0];
////                }else if (j == File.length - 1) {
////                    y = random[0];
////                    z = random[1];
////                }else {
////                    y = random[j+1];
////                    z = random[j+2];
////                }
//
//                // 离散 + 离散 + 连续
////                if (j == File.length-1){
////                    y = random[0];
////                }else {
////                    y = random[j+1];
////                }
////                if (x == File.length){
////                    z = x - 1;
////                }else {
////                    z = x + 1;
////                }
//
//                // 离散 + 连续 + 连续
////                if (x == File.length){
////                    y = x - 1;
////                    z = x - 2;
////                }else if (x == File.length - 1){
////                    y = x + 1;
////                    z = x - 1;
////                }else {
////                    y = x + 1;
////                    z = x + 2;
////                }
//
//                //  离散 + 离散 + 离散 + 离散
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
//
//                //  离散 + 离散 + 离散 + 连续
////                if (j== File.length - 2){
////                    y = random[j+1];
////                    z = random[0];
////                }else if (j == File.length - 1) {
////                    y = random[0];
////                    z = random[1];
////                }else {
////                    y = random[j+1];
////                    z = random[j+2];
////                }
////
////                if (x == File.length){
////                    u = x - 1;
////                }else {
////                    u = x + 1;
////                }
//
//                //  离散 + 离散 + 连续 + 连续
////                if (j == File.length-1){
////                    u = random[0];
////                }else {
////                    u = random[j+1];
////                }
////                if (x == File.length){
////                    y = x - 1;
////                    z = x - 2;
////                }else if (x == File.length - 1){
////                    y = x + 1;
////                    z = x - 1;
////                }else {
////                    y = x + 1;
////                    z = x + 2;
////                }
//
//                //  离散 + 连续 + 连续 + 连续
////                if (j == File.length-1){
////                    y = random[0];
////                }else {
////                    y = random[j+1];
////                }
////
////                if (x == File.length){
////                    z = x - 1;
////                    u = x - 2;
////                }else if (x == File.length - 1){
////                    z = x + 1;
////                    u = x - 1;
////                }else {
////                    z = x + 1;
////                    u = x + 2;
////                }
//
//
//                //  排除司机集合
//                int[] n_order = {x,y,z,u};
//                //  排序
//                int[] n = OrderINT(n_order);
////                new MyUtil.Show().showString(n);
//                String[][] arr = new Generate_Q().Generate_Q(bitsize, n, File[j][1],File[j][2]);
//                IBtreeConstruction.IBtree mytree = new IBtreeConstruction().CreateTree(arr, skeylist);
//                mytree.ibf = IBF.IBFConstruction.IndistinguishableBloomFilter(10000, skeylist);
//                IBtreeConstruction.initTreeNode_Root(mytree);
//                // System.out.println("树节点初始化成功");
//                treelist[j] = mytree;
//            }

            System.out.println();
//            int index = which.indexOf(max);
//            System.out.println("index : "+ random[index]);

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
