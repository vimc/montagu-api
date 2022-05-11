/*
 * This file is generated by jOOQ.
 */
package org.vaccineimpact.api.db.tables;


import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row4;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.vaccineimpact.api.db.Keys;
import org.vaccineimpact.api.db.Public;
import org.vaccineimpact.api.db.tables.records.ResponsibilitySetRecord;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ResponsibilitySet extends TableImpl<ResponsibilitySetRecord> {

    private static final long serialVersionUID = 1L;

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
    public final TableField<ResponsibilitySetRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.responsibility_set.modelling_group</code>.
     */
    public final TableField<ResponsibilitySetRecord, String> MODELLING_GROUP = createField(DSL.name("modelling_group"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.responsibility_set.touchstone</code>.
     */
    public final TableField<ResponsibilitySetRecord, String> TOUCHSTONE = createField(DSL.name("touchstone"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.responsibility_set.status</code>.
     */
    public final TableField<ResponsibilitySetRecord, String> STATUS = createField(DSL.name("status"), SQLDataType.CLOB.nullable(false), this, "");

    private ResponsibilitySet(Name alias, Table<ResponsibilitySetRecord> aliased) {
        this(alias, aliased, null);
    }

    private ResponsibilitySet(Name alias, Table<ResponsibilitySetRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
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

    /**
     * Create a <code>public.responsibility_set</code> table reference
     */
    public ResponsibilitySet() {
        this(DSL.name("responsibility_set"), null);
    }

    public <O extends Record> ResponsibilitySet(Table<O> child, ForeignKey<O, ResponsibilitySetRecord> key) {
        super(child, key, RESPONSIBILITY_SET);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public Identity<ResponsibilitySetRecord, Integer> getIdentity() {
        return (Identity<ResponsibilitySetRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<ResponsibilitySetRecord> getPrimaryKey() {
        return Keys.RESPONSIBILITY_SET_PKEY;
    }

    @Override
    public List<UniqueKey<ResponsibilitySetRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.RESPONSIBILITY_SET_MODELLING_GROUP_TOUCHSTONE_KEY);
    }

    @Override
    public List<ForeignKey<ResponsibilitySetRecord, ?>> getReferences() {
        return Arrays.asList(Keys.RESPONSIBILITY_SET__RESPONSIBILITY_SET_MODELLING_GROUP_FKEY, Keys.RESPONSIBILITY_SET__RESPONSIBILITY_SET_TOUCHSTONE_FKEY, Keys.RESPONSIBILITY_SET__RESPONSIBILITY_SET_STATUS_FKEY);
    }

    private transient ModellingGroup _modellingGroup;
    private transient Touchstone _touchstone;
    private transient ResponsibilitySetStatus _responsibilitySetStatus;

    /**
     * Get the implicit join path to the <code>public.modelling_group</code>
     * table.
     */
    public ModellingGroup modellingGroup() {
        if (_modellingGroup == null)
            _modellingGroup = new ModellingGroup(this, Keys.RESPONSIBILITY_SET__RESPONSIBILITY_SET_MODELLING_GROUP_FKEY);

        return _modellingGroup;
    }

    /**
     * Get the implicit join path to the <code>public.touchstone</code> table.
     */
    public Touchstone touchstone() {
        if (_touchstone == null)
            _touchstone = new Touchstone(this, Keys.RESPONSIBILITY_SET__RESPONSIBILITY_SET_TOUCHSTONE_FKEY);

        return _touchstone;
    }

    /**
     * Get the implicit join path to the
     * <code>public.responsibility_set_status</code> table.
     */
    public ResponsibilitySetStatus responsibilitySetStatus() {
        if (_responsibilitySetStatus == null)
            _responsibilitySetStatus = new ResponsibilitySetStatus(this, Keys.RESPONSIBILITY_SET__RESPONSIBILITY_SET_STATUS_FKEY);

        return _responsibilitySetStatus;
    }

    @Override
    public ResponsibilitySet as(String alias) {
        return new ResponsibilitySet(DSL.name(alias), this);
    }

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

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<Integer, String, String, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}
