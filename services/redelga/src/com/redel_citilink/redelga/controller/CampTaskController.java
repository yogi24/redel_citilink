/*Copyright (c) 2017-2018 asyst.co.id All Rights Reserved.
 This software is the confidential and proprietary information of asyst.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with asyst.co.id*/
package com.redel_citilink.redelga.controller;

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

import com.redel_citilink.redelga.CampTask;
import com.redel_citilink.redelga.TaskSchedule;
import com.redel_citilink.redelga.service.CampTaskService;


/**
 * Controller object for domain model class CampTask.
 * @see CampTask
 */
@RestController("redelga.CampTaskController")
@Api(value = "CampTaskController", description = "Exposes APIs to work with CampTask resource.")
@RequestMapping("/redelga/CampTask")
public class CampTaskController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CampTaskController.class);

    @Autowired
	@Qualifier("redelga.CampTaskService")
	private CampTaskService campTaskService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new CampTask instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public CampTask createCampTask(@RequestBody CampTask campTask) {
		LOGGER.debug("Create CampTask with information: {}" , campTask);

		campTask = campTaskService.create(campTask);
		LOGGER.debug("Created CampTask with information: {}" , campTask);

	    return campTask;
	}

    @ApiOperation(value = "Returns the CampTask instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public CampTask getCampTask(@PathVariable("id") Integer id) {
        LOGGER.debug("Getting CampTask with id: {}" , id);

        CampTask foundCampTask = campTaskService.getById(id);
        LOGGER.debug("CampTask details with id: {}" , foundCampTask);

        return foundCampTask;
    }

    @ApiOperation(value = "Updates the CampTask instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public CampTask editCampTask(@PathVariable("id") Integer id, @RequestBody CampTask campTask) {
        LOGGER.debug("Editing CampTask with id: {}" , campTask.getId());

        campTask.setId(id);
        campTask = campTaskService.update(campTask);
        LOGGER.debug("CampTask details with id: {}" , campTask);

        return campTask;
    }

    @ApiOperation(value = "Deletes the CampTask instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteCampTask(@PathVariable("id") Integer id) {
        LOGGER.debug("Deleting CampTask with id: {}" , id);

        CampTask deletedCampTask = campTaskService.delete(id);

        return deletedCampTask != null;
    }

    /**
     * @deprecated Use {@link #findCampTasks(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of CampTask instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<CampTask> searchCampTasksByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering CampTasks list by query filter:{}", (Object) queryFilters);
        return campTaskService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of CampTask instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<CampTask> findCampTasks(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering CampTasks list by filter:", query);
        return campTaskService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of CampTask instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<CampTask> filterCampTasks(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering CampTasks list by filter", query);
        return campTaskService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param. If query string is too big to fit in GET request's query param, use POST method with application/x-www-form-urlencoded format.")
    @RequestMapping(value = "/export/{exportType}", method = {RequestMethod.GET,  RequestMethod.POST}, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportCampTasks(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return campTaskService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportCampTasksAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = CampTask.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> campTaskService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of CampTask instances matching the optional query (q) request param. If query string is too big to fit in GET request's query param, use POST method with application/x-www-form-urlencoded format.")
	@RequestMapping(value = "/count", method = {RequestMethod.GET, RequestMethod.POST})
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countCampTasks( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting CampTasks");
		return campTaskService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getCampTaskAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return campTaskService.getAggregatedValues(aggregationInfo, pageable);
    }

    @RequestMapping(value="/{id:.+}/taskSchedules", method=RequestMethod.GET)
    @ApiOperation(value = "Gets the taskSchedules instance associated with the given id.")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TaskSchedule> findAssociatedTaskSchedules(@PathVariable("id") Integer id, Pageable pageable) {

        LOGGER.debug("Fetching all associated taskSchedules");
        return campTaskService.findAssociatedTaskSchedules(id, pageable);
    }

    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service CampTaskService instance
	 */
	protected void setCampTaskService(CampTaskService service) {
		this.campTaskService = service;
	}

}