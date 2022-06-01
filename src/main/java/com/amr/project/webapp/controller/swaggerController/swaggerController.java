package com.amr.project.webapp.controller.swaggerController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/** C помощью аннотации @Api над классом контроллера можно добавить название контроллера,
 *  для проверки результата введите в браузере http://localhost:8888/swagger-ui */

@Controller
@RequestMapping("/api/swagger")
@Api(value = "Swagger контроллер")
public class swaggerController {

    /** C помощью аннотации @ApiOperation над методом контроллера можно добавить название метода (value)
     * и описание метода (notes) в документацию Swagger */

    @ApiOperation(value = "Возвращает helloWorld", notes = "Метод getMethod возращает html страницу helloWorld")
    @GetMapping()
    public String getHelloWorld() {
        return "helloWorld";
    }
}
