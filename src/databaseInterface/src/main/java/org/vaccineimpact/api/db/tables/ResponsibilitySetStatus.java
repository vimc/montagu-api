/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;
import org.vaccineimpact.api.db.Indexes;
import org.vaccineimpact.api.db.Keys;
import org.vaccineimpact.api.db.Public;
import org.vaccineimpact.api.db.tables.records.ResponsibilitySetStatusRecord;


/**
 * Possible values {incomplete, submitted, approved}
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ResponsibilitySetStatus extends TableImpl<ResponsibilitySetStatusRecord> {

    private static final long serialVersionUID = 1269830444;

    /**
     * The reference instance of <code>public.responsibility_set_status</code>
     */
    public static final ResponsibilitySetStatus RESPONSIBILITY_SET_STATUS = new ResponsibilitySetStatus();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ResponsibilitySetStatusRecord> getRecordType() {
        return ResponsibilitySetStatusRecord.class;
    }

    /**
     * The column <code>public.responsibility_set_status.id</code>.
     */
    public final TableField<ResponsibilitySetStatusRecord, String> ID = createField("id", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.responsibility_set_status.name</code>.
     */
    public final TableField<ResponsibilitySetStatusRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * Create a <code>public.responsibility_set_status</code> table reference
     */
    public ResponsibilitySetStatus() {
        this(DSL.name("responsibility_set_status"), null);
    }

    /**
     * Create an aliased <code>public.responsibility_set_status</code> table reference
     */
    public ResponsibilitySetStatus(String alias) {
        this(DSL.name(alias), RESPONSIBILITY_SET_STATUS);
    }

    /**
     * Create an aliased <code>public.responsibility_set_status</code> table reference
     */
    public ResponsibilitySetStatus(Name alias) {
        this(alias, RESPONSIBILITY_SET_STATUS);
    }

    private ResponsibilitySetStatus(Name alias, Table<ResponsibilitySetStatusRecord> aliased) {
        this(alias, aliased, null);
    }

    private ResponsibilitySetStatus(Name alias, Table<ResponsibilitySetStatusRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "Possible values {incomplete, submitted, approved}");
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
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.RESPONSIBILITY_SET_STATUS_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ResponsibilitySetStatusRecord> getPrimaryKey() {
        return Keys.RESPONSIBILITY_SET_STATUS_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ResponsibilitySetStatusRecord>> getKeys() {
        return Arrays.<UniqueKey<ResponsibilitySetStatusRecord>>asList(Keys.RESPONSIBILITY_SET_STATUS_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponsibilitySetStatus as(String alias) {
        return new ResponsibilitySetStatus(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponsibilitySetStatus as(Name alias) {
        return new ResponsibilitySetStatus(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ResponsibilitySetStatus rename(String name) {
        return new ResponsibilitySetStatus(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ResponsibilitySetStatus rename(Name name) {
        return new ResponsibilitySetStatus(name, null);
    }
}
