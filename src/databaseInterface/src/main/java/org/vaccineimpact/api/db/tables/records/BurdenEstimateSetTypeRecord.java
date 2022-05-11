/*
 * This file is generated by jOOQ.
 */
package org.vaccineimpact.api.db.tables.records;


import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;
import org.vaccineimpact.api.db.tables.BurdenEstimateSetType;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class BurdenEstimateSetTypeRecord extends UpdatableRecordImpl<BurdenEstimateSetTypeRecord> implements Record3<String, String, Boolean> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.burden_estimate_set_type.code</code>.
     */
    public void setCode(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.burden_estimate_set_type.code</code>.
     */
    public String getCode() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.burden_estimate_set_type.description</code>.
     */
    public void setDescription(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.burden_estimate_set_type.description</code>.
     */
    public String getDescription() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.burden_estimate_set_type.is_valid_option</code>.
     * New burden_estimate_set rows should only be created with set types that
     * have is_valid_option to true. Those with false are for legacy data
     */
    public void setIsValidOption(Boolean value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.burden_estimate_set_type.is_valid_option</code>.
     * New burden_estimate_set rows should only be created with set types that
     * have is_valid_option to true. Those with false are for legacy data
     */
    public Boolean getIsValidOption() {
        return (Boolean) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<String, String, Boolean> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<String, String, Boolean> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return BurdenEstimateSetType.BURDEN_ESTIMATE_SET_TYPE.CODE;
    }

    @Override
    public Field<String> field2() {
        return BurdenEstimateSetType.BURDEN_ESTIMATE_SET_TYPE.DESCRIPTION;
    }

    @Override
    public Field<Boolean> field3() {
        return BurdenEstimateSetType.BURDEN_ESTIMATE_SET_TYPE.IS_VALID_OPTION;
    }

    @Override
    public String component1() {
        return getCode();
    }

    @Override
    public String component2() {
        return getDescription();
    }

    @Override
    public Boolean component3() {
        return getIsValidOption();
    }

    @Override
    public String value1() {
        return getCode();
    }

    @Override
    public String value2() {
        return getDescription();
    }

    @Override
    public Boolean value3() {
        return getIsValidOption();
    }

    @Override
    public BurdenEstimateSetTypeRecord value1(String value) {
        setCode(value);
        return this;
    }

    @Override
    public BurdenEstimateSetTypeRecord value2(String value) {
        setDescription(value);
        return this;
    }

    @Override
    public BurdenEstimateSetTypeRecord value3(Boolean value) {
        setIsValidOption(value);
        return this;
    }

    @Override
    public BurdenEstimateSetTypeRecord values(String value1, String value2, Boolean value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached BurdenEstimateSetTypeRecord
     */
    public BurdenEstimateSetTypeRecord() {
        super(BurdenEstimateSetType.BURDEN_ESTIMATE_SET_TYPE);
    }

    /**
     * Create a detached, initialised BurdenEstimateSetTypeRecord
     */
    public BurdenEstimateSetTypeRecord(String code, String description, Boolean isValidOption) {
        super(BurdenEstimateSetType.BURDEN_ESTIMATE_SET_TYPE);

        setCode(code);
        setDescription(description);
        setIsValidOption(isValidOption);
    }
}
