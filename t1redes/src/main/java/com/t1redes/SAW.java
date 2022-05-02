package com.t1redes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SAW {

    private int currentLine = 1;
    private int currentFrame = 0;
    private int sequence = 0;

    private EventType currentEventType = EventType.FRAME;

    List<String> result = new ArrayList<>();

    public SAW() {

    }

    public List<String> saw(String seqbits, String num_frames, String lost_pkts) {
        var totalBits = (int) Math.pow(2, Double.parseDouble(seqbits)) - 1;// define a sequencia de bits
        var lostPkts = Arrays.stream(lost_pkts.split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());// converte a string dos lost_pkts e para uma lista de ints

        var numFrames = Integer.parseInt(num_frames);
        while (sequence < numFrames || currentEventType == EventType.ACK) { // enquanto o numero de frames for maior q a sequencia ou espera um evento ack
            runFrames(totalBits, lostPkts);// executa os frames
        }
        return result;
    }

    private void runFrames(int totalBits, List<Integer> lostPkts) {
        if (lostPkts.contains(currentLine)) {// se for uma falha
            if (currentEventType == EventType.FRAME) {// e se espera enviar um frame(emissor)
                result.add("A -x B : (" + ++sequence + ") Frame " + currentFrame);
                result.add("Note over A : TIMEOUT (" + sequence + ")");
            } else {// ou espera enviar um ack(receptor)
                result.add("B --x A : Ack " + getNextFrame(totalBits));
                result.add("Note over A : TIMEOUT (" + sequence + ")");
            }
            currentEventType = EventType.RET;
        } else {// mas se der certo
            if (currentEventType == EventType.FRAME) {// se ele espera enviar um frame(emissor)
                result.add("A ->> B : (" + ++sequence + ") Frame " + currentFrame);
            } else if (currentEventType == EventType.ACK) {// se ele espera enviar um ack(receptor)
                updateFrame(totalBits);
                result.add("B -->> A : Ack " + currentFrame);
            } else {
                result.add("A ->> B : (" + sequence + ") Frame " + currentFrame + " (RET)");
            }
            changeEventType();
        }
        currentLine++;// atualiza a sequencia
    }

    private void changeEventType() {
        if (currentEventType == EventType.FRAME || currentEventType == EventType.RET) {
            currentEventType = EventType.ACK;
        } else {
            currentEventType = EventType.FRAME;
        }
    }

    private void updateFrame(int totalBits) {
        currentFrame++;
        if (currentFrame > totalBits) {
            currentFrame = 0;
        }
    }

    private Integer getNextFrame(int totalBits) {
        int nextFrame = currentFrame + 1;
        if (nextFrame > totalBits) {
            return 0;
        }
        return nextFrame;
    }
}
