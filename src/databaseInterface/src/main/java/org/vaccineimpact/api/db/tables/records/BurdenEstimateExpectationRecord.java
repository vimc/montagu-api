/*
 * This file is generated by jOOQ.
 */
package org.vaccineimpact.api.db.tables.records;


import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;
import org.vaccineimpact.api.db.tables.BurdenEstimateExpectation;


/**
 * This table, in combination with burden_estimate_country_expectation and
 * burden_estimate_outcome_expectation, describes in detail the burden estimates
 * we expect to be uploaded for a particular responsibility. If you imagine
 * plotting expected year and age combinations on x and y axes, then the year_*
 * and age_* columns provide a rectangular area. Within those bounds, the cohort
 * columns optionally give us the ability to describe a triangular area. If a
 * cohort_min_inclusive is defined then only people born in that year and
 * afterwards are included. So if this is set to  2000 then the only ages
 * expected in 2000 are 0. Whereas by 2010, ages 0 - 10 are expected. 
 * Similarly, if cohort_max_inclusive is defined then only people born in that
 * year or before are included.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class BurdenEstimateExpectationRecord extends UpdatableRecordImpl<BurdenEstimateExpectationRecord> implements Record9<Integer, Short, Short, Short, Short, Short, Short, String, String> {

    private static final long serialVersionUID = 1L;

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
     * Setter for
     * <code>public.burden_estimate_expectation.year_min_inclusive</code>.
     */
    public void setYearMinInclusive(Short value) {
        set(1, value);
    }

    /**
     * Getter for
     * <code>public.burden_estimate_expectation.year_min_inclusive</code>.
     */
    public Short getYearMinInclusive() {
        return (Short) get(1);
    }

    /**
     * Setter for
     * <code>public.burden_estimate_expectation.year_max_inclusive</code>.
     */
    public void setYearMaxInclusive(Short value) {
        set(2, value);
    }

    /**
     * Getter for
     * <code>public.burden_estimate_expectation.year_max_inclusive</code>.
     */
    public Short getYearMaxInclusive() {
        return (Short) get(2);
    }

    /**
     * Setter for
     * <code>public.burden_estimate_expectation.age_min_inclusive</code>.
     */
    public void setAgeMinInclusive(Short value) {
        set(3, value);
    }

    /**
     * Getter for
     * <code>public.burden_estimate_expectation.age_min_inclusive</code>.
     */
    public Short getAgeMinInclusive() {
        return (Short) get(3);
    }

    /**
     * Setter for
     * <code>public.burden_estimate_expectation.age_max_inclusive</code>.
     */
    public void setAgeMaxInclusive(Short value) {
        set(4, value);
    }

    /**
     * Getter for
     * <code>public.burden_estimate_expectation.age_max_inclusive</code>.
     */
    public Short getAgeMaxInclusive() {
        return (Short) get(4);
    }

    /**
     * Setter for
     * <code>public.burden_estimate_expectation.cohort_min_inclusive</code>.
     */
    public void setCohortMinInclusive(Short value) {
        set(5, value);
    }

    /**
     * Getter for
     * <code>public.burden_estimate_expectation.cohort_min_inclusive</code>.
     */
    public Short getCohortMinInclusive() {
        return (Short) get(5);
    }

    /**
     * Setter for
     * <code>public.burden_estimate_expectation.cohort_max_inclusive</code>.
     */
    public void setCohortMaxInclusive(Short value) {
        set(6, value);
    }

    /**
     * Getter for
     * <code>public.burden_estimate_expectation.cohort_max_inclusive</code>.
     */
    public Short getCohortMaxInclusive() {
        return (Short) get(6);
    }

    /**
     * Setter for <code>public.burden_estimate_expectation.description</code>.
     */
    public void setDescription(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.burden_estimate_expectation.description</code>.
     */
    public String getDescription() {
        return (String) get(7);
    }

    /**
     * Setter for <code>public.burden_estimate_expectation.version</code>.
     */
    public void setVersion(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.burden_estimate_expectation.version</code>.
     */
    public String getVersion() {
        return (String) get(8);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record9 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row9<Integer, Short, Short, Short, Short, Short, Short, String, String> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    @Override
    public Row9<Integer, Short, Short, Short, Short, Short, Short, String, String> valuesRow() {
        return (Row9) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return BurdenEstimateExpectation.BURDEN_ESTIMATE_EXPECTATION.ID;
    }

    @Override
    public Field<Short> field2() {
        return BurdenEstimateExpectation.BURDEN_ESTIMATE_EXPECTATION.YEAR_MIN_INCLUSIVE;
    }

    @Override
    public Field<Short> field3() {
        return BurdenEstimateExpectation.BURDEN_ESTIMATE_EXPECTATION.YEAR_MAX_INCLUSIVE;
    }

    @Override
    public Field<Short> field4() {
        return BurdenEstimateExpectation.BURDEN_ESTIMATE_EXPECTATION.AGE_MIN_INCLUSIVE;
    }

    @Override
    public Field<Short> field5() {
        return BurdenEstimateExpectation.BURDEN_ESTIMATE_EXPECTATION.AGE_MAX_INCLUSIVE;
    }

    @Override
    public Field<Short> field6() {
        return BurdenEstimateExpectation.BURDEN_ESTIMATE_EXPECTATION.COHORT_MIN_INCLUSIVE;
    }

    @Override
    public Field<Short> field7() {
        return BurdenEstimateExpectation.BURDEN_ESTIMATE_EXPECTATION.COHORT_MAX_INCLUSIVE;
    }

    @Override
    public Field<String> field8() {
        return BurdenEstimateExpectation.BURDEN_ESTIMATE_EXPECTATION.DESCRIPTION;
    }

    @Override
    public Field<String> field9() {
        return BurdenEstimateExpectation.BURDEN_ESTIMATE_EXPECTATION.VERSION;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public Short component2() {
        return getYearMinInclusive();
    }

    @Override
    public Short component3() {
        return getYearMaxInclusive();
    }

    @Override
    public Short component4() {
        return getAgeMinInclusive();
    }

    @Override
    public Short component5() {
        return getAgeMaxInclusive();
    }

    @Override
    public Short component6() {
        return getCohortMinInclusive();
    }

    @Override
    public Short component7() {
        return getCohortMaxInclusive();
    }

    @Override
    public String component8() {
        return getDescription();
    }

    @Override
    public String component9() {
        return getVersion();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public Short value2() {
        return getYearMinInclusive();
    }

    @Override
    public Short value3() {
        return getYearMaxInclusive();
    }

    @Override
    public Short value4() {
        return getAgeMinInclusive();
    }

    @Override
    public Short value5() {
        return getAgeMaxInclusive();
    }

    @Override
    public Short value6() {
        return getCohortMinInclusive();
    }

    @Override
    public Short value7() {
        return getCohortMaxInclusive();
    }

    @Override
    public String value8() {
        return getDescription();
    }

    @Override
    public String value9() {
        return getVersion();
    }

    @Override
    public BurdenEstimateExpectationRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public BurdenEstimateExpectationRecord value2(Short value) {
        setYearMinInclusive(value);
        return this;
    }

    @Override
    public BurdenEstimateExpectationRecord value3(Short value) {
        setYearMaxInclusive(value);
        return this;
    }

    @Override
    public BurdenEstimateExpectationRecord value4(Short value) {
        setAgeMinInclusive(value);
        return this;
    }

    @Override
    public BurdenEstimateExpectationRecord value5(Short value) {
        setAgeMaxInclusive(value);
        return this;
    }

    @Override
    public BurdenEstimateExpectationRecord value6(Short value) {
        setCohortMinInclusive(value);
        return this;
    }

    @Override
    public BurdenEstimateExpectationRecord value7(Short value) {
        setCohortMaxInclusive(value);
        return this;
    }

    @Override
    public BurdenEstimateExpectationRecord value8(String value) {
        setDescription(value);
        return this;
    }

    @Override
    public BurdenEstimateExpectationRecord value9(String value) {
        setVersion(value);
        return this;
    }

    @Override
    public BurdenEstimateExpectationRecord values(Integer value1, Short value2, Short value3, Short value4, Short value5, Short value6, Short value7, String value8, String value9) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
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
    public BurdenEstimateExpectationRecord(Integer id, Short yearMinInclusive, Short yearMaxInclusive, Short ageMinInclusive, Short ageMaxInclusive, Short cohortMinInclusive, Short cohortMaxInclusive, String description, String version) {
        super(BurdenEstimateExpectation.BURDEN_ESTIMATE_EXPECTATION);

        setId(id);
        setYearMinInclusive(yearMinInclusive);
        setYearMaxInclusive(yearMaxInclusive);
        setAgeMinInclusive(ageMinInclusive);
        setAgeMaxInclusive(ageMaxInclusive);
        setCohortMinInclusive(cohortMinInclusive);
        setCohortMaxInclusive(cohortMaxInclusive);
        setDescription(description);
        setVersion(version);
    }
}
