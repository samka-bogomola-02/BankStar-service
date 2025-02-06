package bank.recommendationservice.fintech.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management")
public class InfoController {
    private final BuildProperties buildProperties;

    @Autowired
    public InfoController(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }


    /**
     * @return строка, содержащая имя и версию приложения
     */
    @GetMapping("/info")
    public String info() {
        return "name = " + buildProperties.getName() + ", version = " + buildProperties.getVersion();
    }
}

