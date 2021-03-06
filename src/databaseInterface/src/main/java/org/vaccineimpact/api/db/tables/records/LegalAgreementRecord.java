/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables.records;


import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;
import org.vaccineimpact.api.db.tables.LegalAgreement;


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
public class LegalAgreementRecord extends UpdatableRecordImpl<LegalAgreementRecord> implements Record2<String, String> {

    private static final long serialVersionUID = -1758179237;

    /**
     * Setter for <code>public.legal_agreement.name</code>.
     */
    public void setName(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.legal_agreement.name</code>.
     */
    public String getName() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.legal_agreement.description</code>.
     */
    public void setDescription(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.legal_agreement.description</code>.
     */
    public String getDescription() {
        return (String) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<String, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<String, String> valuesRow() {
        return (Row2) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return LegalAgreement.LEGAL_AGREEMENT.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return LegalAgreement.LEGAL_AGREEMENT.DESCRIPTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component1() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LegalAgreementRecord value1(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LegalAgreementRecord value2(String value) {
        setDescription(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LegalAgreementRecord values(String value1, String value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached LegalAgreementRecord
     */
    public LegalAgreementRecord() {
        super(LegalAgreement.LEGAL_AGREEMENT);
    }

    /**
     * Create a detached, initialised LegalAgreementRecord
     */
    public LegalAgreementRecord(String name, String description) {
        super(LegalAgreement.LEGAL_AGREEMENT);

        set(0, name);
        set(1, description);
    }
}
