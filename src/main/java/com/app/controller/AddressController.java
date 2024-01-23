package com.app.controller;

import com.app.dto.ResponseDTO;
import com.app.exceptions.SystemException;
import com.app.exceptions.UnexpectedErrorException;
import com.app.pojo.Address;
import com.app.service.IAddressService;
import com.app.utils.Constants;
import com.app.utils.Error;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/address")
public class AddressController {

    @Autowired
    IAddressService addressService;

    public AddressController() {
        log.info("in " + getClass().getName());
    }

    @Operation(summary = "Get all the addresses for given user",
            description = "This API is used to get all the addresses for the User with given userId",
            tags = {"Checks"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Unexpected Error", content = @Content (schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "403", description = "Access Denied - User is either invalid or is not entitled to requested api action", content = @Content (schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "404", description = "Entity Not Found", content = @Content (schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content (schema = @Schema(implementation = Error.class)))
            }
    )
    @GetMapping(
            value = "/list/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDTO<List>> getAllAddressList(
            @Parameter(description = "User Identifier", required = true) @PathVariable("userId") String userId,
            @Parameter(description = "Unique Request Id", required = false) @RequestHeader(required = false, value = "reqId") String reqId
    ) throws SystemException {

        List<Address> allAddresses = addressService.getAllAddresses(Integer.parseInt(userId));
        ResponseDTO<List> responseDTO = new ResponseDTO<>(true, allAddresses);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

/******************************************************************************************************************************************************************************/
    @PostMapping("/add")
    public ResponseDTO addNewAddress(@RequestBody @Valid Address address) {
        log.info("in add new address");
        Address add = addressService.addAddress(address);
        if (add != null) {
            return new ResponseDTO(true, "Address added successfully");
        }
        throw new UnexpectedErrorException("Error while adding new address", Constants.ERR_DEFAULT);
    }

/******************************************************************************************************************************************************************************/
    @PutMapping("/update/{add_id}")
    public ResponseDTO updateAddress(@PathVariable String add_id, @RequestBody Address address) {
        log.info("in update  address");
        Address add = addressService.updateAddress(Integer.parseInt(add_id), address);
        if (add != null) {
            return new ResponseDTO(true, "Address Updated successfully");
        }
        throw new UnexpectedErrorException("Error while updating address", Constants.ERR_DEFAULT);
    }

/******************************************************************************************************************************************************************************/
    @DeleteMapping("/delete/{add_id}")
    public ResponseDTO deleteAddress(@PathVariable String add_id) {
        log.info("in delete  address");
        return new ResponseDTO(true, addressService.deleteAddress(Integer.parseInt(add_id)));
    }

/******************************************************************************************************************************************************************************/

}