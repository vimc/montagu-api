/*
 * This file is generated by jOOQ.
 */
package org.vaccineimpact.api.db.tables.records;


import java.time.LocalDate;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;
import org.vaccineimpact.api.db.tables.DemographicStatisticType;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DemographicStatisticTypeRecord extends UpdatableRecordImpl<DemographicStatisticTypeRecord> implements Record9<Integer, String, String, String, Integer, LocalDate, Boolean, Integer, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.demographic_statistic_type.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.demographic_statistic_type.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.demographic_statistic_type.code</code>.
     */
    public void setCode(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.demographic_statistic_type.code</code>.
     */
    public String getCode() {
        return (String) get(1);
    }

    /**
     * Setter for
     * <code>public.demographic_statistic_type.age_interpretation</code>.
     */
    public void setAgeInterpretation(String value) {
        set(2, value);
    }

    /**
     * Getter for
     * <code>public.demographic_statistic_type.age_interpretation</code>.
     */
    public String getAgeInterpretation() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.demographic_statistic_type.name</code>.
     */
    public void setName(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.demographic_statistic_type.name</code>.
     */
    public String getName() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.demographic_statistic_type.year_step_size</code>.
     */
    public void setYearStepSize(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.demographic_statistic_type.year_step_size</code>.
     */
    public Integer getYearStepSize() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>public.demographic_statistic_type.reference_date</code>.
     */
    public void setReferenceDate(LocalDate value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.demographic_statistic_type.reference_date</code>.
     */
    public LocalDate getReferenceDate() {
        return (LocalDate) get(5);
    }

    /**
     * Setter for
     * <code>public.demographic_statistic_type.gender_is_applicable</code>.
     */
    public void setGenderIsApplicable(Boolean value) {
        set(6, value);
    }

    /**
     * Getter for
     * <code>public.demographic_statistic_type.gender_is_applicable</code>.
     */
    public Boolean getGenderIsApplicable() {
        return (Boolean) get(6);
    }

    /**
     * Setter for
     * <code>public.demographic_statistic_type.demographic_value_unit</code>.
     */
    public void setDemographicValueUnit(Integer value) {
        set(7, value);
    }

    /**
     * Getter for
     * <code>public.demographic_statistic_type.demographic_value_unit</code>.
     */
    public Integer getDemographicValueUnit() {
        return (Integer) get(7);
    }

    /**
     * Setter for
     * <code>public.demographic_statistic_type.default_variant</code>.
     */
    public void setDefaultVariant(Integer value) {
        set(8, value);
    }

    /**
     * Getter for
     * <code>public.demographic_statistic_type.default_variant</code>.
     */
    public Integer getDefaultVariant() {
        return (Integer) get(8);
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
    public Row9<Integer, String, String, String, Integer, LocalDate, Boolean, Integer, Integer> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    @Override
    public Row9<Integer, String, String, String, Integer, LocalDate, Boolean, Integer, Integer> valuesRow() {
        return (Row9) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return DemographicStatisticType.DEMOGRAPHIC_STATISTIC_TYPE.ID;
    }

    @Override
    public Field<String> field2() {
        return DemographicStatisticType.DEMOGRAPHIC_STATISTIC_TYPE.CODE;
    }

    @Override
    public Field<String> field3() {
        return DemographicStatisticType.DEMOGRAPHIC_STATISTIC_TYPE.AGE_INTERPRETATION;
    }

    @Override
    public Field<String> field4() {
        return DemographicStatisticType.DEMOGRAPHIC_STATISTIC_TYPE.NAME;
    }

    @Override
    public Field<Integer> field5() {
        return DemographicStatisticType.DEMOGRAPHIC_STATISTIC_TYPE.YEAR_STEP_SIZE;
    }

    @Override
    public Field<LocalDate> field6() {
        return DemographicStatisticType.DEMOGRAPHIC_STATISTIC_TYPE.REFERENCE_DATE;
    }

    @Override
    public Field<Boolean> field7() {
        return DemographicStatisticType.DEMOGRAPHIC_STATISTIC_TYPE.GENDER_IS_APPLICABLE;
    }

    @Override
    public Field<Integer> field8() {
        return DemographicStatisticType.DEMOGRAPHIC_STATISTIC_TYPE.DEMOGRAPHIC_VALUE_UNIT;
    }

    @Override
    public Field<Integer> field9() {
        return DemographicStatisticType.DEMOGRAPHIC_STATISTIC_TYPE.DEFAULT_VARIANT;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getCode();
    }

    @Override
    public String component3() {
        return getAgeInterpretation();
    }

    @Override
    public String component4() {
        return getName();
    }

    @Override
    public Integer component5() {
        return getYearStepSize();
    }

    @Override
    public LocalDate component6() {
        return getReferenceDate();
    }

    @Override
    public Boolean component7() {
        return getGenderIsApplicable();
    }

    @Override
    public Integer component8() {
        return getDemographicValueUnit();
    }

    @Override
    public Integer component9() {
        return getDefaultVariant();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getCode();
    }

    @Override
    public String value3() {
        return getAgeInterpretation();
    }

    @Override
    public String value4() {
        return getName();
    }

    @Override
    public Integer value5() {
        return getYearStepSize();
    }

    @Override
    public LocalDate value6() {
        return getReferenceDate();
    }

    @Override
    public Boolean value7() {
        return getGenderIsApplicable();
    }

    @Override
    public Integer value8() {
        return getDemographicValueUnit();
    }

    @Override
    public Integer value9() {
        return getDefaultVariant();
    }

    @Override
    public DemographicStatisticTypeRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public DemographicStatisticTypeRecord value2(String value) {
        setCode(value);
        return this;
    }

    @Override
    public DemographicStatisticTypeRecord value3(String value) {
        setAgeInterpretation(value);
        return this;
    }

    @Override
    public DemographicStatisticTypeRecord value4(String value) {
        setName(value);
        return this;
    }

    @Override
    public DemographicStatisticTypeRecord value5(Integer value) {
        setYearStepSize(value);
        return this;
    }

    @Override
    public DemographicStatisticTypeRecord value6(LocalDate value) {
        setReferenceDate(value);
        return this;
    }

    @Override
    public DemographicStatisticTypeRecord value7(Boolean value) {
        setGenderIsApplicable(value);
        return this;
    }

    @Override
    public DemographicStatisticTypeRecord value8(Integer value) {
        setDemographicValueUnit(value);
        return this;
    }

    @Override
    public DemographicStatisticTypeRecord value9(Integer value) {
        setDefaultVariant(value);
        return this;
    }

    @Override
    public DemographicStatisticTypeRecord values(Integer value1, String value2, String value3, String value4, Integer value5, LocalDate value6, Boolean value7, Integer value8, Integer value9) {
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
     * Create a detached DemographicStatisticTypeRecord
     */
    public DemographicStatisticTypeRecord() {
        super(DemographicStatisticType.DEMOGRAPHIC_STATISTIC_TYPE);
    }

    /**
     * Create a detached, initialised DemographicStatisticTypeRecord
     */
    public DemographicStatisticTypeRecord(Integer id, String code, String ageInterpretation, String name, Integer yearStepSize, LocalDate referenceDate, Boolean genderIsApplicable, Integer demographicValueUnit, Integer defaultVariant) {
        super(DemographicStatisticType.DEMOGRAPHIC_STATISTIC_TYPE);

        setId(id);
        setCode(code);
        setAgeInterpretation(ageInterpretation);
        setName(name);
        setYearStepSize(yearStepSize);
        setReferenceDate(referenceDate);
        setGenderIsApplicable(genderIsApplicable);
        setDemographicValueUnit(demographicValueUnit);
        setDefaultVariant(defaultVariant);
    }
}
