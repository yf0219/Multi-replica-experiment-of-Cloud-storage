import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.jpbc.PairingParameters;
import it.unisa.dia.gas.jpbc.PairingParametersGenerator;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.pairing.a.TypeACurveGenerator;

import java.io.IOException;
import java.util.Arrays;

public class SystemInitialization {



    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        int k = 80; // 安全参数

        // 初始化配对参数

        PairingParametersGenerator paramsGenerator1 = new TypeACurveGenerator(160,512);
        TypeACurveGenerator paramsGenerator2 = new TypeACurveGenerator(k, k + 1);
        PairingParameters pairingParams2 = paramsGenerator2.generate();
        PairingParameters pairingParams1 = paramsGenerator1.generate();

        // 初始化配对
        Pairing pairing = PairingFactory.getPairing("a.properties");

        // 生成主密钥
        Element alpha = pairing.getZr().newRandomElement();
        Element g = pairing.getG1().newRandomElement();
        Element beta = g.powZn(alpha);
        Element gamma = pairing.getZr().newRandomElement();
        Element delta = gamma.powZn(alpha);
        Element z = pairing.getZr().newRandomElement();
        Element omega = pairing.getGT().newRandomElement();
        Element y = omega.powZn(z);

        // 构建公共参数pp和主公钥mpk
        Element g_alpha = g.powZn(alpha);
        Element g_gamma = g.powZn(gamma);

        Element pp = pairing.getG1().newElement();
        pp.setToOne();
        Element[] mpkArray = new Element[]{g_alpha, g_gamma, beta, delta, y};
        int mpk = pairing.getG1().newElement().setFromBytes(Utils.gatherBytes(mpkArray));
        // 生成主密钥msk
        Element[] mskArray = new Element[]{g, gamma, alpha, z};
        int msk = pairing.getZr().newElement().setFromBytes(Utils.gatherBytes(mskArray));

        System.out.println("系统建立完成:");
        System.out.println("系统公共参数 pp: " + pp.toString());
        System.out.println("主公钥 mpk: " +Arrays.toString(mpkArray));
        System.out.println("主密钥 msk: " + Arrays.toString(mskArray));
        long endTime = System.currentTimeMillis();

        long executionTime = endTime - startTime;
        System.out.println("系统建立耗时: " + executionTime + "ms");
    }
}