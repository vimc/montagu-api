/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables;


import java.sql.Date;
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
import org.vaccineimpact.api.db.tables.records.DemographicStatisticTypeRecord;


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
public class DemographicStatisticType extends TableImpl<DemographicStatisticTypeRecord> {

    private static final long serialVersionUID = -26294603;

    /**
     * The reference instance of <code>public.demographic_statistic_type</code>
     */
    public static final DemographicStatisticType DEMOGRAPHIC_STATISTIC_TYPE = new DemographicStatisticType();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DemographicStatisticTypeRecord> getRecordType() {
        return DemographicStatisticTypeRecord.class;
    }

    /**
     * The column <code>public.demographic_statistic_type.id</code>.
     */
    public final TableField<DemographicStatisticTypeRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('demographic_statistic_type_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.demographic_statistic_type.code</code>.
     */
    public final TableField<DemographicStatisticTypeRecord, String> CODE = createField("code", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.demographic_statistic_type.age_interpretation</code>.
     */
    public final TableField<DemographicStatisticTypeRecord, String> AGE_INTERPRETATION = createField("age_interpretation", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.demographic_statistic_type.name</code>.
     */
    public final TableField<DemographicStatisticTypeRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.demographic_statistic_type.year_step_size</code>.
     */
    public final TableField<DemographicStatisticTypeRecord, Integer> YEAR_STEP_SIZE = createField("year_step_size", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.demographic_statistic_type.reference_date</code>.
     */
    public final TableField<DemographicStatisticTypeRecord, Date> REFERENCE_DATE = createField("reference_date", org.jooq.impl.SQLDataType.DATE.nullable(false), this, "");

    /**
     * The column <code>public.demographic_statistic_type.gender_is_applicable</code>.
     */
    public final TableField<DemographicStatisticTypeRecord, Boolean> GENDER_IS_APPLICABLE = createField("gender_is_applicable", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * The column <code>public.demographic_statistic_type.demographic_value_unit</code>.
     */
    public final TableField<DemographicStatisticTypeRecord, Integer> DEMOGRAPHIC_VALUE_UNIT = createField("demographic_value_unit", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.demographic_statistic_type.default_variant</code>.
     */
    public final TableField<DemographicStatisticTypeRecord, Integer> DEFAULT_VARIANT = createField("default_variant", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>public.demographic_statistic_type</code> table reference
     */
    public DemographicStatisticType() {
        this(DSL.name("demographic_statistic_type"), null);
    }

    /**
     * Create an aliased <code>public.demographic_statistic_type</code> table reference
     */
    public DemographicStatisticType(String alias) {
        this(DSL.name(alias), DEMOGRAPHIC_STATISTIC_TYPE);
    }

    /**
     * Create an aliased <code>public.demographic_statistic_type</code> table reference
     */
    public DemographicStatisticType(Name alias) {
        this(alias, DEMOGRAPHIC_STATISTIC_TYPE);
    }

    private DemographicStatisticType(Name alias, Table<DemographicStatisticTypeRecord> aliased) {
        this(alias, aliased, null);
    }

    private DemographicStatisticType(Name alias, Table<DemographicStatisticTypeRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.DEMOGRAPHIC_STATISTIC_TYPE_PKEY, Indexes.DEMOGRAPHIC_STATISTIC_TYPE_UNIQUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<DemographicStatisticTypeRecord, Integer> getIdentity() {
        return Keys.IDENTITY_DEMOGRAPHIC_STATISTIC_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<DemographicStatisticTypeRecord> getPrimaryKey() {
        return Keys.DEMOGRAPHIC_STATISTIC_TYPE_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<DemographicStatisticTypeRecord>> getKeys() {
        return Arrays.<UniqueKey<DemographicStatisticTypeRecord>>asList(Keys.DEMOGRAPHIC_STATISTIC_TYPE_PKEY, Keys.DEMOGRAPHIC_STATISTIC_TYPE_UNIQUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<DemographicStatisticTypeRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<DemographicStatisticTypeRecord, ?>>asList(Keys.DEMOGRAPHIC_STATISTIC_TYPE__DEMOGRAPHIC_STATISTIC_TYPE_DEMOGRAPHIC_VALUE_UNIT_FKEY, Keys.DEMOGRAPHIC_STATISTIC_TYPE__DEMOGRAPHIC_STATISTIC_TYPE_DEFAULT_VARIANT_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DemographicStatisticType as(String alias) {
        return new DemographicStatisticType(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DemographicStatisticType as(Name alias) {
        return new DemographicStatisticType(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public DemographicStatisticType rename(String name) {
        return new DemographicStatisticType(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public DemographicStatisticType rename(Name name) {
        return new DemographicStatisticType(name, null);
    }
}
