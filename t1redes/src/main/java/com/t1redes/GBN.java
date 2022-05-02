package com.t1redes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class GBN {

    private int currentPkg = 1;
    private int sequence = 0;

    List<String> result = new ArrayList<>();
    List<Integer> elementsToAck = new ArrayList<>();
    List<Integer> failedElements = new ArrayList<>();
    List<Integer> numSequence = new LinkedList<>();
    private Integer ackWaitingFrame = 0;
    private int currentFrame = 0;
    private final List<Integer> lostPkgs;
    private final Integer windowSize;
    private final Integer numFrames;

    public GBN(String seqbits, String num_frames, String lost_pkts) {
        this.windowSize = (int) Math.pow(2, Double.parseDouble(seqbits)) - 1; // define o tamanho da janela
        this.lostPkgs = Arrays.stream(lost_pkts.split(",")).map(Integer::valueOf).collect(Collectors.toList()); // transforam a string "1,2,3" em uma lista [1,2,3]
        this.numFrames = Integer.parseInt(num_frames);// converte para int
        for (int i = 0; i < numFrames; i++) {// carrega a janela
            for (int j = 0; j < windowSize + 1; j++) {
                this.numSequence.add(j);
            }
        }
    }

    public List<String> gbn() {
        var currentWindowElement = 0;
        while (currentFrame < numFrames) {// enquanto numero de frames for maior que o frame atual...
            //Emissor
            while (currentWindowElement < windowSize && currentFrame < numFrames) {// enquatno o tamanho da janela for maior que o elemento atual e numero de frames for maior que o frame atual...
                if (lostPkgs.contains(currentPkg)) {// se o pacote atual for um pacote perdido
                    failedElements.add(sequence);// guarda a sequencia dele a uma lista de falhas
                    result.add("A -x B : (" + (sequence + 1) + ") Frame " + numSequence.get(currentFrame));
                } else if (elementsToAck.contains(sequence) || failedElements.contains(sequence)) {// se ele precisa ser enviado ou teve um envio falho
                    elementsToAck.add(sequence);
                    result.add("A ->> B : (" + (sequence + 1) + ") Frame " + numSequence.get(currentFrame) + " (RET)");
                } else {
                    elementsToAck.add(sequence);
                    result.add("A ->> B : (" + (sequence + 1) + ") Frame " + numSequence.get(currentFrame));
                }
                //avança para o próximo
                sequence++;
                currentWindowElement++;
                currentFrame++;
                currentPkg++;
            }
            //Receptor
            for (int i = 0; i < windowSize && !elementsToAck.isEmpty(); i++) {
                Integer sentFrame = elementsToAck.get(0);
                if (lostPkgs.contains(currentPkg)) { // se for falhar
                    result.add("B --x A : Ack " + numSequence.get(sentFrame + 1));
                    //atualiza a janela
                    currentWindowElement--;
                    elementsToAck.remove(0);
                    ackWaitingFrame++;
                    currentPkg++;
                } else if (numSequence.get(ackWaitingFrame).equals(numSequence.get(sentFrame))) { // se der certo
                    result.add("B -->> A : Ack " + numSequence.get(sentFrame + 1));
                    //atualiza a janela
                    currentWindowElement--;
                    elementsToAck.remove(0);
                    ackWaitingFrame++;
                    currentPkg++;
                }
            }
            if (currentWindowElement == windowSize && !elementsToAck.isEmpty()) {// Time Out
                sequence = numSequence.get(ackWaitingFrame);
                result.add("Note over A : TIMEOUT (" + (sequence + 1) + ")");
                currentWindowElement = 0;
                failedElements.addAll(elementsToAck);
                elementsToAck.clear();
                currentFrame = ackWaitingFrame;
            }
        }
        return result;
    }
}
