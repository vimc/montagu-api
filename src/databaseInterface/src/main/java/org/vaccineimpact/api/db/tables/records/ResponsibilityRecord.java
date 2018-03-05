/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables.records;


import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;
import org.vaccineimpact.api.db.tables.Responsibility;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ResponsibilityRecord extends UpdatableRecordImpl<ResponsibilityRecord> implements Record6<Integer, Integer, Integer, Integer, Integer, Boolean> {

    private static final long serialVersionUID = 620336901;

    /**
     * Setter for <code>public.responsibility.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.responsibility.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.responsibility.responsibility_set</code>.
     */
    public void setResponsibilitySet(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.responsibility.responsibility_set</code>.
     */
    public Integer getResponsibilitySet() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>public.responsibility.scenario</code>.
     */
    public void setScenario(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.responsibility.scenario</code>.
     */
    public Integer getScenario() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>public.responsibility.current_burden_estimate_set</code>.
     */
    public void setCurrentBurdenEstimateSet(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.responsibility.current_burden_estimate_set</code>.
     */
    public Integer getCurrentBurdenEstimateSet() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>public.responsibility.current_stochastic_burden_estimate_set</code>.
     */
    public void setCurrentStochasticBurdenEstimateSet(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.responsibility.current_stochastic_burden_estimate_set</code>.
     */
    public Integer getCurrentStochasticBurdenEstimateSet() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>public.responsibility.is_open</code>.
     */
    public void setIsOpen(Boolean value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.responsibility.is_open</code>.
     */
    public Boolean getIsOpen() {
        return (Boolean) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, Integer, Integer, Integer, Integer, Boolean> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, Integer, Integer, Integer, Integer, Boolean> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Responsibility.RESPONSIBILITY.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return Responsibility.RESPONSIBILITY.RESPONSIBILITY_SET;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return Responsibility.RESPONSIBILITY.SCENARIO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return Responsibility.RESPONSIBILITY.CURRENT_BURDEN_ESTIMATE_SET;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return Responsibility.RESPONSIBILITY.CURRENT_STOCHASTIC_BURDEN_ESTIMATE_SET;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field6() {
        return Responsibility.RESPONSIBILITY.IS_OPEN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component2() {
        return getResponsibilitySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component3() {
        return getScenario();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component4() {
        return getCurrentBurdenEstimateSet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component5() {
        return getCurrentStochasticBurdenEstimateSet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean component6() {
        return getIsOpen();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getResponsibilitySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getScenario();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getCurrentBurdenEstimateSet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getCurrentStochasticBurdenEstimateSet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value6() {
        return getIsOpen();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponsibilityRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponsibilityRecord value2(Integer value) {
        setResponsibilitySet(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponsibilityRecord value3(Integer value) {
        setScenario(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponsibilityRecord value4(Integer value) {
        setCurrentBurdenEstimateSet(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponsibilityRecord value5(Integer value) {
        setCurrentStochasticBurdenEstimateSet(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponsibilityRecord value6(Boolean value) {
        setIsOpen(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponsibilityRecord values(Integer value1, Integer value2, Integer value3, Integer value4, Integer value5, Boolean value6) {
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
     * Create a detached ResponsibilityRecord
     */
    public ResponsibilityRecord() {
        super(Responsibility.RESPONSIBILITY);
    }

    /**
     * Create a detached, initialised ResponsibilityRecord
     */
    public ResponsibilityRecord(Integer id, Integer responsibilitySet, Integer scenario, Integer currentBurdenEstimateSet, Integer currentStochasticBurdenEstimateSet, Boolean isOpen) {
        super(Responsibility.RESPONSIBILITY);

        set(0, id);
        set(1, responsibilitySet);
        set(2, scenario);
        set(3, currentBurdenEstimateSet);
        set(4, currentStochasticBurdenEstimateSet);
        set(5, isOpen);
    }
}
