/**
 * Created by 杨 on 2023/3/25.
 */

import it.unisa.dia.gas.jpbc.Element;

import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
public class AuditProofAggregation {

    public static Element main(String[] args) {
        long startTime = System.currentTimeMillis();
        Pairing pairing = PairingFactory.getPairing("a.properties");

        int m = 10; // number of proofs
        int FTV = 1; // number of faulty tag values
        Element[] proofs = new Element[m];

        // initialize proofs with random values
        for (int i = 0; i < m; i++) {
            proofs[i] = pairing.getG1().newRandomElement();
        }

        // initialize c value
        int c = m - FTV;

        // initialize product and sum values
        Element prod = pairing.getG1().newOneElement();
        Element sum = pairing.getG1().newZeroElement();

        // calculate product and sum
        for (int i = 0; i < m; i++) {
            if (i < c) {
                prod.mul(proofs[i]);
            }
            sum.add(proofs[i]);
        }

        // calculate aggregate proof
        Element P_agg = pairing.getG1().newElement();
        P_agg.set(prod);
        P_agg.mul(sum);

        // print the result
        System.out.println("Aggregate Proof: " + P_agg);
        long endTime = System.currentTimeMillis();

        long executionTime = endTime - startTime;
        System.out.println("聚合验证耗时: " + executionTime + "ms");
        return prod;
    }


}

