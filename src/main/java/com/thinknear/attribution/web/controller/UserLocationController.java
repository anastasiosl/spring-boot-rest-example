package com.thinknear.attribution.web.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import com.thinknear.attribution.annotation.Profile;
import com.thinknear.attribution.web.service.UserLocationService;
import com.thinknear.attribution.web.model.UserLocation;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@Api(basePath = "/attribution/userlocation", value = "UserLocation Service", description = "UserLocation Operations", produces = "application/json")
@RequestMapping("/attribution/userlocation")
public class UserLocationController {
    private UserLocationService userLocationService;

    @Autowired
    public UserLocationController(UserLocationService userLocationService) {
        this.userLocationService = userLocationService;
    }

    public UserLocationService getUserLocationService() {
        return userLocationService;
    }

    public void setUserLocationService(UserLocationService userLocationService) {
        this.userLocationService = userLocationService;
    }

    /**
     * Generates the Location headers for the <code>UserLocation</code> object.
     * TODO: Consider putting this logic in a Filter so that it is done for every relevant response.
     * @param userLocation
     * @return
     */
    protected HttpHeaders getResponseHeadersForUserLocation(UserLocation userLocation) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Location", linkTo(methodOn(UserLocationController.class).getUserLocationById(userLocation.getUserLocationId())).withSelfRel().getHref());
        return responseHeaders;
    }

    @ApiOperation(value = "Gets an existing UserLocation by Id", notes = "Returns a UserLocation for given id")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "UserLocation was not found"),  // TODO: Not implemented
            @ApiResponse(code = 200, message = "UserLocation was found and returned") })
    @Profile("UserLocationController#getUserLocationById")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<UserLocation> getUserLocationById(@PathVariable(value = "id") String id) {
        UserLocation userLocation = userLocationService.getUserLocation(id);
        userLocation.add(linkTo(methodOn(UserLocationController.class).getUserLocationById(id)).withSelfRel());
        return new ResponseEntity<UserLocation>(userLocation, getResponseHeadersForUserLocation(userLocation), HttpStatus.OK);
    }

    @ApiOperation(value = "Create new UserLocation", notes = "Creates new UserLocation")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Fields are with validation errors"),  // TODO: Not implemented
            @ApiResponse(code = 201, message = "UserLocation was created") })
    @Profile("UserLocationController#createUserLocation")
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public HttpEntity<UserLocation> createUserLocation(@RequestBody UserLocation userLocation) {
        userLocation = userLocationService.createUserLocation(userLocation);
        userLocation.add(linkTo(methodOn(UserLocationController.class).getUserLocationById(userLocation.getUserLocationId())).withSelfRel());
        return new ResponseEntity<UserLocation>(userLocation, getResponseHeadersForUserLocation(userLocation), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Updates an existing UserLocation", notes = "Updates UserLocation")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "UserLocation to update was not found"),  // TODO: Not implemented
            @ApiResponse(code = 400, message = "Fields are with validation errors"),  // TODO: Not implemented
            @ApiResponse(code = 501, message = "Not implemented yet"),  // TODO: Not implemented
            @ApiResponse(code = 204, message = "UserLocation was updated") })
    @Profile("UserLocationController#updateUserLocation")
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public HttpEntity<Void> updateUserLocation(@RequestBody UserLocation userLocation) {
        userLocationService.updateUserLocation(userLocation);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "Deletes an existing UserLocation", notes = "Deletes UserLocation")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "UserLocation to delete was not found"),  // TODO: Not implemented
            @ApiResponse(code = 501, message = "Not implemented yet"),  // TODO: Not implemented
            @ApiResponse(code = 204, message = "UserLocation was deleted") })
    @Profile("UserLocationController#deleteUserLocation")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public HttpEntity<Void> deleteUserLocation(@PathVariable(value = "id") String id) {
        userLocationService.deleteUserLocation(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }




}