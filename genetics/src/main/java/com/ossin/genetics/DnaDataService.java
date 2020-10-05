package com.ossin.genetics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Set;

@Service
public class DnaDataService {
//    private final Set<BigInteger> genePool;
    private final Trie genePool;
//    private final DnaCharacter[] PREFIX;
    private final char[] PREFIX;
    private int prefixIndex;

    @Autowired
//    public DnaParsingService(Trie trie, @Value("${dna-file}") String dnaFileName)
//            throws IOException {
//        genePool = new HashSet<>();
    public DnaDataService(@Value("${gene-prefix}") String genePrefix) {
        this.genePool = new Trie();
//        this.PREFIX = new DnaCharacter[genePrefix.length()];
        this.PREFIX = new char[genePrefix.length()];
        for (int i = 0; i < genePrefix.length(); i++) {
//            PREFIX[i] = DnaCharacter.fromCharacterValue(genePrefix.charAt(i));
            PREFIX[i] = genePrefix.charAt(i);
        }
        prefixIndex = 0;

//        FileWriter writer = new FileWriter("/tmp/dna-server.log");
//        FileReader reader = new FileReader(dnaFileName);
//        char[] buffer = new char[31];
//        int actuallyRead = reader.read(buffer, 0, 31);
//        int characterRead = reader.read();
//        boolean readingPrefix = true;

//        int itr = 1;
//        while (actuallyRead > 0) {
//        while (characterRead > 0) {
//            characterRead = reader.read();
//            genePool.add(characterRead);
//        }
//            actuallyRead = reader.read(buffer, 0, 31);
//            String value = new String(buffer, 11, 20);
//            genePool.add(value);
            //genePool.addAll(convertDna(buffer));
//            List<BigInteger> numbers = convertDna(buffer);
//            convertDna(buffer, genePool);
//            for (int i = numbers.size()-1; i >= 0; i--) {
//                if (!genePool.add(numbers.get(i))) {
//                    break;
//                }
//            }

//            if (itr % 10000 == 0) {
//                writer.write(value + " " + genePool.getNodes() + "\n");
//                writer.flush();
//            }
//            itr++;
//            characterRead = reader.read();
//        }
//        reader.close();
//        writer.close();
    }

    public void startDnaSequence() {
        prefixIndex = 0;
    }

    public void addCharacter(char dnaCharacter) {
//        addCharacter(DnaCharacter.fromCharacterValue(character));
//    }
//    public void addCharacter(DnaCharacter dnaCharacter) {
        if (PREFIX[prefixIndex] == dnaCharacter) {
            prefixIndex++;
            if (prefixIndex == PREFIX.length) {
                genePool.startGeneSquence();
                for (int i = 0; i < prefixIndex; i++) {
                    genePool.add(PREFIX[i]);
                }
                prefixIndex = 0;
            }
        } else {
            for (int i = 0; i < prefixIndex; i++) {
                genePool.add(PREFIX[i]);
            }
            prefixIndex = 0;
            genePool.add(dnaCharacter);
        }
    }

    public static void convertDna(char[] dna, Set<BigInteger> pool) {
//        List<BigInteger> result = new ArrayList<>();
        BigInteger currentValue = BigInteger.ZERO;
        BigInteger two = BigInteger.valueOf(2);
        BigInteger three = BigInteger.valueOf(3);
        BigInteger four = BigInteger.valueOf(4);
        for (char c : dna) {
//            result.add(currentValue);
            pool.add(currentValue);
            currentValue = currentValue.multiply(four);
            switch(c) {
                case 'A':
                    currentValue = currentValue.add(BigInteger.ONE);
                    break;
                case 'C':
                    currentValue = currentValue.add(two);
                    break;
                case 'G':
                    currentValue = currentValue.add(three);
                    break;
                case 'T':
                    currentValue = currentValue.add(four);
                    break;
            }
        }
//        result.add(currentValue);
//        return result;
    }

    public boolean exist(char[] gene) {
//        DnaDataService.DnaCharacter[] characters = new DnaDataService.DnaCharacter[gene.length()];
//        for (int i = 0 ; i < gene.length; i++) {
//            characters[i] = DnaDataService.DnaCharacter.fromCharacterValue(gene.charAt(i));
//        }
        return genePool.exists(gene);
    }

//    public boolean exist(DnaDataService.DnaCharacter[] gene) {
//        return genePool.exists(gene);
//    }

    public enum DnaCharacter {
        A('A'), C('C'), G('G'), T('T');

        private final char charValue;
        DnaCharacter(char charValue) {
            this.charValue = charValue;
        }

        public static DnaCharacter fromCharacterValue(char charAt) {
            DnaCharacter result = null;
            switch (charAt) {
                case 'A':
                    result = DnaCharacter.A;
                    break;
                case 'C':
                    result = DnaCharacter.C;
                    break;
                case 'G':
                    result = DnaCharacter.G;
                    break;
                case 'T':
                    result = DnaCharacter.T;
                    break;
            }
            return result;
        }

        public char charValue() {
            return charValue;
        }
    }
}
