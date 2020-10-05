package com.ossin.genetics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
        int intRead = reader.read();
        char characterRead;

        int itr = 1;
        while (intRead > 0) {
            characterRead = (char) intRead;
            this.dataService.addCharacter(characterRead);

            if (itr % 10000 == 0) {
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
