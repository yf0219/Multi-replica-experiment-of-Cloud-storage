/**
 * Created by 杨 on 2023/3/24.
 */

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

public class AuditProofVerification {
    public static boolean verifyProof(Element P_agg, Element[] tags, Element chal, int FTV) {
        long startTime = System.currentTimeMillis();
        int m = tags.length;
        Pairing pairing = PairingFactory.getPairing("a.properties");
        // 初始化生成的值
        Element prod = pairing.getG1().newOneElement();

        // 计算生成值
        for (int i = 0; i < m; i++) {
            prod = prod.mul(tags[i].pow(chal.powZn(pairing.getZr().newElement(i+1)).toBigInteger()));
        }

        // 验证审计证明
        Element left = Utils.pairing(P_agg, pairing.getG2().newElement().setToRandom());
        Element right = Utils.pairing(prod, pairing.getG2().newElement().setToRandom());
        long endTime = System.currentTimeMillis();

        long executionTime = endTime - startTime;
        System.out.println("验证耗时: " + executionTime + "ms");
        return left.isEqual(right);
    }
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Pairing pairing = PairingFactory.getPairing("a.properties");
        int m = 10;
        Element[] tags = new Element[m];
        for (int i = 0; i < m; i++) {
            tags[i] = Utils.getRandG1();
        }

        Element chal = Utils.getRandZr();

        int FTV = 5;
        Element[] proofs = new Element[m];
        for (int i = 0; i < m; i++) {
            proofs[i] = tags[i].duplicate();
            proofs[i] = tags[i].powZn(chal.powZn(PairingFactory.getPairing("a.properties").getZr().newElement(i+1)).getImmutable()).getImmutable();

        }

        Element P_agg = AuditProofAggregation.main(args);
        Element left = Utils.pairing(P_agg, pairing.getG2().newElement().setToRandom());
        if (P_agg == null || tags == null || chal == null) {
            throw new IllegalArgumentException("Invalid input");
        }
        if (left == null) {
            throw new RuntimeException("Invalid pairing result");
        }
        if (P_agg == null) {
            throw new RuntimeException("Invalid aggregation result");
        }

        boolean result = verifyProof(P_agg, tags, chal, FTV);
        long endTime = System.currentTimeMillis();

        long executionTime = endTime - startTime;
        System.out.println("验证耗时: " + executionTime + "ms");
        System.out.println("Verification Result: " + result);
    }
}





