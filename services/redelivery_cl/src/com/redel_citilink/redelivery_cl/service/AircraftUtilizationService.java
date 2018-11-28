/*Copyright (c) 2017-2018 asyst.co.id All Rights Reserved.
 This software is the confidential and proprietary information of asyst.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with asyst.co.id*/
package com.redel_citilink.redelivery_cl.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;

import com.redel_citilink.redelivery_cl.AircraftUtilization;

/**
 * Service object for domain model class {@link AircraftUtilization}.
 */
public interface AircraftUtilizationService {

    /**
     * Creates a new AircraftUtilization. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on AircraftUtilization if any.
     *
     * @param aircraftUtilization Details of the AircraftUtilization to be created; value cannot be null.
     * @return The newly created AircraftUtilization.
     */
    AircraftUtilization create(@Valid AircraftUtilization aircraftUtilization);


	/**
     * Returns AircraftUtilization by given id if exists.
     *
     * @param aircraftutilizationId The id of the AircraftUtilization to get; value cannot be null.
     * @return AircraftUtilization associated with the given aircraftutilizationId.
	 * @throws EntityNotFoundException If no AircraftUtilization is found.
     */
    AircraftUtilization getById(Integer aircraftutilizationId);

    /**
     * Find and return the AircraftUtilization by given id if exists, returns null otherwise.
     *
     * @param aircraftutilizationId The id of the AircraftUtilization to get; value cannot be null.
     * @return AircraftUtilization associated with the given aircraftutilizationId.
     */
    AircraftUtilization findById(Integer aircraftutilizationId);

	/**
     * Find and return the list of AircraftUtilizations by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param aircraftutilizationIds The id's of the AircraftUtilization to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return AircraftUtilizations associated with the given aircraftutilizationIds.
     */
    List<AircraftUtilization> findByMultipleIds(List<Integer> aircraftutilizationIds, boolean orderedReturn);


    /**
     * Updates the details of an existing AircraftUtilization. It replaces all fields of the existing AircraftUtilization with the given aircraftUtilization.
     *
     * This method overrides the input field values using Server side or database managed properties defined on AircraftUtilization if any.
     *
     * @param aircraftUtilization The details of the AircraftUtilization to be updated; value cannot be null.
     * @return The updated AircraftUtilization.
     * @throws EntityNotFoundException if no AircraftUtilization is found with given input.
     */
    AircraftUtilization update(@Valid AircraftUtilization aircraftUtilization);

    /**
     * Deletes an existing AircraftUtilization with the given id.
     *
     * @param aircraftutilizationId The id of the AircraftUtilization to be deleted; value cannot be null.
     * @return The deleted AircraftUtilization.
     * @throws EntityNotFoundException if no AircraftUtilization found with the given id.
     */
    AircraftUtilization delete(Integer aircraftutilizationId);

    /**
     * Deletes an existing AircraftUtilization with the given object.
     *
     * @param aircraftUtilization The instance of the AircraftUtilization to be deleted; value cannot be null.
     */
    void delete(AircraftUtilization aircraftUtilization);

    /**
     * Find all AircraftUtilizations matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching AircraftUtilizations.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<AircraftUtilization> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all AircraftUtilizations matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching AircraftUtilizations.
     *
     * @see Pageable
     * @see Page
     */
    Page<AircraftUtilization> findAll(String query, Pageable pageable);

    /**
     * Exports all AircraftUtilizations matching the given input query to the given exportType format.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param exportType The format in which to export the data; value cannot be null.
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @return The Downloadable file in given export type.
     *
     * @see Pageable
     * @see ExportType
     * @see Downloadable
     */
    Downloadable export(ExportType exportType, String query, Pageable pageable);

    /**
     * Exports all AircraftUtilizations matching the given input query to the given exportType format.
     *
     * @param options The export options provided by the user; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @param outputStream The output stream of the file for the exported data to be written to.
     *
     * @see DataExportOptions
     * @see Pageable
     * @see OutputStream
     */
    void export(DataExportOptions options, Pageable pageable, OutputStream outputStream);

    /**
     * Retrieve the count of the AircraftUtilizations in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the AircraftUtilization.
     */
    long count(String query);

    /**
     * Retrieve aggregated values with matching aggregation info.
     *
     * @param aggregationInfo info related to aggregations.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @return Paginated data with included fields.
     *
     * @see AggregationInfo
     * @see Pageable
     * @see Page
	 */
    Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable);


}