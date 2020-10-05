package com.ossin.genetics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class DnaParsingService {
    private final DnaDataService dataService;
    private final ConcurrentLinkedQueue<Character> queue = new ConcurrentLinkedQueue<>();
    private final FileWriter writer;

    @Autowired
    public DnaParsingService(DnaDataService dataService, @Value("${dna-file}") String dnaFileName,
                             @Value("${log-file}") String logFileName)
            throws IOException {
        this.dataService = dataService;
        writer = new FileWriter(logFileName);
        parseFile(dnaFileName);
        writer.close();
    }

    private void parseFile(String dnaFileName) throws IOException {
        DnaFileReader reader = new DnaFileReader(dnaFileName);
        reader.start();
        addCharactersToDataService(reader);
        writer.write("# Nodes: " + dataService.getNodes());
    }
    private void addCharactersToDataService(DnaFileReader reader) {
        while (reader.isAlive() || !queue.isEmpty()) {
            while (reader.isAlive() && queue.isEmpty()) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            while (!queue.isEmpty()) {
                dataService.addCharacter(queue.poll());
            }
        }
    }

    private class DnaFileReader extends Thread {
        private final String fileName;

        DnaFileReader(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public void run() {
            try {
                FileReader reader = new FileReader(fileName);
                int intRead = reader.read();
                char characterRead;

                int itr = 1;
                while (intRead > 0) {
                    characterRead = (char) intRead;
                    queue.add(characterRead);

                    if (itr % 1500000 == 0) {
                        writer.write("Characters read: " + itr + "\n");
                        writer.flush();
                    }
                    itr++;
                    intRead = reader.read();
                }
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
