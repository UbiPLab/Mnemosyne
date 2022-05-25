package TimeTest;

import IBFStructure.IBtreeConstruction;
import QueryProcessing.QueryProcessing;
import RideRequest.Generate_Q;
import RideResponse.Generate_Q_driver;
import RideResponse.RideResponse_Token;
import ReadFileData.ReadFiledata;

import java.util.Arrays;

import static IBFStructure.IBFConstruction.CreateSecretKey;
import static IBFStructure.IBFConstruction.CreateSecretKey;

public class RHSPTime_add {
    public String address = "E:\\Gowalla\\Gowlla-lat-lng-20.txt";
    public String getRHSPMatching_OneOnOne() throws Exception {
        System.out.println("RHSP Matching Worst");
        StringBuffer stringBuffer=new StringBuffer();
        int bitsize =11;
        double[][] File = new ReadFiledata().readArray(address);

        //  共享密钥
        int sklength = 6;
        String[] skeylist = new String[sklength];
        skeylist = CreateSecretKey(sklength);

        //  Rider IBFtree
        IBtreeConstruction.IBtree[] treelist = new IBtreeConstruction.IBtree[File.length];
        for(int i=0;i< File.length;i++){
            int[] n = createINT(File.length-i, File.length);
            String[][] arr = new Generate_Q().Generate_Q(bitsize, n, File[i][1],File[i][2]);
            IBtreeConstruction.IBtree mytree = new IBtreeConstruction().CreateTree(arr, skeylist);
            mytree.ibf = IBFStructure.IBFConstruction.IndistinguishableBloomFilter(10000, skeylist);
//            IBtreeConstruction.initTreeLeafNode(mytree);
            IBtreeConstruction.initTreeNode_Root(mytree);
            // System.out.println("树节点初始化成功");
            treelist[i] = mytree;
        }
        System.out.println(" Riders are ready to go !");

        //  Driver Token
        String[][][] tokenlist = new String[File.length][bitsize][sklength-1];
        for (int i=0;i< File.length;i++){
            String[][] arr_driver = new Generate_Q_driver().Generate_Q(bitsize,i+2,File[i][1],File[i][2]);
            String[] keyword = new Generate_Q().StringStringtoString(arr_driver);
            String[][] Token = new RideResponse_Token().Keyword(keyword,skeylist);
            tokenlist[i] = Token;
        }
        System.out.println(" Drivers are ok ! ");

        //  RHSP Matching

        System.out.println(" RHSP starts Matching !");
        long startTime = System.currentTimeMillis();
        for (int i=0;i<treelist.length;i++){
            for (int j=0;j<tokenlist.length;j++){
//                long startTime = System.currentTimeMillis();
                boolean tag ;
                String[][] Mar = new QueryProcessing().secondProcessing(tokenlist[j],treelist[i].ibf);
                tag = new QueryProcessing().Query2(Mar,treelist[i]);
//                System.out.println(" rider "+i+" driver "+j+" match "+tag);
//                long endTime = System.currentTimeMillis();
//                TimeList[i] = endTime - startTime;     //ms-- s
                if (tag == true){
                    tokenlist[j] = tokenlist[tokenlist.length-1];
                    tokenlist = Arrays.copyOf(tokenlist, tokenlist.length-1);
                    break;
                }
            }
        }

        long endTime = System.currentTimeMillis();
        long totalTime = endTime-startTime;
        stringBuffer.append("Time Cost of Worst"+" : "+totalTime+" ms ");

        return stringBuffer.toString();
    }

