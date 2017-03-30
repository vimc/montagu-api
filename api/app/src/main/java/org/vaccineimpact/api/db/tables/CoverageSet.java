/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables;


import org.jooq.*;
import org.jooq.impl.TableImpl;
import org.vaccineimpact.api.db.Keys;
import org.vaccineimpact.api.db.Public;
import org.vaccineimpact.api.db.tables.records.CoverageSetRecord;

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
public class CoverageSet extends TableImpl<CoverageSetRecord> {

    private static final long serialVersionUID = 931006319;

    /**
     * The reference instance of <code>public.coverage_set</code>
     */
    public static final CoverageSet COVERAGE_SET = new CoverageSet();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CoverageSetRecord> getRecordType() {
        return CoverageSetRecord.class;
    }

    /**
     * The column <code>public.coverage_set.id</code>.
     */
    public final TableField<CoverageSetRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('coverage_set_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.coverage_set.name</code>.
     */
    public final TableField<CoverageSetRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.coverage_set.touchstone</code>.
     */
    public final TableField<CoverageSetRecord, String> TOUCHSTONE = createField("touchstone", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.coverage_set.vaccine</code>.
     */
    public final TableField<CoverageSetRecord, String> VACCINE = createField("vaccine", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.coverage_set.vaccination_level</code>.
     */
    public final TableField<CoverageSetRecord, String> VACCINATION_LEVEL = createField("vaccination_level", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.coverage_set.scenario_type</code>.
     */
    public final TableField<CoverageSetRecord, String> SCENARIO_TYPE = createField("scenario_type", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * Create a <code>public.coverage_set</code> table reference
     */
    public CoverageSet() {
        this("coverage_set", null);
    }

    /**
     * Create an aliased <code>public.coverage_set</code> table reference
     */
    public CoverageSet(String alias) {
        this(alias, COVERAGE_SET);
    }

    private CoverageSet(String alias, Table<CoverageSetRecord> aliased) {
        this(alias, aliased, null);
    }

    private CoverageSet(String alias, Table<CoverageSetRecord> aliased, Field<?>[] parameters) {
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
    public Identity<CoverageSetRecord, Integer> getIdentity() {
        return Keys.IDENTITY_COVERAGE_SET;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<CoverageSetRecord> getPrimaryKey() {
        return Keys.COVERAGE_SET_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<CoverageSetRecord>> getKeys() {
        return Arrays.<UniqueKey<CoverageSetRecord>>asList(Keys.COVERAGE_SET_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<CoverageSetRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<CoverageSetRecord, ?>>asList(Keys.COVERAGE_SET__COVERAGE_SET_TOUCHSTONE_FKEY, Keys.COVERAGE_SET__COVERAGE_SET_VACCINE_FKEY, Keys.COVERAGE_SET__COVERAGE_SET_VACCINATION_LEVEL_FKEY, Keys.COVERAGE_SET__COVERAGE_SET_SCENARIO_TYPE_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CoverageSet as(String alias) {
        return new CoverageSet(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public CoverageSet rename(String name) {
        return new CoverageSet(name, null);
    }
}
