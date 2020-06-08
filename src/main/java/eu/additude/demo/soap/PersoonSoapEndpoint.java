package eu.additude.demo.soap;

import eu.additude.demo.controller.PersoonService;
import eu.additude.guides.gs_producing_web_service.GetPersoonRequest;
import eu.additude.guides.gs_producing_web_service.GetAllePersonenRequest;
import eu.additude.guides.gs_producing_web_service.GetPersoonResponse;
import eu.additude.guides.gs_producing_web_service.GetAllePersonenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class PersoonSoapEndpoint {
    private static final String NAMESPACE_URI = "http://additude.eu/guides/gs-producing-web-service";

    @Autowired
    private PersoonService service;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPersoonRequest")
    @ResponsePayload // Maak er een SOAP bericht van
    public GetPersoonResponse getPersoon(@RequestPayload GetPersoonRequest request) {
        System.out.println("LOG- SOAP: personen/" + request.getId() + " - Aanroep van onze webserivce voor het opvragen van één persoon.");
        GetPersoonResponse response = new GetPersoonResponse();
        response.setPersoon(service.findPersoonSOAPById(request.getId()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllePersonenRequest")
    @ResponsePayload // Maak er een SOAP bericht van
    public GetAllePersonenResponse getAllePersonen(@RequestPayload GetAllePersonenRequest request) {
        System.out.println("LOG- SOAP: personen/ - Aanroep van onze webserivce voor het opvragen van alle personen.");
        GetAllePersonenResponse response = new GetAllePersonenResponse();
        response.getPersonenlist().addAll(service.getAlleSOAPPersonen());
        return response;
    }
}