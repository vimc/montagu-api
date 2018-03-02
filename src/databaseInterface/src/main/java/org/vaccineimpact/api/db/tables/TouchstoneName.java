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
import org.vaccineimpact.api.db.tables.records.TouchstoneNameRecord;


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
public class TouchstoneName extends TableImpl<TouchstoneNameRecord> {

    private static final long serialVersionUID = -40999454;

    /**
     * The reference instance of <code>public.touchstone_name</code>
     */
    public static final TouchstoneName TOUCHSTONE_NAME = new TouchstoneName();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TouchstoneNameRecord> getRecordType() {
        return TouchstoneNameRecord.class;
    }

    /**
     * The column <code>public.touchstone_name.id</code>.
     */
    public final TableField<TouchstoneNameRecord, String> ID = createField("id", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.touchstone_name.description</code>.
     */
    public final TableField<TouchstoneNameRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.touchstone_name.comment</code>.
     */
    public final TableField<TouchstoneNameRecord, String> COMMENT = createField("comment", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * Create a <code>public.touchstone_name</code> table reference
     */
    public TouchstoneName() {
        this(DSL.name("touchstone_name"), null);
    }

    /**
     * Create an aliased <code>public.touchstone_name</code> table reference
     */
    public TouchstoneName(String alias) {
        this(DSL.name(alias), TOUCHSTONE_NAME);
    }

    /**
     * Create an aliased <code>public.touchstone_name</code> table reference
     */
    public TouchstoneName(Name alias) {
        this(alias, TOUCHSTONE_NAME);
    }

    private TouchstoneName(Name alias, Table<TouchstoneNameRecord> aliased) {
        this(alias, aliased, null);
    }

    private TouchstoneName(Name alias, Table<TouchstoneNameRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.TOUCHSTONE_NAME_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TouchstoneNameRecord> getPrimaryKey() {
        return Keys.TOUCHSTONE_NAME_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TouchstoneNameRecord>> getKeys() {
        return Arrays.<UniqueKey<TouchstoneNameRecord>>asList(Keys.TOUCHSTONE_NAME_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TouchstoneName as(String alias) {
        return new TouchstoneName(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TouchstoneName as(Name alias) {
        return new TouchstoneName(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public TouchstoneName rename(String name) {
        return new TouchstoneName(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public TouchstoneName rename(Name name) {
        return new TouchstoneName(name, null);
    }
}
