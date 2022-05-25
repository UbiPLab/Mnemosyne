package TimeTest;

import IBFStructure.IBtreeConstruction;
import RideRequest.Generate_Q;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static IBFStructure.IBFConstruction.CreateSecretKey;
import static IBFStructure.IBtreeConstruction.*;

public class RiderTime {


    public String getRiderRequest() throws NoSuchAlgorithmException {
        System.out.println("Rider Request");
        StringBuffer stringBuffer=new StringBuffer();

        int bitsize =11;
        int[] n = createRandom(1,100);
//        int[] n ={28,129,257,387,466,564,688,776,891,998};
//        int[] n ={8,65,127,256,313,564,714,865,911,984};
        double lat =23.9999;
        double lng =96.3967;
        long[] TimeList = new long[100];
//        long startTime = System.nanoTime();
        for (int i=0;i<100;i++) {
            long startTime = System.currentTimeMillis();
            String[][] arr = new Generate_Q().Generate_Q(bitsize, n, lat, lng);

            int sklength = 6;
            String[] skeylist = CreateSecretKey(sklength);
            IBtreeConstruction.IBtree mytree = new IBtreeConstruction().CreateTree(arr, skeylist);
//        System.out.println("叶子节点先初始化：");
            IBtreeConstruction.initTreeLeafNode(mytree);
            //  IBFConstruction.showIBF(h.next.treenode.ibf);
//        System.out.println("叶子节点初始化成功");
//        System.out.println("树高度："+mytree.height);
//        System.out.println("树节点初始化：");
            IBtreeConstruction.initTreeNode(mytree);
//            System.out.println("树节点初始化成功");
            long endTime = System.currentTimeMillis();
            TimeList[i] = endTime - startTime;     //ms-- s
        }

        long totalTime = 0;
        long maxTime =0;
        long minTime =TimeList[0];
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
        stringBuffer.append("Time Cost of 100"+" : "+totalTime+" ms ");

        return stringBuffer.toString();
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

    public int[] OrderINT(int[] ints){
        return Arrays.stream(ints).sorted().toArray();
    }
}
