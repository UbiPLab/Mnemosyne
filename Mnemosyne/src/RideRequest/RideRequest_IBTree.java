package RideRequest;

import IBFStructure.IBtreeConstruction;

import java.security.NoSuchAlgorithmException;

import static IBFStructure.IBFConstruction.CreateSecretKey;
import static IBFStructure.IBtreeConstruction.*;

public class RideRequest_IBTree {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        int bitsize =11;
        int[] n ={9};
        double lat =23.9999;
        double lng =96.3967;
        String[][] arr = new Generate_Q().Generate_Q(bitsize,n,lat,lng);

        int sklength = 6;
        String[] skeylist = new String[sklength];
        skeylist = CreateSecretKey(sklength);

        IBtree mytree = new IBtreeConstruction().CreateTree(arr,skeylist);
        mytree.ibf = IBFStructure.IBFConstruction.IndistinguishableBloomFilter(10000, skeylist);
        System.out.println("遍历树：");
        preOrder(mytree);
        System.out.println("遍历叶子节点：");
        leaf(mytree);
        System.out.println();
//        IBtreeConstruction.Node h =new IBtreeConstruction.Node();
//        IBtreeConstruction.createlist(mytree,h);
//        System.out.println("遍历叶子节点链表：");
//        display(h);
//        System.out.println();
//        System.out.println("叶子节点先初始化：");
//        IBtreeConstruction.initTreeLeafNode(mytree);
//        //  IBFConstruction.showIBF(h.next.treenode.ibf);
//        System.out.println("叶子节点初始化成功");
        System.out.println("树高度："+mytree.height);
        System.out.println("树节点初始化：");
//        IBtreeConstruction.initTreeNode(mytree);
        IBtreeConstruction.initTreeNode_Root(mytree);
        System.out.println("树节点初始化成功");


    }
}
