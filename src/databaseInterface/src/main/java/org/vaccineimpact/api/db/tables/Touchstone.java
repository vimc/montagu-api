/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;
import org.vaccineimpact.api.db.Keys;
import org.vaccineimpact.api.db.Public;
import org.vaccineimpact.api.db.tables.records.TouchstoneRecord;


/**
 * This is the top-level categorization. It refers to an Operational Forecast 
 * from GAVI, a WUENIC July update, or some other data set against which impact 
 * estimates are going to be done 
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Touchstone extends TableImpl<TouchstoneRecord> {

    private static final long serialVersionUID = 24754719;

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
    public final TableField<TouchstoneRecord, String> ID = createField("id", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.touchstone.touchstone_name</code>.
     */
    public final TableField<TouchstoneRecord, String> TOUCHSTONE_NAME = createField("touchstone_name", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.touchstone.version</code>.
     */
    public final TableField<TouchstoneRecord, Integer> VERSION = createField("version", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.touchstone.status</code>.
     */
    public final TableField<TouchstoneRecord, String> STATUS = createField("status", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.touchstone.year_start</code>.
     */
    public final TableField<TouchstoneRecord, Integer> YEAR_START = createField("year_start", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.touchstone.year_end</code>.
     */
    public final TableField<TouchstoneRecord, Integer> YEAR_END = createField("year_end", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>public.touchstone</code> table reference
     */
    public Touchstone() {
        this("touchstone", null);
    }

    /**
     * Create an aliased <code>public.touchstone</code> table reference
     */
    public Touchstone(String alias) {
        this(alias, TOUCHSTONE);
    }

    private Touchstone(String alias, Table<TouchstoneRecord> aliased) {
        this(alias, aliased, null);
    }

    private Touchstone(String alias, Table<TouchstoneRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "This is the top-level categorization. It refers to an Operational Forecast from GAVI, a WUENIC July update, or some other data set against which impact estimates are going to be done ");
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
    public UniqueKey<TouchstoneRecord> getPrimaryKey() {
        return Keys.TOUCHSTONE_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TouchstoneRecord>> getKeys() {
        return Arrays.<UniqueKey<TouchstoneRecord>>asList(Keys.TOUCHSTONE_PKEY, Keys.TOUCHSTONE_TOUCHSTONE_NAME_VERSION_KEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TouchstoneRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TouchstoneRecord, ?>>asList(Keys.TOUCHSTONE__TOUCHSTONE_TOUCHSTONE_NAME_FKEY, Keys.TOUCHSTONE__TOUCHSTONE_STATUS_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Touchstone as(String alias) {
        return new Touchstone(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Touchstone rename(String name) {
        return new Touchstone(name, null);
    }
}
