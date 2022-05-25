package IBFStructure;



import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class IBtreeConstruction {
    public int ibf_length = 10000;

    public static class IBtree{
        public IBFConstruction.IBF ibf;
        public String[][] data;
        public double height;
        public boolean tag;
        public IBtree left;
        public IBtree right;
    }

    public IBtree CreateTree(String[][] root ,String[] keylist) throws NoSuchAlgorithmException {
        IBtree treeNode = null;
        if (root.length == 0) {                      //集合为空
            return treeNode;
        }
        if (root.length == 1) {                      //集合内仅有一个元素，作为叶子节点
            treeNode = new IBtree();
            treeNode.data = root;
            treeNode.height = 1;
            treeNode.ibf = IBFStructure.IBFConstruction.IndistinguishableBloomFilter(ibf_length, keylist);
            return treeNode;
        } else {
            int mid = root.length / 2;                  //取中间
            int little = root.length % 2;               //判断奇偶
            treeNode = new IBtree();              //该节点赋值
            treeNode.data = root;
            treeNode.height = (Math.ceil(Math.log(root.length) / Math.log(2))) + 1;    //该节点高度 (log n / log 2)向上取整+1

            String[][] leftNums = new String[mid + little][root[0].length]; //左子树取前半集合
            for (int i = 0; i < leftNums.length; i++) {
                for (int j = 0; j < root[0].length; j++) {
                    leftNums[i][j] = root[i][j];
                }
            }
            String[][] rightNums = new String[root.length - mid - little][root[0].length];  //右子树取右半集合
            for (int i = 0; i < rightNums.length; i++) {
                for (int j = 0; j < root[0].length; j++) {
                    rightNums[i][j] = root[i + mid + little][j];
                }
            }
            treeNode.ibf = IBFStructure.IBFConstruction.IndistinguishableBloomFilter(ibf_length, keylist);
            treeNode.left = CreateTree(leftNums,keylist);
            treeNode.right = CreateTree(rightNums,keylist);
//            treeNode.ibf = IBF.IBFConstruction.IndistinguishableBloomFilter(10000, keylist);

            return treeNode;
        }
    }

        public static void initTreeNode(IBtree treeNode) throws NoSuchAlgorithmException {
            if (treeNode==null){
                return;
            }
            for (int h=2;h<=treeNode.height;h++) {
                initTreeHeightNode(treeNode,h);
                initTreeHeightNode(treeNode.left,h);
                initTreeHeightNode(treeNode.right,h);
            }
        }
//        public static void initTreeNode_list(IBtree treeNode,IBtree[] levelpre) throws NoSuchAlgorithmException {
//            if (treeNode==null){
//                return;
//            }
//            for (int h=2;h<=treeNode.height;h++) {
//                for (int i = 0; i < levelpre.length; i++) {
//                    initTreeHeightNode(levelpre[i],h);
//                }
////                initTreeHeightNode(treeNode,h);
////                initTreeHeightNode(treeNode.left,h);
////                initTreeHeightNode(treeNode.right,h);
//            }
//        }


    public static void initTreeHeightNode(IBtree treeNode,int height) throws NoSuchAlgorithmException {
        if (treeNode==null){
            return;
        }
        if (treeNode.tag == false) {
            if (treeNode.height == height) {
                for (int i = 0; i < treeNode.ibf.twinlist.length; i++) {
                    treeNode.ibf.twinlist[i][0] = together(treeNode.left.ibf.twinlist[i][0], treeNode.right.ibf.twinlist[i][0]);
                    treeNode.ibf.twinlist[i][1] = together(treeNode.left.ibf.twinlist[i][1], treeNode.right.ibf.twinlist[i][1]);
                }
            }
        }
        initTreeHeightNode(treeNode.left,height);
        initTreeHeightNode(treeNode.right,height);
    }

    public static void initTreeLeafNode(IBtree treeNode) throws NoSuchAlgorithmException {
        if (treeNode==null){
            return;
        }
        if (treeNode.height==1){
            for (int i=0;i<treeNode.data.length;i++){
                for (int j=0;j<treeNode.data[0].length;j++){
                    IBFConstruction.insert(treeNode.ibf,treeNode.data[i][j]);
                    treeNode.tag=true;
                }
            }
        }
        initTreeLeafNode(treeNode.left);
        initTreeLeafNode(treeNode.right);
    }

    public static void initTreeNode_Root(IBtree treeNode){
        for (int i=0;i<treeNode.data.length;i++){
            for (int j=0;j<treeNode.data[0].length;j++){
                IBFConstruction.insert(treeNode.ibf,treeNode.data[i][j]);
                treeNode.tag=true;
            }
        }
    }



    public static byte together(byte a, byte b){               //取并集
//        if (a==b){
//            return a;
//        }
//        else {
//            if (a==1)
//                return a;
//            else
//                return b;
//        }
        byte m = 1;
        if( (a==1) || (b==1)){
            return m;
        }else {
            return a;
        }
    }

    public static void preOrder(IBtree node) {     //二分搜索树的前序遍历(前序遍历：根结点 ---> 左子树 ---> 右子树)
        if (node == null) {
            //System.out.println("空");
            return;
        }
        for (int i = 0; i < node.data.length; i++)
            System.out.print(node.data[i][0] + "  ");
        System.out.println();
        //System.out.println(node.height);
        preOrder(node.left);
        preOrder(node.right);
    }



    public static void leaf(IBtree node) {         //输出叶子结点
        if (node == null) {
            return;
        }
        if (node.height == 1)
            System.out.print(node.data[0][0] + "  ");
        leaf(node.left);
        leaf(node.right);
    }

    public static class Node {             //      终端节点    链表  从树 最左侧 至 最右侧
        public IBtree treenode;
        //存放结点的变量,默认为null
        public Node next;
        //构造方法，在构造时就能够给data赋值
        public Node(){
            this.next = null;
        }

    }
    public static void insert_head(IBtree treenode,Node h){        //  插入链表
            Node p = new Node();
            p.treenode=treenode;
            p.next = h.next;//改变指针
            h.next = p;//使h的直接后继为p
    }

    public static void display(Node h) {                   //  遍历链表
        if (h == null) {
            System.out.println("list is null");
        } else if (h.next == null) {
            System.out.println(h.treenode.data[0][0]);      //  单长
        } else {
            while (h.next != null) {
                System.out.print(h.next.treenode.data[0][0] + " ");//不遍历头结点
                h = h.next;
            }
        }
    }

    public static void createlist(IBtree node, Node h){

        if (node.height == 1)
            insert_head(node,h);
        else {
            createlist(node.left, h);
            createlist(node.right, h);
        }
    }


    public static ArrayList<IBtree> PrintFromTopToBottom(IBtree root) {
        ArrayList<IBtree> list=new ArrayList<IBtree>();  //存放结果
        Queue<IBtree> queue= new LinkedList<IBtree>();   //辅助队列
        if (root!=null){
            //根节点入队
            queue.offer(root);
        }
        //队列不为空，执行循环
        while (!queue.isEmpty()){
            IBtree node=queue.poll();
            list.add(node);     //将队列元素输出

            //如果有左节点，就把左节点加入
            if (node.left!=null){
                queue.offer(node.left);
            }
            //如果有右节点，就把右节点加入
            if(node.right!=null){
                queue.offer(node.right);
            }
        }
        return list;
    }

    public static IBtree[] ListToString1(List<IBtree> list){
        IBtree[] f= new IBtree[list.size()];
        for(int i=0;i<f.length;i++){
            f[i]=list.get(i);
//            System.out.print(" "+f[i]+" ");
        }
//        System.out.println();
        return f;
    }


}
