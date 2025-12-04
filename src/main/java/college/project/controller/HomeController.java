package college.project.controller;

import college.project.TemporaryClass.ReturnSearchedInfo;
import college.project.TemporaryClass.SearchBlood;
import college.project.service.SearchBloodService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home/page")
public class HomeController {

    @Autowired
    private SearchBloodService searchBloodService;

    @PostMapping("/searchBlood")
    public ResponseEntity<List<ReturnSearchedInfo>> searchBlood(@Valid @RequestBody SearchBlood searchBlood){

        return ResponseEntity.ok(searchBloodService.searchBlood(searchBlood));
    }
}
