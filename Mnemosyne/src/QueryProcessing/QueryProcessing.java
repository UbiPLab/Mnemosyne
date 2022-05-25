package QueryProcessing;

import IBFStructure.IBFConstruction;
import IBFStructure.IBtreeConstruction;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import static IBFStructure.IBFConstruction.addBytes;

public class QueryProcessing {

    public static String[][] secondProcessing(String[][] Trapdoor, IBFConstruction.IBF ibf) throws NoSuchAlgorithmException {
        String[][] M = new String[Trapdoor.length][Trapdoor[0].length];

        for (int i=0;i< M.length;i++){
            for (int j=0;j<M[0].length;j++){
                byte[] outbytes = new QueryProcessing().hexStringToBytes(Trapdoor[i][j]);
                BigInteger bi = new BigInteger(1, outbytes);
                int twinindex = bi.mod(BigInteger.valueOf(ibf.length)).intValue();

                byte[] hkp1 = ibf.mdinstance.digest(addBytes(outbytes, ibf.Keylist[ibf.Keylist.length-1].getBytes()));//h_k+1
                BigInteger hkp1bi = new BigInteger(1, hkp1);
                byte[] sha1bytes = ibf.H.digest(hkp1bi.xor(BigInteger.valueOf(ibf.rb)).toByteArray());//sha1_xor
                int location = new BigInteger(1, sha1bytes).mod(BigInteger.valueOf(2)).intValue();//mod2
                if (location == 0) {
                    StringBuffer s=new StringBuffer();
                    s.append(twinindex+"|"+1+"|"+0);
                    M[i][j]=s.toString();
                } else {
                    StringBuffer s=new StringBuffer();
                    s.append(twinindex+"|"+0+"|"+1);
                    M[i][j]=s.toString();
                }
            }
        }
        return M;
    }


    public static Boolean Query2(String[][] Mar, IBtreeConstruction.IBtree root){
        boolean tag = false;
        if (root==null){
            return false;
        }
        // copy Mar
//        String[][] M = Mar.clone();
        String[][] M = new String[Mar.length][Mar[0].length];
        for (int i=0;i< M.length;i++){
            for (int j=0;j<M[0].length;j++){
                M[i][j]=Mar[i][j];
            }
        }

        int k=0;

        for (int i=0;i< M.length;i++){
            int time=0;
            for (int j=0;j<M[0].length;j++){
                int index = Integer.valueOf(M[i][j].substring(0,M[i][j].length()-4)).intValue();
//                int location00 = Integer.valueOf(M[i][j].substring(M[i][j].length()-3,M[i][j].length()-2)).intValue();
//                int location01 = Integer.valueOf(M[i][j].substring(M[i][j].length()-1,M[i][j].length())).intValue();
//                System.out.print(index+"|"+location00+"|"+location01+" query ");
//                System.out.print(root.ibf.twinlist[index][0]+"|"+root.ibf.twinlist[index][1]+"  tree  ");
//                System.out.println();


                if ((root.ibf.twinlist[index][0]==1) || (root.ibf.twinlist[index][1]==1)){
                    time++;
//                    System.out.print(index+"|"+location00+"|"+location01+" query ");
//                    System.out.print(root.ibf.twinlist[index][0]+"|"+root.ibf.twinlist[index][1]+"  tree  ");
//                    System.out.println();
                }
            }
            if (time==M[0].length){
                k++;

            }
        }
        if (k>0){
            //System.out.println("根节点存在交集 in");
            tag = true;
            }
        else {
            //System.out.println(" 根节点不存在交集 ");
            tag= false;

        }
        return tag;
    }





    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }


}
