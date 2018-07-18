/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables.records;


import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;
import org.vaccineimpact.api.db.tables.BurdenEstimateExpectation;


/**
 * This table, in combination with burden_estimate_country_expectation and 
 * burden_estimate_outcome_expectation, describes in detail the burden estimates 
 * we expect to be uploaded for a particular responsibility. If you imagine 
 * plotting expected year and age combinations on x and y axes, then the year_* 
 * and age_* columns provide a rectangular area. Within those bounds, the 
 * cohort columns optionally give us the ability to describe a triangular 
 * area. If a cohort_min_inclusive is defined then only people born in that 
 * year and afterwards are included. So if this is set to  2000 then the only 
 * ages expected in 2000 are 0. Whereas by 2010, ages 0 - 10 are expected. 
 *  Similarly, if cohort_max_inclusive is defined then only people born in 
 * that year or before are included.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class BurdenEstimateExpectationRecord extends UpdatableRecordImpl<BurdenEstimateExpectationRecord> implements Record7<Integer, Short, Short, Short, Short, Short, Short> {

    private static final long serialVersionUID = -892168411;

    /**
     * Setter for <code>public.burden_estimate_expectation.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.burden_estimate_expectation.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.burden_estimate_expectation.year_min_inclusive</code>.
     */
    public void setYearMinInclusive(Short value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.burden_estimate_expectation.year_min_inclusive</code>.
     */
    public Short getYearMinInclusive() {
        return (Short) get(1);
    }

    /**
     * Setter for <code>public.burden_estimate_expectation.year_max_inclusive</code>.
     */
    public void setYearMaxInclusive(Short value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.burden_estimate_expectation.year_max_inclusive</code>.
     */
    public Short getYearMaxInclusive() {
        return (Short) get(2);
    }

    /**
     * Setter for <code>public.burden_estimate_expectation.age_min_inclusive</code>.
     */
    public void setAgeMinInclusive(Short value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.burden_estimate_expectation.age_min_inclusive</code>.
     */
    public Short getAgeMinInclusive() {
        return (Short) get(3);
    }

    /**
     * Setter for <code>public.burden_estimate_expectation.age_max_inclusive</code>.
     */
    public void setAgeMaxInclusive(Short value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.burden_estimate_expectation.age_max_inclusive</code>.
     */
    public Short getAgeMaxInclusive() {
        return (Short) get(4);
    }

    /**
     * Setter for <code>public.burden_estimate_expectation.cohort_min_inclusive</code>.
     */
    public void setCohortMinInclusive(Short value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.burden_estimate_expectation.cohort_min_inclusive</code>.
     */
    public Short getCohortMinInclusive() {
        return (Short) get(5);
    }

    /**
     * Setter for <code>public.burden_estimate_expectation.cohort_max_inclusive</code>.
     */
    public void setCohortMaxInclusive(Short value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.burden_estimate_expectation.cohort_max_inclusive</code>.
     */
    public Short getCohortMaxInclusive() {
        return (Short) get(6);
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
    // Record7 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, Short, Short, Short, Short, Short, Short> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, Short, Short, Short, Short, Short, Short> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return BurdenEstimateExpectation.BURDEN_ESTIMATE_EXPECTATION.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Short> field2() {
        return BurdenEstimateExpectation.BURDEN_ESTIMATE_EXPECTATION.YEAR_MIN_INCLUSIVE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Short> field3() {
        return BurdenEstimateExpectation.BURDEN_ESTIMATE_EXPECTATION.YEAR_MAX_INCLUSIVE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Short> field4() {
        return BurdenEstimateExpectation.BURDEN_ESTIMATE_EXPECTATION.AGE_MIN_INCLUSIVE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Short> field5() {
        return BurdenEstimateExpectation.BURDEN_ESTIMATE_EXPECTATION.AGE_MAX_INCLUSIVE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Short> field6() {
        return BurdenEstimateExpectation.BURDEN_ESTIMATE_EXPECTATION.COHORT_MIN_INCLUSIVE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Short> field7() {
        return BurdenEstimateExpectation.BURDEN_ESTIMATE_EXPECTATION.COHORT_MAX_INCLUSIVE;
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
    public Short component2() {
        return getYearMinInclusive();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short component3() {
        return getYearMaxInclusive();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short component4() {
        return getAgeMinInclusive();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short component5() {
        return getAgeMaxInclusive();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short component6() {
        return getCohortMinInclusive();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short component7() {
        return getCohortMaxInclusive();
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
    public Short value2() {
        return getYearMinInclusive();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short value3() {
        return getYearMaxInclusive();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short value4() {
        return getAgeMinInclusive();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short value5() {
        return getAgeMaxInclusive();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short value6() {
        return getCohortMinInclusive();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short value7() {
        return getCohortMaxInclusive();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenEstimateExpectationRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenEstimateExpectationRecord value2(Short value) {
        setYearMinInclusive(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenEstimateExpectationRecord value3(Short value) {
        setYearMaxInclusive(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenEstimateExpectationRecord value4(Short value) {
        setAgeMinInclusive(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenEstimateExpectationRecord value5(Short value) {
        setAgeMaxInclusive(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenEstimateExpectationRecord value6(Short value) {
        setCohortMinInclusive(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenEstimateExpectationRecord value7(Short value) {
        setCohortMaxInclusive(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurdenEstimateExpectationRecord values(Integer value1, Short value2, Short value3, Short value4, Short value5, Short value6, Short value7) {
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
     * Create a detached BurdenEstimateExpectationRecord
     */
    public BurdenEstimateExpectationRecord() {
        super(BurdenEstimateExpectation.BURDEN_ESTIMATE_EXPECTATION);
    }

    /**
     * Create a detached, initialised BurdenEstimateExpectationRecord
     */
    public BurdenEstimateExpectationRecord(Integer id, Short yearMinInclusive, Short yearMaxInclusive, Short ageMinInclusive, Short ageMaxInclusive, Short cohortMinInclusive, Short cohortMaxInclusive) {
        super(BurdenEstimateExpectation.BURDEN_ESTIMATE_EXPECTATION);

        set(0, id);
        set(1, yearMinInclusive);
        set(2, yearMaxInclusive);
        set(3, ageMinInclusive);
        set(4, ageMaxInclusive);
        set(5, cohortMinInclusive);
        set(6, cohortMaxInclusive);
    }
}
