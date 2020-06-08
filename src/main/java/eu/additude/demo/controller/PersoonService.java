package eu.additude.demo.controller;

import eu.additude.demo.dto.PersoonDTO;
import eu.additude.demo.exceptions.ConflictException;
import eu.additude.demo.exceptions.ResourceNotFoundException;
import eu.additude.demo.model.Afdeling;
import eu.additude.demo.model.Persoon;
import eu.additude.guides.gs_producing_web_service.PersoonSoap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersoonService {

    @Autowired
    private PersoonRepository repository;
    @Autowired
    private AfdelingService afdelingService;

    public Persoon findPersoonById(Long id) {
        Optional<Persoon> persoonOptional = repository.findById(id);
        if (!persoonOptional.isPresent()) {
            throw new ResourceNotFoundException("Persoon met id " + id + " niet gevonden");
        }
        Persoon persoon = persoonOptional.get();
        if (persoon.getAfdeling() != null) {
            persoon.setAfdelingId(persoon.getAfdeling().getId()); // aparte methode in Persoon
        }
        return persoon;
    }

    public PersoonDTO findPersoonDTOById(Long id) {
        Optional<Persoon> persoonOptional = repository.findById(id);
        if (!persoonOptional.isPresent()) {
            throw new ResourceNotFoundException("Persoon met id " + id + " niet gevonden");
        }
        return new PersoonDTO(persoonOptional.get());
    }

    // ToDo pas maken na het genereren van de classes door jaxb2-maven-plugin
    public PersoonSoap findPersoonSOAPById(Long id) {
        Optional<Persoon> persoonOptional = repository.findById(id);
        if (!persoonOptional.isPresent()) {
            throw new ResourceNotFoundException("Persoon met id " + id + " niet gevonden");
        }
        return PersoonDTO.createPersoonSOAP(persoonOptional.get());
    }

    public List<PersoonSoap> getAlleSOAPPersonen() {
        List<Persoon> list = repository.findAll();

        List<PersoonSoap> persoonSoaps = list.stream()
                .map(persoon -> PersoonDTO.createPersoonSOAP(persoon))  //personen uit de lijst liggen op de pijnbank en die gaan we met map omzetten naar een PersoonSoap
                .collect(Collectors.toList()); // de PersoonSoaps worden opengeslagen in een List<PersoonSoap>

//        if (list.isEmpty()) {
//            throw new ResourceNotFoundException("Lijst met personen is niet gevonden");
//        }
//        List<PersoonSoap> soapList = new ArrayList<>();
//        for (Persoon persoon : list) {
//            PersoonSoap persoonSoap = PersoonDTO.createPersoonSOAP(persoon);
//            soapList.add(persoonSoap);
//        }

        return persoonSoaps; //soapList;
    }

    public List<Persoon> getAllePersonen() {
        return repository.findAll();
    }

    // ToDo ?? @NotNull of @NonNull, zijn meerdere varianten. Handig?? Nog even overleggen met Johan
    public List<PersoonDTO> getAllePersonenDTOVanAfdeling(@NotNull Long id) {
        return afdelingService.findAfdelingById(id)
                .getPersonen()
                .stream()
                .map(PersoonDTO::new)
                .collect(Collectors.toList());

//        Altijd leuk om te streamen en filters toe te passen, maar handiger om de afdelingService te gebruiken.
//        Filters zou ik nog wel combineren en in een aparte methode wegstoppen
//        return getAllePersonen()
//                .stream()
//                .filter(persoon -> persoon.getAfdeling() != null)
//                .filter(persoon -> persoon.getAfdeling().getId().equals(id))
//                .map(PersoonDTO::new)
//                .collect(Collectors.toList());
    }

    public Persoon postPersoon(Persoon persoon) {
        if (persoon.getAfdelingId() != null) {
            // afdeling gaan koppelen. (alternatief is om dit direct via de afdelingRepository te doen.)
            Afdeling afdeling = afdelingService.findAfdelingById(persoon.getAfdelingId());
            persoon.setAfdeling(afdeling);
        } // Eventueel badrequest als er geen afdeling is ingevuld => keuze
        return repository.save(persoon);
    }

    public Persoon putPersoon(Long id, Persoon persoon) {
        if (!id.equals(persoon.getId())) {
            throw new ConflictException("Id " + id + " komt niet overeen met de gevonden id van persoon.");
        }
        Persoon target = findPersoonById(id);
        target.zetGegevensOver(persoon);
        return repository.save(target);
    }

    public void deletePersoon(Long id) {
        Persoon persoon = findPersoonById(id);
        repository.delete(persoon);
    }
}