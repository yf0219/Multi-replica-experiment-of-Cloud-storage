/**
 * Created by 杨 on 2023/3/19.
 */
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.jpbc.Field;
public class LaunchIntegrityChallenge {


    public static Element[] launchChallenge(int m, String[] replicaIDs, String[] CSPs, Element[] r, Element[] w, Element[] t, Element[] d) {
        long startTime = System.currentTimeMillis();
        Pairing pairing = PairingFactory.getPairing("a.properties");
        Field<Element> field = pairing.getG1();
        Element[] challengeSet = new Element[m];
        for (int i = 0; i < m; i++) {
            // 生成随机挑战c_i
            Element c_i = field.newRandomElement();

            // 计算挑战的哈希值
            Element h_i = Utils.hashToZp(c_i);

            // 计算第i个副本的挑战
            Element ci_ri = h_i.mulZn(pairing.getZr().newElementFromBytes(r[i].toBytes()));
            Element ci_wi = h_i.mulZn(pairing.getZr().newElementFromBytes(w[i].toBytes()));
            Element ci_ti = h_i.mulZn(pairing.getZr().newElementFromBytes(t[i].toBytes()));
            Element ci_di = h_i.mulZn(pairing.getZr().newElementFromBytes(d[i].toBytes()));

            // 构造挑战元组
            Element[] challengeTuple = {c_i, ci_ri, ci_wi, ci_ti, ci_di};

            // 将挑战元组发送到相应的CSP
            String cspID = CSPs[i];
            // 用于向ID为cspID的CSP发送挑战元组

            // 将挑战值存储在挑战集中
            challengeSet[i] = c_i;
        }
        long endTime = System.currentTimeMillis();

        long executionTime = endTime - startTime;
        System.out.println("生成挑战耗时: " + executionTime + "ms");
        return challengeSet;
    }
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        int m = 50;
        String[] replicaIDs = {"1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
                "10",
                "11",
                "12",
                "13",
                "14",
                "15",
                "16",
                "17",
                "18",
                "19",
                "20",
                "21",
                "22",
                "23",
                "24",
                "25",
                "26",
                "27",
                "28",
                "29",
                "30",
                "31",
                "32",
                "33",
                "34",
                "35",
                "36",
                "37",
                "38",
                "39",
                "40",
                "41",
                "42",
                "43",
                "44",
                "45",
                "46",
                "47",
                "48",
                "49",
                "50",
        };
        String[] CSPs = {"CSP1",
                "CSP2",
                "CSP3",
                "CSP4",
                "CSP5",
                "CSP6",
                "CSP7",
                "CSP8",
                "CSP9",
                "CSP10",
                "CSP11",
                "CSP12",
                "CSP13",
                "CSP14",
                "CSP15",
                "CSP16",
                "CSP17",
                "CSP18",
                "CSP19",
                "CSP20",
                "CSP21",
                "CSP22",
                "CSP23",
                "CSP24",
                "CSP25",
                "CSP26",
                "CSP27",
                "CSP28",
                "CSP29",
                "CSP30",
                "CSP31",
                "CSP32",
                "CSP33",
                "CSP34",
                "CSP35",
                "CSP36",
                "CSP37",
                "CSP38",
                "CSP39",
                "CSP40",
                "CSP41",
                "CSP42",
                "CSP43",
                "CSP44",
                "CSP45",
                "CSP46",
                "CSP47",
                "CSP48",
                "CSP49",
                "CSP50",
        };

        Element[] r = new Element[m];
        Element[] w = new Element[m];
        Element[] t = new Element[m];
        Element[] d = new Element[m];

        Pairing pairing = PairingFactory.getPairing("a.properties");

        for (int i = 0; i < m; i++) {
            r[i] = pairing.getG1().newRandomElement();
            w[i] = pairing.getG1().newRandomElement();
            t[i] = pairing.getG1().newRandomElement();
            d[i] = pairing.getZr().newRandomElement();
        }

        Element[] challengeSet = launchChallenge(m, replicaIDs, CSPs, r, w, t, d);

        for (Element c : challengeSet) {
            System.out.println(c);

        }
        long endTime = System.currentTimeMillis();

        long executionTime = endTime - startTime;
        System.out.println("发送挑战耗时: " + executionTime + "ms");
    }

}
