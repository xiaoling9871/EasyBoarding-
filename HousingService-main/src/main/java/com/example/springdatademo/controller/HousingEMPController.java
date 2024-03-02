package com.example.springdatademo.controller;

import com.example.springdatademo.domain.ResponseReportEMP;
import com.example.springdatademo.service.HouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/emp")
@Api("Housing Service for Employees endpoint")
@PreAuthorize(value = "hasAnyAuthority('EMP','HR')")
public class HousingEMPController {
    private final HouseService service;

    @Autowired
    public HousingEMPController(HouseService service) {
        this.service = service;
    }

    @GetMapping(value = "/detail/address")
    @ApiOperation(value = "Get address by houseId", response = String.class)
    public String getAddress(@RequestParam(name = "houseId") Integer HouseId){
        return service.getAddress(HouseId);
    }

    @PostMapping(value = "/report/createReport")
    @ApiOperation(value = "Add new report", response = String.class)
    public String addNewReport(@RequestParam(name = "title")String title, @RequestParam(name = "description")String description, @RequestParam(name = "type")String type, @RequestParam(name = "houseId")Integer houseId,@RequestHeader("Authorization") String token){
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        JSONObject json = new JSONObject(payload);
        String empId = (String) json.get("employeeId");
        service.addNewReport(title,description,type,houseId, empId);
        return "Your report regarding " + type +" has been added SUCCESSFULLY!!";
    }
    @PostMapping(value = "/addComment")
    @ApiOperation(value = "Add new comment", response = String.class)
    public String addComments(@RequestParam(name = "reportId") Integer reportId, @RequestParam(name = "comment") String comment, @RequestHeader("Authorization") String token){
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        JSONObject json = new JSONObject(payload);
        String employeeId = (String) json.get("employeeId");
        service.addComments(reportId, comment, employeeId);
        return "Your comment has been added SUCCESSFULLY!!";
    }
    @GetMapping(value = "/report/existingReports")
    @ApiOperation(value = "View all the reports", response = Iterable.class)
    public List<ResponseReportEMP> viewAllReports(){
        return service.viewAllReports();
    }
    @GetMapping(value = "/report/existingReportsAuthor")
    @ApiOperation(value = "View all the reports made by current employee", response = Iterable.class)
    public List<Map<String, Object>> viewReportsAuthor(@RequestHeader("Authorization") String token){
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        JSONObject json = new JSONObject(payload);
        String empId = (String) json.get("employeeId");
        return service.viewReportsAuthor(empId);
    }

    @PatchMapping(value = "/report/editComment")
    @ApiOperation(value = "Edit reports", response = String.class)
    public String editComment(@RequestBody List<Map<String, Object>> commentList, @RequestHeader("Authorization") String token) throws ParseException {
        service.editComment(commentList, token);
        return "You comments has been edited SUCCESSFULLY!!";
    }


}
