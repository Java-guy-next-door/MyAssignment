package com.ossin.genetics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DnaParsingService {
//    private final Set<BigInteger> genePool;
    private final Trie genePool;

    @Autowired
    public DnaParsingService(Trie trie, @Value("${dna-file}") String dnaFileName)
            throws IOException {
//        genePool = new HashSet<>();
        this.genePool = trie;

        FileWriter writer = new FileWriter("/tmp/dna-server.log");
        FileReader reader = new FileReader(dnaFileName);
        char[] buffer = new char[31];
        int actuallyRead = reader.read(buffer, 0, 31);
//        int characterRead = reader.read();

        int itr = 1;
        while (actuallyRead > 0) {
//        while (characterRead > 0) {
//            characterRead = reader.read();
//            genePool.add(characterRead);
//        }
            actuallyRead = reader.read(buffer, 0, 31);
            String value = new String(buffer, 11, 20);
            genePool.add(value);
            //genePool.addAll(convertDna(buffer));
//            List<BigInteger> numbers = convertDna(buffer);
//            convertDna(buffer, genePool);
//            for (int i = numbers.size()-1; i >= 0; i--) {
//                if (!genePool.add(numbers.get(i))) {
//                    break;
//                }
//            }

            if (itr % 10000 == 0) {
                writer.write(value + " " + genePool.getNodes() + "\n");
                writer.flush();
            }
            itr++;
        }
        reader.close();
        writer.close();
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

//    public boolean exist(String value) {
//        return genePool.contains(value);
//    }

    public boolean exist(String value) {
        return genePool.exists(value);
    }
}
