package QueryProcessing;

import IBFStructure.IBtreeConstruction;
import RideRequest.Generate_Q;
import RideResponse.Generate_Q_driver;
import RideResponse.RideResponse_Token;

import static IBFStructure.IBFConstruction.CreateSecretKey;
import static IBFStructure.IBFConstruction.showIBF;
import static IBFStructure.IBtreeConstruction.*;
import static RideResponse.RideResponse_Token.show;

public class ReceivePoint {


    /*
        接受
     */

    public static void main(String[] args) throws Exception {
        int bitsize =11;
        int n_driver = 9;
//        int[] n = createINT(n_driver,1023);
        int[] n = { 8,56};

        int N =20;
        double lat_driver =23.9999;
        double lng_driver =96.3967;
        double lat_rider =23;
        double lng_rider =97;
        String[][] arr = new Generate_Q().Generate_Q(bitsize,n,lat_rider,lng_rider);

        int sklength = 6;
        String[] skeylist = new String[sklength];
        skeylist = CreateSecretKey(sklength);

        IBtree mytree = new IBtreeConstruction().CreateTree(arr,skeylist);
        System.out.println("遍历树：");
        preOrder(mytree);
        System.out.println("遍历叶子节点：");
        leaf(mytree);
        System.out.println();
        Node h =new Node();
        IBtreeConstruction.createlist(mytree,h);
        System.out.println("遍历叶子节点链表：");
        display(h);
        System.out.println();
        System.out.println("叶子节点先初始化：");
        IBtreeConstruction.initTreeLeafNode(mytree);
        //  IBFConstruction.showIBF(h.next.treenode.ibf);
        System.out.println("叶子节点初始化成功");
        System.out.println("树高度："+mytree.height);
        System.out.println("树节点初始化：");
        IBtreeConstruction.initTreeNode(mytree);
        System.out.println("树节点初始化成功");

        showIBF(mytree.ibf);


        String temp = Integer.toBinaryString(n_driver);
        System.out.println("temp :"+temp);
        String[][] arr_driver = new Generate_Q_driver().Generate_Q(bitsize,n_driver,lat_driver,lng_driver);
        String[] keyword = new Generate_Q().StringStringtoString(arr_driver);

        String[][] Token = new RideResponse_Token().Keyword(keyword,mytree.ibf.Keylist);
        System.out.println();
        System.out.println("show Token :");
        show(Token);
        System.out.println();

        String[][] Mar = new QueryProcessing().secondProcessing(Token,mytree.ibf);

        System.out.println("show Mar :");
        show(Mar);
        System.out.println();

//        new QueryProcessing().Query(Mar,mytree);

        System.out.println("result : "+new QueryProcessing().Query2(Mar,mytree));
//        showIBF(mytree.ibf);

        for (int i=0;i<n.length;i++){
            System.out.print(n[i]+" ");
        }
        System.out.println();
        System.out.println(n_driver);
    }

    public static int[] createINT(int k,int all){
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

}
