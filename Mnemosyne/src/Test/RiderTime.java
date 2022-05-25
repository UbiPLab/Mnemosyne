package Test;

import IBFStructure.IBtreeConstruction;
import RideRequest.ExcloudDriver;
import RideRequest.Generate_Q;

import java.security.NoSuchAlgorithmException;

import static IBFStructure.IBFConstruction.CreateSecretKey;

/**
 * @Author UbiP Lab Laptop 02
 * @Date 2022/4/7 9:32
 * @Version 1.0
 */
public class RiderTime {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println("Rider Request");
        StringBuffer stringBuffer = new StringBuffer();

        int bitsize = 11;
        int[] n = new ExcloudDriver().createRandom(1, 600);
//        int[] n = createRandom(1,100);
//        int[] n ={28,129,257,387,466,564,688,776,891,998};
//        int[] n ={8,65,127,256,313,564,714,865,911,984};
        //  37.78647887	-122.4035633

        //  默认乘客位置
        double lat = 37.78647887;
        double lng = -122.4035633;

        int test_time = 100;
        long[] TimeList = new long[test_time];

//        long startTime = System.nanoTime();
        for (int i = 0; i < test_time; i++) {
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
        long maxTime = 0;
        long minTime = TimeList[0];
        for (int i = 0; i < TimeList.length; i++) {
            if (TimeList[i] < minTime) {
                minTime = TimeList[i];
            }
            if (TimeList[i] > maxTime) {
                maxTime = TimeList[i];
            }
            totalTime = totalTime + TimeList[i];
            System.out.print(" " + TimeList[i]);
        }
        System.out.println();
        System.out.println("max time is :" + maxTime);
        System.out.println("min time is " + minTime);
        stringBuffer.append("Time Cost of 100" + " : " + totalTime + " ms ");


    }
}
