/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables;


import org.jooq.*;
import org.jooq.impl.TableImpl;
import org.vaccineimpact.api.db.Keys;
import org.vaccineimpact.api.db.Public;
import org.vaccineimpact.api.db.tables.records.TouchstoneCountryRecord;

import javax.annotation.Generated;
import java.util.Arrays;
import java.util.List;


/**
 * This class is generated by jOOQ.
 */
@Generated(
        value = {
                "http://www.jooq.org",
                "jOOQ version:3.9.1"
        },
        comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class TouchstoneCountry extends TableImpl<TouchstoneCountryRecord> {

    private static final long serialVersionUID = 912244992;

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
     * The column <code>public.touchstone_country.who_region</code>.
     */
    public final TableField<TouchstoneCountryRecord, String> WHO_REGION = createField("who_region", org.jooq.impl.SQLDataType.CLOB.nullable(false).defaultValue(org.jooq.impl.DSL.field("'NULL'::text", org.jooq.impl.SQLDataType.CLOB)), this, "");

    /**
     * The column <code>public.touchstone_country.gavi73</code>.
     */
    public final TableField<TouchstoneCountryRecord, Boolean> GAVI73 = createField("gavi73", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * The column <code>public.touchstone_country.wuenic</code>.
     */
    public final TableField<TouchstoneCountryRecord, Boolean> WUENIC = createField("wuenic", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * Create a <code>public.touchstone_country</code> table reference
     */
    public TouchstoneCountry() {
        this("touchstone_country", null);
    }

    /**
     * Create an aliased <code>public.touchstone_country</code> table reference
     */
    public TouchstoneCountry(String alias) {
        this(alias, TOUCHSTONE_COUNTRY);
    }

    private TouchstoneCountry(String alias, Table<TouchstoneCountryRecord> aliased) {
        this(alias, aliased, null);
    }

    private TouchstoneCountry(String alias, Table<TouchstoneCountryRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<ForeignKey<TouchstoneCountryRecord, ?>>asList(Keys.TOUCHSTONE_COUNTRY__TOUCHSTONE_COUNTRY_TOUCHSTONE_FKEY, Keys.TOUCHSTONE_COUNTRY__TOUCHSTONE_COUNTRY_COUNTRY_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TouchstoneCountry as(String alias) {
        return new TouchstoneCountry(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public TouchstoneCountry rename(String name) {
        return new TouchstoneCountry(name, null);
    }
}
