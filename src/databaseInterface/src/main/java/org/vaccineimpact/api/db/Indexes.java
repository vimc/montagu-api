/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db;


import javax.annotation.Generated;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;
import org.vaccineimpact.api.db.tables.ActivityType;
import org.vaccineimpact.api.db.tables.ApiAccessLog;
import org.vaccineimpact.api.db.tables.AppUser;
import org.vaccineimpact.api.db.tables.BurdenEstimate;
import org.vaccineimpact.api.db.tables.BurdenEstimateCountryExpectation;
import org.vaccineimpact.api.db.tables.BurdenEstimateExpectation;
import org.vaccineimpact.api.db.tables.BurdenEstimateOutcomeExpectation;
import org.vaccineimpact.api.db.tables.BurdenEstimateSet;
import org.vaccineimpact.api.db.tables.BurdenEstimateSetProblem;
import org.vaccineimpact.api.db.tables.BurdenEstimateSetStatus;
import org.vaccineimpact.api.db.tables.BurdenEstimateSetType;
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
import org.vaccineimpact.api.db.tables.GaviEligibility;
import org.vaccineimpact.api.db.tables.GaviEligibilityStatus;
import org.vaccineimpact.api.db.tables.GaviFocalModel;
import org.vaccineimpact.api.db.tables.GaviSupportLevel;
import org.vaccineimpact.api.db.tables.Gender;
import org.vaccineimpact.api.db.tables.ImpactEstimate;
import org.vaccineimpact.api.db.tables.ImpactEstimateIngredient;
import org.vaccineimpact.api.db.tables.ImpactEstimateRecipe;
import org.vaccineimpact.api.db.tables.ImpactEstimateSet;
import org.vaccineimpact.api.db.tables.ImpactEstimateSetIngredient;
import org.vaccineimpact.api.db.tables.ImpactOutcome;
import org.vaccineimpact.api.db.tables.LegalAgreement;
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
import org.vaccineimpact.api.db.tables.SupportType;
import org.vaccineimpact.api.db.tables.Touchstone;
import org.vaccineimpact.api.db.tables.TouchstoneCountry;
import org.vaccineimpact.api.db.tables.TouchstoneDemographicDataset;
import org.vaccineimpact.api.db.tables.TouchstoneDemographicSource;
import org.vaccineimpact.api.db.tables.TouchstoneName;
import org.vaccineimpact.api.db.tables.TouchstoneStatus;
import org.vaccineimpact.api.db.tables.TouchstoneYears;
import org.vaccineimpact.api.db.tables.UploadInfo;
import org.vaccineimpact.api.db.tables.UserGroup;
import org.vaccineimpact.api.db.tables.UserGroupMembership;
import org.vaccineimpact.api.db.tables.UserGroupRole;
import org.vaccineimpact.api.db.tables.Vaccine;
import org.vaccineimpact.api.db.tables.VaccineRoutineAge;


