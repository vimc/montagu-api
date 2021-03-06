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
import org.vaccineimpact.api.db.tables.records.TouchstoneDemographicSourceRecord;


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
public class TouchstoneDemographicSource extends TableImpl<TouchstoneDemographicSourceRecord> {

    private static final long serialVersionUID = -159749964;

    /**
     * The reference instance of <code>public.touchstone_demographic_source</code>
     */
    public static final TouchstoneDemographicSource TOUCHSTONE_DEMOGRAPHIC_SOURCE = new TouchstoneDemographicSource();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TouchstoneDemographicSourceRecord> getRecordType() {
        return TouchstoneDemographicSourceRecord.class;
    }

    /**
     * The column <code>public.touchstone_demographic_source.id</code>.
     */
    public final TableField<TouchstoneDemographicSourceRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('touchstone_demographic_source_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.touchstone_demographic_source.touchstone</code>.
     */
    public final TableField<TouchstoneDemographicSourceRecord, String> TOUCHSTONE = createField("touchstone", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.touchstone_demographic_source.demographic_source</code>.
     */
    public final TableField<TouchstoneDemographicSourceRecord, Integer> DEMOGRAPHIC_SOURCE = createField("demographic_source", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>public.touchstone_demographic_source</code> table reference
     */
    public TouchstoneDemographicSource() {
        this(DSL.name("touchstone_demographic_source"), null);
    }

    /**
     * Create an aliased <code>public.touchstone_demographic_source</code> table reference
     */
    public TouchstoneDemographicSource(String alias) {
        this(DSL.name(alias), TOUCHSTONE_DEMOGRAPHIC_SOURCE);
    }

    /**
     * Create an aliased <code>public.touchstone_demographic_source</code> table reference
     */
    public TouchstoneDemographicSource(Name alias) {
        this(alias, TOUCHSTONE_DEMOGRAPHIC_SOURCE);
    }

    private TouchstoneDemographicSource(Name alias, Table<TouchstoneDemographicSourceRecord> aliased) {
        this(alias, aliased, null);
    }

    private TouchstoneDemographicSource(Name alias, Table<TouchstoneDemographicSourceRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.TOUCHSTONE_DEMOGRAPHIC_SOURCE_PKEY, Indexes.TOUCHSTONE_DEMOGRAPHIC_SOURCE_TOUCHSTONE_IDX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<TouchstoneDemographicSourceRecord, Integer> getIdentity() {
        return Keys.IDENTITY_TOUCHSTONE_DEMOGRAPHIC_SOURCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TouchstoneDemographicSourceRecord> getPrimaryKey() {
        return Keys.TOUCHSTONE_DEMOGRAPHIC_SOURCE_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TouchstoneDemographicSourceRecord>> getKeys() {
        return Arrays.<UniqueKey<TouchstoneDemographicSourceRecord>>asList(Keys.TOUCHSTONE_DEMOGRAPHIC_SOURCE_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TouchstoneDemographicSourceRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TouchstoneDemographicSourceRecord, ?>>asList(Keys.TOUCHSTONE_DEMOGRAPHIC_SOURCE__TOUCHSTONE_DEMOGRAPHIC_SOURCE_TOUCHSTONE_FKEY, Keys.TOUCHSTONE_DEMOGRAPHIC_SOURCE__TOUCHSTONE_DEMOGRAPHIC_SOURCE_DEMOGRAPHIC_SOURCE_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TouchstoneDemographicSource as(String alias) {
        return new TouchstoneDemographicSource(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TouchstoneDemographicSource as(Name alias) {
        return new TouchstoneDemographicSource(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public TouchstoneDemographicSource rename(String name) {
        return new TouchstoneDemographicSource(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public TouchstoneDemographicSource rename(Name name) {
        return new TouchstoneDemographicSource(name, null);
    }
}