    public String getRHSPMatching_Best() throws Exception {
        System.out.println("RHSP Matching Best");
        StringBuffer stringBuffer=new StringBuffer();
        int bitsize =11;

//        long[] TimeList = new long[200];
//        long startTime = System.nanoTime();

        //  导入文件
//        String address = "E:\\Gowalla\\Gowlla-lat-lng-20.txt";
        double[][] File = new ReadFiledata().readArray(address);

        //  共享密钥
        int sklength = 6;
        String[] skeylist = new String[sklength];
        skeylist = CreateSecretKey(sklength);

        //  Rider IBFtree
        IBtreeConstruction.IBtree[] treelist = new IBtreeConstruction.IBtree[File.length];
        for(int i=0;i< File.length;i++){
            int[] n = {56};
            String[][] arr = new Generate_Q().Generate_Q(bitsize, n, File[i][1],File[i][2]);
            IBtreeConstruction.IBtree mytree = new IBtreeConstruction().CreateTree(arr, skeylist);
            mytree.ibf = IBFStructure.IBFConstruction.IndistinguishableBloomFilter(10000, skeylist);
            IBtreeConstruction.initTreeNode_Root(mytree);
            // System.out.println("树节点初始化成功");
            treelist[i] = mytree;
        }
        System.out.println(" Riders are ready to go !");

        //  Driver Token
        String[][][] tokenlist = new String[File.length][bitsize][sklength-1];
        for (int i=0;i< File.length;i++){
            String[][] arr_driver = new Generate_Q_driver().Generate_Q(bitsize,i+2,File[i][1],File[i][2]);
            String[] keyword = new Generate_Q().StringStringtoString(arr_driver);
            String[][] Token = new RideResponse_Token().Keyword(keyword,skeylist);
            tokenlist[i] = Token;
        }
        System.out.println(" Drivers are ok ! ");

        //  RHSP Matching

        System.out.println(" RHSP starts Matching !");
        long startTime = System.currentTimeMillis();
        for (int i=0;i<treelist.length;i++){

            boolean tag ;
            String[][] Mar = new QueryProcessing().secondProcessing(tokenlist[i],treelist[i].ibf);
            tag = new QueryProcessing().Query2(Mar,treelist[i]);
//                System.out.println(" rider "+i+" driver "+i+" match "+tag);

        }

        long endTime = System.currentTimeMillis();
        long totalTime = endTime-startTime;
        stringBuffer.append("Time Cost of Best"+" : "+totalTime+" ms ");

        return stringBuffer.toString();
    }

    public long getRHSPMatching_Normal() throws Exception {
        System.out.println("RHSP Matching Normal");
        int bitsize =8;

        long[] TimeList = new long[100];
//        long startTime = System.nanoTime();

        //  导入文件
        String address_used = "E:\\Gowalla\\Gowlla-lat-lng-20.txt";
        double[][] File = new ReadFiledata().readArray(address_used);

        //  共享密钥
        int sklength = 6;
        String[] skeylist = CreateSecretKey(sklength);
        //  Rider IBFtree

        IBtreeConstruction.IBtree[] treelist = new IBtreeConstruction.IBtree[File.length];
        int[] random = createRandom(File.length);
        for(int i=0;i< File.length;i++){

            int x=random[i];  // agree driver id
//            int y;
//
//            if (i== File.length - 1){
//                y = random[0];
//            }else {
//                y = random[i+1];
//            }
//            System.out.println(" 允许 "+x);
//            int[] n_order = createRandom(2,File.length);
//            int[] n_order_2 = {n_order[0],n_order[1],n_order[1]+1,n_order[1]+2};
//            int[] n_order_2 = {x,x+1,x+2,x+3};
            int[] n_order = {x};
            int[] n = OrderINT(n_order);
//            int[] n =createINT(x, File.length);
            String[][] arr = new Generate_Q().Generate_Q(bitsize, n, File[i][1],File[i][2]);
            IBtreeConstruction.IBtree mytree = new IBtreeConstruction().CreateTree(arr, skeylist);
            mytree.ibf = IBFStructure.IBFConstruction.IndistinguishableBloomFilter(10000, skeylist);
            IBtreeConstruction.initTreeNode_Root(mytree);
            // System.out.println("树节点初始化成功");

            treelist[i] = mytree;

        }
        System.out.println(" Riders are ready to go !");


        //  Driver Token
        String[][][] tokenlist = new String[File.length][bitsize][sklength-1];
        for (int i=0;i< File.length;i++){
            String[][] arr_driver = new Generate_Q_driver().Generate_Q(bitsize,i+2,File[i][1],File[i][2]);
            String[] keyword = new Generate_Q().StringStringtoString(arr_driver);
            String[][] Token = new RideResponse_Token().Keyword(keyword,skeylist);
            tokenlist[i] = Token;
        }
        System.out.println(" Drivers are ok ! ");

        //  RHSP Matching

        System.out.println(" RHSP starts Matching !");
        long startTime = System.currentTimeMillis();
        for (int i=0;i<treelist.length;i++){
            for (int j=0;j<tokenlist.length;j++){
                boolean tag ;
                String[][] Mar = new QueryProcessing().secondProcessing(tokenlist[j],treelist[i].ibf);
                tag = new QueryProcessing().Query2(Mar,treelist[i]);
//                System.out.println(" rider "+i+" driver "+j+" match "+tag);
                if (tag == true){
//                    System.out.println(" rider "+i+" driver "+j+" match "+tag);
                    tokenlist[j] = tokenlist[tokenlist.length-1];
                    tokenlist = Arrays.copyOf(tokenlist, tokenlist.length-1);
                    break;
                }
            }
        }

        long endTime = System.currentTimeMillis();
        long totalTime = endTime-startTime;

        return totalTime;
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
