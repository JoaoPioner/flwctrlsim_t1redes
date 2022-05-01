package com.t1redes;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GBNTest {

    private GBN gbn;

    @Test
    void gbn1() {
        String expected = "A ->> B : (1) Frame 0\n" +
                "A ->> B : (2) Frame 1\n" +
                "A -x B : (3) Frame 2\n" +
                "B -->> A : Ack 1\n" +
                "B -->> A : Ack 2\n" +
                "A ->> B : (4) Frame 3\n" +
                "A ->> B : (5) Frame 0\n" +
                "Note over A : TIMEOUT (3)\n" +
                "A ->> B : (3) Frame 2 (RET)\n" +
                "A ->> B : (4) Frame 3 (RET)\n" +
                "A ->> B : (5) Frame 0 (RET)\n" +
                "B -->> A : Ack 3\n" +
                "B -->> A : Ack 0\n" +
                "B -->> A : Ack 1\n" +
                "A ->> B : (6) Frame 1\n" +
                "B -->> A : Ack 2";
        String algo = "gbn";
        String seqbits = "2";
        String num_frames = "6";
        String lost_pkts = "3";
        gbn = new GBN(algo, seqbits, num_frames, lost_pkts);
        List<String> result = gbn.gbn();
        List<String> expectedList = Arrays.asList(expected.split("\n"));
        assertEquals(expectedList, result);
    }

    @Test
    void gbn2() {
        String expected = "A ->> B : (1) Frame 0\n" +
                "A ->> B : (2) Frame 1\n" +
                "A ->> B : (3) Frame 2\n" +
                "B -->> A : Ack 1\n" +
                "B -->> A : Ack 2\n" +
                "B -->> A : Ack 3\n" +
                "A ->> B : (4) Frame 3\n" +
                "A ->> B : (5) Frame 0\n" +
                "A ->> B : (6) Frame 1\n" +
                "B -->> A : Ack 0\n" +
                "B -->> A : Ack 1\n" +
                "B -->> A : Ack 2";
        String algo = "gbn";
        String seqbits = "2";
        String num_frames = "6";
        String lost_pkts = "0";
        gbn = new GBN(algo, seqbits, num_frames, lost_pkts);
        List<String> result = gbn.gbn();
        List<String> expectedList = Arrays.asList(expected.split("\n"));
        assertEquals(expectedList, result);
    }

    @Test
    void gbn3() {
        String expected = "A -x B : (1) Frame 0\n" +
                "A ->> B : (2) Frame 1\n" +
                "A ->> B : (3) Frame 2\n" +
                "Note over A : TIMEOUT (1)\n" +
                "A ->> B : (1) Frame 0 (RET)\n" +
                "A ->> B : (2) Frame 1 (RET)\n" +
                "A ->> B : (3) Frame 2 (RET)\n" +
                "B -->> A : Ack 1\n" +
                "B -->> A : Ack 2\n" +
                "B -->> A : Ack 3\n" +
                "A ->> B : (4) Frame 3\n" +
                "A ->> B : (5) Frame 0\n" +
                "A ->> B : (6) Frame 1\n" +
                "B -->> A : Ack 0\n" +
                "B -->> A : Ack 1\n" +
                "B -->> A : Ack 2";
        String algo = "gbn";
        String seqbits = "2";
        String num_frames = "6";
        String lost_pkts = "1";
        gbn = new GBN(algo, seqbits, num_frames, lost_pkts);
        List<String> result = gbn.gbn();
        List<String> expectedList = Arrays.asList(expected.split("\n"));
        assertEquals(expectedList, result);
    }

    @Test
    void gbn4() {
        String expected = "A -x B : (1) Frame 0\n" +
                "A ->> B : (2) Frame 1\n" +
                "A ->> B : (3) Frame 2\n" +
                "Note over A : TIMEOUT (1)\n" +
                "A ->> B : (1) Frame 0 (RET)\n" +
                "A ->> B : (2) Frame 1 (RET)\n" +
                "A ->> B : (3) Frame 2 (RET)\n" +
                "B --x A : Ack 1\n" +
                "B -->> A : Ack 2\n" +
                "B -->> A : Ack 3\n" +
                "A ->> B : (4) Frame 3\n" +
                "A ->> B : (5) Frame 0\n" +
                "A ->> B : (6) Frame 1\n" +
                "B -->> A : Ack 0\n" +
                "B -->> A : Ack 1\n" +
                "B -->> A : Ack 2";
        String algo = "gbn";
        String seqbits = "2";
        String num_frames = "6";
        String lost_pkts = "1,7";
        gbn = new GBN(algo, seqbits, num_frames, lost_pkts);
        List<String> result = gbn.gbn();
        List<String> expectedList = Arrays.asList(expected.split("\n"));
        assertEquals(expectedList, result);
    }

    @Test
    void gbn5() {
        String expected = "A ->> B : (1) Frame 0\n" +
                "A -x B : (2) Frame 1\n" +
                "A -x B : (3) Frame 2\n" +
                "A ->> B : (4) Frame 3\n" +
                "A ->> B : (5) Frame 4\n" +
                "A ->> B : (6) Frame 5\n" +
                "A ->> B : (7) Frame 6\n" +
                "B --x A : Ack 1\n" +
                "Note over A : TIMEOUT (1)\n" +
                "A ->> B : (1) Frame 0 (RET)\n" +
                "A -x B : (2) Frame 1 (RET)\n" +
                "A ->> B : (3) Frame 2 (RET)\n" +
                "A ->> B : (4) Frame 3 (RET)\n" +
                "A ->> B : (5) Frame 4 (RET)\n" +
                "A ->> B : (6) Frame 5 (RET)\n" +
                "A ->> B : (7) Frame 6 (RET)\n" +
                "B -->> A : Ack 1\n" +
                "A ->> B : (8) Frame 7\n" +
                "Note over A : TIMEOUT (2)\n" +
                "A ->> B : (2) Frame 1 (RET)\n" +
                "A ->> B : (3) Frame 2 (RET)\n" +
                "A ->> B : (4) Frame 3 (RET)\n" +
                "A ->> B : (5) Frame 4 (RET)\n" +
                "A ->> B : (6) Frame 5 (RET)\n" +
                "A ->> B : (7) Frame 6 (RET)\n" +
                "A ->> B : (8) Frame 7 (RET)\n" +
                "B -->> A : Ack 2\n" +
                "B -->> A : Ack 3\n" +
                "B -->> A : Ack 4\n" +
                "B -->> A : Ack 5\n" +
                "B -->> A : Ack 6\n" +
                "B -->> A : Ack 7\n" +
                "B -->> A : Ack 0\n" +
                "A ->> B : (9) Frame 0\n" +
                "A ->> B : (10) Frame 1\n" +
                "B -->> A : Ack 1\n" +
                "B -->> A : Ack 2";
        String algo = "gbn";
        String seqbits = "3";
        String num_frames = "10";
        String lost_pkts = "2,3,8,10";
        gbn = new GBN(algo, seqbits, num_frames, lost_pkts);
        List<String> result = gbn.gbn();
        result.forEach(System.out::println);
        System.out.println();
        List<String> expectedList = Arrays.asList(expected.split("\n"));
        expectedList.forEach(System.out::println);
        assertEquals(expectedList, result);
    }
}