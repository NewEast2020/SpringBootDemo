package eu.additude.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {

    @RequestMapping("/") // de ("/") is in dit geval niet persé nodig. => mapping vanaf het gedeelte achter 8080
    public String helloWorld() {
        return "Welkom op mijn super mooie website... En nu ben ik aangepast.";
    }

    @RequestMapping("/hacking") // de ("/") is in dit geval niet persé nodig. => mapping vanaf het gedeelte achter 8080
    public String hacking() {
        return "Mijn site wordt gehacked...";
    }

    @RequestMapping("/landing")
    public String landing(){
        return "<html><head><title>Hello</title><head><body><b>Hello,</b><br>prutsers united</body></html>";
    }
}
