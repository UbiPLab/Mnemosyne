package RideResponse;

import IBFStructure.IBFConstruction;
import RideRequest.Generate_Q;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RideResponse_Token {

    public static String[][] Keyword (String[] word,String[] keylist) throws NoSuchAlgorithmException {
        //  加密上传
        String[][] trapdoor = new String[word.length][keylist.length-1];

        MessageDigest mdinstance= MessageDigest.getInstance("sha-256");
        for (int w=0;w<word.length;w++) {                     // twice hash
            for (int i = 0; i < keylist.length - 1; i++) {
                byte[] outbytes = mdinstance.digest((word[w] + keylist[i]).getBytes());      //h_k    length : 16
                trapdoor[w][i]= IBFConstruction.byteToHexString(outbytes);    //trapdoor M element
            }
        }
        return trapdoor;                     // return trapdoor
    }

    public static void show(String[][] root){              //输出 根节点矩阵


        for (int i=0;i< root.length;i++){
            for (int j=0;j<root[0].length;j++){
                System.out.print(root[i][j]+"    ");
            }
            System.out.println();
        }

    }

    public static void main(String[] args) throws Exception {
        int bitsize =5;
        int n =9;
        int N =20;
        double lat_driver =23.9999;
        double lng_driver =96.3967;
        double lat_rider =21;
        double lng_rider =97;

       // String[][] arr_rider = new Generate_Q().Generate_Q(bitsize,n,N,lat_rider,lng_rider);
        String[][] arr_driver = new Generate_Q_driver().Generate_Q(bitsize,n,lat_driver,lng_driver);
        String[] keyword = new Generate_Q().StringStringtoString(arr_driver);

        String[] keylist = new IBFConstruction().CreateSecretKey(6);

        String[][] Token = new RideResponse_Token().Keyword(keyword,keylist);
        System.out.println();
        show(Token);


    }




}
