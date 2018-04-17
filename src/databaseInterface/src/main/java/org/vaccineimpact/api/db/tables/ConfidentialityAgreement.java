/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables;


import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;
import org.vaccineimpact.api.db.Keys;
import org.vaccineimpact.api.db.Public;
import org.vaccineimpact.api.db.tables.records.ConfidentialityAgreementRecord;


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
public class ConfidentialityAgreement extends TableImpl<ConfidentialityAgreementRecord> {

    private static final long serialVersionUID = 445849337;

    /**
     * The reference instance of <code>public.confidentiality_agreement</code>
     */
    public static final ConfidentialityAgreement CONFIDENTIALITY_AGREEMENT = new ConfidentialityAgreement();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ConfidentialityAgreementRecord> getRecordType() {
        return ConfidentialityAgreementRecord.class;
    }

    /**
     * The column <code>public.confidentiality_agreement.username</code>.
     */
    public final TableField<ConfidentialityAgreementRecord, String> USERNAME = createField("username", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.confidentiality_agreement.date</code>.
     */
    public final TableField<ConfidentialityAgreementRecord, Timestamp> DATE = createField("date", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>public.confidentiality_agreement.signed</code>.
     */
    public final TableField<ConfidentialityAgreementRecord, Boolean> SIGNED = createField("signed", org.jooq.impl.SQLDataType.BOOLEAN, this, "");

    /**
     * Create a <code>public.confidentiality_agreement</code> table reference
     */
    public ConfidentialityAgreement() {
        this(DSL.name("confidentiality_agreement"), null);
    }

    /**
     * Create an aliased <code>public.confidentiality_agreement</code> table reference
     */
    public ConfidentialityAgreement(String alias) {
        this(DSL.name(alias), CONFIDENTIALITY_AGREEMENT);
    }

    /**
     * Create an aliased <code>public.confidentiality_agreement</code> table reference
     */
    public ConfidentialityAgreement(Name alias) {
        this(alias, CONFIDENTIALITY_AGREEMENT);
    }

    private ConfidentialityAgreement(Name alias, Table<ConfidentialityAgreementRecord> aliased) {
        this(alias, aliased, null);
    }

    private ConfidentialityAgreement(Name alias, Table<ConfidentialityAgreementRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<ConfidentialityAgreementRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ConfidentialityAgreementRecord, ?>>asList(Keys.CONFIDENTIALITY_AGREEMENT__CONFIDENTIALITY_AGREEMENT_USERNAME_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfidentialityAgreement as(String alias) {
        return new ConfidentialityAgreement(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfidentialityAgreement as(Name alias) {
        return new ConfidentialityAgreement(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ConfidentialityAgreement rename(String name) {
        return new ConfidentialityAgreement(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ConfidentialityAgreement rename(Name name) {
        return new ConfidentialityAgreement(name, null);
    }
}
