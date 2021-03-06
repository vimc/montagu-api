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
import org.vaccineimpact.api.db.tables.ModelVersion;


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
public class ModelVersionRecord extends UpdatableRecordImpl<ModelVersionRecord> implements Record7<Integer, String, String, String, String, String, Boolean> {

    private static final long serialVersionUID = -191872098;

    /**
     * Setter for <code>public.model_version.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.model_version.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.model_version.model</code>.
     */
    public void setModel(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.model_version.model</code>.
     */
    public String getModel() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.model_version.version</code>.
     */
    public void setVersion(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.model_version.version</code>.
     */
    public String getVersion() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.model_version.note</code>.
     */
    public void setNote(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.model_version.note</code>.
     */
    public String getNote() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.model_version.fingerprint</code>.
     */
    public void setFingerprint(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.model_version.fingerprint</code>.
     */
    public String getFingerprint() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.model_version.code</code>.
     */
    public void setCode(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.model_version.code</code>.
     */
    public String getCode() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.model_version.is_dynamic</code>.
     */
    public void setIsDynamic(Boolean value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.model_version.is_dynamic</code>.
     */
    public Boolean getIsDynamic() {
        return (Boolean) get(6);
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
    public Row7<Integer, String, String, String, String, String, Boolean> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, String, String, String, String, String, Boolean> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return ModelVersion.MODEL_VERSION.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return ModelVersion.MODEL_VERSION.MODEL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return ModelVersion.MODEL_VERSION.VERSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return ModelVersion.MODEL_VERSION.NOTE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return ModelVersion.MODEL_VERSION.FINGERPRINT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return ModelVersion.MODEL_VERSION.CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field7() {
        return ModelVersion.MODEL_VERSION.IS_DYNAMIC;
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
    public String component2() {
        return getModel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getNote();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getFingerprint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean component7() {
        return getIsDynamic();
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
    public String value2() {
        return getModel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getNote();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getFingerprint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value7() {
        return getIsDynamic();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelVersionRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelVersionRecord value2(String value) {
        setModel(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelVersionRecord value3(String value) {
        setVersion(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelVersionRecord value4(String value) {
        setNote(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelVersionRecord value5(String value) {
        setFingerprint(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelVersionRecord value6(String value) {
        setCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelVersionRecord value7(Boolean value) {
        setIsDynamic(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelVersionRecord values(Integer value1, String value2, String value3, String value4, String value5, String value6, Boolean value7) {
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
     * Create a detached ModelVersionRecord
     */
    public ModelVersionRecord() {
        super(ModelVersion.MODEL_VERSION);
    }

    /**
     * Create a detached, initialised ModelVersionRecord
     */
    public ModelVersionRecord(Integer id, String model, String version, String note, String fingerprint, String code, Boolean isDynamic) {
        super(ModelVersion.MODEL_VERSION);

        set(0, id);
        set(1, model);
        set(2, version);
        set(3, note);
        set(4, fingerprint);
        set(5, code);
        set(6, isDynamic);
    }
}
