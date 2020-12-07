/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables.records;


import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;
import org.vaccineimpact.api.db.tables.CoverageSetUploadMetadata;


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
public class CoverageSetUploadMetadataRecord extends UpdatableRecordImpl<CoverageSetUploadMetadataRecord> implements Record5<Integer, String, Timestamp, String, String> {

    private static final long serialVersionUID = -1188074992;

    /**
     * Setter for <code>public.coverage_set_upload_metadata.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.coverage_set_upload_metadata.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.coverage_set_upload_metadata.uploaded_by</code>.
     */
    public void setUploadedBy(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.coverage_set_upload_metadata.uploaded_by</code>.
     */
    public String getUploadedBy() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.coverage_set_upload_metadata.uploaded_on</code>.
     */
    public void setUploadedOn(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.coverage_set_upload_metadata.uploaded_on</code>.
     */
    public Timestamp getUploadedOn() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>public.coverage_set_upload_metadata.filename</code>.
     */
    public void setFilename(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.coverage_set_upload_metadata.filename</code>.
     */
    public String getFilename() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.coverage_set_upload_metadata.description</code>.
     */
    public void setDescription(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.coverage_set_upload_metadata.description</code>.
     */
    public String getDescription() {
        return (String) get(4);
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
    // Record5 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Integer, String, Timestamp, String, String> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Integer, String, Timestamp, String, String> valuesRow() {
        return (Row5) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return CoverageSetUploadMetadata.COVERAGE_SET_UPLOAD_METADATA.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return CoverageSetUploadMetadata.COVERAGE_SET_UPLOAD_METADATA.UPLOADED_BY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return CoverageSetUploadMetadata.COVERAGE_SET_UPLOAD_METADATA.UPLOADED_ON;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return CoverageSetUploadMetadata.COVERAGE_SET_UPLOAD_METADATA.FILENAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return CoverageSetUploadMetadata.COVERAGE_SET_UPLOAD_METADATA.DESCRIPTION;
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
        return getUploadedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component3() {
        return getUploadedOn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getFilename();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getDescription();
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
        return getUploadedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value3() {
        return getUploadedOn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getFilename();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CoverageSetUploadMetadataRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CoverageSetUploadMetadataRecord value2(String value) {
        setUploadedBy(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CoverageSetUploadMetadataRecord value3(Timestamp value) {
        setUploadedOn(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CoverageSetUploadMetadataRecord value4(String value) {
        setFilename(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CoverageSetUploadMetadataRecord value5(String value) {
        setDescription(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CoverageSetUploadMetadataRecord values(Integer value1, String value2, Timestamp value3, String value4, String value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached CoverageSetUploadMetadataRecord
     */
    public CoverageSetUploadMetadataRecord() {
        super(CoverageSetUploadMetadata.COVERAGE_SET_UPLOAD_METADATA);
    }

    /**
     * Create a detached, initialised CoverageSetUploadMetadataRecord
     */
    public CoverageSetUploadMetadataRecord(Integer id, String uploadedBy, Timestamp uploadedOn, String filename, String description) {
        super(CoverageSetUploadMetadata.COVERAGE_SET_UPLOAD_METADATA);

        set(0, id);
        set(1, uploadedBy);
        set(2, uploadedOn);
        set(3, filename);
        set(4, description);
    }
}