/**
 * A class modelling indexes of tables of the <code>public</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index ACTIVITY_TYPE_PKEY = Indexes0.ACTIVITY_TYPE_PKEY;
    public static final Index API_ACCESS_LOG_IP_ADDRESS_IDX = Indexes0.API_ACCESS_LOG_IP_ADDRESS_IDX;
    public static final Index API_ACCESS_LOG_PKEY = Indexes0.API_ACCESS_LOG_PKEY;
    public static final Index API_ACCESS_LOG_RESULT_IDX = Indexes0.API_ACCESS_LOG_RESULT_IDX;
    public static final Index API_ACCESS_LOG_WHAT_IDX = Indexes0.API_ACCESS_LOG_WHAT_IDX;
    public static final Index API_ACCESS_LOG_WHO_IDX = Indexes0.API_ACCESS_LOG_WHO_IDX;
    public static final Index APP_USER_PKEY = Indexes0.APP_USER_PKEY;
    public static final Index BURDEN_ESTIMATE_BURDEN_ESTIMATE_SET_IDX = Indexes0.BURDEN_ESTIMATE_BURDEN_ESTIMATE_SET_IDX;
    public static final Index BURDEN_ESTIMATE_UNIQUE = Indexes0.BURDEN_ESTIMATE_UNIQUE;
    public static final Index BURDEN_ESTIMATE_COUNTRY_EXPECTATION_PKEY = Indexes0.BURDEN_ESTIMATE_COUNTRY_EXPECTATION_PKEY;
    public static final Index BURDEN_ESTIMATE_EXPECTATION_PKEY = Indexes0.BURDEN_ESTIMATE_EXPECTATION_PKEY;
    public static final Index BURDEN_ESTIMATE_OUTCOME_EXPECTATION_PKEY = Indexes0.BURDEN_ESTIMATE_OUTCOME_EXPECTATION_PKEY;
    public static final Index BURDEN_ESTIMATE_SET_PKEY = Indexes0.BURDEN_ESTIMATE_SET_PKEY;
    public static final Index BURDEN_ESTIMATE_SET_PROBLEM_PKEY = Indexes0.BURDEN_ESTIMATE_SET_PROBLEM_PKEY;
    public static final Index BURDEN_ESTIMATE_SET_STATUS_PKEY = Indexes0.BURDEN_ESTIMATE_SET_STATUS_PKEY;
    public static final Index BURDEN_ESTIMATE_SET_TYPE_PKEY = Indexes0.BURDEN_ESTIMATE_SET_TYPE_PKEY;
    public static final Index BURDEN_OUTCOME_CODE_KEY = Indexes0.BURDEN_OUTCOME_CODE_KEY;
    public static final Index BURDEN_OUTCOME_PKEY = Indexes0.BURDEN_OUTCOME_PKEY;
    public static final Index COUNTRY_NID_UNIQUE = Indexes0.COUNTRY_NID_UNIQUE;
    public static final Index COUNTRY_PKEY = Indexes0.COUNTRY_PKEY;
    public static final Index COUNTRY_METADATA_PKEY = Indexes0.COUNTRY_METADATA_PKEY;
    public static final Index COUNTRY_VACCINE_METADATA_PKEY = Indexes0.COUNTRY_VACCINE_METADATA_PKEY;
    public static final Index COVERAGE_PKEY = Indexes0.COVERAGE_PKEY;
    public static final Index COVERAGE_SET_PKEY = Indexes0.COVERAGE_SET_PKEY;
    public static final Index DEMOGRAPHIC_DATASET_PKEY = Indexes0.DEMOGRAPHIC_DATASET_PKEY;
    public static final Index DEMOGRAPHIC_SOURCE_PKEY = Indexes0.DEMOGRAPHIC_SOURCE_PKEY;
    public static final Index DEMOGRAPHIC_STATISTIC_COUNTRY_IDX = Indexes0.DEMOGRAPHIC_STATISTIC_COUNTRY_IDX;
    public static final Index DEMOGRAPHIC_STATISTIC_DEMOGRAPHIC_SOURCE_IDX = Indexes0.DEMOGRAPHIC_STATISTIC_DEMOGRAPHIC_SOURCE_IDX;
    public static final Index DEMOGRAPHIC_STATISTIC_DEMOGRAPHIC_STATISTIC_TYPE_IDX = Indexes0.DEMOGRAPHIC_STATISTIC_DEMOGRAPHIC_STATISTIC_TYPE_IDX;
    public static final Index DEMOGRAPHIC_STATISTIC_DEMOGRAPHIC_VARIANT_IDX = Indexes0.DEMOGRAPHIC_STATISTIC_DEMOGRAPHIC_VARIANT_IDX;
    public static final Index DEMOGRAPHIC_STATISTIC_GENDER_IDX = Indexes0.DEMOGRAPHIC_STATISTIC_GENDER_IDX;
    public static final Index DEMOGRAPHIC_STATISTIC_PKEY = Indexes0.DEMOGRAPHIC_STATISTIC_PKEY;
    public static final Index DEMOGRAPHIC_STATISTIC_TYPE_PKEY = Indexes0.DEMOGRAPHIC_STATISTIC_TYPE_PKEY;
    public static final Index DEMOGRAPHIC_STATISTIC_TYPE_VARIANT_PKEY = Indexes0.DEMOGRAPHIC_STATISTIC_TYPE_VARIANT_PKEY;
    public static final Index DEMOGRAPHIC_VALUE_UNIT_PKEY = Indexes0.DEMOGRAPHIC_VALUE_UNIT_PKEY;
    public static final Index DEMOGRAPHIC_VARIANT_PKEY = Indexes0.DEMOGRAPHIC_VARIANT_PKEY;
    public static final Index DISABILITY_WEIGHT_PKEY = Indexes0.DISABILITY_WEIGHT_PKEY;
    public static final Index DISEASE_PKEY = Indexes0.DISEASE_PKEY;
    public static final Index GAVI_ELIGIBILITY_PKEY = Indexes0.GAVI_ELIGIBILITY_PKEY;
    public static final Index GAVI_ELIGIBILITY_TOUCHSTONE_COUNTRY_YEAR_KEY = Indexes0.GAVI_ELIGIBILITY_TOUCHSTONE_COUNTRY_YEAR_KEY;
    public static final Index GAVI_ELIGIBILITY_STATUS_PKEY = Indexes0.GAVI_ELIGIBILITY_STATUS_PKEY;
    public static final Index GAVI_FOCAL_MODEL_PKEY = Indexes0.GAVI_FOCAL_MODEL_PKEY;
    public static final Index GAVI_SUPPORT_LEVEL_PKEY = Indexes0.GAVI_SUPPORT_LEVEL_PKEY;
    public static final Index GENDER_PKEY = Indexes0.GENDER_PKEY;
    public static final Index IMPACT_ESTIMATE_PKEY = Indexes0.IMPACT_ESTIMATE_PKEY;
    public static final Index IMPACT_ESTIMATE_INGREDIENT_PKEY = Indexes0.IMPACT_ESTIMATE_INGREDIENT_PKEY;
    public static final Index IMPACT_ESTIMATE_INGREDIENT_RESPONSIBILITY_IMPACT_ESTIMATE_R_KEY = Indexes0.IMPACT_ESTIMATE_INGREDIENT_RESPONSIBILITY_IMPACT_ESTIMATE_R_KEY;
    public static final Index IMPACT_ESTIMATE_RECIPE_PKEY = Indexes0.IMPACT_ESTIMATE_RECIPE_PKEY;
    public static final Index IMPACT_ESTIMATE_SET_PKEY = Indexes0.IMPACT_ESTIMATE_SET_PKEY;
    public static final Index IMPACT_ESTIMATE_SET_INGREDIENT_PKEY = Indexes0.IMPACT_ESTIMATE_SET_INGREDIENT_PKEY;
    public static final Index IMPACT_OUTCOME_PKEY = Indexes0.IMPACT_OUTCOME_PKEY;
    public static final Index LEGAL_AGREEMENT_PKEY = Indexes0.LEGAL_AGREEMENT_PKEY;
    public static final Index MODEL_PKEY = Indexes0.MODEL_PKEY;
    public static final Index MODELLING_GROUP_DISEASE_UNIQUE_WHEN_CURRENT = Indexes0.MODELLING_GROUP_DISEASE_UNIQUE_WHEN_CURRENT;
    public static final Index MODEL_RUN_PKEY = Indexes0.MODEL_RUN_PKEY;
    public static final Index MODEL_RUN_PARAMETER_KEY_MODEL_RUN_PARAMETER_SET_KEY = Indexes0.MODEL_RUN_PARAMETER_KEY_MODEL_RUN_PARAMETER_SET_KEY;
    public static final Index MODEL_RUN_PARAMETER_PKEY = Indexes0.MODEL_RUN_PARAMETER_PKEY;
    public static final Index MODEL_RUN_PARAMETER_SET_PKEY = Indexes0.MODEL_RUN_PARAMETER_SET_PKEY;
    public static final Index MODEL_RUN_PARAMETER_VALUE_MODEL_RUN_PARAMETER_MODEL_RUN_KEY = Indexes0.MODEL_RUN_PARAMETER_VALUE_MODEL_RUN_PARAMETER_MODEL_RUN_KEY;
    public static final Index MODEL_RUN_PARAMETER_VALUE_PKEY = Indexes0.MODEL_RUN_PARAMETER_VALUE_PKEY;
    public static final Index MODEL_VERSION_MODEL_VERSION_KEY = Indexes0.MODEL_VERSION_MODEL_VERSION_KEY;
    public static final Index MODEL_VERSION_PKEY = Indexes0.MODEL_VERSION_PKEY;
    public static final Index MODELLING_GROUP_PKEY = Indexes0.MODELLING_GROUP_PKEY;
    public static final Index ONETIME_TOKEN_PKEY = Indexes0.ONETIME_TOKEN_PKEY;
    public static final Index PERMISSION_PKEY = Indexes0.PERMISSION_PKEY;
    public static final Index RESPONSIBILITY_PKEY = Indexes0.RESPONSIBILITY_PKEY;
    public static final Index RESPONSIBILITY_RESPONSIBILITY_SET_SCENARIO_KEY = Indexes0.RESPONSIBILITY_RESPONSIBILITY_SET_SCENARIO_KEY;
    public static final Index RESPONSIBILITY_SET_MODELLING_GROUP_TOUCHSTONE_KEY = Indexes0.RESPONSIBILITY_SET_MODELLING_GROUP_TOUCHSTONE_KEY;
    public static final Index RESPONSIBILITY_SET_PKEY = Indexes0.RESPONSIBILITY_SET_PKEY;
    public static final Index RESPONSIBILITY_SET_STATUS_PKEY = Indexes0.RESPONSIBILITY_SET_STATUS_PKEY;
    public static final Index ROLE_PKEY = Indexes0.ROLE_PKEY;
    public static final Index ROLE_PERMISSION_PKEY = Indexes0.ROLE_PERMISSION_PKEY;
    public static final Index SCENARIO_PKEY = Indexes0.SCENARIO_PKEY;
    public static final Index SCENARIO_TOUCHSTONE_SCENARIO_DESCRIPTION_KEY = Indexes0.SCENARIO_TOUCHSTONE_SCENARIO_DESCRIPTION_KEY;
    public static final Index SCENARIO_COVERAGE_SET_PKEY = Indexes0.SCENARIO_COVERAGE_SET_PKEY;
    public static final Index SCENARIO_DESCRIPTION_PKEY = Indexes0.SCENARIO_DESCRIPTION_PKEY;
    public static final Index SUPPORT_TYPE_PKEY = Indexes0.SUPPORT_TYPE_PKEY;
    public static final Index TOUCHSTONE_PKEY = Indexes0.TOUCHSTONE_PKEY;
    public static final Index TOUCHSTONE_TOUCHSTONE_NAME_VERSION_KEY = Indexes0.TOUCHSTONE_TOUCHSTONE_NAME_VERSION_KEY;
    public static final Index TOUCHSTONE_COUNTRY_PKEY = Indexes0.TOUCHSTONE_COUNTRY_PKEY;
    public static final Index TOUCHSTONE_DEMOGRAPHIC_DATASET_PKEY = Indexes0.TOUCHSTONE_DEMOGRAPHIC_DATASET_PKEY;
    public static final Index TOUCHSTONE_DEMOGRAPHIC_SOURCE_PKEY = Indexes0.TOUCHSTONE_DEMOGRAPHIC_SOURCE_PKEY;
    public static final Index TOUCHSTONE_DEMOGRAPHIC_SOURCE_TOUCHSTONE_IDX = Indexes0.TOUCHSTONE_DEMOGRAPHIC_SOURCE_TOUCHSTONE_IDX;
    public static final Index TOUCHSTONE_NAME_PKEY = Indexes0.TOUCHSTONE_NAME_PKEY;
    public static final Index TOUCHSTONE_STATUS_PKEY = Indexes0.TOUCHSTONE_STATUS_PKEY;
    public static final Index TOUCHSTONE_YEARS_PKEY = Indexes0.TOUCHSTONE_YEARS_PKEY;
    public static final Index UPLOAD_INFO_PKEY = Indexes0.UPLOAD_INFO_PKEY;
    public static final Index USER_GROUP_PKEY = Indexes0.USER_GROUP_PKEY;
    public static final Index USER_GROUP_MEMBERSHIP_PKEY = Indexes0.USER_GROUP_MEMBERSHIP_PKEY;
    public static final Index USER_GROUP_ROLE_PKEY = Indexes0.USER_GROUP_ROLE_PKEY;
    public static final Index VACCINE_PKEY = Indexes0.VACCINE_PKEY;
    public static final Index VACCINE_ROUTINE_AGE_PKEY = Indexes0.VACCINE_ROUTINE_AGE_PKEY;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index ACTIVITY_TYPE_PKEY = Internal.createIndex("activity_type_pkey", ActivityType.ACTIVITY_TYPE, new OrderField[] { ActivityType.ACTIVITY_TYPE.ID }, true);
        public static Index API_ACCESS_LOG_IP_ADDRESS_IDX = Internal.createIndex("api_access_log_ip_address_idx", ApiAccessLog.API_ACCESS_LOG, new OrderField[] { ApiAccessLog.API_ACCESS_LOG.IP_ADDRESS }, false);
        public static Index API_ACCESS_LOG_PKEY = Internal.createIndex("api_access_log_pkey", ApiAccessLog.API_ACCESS_LOG, new OrderField[] { ApiAccessLog.API_ACCESS_LOG.ID }, true);
        public static Index API_ACCESS_LOG_RESULT_IDX = Internal.createIndex("api_access_log_result_idx", ApiAccessLog.API_ACCESS_LOG, new OrderField[] { ApiAccessLog.API_ACCESS_LOG.RESULT }, false);
        public static Index API_ACCESS_LOG_WHAT_IDX = Internal.createIndex("api_access_log_what_idx", ApiAccessLog.API_ACCESS_LOG, new OrderField[] { ApiAccessLog.API_ACCESS_LOG.WHAT }, false);
        public static Index API_ACCESS_LOG_WHO_IDX = Internal.createIndex("api_access_log_who_idx", ApiAccessLog.API_ACCESS_LOG, new OrderField[] { ApiAccessLog.API_ACCESS_LOG.WHO }, false);
        public static Index APP_USER_PKEY = Internal.createIndex("app_user_pkey", AppUser.APP_USER, new OrderField[] { AppUser.APP_USER.USERNAME }, true);
        public static Index BURDEN_ESTIMATE_BURDEN_ESTIMATE_SET_IDX = Internal.createIndex("burden_estimate_burden_estimate_set_idx", BurdenEstimate.BURDEN_ESTIMATE, new OrderField[] { BurdenEstimate.BURDEN_ESTIMATE.BURDEN_ESTIMATE_SET }, false);
        public static Index BURDEN_ESTIMATE_UNIQUE = Internal.createIndex("burden_estimate_unique", BurdenEstimate.BURDEN_ESTIMATE, new OrderField[] { BurdenEstimate.BURDEN_ESTIMATE.BURDEN_ESTIMATE_SET, BurdenEstimate.BURDEN_ESTIMATE.COUNTRY, BurdenEstimate.BURDEN_ESTIMATE.YEAR, BurdenEstimate.BURDEN_ESTIMATE.AGE, BurdenEstimate.BURDEN_ESTIMATE.BURDEN_OUTCOME }, true);
        public static Index BURDEN_ESTIMATE_COUNTRY_EXPECTATION_PKEY = Internal.createIndex("burden_estimate_country_expectation_pkey", BurdenEstimateCountryExpectation.BURDEN_ESTIMATE_COUNTRY_EXPECTATION, new OrderField[] { BurdenEstimateCountryExpectation.BURDEN_ESTIMATE_COUNTRY_EXPECTATION.BURDEN_ESTIMATE_EXPECTATION, BurdenEstimateCountryExpectation.BURDEN_ESTIMATE_COUNTRY_EXPECTATION.COUNTRY }, true);
        public static Index BURDEN_ESTIMATE_EXPECTATION_PKEY = Internal.createIndex("burden_estimate_expectation_pkey", BurdenEstimateExpectation.BURDEN_ESTIMATE_EXPECTATION, new OrderField[] { BurdenEstimateExpectation.BURDEN_ESTIMATE_EXPECTATION.ID }, true);
        public static Index BURDEN_ESTIMATE_OUTCOME_EXPECTATION_PKEY = Internal.createIndex("burden_estimate_outcome_expectation_pkey", BurdenEstimateOutcomeExpectation.BURDEN_ESTIMATE_OUTCOME_EXPECTATION, new OrderField[] { BurdenEstimateOutcomeExpectation.BURDEN_ESTIMATE_OUTCOME_EXPECTATION.BURDEN_ESTIMATE_EXPECTATION, BurdenEstimateOutcomeExpectation.BURDEN_ESTIMATE_OUTCOME_EXPECTATION.OUTCOME }, true);
        public static Index BURDEN_ESTIMATE_SET_PKEY = Internal.createIndex("burden_estimate_set_pkey", BurdenEstimateSet.BURDEN_ESTIMATE_SET, new OrderField[] { BurdenEstimateSet.BURDEN_ESTIMATE_SET.ID }, true);
        public static Index BURDEN_ESTIMATE_SET_PROBLEM_PKEY = Internal.createIndex("burden_estimate_set_problem_pkey", BurdenEstimateSetProblem.BURDEN_ESTIMATE_SET_PROBLEM, new OrderField[] { BurdenEstimateSetProblem.BURDEN_ESTIMATE_SET_PROBLEM.ID }, true);
        public static Index BURDEN_ESTIMATE_SET_STATUS_PKEY = Internal.createIndex("burden_estimate_set_status_pkey", BurdenEstimateSetStatus.BURDEN_ESTIMATE_SET_STATUS, new OrderField[] { BurdenEstimateSetStatus.BURDEN_ESTIMATE_SET_STATUS.CODE }, true);
        public static Index BURDEN_ESTIMATE_SET_TYPE_PKEY = Internal.createIndex("burden_estimate_set_type_pkey", BurdenEstimateSetType.BURDEN_ESTIMATE_SET_TYPE, new OrderField[] { BurdenEstimateSetType.BURDEN_ESTIMATE_SET_TYPE.CODE }, true);
        public static Index BURDEN_OUTCOME_CODE_KEY = Internal.createIndex("burden_outcome_code_key", BurdenOutcome.BURDEN_OUTCOME, new OrderField[] { BurdenOutcome.BURDEN_OUTCOME.CODE }, true);
        public static Index BURDEN_OUTCOME_PKEY = Internal.createIndex("burden_outcome_pkey", BurdenOutcome.BURDEN_OUTCOME, new OrderField[] { BurdenOutcome.BURDEN_OUTCOME.ID }, true);
        public static Index COUNTRY_NID_UNIQUE = Internal.createIndex("country_nid_unique", Country.COUNTRY, new OrderField[] { Country.COUNTRY.NID }, true);
        public static Index COUNTRY_PKEY = Internal.createIndex("country_pkey", Country.COUNTRY, new OrderField[] { Country.COUNTRY.ID }, true);
        public static Index COUNTRY_METADATA_PKEY = Internal.createIndex("country_metadata_pkey", CountryMetadata.COUNTRY_METADATA, new OrderField[] { CountryMetadata.COUNTRY_METADATA.ID }, true);
        public static Index COUNTRY_VACCINE_METADATA_PKEY = Internal.createIndex("country_vaccine_metadata_pkey", CountryVaccineMetadata.COUNTRY_VACCINE_METADATA, new OrderField[] { CountryVaccineMetadata.COUNTRY_VACCINE_METADATA.ID }, true);
        public static Index COVERAGE_PKEY = Internal.createIndex("coverage_pkey", Coverage.COVERAGE, new OrderField[] { Coverage.COVERAGE.ID }, true);
        public static Index COVERAGE_SET_PKEY = Internal.createIndex("coverage_set_pkey", CoverageSet.COVERAGE_SET, new OrderField[] { CoverageSet.COVERAGE_SET.ID }, true);
        public static Index DEMOGRAPHIC_DATASET_PKEY = Internal.createIndex("demographic_dataset_pkey", DemographicDataset.DEMOGRAPHIC_DATASET, new OrderField[] { DemographicDataset.DEMOGRAPHIC_DATASET.ID }, true);
        public static Index DEMOGRAPHIC_SOURCE_PKEY = Internal.createIndex("demographic_source_pkey", DemographicSource.DEMOGRAPHIC_SOURCE, new OrderField[] { DemographicSource.DEMOGRAPHIC_SOURCE.ID }, true);
        public static Index DEMOGRAPHIC_STATISTIC_COUNTRY_IDX = Internal.createIndex("demographic_statistic_country_idx", DemographicStatistic.DEMOGRAPHIC_STATISTIC, new OrderField[] { DemographicStatistic.DEMOGRAPHIC_STATISTIC.COUNTRY }, false);
        public static Index DEMOGRAPHIC_STATISTIC_DEMOGRAPHIC_SOURCE_IDX = Internal.createIndex("demographic_statistic_demographic_source_idx", DemographicStatistic.DEMOGRAPHIC_STATISTIC, new OrderField[] { DemographicStatistic.DEMOGRAPHIC_STATISTIC.DEMOGRAPHIC_SOURCE }, false);
        public static Index DEMOGRAPHIC_STATISTIC_DEMOGRAPHIC_STATISTIC_TYPE_IDX = Internal.createIndex("demographic_statistic_demographic_statistic_type_idx", DemographicStatistic.DEMOGRAPHIC_STATISTIC, new OrderField[] { DemographicStatistic.DEMOGRAPHIC_STATISTIC.DEMOGRAPHIC_STATISTIC_TYPE }, false);
        public static Index DEMOGRAPHIC_STATISTIC_DEMOGRAPHIC_VARIANT_IDX = Internal.createIndex("demographic_statistic_demographic_variant_idx", DemographicStatistic.DEMOGRAPHIC_STATISTIC, new OrderField[] { DemographicStatistic.DEMOGRAPHIC_STATISTIC.DEMOGRAPHIC_VARIANT }, false);
        public static Index DEMOGRAPHIC_STATISTIC_GENDER_IDX = Internal.createIndex("demographic_statistic_gender_idx", DemographicStatistic.DEMOGRAPHIC_STATISTIC, new OrderField[] { DemographicStatistic.DEMOGRAPHIC_STATISTIC.GENDER }, false);
        public static Index DEMOGRAPHIC_STATISTIC_PKEY = Internal.createIndex("demographic_statistic_pkey", DemographicStatistic.DEMOGRAPHIC_STATISTIC, new OrderField[] { DemographicStatistic.DEMOGRAPHIC_STATISTIC.ID }, true);
        public static Index DEMOGRAPHIC_STATISTIC_TYPE_PKEY = Internal.createIndex("demographic_statistic_type_pkey", DemographicStatisticType.DEMOGRAPHIC_STATISTIC_TYPE, new OrderField[] { DemographicStatisticType.DEMOGRAPHIC_STATISTIC_TYPE.ID }, true);
        public static Index DEMOGRAPHIC_STATISTIC_TYPE_VARIANT_PKEY = Internal.createIndex("demographic_statistic_type_variant_pkey", DemographicStatisticTypeVariant.DEMOGRAPHIC_STATISTIC_TYPE_VARIANT, new OrderField[] { DemographicStatisticTypeVariant.DEMOGRAPHIC_STATISTIC_TYPE_VARIANT.DEMOGRAPHIC_STATISTIC_TYPE, DemographicStatisticTypeVariant.DEMOGRAPHIC_STATISTIC_TYPE_VARIANT.DEMOGRAPHIC_VARIANT }, true);
        public static Index DEMOGRAPHIC_VALUE_UNIT_PKEY = Internal.createIndex("demographic_value_unit_pkey", DemographicValueUnit.DEMOGRAPHIC_VALUE_UNIT, new OrderField[] { DemographicValueUnit.DEMOGRAPHIC_VALUE_UNIT.ID }, true);
        public static Index DEMOGRAPHIC_VARIANT_PKEY = Internal.createIndex("demographic_variant_pkey", DemographicVariant.DEMOGRAPHIC_VARIANT, new OrderField[] { DemographicVariant.DEMOGRAPHIC_VARIANT.ID }, true);
        public static Index DISABILITY_WEIGHT_PKEY = Internal.createIndex("disability_weight_pkey", DisabilityWeight.DISABILITY_WEIGHT, new OrderField[] { DisabilityWeight.DISABILITY_WEIGHT.ID }, true);
        public static Index DISEASE_PKEY = Internal.createIndex("disease_pkey", Disease.DISEASE, new OrderField[] { Disease.DISEASE.ID }, true);
        public static Index GAVI_ELIGIBILITY_PKEY = Internal.createIndex("gavi_eligibility_pkey", GaviEligibility.GAVI_ELIGIBILITY, new OrderField[] { GaviEligibility.GAVI_ELIGIBILITY.ID }, true);
        public static Index GAVI_ELIGIBILITY_TOUCHSTONE_COUNTRY_YEAR_KEY = Internal.createIndex("gavi_eligibility_touchstone_country_year_key", GaviEligibility.GAVI_ELIGIBILITY, new OrderField[] { GaviEligibility.GAVI_ELIGIBILITY.TOUCHSTONE, GaviEligibility.GAVI_ELIGIBILITY.COUNTRY, GaviEligibility.GAVI_ELIGIBILITY.YEAR }, true);
        public static Index GAVI_ELIGIBILITY_STATUS_PKEY = Internal.createIndex("gavi_eligibility_status_pkey", GaviEligibilityStatus.GAVI_ELIGIBILITY_STATUS, new OrderField[] { GaviEligibilityStatus.GAVI_ELIGIBILITY_STATUS.ID }, true);
        public static Index GAVI_FOCAL_MODEL_PKEY = Internal.createIndex("gavi_focal_model_pkey", GaviFocalModel.GAVI_FOCAL_MODEL, new OrderField[] { GaviFocalModel.GAVI_FOCAL_MODEL.ID }, true);
        public static Index GAVI_SUPPORT_LEVEL_PKEY = Internal.createIndex("gavi_support_level_pkey", GaviSupportLevel.GAVI_SUPPORT_LEVEL, new OrderField[] { GaviSupportLevel.GAVI_SUPPORT_LEVEL.ID }, true);
        public static Index GENDER_PKEY = Internal.createIndex("gender_pkey", Gender.GENDER, new OrderField[] { Gender.GENDER.ID }, true);
        public static Index IMPACT_ESTIMATE_PKEY = Internal.createIndex("impact_estimate_pkey", ImpactEstimate.IMPACT_ESTIMATE, new OrderField[] { ImpactEstimate.IMPACT_ESTIMATE.ID }, true);
        public static Index IMPACT_ESTIMATE_INGREDIENT_PKEY = Internal.createIndex("impact_estimate_ingredient_pkey", ImpactEstimateIngredient.IMPACT_ESTIMATE_INGREDIENT, new OrderField[] { ImpactEstimateIngredient.IMPACT_ESTIMATE_INGREDIENT.ID }, true);
        public static Index IMPACT_ESTIMATE_INGREDIENT_RESPONSIBILITY_IMPACT_ESTIMATE_R_KEY = Internal.createIndex("impact_estimate_ingredient_responsibility_impact_estimate_r_key", ImpactEstimateIngredient.IMPACT_ESTIMATE_INGREDIENT, new OrderField[] { ImpactEstimateIngredient.IMPACT_ESTIMATE_INGREDIENT.RESPONSIBILITY, ImpactEstimateIngredient.IMPACT_ESTIMATE_INGREDIENT.IMPACT_ESTIMATE_RECIPE, ImpactEstimateIngredient.IMPACT_ESTIMATE_INGREDIENT.BURDEN_OUTCOME, ImpactEstimateIngredient.IMPACT_ESTIMATE_INGREDIENT.NAME }, true);
        public static Index IMPACT_ESTIMATE_RECIPE_PKEY = Internal.createIndex("impact_estimate_recipe_pkey", ImpactEstimateRecipe.IMPACT_ESTIMATE_RECIPE, new OrderField[] { ImpactEstimateRecipe.IMPACT_ESTIMATE_RECIPE.ID }, true);
        public static Index IMPACT_ESTIMATE_SET_PKEY = Internal.createIndex("impact_estimate_set_pkey", ImpactEstimateSet.IMPACT_ESTIMATE_SET, new OrderField[] { ImpactEstimateSet.IMPACT_ESTIMATE_SET.ID }, true);
        public static Index IMPACT_ESTIMATE_SET_INGREDIENT_PKEY = Internal.createIndex("impact_estimate_set_ingredient_pkey", ImpactEstimateSetIngredient.IMPACT_ESTIMATE_SET_INGREDIENT, new OrderField[] { ImpactEstimateSetIngredient.IMPACT_ESTIMATE_SET_INGREDIENT.ID }, true);
        public static Index IMPACT_OUTCOME_PKEY = Internal.createIndex("impact_outcome_pkey", ImpactOutcome.IMPACT_OUTCOME, new OrderField[] { ImpactOutcome.IMPACT_OUTCOME.ID }, true);
        public static Index LEGAL_AGREEMENT_PKEY = Internal.createIndex("legal_agreement_pkey", LegalAgreement.LEGAL_AGREEMENT, new OrderField[] { LegalAgreement.LEGAL_AGREEMENT.NAME }, true);
        public static Index MODEL_PKEY = Internal.createIndex("model_pkey", Model.MODEL, new OrderField[] { Model.MODEL.ID }, true);
        public static Index MODELLING_GROUP_DISEASE_UNIQUE_WHEN_CURRENT = Internal.createIndex("modelling_group_disease_unique_when_current", Model.MODEL, new OrderField[] { Model.MODEL.MODELLING_GROUP, Model.MODEL.DISEASE }, true);
        public static Index MODEL_RUN_PKEY = Internal.createIndex("model_run_pkey", ModelRun.MODEL_RUN, new OrderField[] { ModelRun.MODEL_RUN.INTERNAL_ID }, true);
        public static Index MODEL_RUN_PARAMETER_KEY_MODEL_RUN_PARAMETER_SET_KEY = Internal.createIndex("model_run_parameter_key_model_run_parameter_set_key", ModelRunParameter.MODEL_RUN_PARAMETER, new OrderField[] { ModelRunParameter.MODEL_RUN_PARAMETER.KEY, ModelRunParameter.MODEL_RUN_PARAMETER.MODEL_RUN_PARAMETER_SET }, true);
        public static Index MODEL_RUN_PARAMETER_PKEY = Internal.createIndex("model_run_parameter_pkey", ModelRunParameter.MODEL_RUN_PARAMETER, new OrderField[] { ModelRunParameter.MODEL_RUN_PARAMETER.ID }, true);
        public static Index MODEL_RUN_PARAMETER_SET_PKEY = Internal.createIndex("model_run_parameter_set_pkey", ModelRunParameterSet.MODEL_RUN_PARAMETER_SET, new OrderField[] { ModelRunParameterSet.MODEL_RUN_PARAMETER_SET.ID }, true);
        public static Index MODEL_RUN_PARAMETER_VALUE_MODEL_RUN_PARAMETER_MODEL_RUN_KEY = Internal.createIndex("model_run_parameter_value_model_run_parameter_model_run_key", ModelRunParameterValue.MODEL_RUN_PARAMETER_VALUE, new OrderField[] { ModelRunParameterValue.MODEL_RUN_PARAMETER_VALUE.MODEL_RUN_PARAMETER, ModelRunParameterValue.MODEL_RUN_PARAMETER_VALUE.MODEL_RUN }, true);
        public static Index MODEL_RUN_PARAMETER_VALUE_PKEY = Internal.createIndex("model_run_parameter_value_pkey", ModelRunParameterValue.MODEL_RUN_PARAMETER_VALUE, new OrderField[] { ModelRunParameterValue.MODEL_RUN_PARAMETER_VALUE.ID }, true);
        public static Index MODEL_VERSION_MODEL_VERSION_KEY = Internal.createIndex("model_version_model_version_key", ModelVersion.MODEL_VERSION, new OrderField[] { ModelVersion.MODEL_VERSION.MODEL, ModelVersion.MODEL_VERSION.VERSION }, true);
        public static Index MODEL_VERSION_PKEY = Internal.createIndex("model_version_pkey", ModelVersion.MODEL_VERSION, new OrderField[] { ModelVersion.MODEL_VERSION.ID }, true);
        public static Index MODELLING_GROUP_PKEY = Internal.createIndex("modelling_group_pkey", ModellingGroup.MODELLING_GROUP, new OrderField[] { ModellingGroup.MODELLING_GROUP.ID }, true);
        public static Index ONETIME_TOKEN_PKEY = Internal.createIndex("onetime_token_pkey", OnetimeToken.ONETIME_TOKEN, new OrderField[] { OnetimeToken.ONETIME_TOKEN.TOKEN }, true);
        public static Index PERMISSION_PKEY = Internal.createIndex("permission_pkey", Permission.PERMISSION, new OrderField[] { Permission.PERMISSION.NAME }, true);
        public static Index RESPONSIBILITY_PKEY = Internal.createIndex("responsibility_pkey", Responsibility.RESPONSIBILITY, new OrderField[] { Responsibility.RESPONSIBILITY.ID }, true);
        public static Index RESPONSIBILITY_RESPONSIBILITY_SET_SCENARIO_KEY = Internal.createIndex("responsibility_responsibility_set_scenario_key", Responsibility.RESPONSIBILITY, new OrderField[] { Responsibility.RESPONSIBILITY.RESPONSIBILITY_SET, Responsibility.RESPONSIBILITY.SCENARIO }, true);
        public static Index RESPONSIBILITY_SET_MODELLING_GROUP_TOUCHSTONE_KEY = Internal.createIndex("responsibility_set_modelling_group_touchstone_key", ResponsibilitySet.RESPONSIBILITY_SET, new OrderField[] { ResponsibilitySet.RESPONSIBILITY_SET.MODELLING_GROUP, ResponsibilitySet.RESPONSIBILITY_SET.TOUCHSTONE }, true);
        public static Index RESPONSIBILITY_SET_PKEY = Internal.createIndex("responsibility_set_pkey", ResponsibilitySet.RESPONSIBILITY_SET, new OrderField[] { ResponsibilitySet.RESPONSIBILITY_SET.ID }, true);
        public static Index RESPONSIBILITY_SET_STATUS_PKEY = Internal.createIndex("responsibility_set_status_pkey", ResponsibilitySetStatus.RESPONSIBILITY_SET_STATUS, new OrderField[] { ResponsibilitySetStatus.RESPONSIBILITY_SET_STATUS.ID }, true);
        public static Index ROLE_PKEY = Internal.createIndex("role_pkey", Role.ROLE, new OrderField[] { Role.ROLE.ID }, true);
        public static Index ROLE_PERMISSION_PKEY = Internal.createIndex("role_permission_pkey", RolePermission.ROLE_PERMISSION, new OrderField[] { RolePermission.ROLE_PERMISSION.ROLE, RolePermission.ROLE_PERMISSION.PERMISSION }, true);
        public static Index SCENARIO_PKEY = Internal.createIndex("scenario_pkey", Scenario.SCENARIO, new OrderField[] { Scenario.SCENARIO.ID }, true);
        public static Index SCENARIO_TOUCHSTONE_SCENARIO_DESCRIPTION_KEY = Internal.createIndex("scenario_touchstone_scenario_description_key", Scenario.SCENARIO, new OrderField[] { Scenario.SCENARIO.TOUCHSTONE, Scenario.SCENARIO.SCENARIO_DESCRIPTION }, true);
        public static Index SCENARIO_COVERAGE_SET_PKEY = Internal.createIndex("scenario_coverage_set_pkey", ScenarioCoverageSet.SCENARIO_COVERAGE_SET, new OrderField[] { ScenarioCoverageSet.SCENARIO_COVERAGE_SET.ID }, true);
        public static Index SCENARIO_DESCRIPTION_PKEY = Internal.createIndex("scenario_description_pkey", ScenarioDescription.SCENARIO_DESCRIPTION, new OrderField[] { ScenarioDescription.SCENARIO_DESCRIPTION.ID }, true);
        public static Index SUPPORT_TYPE_PKEY = Internal.createIndex("support_type_pkey", SupportType.SUPPORT_TYPE, new OrderField[] { SupportType.SUPPORT_TYPE.ID }, true);
        public static Index TOUCHSTONE_PKEY = Internal.createIndex("touchstone_pkey", Touchstone.TOUCHSTONE, new OrderField[] { Touchstone.TOUCHSTONE.ID }, true);
        public static Index TOUCHSTONE_TOUCHSTONE_NAME_VERSION_KEY = Internal.createIndex("touchstone_touchstone_name_version_key", Touchstone.TOUCHSTONE, new OrderField[] { Touchstone.TOUCHSTONE.TOUCHSTONE_NAME, Touchstone.TOUCHSTONE.VERSION }, true);
        public static Index TOUCHSTONE_COUNTRY_PKEY = Internal.createIndex("touchstone_country_pkey", TouchstoneCountry.TOUCHSTONE_COUNTRY, new OrderField[] { TouchstoneCountry.TOUCHSTONE_COUNTRY.ID }, true);
        public static Index TOUCHSTONE_DEMOGRAPHIC_DATASET_PKEY = Internal.createIndex("touchstone_demographic_dataset_pkey", TouchstoneDemographicDataset.TOUCHSTONE_DEMOGRAPHIC_DATASET, new OrderField[] { TouchstoneDemographicDataset.TOUCHSTONE_DEMOGRAPHIC_DATASET.ID }, true);
        public static Index TOUCHSTONE_DEMOGRAPHIC_SOURCE_PKEY = Internal.createIndex("touchstone_demographic_source_pkey", TouchstoneDemographicSource.TOUCHSTONE_DEMOGRAPHIC_SOURCE, new OrderField[] { TouchstoneDemographicSource.TOUCHSTONE_DEMOGRAPHIC_SOURCE.ID }, true);
        public static Index TOUCHSTONE_DEMOGRAPHIC_SOURCE_TOUCHSTONE_IDX = Internal.createIndex("touchstone_demographic_source_touchstone_idx", TouchstoneDemographicSource.TOUCHSTONE_DEMOGRAPHIC_SOURCE, new OrderField[] { TouchstoneDemographicSource.TOUCHSTONE_DEMOGRAPHIC_SOURCE.TOUCHSTONE }, false);
        public static Index TOUCHSTONE_NAME_PKEY = Internal.createIndex("touchstone_name_pkey", TouchstoneName.TOUCHSTONE_NAME, new OrderField[] { TouchstoneName.TOUCHSTONE_NAME.ID }, true);
        public static Index TOUCHSTONE_STATUS_PKEY = Internal.createIndex("touchstone_status_pkey", TouchstoneStatus.TOUCHSTONE_STATUS, new OrderField[] { TouchstoneStatus.TOUCHSTONE_STATUS.ID }, true);
        public static Index TOUCHSTONE_YEARS_PKEY = Internal.createIndex("touchstone_years_pkey", TouchstoneYears.TOUCHSTONE_YEARS, new OrderField[] { TouchstoneYears.TOUCHSTONE_YEARS.ID }, true);
        public static Index UPLOAD_INFO_PKEY = Internal.createIndex("upload_info_pkey", UploadInfo.UPLOAD_INFO, new OrderField[] { UploadInfo.UPLOAD_INFO.ID }, true);
        public static Index USER_GROUP_PKEY = Internal.createIndex("user_group_pkey", UserGroup.USER_GROUP, new OrderField[] { UserGroup.USER_GROUP.ID }, true);
        public static Index USER_GROUP_MEMBERSHIP_PKEY = Internal.createIndex("user_group_membership_pkey", UserGroupMembership.USER_GROUP_MEMBERSHIP, new OrderField[] { UserGroupMembership.USER_GROUP_MEMBERSHIP.USERNAME, UserGroupMembership.USER_GROUP_MEMBERSHIP.USER_GROUP }, true);
        public static Index USER_GROUP_ROLE_PKEY = Internal.createIndex("user_group_role_pkey", UserGroupRole.USER_GROUP_ROLE, new OrderField[] { UserGroupRole.USER_GROUP_ROLE.USER_GROUP, UserGroupRole.USER_GROUP_ROLE.ROLE, UserGroupRole.USER_GROUP_ROLE.SCOPE_ID }, true);
        public static Index VACCINE_PKEY = Internal.createIndex("vaccine_pkey", Vaccine.VACCINE, new OrderField[] { Vaccine.VACCINE.ID }, true);
        public static Index VACCINE_ROUTINE_AGE_PKEY = Internal.createIndex("vaccine_routine_age_pkey", VaccineRoutineAge.VACCINE_ROUTINE_AGE, new OrderField[] { VaccineRoutineAge.VACCINE_ROUTINE_AGE.ID }, true);
    }
}
