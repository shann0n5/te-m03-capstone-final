package com.techelevator.controller;

import com.techelevator.exception.ServiceException;
import com.techelevator.model.ServiceRequest;
import com.techelevator.service.ServiceRequestService;
import com.techelevator.service.ServiceRequestServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class ServiceRequestController {
    private ServiceRequestService serviceRequestService;

    public ServiceRequestController(ServiceRequestService serviceRequestService) {
        this.serviceRequestService = serviceRequestService;
    }

    @GetMapping("/service-requests")
    public List<ServiceRequest> getAllServiceRequests(@Valid Principal principal){
        try{
            List<ServiceRequest> serviceRequests = serviceRequestService.viewAllServiceRequests(principal);
            return serviceRequests;
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error encountered");
        }
    }

//    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/service-requests")
    @ResponseStatus(HttpStatus.CREATED) //Status: Open
    public ResponseEntity<ServiceRequest> addServiceRequest(@Valid Principal principal, @RequestBody ServiceRequest newServiceRequest){
        ServiceRequest createdServiceRequest = null;
        try{
            createdServiceRequest = serviceRequestService.createServiceRequest(principal, newServiceRequest);
            if(createdServiceRequest == null){
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                return ResponseEntity.ok(createdServiceRequest);
            }
        }catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error encountered");
        }
    }


    @GetMapping("/service-requests/status/{status}")
    public List<ServiceRequest> getServiceRequestsByStatus(@Valid Principal principal, String status){
        try{
            List<ServiceRequest> serviceRequests = serviceRequestService.viewServiceRequestsByStatus(principal, status);
            return serviceRequests;
        }catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error encountered");
        }
    }

    @GetMapping("/service-requests/{id}")
    public ServiceRequest getServiceRequestById(@Valid Principal principal, @PathVariable("id") int serviceRequestId){
        try{
            ServiceRequest serviceRequest = serviceRequestService.viewServiceRequestById(principal, serviceRequestId);
            if(serviceRequest == null){
                throw new ServiceException("No service request found with ID: " + serviceRequestId);
            }
            return serviceRequest;
        }catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error encountered");
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/service-requests/{id}") //Status: In Progress
    public ServiceRequest approveServiceRequest(@Valid Principal principal, @RequestBody ServiceRequest serviceRequest,
                                                @PathVariable("id") int serviceRequestId){
        try{
            ServiceRequest updateServiceRequest = serviceRequestService.updateServiceRequest(principal, serviceRequest);
            if(updateServiceRequest == null){
                throw new ServiceException("No service request found with ID: " + serviceRequestId);
            }
            return updateServiceRequest;
        }catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error encountered");
        }
    }

}
