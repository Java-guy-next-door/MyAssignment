package com.ossin.genetics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Set;

@Service
public class DnaParsingService {
    private final DnaDataService dataService;

    @Autowired
    public DnaParsingService(DnaDataService dataService, @Value("${dna-file}") String dnaFileName)
            throws IOException {
        this.dataService = dataService;
        parseFile(dnaFileName);
    }

    private void parseFile(String dnaFileName) throws IOException {
        FileWriter writer = new FileWriter("/tmp/dna-server.log");
        FileReader reader = new FileReader(dnaFileName);
//        char[] buffer = new char[31];
//        int actuallyRead = reader.read(buffer, 0, 31);
        int intRead = reader.read();
        char characterRead;
//        boolean readingPrefix = true;

        int itr = 1;
//        while (actuallyRead > 0) {
        while (intRead > 0) {
            characterRead = (char) intRead;
            this.dataService.addCharacter(characterRead);
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

            if (itr % 5000 == 0) {
                writer.write(itr + "\n");
                writer.flush();
            }
            itr++;
            intRead = reader.read();
        }
        reader.close();
        writer.close();
    }
}
