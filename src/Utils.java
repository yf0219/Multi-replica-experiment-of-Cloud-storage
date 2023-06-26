import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * Created by 杨 on 2023/3/15.
 */
public class Utils {


    //私钥生成中用到的字符串到字节数组的转化方法
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
    //系统建立中生成主密钥主公钥用到的Element[]和数组类型转化方法
    public static byte[] gatherBytes(Element[] elements) throws IOException {
        int length = 0;
        for (Element e : elements) {
            length += e.getLengthInBytes();
        }
        byte[] result = new byte[length];
        int offset = 0;
        for (Element e : elements) {
            byte[] bytes = e.toBytes();
            System.arraycopy(bytes, 0, result, offset, bytes.length);
            offset += bytes.length;
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for (Element element : elements) {
            byte[] elementBytes = element.toBytes();
            try {
                outputStream.write(elementBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return outputStream.toByteArray();


    }
    // sha-256哈希算法

    public static byte[] sha256(byte[] input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(input);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    // 计算挑战值用到的Zp域的哈希方法
    public static Element hashToZp(Element input) {
        // 字节数组的哈希输入
        byte[] inputBytes = input.toBytes();

        // 用SHA-256计算输入的哈希
        byte[] hashBytes = sha256(inputBytes);

        // 将哈希转换为范围为[0，q-1]的Zr元素
        return PairingFactory.getPairing("a.properties").getZr().newElementFromHash(hashBytes, 0, hashBytes.length);
    }
    public static Element generateRandomElement(Field field) {
        return field.newRandomElement();
    }
    // 审计证明生成中使用SHA-256对字节进行哈希
    public static byte[] hashToBytes(Element element) {
        MessageDigest sha256 = null;
        try {
            sha256 = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sha256.digest(element.toBytes());
    }
    private static Pairing pairing;

    public static void init(Pairing p) {
        pairing = p;
    }

    public static Element pairing(Element e1, Element e2) {

        return pairing.pairing(e1, e2);
    }


    public static Element getRandG1() {
        Pairing pairing = PairingFactory.getPairing("a.properties");
        Element g1 = pairing.getG1().newRandomElement();
        return g1;
    }
    public static Element getRandZr() {
        Pairing pairing = PairingFactory.getPairing("a.properties");
        Element Zr = pairing.getZr().newRandomElement();
        return Zr;
    }
}
   

