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
import org.vaccineimpact.api.db.tables.records.TouchstoneCountryRecord;


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
public class TouchstoneCountry extends TableImpl<TouchstoneCountryRecord> {

    private static final long serialVersionUID = 1978149719;

    /**
     * The reference instance of <code>public.touchstone_country</code>
     */
    public static final TouchstoneCountry TOUCHSTONE_COUNTRY = new TouchstoneCountry();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TouchstoneCountryRecord> getRecordType() {
        return TouchstoneCountryRecord.class;
    }

    /**
     * The column <code>public.touchstone_country.id</code>.
     */
    public final TableField<TouchstoneCountryRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('touchstone_country_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.touchstone_country.touchstone</code>.
     */
    public final TableField<TouchstoneCountryRecord, String> TOUCHSTONE = createField("touchstone", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.touchstone_country.country</code>.
     */
    public final TableField<TouchstoneCountryRecord, String> COUNTRY = createField("country", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.touchstone_country.disease</code>.
     */
    public final TableField<TouchstoneCountryRecord, String> DISEASE = createField("disease", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * Create a <code>public.touchstone_country</code> table reference
     */
    public TouchstoneCountry() {
        this(DSL.name("touchstone_country"), null);
    }

    /**
     * Create an aliased <code>public.touchstone_country</code> table reference
     */
    public TouchstoneCountry(String alias) {
        this(DSL.name(alias), TOUCHSTONE_COUNTRY);
    }

    /**
     * Create an aliased <code>public.touchstone_country</code> table reference
     */
    public TouchstoneCountry(Name alias) {
        this(alias, TOUCHSTONE_COUNTRY);
    }

    private TouchstoneCountry(Name alias, Table<TouchstoneCountryRecord> aliased) {
        this(alias, aliased, null);
    }

    private TouchstoneCountry(Name alias, Table<TouchstoneCountryRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.TOUCHSTONE_COUNTRY_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<TouchstoneCountryRecord, Integer> getIdentity() {
        return Keys.IDENTITY_TOUCHSTONE_COUNTRY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TouchstoneCountryRecord> getPrimaryKey() {
        return Keys.TOUCHSTONE_COUNTRY_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TouchstoneCountryRecord>> getKeys() {
        return Arrays.<UniqueKey<TouchstoneCountryRecord>>asList(Keys.TOUCHSTONE_COUNTRY_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TouchstoneCountryRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TouchstoneCountryRecord, ?>>asList(Keys.TOUCHSTONE_COUNTRY__TOUCHSTONE_COUNTRY_TOUCHSTONE_FKEY, Keys.TOUCHSTONE_COUNTRY__TOUCHSTONE_COUNTRY_COUNTRY_FKEY, Keys.TOUCHSTONE_COUNTRY__TOUCHSTONE_COUNTRY_DISEASE_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TouchstoneCountry as(String alias) {
        return new TouchstoneCountry(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TouchstoneCountry as(Name alias) {
        return new TouchstoneCountry(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public TouchstoneCountry rename(String name) {
        return new TouchstoneCountry(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public TouchstoneCountry rename(Name name) {
        return new TouchstoneCountry(name, null);
    }
}
