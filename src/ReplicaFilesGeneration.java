/**
 * Created by 杨 on 2023/3/15.
 */
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.util.io.Base64;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ReplicaFilesGeneration {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        long startTime = System.currentTimeMillis();
        Pairing pairing = PairingFactory.getPairing("a.properties");

        // 加载EHR文件
        byte[] ehrFileBytes = Files.readAllBytes(new File("ehr_file.txt").toPath());

        // 计算文件的哈希值
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] fileHashBytes = digest.digest(ehrFileBytes);

        // 生成EHR文件的云副本
        Element[] cloudDuplicates = new Element[30];
        for (int i = 0; i < 30; i++) {
            Element ri = pairing.getZr().newRandomElement();
            Element ci = pairing.getG1().newElementFromHash(fileHashBytes, 0, fileHashBytes.length)
                    .powZn(ri);
            cloudDuplicates[i] = ci;
        }

        // 将云副本编码为base64字符串
        String[] cloudDuplicateStrings = new String[30];
        for (int i = 0; i < 30; i++) {
            cloudDuplicateStrings[i] = Base64.encodeBytes(cloudDuplicates[i].toBytes());
        }

        System.out.println("云副本生成完成:");
        for (int i = 0; i < 30; i++) {
            System.out.println("cloud_duplicate_ " + (i+1) + ": " + cloudDuplicateStrings[i]);
        }
        long endTime = System.currentTimeMillis();

        long executionTime = endTime - startTime;
        System.out.println("云副本生成耗时: " + executionTime + "ms");
    }
}
