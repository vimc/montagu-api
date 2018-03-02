/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables;


import java.math.BigDecimal;
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
import org.vaccineimpact.api.db.tables.records.CoverageRecord;


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
public class Coverage extends TableImpl<CoverageRecord> {

    private static final long serialVersionUID = -176369884;

    /**
     * The reference instance of <code>public.coverage</code>
     */
    public static final Coverage COVERAGE = new Coverage();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CoverageRecord> getRecordType() {
        return CoverageRecord.class;
    }

    /**
     * The column <code>public.coverage.id</code>.
     */
    public final TableField<CoverageRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('coverage_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.coverage.coverage_set</code>.
     */
    public final TableField<CoverageRecord, Integer> COVERAGE_SET = createField("coverage_set", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.coverage.year</code>.
     */
    public final TableField<CoverageRecord, Integer> YEAR = createField("year", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.coverage.country</code>.
     */
    public final TableField<CoverageRecord, String> COUNTRY = createField("country", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.coverage.age_from</code>.
     */
    public final TableField<CoverageRecord, BigDecimal> AGE_FROM = createField("age_from", org.jooq.impl.SQLDataType.NUMERIC.nullable(false), this, "");

    /**
     * The column <code>public.coverage.age_to</code>.
     */
    public final TableField<CoverageRecord, BigDecimal> AGE_TO = createField("age_to", org.jooq.impl.SQLDataType.NUMERIC.nullable(false), this, "");

    /**
     * The column <code>public.coverage.age_range_verbatim</code>.
     */
    public final TableField<CoverageRecord, String> AGE_RANGE_VERBATIM = createField("age_range_verbatim", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.coverage.coverage</code>.
     */
    public final TableField<CoverageRecord, BigDecimal> COVERAGE_ = createField("coverage", org.jooq.impl.SQLDataType.NUMERIC, this, "");

    /**
     * The column <code>public.coverage.target</code>. This field is valid only for campaign coverage
     */
    public final TableField<CoverageRecord, BigDecimal> TARGET = createField("target", org.jooq.impl.SQLDataType.NUMERIC, this, "This field is valid only for campaign coverage");

    /**
     * The column <code>public.coverage.gavi_support</code>.
     */
    public final TableField<CoverageRecord, Boolean> GAVI_SUPPORT = createField("gavi_support", org.jooq.impl.SQLDataType.BOOLEAN, this, "");

    /**
     * The column <code>public.coverage.gender</code>.
     */
    public final TableField<CoverageRecord, Integer> GENDER = createField("gender", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * Create a <code>public.coverage</code> table reference
     */
    public Coverage() {
        this(DSL.name("coverage"), null);
    }

    /**
     * Create an aliased <code>public.coverage</code> table reference
     */
    public Coverage(String alias) {
        this(DSL.name(alias), COVERAGE);
    }

    /**
     * Create an aliased <code>public.coverage</code> table reference
     */
    public Coverage(Name alias) {
        this(alias, COVERAGE);
    }

    private Coverage(Name alias, Table<CoverageRecord> aliased) {
        this(alias, aliased, null);
    }

    private Coverage(Name alias, Table<CoverageRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.COVERAGE_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<CoverageRecord, Integer> getIdentity() {
        return Keys.IDENTITY_COVERAGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<CoverageRecord> getPrimaryKey() {
        return Keys.COVERAGE_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<CoverageRecord>> getKeys() {
        return Arrays.<UniqueKey<CoverageRecord>>asList(Keys.COVERAGE_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<CoverageRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<CoverageRecord, ?>>asList(Keys.COVERAGE__COVERAGE_COVERAGE_SET_FKEY, Keys.COVERAGE__COVERAGE_COUNTRY_FKEY, Keys.COVERAGE__COVERAGE_GENDER_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Coverage as(String alias) {
        return new Coverage(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Coverage as(Name alias) {
        return new Coverage(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Coverage rename(String name) {
        return new Coverage(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Coverage rename(Name name) {
        return new Coverage(name, null);
    }
}
