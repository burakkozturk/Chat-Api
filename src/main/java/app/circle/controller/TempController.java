package app.circle.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TempController {


    @GetMapping("/temp")
    public ResponseEntity<String> temp(){
        return  ResponseEntity.ok("temp, secured method");
    }
}
