/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
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
import org.vaccineimpact.api.db.tables.records.ResponsibilitySetRecord;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ResponsibilitySet extends TableImpl<ResponsibilitySetRecord> {

    private static final long serialVersionUID = 48363494;

    /**
     * The reference instance of <code>public.responsibility_set</code>
     */
    public static final ResponsibilitySet RESPONSIBILITY_SET = new ResponsibilitySet();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ResponsibilitySetRecord> getRecordType() {
        return ResponsibilitySetRecord.class;
    }

    /**
     * The column <code>public.responsibility_set.id</code>.
     */
    public final TableField<ResponsibilitySetRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('responsibility_set_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.responsibility_set.modelling_group</code>.
     */
    public final TableField<ResponsibilitySetRecord, String> MODELLING_GROUP = createField("modelling_group", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.responsibility_set.touchstone</code>.
     */
    public final TableField<ResponsibilitySetRecord, String> TOUCHSTONE = createField("touchstone", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.responsibility_set.status</code>.
     */
    public final TableField<ResponsibilitySetRecord, String> STATUS = createField("status", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * Create a <code>public.responsibility_set</code> table reference
     */
    public ResponsibilitySet() {
        this(DSL.name("responsibility_set"), null);
    }

    /**
     * Create an aliased <code>public.responsibility_set</code> table reference
     */
    public ResponsibilitySet(String alias) {
        this(DSL.name(alias), RESPONSIBILITY_SET);
    }

    /**
     * Create an aliased <code>public.responsibility_set</code> table reference
     */
    public ResponsibilitySet(Name alias) {
        this(alias, RESPONSIBILITY_SET);
    }

    private ResponsibilitySet(Name alias, Table<ResponsibilitySetRecord> aliased) {
        this(alias, aliased, null);
    }

    private ResponsibilitySet(Name alias, Table<ResponsibilitySetRecord> aliased, Field<?>[] parameters) {
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
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.RESPONSIBILITY_SET_MODELLING_GROUP_TOUCHSTONE_KEY, Indexes.RESPONSIBILITY_SET_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ResponsibilitySetRecord, Integer> getIdentity() {
        return Keys.IDENTITY_RESPONSIBILITY_SET;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ResponsibilitySetRecord> getPrimaryKey() {
        return Keys.RESPONSIBILITY_SET_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ResponsibilitySetRecord>> getKeys() {
        return Arrays.<UniqueKey<ResponsibilitySetRecord>>asList(Keys.RESPONSIBILITY_SET_PKEY, Keys.RESPONSIBILITY_SET_MODELLING_GROUP_TOUCHSTONE_KEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<ResponsibilitySetRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ResponsibilitySetRecord, ?>>asList(Keys.RESPONSIBILITY_SET__RESPONSIBILITY_SET_MODELLING_GROUP_FKEY, Keys.RESPONSIBILITY_SET__RESPONSIBILITY_SET_TOUCHSTONE_FKEY, Keys.RESPONSIBILITY_SET__RESPONSIBILITY_SET_STATUS_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponsibilitySet as(String alias) {
        return new ResponsibilitySet(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponsibilitySet as(Name alias) {
        return new ResponsibilitySet(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ResponsibilitySet rename(String name) {
        return new ResponsibilitySet(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ResponsibilitySet rename(Name name) {
        return new ResponsibilitySet(name, null);
    }
}
