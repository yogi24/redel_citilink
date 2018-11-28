/*Copyright (c) 2017-2018 asyst.co.id All Rights Reserved.
 This software is the confidential and proprietary information of asyst.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with asyst.co.id*/
package com.redel_citilink.redelivery_cl.controller;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wavemaker.commons.wrapper.StringWrapper;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.manager.ExportedFileManager;
import com.wavemaker.runtime.file.model.Downloadable;
import com.wavemaker.tools.api.core.annotations.WMAccessVisibility;
import com.wavemaker.tools.api.core.models.AccessSpecifier;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import com.redel_citilink.redelivery_cl.AircraftUtilization;
import com.redel_citilink.redelivery_cl.service.AircraftUtilizationService;


/**
 * Controller object for domain model class AircraftUtilization.
 * @see AircraftUtilization
 */
@RestController("redelivery_cl.AircraftUtilizationController")
@Api(value = "AircraftUtilizationController", description = "Exposes APIs to work with AircraftUtilization resource.")
@RequestMapping("/redelivery_cl/AircraftUtilization")
public class AircraftUtilizationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AircraftUtilizationController.class);

    @Autowired
	@Qualifier("redelivery_cl.AircraftUtilizationService")
	private AircraftUtilizationService aircraftUtilizationService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new AircraftUtilization instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public AircraftUtilization createAircraftUtilization(@RequestBody AircraftUtilization aircraftUtilization) {
		LOGGER.debug("Create AircraftUtilization with information: {}" , aircraftUtilization);

		aircraftUtilization = aircraftUtilizationService.create(aircraftUtilization);
		LOGGER.debug("Created AircraftUtilization with information: {}" , aircraftUtilization);

	    return aircraftUtilization;
	}

    @ApiOperation(value = "Returns the AircraftUtilization instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public AircraftUtilization getAircraftUtilization(@PathVariable("id") Integer id) {
        LOGGER.debug("Getting AircraftUtilization with id: {}" , id);

        AircraftUtilization foundAircraftUtilization = aircraftUtilizationService.getById(id);
        LOGGER.debug("AircraftUtilization details with id: {}" , foundAircraftUtilization);

        return foundAircraftUtilization;
    }

    @ApiOperation(value = "Updates the AircraftUtilization instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public AircraftUtilization editAircraftUtilization(@PathVariable("id") Integer id, @RequestBody AircraftUtilization aircraftUtilization) {
        LOGGER.debug("Editing AircraftUtilization with id: {}" , aircraftUtilization.getId());

        aircraftUtilization.setId(id);
        aircraftUtilization = aircraftUtilizationService.update(aircraftUtilization);
        LOGGER.debug("AircraftUtilization details with id: {}" , aircraftUtilization);

        return aircraftUtilization;
    }

    @ApiOperation(value = "Deletes the AircraftUtilization instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteAircraftUtilization(@PathVariable("id") Integer id) {
        LOGGER.debug("Deleting AircraftUtilization with id: {}" , id);

        AircraftUtilization deletedAircraftUtilization = aircraftUtilizationService.delete(id);

        return deletedAircraftUtilization != null;
    }

    /**
     * @deprecated Use {@link #findAircraftUtilizations(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of AircraftUtilization instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<AircraftUtilization> searchAircraftUtilizationsByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering AircraftUtilizations list by query filter:{}", (Object) queryFilters);
        return aircraftUtilizationService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of AircraftUtilization instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<AircraftUtilization> findAircraftUtilizations(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering AircraftUtilizations list by filter:", query);
        return aircraftUtilizationService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of AircraftUtilization instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<AircraftUtilization> filterAircraftUtilizations(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering AircraftUtilizations list by filter", query);
        return aircraftUtilizationService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param. If query string is too big to fit in GET request's query param, use POST method with application/x-www-form-urlencoded format.")
    @RequestMapping(value = "/export/{exportType}", method = {RequestMethod.GET,  RequestMethod.POST}, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportAircraftUtilizations(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return aircraftUtilizationService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportAircraftUtilizationsAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = AircraftUtilization.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> aircraftUtilizationService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of AircraftUtilization instances matching the optional query (q) request param. If query string is too big to fit in GET request's query param, use POST method with application/x-www-form-urlencoded format.")
	@RequestMapping(value = "/count", method = {RequestMethod.GET, RequestMethod.POST})
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countAircraftUtilizations( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting AircraftUtilizations");
		return aircraftUtilizationService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getAircraftUtilizationAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return aircraftUtilizationService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service AircraftUtilizationService instance
	 */
	protected void setAircraftUtilizationService(AircraftUtilizationService service) {
		this.aircraftUtilizationService = service;
	}

}