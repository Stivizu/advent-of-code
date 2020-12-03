package com.stevecorp.codingcontest.adventofcode._003;

import java.util.List;

import static com.stevecorp.codingcontest.adventofcode.common.InputReader.parseFile;

public class Exercise2 {

    public static void main(final String... args) {
        final List<String> in = parseFile("challenges/003/input.txt", String::valueOf);
        final int is = in.get(0).length();
        final int[][] st =  new int[][] {{1, 1}, {3, 1}, {5, 1}, {7, 1}, {1, 2}};
        final int[] sot = new int[st.length];
        for (int i = 0; i < st.length; i++) {
            final int sty = st[i][0];
            final int stx = st[i][1];
            int cy = 0;
            int cx = 0;
            int so = 0;
            do {
                so += in.get(cx).charAt(cy) == '#' ? 1 : 0;
                cy += sty;
                cx += stx;
                cy = cy >= is ? cy - is : cy;
            } while (cx < in.size());
            sot[i] = so;
        }
        long mu = 1;
        for (int j : sot) {
            System.out.println(j);
            mu *= j;
        }
        System.out.println(mu);
    }
}
