/**
 * Created by 杨 on 2023/3/18.
 */
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import java.util.Base64;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;

public class TagsSetGeneration {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        long startTime = System.currentTimeMillis();

        Pairing pairing = PairingFactory.getPairing("a.properties");

        // 加载云副本
        String[] cloudDuplicateStrings = {"XHiT1G5wvylDmDa=",
                "QzqW9MLuPvX26MK=",
                "DctF6H05vtbDhln=",
                "6vkc4t5gVNtQ34S=",
                "DMtBixpKXTnhM0T=",
                "cOMNNLrbXhGmslT=",
                "DgWjg0DbTOiDeQt=",
                "wjE4Bczdpf5KZcP=",
                "9n1Yxt7OouCiurF=",
                "qRd7ugfoSkiRIsW=",
                "alDNWstpjddjkaR=",
                "FV5o8Y4wROucDIl=",
                "04ScwVUOBCRicxR=",
                "vtkVWz6dEfsVBWG=",
                "GFFSD7sEAS0k6RW=",
                "SKyNPBvPjhwkiyL=",
                "3hiB0pNbW19jzKC=",
                "FGzIY8619nkZVgR=",
                "ebL7QpIPYHVeXlr=",
                "BwLZfrfCmGRAfot=",
                "lzZ5V3LqFm8eWgR=",
                "gTFjaEdywuxgyfK=",
                "0d5LBUJFa9mtL8M=",
                "SDx5xVBhriFfj43=",
                "LCIW50FjxltaJuP=",
                "sU5R1FK5xu1EqzV=",
                "MNKE1u28R9wPyLW=",
                "h9Qt3ujWxicFJR4=",
                "nhtv4tMDmLFxCXg=",
                "JIjKYEijTiqyeVh=",
                "tvSjPeduDMh8D1r=",
                "JUSfpSNTdpT4O6S=",
                "0amNzqGcpQbHUgk=",
                "71Q9ws2hy9LD9xg=",
                "aMghRfQGmXb6gUg=",
                "9AQD6HdKBkkKavG=",
                "a88LYcxnjCl8xUl=",
                "Du8Snp6c1QtoBNz=",
                "cOJFY4ah7xNbuyx=",
                "a2Dyu1SSHk5Gg0m=",
                "Ja9ipYDXcFUPEqc=",
                "iII7w7yfGU44CzW=",
                "JC1R1dLE3zzlQ1u=",
                "XQa10Rl0rE0uqUK=",
                "XgjQb3aovgq42bv=",
                "BQJWETlB6GRLAz0=",
                "mfJliPvm7GR73Hs=",
                "4tIKm9iHrU6p3jN=",
                "ZswBaNUSwRO13y0=",
                "OrfuSLPoRCga8H0=",
                "fvd0m7OgTxZ75TH=",
                "GppBd7UqMHyMKei=",
                "MFdJvRyBJKPpXJG=",
                "iDobk7Vhdf6nqnO=",
                "oyFdfNWMrkj5LVy=",
                "q45OsLDc8GZdX2r=",
                "6GQzgB5iGdjffTL=",
                "d5aAna26nt9Y9Ry=",
                "wwyqfJ0SryB2xyo=",
                "1fJ3SxmOLIhbmrY=",
                "6Ni2zM0d58hDUgN=",
                "Iw1mrAKSNfItAjf=",
                "Tlg9w72Yk1hRchP=",
                "PaQ8006PGHwiLEL=",
                "NFyVTAm64eBpZ6d=",
                "e1KZpZTBIREhGbD=",
                "7slcJBr6wKLRDMa=",
                "Z5nPuqry3w4LzJU=",
                "x8Kxqbz5BjAoTH7=",
                "SymDzqMy3XDNLUI=",
                "UorwjuqgdZGywAy=",
                "WlEfNH1VCz3a7sO=",
                "zcKWK3n7Apwjwo1=",
                "CSBJrHQ5oVVyOe6=",
                "KjIkPShO5qGUud4=",
                "a61BroQZ7q6v0Di=",
                "pqmgSi1qKQ6s4Ny=",
                "lWDVDtHdirywK40=",
                "H2DTBIUUKFcwHQt=",
                "MaRCebQzG3I9uiN=",
                "AkzIsz0tuSmXOiQ=",
                "9A9Ia1MmUZ630u3=",
                "OugorNMGxOuDoWs=",
                "MNex1qSAVgXDEQg=",
                "HiM66jmgLbiYKqR=",
                "Ko81U3raZ1cD37B=",
                "RZvyseEqIsJFO2r=",
                "t1zw9uWJXcPqvnA=",
                "MCmGlUzyWNczZ2h=",
                "TQr2akkX6bl5CAN=",
                "dEvnL92OZdCxRlt=",
                "cteHiDyezEWoXvt=",
                "aRXGa6C89suXb2u=",
                "jZMCSvBD9OQFfrg=",
                "1Lzew2NS3OLZLnz=",
                "o4HrRupzKZSrmHU=",
                "xKj0AVg8LMTt3hP=",
                "hKHe2Bevu9vvkbV=",
                "DCn5IKGTAWlvtYD=",


        };

        // 加载患者私钥
        Element skID = loadPrivateKey();

        // 为每个云副本生成标签

        String[] tagSetStrings = new String[50];
        for (int i = 0; i < 50; i++) {
            Element ci = pairing.getG1().newElementFromBytes(Base64.getMimeDecoder().decode(cloudDuplicateStrings[i]));
            Element ti = ci.powZn(skID).getImmutable();
            // 将标签集编码为base64字符串
            tagSetStrings[i] = Base64.getEncoder().encodeToString(ti.toBytes());

        }

        // 将云副本和标签集发送到CSM
        sendToCSM(cloudDuplicateStrings, tagSetStrings);

        System.out.println("标签集生成完成:");

        long endTime = System.currentTimeMillis();

        long executionTime = endTime - startTime;
        System.out.println("标签生成耗时: " + executionTime + "ms");
    }

    private static String[] loadCloudDuplicates() throws IOException {
        String[] cloudDuplicateStrings = new String[50];
        for (int i = 0; i < 50; i++) {
            cloudDuplicateStrings[i] = String.valueOf(Files.readAllBytes(new File("cloud_duplicate_" + (i+1) + ".txt").toPath()));

        }
        return cloudDuplicateStrings;
    }


    private static Element loadPrivateKey() throws IOException {
        byte[] privateKeyBytes = Files.readAllBytes(new File("private_key.txt").toPath());
        return PairingFactory.getPairing("a.properties").getZr().newElementFromBytes(privateKeyBytes);
    }

    private static void sendToCSM(String[] cloudDuplicateStrings, String[] tagSetStrings) {
        // 把云副本和标签发送到CSM的方法
        // 我们只将values.tes和标记集打印到CSM。
        System.out.println("发送云副本和标签集到CSM:");
        for (int i = 0; i < 50; i++) {
            System.out.println("云副本" + (i+1) + ": " + cloudDuplicateStrings[i]);
            System.out.println("云副本的标签 " + (i+1) + ": " + tagSetStrings[i]);
        }

    }

}
