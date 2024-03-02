package com.example.springdatademo.controller;

import com.example.springdatademo.domain.*;
import com.example.springdatademo.repository.HouseRepo;
import com.example.springdatademo.service.HouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hr")
@Api("Housing Service for HRs endpoint")
@PreAuthorize(value = "hasAuthority('HR')")
public class HousingHRController {
    private final HouseService service;

    @Autowired
    public HousingHRController(HouseService service) {
        this.service = service;
    }
    @GetMapping(value = "/landlordInfo")
    @ApiOperation(value = "Get landlordInfo by houseId", response = ResponseLandlord.class)
    public ResponseLandlord landlordInfo(@RequestParam Integer houseId){
        return service.landlordInfo(houseId);
    }
    @GetMapping(value = "/houseOccList")
    @ApiOperation(value = "Get occupation map for each house", response = Map.class)
    public Map<Integer, Integer> houseOccList(){
        return service.houseOccList();
    }
    @PostMapping(value = "/addHouse")
    @ApiOperation(value = "Add house and its facilities and landlord", response = String.class)
    public String addHouse(@RequestBody ResponseAddHouseHr responseAddHouseHr){
        service.addHouse(responseAddHouseHr);
        return "The house and its landlord and facilities has been added SUCCESSFULLY!!";
    }
    @DeleteMapping(value = "/deleteHouse")
    @ApiOperation(value = "Delete house and its facilities", response = String.class)
    public String deleteHouse(@RequestParam Integer houseId){
        service.deleteHouse(houseId);
        return "The house and its facilities has been deleted SUCCESSFULLY!!";
    }
    @GetMapping(value = "/facilityInfo")
    @ApiOperation(value = "View facilities info by houseId", response = Iterable.class)
    public List<Facility> viewFacilities(@RequestParam Integer houseId){
        return service.viewFacilities(houseId);
    }

    @GetMapping(value = "/facilitySum")
    @ApiOperation(value = "Get list of facilities mapping by houseId", response = Iterable.class)
    public List<Map<String,Object>> facilitySummary(@RequestParam Integer house_id){
        return service.facilitySummary(house_id);
    }

    @PatchMapping(value = "/changeStatus")
    @ApiOperation(value = "Change Report status", response = String.class)
    public String changeStatus(@RequestParam Integer reportId, @RequestParam String status){
        service.changeStatus(reportId,status);
        return "Report " + reportId + " has been updated SUCCESSFULLY to status " + status.toUpperCase() +" !!!";
    }

}
