package eu.additude.demo.rest;

import eu.additude.demo.controller.AfdelingService;
import eu.additude.demo.dto.AfdelingDTO;
import eu.additude.demo.model.Afdeling;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/afdelingen")
@Slf4j
@CrossOrigin
public class AfdelingEndpoint {
    @Autowired
    AfdelingService service;

    @GetMapping("/{id}")
    public AfdelingDTO getPersoonById(@PathVariable Long id) {
        System.out.println("LOG- GET: afdelingen/" + id + " - Aanroep van onze restserivce voor het opvragen van één afdeling.");
        log.info("LOG- GET: afdelingen/{} - Aanroep van onze restserivce voor het opvragen van één afdeling.", id);
        return service.findAfdelingDTOById(id);
    }

    @GetMapping()
    public List<AfdelingDTO> getAlleAfdelingen() {
        System.out.println("LOG- GET: afdelingen - Aanroep van onze restserivce voor het opvragen van alle afdelingen.");
        return service.getAlleAfdelingen();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Afdeling postAfdeling(@RequestBody @Valid Afdeling afdeling) {
        System.out.println("LOG- POST: afdelingen - Aanroep van onze restserivce voor het toevoegen van één afdeling.");
        // Vertrouw nooit de info die je vanuit de client kant binnenkrijgt!!

        return service.postAfdeling(afdeling);
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Afdeling changeAfdeling(@PathVariable Long id, @RequestBody Afdeling afdeling) {
        System.out.println("LOG- PUT: afdelingen - Aanroep van onze restserivce voor het wijzigen van één afdeling.");
        return service.putAfdeling(id, afdeling);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteAfdeling(@PathVariable Long id) {
        System.out.println("LOG- DELETE/" + id + ": afdelingen - Aanroep van onze restserivce voor het verwijderen van één afdelinge.");
        log.info("LOG- DELETE/{}: afdelingen - Aanroep van onze restserivce voor het verwijderen van één afdeling.", id);
        service.deleteAfdeling(id);
    }
}
