/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables.records;


import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.TableRecordImpl;
import org.vaccineimpact.api.db.tables.VCoverageInfo;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class VCoverageInfoRecord extends TableRecordImpl<VCoverageInfoRecord> implements Record7<String, String, String, String, String, String, Integer> {

    private static final long serialVersionUID = -1458297327;

    /**
     * Setter for <code>public.v_coverage_info.touchstone</code>.
     */
    public void setTouchstone(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.v_coverage_info.touchstone</code>.
     */
    public String getTouchstone() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.v_coverage_info.scenario_description</code>.
     */
    public void setScenarioDescription(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.v_coverage_info.scenario_description</code>.
     */
    public String getScenarioDescription() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.v_coverage_info.disease</code>.
     */
    public void setDisease(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.v_coverage_info.disease</code>.
     */
    public String getDisease() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.v_coverage_info.vaccine</code>.
     */
    public void setVaccine(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.v_coverage_info.vaccine</code>.
     */
    public String getVaccine() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.v_coverage_info.gavi_support_level</code>.
     */
    public void setGaviSupportLevel(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.v_coverage_info.gavi_support_level</code>.
     */
    public String getGaviSupportLevel() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.v_coverage_info.activity_type</code>.
     */
    public void setActivityType(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.v_coverage_info.activity_type</code>.
     */
    public String getActivityType() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.v_coverage_info.coverage_set_id</code>.
     */
    public void setCoverageSetId(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.v_coverage_info.coverage_set_id</code>.
     */
    public Integer getCoverageSetId() {
        return (Integer) get(6);
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<String, String, String, String, String, String, Integer> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<String, String, String, String, String, String, Integer> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return VCoverageInfo.V_COVERAGE_INFO.TOUCHSTONE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return VCoverageInfo.V_COVERAGE_INFO.SCENARIO_DESCRIPTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return VCoverageInfo.V_COVERAGE_INFO.DISEASE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return VCoverageInfo.V_COVERAGE_INFO.VACCINE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return VCoverageInfo.V_COVERAGE_INFO.GAVI_SUPPORT_LEVEL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return VCoverageInfo.V_COVERAGE_INFO.ACTIVITY_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return VCoverageInfo.V_COVERAGE_INFO.COVERAGE_SET_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component1() {
        return getTouchstone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getScenarioDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getDisease();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getVaccine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getGaviSupportLevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getActivityType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component7() {
        return getCoverageSetId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getTouchstone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getScenarioDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getDisease();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getVaccine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getGaviSupportLevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getActivityType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getCoverageSetId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VCoverageInfoRecord value1(String value) {
        setTouchstone(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VCoverageInfoRecord value2(String value) {
        setScenarioDescription(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VCoverageInfoRecord value3(String value) {
        setDisease(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VCoverageInfoRecord value4(String value) {
        setVaccine(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VCoverageInfoRecord value5(String value) {
        setGaviSupportLevel(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VCoverageInfoRecord value6(String value) {
        setActivityType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VCoverageInfoRecord value7(Integer value) {
        setCoverageSetId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VCoverageInfoRecord values(String value1, String value2, String value3, String value4, String value5, String value6, Integer value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached VCoverageInfoRecord
     */
    public VCoverageInfoRecord() {
        super(VCoverageInfo.V_COVERAGE_INFO);
    }

    /**
     * Create a detached, initialised VCoverageInfoRecord
     */
    public VCoverageInfoRecord(String touchstone, String scenarioDescription, String disease, String vaccine, String gaviSupportLevel, String activityType, Integer coverageSetId) {
        super(VCoverageInfo.V_COVERAGE_INFO);

        set(0, touchstone);
        set(1, scenarioDescription);
        set(2, disease);
        set(3, vaccine);
        set(4, gaviSupportLevel);
        set(5, activityType);
        set(6, coverageSetId);
    }
}
