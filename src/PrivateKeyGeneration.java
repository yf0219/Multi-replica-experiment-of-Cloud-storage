/**
 * Created by 杨 on 2023/3/11.
 */

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

public class PrivateKeyGeneration {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Pairing pairing = PairingFactory.getPairing("a.properties");

        // 加载公共参数pp、主公钥mpk和主密钥msk
        Element pp = pairing.getG1().newElementFromBytes(Utils.hexStringToByteArray("............................................"));
        Element mpk = pairing.getG1().newElementFromBytes(Utils.hexStringToByteArray(".............................................................................."));
        Element msk = pairing.getZr().newElementFromBytes(Utils.hexStringToByteArray("......................................................................................................"));

        // 定义患者身份ID
        String ID = "Patient123";

        // 基于患者身份ID生成私钥
        Element r = pairing.getZr().newRandomElement();
        Element SKID = pp.duplicate().mul(mpk.powZn(r)).powZn(msk.duplicate().mul(pairing.getZr().newElementFromBytes(Utils.sha256(ID.getBytes()))));

        System.out.println("私钥生成完成:");
        System.out.println("患者私钥SKID: " + SKID.toString());




        long endTime = System.currentTimeMillis();

        long executionTime = endTime - startTime;
        System.out.println("密钥生成耗时: " + executionTime + "ms");

    }
}
