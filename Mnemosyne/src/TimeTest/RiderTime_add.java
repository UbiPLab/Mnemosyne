package TimeTest;

import IBFStructure.IBtreeConstruction;
import RideRequest.Generate_Q;
import RideResponse.Generate_Q_driver;
import RideResponse.RideResponse_Token;
import ReadFileData.ReadFiledata;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static IBFStructure.IBFConstruction.CreateSecretKey;

public class RiderTime_add {
    public Map<Integer,Long> getRHSPMatching_Normal() throws Exception {
        System.out.println("Rider Matching Normal");
        Map<Integer,Long> map =  new HashMap<>();
        int bitsize =8;

        long[] TimeList = new long[100];
//        long startTime = System.nanoTime();

        //  导入文件
        String address_used = "E:\\Gowalla\\Gowlla-lat-lng-20.txt";
        double[][] File = new ReadFiledata().readArray(address_used);

        long startTime = System.currentTimeMillis();

        //  共享密钥
        int sklength = 6;
        String[] skeylist = CreateSecretKey(sklength);
        //  Rider IBFtree

        IBtreeConstruction.IBtree[] treelist = new IBtreeConstruction.IBtree[File.length];
        long[] time_list = new long[File.length];
        int[] random = createRandom(File.length);
        for(int i=0;i< File.length;i++){
            long o = System.currentTimeMillis();
//            int x=random[i];  // agree driver id
//            int y;
//
//            if (i== File.length - 1){
//                y = random[0];
//            }else {
//                y = random[i+1];
//            }
//            System.out.println(" 允许 "+x);
            int[] n_order = createRandom(2,File.length);
//            int[] n_order_2 = {n_order[0],n_order[1],n_order[1]+1,n_order[1]+2};
//            int[] n_order_2 = {x};
//            int[] n_order = {x,y};
            int[] n = OrderINT(n_order);
//            int[] n =createINT(x, File.length);
            String[][] arr = new Generate_Q().Generate_Q(bitsize, n, File[i][1],File[i][2]);
            IBtreeConstruction.IBtree mytree = new IBtreeConstruction().CreateTree(arr, skeylist);
            mytree.ibf = IBFStructure.IBFConstruction.IndistinguishableBloomFilter(10000, skeylist);
            IBtreeConstruction.initTreeNode_Root(mytree);
            // System.out.println("树节点初始化成功");

            treelist[i] = mytree;
            long k = System.currentTimeMillis();
            time_list[i] = k-o;
        }

        long endTime = System.currentTimeMillis();
        long totalTime = endTime-startTime;

        long min = 80000;
        long max = 0;
        for (int i = 0; i < time_list.length; i++) {
            if (time_list[i]<min){
                min = time_list[i];
            }
            if (time_list[i]>max){
                max = time_list[i];
            }
            System.out.print(time_list[i]+",");
        }
        map.put(0,min);
        map.put(1,max);

        System.out.println();
        System.out.println(" Riders are ready to go !");

        return map;
    }


    public int[] OrderINT(int[] ints){
        return Arrays.stream(ints).sorted().toArray();
    }

//    public int[] createINT_group(int count,int all){
//        int[] result = new int[count];
//        result[0] = (int) (Math.random() * all) + 1;
//    }



    public int[] createINT(int k,int all){      // 1-all中排除 k
        int[] list = new int[all];

        for (int i=0;i<all;i++){
            list[i] = i+1;
        }
        for (int i=k;i<list.length;i++){
            list[i-1]=list[i];
        }
        int[] list2 = new int[all-1];
        for (int i=0;i<list2.length;i++){
            list2[i]=list[i];
        }

        return list2;
    }

    public int[] createINT2(int k,int all){         // 1-all 排除 前k个
        int[] list = new int[all-k+1];
        for (int i = 0; i < list.length; i++) {
            list[i] = i+k;
        }
        return list;
    }

    public int[] createRandom(int all){
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

    public int[] createRandom(int count,int all){
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
