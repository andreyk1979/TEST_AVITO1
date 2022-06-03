package com.amr.project.webapp.controller.swaggerController;

import com.amr.project.model.dto.CategoryDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/** C помощью аннотации @Api над классом контроллера можно добавить описание контроллера,
 *  для проверки результата введите в браузере http://localhost:8888/swagger-ui/   */

@RestController
@RequestMapping("/api/swagger-rest")
@Api(description = "Пример Rest контроллера с аннотациями Swagger")
public class swaggerRestController {

    /** C помощью аннотации @ApiOperation над методом контроллера можно добавить описание метода,
     * с помощью аннотации @ApiParam перед параметром метода можно добавить описание параметра  */

    @ApiOperation(value = "Метод postCategoryDto",
            notes = "Метод postCategoryDto принемает и возращает CategoryDto")
    @PostMapping()
    public ResponseEntity<CategoryDto> postCategoryDto(@ApiParam("Dto категория") @RequestBody CategoryDto categoryDto) {
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }
}
