/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Catalog;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.Result;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.jooq.impl.SchemaImpl;
import org.vaccineimpact.api.db.tables.ActivityType;
import org.vaccineimpact.api.db.tables.ApiAccessLog;
import org.vaccineimpact.api.db.tables.AppUser;
import org.vaccineimpact.api.db.tables.BurdenEstimate;
import org.vaccineimpact.api.db.tables.BurdenEstimateSet;
import org.vaccineimpact.api.db.tables.BurdenEstimateSetProblem;
import org.vaccineimpact.api.db.tables.BurdenEstimateSetStatus;
import org.vaccineimpact.api.db.tables.BurdenEstimateSetType;
import org.vaccineimpact.api.db.tables.BurdenEstimateStochastic;
import org.vaccineimpact.api.db.tables.BurdenOutcome;
import org.vaccineimpact.api.db.tables.Country;
import org.vaccineimpact.api.db.tables.CountryMetadata;
import org.vaccineimpact.api.db.tables.CountryVaccineMetadata;
import org.vaccineimpact.api.db.tables.Coverage;
import org.vaccineimpact.api.db.tables.CoverageSet;
import org.vaccineimpact.api.db.tables.DemographicDataset;
import org.vaccineimpact.api.db.tables.DemographicSource;
import org.vaccineimpact.api.db.tables.DemographicStatistic;
import org.vaccineimpact.api.db.tables.DemographicStatisticType;
import org.vaccineimpact.api.db.tables.DemographicStatisticTypeVariant;
import org.vaccineimpact.api.db.tables.DemographicValueUnit;
import org.vaccineimpact.api.db.tables.DemographicVariant;
import org.vaccineimpact.api.db.tables.DisabilityWeight;
import org.vaccineimpact.api.db.tables.Disease;
import org.vaccineimpact.api.db.tables.GaviFocalModel;
import org.vaccineimpact.api.db.tables.GaviSupportLevel;
import org.vaccineimpact.api.db.tables.Gender;
import org.vaccineimpact.api.db.tables.ImpactEstimate;
import org.vaccineimpact.api.db.tables.ImpactEstimateIngredient;
import org.vaccineimpact.api.db.tables.ImpactEstimateRecipe;
import org.vaccineimpact.api.db.tables.ImpactEstimateSet;
import org.vaccineimpact.api.db.tables.ImpactEstimateSetIngredient;
import org.vaccineimpact.api.db.tables.ImpactOutcome;
import org.vaccineimpact.api.db.tables.Model;
import org.vaccineimpact.api.db.tables.ModelRun;
import org.vaccineimpact.api.db.tables.ModelRunParameter;
import org.vaccineimpact.api.db.tables.ModelRunParameterSet;
import org.vaccineimpact.api.db.tables.ModelRunParameterValue;
import org.vaccineimpact.api.db.tables.ModelVersion;
import org.vaccineimpact.api.db.tables.ModellingGroup;
import org.vaccineimpact.api.db.tables.OnetimeToken;
import org.vaccineimpact.api.db.tables.Permission;
import org.vaccineimpact.api.db.tables.Responsibility;
import org.vaccineimpact.api.db.tables.ResponsibilitySet;
import org.vaccineimpact.api.db.tables.ResponsibilitySetStatus;
import org.vaccineimpact.api.db.tables.Role;
import org.vaccineimpact.api.db.tables.RolePermission;
import org.vaccineimpact.api.db.tables.Scenario;
import org.vaccineimpact.api.db.tables.ScenarioCoverageSet;
import org.vaccineimpact.api.db.tables.ScenarioDescription;
import org.vaccineimpact.api.db.tables.SelectBurdenData1;
import org.vaccineimpact.api.db.tables.SelectBurdenData2;
import org.vaccineimpact.api.db.tables.SelectBurdenData3;
import org.vaccineimpact.api.db.tables.SelectBurdenData4;
import org.vaccineimpact.api.db.tables.SelectBurdenData5;
import org.vaccineimpact.api.db.tables.SelectBurdenData6;
import org.vaccineimpact.api.db.tables.SelectBurdenData7;
import org.vaccineimpact.api.db.tables.SelectBurdenData8;
import org.vaccineimpact.api.db.tables.SelectBurdenDataCol;
import org.vaccineimpact.api.db.tables.SupportType;
import org.vaccineimpact.api.db.tables.Touchstone;
import org.vaccineimpact.api.db.tables.TouchstoneCountry;
import org.vaccineimpact.api.db.tables.TouchstoneDemographicDataset;
import org.vaccineimpact.api.db.tables.TouchstoneDemographicSource;
import org.vaccineimpact.api.db.tables.TouchstoneName;
import org.vaccineimpact.api.db.tables.TouchstoneStatus;
import org.vaccineimpact.api.db.tables.TouchstoneYears;
import org.vaccineimpact.api.db.tables.UploadInfo;
import org.vaccineimpact.api.db.tables.UserRole;
import org.vaccineimpact.api.db.tables.VCoverageInfo;
import org.vaccineimpact.api.db.tables.VResponsibilityInfo;
import org.vaccineimpact.api.db.tables.Vaccine;
import org.vaccineimpact.api.db.tables.VaccineRoutineAge;
import org.vaccineimpact.api.db.tables.records.SelectBurdenData1Record;
import org.vaccineimpact.api.db.tables.records.SelectBurdenData2Record;
import org.vaccineimpact.api.db.tables.records.SelectBurdenData3Record;
import org.vaccineimpact.api.db.tables.records.SelectBurdenData4Record;
import org.vaccineimpact.api.db.tables.records.SelectBurdenData5Record;
import org.vaccineimpact.api.db.tables.records.SelectBurdenData6Record;
import org.vaccineimpact.api.db.tables.records.SelectBurdenData7Record;
import org.vaccineimpact.api.db.tables.records.SelectBurdenData8Record;
import org.vaccineimpact.api.db.tables.records.SelectBurdenDataColRecord;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Public extends SchemaImpl {

    private static final long serialVersionUID = -636597794;

    /**
     * The reference instance of <code>public</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * This is mostly "none", "routine" or "campaign" but with a few extras
     */
    public final ActivityType ACTIVITY_TYPE = org.vaccineimpact.api.db.tables.ActivityType.ACTIVITY_TYPE;

    /**
     * The table <code>public.api_access_log</code>.
     */
    public final ApiAccessLog API_ACCESS_LOG = org.vaccineimpact.api.db.tables.ApiAccessLog.API_ACCESS_LOG;

    /**
     * The table <code>public.app_user</code>.
     */
    public final AppUser APP_USER = org.vaccineimpact.api.db.tables.AppUser.APP_USER;

    /**
     * The table <code>public.burden_estimate</code>.
     */
    public final BurdenEstimate BURDEN_ESTIMATE = org.vaccineimpact.api.db.tables.BurdenEstimate.BURDEN_ESTIMATE;

    /**
     * The table <code>public.burden_estimate_set</code>.
     */
    public final BurdenEstimateSet BURDEN_ESTIMATE_SET = org.vaccineimpact.api.db.tables.BurdenEstimateSet.BURDEN_ESTIMATE_SET;

    /**
     * The table <code>public.burden_estimate_set_problem</code>.
     */
    public final BurdenEstimateSetProblem BURDEN_ESTIMATE_SET_PROBLEM = org.vaccineimpact.api.db.tables.BurdenEstimateSetProblem.BURDEN_ESTIMATE_SET_PROBLEM;

    /**
     * The table <code>public.burden_estimate_set_status</code>.
     */
    public final BurdenEstimateSetStatus BURDEN_ESTIMATE_SET_STATUS = org.vaccineimpact.api.db.tables.BurdenEstimateSetStatus.BURDEN_ESTIMATE_SET_STATUS;

    /**
     * The table <code>public.burden_estimate_set_type</code>.
     */
    public final BurdenEstimateSetType BURDEN_ESTIMATE_SET_TYPE = org.vaccineimpact.api.db.tables.BurdenEstimateSetType.BURDEN_ESTIMATE_SET_TYPE;

    /**
     * The table <code>public.burden_estimate_stochastic</code>.
     */
    public final BurdenEstimateStochastic BURDEN_ESTIMATE_STOCHASTIC = org.vaccineimpact.api.db.tables.BurdenEstimateStochastic.BURDEN_ESTIMATE_STOCHASTIC;

    /**
     * The table <code>public.burden_outcome</code>.
     */
    public final BurdenOutcome BURDEN_OUTCOME = org.vaccineimpact.api.db.tables.BurdenOutcome.BURDEN_OUTCOME;

    /**
     * The table <code>public.country</code>.
     */
    public final Country COUNTRY = org.vaccineimpact.api.db.tables.Country.COUNTRY;

    /**
     * The table <code>public.country_metadata</code>.
     */
    public final CountryMetadata COUNTRY_METADATA = org.vaccineimpact.api.db.tables.CountryMetadata.COUNTRY_METADATA;

    /**
     * The table <code>public.country_vaccine_metadata</code>.
     */
    public final CountryVaccineMetadata COUNTRY_VACCINE_METADATA = org.vaccineimpact.api.db.tables.CountryVaccineMetadata.COUNTRY_VACCINE_METADATA;

    /**
     * The table <code>public.coverage</code>.
     */
    public final Coverage COVERAGE = org.vaccineimpact.api.db.tables.Coverage.COVERAGE;

    /**
     * The table <code>public.coverage_set</code>.
     */
    public final CoverageSet COVERAGE_SET = org.vaccineimpact.api.db.tables.CoverageSet.COVERAGE_SET;

    /**
     * The table <code>public.demographic_dataset</code>.
     */
    public final DemographicDataset DEMOGRAPHIC_DATASET = org.vaccineimpact.api.db.tables.DemographicDataset.DEMOGRAPHIC_DATASET;

    /**
     * The table <code>public.demographic_source</code>.
     */
    public final DemographicSource DEMOGRAPHIC_SOURCE = org.vaccineimpact.api.db.tables.DemographicSource.DEMOGRAPHIC_SOURCE;

    /**
     * The table <code>public.demographic_statistic</code>.
     */
    public final DemographicStatistic DEMOGRAPHIC_STATISTIC = org.vaccineimpact.api.db.tables.DemographicStatistic.DEMOGRAPHIC_STATISTIC;

    /**
     * The table <code>public.demographic_statistic_type</code>.
     */
    public final DemographicStatisticType DEMOGRAPHIC_STATISTIC_TYPE = org.vaccineimpact.api.db.tables.DemographicStatisticType.DEMOGRAPHIC_STATISTIC_TYPE;

    /**
     * The table <code>public.demographic_statistic_type_variant</code>.
     */
    public final DemographicStatisticTypeVariant DEMOGRAPHIC_STATISTIC_TYPE_VARIANT = org.vaccineimpact.api.db.tables.DemographicStatisticTypeVariant.DEMOGRAPHIC_STATISTIC_TYPE_VARIANT;

    /**
     * The table <code>public.demographic_value_unit</code>.
     */
    public final DemographicValueUnit DEMOGRAPHIC_VALUE_UNIT = org.vaccineimpact.api.db.tables.DemographicValueUnit.DEMOGRAPHIC_VALUE_UNIT;

    /**
     * The table <code>public.demographic_variant</code>.
     */
    public final DemographicVariant DEMOGRAPHIC_VARIANT = org.vaccineimpact.api.db.tables.DemographicVariant.DEMOGRAPHIC_VARIANT;

    /**
     * The table <code>public.disability_weight</code>.
     */
    public final DisabilityWeight DISABILITY_WEIGHT = org.vaccineimpact.api.db.tables.DisabilityWeight.DISABILITY_WEIGHT;

    /**
     * The table <code>public.disease</code>.
     */
    public final Disease DISEASE = org.vaccineimpact.api.db.tables.Disease.DISEASE;

    /**
     * The table <code>public.gavi_focal_model</code>.
     */
    public final GaviFocalModel GAVI_FOCAL_MODEL = org.vaccineimpact.api.db.tables.GaviFocalModel.GAVI_FOCAL_MODEL;

    /**
     * Enum table. Possible values: none (No vaccination), without (Vaccination without GAVI support), with (Vaccination with GAVI support)
     */
    public final GaviSupportLevel GAVI_SUPPORT_LEVEL = org.vaccineimpact.api.db.tables.GaviSupportLevel.GAVI_SUPPORT_LEVEL;

    /**
     * The table <code>public.gender</code>.
     */
    public final Gender GENDER = org.vaccineimpact.api.db.tables.Gender.GENDER;

    /**
     * The table <code>public.impact_estimate</code>.
     */
    public final ImpactEstimate IMPACT_ESTIMATE = org.vaccineimpact.api.db.tables.ImpactEstimate.IMPACT_ESTIMATE;

    /**
     * The table <code>public.impact_estimate_ingredient</code>.
     */
    public final ImpactEstimateIngredient IMPACT_ESTIMATE_INGREDIENT = org.vaccineimpact.api.db.tables.ImpactEstimateIngredient.IMPACT_ESTIMATE_INGREDIENT;

    /**
     * The table <code>public.impact_estimate_recipe</code>.
     */
    public final ImpactEstimateRecipe IMPACT_ESTIMATE_RECIPE = org.vaccineimpact.api.db.tables.ImpactEstimateRecipe.IMPACT_ESTIMATE_RECIPE;

    /**
     * The table <code>public.impact_estimate_set</code>.
     */
    public final ImpactEstimateSet IMPACT_ESTIMATE_SET = org.vaccineimpact.api.db.tables.ImpactEstimateSet.IMPACT_ESTIMATE_SET;

    /**
     * The table <code>public.impact_estimate_set_ingredient</code>.
     */
    public final ImpactEstimateSetIngredient IMPACT_ESTIMATE_SET_INGREDIENT = org.vaccineimpact.api.db.tables.ImpactEstimateSetIngredient.IMPACT_ESTIMATE_SET_INGREDIENT;

    /**
     * The table <code>public.impact_outcome</code>.
     */
    public final ImpactOutcome IMPACT_OUTCOME = org.vaccineimpact.api.db.tables.ImpactOutcome.IMPACT_OUTCOME;

    /**
     * With the self-referencing "current" field; we consider a model to be the current one if current is null.  See comment about recursion in modelling_group
     */
    public final Model MODEL = org.vaccineimpact.api.db.tables.Model.MODEL;

    /**
     * The table <code>public.model_run</code>.
     */
    public final ModelRun MODEL_RUN = org.vaccineimpact.api.db.tables.ModelRun.MODEL_RUN;

    /**
     * The table <code>public.model_run_parameter</code>.
     */
    public final ModelRunParameter MODEL_RUN_PARAMETER = org.vaccineimpact.api.db.tables.ModelRunParameter.MODEL_RUN_PARAMETER;

    /**
     * The table <code>public.model_run_parameter_set</code>.
     */
    public final ModelRunParameterSet MODEL_RUN_PARAMETER_SET = org.vaccineimpact.api.db.tables.ModelRunParameterSet.MODEL_RUN_PARAMETER_SET;

    /**
     * The table <code>public.model_run_parameter_value</code>.
     */
    public final ModelRunParameterValue MODEL_RUN_PARAMETER_VALUE = org.vaccineimpact.api.db.tables.ModelRunParameterValue.MODEL_RUN_PARAMETER_VALUE;

    /**
     * The table <code>public.model_version</code>.
     */
    public final ModelVersion MODEL_VERSION = org.vaccineimpact.api.db.tables.ModelVersion.MODEL_VERSION;

    /**
     * With the self-referencing "current" field; we consider a modelling group to be the current one if current is null.  This is not recursive; if we move a modelling group to a new id then every modelling group that has current pointing at the old id must be updated to point at the new one.  This means that no `current` points at an `id` that does not have `current` as `null`.
     */
    public final ModellingGroup MODELLING_GROUP = org.vaccineimpact.api.db.tables.ModellingGroup.MODELLING_GROUP;

    /**
     * The table <code>public.onetime_token</code>.
     */
    public final OnetimeToken ONETIME_TOKEN = org.vaccineimpact.api.db.tables.OnetimeToken.ONETIME_TOKEN;

    /**
     * The table <code>public.permission</code>.
     */
    public final Permission PERMISSION = org.vaccineimpact.api.db.tables.Permission.PERMISSION;

    /**
     * The table <code>public.responsibility</code>.
     */
    public final Responsibility RESPONSIBILITY = org.vaccineimpact.api.db.tables.Responsibility.RESPONSIBILITY;

    /**
     * The table <code>public.responsibility_set</code>.
     */
    public final ResponsibilitySet RESPONSIBILITY_SET = org.vaccineimpact.api.db.tables.ResponsibilitySet.RESPONSIBILITY_SET;

    /**
     * Possible values {incomplete, submitted, approved}
     */
    public final ResponsibilitySetStatus RESPONSIBILITY_SET_STATUS = org.vaccineimpact.api.db.tables.ResponsibilitySetStatus.RESPONSIBILITY_SET_STATUS;

    /**
     * The table <code>public.role</code>.
     */
    public final Role ROLE = org.vaccineimpact.api.db.tables.Role.ROLE;

    /**
     * The table <code>public.role_permission</code>.
     */
    public final RolePermission ROLE_PERMISSION = org.vaccineimpact.api.db.tables.RolePermission.ROLE_PERMISSION;

    /**
     * The table <code>public.scenario</code>.
     */
    public final Scenario SCENARIO = org.vaccineimpact.api.db.tables.Scenario.SCENARIO;

    /**
     * The table <code>public.scenario_coverage_set</code>.
     */
    public final ScenarioCoverageSet SCENARIO_COVERAGE_SET = org.vaccineimpact.api.db.tables.ScenarioCoverageSet.SCENARIO_COVERAGE_SET;

    /**
     * The table <code>public.scenario_description</code>.
     */
    public final ScenarioDescription SCENARIO_DESCRIPTION = org.vaccineimpact.api.db.tables.ScenarioDescription.SCENARIO_DESCRIPTION;

    /**
     * The table <code>public.select_burden_data1</code>.
     */
    public final SelectBurdenData1 SELECT_BURDEN_DATA1 = org.vaccineimpact.api.db.tables.SelectBurdenData1.SELECT_BURDEN_DATA1;

    /**
     * Call <code>public.select_burden_data1</code>.
     */
    public static Result<SelectBurdenData1Record> SELECT_BURDEN_DATA1(Configuration configuration, Integer set1, Integer outcome1) {
        return DSL.using(configuration).selectFrom(SelectBurdenData1.SELECT_BURDEN_DATA1.call(set1, outcome1)).fetch();
    }

    /**
     * Get <code>public.select_burden_data1</code> as a table.
     */
    public static SelectBurdenData1 SELECT_BURDEN_DATA1(Integer set1, Integer outcome1) {
        return SelectBurdenData1.SELECT_BURDEN_DATA1.call(set1, outcome1);
    }

    /**
     * Get <code>public.select_burden_data1</code> as a table.
     */
    public static SelectBurdenData1 SELECT_BURDEN_DATA1(Field<Integer> set1, Field<Integer> outcome1) {
        return SelectBurdenData1.SELECT_BURDEN_DATA1.call(set1, outcome1);
    }

    /**
     * The table <code>public.select_burden_data2</code>.
     */
    public final SelectBurdenData2 SELECT_BURDEN_DATA2 = org.vaccineimpact.api.db.tables.SelectBurdenData2.SELECT_BURDEN_DATA2;

    /**
     * Call <code>public.select_burden_data2</code>.
     */
    public static Result<SelectBurdenData2Record> SELECT_BURDEN_DATA2(Configuration configuration, Integer set1, Integer outcome1, Integer set2, Integer outcome2) {
        return DSL.using(configuration).selectFrom(SelectBurdenData2.SELECT_BURDEN_DATA2.call(set1, outcome1, set2, outcome2)).fetch();
    }

    /**
     * Get <code>public.select_burden_data2</code> as a table.
     */
    public static SelectBurdenData2 SELECT_BURDEN_DATA2(Integer set1, Integer outcome1, Integer set2, Integer outcome2) {
        return SelectBurdenData2.SELECT_BURDEN_DATA2.call(set1, outcome1, set2, outcome2);
    }

    /**
     * Get <code>public.select_burden_data2</code> as a table.
     */
    public static SelectBurdenData2 SELECT_BURDEN_DATA2(Field<Integer> set1, Field<Integer> outcome1, Field<Integer> set2, Field<Integer> outcome2) {
        return SelectBurdenData2.SELECT_BURDEN_DATA2.call(set1, outcome1, set2, outcome2);
    }

    /**
     * The table <code>public.select_burden_data3</code>.
     */
    public final SelectBurdenData3 SELECT_BURDEN_DATA3 = org.vaccineimpact.api.db.tables.SelectBurdenData3.SELECT_BURDEN_DATA3;

    /**
     * Call <code>public.select_burden_data3</code>.
     */
    public static Result<SelectBurdenData3Record> SELECT_BURDEN_DATA3(Configuration configuration, Integer set1, Integer outcome1, Integer set2, Integer outcome2, Integer set3, Integer outcome3) {
        return DSL.using(configuration).selectFrom(SelectBurdenData3.SELECT_BURDEN_DATA3.call(set1, outcome1, set2, outcome2, set3, outcome3)).fetch();
    }

    /**
     * Get <code>public.select_burden_data3</code> as a table.
     */
    public static SelectBurdenData3 SELECT_BURDEN_DATA3(Integer set1, Integer outcome1, Integer set2, Integer outcome2, Integer set3, Integer outcome3) {
        return SelectBurdenData3.SELECT_BURDEN_DATA3.call(set1, outcome1, set2, outcome2, set3, outcome3);
    }

    /**
     * Get <code>public.select_burden_data3</code> as a table.
     */
    public static SelectBurdenData3 SELECT_BURDEN_DATA3(Field<Integer> set1, Field<Integer> outcome1, Field<Integer> set2, Field<Integer> outcome2, Field<Integer> set3, Field<Integer> outcome3) {
        return SelectBurdenData3.SELECT_BURDEN_DATA3.call(set1, outcome1, set2, outcome2, set3, outcome3);
    }

    /**
     * The table <code>public.select_burden_data4</code>.
     */
    public final SelectBurdenData4 SELECT_BURDEN_DATA4 = org.vaccineimpact.api.db.tables.SelectBurdenData4.SELECT_BURDEN_DATA4;

    /**
     * Call <code>public.select_burden_data4</code>.
     */
    public static Result<SelectBurdenData4Record> SELECT_BURDEN_DATA4(Configuration configuration, Integer set1, Integer outcome1, Integer set2, Integer outcome2, Integer set3, Integer outcome3, Integer set4, Integer outcome4) {
        return DSL.using(configuration).selectFrom(SelectBurdenData4.SELECT_BURDEN_DATA4.call(set1, outcome1, set2, outcome2, set3, outcome3, set4, outcome4)).fetch();
    }

    /**
     * Get <code>public.select_burden_data4</code> as a table.
     */
    public static SelectBurdenData4 SELECT_BURDEN_DATA4(Integer set1, Integer outcome1, Integer set2, Integer outcome2, Integer set3, Integer outcome3, Integer set4, Integer outcome4) {
        return SelectBurdenData4.SELECT_BURDEN_DATA4.call(set1, outcome1, set2, outcome2, set3, outcome3, set4, outcome4);
    }

    /**
     * Get <code>public.select_burden_data4</code> as a table.
     */
    public static SelectBurdenData4 SELECT_BURDEN_DATA4(Field<Integer> set1, Field<Integer> outcome1, Field<Integer> set2, Field<Integer> outcome2, Field<Integer> set3, Field<Integer> outcome3, Field<Integer> set4, Field<Integer> outcome4) {
        return SelectBurdenData4.SELECT_BURDEN_DATA4.call(set1, outcome1, set2, outcome2, set3, outcome3, set4, outcome4);
    }

    /**
     * The table <code>public.select_burden_data5</code>.
     */
    public final SelectBurdenData5 SELECT_BURDEN_DATA5 = org.vaccineimpact.api.db.tables.SelectBurdenData5.SELECT_BURDEN_DATA5;

    /**
     * Call <code>public.select_burden_data5</code>.
     */
    public static Result<SelectBurdenData5Record> SELECT_BURDEN_DATA5(Configuration configuration, Integer set1, Integer outcome1, Integer set2, Integer outcome2, Integer set3, Integer outcome3, Integer set4, Integer outcome4, Integer set5, Integer outcome5) {
        return DSL.using(configuration).selectFrom(SelectBurdenData5.SELECT_BURDEN_DATA5.call(set1, outcome1, set2, outcome2, set3, outcome3, set4, outcome4, set5, outcome5)).fetch();
    }

    /**
     * Get <code>public.select_burden_data5</code> as a table.
     */
    public static SelectBurdenData5 SELECT_BURDEN_DATA5(Integer set1, Integer outcome1, Integer set2, Integer outcome2, Integer set3, Integer outcome3, Integer set4, Integer outcome4, Integer set5, Integer outcome5) {
        return SelectBurdenData5.SELECT_BURDEN_DATA5.call(set1, outcome1, set2, outcome2, set3, outcome3, set4, outcome4, set5, outcome5);
    }

    /**
     * Get <code>public.select_burden_data5</code> as a table.
     */
    public static SelectBurdenData5 SELECT_BURDEN_DATA5(Field<Integer> set1, Field<Integer> outcome1, Field<Integer> set2, Field<Integer> outcome2, Field<Integer> set3, Field<Integer> outcome3, Field<Integer> set4, Field<Integer> outcome4, Field<Integer> set5, Field<Integer> outcome5) {
        return SelectBurdenData5.SELECT_BURDEN_DATA5.call(set1, outcome1, set2, outcome2, set3, outcome3, set4, outcome4, set5, outcome5);
    }

    /**
     * The table <code>public.select_burden_data6</code>.
     */
    public final SelectBurdenData6 SELECT_BURDEN_DATA6 = org.vaccineimpact.api.db.tables.SelectBurdenData6.SELECT_BURDEN_DATA6;

    /**
     * Call <code>public.select_burden_data6</code>.
     */
    public static Result<SelectBurdenData6Record> SELECT_BURDEN_DATA6(Configuration configuration, Integer set1, Integer outcome1, Integer set2, Integer outcome2, Integer set3, Integer outcome3, Integer set4, Integer outcome4, Integer set5, Integer outcome5, Integer set6, Integer outcome6) {
        return DSL.using(configuration).selectFrom(SelectBurdenData6.SELECT_BURDEN_DATA6.call(set1, outcome1, set2, outcome2, set3, outcome3, set4, outcome4, set5, outcome5, set6, outcome6)).fetch();
    }

    /**
     * Get <code>public.select_burden_data6</code> as a table.
     */
    public static SelectBurdenData6 SELECT_BURDEN_DATA6(Integer set1, Integer outcome1, Integer set2, Integer outcome2, Integer set3, Integer outcome3, Integer set4, Integer outcome4, Integer set5, Integer outcome5, Integer set6, Integer outcome6) {
        return SelectBurdenData6.SELECT_BURDEN_DATA6.call(set1, outcome1, set2, outcome2, set3, outcome3, set4, outcome4, set5, outcome5, set6, outcome6);
    }

    /**
     * Get <code>public.select_burden_data6</code> as a table.
     */
    public static SelectBurdenData6 SELECT_BURDEN_DATA6(Field<Integer> set1, Field<Integer> outcome1, Field<Integer> set2, Field<Integer> outcome2, Field<Integer> set3, Field<Integer> outcome3, Field<Integer> set4, Field<Integer> outcome4, Field<Integer> set5, Field<Integer> outcome5, Field<Integer> set6, Field<Integer> outcome6) {
        return SelectBurdenData6.SELECT_BURDEN_DATA6.call(set1, outcome1, set2, outcome2, set3, outcome3, set4, outcome4, set5, outcome5, set6, outcome6);
    }

    /**
     * The table <code>public.select_burden_data7</code>.
     */
    public final SelectBurdenData7 SELECT_BURDEN_DATA7 = org.vaccineimpact.api.db.tables.SelectBurdenData7.SELECT_BURDEN_DATA7;

    /**
     * Call <code>public.select_burden_data7</code>.
     */
    public static Result<SelectBurdenData7Record> SELECT_BURDEN_DATA7(Configuration configuration, Integer set1, Integer outcome1, Integer set2, Integer outcome2, Integer set3, Integer outcome3, Integer set4, Integer outcome4, Integer set5, Integer outcome5, Integer set6, Integer outcome6, Integer set7, Integer outcome7) {
        return DSL.using(configuration).selectFrom(SelectBurdenData7.SELECT_BURDEN_DATA7.call(set1, outcome1, set2, outcome2, set3, outcome3, set4, outcome4, set5, outcome5, set6, outcome6, set7, outcome7)).fetch();
    }

    /**
     * Get <code>public.select_burden_data7</code> as a table.
     */
    public static SelectBurdenData7 SELECT_BURDEN_DATA7(Integer set1, Integer outcome1, Integer set2, Integer outcome2, Integer set3, Integer outcome3, Integer set4, Integer outcome4, Integer set5, Integer outcome5, Integer set6, Integer outcome6, Integer set7, Integer outcome7) {
        return SelectBurdenData7.SELECT_BURDEN_DATA7.call(set1, outcome1, set2, outcome2, set3, outcome3, set4, outcome4, set5, outcome5, set6, outcome6, set7, outcome7);
    }

    /**
     * Get <code>public.select_burden_data7</code> as a table.
     */
    public static SelectBurdenData7 SELECT_BURDEN_DATA7(Field<Integer> set1, Field<Integer> outcome1, Field<Integer> set2, Field<Integer> outcome2, Field<Integer> set3, Field<Integer> outcome3, Field<Integer> set4, Field<Integer> outcome4, Field<Integer> set5, Field<Integer> outcome5, Field<Integer> set6, Field<Integer> outcome6, Field<Integer> set7, Field<Integer> outcome7) {
        return SelectBurdenData7.SELECT_BURDEN_DATA7.call(set1, outcome1, set2, outcome2, set3, outcome3, set4, outcome4, set5, outcome5, set6, outcome6, set7, outcome7);
    }

    /**
     * The table <code>public.select_burden_data8</code>.
     */
    public final SelectBurdenData8 SELECT_BURDEN_DATA8 = org.vaccineimpact.api.db.tables.SelectBurdenData8.SELECT_BURDEN_DATA8;

    /**
     * Call <code>public.select_burden_data8</code>.
     */
    public static Result<SelectBurdenData8Record> SELECT_BURDEN_DATA8(Configuration configuration, Integer set1, Integer outcome1, Integer set2, Integer outcome2, Integer set3, Integer outcome3, Integer set4, Integer outcome4, Integer set5, Integer outcome5, Integer set6, Integer outcome6, Integer set7, Integer outcome7, Integer set8, Integer outcome8) {
        return DSL.using(configuration).selectFrom(SelectBurdenData8.SELECT_BURDEN_DATA8.call(set1, outcome1, set2, outcome2, set3, outcome3, set4, outcome4, set5, outcome5, set6, outcome6, set7, outcome7, set8, outcome8)).fetch();
    }

    /**
     * Get <code>public.select_burden_data8</code> as a table.
     */
    public static SelectBurdenData8 SELECT_BURDEN_DATA8(Integer set1, Integer outcome1, Integer set2, Integer outcome2, Integer set3, Integer outcome3, Integer set4, Integer outcome4, Integer set5, Integer outcome5, Integer set6, Integer outcome6, Integer set7, Integer outcome7, Integer set8, Integer outcome8) {
        return SelectBurdenData8.SELECT_BURDEN_DATA8.call(set1, outcome1, set2, outcome2, set3, outcome3, set4, outcome4, set5, outcome5, set6, outcome6, set7, outcome7, set8, outcome8);
    }

    /**
     * Get <code>public.select_burden_data8</code> as a table.
     */
    public static SelectBurdenData8 SELECT_BURDEN_DATA8(Field<Integer> set1, Field<Integer> outcome1, Field<Integer> set2, Field<Integer> outcome2, Field<Integer> set3, Field<Integer> outcome3, Field<Integer> set4, Field<Integer> outcome4, Field<Integer> set5, Field<Integer> outcome5, Field<Integer> set6, Field<Integer> outcome6, Field<Integer> set7, Field<Integer> outcome7, Field<Integer> set8, Field<Integer> outcome8) {
        return SelectBurdenData8.SELECT_BURDEN_DATA8.call(set1, outcome1, set2, outcome2, set3, outcome3, set4, outcome4, set5, outcome5, set6, outcome6, set7, outcome7, set8, outcome8);
    }

    /**
     * The table <code>public.select_burden_data_col</code>.
     */
    public final SelectBurdenDataCol SELECT_BURDEN_DATA_COL = org.vaccineimpact.api.db.tables.SelectBurdenDataCol.SELECT_BURDEN_DATA_COL;

    /**
     * Call <code>public.select_burden_data_col</code>.
     */
    public static Result<SelectBurdenDataColRecord> SELECT_BURDEN_DATA_COL(Configuration configuration, Integer setId, Integer outcomeId) {
        return DSL.using(configuration).selectFrom(SelectBurdenDataCol.SELECT_BURDEN_DATA_COL.call(setId, outcomeId)).fetch();
    }

    /**
     * Get <code>public.select_burden_data_col</code> as a table.
     */
    public static SelectBurdenDataCol SELECT_BURDEN_DATA_COL(Integer setId, Integer outcomeId) {
        return SelectBurdenDataCol.SELECT_BURDEN_DATA_COL.call(setId, outcomeId);
    }

    /**
     * Get <code>public.select_burden_data_col</code> as a table.
     */
    public static SelectBurdenDataCol SELECT_BURDEN_DATA_COL(Field<Integer> setId, Field<Integer> outcomeId) {
        return SelectBurdenDataCol.SELECT_BURDEN_DATA_COL.call(setId, outcomeId);
    }

    /**
     * The table <code>public.support_type</code>.
     */
    public final SupportType SUPPORT_TYPE = org.vaccineimpact.api.db.tables.SupportType.SUPPORT_TYPE;

    /**
     * This is the top-level categorization. It refers to an Operational Forecast from GAVI, a WUENIC July update, or some other data set against which impact estimates are going to be done 
     */
    public final Touchstone TOUCHSTONE = org.vaccineimpact.api.db.tables.Touchstone.TOUCHSTONE;

    /**
     * The table <code>public.touchstone_country</code>.
     */
    public final TouchstoneCountry TOUCHSTONE_COUNTRY = org.vaccineimpact.api.db.tables.TouchstoneCountry.TOUCHSTONE_COUNTRY;

    /**
     * The table <code>public.touchstone_demographic_dataset</code>.
     */
    public final TouchstoneDemographicDataset TOUCHSTONE_DEMOGRAPHIC_DATASET = org.vaccineimpact.api.db.tables.TouchstoneDemographicDataset.TOUCHSTONE_DEMOGRAPHIC_DATASET;

    /**
     * The table <code>public.touchstone_demographic_source</code>.
     */
    public final TouchstoneDemographicSource TOUCHSTONE_DEMOGRAPHIC_SOURCE = org.vaccineimpact.api.db.tables.TouchstoneDemographicSource.TOUCHSTONE_DEMOGRAPHIC_SOURCE;

    /**
     * The table <code>public.touchstone_name</code>.
     */
    public final TouchstoneName TOUCHSTONE_NAME = org.vaccineimpact.api.db.tables.TouchstoneName.TOUCHSTONE_NAME;

    /**
     * Valid values: {in-preparation, open, finished}
     */
    public final TouchstoneStatus TOUCHSTONE_STATUS = org.vaccineimpact.api.db.tables.TouchstoneStatus.TOUCHSTONE_STATUS;

    /**
     * The table <code>public.touchstone_years</code>.
     */
    public final TouchstoneYears TOUCHSTONE_YEARS = org.vaccineimpact.api.db.tables.TouchstoneYears.TOUCHSTONE_YEARS;

    /**
     * The table <code>public.upload_info</code>.
     */
    public final UploadInfo UPLOAD_INFO = org.vaccineimpact.api.db.tables.UploadInfo.UPLOAD_INFO;

    /**
     * The table <code>public.user_role</code>.
     */
    public final UserRole USER_ROLE = org.vaccineimpact.api.db.tables.UserRole.USER_ROLE;

    /**
     * The table <code>public.v_coverage_info</code>.
     */
    public final VCoverageInfo V_COVERAGE_INFO = org.vaccineimpact.api.db.tables.VCoverageInfo.V_COVERAGE_INFO;

    /**
     * The table <code>public.v_responsibility_info</code>.
     */
    public final VResponsibilityInfo V_RESPONSIBILITY_INFO = org.vaccineimpact.api.db.tables.VResponsibilityInfo.V_RESPONSIBILITY_INFO;

    /**
     * The table <code>public.vaccine</code>.
     */
    public final Vaccine VACCINE = org.vaccineimpact.api.db.tables.Vaccine.VACCINE;

    /**
     * The table <code>public.vaccine_routine_age</code>.
     */
    public final VaccineRoutineAge VACCINE_ROUTINE_AGE = org.vaccineimpact.api.db.tables.VaccineRoutineAge.VACCINE_ROUTINE_AGE;

    /**
     * No further instances allowed
     */
    private Public() {
        super("public", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Sequence<?>> getSequences() {
        List result = new ArrayList();
        result.addAll(getSequences0());
        return result;
    }

    private final List<Sequence<?>> getSequences0() {
        return Arrays.<Sequence<?>>asList(
            Sequences.API_ACCESS_LOG_ID_SEQ,
            Sequences.BURDEN_ESTIMATE_ID_SEQ,
            Sequences.BURDEN_ESTIMATE_SET_ID_SEQ,
            Sequences.BURDEN_ESTIMATE_SET_PROBLEM_ID_SEQ,
            Sequences.BURDEN_ESTIMATE_STOCHASTIC_ID_SEQ,
            Sequences.BURDEN_OUTCOME_ID_SEQ,
            Sequences.COUNTRY_METADATA_ID_SEQ,
            Sequences.COUNTRY_VACCINE_METADATA_ID_SEQ,
            Sequences.COVERAGE_ID_SEQ,
            Sequences.COVERAGE_SET_ID_SEQ,
            Sequences.DEMOGRAPHIC_DATASET_ID_SEQ,
            Sequences.DEMOGRAPHIC_SOURCE_ID_SEQ,
            Sequences.DEMOGRAPHIC_STATISTIC_ID_SEQ,
            Sequences.DEMOGRAPHIC_STATISTIC_TYPE_ID_SEQ,
            Sequences.DEMOGRAPHIC_VALUE_UNIT_ID_SEQ,
            Sequences.DEMOGRAPHIC_VARIANT_ID_SEQ,
            Sequences.DISABILITY_WEIGHT_ID_SEQ,
            Sequences.GAVI_FOCAL_MODEL_ID_SEQ,
            Sequences.GENDER_ID_SEQ,
            Sequences.IMPACT_ESTIMATE_ID_SEQ,
            Sequences.IMPACT_ESTIMATE_INGREDIENT_ID_SEQ,
            Sequences.IMPACT_ESTIMATE_RECIPE_ID_SEQ,
            Sequences.IMPACT_ESTIMATE_SET_ID_SEQ,
            Sequences.IMPACT_ESTIMATE_SET_INGREDIENT_ID_SEQ,
            Sequences.MODEL_RUN_INTERNAL_ID_SEQ,
            Sequences.MODEL_RUN_PARAMETER_ID_SEQ,
            Sequences.MODEL_RUN_PARAMETER_SET_ID_SEQ,
            Sequences.MODEL_RUN_PARAMETER_VALUE_ID_SEQ,
            Sequences.MODEL_VERSION_ID_SEQ,
            Sequences.RESPONSIBILITY_ID_SEQ,
            Sequences.RESPONSIBILITY_SET_ID_SEQ,
            Sequences.ROLE_ID_SEQ,
            Sequences.SCENARIO_COVERAGE_SET_ID_SEQ,
            Sequences.SCENARIO_ID_SEQ,
            Sequences.TOUCHSTONE_COUNTRY_ID_SEQ,
            Sequences.TOUCHSTONE_DEMOGRAPHIC_DATASET_ID_SEQ,
            Sequences.TOUCHSTONE_DEMOGRAPHIC_SOURCE_ID_SEQ,
            Sequences.TOUCHSTONE_YEARS_ID_SEQ,
            Sequences.UPLOAD_INFO_ID_SEQ,
            Sequences.VACCINE_ROUTINE_AGE_ID_SEQ);
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            ActivityType.ACTIVITY_TYPE,
            ApiAccessLog.API_ACCESS_LOG,
            AppUser.APP_USER,
            BurdenEstimate.BURDEN_ESTIMATE,
            BurdenEstimateSet.BURDEN_ESTIMATE_SET,
            BurdenEstimateSetProblem.BURDEN_ESTIMATE_SET_PROBLEM,
            BurdenEstimateSetStatus.BURDEN_ESTIMATE_SET_STATUS,
            BurdenEstimateSetType.BURDEN_ESTIMATE_SET_TYPE,
            BurdenEstimateStochastic.BURDEN_ESTIMATE_STOCHASTIC,
            BurdenOutcome.BURDEN_OUTCOME,
            Country.COUNTRY,
            CountryMetadata.COUNTRY_METADATA,
            CountryVaccineMetadata.COUNTRY_VACCINE_METADATA,
            Coverage.COVERAGE,
            CoverageSet.COVERAGE_SET,
            DemographicDataset.DEMOGRAPHIC_DATASET,
            DemographicSource.DEMOGRAPHIC_SOURCE,
            DemographicStatistic.DEMOGRAPHIC_STATISTIC,
            DemographicStatisticType.DEMOGRAPHIC_STATISTIC_TYPE,
            DemographicStatisticTypeVariant.DEMOGRAPHIC_STATISTIC_TYPE_VARIANT,
            DemographicValueUnit.DEMOGRAPHIC_VALUE_UNIT,
            DemographicVariant.DEMOGRAPHIC_VARIANT,
            DisabilityWeight.DISABILITY_WEIGHT,
            Disease.DISEASE,
            GaviFocalModel.GAVI_FOCAL_MODEL,
            GaviSupportLevel.GAVI_SUPPORT_LEVEL,
            Gender.GENDER,
            ImpactEstimate.IMPACT_ESTIMATE,
            ImpactEstimateIngredient.IMPACT_ESTIMATE_INGREDIENT,
            ImpactEstimateRecipe.IMPACT_ESTIMATE_RECIPE,
            ImpactEstimateSet.IMPACT_ESTIMATE_SET,
            ImpactEstimateSetIngredient.IMPACT_ESTIMATE_SET_INGREDIENT,
            ImpactOutcome.IMPACT_OUTCOME,
            Model.MODEL,
            ModelRun.MODEL_RUN,
            ModelRunParameter.MODEL_RUN_PARAMETER,
            ModelRunParameterSet.MODEL_RUN_PARAMETER_SET,
            ModelRunParameterValue.MODEL_RUN_PARAMETER_VALUE,
            ModelVersion.MODEL_VERSION,
            ModellingGroup.MODELLING_GROUP,
            OnetimeToken.ONETIME_TOKEN,
            Permission.PERMISSION,
            Responsibility.RESPONSIBILITY,
            ResponsibilitySet.RESPONSIBILITY_SET,
            ResponsibilitySetStatus.RESPONSIBILITY_SET_STATUS,
            Role.ROLE,
            RolePermission.ROLE_PERMISSION,
            Scenario.SCENARIO,
            ScenarioCoverageSet.SCENARIO_COVERAGE_SET,
            ScenarioDescription.SCENARIO_DESCRIPTION,
            SelectBurdenData1.SELECT_BURDEN_DATA1,
            SelectBurdenData2.SELECT_BURDEN_DATA2,
            SelectBurdenData3.SELECT_BURDEN_DATA3,
            SelectBurdenData4.SELECT_BURDEN_DATA4,
            SelectBurdenData5.SELECT_BURDEN_DATA5,
            SelectBurdenData6.SELECT_BURDEN_DATA6,
            SelectBurdenData7.SELECT_BURDEN_DATA7,
            SelectBurdenData8.SELECT_BURDEN_DATA8,
            SelectBurdenDataCol.SELECT_BURDEN_DATA_COL,
            SupportType.SUPPORT_TYPE,
            Touchstone.TOUCHSTONE,
            TouchstoneCountry.TOUCHSTONE_COUNTRY,
            TouchstoneDemographicDataset.TOUCHSTONE_DEMOGRAPHIC_DATASET,
            TouchstoneDemographicSource.TOUCHSTONE_DEMOGRAPHIC_SOURCE,
            TouchstoneName.TOUCHSTONE_NAME,
            TouchstoneStatus.TOUCHSTONE_STATUS,
            TouchstoneYears.TOUCHSTONE_YEARS,
            UploadInfo.UPLOAD_INFO,
            UserRole.USER_ROLE,
            VCoverageInfo.V_COVERAGE_INFO,
            VResponsibilityInfo.V_RESPONSIBILITY_INFO,
            Vaccine.VACCINE,
            VaccineRoutineAge.VACCINE_ROUTINE_AGE);
    }
}
