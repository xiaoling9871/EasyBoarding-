package com.example.springdatademo.service;

import com.example.springdatademo.domain.*;
import com.example.springdatademo.repository.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class HouseService {
    private final HouseRepo houseRepo;
    private final FacilityReportRepo facilityReportRepo;
    private final FacilityRepo facilityRepo;
    private final LandlordRepo landlordRepo;
    private final FacilityReportDetailRepo facilityReportDetailRepo;

    @Autowired
    public HouseService(HouseRepo houseRepo, FacilityReportRepo facilityReportRepo, FacilityRepo facilityRepo,
                        LandlordRepo landlordRepo, FacilityReportDetailRepo facilityReportDetailRepo) {
        this.houseRepo = houseRepo;
        this.facilityReportRepo = facilityReportRepo;
        this.facilityRepo = facilityRepo;
        this.landlordRepo = landlordRepo;
        this.facilityReportDetailRepo = facilityReportDetailRepo;
    }

    public String getAddress(Integer houseId) {
        return houseRepo.findById(houseId).get().getAddress();
    }

    public void addNewReport(String title, String description, String type, Integer houseId, String empId) {
        FacilityReport newReport = new FacilityReport();
        newReport.setTitle(title);
        newReport.setDescription(description);
        newReport.setStatus("Open");
        newReport.setCreateDate(new Timestamp(new Date().getTime()));
        newReport.setEmployeeId(empId);
        Facility facility = facilityRepo.findFacilityByHouseIdAndType(houseId, type);
        newReport.setFacility(facility);
        facilityReportRepo.save(newReport);
    }

    public List<ResponseReportEMP> viewAllReports() {

        List<FacilityReport> facilityReports = facilityReportRepo.findAll();
        List<ResponseReportEMP> reportEMPS = new ArrayList<>();
        for (FacilityReport facilityReport:facilityReports){
            List<Map<String, Object>> tempDetail =  facilityReportDetailRepo.ResponseReportDetails(facilityReport.getId());
            ResponseReportEMP responseReport =
                    ResponseReportEMP.builder().Title(facilityReport.getTitle()).Description(facilityReport.getDescription())
                            .status(facilityReport.getStatus()).time(facilityReport.getCreateDate()).author(facilityReport.getEmployeeId())
                            .facilityReportDetails(tempDetail)
                            .id(facilityReport.getId())
                            .build();
            reportEMPS.add(responseReport);
        }
        return reportEMPS;
    }

    public List<Map<String, Object>> viewReportsAuthor(String userId) {
        List<Map<String, Object>> tempDetail = facilityReportDetailRepo.ResponseReportDetailsAuthor(userId);
        return tempDetail;
    }
    public void editComment(List<Map<String, Object>> commentList, String token) throws ParseException {
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        JSONObject json = new JSONObject(payload);
        String empId = (String) json.get("employeeId");
        List<Map<String, Object>> oldList = viewReportsAuthor(empId);

        for(int i = 0; i < commentList.size(); i++){
            if (!oldList.get(i).equals(commentList.get(i))) {
                Timestamp editTime = new Timestamp((Long) commentList.get(i).get("last_modification_date"));
                Timestamp createTime = new Timestamp((Long) commentList.get(i).get("create_date"));
                FacilityReportDetail detail = facilityReportDetailRepo.findFacilityReportDetailByLastModificationDateAndCreateDate(editTime, createTime);
                detail.setComment((String) commentList.get(i).get("comment"));
                detail.setLastModificationDate(new Timestamp(new Date().getTime()));
                facilityReportDetailRepo.save(detail);}
        }
    }

////   for hr:
    public ResponseLandlord landlordInfo(Integer houseId){
        House house = houseRepo.findById(houseId).get();
        Landlord landlord = landlordRepo.findLandlordByHouses(house);
        return ResponseLandlord.builder().fullname(landlord.getFirstname() + " " +landlord.getLastname())
                .phone(landlord.getCellphone()).email(landlord.getEmail()).build();
    }

  public Map<Integer, Integer> houseOccList(){
      List<Map<String,Object>> houselist = houseRepo.houseOccList();
      Map<Integer,Integer> occMap = new HashMap<>();
      for (Map<String,Object> house :houselist){
         occMap.put((Integer) house.get("id"), (Integer) house.get("max_occupant"));
      }
        return occMap;
  }

    public void addHouse(ResponseAddHouseHr responseAddHouseHr) {

     Landlord landlord = new Landlord();
     landlord.setFirstname(responseAddHouseHr.getFirstname());
     landlord.setLastname(responseAddHouseHr.getLastname());
     landlord.setCellphone(responseAddHouseHr.getCellphone());
     landlord.setEmail(responseAddHouseHr.getEmail());
     landlordRepo.save(landlord);

     House house = new House();
    house.setAddress(responseAddHouseHr.getAddress());
    house.setMaxOccupant(responseAddHouseHr.getMaxOccupants());
    house.setLandlord(landlordRepo.findLandlordByCellphone(responseAddHouseHr.getCellphone()));
    houseRepo.save(house);

     Facility facility = new Facility();
     facility.setType(responseAddHouseHr.getType());
     facility.setDescription(responseAddHouseHr.getDescription());
     facility.setQuantity(responseAddHouseHr.getQuantity());
     facility.setHouse(houseRepo.findByAddress(responseAddHouseHr.getAddress()));
     facilityRepo.save(facility);

 }
    public List<Facility> viewFacilities(Integer houseId){
        return facilityRepo.findFacilitiesByHouseId(houseId);
    }
    public void deleteHouse(Integer id){
        House house = houseRepo.findById(id).get();
        facilityRepo.deleteAllByHouse(house);
        houseRepo.delete(house);
    }

    public List<Map<String,Object>> facilitySummary(Integer house_id){
        return facilityRepo.facilitySummary(house_id);
    }

    public void addComments(Integer reportId, String comment, String employeeId){
        FacilityReportDetail facilityReportDetail = new FacilityReportDetail();
        facilityReportDetail.setComment(comment);
        facilityReportDetail.setFacilityReport(facilityReportRepo.findById(reportId).get());
        facilityReportDetail.setCreateDate(new Timestamp(new Date().getTime()));
        facilityReportDetail.setLastModificationDate(new Timestamp(new Date().getTime()));
        facilityReportDetail.setEmployeeId(employeeId);
        facilityReportDetailRepo.save(facilityReportDetail);
    }

    public void changeStatus(Integer reportID, String status) {
       FacilityReport facilityReport = facilityReportRepo.findById(reportID).get();
       facilityReport.setStatus(status);
       facilityReportRepo.save(facilityReport);
    }
}