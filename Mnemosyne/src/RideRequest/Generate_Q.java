package RideRequest;

import PrefixEncoding.IndexElementEncoding;
import SpaceEncoding.Projection;

public class Generate_Q {

    // N : the Number of driver
    // n ; the number of this driver
    // bitsize : use for prefix encooding

    public String[] Generate_S1(int bitsize , int n ,int N){     //排除 单一司机
        String [] s1 = IndexElementEncoding.range(bitsize,1,n-1);
        String [] s2 = IndexElementEncoding.range(bitsize,n+1,N);
        String [] S = new String[s1.length+s2.length];
        // compute full range S
        for (int i = 0; i < s1.length ;i++){
            S[i] = s1 [i];
        }
        for ( int i = 0; i<s2.length;i++){
            S[s1.length+i] = s2[i] ;
        }
        System.out.println("Generate_S");
        for (int i=0;i<S.length;i++)
            System.out.print(S[i]+"   ");
        System.out.println();
        return S;
    }



    public String[] Generate_S(int bitsize , int[] n ){     //排除    司机集合
        int max = 64;
        String[] s_start;
        String[] s_end;
        if (n[0] == 1){
            s_start = null;
        }else {
            s_start = IndexElementEncoding.range(bitsize,1,n[0]-1);
        }

        if (n[n.length-1] == max){
            s_end = null;
        }else {
            s_end = IndexElementEncoding.range(bitsize,n[n.length-1]+1,max);
        }

        String[] S;
        if (s_start == null){
            if (n.length == 1){
                S = new String[s_end.length];
                for (int i=0;i<s_end.length;i++){
                    S[i] = s_end[i];
                }
                return S;
            }else {
                String[] s_temp;
                s_temp = IndexElementEncoding.range(bitsize,n[0]+1,n[1]-1);
                S = new String[s_temp.length];
                for (int i=0;i<s_temp.length;i++){
                    S[i] = s_temp[i];
                }
                for (int i=1;i<n.length-1;i++){
                    if (n[i] + 1 < n[i+1]){
                        s_temp = IndexElementEncoding.range(bitsize,n[i]+1,n[i+1]-1);
                        S=TwoStringToOneString(S,s_temp);
                    }
                }
                if (s_end == null){
                    return S;
                }else {
                    S=TwoStringToOneString(S,s_end);
                    return S;
                }
            }
        }else {
            S = new String[s_start.length];
            for (int i=0;i<s_start.length;i++){
                S[i] = s_start[i];
            }
            for (int i=0;i<n.length-1;i++){
                if (n[i] + 1 < n[i+1]){
                    String[] s_temp = IndexElementEncoding.range(bitsize,n[i]+1,n[i+1]-1);
                    S=TwoStringToOneString(S,s_temp);
                }
            }
            if (s_end == null){
                return S;
            }else {
                S=TwoStringToOneString(S,s_end);
                return S;
            }
        }




//        System.out.println("Generate_S");
//        for (int i=0;i<S.length;i++)
//            System.out.print(S[i]+"   ");
//        System.out.println();
//        return S;
    }


    public String[] Generate_S_old(int bitsize , int[] n ){     //排除    司机集合

        String[] s_start = IndexElementEncoding.range(bitsize,0,n[0]-1);
        String[] s_end = IndexElementEncoding.range(bitsize,n[n.length-1]+1,20);
        String[] S = new String[s_start.length];
        for (int i=0;i<s_start.length;i++){
            S[i] = s_start[i];
        }
        for (int i=0;i<n.length-1;i++){
            if (n[i] + 1 < n[i+1]){
                String[] s_temp = IndexElementEncoding.range(bitsize,n[i]+1,n[i+1]-1);
                S=TwoStringToOneString(S,s_temp);
            }
        }
        S=TwoStringToOneString(S,s_end);

//        System.out.println("Generate_S");
//        for (int i=0;i<S.length;i++)
//            System.out.print(S[i]+"   ");
//        System.out.println();
        return S;
    }







    public String[][] Generate_Q(int bitsize , int[] n ,double lat, double lng,int functionNum){
        String[] Q1 = new Projection().Compute_concatenate(lat,lng,functionNum);
        String[] S = Generate_S(bitsize,n);
        String[][] Q = new String[ S.length][Q1.length];
        for (int i=0;i<S.length;i++){
            for (int j=0;j< Q1.length;j++){
                Q[i][j]=connect(Q1[j],S[i]);
            }
        }
        return Q;
    }

    public String[][] Generate_Q(int bitsize , int[] n ,double lat, double lng){
        String[] Q1 = new Projection().Compute_concatenate(lat,lng);
        String[] S = Generate_S(bitsize,n);
        String[][] Q = new String[ S.length][Q1.length];
        for (int i=0;i<S.length;i++){
            for (int j=0;j< Q1.length;j++){
                Q[i][j]=connect(Q1[j],S[i]);
            }
        }
        return Q;
    }

    public String connect(String s1 ,String s2){   //字符串连接
        String s ;
        StringBuilder sa = new StringBuilder();
        sa.append(s1+s2);
        s= sa.toString();
        return s;
    }

    public static String[] StringStringtoString(String[][] high){
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

    public String[] TwoStringToOneString(String[] low,String[] high){
        String[] full = new String[low.length+high.length];
        for (int i=0;i< low.length;i++){
            full[i]=low[i];
        }
        for (int i=0;i<high.length;i++){
            full[i+low.length]=high[i];
        }
        return full;
    }
}
