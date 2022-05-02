package com.t1redes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SAWTest {

    private SAW saw;

    @BeforeEach
    public void setup() {
        saw = new SAW();
    }

    @Test
    void saw1() {
        String expected = "A ->> B : (1) Frame 0\n" +
                "B -->> A : Ack 1\n" +
                "A -x B : (2) Frame 1\n" +
                "Note over A : TIMEOUT (2)\n" +
                "A ->> B : (2) Frame 1 (RET)\n" +
                "B -->> A : Ack 0\n" +
                "A ->> B : (3) Frame 0\n" +
                "B -->> A : Ack 1\n" +
                "A ->> B : (4) Frame 1\n" +
                "B -->> A : Ack 0\n" +
                "A -x B : (5) Frame 0\n" +
                "Note over A : TIMEOUT (5)\n" +
                "A ->> B : (5) Frame 0 (RET)\n" +
                "B -->> A : Ack 1\n" +
                "A ->> B : (6) Frame 1\n" +
                "B -->> A : Ack 0\n" +
                "A -x B : (7) Frame 0\n" +
                "Note over A : TIMEOUT (7)\n" +
                "A ->> B : (7) Frame 0 (RET)\n" +
                "B -->> A : Ack 1\n" +
                "A ->> B : (8) Frame 1\n" +
                "B -->> A : Ack 0\n" +
                "A ->> B : (9) Frame 0\n" +
                "B -->> A : Ack 1\n" +
                "A ->> B : (10) Frame 1\n" +
                "B -->> A : Ack 0";
        String algo = "saw";
        String seqbits = "1";
        String num_frames = "10";
        String lost_pkts = "3,10,15";
        List<String> result = saw.saw(seqbits, num_frames, lost_pkts);
        List<String> expectedList = Arrays.asList(expected.split("\n"));
        assertEquals(expectedList, result);
    }

    @Test
    void saw2() {
        String expected = "A ->> B : (1) Frame 0\n" +
                "B -->> A : Ack 1\n" +
                "A ->> B : (2) Frame 1\n" +
                "B -->> A : Ack 0\n" +
                "A ->> B : (3) Frame 0\n" +
                "B -->> A : Ack 1\n" +
                "A ->> B : (4) Frame 1\n" +
                "B -->> A : Ack 0";
        String algo = "saw";
        String seqbits = "1";
        String num_frames = "4";
        String lost_pkts = "0";
        List<String> result = saw.saw(seqbits, num_frames, lost_pkts);
        List<String> expectedList = Arrays.asList(expected.split("\n"));
        assertEquals(expectedList, result);
    }

    @Test
    void saw3() {
        String expected = "A -x B : (1) Frame 0\n" +
                "Note over A : TIMEOUT (1)\n" +
                "A ->> B : (1) Frame 0 (RET)\n" +
                "B -->> A : Ack 1\n" +
                "A ->> B : (2) Frame 1\n" +
                "B -->> A : Ack 0\n" +
                "A ->> B : (3) Frame 0\n" +
                "B -->> A : Ack 1\n" +
                "A ->> B : (4) Frame 1\n" +
                "B -->> A : Ack 0";
        String algo = "saw";
        String seqbits = "1";
        String num_frames = "4";
        String lost_pkts = "1";
        List<String> result = saw.saw(seqbits, num_frames, lost_pkts);
        List<String> expectedList = Arrays.asList(expected.split("\n"));
        assertEquals(expectedList, result);
    }

    @Test
    void saw4() {
        String expected = "A -x B : (1) Frame 0\n" +
                "Note over A : TIMEOUT (1)\n" +
                "A ->> B : (1) Frame 0 (RET)\n" +
                "B --x A : Ack 1\n" +
                "Note over A : TIMEOUT (1)\n" +
                "A ->> B : (1) Frame 0 (RET)\n" +
                "B -->> A : Ack 1\n" +
                "A ->> B : (2) Frame 1\n" +
                "B -->> A : Ack 0\n" +
                "A ->> B : (3) Frame 0\n" +
                "B -->> A : Ack 1\n" +
                "A ->> B : (4) Frame 1\n" +
                "B -->> A : Ack 0";
        String algo = "saw";
        String seqbits = "1";
        String num_frames = "4";
        String lost_pkts = "1,3";
        List<String> result = saw.saw(seqbits, num_frames, lost_pkts);
        List<String> expectedList = Arrays.asList(expected.split("\n"));
        assertEquals(expectedList, result);
    }

    @Test
    void saw5() {
        String expected = "A ->> B : (1) Frame 0\n" +
                "B -->> A : Ack 1\n" +
                "A -x B : (2) Frame 1\n" +
                "Note over A : TIMEOUT (2)\n" +
                "A ->> B : (2) Frame 1 (RET)\n" +
                "B -->> A : Ack 0\n" +
                "A -x B : (3) Frame 0\n" +
                "Note over A : TIMEOUT (3)\n" +
                "A ->> B : (3) Frame 0 (RET)\n" +
                "B -->> A : Ack 1\n" +
                "A ->> B : (4) Frame 1\n" +
                "B -->> A : Ack 0";
        String algo = "saw";
        String seqbits = "1";
        String num_frames = "4";
        String lost_pkts = "3,6";
        List<String> result = saw.saw(seqbits, num_frames, lost_pkts);
        List<String> expectedList = Arrays.asList(expected.split("\n"));
        assertEquals(expectedList, result);
    }
}