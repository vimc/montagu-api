/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Catalog;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;
import org.vaccineimpact.api.db.tables.ActivityType;
import org.vaccineimpact.api.db.tables.AppUser;
import org.vaccineimpact.api.db.tables.BurdenEstimate;
import org.vaccineimpact.api.db.tables.BurdenEstimateSet;
import org.vaccineimpact.api.db.tables.Country;
import org.vaccineimpact.api.db.tables.Coverage;
import org.vaccineimpact.api.db.tables.CoverageSet;
import org.vaccineimpact.api.db.tables.Disease;
import org.vaccineimpact.api.db.tables.GaviSupportLevel;
import org.vaccineimpact.api.db.tables.ImpactEstimate;
import org.vaccineimpact.api.db.tables.ImpactEstimateComponents;
import org.vaccineimpact.api.db.tables.ImpactEstimateSet;
import org.vaccineimpact.api.db.tables.Model;
import org.vaccineimpact.api.db.tables.ModelVersion;
import org.vaccineimpact.api.db.tables.ModellingGroup;
import org.vaccineimpact.api.db.tables.Outcome;
import org.vaccineimpact.api.db.tables.Permission;
import org.vaccineimpact.api.db.tables.Responsibility;
import org.vaccineimpact.api.db.tables.ResponsibilitySet;
import org.vaccineimpact.api.db.tables.ResponsibilitySetStatus;
import org.vaccineimpact.api.db.tables.Role;
import org.vaccineimpact.api.db.tables.RolePermission;
import org.vaccineimpact.api.db.tables.Scenario;
import org.vaccineimpact.api.db.tables.ScenarioCoverageSet;
import org.vaccineimpact.api.db.tables.ScenarioDescription;
import org.vaccineimpact.api.db.tables.Touchstone;
import org.vaccineimpact.api.db.tables.TouchstoneCountry;
import org.vaccineimpact.api.db.tables.TouchstoneName;
import org.vaccineimpact.api.db.tables.TouchstoneStatus;
import org.vaccineimpact.api.db.tables.UserRole;
import org.vaccineimpact.api.db.tables.Vaccine;


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

    private static final long serialVersionUID = 804692792;

    /**
     * The reference instance of <code>public</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * This is mostly "none", "routine" or "campaign" but with a few extras
     */
    public final ActivityType ACTIVITY_TYPE = org.vaccineimpact.api.db.tables.ActivityType.ACTIVITY_TYPE;

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
     * The table <code>public.country</code>.
     */
    public final Country COUNTRY = org.vaccineimpact.api.db.tables.Country.COUNTRY;

    /**
     * The table <code>public.coverage</code>.
     */
    public final Coverage COVERAGE = org.vaccineimpact.api.db.tables.Coverage.COVERAGE;

    /**
     * The table <code>public.coverage_set</code>.
     */
    public final CoverageSet COVERAGE_SET = org.vaccineimpact.api.db.tables.CoverageSet.COVERAGE_SET;

    /**
     * The table <code>public.disease</code>.
     */
    public final Disease DISEASE = org.vaccineimpact.api.db.tables.Disease.DISEASE;

    /**
     * Enum table. Possible values: none (No vaccination), without (Vaccination without GAVI support), with (Vaccination with GAVI support)
     */
    public final GaviSupportLevel GAVI_SUPPORT_LEVEL = org.vaccineimpact.api.db.tables.GaviSupportLevel.GAVI_SUPPORT_LEVEL;

    /**
     * The table <code>public.impact_estimate</code>.
     */
    public final ImpactEstimate IMPACT_ESTIMATE = org.vaccineimpact.api.db.tables.ImpactEstimate.IMPACT_ESTIMATE;

    /**
     * The table <code>public.impact_estimate_components</code>.
     */
    public final ImpactEstimateComponents IMPACT_ESTIMATE_COMPONENTS = org.vaccineimpact.api.db.tables.ImpactEstimateComponents.IMPACT_ESTIMATE_COMPONENTS;

    /**
     * The table <code>public.impact_estimate_set</code>.
     */
    public final ImpactEstimateSet IMPACT_ESTIMATE_SET = org.vaccineimpact.api.db.tables.ImpactEstimateSet.IMPACT_ESTIMATE_SET;

    /**
     * With the self-referencing "current" field; we consider a model to be the current one if current is null.  See comment about recursion in modelling_group
     */
    public final Model MODEL = org.vaccineimpact.api.db.tables.Model.MODEL;

    /**
     * The table <code>public.model_version</code>.
     */
    public final ModelVersion MODEL_VERSION = org.vaccineimpact.api.db.tables.ModelVersion.MODEL_VERSION;

    /**
     * With the self-referencing "current" field; we consider a modelling group to be the current one if current is null.  This is not recursive; if we move a modelling group to a new id then every modelling group that has current pointing at the old id must be updated to point at the new one.  This means that no `current` points at an `id` that does not have `current` as `null`.
     */
    public final ModellingGroup MODELLING_GROUP = org.vaccineimpact.api.db.tables.ModellingGroup.MODELLING_GROUP;

    /**
     * The table <code>public.outcome</code>.
     */
    public final Outcome OUTCOME = org.vaccineimpact.api.db.tables.Outcome.OUTCOME;

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
     * This is the top-level categorization. It refers to an Operational Forecast from GAVI, a WUENIC July update, or some other data set against which impact estimates are going to be done 
     */
    public final Touchstone TOUCHSTONE = org.vaccineimpact.api.db.tables.Touchstone.TOUCHSTONE;

    /**
     * The table <code>public.touchstone_country</code>.
     */
    public final TouchstoneCountry TOUCHSTONE_COUNTRY = org.vaccineimpact.api.db.tables.TouchstoneCountry.TOUCHSTONE_COUNTRY;

    /**
     * The table <code>public.touchstone_name</code>.
     */
    public final TouchstoneName TOUCHSTONE_NAME = org.vaccineimpact.api.db.tables.TouchstoneName.TOUCHSTONE_NAME;

    /**
     * Valid values: {in-preparation, open, finished}
     */
    public final TouchstoneStatus TOUCHSTONE_STATUS = org.vaccineimpact.api.db.tables.TouchstoneStatus.TOUCHSTONE_STATUS;

    /**
     * The table <code>public.user_role</code>.
     */
    public final UserRole USER_ROLE = org.vaccineimpact.api.db.tables.UserRole.USER_ROLE;

    /**
     * The table <code>public.vaccine</code>.
     */
    public final Vaccine VACCINE = org.vaccineimpact.api.db.tables.Vaccine.VACCINE;

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
            Sequences.BURDEN_ESTIMATE_ID_SEQ,
            Sequences.BURDEN_ESTIMATE_SET_ID_SEQ,
            Sequences.COVERAGE_ID_SEQ,
            Sequences.COVERAGE_SET_ID_SEQ,
            Sequences.IMPACT_ESTIMATE_COMPONENTS_ID_SEQ,
            Sequences.IMPACT_ESTIMATE_ID_SEQ,
            Sequences.IMPACT_ESTIMATE_SET_ID_SEQ,
            Sequences.MODEL_VERSION_ID_SEQ,
            Sequences.OUTCOME_ID_SEQ,
            Sequences.RESPONSIBILITY_ID_SEQ,
            Sequences.RESPONSIBILITY_SET_ID_SEQ,
            Sequences.ROLE_ID_SEQ,
            Sequences.SCENARIO_COVERAGE_SET_ID_SEQ,
            Sequences.SCENARIO_ID_SEQ,
            Sequences.TOUCHSTONE_COUNTRY_ID_SEQ);
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
            AppUser.APP_USER,
            BurdenEstimate.BURDEN_ESTIMATE,
            BurdenEstimateSet.BURDEN_ESTIMATE_SET,
            Country.COUNTRY,
            Coverage.COVERAGE,
            CoverageSet.COVERAGE_SET,
            Disease.DISEASE,
            GaviSupportLevel.GAVI_SUPPORT_LEVEL,
            ImpactEstimate.IMPACT_ESTIMATE,
            ImpactEstimateComponents.IMPACT_ESTIMATE_COMPONENTS,
            ImpactEstimateSet.IMPACT_ESTIMATE_SET,
            Model.MODEL,
            ModelVersion.MODEL_VERSION,
            ModellingGroup.MODELLING_GROUP,
            Outcome.OUTCOME,
            Permission.PERMISSION,
            Responsibility.RESPONSIBILITY,
            ResponsibilitySet.RESPONSIBILITY_SET,
            ResponsibilitySetStatus.RESPONSIBILITY_SET_STATUS,
            Role.ROLE,
            RolePermission.ROLE_PERMISSION,
            Scenario.SCENARIO,
            ScenarioCoverageSet.SCENARIO_COVERAGE_SET,
            ScenarioDescription.SCENARIO_DESCRIPTION,
            Touchstone.TOUCHSTONE,
            TouchstoneCountry.TOUCHSTONE_COUNTRY,
            TouchstoneName.TOUCHSTONE_NAME,
            TouchstoneStatus.TOUCHSTONE_STATUS,
            UserRole.USER_ROLE,
            Vaccine.VACCINE);
    }
}
