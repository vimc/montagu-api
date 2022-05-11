/*
 * This file is generated by jOOQ.
 */
package org.vaccineimpact.api.db.tables;


import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row6;
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
import org.vaccineimpact.api.db.tables.records.TouchstoneRecord;


/**
 * This is the top-level categorization. It refers to an Operational Forecast
 * from GAVI, a WUENIC July update, or some other data set against which impact
 * estimates are going to be done 
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Touchstone extends TableImpl<TouchstoneRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.touchstone</code>
     */
    public static final Touchstone TOUCHSTONE = new Touchstone();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TouchstoneRecord> getRecordType() {
        return TouchstoneRecord.class;
    }

    /**
     * The column <code>public.touchstone.id</code>.
     */
    public final TableField<TouchstoneRecord, String> ID = createField(DSL.name("id"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.touchstone.touchstone_name</code>.
     */
    public final TableField<TouchstoneRecord, String> TOUCHSTONE_NAME = createField(DSL.name("touchstone_name"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.touchstone.version</code>.
     */
    public final TableField<TouchstoneRecord, Integer> VERSION = createField(DSL.name("version"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.touchstone.description</code>.
     */
    public final TableField<TouchstoneRecord, String> DESCRIPTION = createField(DSL.name("description"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.touchstone.status</code>.
     */
    public final TableField<TouchstoneRecord, String> STATUS = createField(DSL.name("status"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.touchstone.comment</code>.
     */
    public final TableField<TouchstoneRecord, String> COMMENT = createField(DSL.name("comment"), SQLDataType.CLOB.nullable(false), this, "");

    private Touchstone(Name alias, Table<TouchstoneRecord> aliased) {
        this(alias, aliased, null);
    }

    private Touchstone(Name alias, Table<TouchstoneRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("This is the top-level categorization. It refers to an Operational Forecast from GAVI, a WUENIC July update, or some other data set against which impact estimates are going to be done "), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.touchstone</code> table reference
     */
    public Touchstone(String alias) {
        this(DSL.name(alias), TOUCHSTONE);
    }

    /**
     * Create an aliased <code>public.touchstone</code> table reference
     */
    public Touchstone(Name alias) {
        this(alias, TOUCHSTONE);
    }

    /**
     * Create a <code>public.touchstone</code> table reference
     */
    public Touchstone() {
        this(DSL.name("touchstone"), null);
    }

    public <O extends Record> Touchstone(Table<O> child, ForeignKey<O, TouchstoneRecord> key) {
        super(child, key, TOUCHSTONE);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<TouchstoneRecord> getPrimaryKey() {
        return Keys.TOUCHSTONE_PKEY;
    }

    @Override
    public List<UniqueKey<TouchstoneRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.TOUCHSTONE_TOUCHSTONE_NAME_VERSION_KEY);
    }

    @Override
    public List<ForeignKey<TouchstoneRecord, ?>> getReferences() {
        return Arrays.asList(Keys.TOUCHSTONE__TOUCHSTONE_TOUCHSTONE_NAME_FKEY, Keys.TOUCHSTONE__TOUCHSTONE_STATUS_FKEY);
    }

    private transient TouchstoneName _touchstoneName;
    private transient TouchstoneStatus _touchstoneStatus;

    /**
     * Get the implicit join path to the <code>public.touchstone_name</code>
     * table.
     */
    public TouchstoneName touchstoneName() {
        if (_touchstoneName == null)
            _touchstoneName = new TouchstoneName(this, Keys.TOUCHSTONE__TOUCHSTONE_TOUCHSTONE_NAME_FKEY);

        return _touchstoneName;
    }

    /**
     * Get the implicit join path to the <code>public.touchstone_status</code>
     * table.
     */
    public TouchstoneStatus touchstoneStatus() {
        if (_touchstoneStatus == null)
            _touchstoneStatus = new TouchstoneStatus(this, Keys.TOUCHSTONE__TOUCHSTONE_STATUS_FKEY);

        return _touchstoneStatus;
    }

    @Override
    public Touchstone as(String alias) {
        return new Touchstone(DSL.name(alias), this);
    }

    @Override
    public Touchstone as(Name alias) {
        return new Touchstone(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Touchstone rename(String name) {
        return new Touchstone(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Touchstone rename(Name name) {
        return new Touchstone(name, null);
    }

    // -------------------------------------------------------------------------
    // Row6 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row6<String, String, Integer, String, String, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }
}
