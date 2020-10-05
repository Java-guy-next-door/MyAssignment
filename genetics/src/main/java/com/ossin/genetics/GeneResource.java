package com.ossin.genetics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeneResource {
    private final DnaParsingService dnaService;

    @Autowired
    public GeneResource(DnaParsingService dnaService) {
        this.dnaService = dnaService;
    }

    @RequestMapping("/genes/find/{gene}")
    public ResponseEntity<String> index(@PathVariable("gene") String gene) {
        if (dnaService.exist(gene)) {
            return new ResponseEntity("TRUE", HttpStatus.OK);
        } else {
            return new ResponseEntity("FALSE", HttpStatus.NOT_FOUND);
        }
    }
}
