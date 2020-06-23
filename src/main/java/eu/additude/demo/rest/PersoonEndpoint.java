package eu.additude.demo.rest;

import eu.additude.demo.controller.PersoonService;
import eu.additude.demo.dto.PersoonDTO;
import eu.additude.demo.model.Persoon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/personen")
//@Slf4j // Via Lombok kun je ook op deze manier een logger krijgen, zonder de instantie
@CrossOrigin
public class PersoonEndpoint {
    private Logger log = LoggerFactory.getLogger(PersoonEndpoint.class);
    @Autowired
    PersoonService service;

    @GetMapping("/{id}")
    public Persoon getPersoonById(@PathVariable Long id) {
        System.out.println("LOG- GET: personen/" + id + " - Aanroep van onze restserivce voor het opvragen van één persoon.");
        log.info("LOG- GET: personen/{} - Aanroep van onze restserivce voor het opvragen van één persoon.", id);
        return service.findPersoonById(id);
    }

    @GetMapping("/DTO/{id}")
    public PersoonDTO getPersoonDTOById(@PathVariable Long id) {
        System.out.println("LOG- GET: personen/DTO/" + id + " - Aanroep van onze restserivce voor het opvragen van één persoon.");
        log.info("LOG- GET: personen/DTO/{} - Aanroep van onze restserivce voor het opvragen van één persoon.", id);
        return service.findPersoonDTOById(id);
    }

    @GetMapping()
    public List<PersoonDTO> getAllePersonen() {
        System.out.println("LOG- GET: personen - Aanroep van onze restserivce voor het opvragen van alle personen.");

//        return service.getAllePersonen()
//                .stream()
//                .map(PersoonDTO::new)                     // zet een persoon om in een PersoonDTO
//                .collect(Collectors.toList());            // alles op de band weer verzamelen in een List.

        List<Persoon> personen = service.getAllePersonen();
        List<PersoonDTO> dtoPersonen = personen
                .stream()                                   // maak er een lopende band van
                .map(persoon -> new PersoonDTO(persoon))    // zet een persoon om in een PersoonDTO  // PersoonDTO::new // Korter en mooier
                .collect(Collectors.toList());              // alles op de band weer verzamelen in een List.
        return dtoPersonen;                                 // Manier 2 sturen we nu terug. bij manier 1 krijgen we door a & b nu natuurlijk alle personen dubbel
    }

    @GetMapping("/afdeling/{id}")
    public List<PersoonDTO> getAllePersonenVanAfdeling(@PathVariable Long id) {
        log.info("LOG- GET: personen/afdeling/{} - Aanroep van onze restserivce voor het opvragen van alle personen van een specifieke afdeling.", id);

        return service.getAllePersonenDTOVanAfdeling(id);
        // of
//        return service.getAllePersonen()
//                .stream()
//                .filter(persoon -> persoon.getAfdeling() != null)
//                .filter(persoon -> persoon.getAfdeling().getId().equals(id))
//                .map(PersoonDTO::new)
//                .collect(Collectors.toList());
    }

    @PostMapping()
    // Post zorgt ervoor dat de mapping gelijk kan blijven, geen conflict met de GET voor alle personen. COOL.
    @ResponseStatus(HttpStatus.CREATED)
    public Persoon postPersoon(@RequestBody @Valid Persoon persoon) {      // Dit is de persoon die we in het bericht binnenkrijgen
        System.out.println("LOG- POST: personen - Aanroep van onze restserivce voor het toevoegen van één persoon.");
        // Eigenlijk moeten we de persoon die binnenkomt, qua gegevens overzetten in een nieuw persoon
        // Vertrouw nooit de info die je vanuit de client kant binnenkrijgt!!

//        if (persoon.getAchternaam() == null || persoon.getAchternaam().isEmpty()) {
//            throw new BadRequestException("De achternaam moet ingevuld zijn.");
//        }

        //Persoon nieuwPersoon = new Persoon(persoon);
        return service.postPersoon(persoon);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Persoon changePersoon(@PathVariable Long id, @RequestBody Persoon persoon) {      // Dit is de persoon die we in het bericht binnenkrijgen
        System.out.println("LOG- PUT: personen - Aanroep van onze restserivce voor het wijzigen van één persoon.");
        return service.putPersoon(id, persoon);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deletePersoon(@PathVariable Long id) {
        System.out.println("LOG- DELETE/" + id + ": personen - Aanroep van onze restserivce voor het verwijderen van één persoon.");
        log.info("LOG- DELETE/{}: personen - Aanroep van onze restserivce voor het verwijderen van één persoon.", id);
        service.deletePersoon(id);
    }
}
