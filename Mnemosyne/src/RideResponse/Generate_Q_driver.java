package RideResponse;


import PrefixEncoding.IndexElementEncoding;
import SpaceEncoding.Projection;

public class Generate_Q_driver {

    // N : the Number of driver
    // n ; the number of this driver
    // bitsize : use for prefix encooding

    public String[][] Generate_Q(int bitsize ,int n,double lat, double lng,int fuctionNum) throws Exception {
        String[] Q1 = new Projection().Compute_concatenate(lat,lng,fuctionNum);
        String[] S = new  IndexElementEncoding().prefix(bitsize,n);
        String[][] Q = new String[ S.length][Q1.length];
        for (int i=0;i<S.length;i++){
            for (int j=0;j< Q1.length;j++){
                Q[i][j]=connect(Q1[j],S[i]);
            }
        }
        return Q;
    }

    public String[][] Generate_Q(int bitsize ,int n,double lat, double lng) throws Exception {
        String[] Q1 = new Projection().Compute_concatenate(lat,lng);
        String[] S = new  IndexElementEncoding().prefix(bitsize,n);
        String[][] Q = new String[ S.length][Q1.length];
        for (int i=0;i<S.length;i++){
            for (int j=0;j< Q1.length;j++){
                Q[i][j]=connect(Q1[j],S[i]);
            }
        }
        return Q;
    }

    public String connect (String s1 ,String s2){   //字符串连接
        String s ;
        StringBuilder sa = new StringBuilder();
        sa.append(s1+s2);
        s= sa.toString();
        return s;
    }

    public  String[] StringStringtoString(String[][] high){
        String[] low = new String[high.length*high[0].length];
        int index = 0;
        for (String[] array : high) {
            for (String element : array) {
                low[index++] = element;
            }
        }
//        for (String element : low) {
//            System.out.print(element + "  ");
//        }
        return low;
    }
}
