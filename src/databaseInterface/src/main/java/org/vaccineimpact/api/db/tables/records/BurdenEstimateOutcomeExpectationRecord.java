/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables.records;


import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;
import org.vaccineimpact.api.db.tables.BurdenEstimateOutcomeExpectation;


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
public class BurdenEstimateOutcomeExpectationRecord extends UpdatableRecordImpl<BurdenEstimateOutcomeExpectationRecord> implements Record2<Integer, String> {

    private static final long serialVersionUID = -1640282702;

    /**
     * Setter for <code>public.burden_estimate_outcome_expectation.burden_estimate_expectation</code>.
     */
    public void setBurdenEstimateExpectation(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.burden_estimate_outcome_expectation.burden_estimate_expectation</code>.
     */
    public Integer getBurdenEstimateExpectation() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.burden_estimate_outcome_expectation.outcome</code>.
     */
    public void setOutcome(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.burden_estimate_outcome_expectation.outcome</code>.
     */
    public String getOutcome() {
        return (String) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record2<Integer, String> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<Integer, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<Integer, String> valuesRow() {
        return (Row2) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return BurdenEstimateOutcomeExpectation.BURDEN_ESTIMATE_OUTCOME_EXPECTATION.BURDEN_ESTIMATE_EXPECTATION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return BurdenEstimateOutcomeExpectation.BURDEN_ESTIMATE_OUTCOME_EXPECTATION.OUTCOME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getBurdenEstimateExpectation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getOutcome();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getBurdenEstimateExpectation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getOutcome();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenEstimateOutcomeExpectationRecord value1(Integer value) {
        setBurdenEstimateExpectation(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenEstimateOutcomeExpectationRecord value2(String value) {
        setOutcome(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenEstimateOutcomeExpectationRecord values(Integer value1, String value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached BurdenEstimateOutcomeExpectationRecord
     */
    public BurdenEstimateOutcomeExpectationRecord() {
        super(BurdenEstimateOutcomeExpectation.BURDEN_ESTIMATE_OUTCOME_EXPECTATION);
    }

    /**
     * Create a detached, initialised BurdenEstimateOutcomeExpectationRecord
     */
    public BurdenEstimateOutcomeExpectationRecord(Integer burdenEstimateExpectation, String outcome) {
        super(BurdenEstimateOutcomeExpectation.BURDEN_ESTIMATE_OUTCOME_EXPECTATION);

        set(0, burdenEstimateExpectation);
        set(1, outcome);
    }
}
