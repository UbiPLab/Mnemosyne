package IBFStructure;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class IBFConstruction {


    public static class IBF{
        public int length;
        public byte[][] twinlist;
        public String[] Keylist;
        public String RandomNumber;
        public int rb;
        public MessageDigest mdinstance;
        public MessageDigest H;
    }

    public static IBF IndistinguishableBloomFilter(int ibfLength, String[] keylist) throws NoSuchAlgorithmException {
        IBF ibf = new IBF();
        ibf.length=ibfLength;
        ibf.Keylist=keylist;
//        ibf.RandomNumber=getRandomString(128);
        ibf.twinlist = new byte[ibfLength][2];
        ibf.rb=new Random().nextInt();
        ibf.mdinstance=MessageDigest.getInstance("sha-256");
        ibf.H=MessageDigest.getInstance("sha-384");
        return ibf;
    }

    /*
            use  AES  create Secret key
    public static String[] CreateSecretKey(int keylength){
        String[] keylist = new String[keylength];
        KeyGenerator kg;
        try {
            kg = KeyGenerator.getInstance("AES");
            kg.init(128);//要生成多少位，只需要修改这里即可128, 192或256
            for(int i=0;i<keylength;i++) {
                SecretKey sk = kg.generateKey();    //密钥
                byte[] b = sk.getEncoded();     //加密
                String skey=byteToHexString(b); //转换形式
               // System.out.println("第"+i+"个密钥："+skey);
                keylist[i]=skey;                                        //  密钥组 k+1个
            }
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return keylist;
    }

     */
    public static String[] CreateSecretKey(int keylength){
        String[] keylist = new String[keylength];
        StringBuffer bigstring=new StringBuffer();
        Random random = new Random();
        int length=128;//自定义的位数
        for (int j=0;j<keylist.length;j++) {
            for (int i = 0; i < length; i++) {
                bigstring.append(random.nextInt(10));
            }
            keylist[j]=bigstring.toString();
        }
        return keylist;
    }


    public static String byteToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String strHex = Integer.toHexString(bytes[i]);
            if (strHex.length() > 3) {
                sb.append(strHex.substring(6));
            } else {
                if (strHex.length() < 2) {
                    sb.append("0" + strHex);
                } else {
                    sb.append(strHex);
                }
            }
        }
        return sb.toString();
    }
    public static String getRandomString(int length) {      //产生随机数
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
    public static String getRandomString2(int length) {      //产生数字型随机数0-9
        String str = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(10);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }




    //        for (int i = 0 ;i< keylist.length;i++){
//            ibf.Keylist[i] = keylist[i];
//        }
//        ibf.mdinstance=MessageDigest.getInstance("md5");

    public static void showIBF(IBF ibf) {     //输出IBF
        int firstline[] = new int[ibf.length];
        int secondline[] = new int[ibf.length];
        for (int i = 0; i < ibf.length; i++) {
            firstline[i] = ibf.twinlist[i][0];
            secondline[i] = ibf.twinlist[i][1];
        }
        System.out.println("IBF Content: ");
        for (int i = 0; i < ibf.length; i++) {
            System.out.print(firstline[i] + " ");
        }
        System.out.println("");
        for (int i = 0; i < ibf.length; i++) {
            System.out.print(secondline[i] + " ");
        }
    }

    public static void insert(IBF ibf, String data) {
        for (int i = 0; i < ibf.Keylist.length-1; i++) {
            byte[] outbytes = ibf.mdinstance.digest((data + ibf.Keylist[i]).getBytes());
            //System.out.println(IndistinguishableBloomFilter.byteToHexString(outbytes));
            BigInteger bi = new BigInteger(1, outbytes);
            int twinindex = bi.mod(BigInteger.valueOf(ibf.length)).intValue();
            //now we get k twins
         //   System.out.println("Twin index " + twinindex);
            //for each twin, compute the chosen location
            byte[] hkp1 = ibf.mdinstance.digest(addBytes(outbytes, ibf.Keylist[ibf.Keylist.length-1].getBytes()));//h_k+1
            BigInteger hkp1bi = new BigInteger(1, hkp1);
            byte[] sha1bytes = ibf.H.digest(hkp1bi.xor(BigInteger.valueOf(ibf.rb)).toByteArray());//sha1_xor
            int location = new BigInteger(1, sha1bytes).mod(BigInteger.valueOf(2)).intValue();//mod2
         //   System.out.println("Location " + location);
            if (location == 0) {
                ibf.twinlist[twinindex][0] = 1;
                ibf.twinlist[twinindex][1] = 0;
            } else {
                ibf.twinlist[twinindex][1] = 1;
                ibf.twinlist[twinindex][0] = 0;
            }
        }
    }

    public static byte[] addBytes(byte[] data1, byte[] data2) {
        byte[] data3 = new byte[data1.length + data2.length];
        System.arraycopy(data1, 0, data3, 0, data1.length);
        System.arraycopy(data2, 0, data3, data1.length, data2.length);
        return data3;

    }
}





