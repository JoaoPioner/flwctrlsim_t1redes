package com.t1redes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SR {
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
    private EventType currentEventType = EventType.FRAME;

    public SR(String algo, String seqbits, String num_frames, String lost_pkts) {
        this.windowSize = (int) Math.pow(2, Double.parseDouble(seqbits)) - 1;
        this.lostPkgs = Arrays.stream(lost_pkts.split(",")).map(Integer::valueOf).collect(Collectors.toList());
        this.numFrames = Integer.parseInt(num_frames);
        for (int i = 0; i < numFrames; i++) {
            for (int j = 0; j < windowSize + 1; j++) {
                this.numSequence.add(j);
            }
        }
    }

    public List<String> sr() {
        var currentWindowElement = 0;
        while (currentFrame < numFrames) {
            while (currentWindowElement < windowSize && currentFrame < numFrames) {
                if (lostPkgs.contains(currentPkg)) {
                    failedElements.add(sequence);
                    result.add("A -x B : (" + (sequence + 1) + ") Frame " + numSequence.get(currentFrame));
                } else if (elementsToAck.contains(sequence) || failedElements.contains(sequence)) {
                    elementsToAck.add(sequence);
                    result.add("A ->> B : (" + (sequence + 1) + ") Frame " + numSequence.get(currentFrame) + " (RET)");
                } else {
                    elementsToAck.add(sequence);
                    result.add("A ->> B : (" + (sequence + 1) + ") Frame " + numSequence.get(currentFrame));
                }
                sequence++;
                currentWindowElement++;
                currentFrame++;
                currentPkg++;
            }
            for (int i = 0; i < windowSize && !elementsToAck.isEmpty(); i++) {
                Integer sentFrame = elementsToAck.get(0);
                if (lostPkgs.contains(currentPkg)) {
                    currentEventType = EventType.NAK;
                    result.add("B --x A : Nak " + numSequence.get(sentFrame + 1));
                    currentWindowElement--;
                    elementsToAck.remove(0);
                    ackWaitingFrame++;
                    currentPkg++;
                } else if (numSequence.get(ackWaitingFrame).equals(numSequence.get(sentFrame))) {
                    result.add("B -->> A : Ack " + numSequence.get(sentFrame + 1));
                    currentWindowElement--;
                    elementsToAck.remove(0);
                    ackWaitingFrame++;
                    currentPkg++;
                }
            }
            if (currentWindowElement == windowSize && !elementsToAck.isEmpty()) {
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
