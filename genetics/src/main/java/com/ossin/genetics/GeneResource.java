package com.ossin.genetics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeneResource {
    private final DnaDataService dnaService;
    private final String GENE_PREFIX;

    @Autowired
    public GeneResource(DnaDataService dnaService, @Value("${gene-prefix}") String genePrefix) {
        this.dnaService = dnaService;
        GENE_PREFIX = genePrefix;
    }

    @RequestMapping("/genes/find/{gene}")
    public ResponseEntity<String> index(@PathVariable("gene") String gene) {
        if (gene == null || !gene.startsWith(GENE_PREFIX)) {
            return new ResponseEntity("BAD FORMAT", HttpStatus.BAD_REQUEST);
        }
        if (dnaService.exist(gene.toCharArray())) {
            return new ResponseEntity("TRUE", HttpStatus.OK);
        } else {
            return new ResponseEntity("FALSE", HttpStatus.NOT_FOUND);
        }
    }
}
