package com.amr.project.webapp.controller.swaggerController;

import com.amr.project.model.dto.CountryDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/** C помощью аннотации @Api над классом контроллера можно добавить описание контроллера,
 *  для проверки результата введите в браузере http://localhost:8888/swagger-ui/   */

@Controller
@RequestMapping("/api/swagger")
@Api(description = "Пример контроллера с аннотациями Swagger")
public class swaggerController {

    /** C помощью аннотации @ApiOperation над методом контроллера можно добавить описание метода */

    @ApiOperation(value = "Метод getHelloWorld", notes = "Метод getHelloWorld принемает CountryDto " +
            "и возращает html страницу helloWorld")
    @GetMapping()
    public String getHelloWorld(@ModelAttribute("countryDto") CountryDto countryDto) {
        return "helloWorld";
    }
}
