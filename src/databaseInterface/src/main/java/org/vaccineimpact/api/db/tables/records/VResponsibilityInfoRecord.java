/*
 * This file is generated by jOOQ.
 */
package org.vaccineimpact.api.db.tables.records;


import org.jooq.Field;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.TableRecordImpl;
import org.vaccineimpact.api.db.tables.VResponsibilityInfo;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class VResponsibilityInfoRecord extends TableRecordImpl<VResponsibilityInfoRecord> implements Record6<String, String, String, String, Integer, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.v_responsibility_info.touchstone</code>.
     */
    public void setTouchstone(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.v_responsibility_info.touchstone</code>.
     */
    public String getTouchstone() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.v_responsibility_info.status</code>.
     */
    public void setStatus(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.v_responsibility_info.status</code>.
     */
    public String getStatus() {
        return (String) get(1);
    }

    /**
     * Setter for
     * <code>public.v_responsibility_info.scenario_description</code>.
     */
    public void setScenarioDescription(String value) {
        set(2, value);
    }

    /**
     * Getter for
     * <code>public.v_responsibility_info.scenario_description</code>.
     */
    public String getScenarioDescription() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.v_responsibility_info.disease</code>.
     */
    public void setDisease(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.v_responsibility_info.disease</code>.
     */
    public String getDisease() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.v_responsibility_info.responsibility_id</code>.
     */
    public void setResponsibilityId(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.v_responsibility_info.responsibility_id</code>.
     */
    public Integer getResponsibilityId() {
        return (Integer) get(4);
    }

    /**
     * Setter for
     * <code>public.v_responsibility_info.current_burden_estimate_set</code>.
     */
    public void setCurrentBurdenEstimateSet(Integer value) {
        set(5, value);
    }

    /**
     * Getter for
     * <code>public.v_responsibility_info.current_burden_estimate_set</code>.
     */
    public Integer getCurrentBurdenEstimateSet() {
        return (Integer) get(5);
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row6<String, String, String, String, Integer, Integer> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    public Row6<String, String, String, String, Integer, Integer> valuesRow() {
        return (Row6) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return VResponsibilityInfo.V_RESPONSIBILITY_INFO.TOUCHSTONE;
    }

    @Override
    public Field<String> field2() {
        return VResponsibilityInfo.V_RESPONSIBILITY_INFO.STATUS;
    }

    @Override
    public Field<String> field3() {
        return VResponsibilityInfo.V_RESPONSIBILITY_INFO.SCENARIO_DESCRIPTION;
    }

    @Override
    public Field<String> field4() {
        return VResponsibilityInfo.V_RESPONSIBILITY_INFO.DISEASE;
    }

    @Override
    public Field<Integer> field5() {
        return VResponsibilityInfo.V_RESPONSIBILITY_INFO.RESPONSIBILITY_ID;
    }

    @Override
    public Field<Integer> field6() {
        return VResponsibilityInfo.V_RESPONSIBILITY_INFO.CURRENT_BURDEN_ESTIMATE_SET;
    }

    @Override
    public String component1() {
        return getTouchstone();
    }

    @Override
    public String component2() {
        return getStatus();
    }

    @Override
    public String component3() {
        return getScenarioDescription();
    }

    @Override
    public String component4() {
        return getDisease();
    }

    @Override
    public Integer component5() {
        return getResponsibilityId();
    }

    @Override
    public Integer component6() {
        return getCurrentBurdenEstimateSet();
    }

    @Override
    public String value1() {
        return getTouchstone();
    }

    @Override
    public String value2() {
        return getStatus();
    }

    @Override
    public String value3() {
        return getScenarioDescription();
    }

    @Override
    public String value4() {
        return getDisease();
    }

    @Override
    public Integer value5() {
        return getResponsibilityId();
    }

    @Override
    public Integer value6() {
        return getCurrentBurdenEstimateSet();
    }

    @Override
    public VResponsibilityInfoRecord value1(String value) {
        setTouchstone(value);
        return this;
    }

    @Override
    public VResponsibilityInfoRecord value2(String value) {
        setStatus(value);
        return this;
    }

    @Override
    public VResponsibilityInfoRecord value3(String value) {
        setScenarioDescription(value);
        return this;
    }

    @Override
    public VResponsibilityInfoRecord value4(String value) {
        setDisease(value);
        return this;
    }

    @Override
    public VResponsibilityInfoRecord value5(Integer value) {
        setResponsibilityId(value);
        return this;
    }

    @Override
    public VResponsibilityInfoRecord value6(Integer value) {
        setCurrentBurdenEstimateSet(value);
        return this;
    }

    @Override
    public VResponsibilityInfoRecord values(String value1, String value2, String value3, String value4, Integer value5, Integer value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached VResponsibilityInfoRecord
     */
    public VResponsibilityInfoRecord() {
        super(VResponsibilityInfo.V_RESPONSIBILITY_INFO);
    }

    /**
     * Create a detached, initialised VResponsibilityInfoRecord
     */
    public VResponsibilityInfoRecord(String touchstone, String status, String scenarioDescription, String disease, Integer responsibilityId, Integer currentBurdenEstimateSet) {
        super(VResponsibilityInfo.V_RESPONSIBILITY_INFO);

        setTouchstone(touchstone);
        setStatus(status);
        setScenarioDescription(scenarioDescription);
        setDisease(disease);
        setResponsibilityId(responsibilityId);
        setCurrentBurdenEstimateSet(currentBurdenEstimateSet);
    }
}
