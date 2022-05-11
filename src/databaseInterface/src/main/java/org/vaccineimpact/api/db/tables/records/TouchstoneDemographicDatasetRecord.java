/*
 * This file is generated by jOOQ.
 */
package org.vaccineimpact.api.db.tables.records;


import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;
import org.vaccineimpact.api.db.tables.TouchstoneDemographicDataset;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TouchstoneDemographicDatasetRecord extends UpdatableRecordImpl<TouchstoneDemographicDatasetRecord> implements Record3<Integer, String, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.touchstone_demographic_dataset.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.touchstone_demographic_dataset.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.touchstone_demographic_dataset.touchstone</code>.
     */
    public void setTouchstone(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.touchstone_demographic_dataset.touchstone</code>.
     */
    public String getTouchstone() {
        return (String) get(1);
    }

    /**
     * Setter for
     * <code>public.touchstone_demographic_dataset.demographic_dataset</code>.
     */
    public void setDemographicDataset(Integer value) {
        set(2, value);
    }

    /**
     * Getter for
     * <code>public.touchstone_demographic_dataset.demographic_dataset</code>.
     */
    public Integer getDemographicDataset() {
        return (Integer) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<Integer, String, Integer> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<Integer, String, Integer> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return TouchstoneDemographicDataset.TOUCHSTONE_DEMOGRAPHIC_DATASET.ID;
    }

    @Override
    public Field<String> field2() {
        return TouchstoneDemographicDataset.TOUCHSTONE_DEMOGRAPHIC_DATASET.TOUCHSTONE;
    }

    @Override
    public Field<Integer> field3() {
        return TouchstoneDemographicDataset.TOUCHSTONE_DEMOGRAPHIC_DATASET.DEMOGRAPHIC_DATASET;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getTouchstone();
    }

    @Override
    public Integer component3() {
        return getDemographicDataset();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getTouchstone();
    }

    @Override
    public Integer value3() {
        return getDemographicDataset();
    }

    @Override
    public TouchstoneDemographicDatasetRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public TouchstoneDemographicDatasetRecord value2(String value) {
        setTouchstone(value);
        return this;
    }

    @Override
    public TouchstoneDemographicDatasetRecord value3(Integer value) {
        setDemographicDataset(value);
        return this;
    }

    @Override
    public TouchstoneDemographicDatasetRecord values(Integer value1, String value2, Integer value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TouchstoneDemographicDatasetRecord
     */
    public TouchstoneDemographicDatasetRecord() {
        super(TouchstoneDemographicDataset.TOUCHSTONE_DEMOGRAPHIC_DATASET);
    }

    /**
     * Create a detached, initialised TouchstoneDemographicDatasetRecord
     */
    public TouchstoneDemographicDatasetRecord(Integer id, String touchstone, Integer demographicDataset) {
        super(TouchstoneDemographicDataset.TOUCHSTONE_DEMOGRAPHIC_DATASET);

        setId(id);
        setTouchstone(touchstone);
        setDemographicDataset(demographicDataset);
    }
}
