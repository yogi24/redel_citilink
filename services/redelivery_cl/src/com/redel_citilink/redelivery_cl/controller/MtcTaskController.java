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
import com.wavemaker.runtime.security.xss.XssDisable;
import com.wavemaker.tools.api.core.annotations.WMAccessVisibility;
import com.wavemaker.tools.api.core.models.AccessSpecifier;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import com.redel_citilink.redelivery_cl.MtcTask;
import com.redel_citilink.redelivery_cl.MtcTaskPlanned;
import com.redel_citilink.redelivery_cl.service.MtcTaskService;


/**
 * Controller object for domain model class MtcTask.
 * @see MtcTask
 */
@RestController("redelivery_cl.MtcTaskController")
@Api(value = "MtcTaskController", description = "Exposes APIs to work with MtcTask resource.")
@RequestMapping("/redelivery_cl/MtcTask")
public class MtcTaskController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MtcTaskController.class);

    @Autowired
	@Qualifier("redelivery_cl.MtcTaskService")
	private MtcTaskService mtcTaskService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new MtcTask instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public MtcTask createMtcTask(@RequestBody MtcTask mtcTask) {
		LOGGER.debug("Create MtcTask with information: {}" , mtcTask);

		mtcTask = mtcTaskService.create(mtcTask);
		LOGGER.debug("Created MtcTask with information: {}" , mtcTask);

	    return mtcTask;
	}

    @ApiOperation(value = "Returns the MtcTask instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public MtcTask getMtcTask(@PathVariable("id") Integer id) {
        LOGGER.debug("Getting MtcTask with id: {}" , id);

        MtcTask foundMtcTask = mtcTaskService.getById(id);
        LOGGER.debug("MtcTask details with id: {}" , foundMtcTask);

        return foundMtcTask;
    }

    @ApiOperation(value = "Updates the MtcTask instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public MtcTask editMtcTask(@PathVariable("id") Integer id, @RequestBody MtcTask mtcTask) {
        LOGGER.debug("Editing MtcTask with id: {}" , mtcTask.getId());

        mtcTask.setId(id);
        mtcTask = mtcTaskService.update(mtcTask);
        LOGGER.debug("MtcTask details with id: {}" , mtcTask);

        return mtcTask;
    }

    @ApiOperation(value = "Deletes the MtcTask instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteMtcTask(@PathVariable("id") Integer id) {
        LOGGER.debug("Deleting MtcTask with id: {}" , id);

        MtcTask deletedMtcTask = mtcTaskService.delete(id);

        return deletedMtcTask != null;
    }

    /**
     * @deprecated Use {@link #findMtcTasks(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of MtcTask instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @XssDisable
    public Page<MtcTask> searchMtcTasksByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering MtcTasks list by query filter:{}", (Object) queryFilters);
        return mtcTaskService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of MtcTask instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<MtcTask> findMtcTasks(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering MtcTasks list by filter:", query);
        return mtcTaskService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of MtcTask instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @XssDisable
    public Page<MtcTask> filterMtcTasks(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering MtcTasks list by filter", query);
        return mtcTaskService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param. If query string is too big to fit in GET request's query param, use POST method with application/x-www-form-urlencoded format.")
    @RequestMapping(value = "/export/{exportType}", method = {RequestMethod.GET,  RequestMethod.POST}, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @XssDisable
    public Downloadable exportMtcTasks(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return mtcTaskService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @XssDisable
    public StringWrapper exportMtcTasksAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = MtcTask.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> mtcTaskService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of MtcTask instances matching the optional query (q) request param. If query string is too big to fit in GET request's query param, use POST method with application/x-www-form-urlencoded format.")
	@RequestMapping(value = "/count", method = {RequestMethod.GET, RequestMethod.POST})
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	@XssDisable
	public Long countMtcTasks( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting MtcTasks");
		return mtcTaskService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	@XssDisable
	public Page<Map<String, Object>> getMtcTaskAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return mtcTaskService.getAggregatedValues(aggregationInfo, pageable);
    }

    @RequestMapping(value="/{id:.+}/mtcTaskPlanneds", method=RequestMethod.GET)
    @ApiOperation(value = "Gets the mtcTaskPlanneds instance associated with the given id.")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<MtcTaskPlanned> findAssociatedMtcTaskPlanneds(@PathVariable("id") Integer id, Pageable pageable) {

        LOGGER.debug("Fetching all associated mtcTaskPlanneds");
        return mtcTaskService.findAssociatedMtcTaskPlanneds(id, pageable);
    }

    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service MtcTaskService instance
	 */
	protected void setMtcTaskService(MtcTaskService service) {
		this.mtcTaskService = service;
	}

}