package com.t1redes;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        String algo = args[0];
        String seqbits = args[1];
        String num_frames = args[2];
        String lost_pkts = args[3];

        switch (algo) {
            case "saw": {
                System.out.println("executando SAW");
                SAW saw = new SAW();
                saw.saw(seqbits,num_frames,lost_pkts).forEach(System.out::println);
            }break;
            case "gbn": {
                System.out.println("executando GBN");
                GBN gbn = new GBN(algo, seqbits, num_frames, lost_pkts);
                gbn.gbn().forEach(System.out::println);
            }break;
            case "sr": {

            }break;
        }



    }
}
