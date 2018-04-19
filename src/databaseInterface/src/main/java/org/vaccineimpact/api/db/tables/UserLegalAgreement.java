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
import org.vaccineimpact.api.db.tables.records.UserLegalAgreementRecord;


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
public class UserLegalAgreement extends TableImpl<UserLegalAgreementRecord> {

    private static final long serialVersionUID = 1168419857;

    /**
     * The reference instance of <code>public.user_legal_agreement</code>
     */
    public static final UserLegalAgreement USER_LEGAL_AGREEMENT = new UserLegalAgreement();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserLegalAgreementRecord> getRecordType() {
        return UserLegalAgreementRecord.class;
    }

    /**
     * The column <code>public.user_legal_agreement.legal_agreement</code>.
     */
    public final TableField<UserLegalAgreementRecord, String> LEGAL_AGREEMENT = createField("legal_agreement", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.user_legal_agreement.username</code>.
     */
    public final TableField<UserLegalAgreementRecord, String> USERNAME = createField("username", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.user_legal_agreement.date</code>.
     */
    public final TableField<UserLegalAgreementRecord, Timestamp> DATE = createField("date", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>public.user_legal_agreement</code> table reference
     */
    public UserLegalAgreement() {
        this(DSL.name("user_legal_agreement"), null);
    }

    /**
     * Create an aliased <code>public.user_legal_agreement</code> table reference
     */
    public UserLegalAgreement(String alias) {
        this(DSL.name(alias), USER_LEGAL_AGREEMENT);
    }

    /**
     * Create an aliased <code>public.user_legal_agreement</code> table reference
     */
    public UserLegalAgreement(Name alias) {
        this(alias, USER_LEGAL_AGREEMENT);
    }

    private UserLegalAgreement(Name alias, Table<UserLegalAgreementRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserLegalAgreement(Name alias, Table<UserLegalAgreementRecord> aliased, Field<?>[] parameters) {
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
    public List<ForeignKey<UserLegalAgreementRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<UserLegalAgreementRecord, ?>>asList(Keys.USER_LEGAL_AGREEMENT__USER_LEGAL_AGREEMENT_LEGAL_AGREEMENT_FKEY, Keys.USER_LEGAL_AGREEMENT__USER_LEGAL_AGREEMENT_USERNAME_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserLegalAgreement as(String alias) {
        return new UserLegalAgreement(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserLegalAgreement as(Name alias) {
        return new UserLegalAgreement(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserLegalAgreement rename(String name) {
        return new UserLegalAgreement(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public UserLegalAgreement rename(Name name) {
        return new UserLegalAgreement(name, null);
    }
}
