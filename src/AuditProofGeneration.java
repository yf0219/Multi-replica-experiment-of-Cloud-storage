/**
 * Created by 杨 on 2023/3/24.
 */

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.field.z.ZrElement;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

public class AuditProofGeneration {


    public static <G1Element extends Element> Element[] generateProofs(Element[] challengeSet, String[] replicaIDs, Element[] si, Element[] ci, Element[] yi, Element[] zi) {
        long startTime = System.currentTimeMillis();
        int m = challengeSet.length;
        Element[] proofs = new Element[m];
        Pairing pairing = PairingFactory.getPairing("a.properties");
        Field<G1Element> G1 = pairing.getG1();
        Field<ZrElement> Zr = pairing.getZr();
        for (int i = 0; i < m; i++) {
            // 检索第i个复制副本的参数
            Element s_i = si[i];
            Element c_i = ci[i];
            Element y_i = yi[i];
            Element y_i_element = pairing.getZr().newElement();
            y_i_element.setFromBytes(y_i.toBytes());
            Element z_i = zi[i];

            // 计算挑战的哈希值
            Element h_i = Utils.hashToZp(c_i);

            // 计算证明
            Element proof_i = s_i.duplicate();
            proof_i.mul(h_i);
            proof_i.add(y_i_element);
            Element zi_pow_h = pairing.getZr().newElement();

            zi_pow_h.setFromHash(Utils.hashToBytes(z_i), 0, Utils.hashToBytes(z_i).length);
            zi_pow_h.powZn(h_i);
            proof_i.add(zi_pow_h);


            // 将证明存储在数组中
            proofs[i] = proof_i;
        }
        long endTime = System.currentTimeMillis();

        long executionTime = endTime - startTime;
        System.out.println("证明生成耗时: " + executionTime + "ms");
        return proofs;
    }
    public static void main(String[] args) {
        // Set up inputs
        Pairing pairing = PairingFactory.getPairing("a.properties");
        long startTime = System.currentTimeMillis();
        int m = 100;
        Element[] challengeSet = new Element[m];
        String[] replicaIDs = new String[m];
        Element[] si = new Element[m];
        Element[] ci = new Element[m];
        Element[] yi = new Element[m];
        Element[] zi;
        zi = new Element[m];

        for (int i = 0; i < m; i++) {
            challengeSet[i] = pairing.getG1().newRandomElement();
            replicaIDs[i] = "replica" + i;
            si[i] = pairing.getZr().newRandomElement();
            ci[i] = pairing.getG1().newRandomElement();
            yi[i] = pairing.getG1().newRandomElement();
            zi[i] = pairing.getG1().newRandomElement();

        }
        long endTime = System.currentTimeMillis();

        long executionTime = endTime - startTime;
        System.out.println("证明生成耗时: " + executionTime + "ms");

    }

}

