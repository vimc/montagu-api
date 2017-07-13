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
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;
import org.vaccineimpact.api.db.Keys;
import org.vaccineimpact.api.db.Public;
import org.vaccineimpact.api.db.tables.records.TouchstoneYearsRecord;


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
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TouchstoneYears extends TableImpl<TouchstoneYearsRecord> {

    private static final long serialVersionUID = -1559219544;

    /**
     * The reference instance of <code>public.touchstone_years</code>
     */
    public static final TouchstoneYears TOUCHSTONE_YEARS = new TouchstoneYears();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TouchstoneYearsRecord> getRecordType() {
        return TouchstoneYearsRecord.class;
    }

    /**
     * The column <code>public.touchstone_years.id</code>.
     */
    public final TableField<TouchstoneYearsRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('touchstone_years_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.touchstone_years.touchstone</code>.
     */
    public final TableField<TouchstoneYearsRecord, String> TOUCHSTONE = createField("touchstone", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.touchstone_years.disease</code>.
     */
    public final TableField<TouchstoneYearsRecord, String> DISEASE = createField("disease", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.touchstone_years.year_first</code>.
     */
    public final TableField<TouchstoneYearsRecord, Integer> YEAR_FIRST = createField("year_first", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.touchstone_years.year_last</code>.
     */
    public final TableField<TouchstoneYearsRecord, Integer> YEAR_LAST = createField("year_last", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>public.touchstone_years</code> table reference
     */
    public TouchstoneYears() {
        this("touchstone_years", null);
    }

    /**
     * Create an aliased <code>public.touchstone_years</code> table reference
     */
    public TouchstoneYears(String alias) {
        this(alias, TOUCHSTONE_YEARS);
    }

    private TouchstoneYears(String alias, Table<TouchstoneYearsRecord> aliased) {
        this(alias, aliased, null);
    }

    private TouchstoneYears(String alias, Table<TouchstoneYearsRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TouchstoneYearsRecord, Integer> getIdentity() {
        return Keys.IDENTITY_TOUCHSTONE_YEARS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TouchstoneYearsRecord> getPrimaryKey() {
        return Keys.TOUCHSTONE_YEARS_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TouchstoneYearsRecord>> getKeys() {
        return Arrays.<UniqueKey<TouchstoneYearsRecord>>asList(Keys.TOUCHSTONE_YEARS_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TouchstoneYearsRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TouchstoneYearsRecord, ?>>asList(Keys.TOUCHSTONE_YEARS__TOUCHSTONE_YEARS_TOUCHSTONE_FKEY, Keys.TOUCHSTONE_YEARS__TOUCHSTONE_YEARS_DISEASE_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TouchstoneYears as(String alias) {
        return new TouchstoneYears(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public TouchstoneYears rename(String name) {
        return new TouchstoneYears(name, null);
    }
}
