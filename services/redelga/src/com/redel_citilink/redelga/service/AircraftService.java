/*Copyright (c) 2017-2018 asyst.co.id All Rights Reserved.
 This software is the confidential and proprietary information of asyst.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with asyst.co.id*/
package com.redel_citilink.redelga.service;

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

import com.redel_citilink.redelga.Aircraft;
import com.redel_citilink.redelga.AircraftMtcSchedule;
import com.redel_citilink.redelga.AircraftUtilizationInfo;
import com.redel_citilink.redelga.RedeliveryMatrix;
import com.redel_citilink.redelga.TaskSchedule;

/**
 * Service object for domain model class {@link Aircraft}.
 */
public interface AircraftService {

    /**
     * Creates a new Aircraft. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on Aircraft if any.
     *
     * @param aircraft Details of the Aircraft to be created; value cannot be null.
     * @return The newly created Aircraft.
     */
    Aircraft create(@Valid Aircraft aircraft);


	/**
     * Returns Aircraft by given id if exists.
     *
     * @param aircraftId The id of the Aircraft to get; value cannot be null.
     * @return Aircraft associated with the given aircraftId.
	 * @throws EntityNotFoundException If no Aircraft is found.
     */
    Aircraft getById(Integer aircraftId);

    /**
     * Find and return the Aircraft by given id if exists, returns null otherwise.
     *
     * @param aircraftId The id of the Aircraft to get; value cannot be null.
     * @return Aircraft associated with the given aircraftId.
     */
    Aircraft findById(Integer aircraftId);

	/**
     * Find and return the list of Aircrafts by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param aircraftIds The id's of the Aircraft to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return Aircrafts associated with the given aircraftIds.
     */
    List<Aircraft> findByMultipleIds(List<Integer> aircraftIds, boolean orderedReturn);


    /**
     * Updates the details of an existing Aircraft. It replaces all fields of the existing Aircraft with the given aircraft.
     *
     * This method overrides the input field values using Server side or database managed properties defined on Aircraft if any.
     *
     * @param aircraft The details of the Aircraft to be updated; value cannot be null.
     * @return The updated Aircraft.
     * @throws EntityNotFoundException if no Aircraft is found with given input.
     */
    Aircraft update(@Valid Aircraft aircraft);

    /**
     * Deletes an existing Aircraft with the given id.
     *
     * @param aircraftId The id of the Aircraft to be deleted; value cannot be null.
     * @return The deleted Aircraft.
     * @throws EntityNotFoundException if no Aircraft found with the given id.
     */
    Aircraft delete(Integer aircraftId);

    /**
     * Deletes an existing Aircraft with the given object.
     *
     * @param aircraft The instance of the Aircraft to be deleted; value cannot be null.
     */
    void delete(Aircraft aircraft);

    /**
     * Find all Aircrafts matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching Aircrafts.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<Aircraft> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all Aircrafts matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching Aircrafts.
     *
     * @see Pageable
     * @see Page
     */
    Page<Aircraft> findAll(String query, Pageable pageable);

    /**
     * Exports all Aircrafts matching the given input query to the given exportType format.
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
     * Exports all Aircrafts matching the given input query to the given exportType format.
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
     * Retrieve the count of the Aircrafts in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the Aircraft.
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

    /*
     * Returns the associated aircraftMtcSchedules for given Aircraft id.
     *
     * @param id value of id; value cannot be null
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of associated AircraftMtcSchedule instances.
     *
     * @see Pageable
     * @see Page
     */
    Page<AircraftMtcSchedule> findAssociatedAircraftMtcSchedules(Integer id, Pageable pageable);

    /*
     * Returns the associated aircraftUtilizationInfos for given Aircraft id.
     *
     * @param id value of id; value cannot be null
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of associated AircraftUtilizationInfo instances.
     *
     * @see Pageable
     * @see Page
     */
    Page<AircraftUtilizationInfo> findAssociatedAircraftUtilizationInfos(Integer id, Pageable pageable);

    /*
     * Returns the associated redeliveryMatrixes for given Aircraft id.
     *
     * @param id value of id; value cannot be null
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of associated RedeliveryMatrix instances.
     *
     * @see Pageable
     * @see Page
     */
    Page<RedeliveryMatrix> findAssociatedRedeliveryMatrixes(Integer id, Pageable pageable);

    /*
     * Returns the associated taskSchedules for given Aircraft id.
     *
     * @param id value of id; value cannot be null
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of associated TaskSchedule instances.
     *
     * @see Pageable
     * @see Page
     */
    Page<TaskSchedule> findAssociatedTaskSchedules(Integer id, Pageable pageable);

}