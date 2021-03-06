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
import org.vaccineimpact.api.db.tables.records.DemographicDatasetRecord;


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
public class DemographicDataset extends TableImpl<DemographicDatasetRecord> {

    private static final long serialVersionUID = -407441789;

    /**
     * The reference instance of <code>public.demographic_dataset</code>
     */
    public static final DemographicDataset DEMOGRAPHIC_DATASET = new DemographicDataset();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DemographicDatasetRecord> getRecordType() {
        return DemographicDatasetRecord.class;
    }

    /**
     * The column <code>public.demographic_dataset.id</code>.
     */
    public final TableField<DemographicDatasetRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('demographic_dataset_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.demographic_dataset.description</code>.
     */
    public final TableField<DemographicDatasetRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.demographic_dataset.demographic_source</code>.
     */
    public final TableField<DemographicDatasetRecord, Integer> DEMOGRAPHIC_SOURCE = createField("demographic_source", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.demographic_dataset.demographic_statistic_type</code>.
     */
    public final TableField<DemographicDatasetRecord, Integer> DEMOGRAPHIC_STATISTIC_TYPE = createField("demographic_statistic_type", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>public.demographic_dataset</code> table reference
     */
    public DemographicDataset() {
        this(DSL.name("demographic_dataset"), null);
    }

    /**
     * Create an aliased <code>public.demographic_dataset</code> table reference
     */
    public DemographicDataset(String alias) {
        this(DSL.name(alias), DEMOGRAPHIC_DATASET);
    }

    /**
     * Create an aliased <code>public.demographic_dataset</code> table reference
     */
    public DemographicDataset(Name alias) {
        this(alias, DEMOGRAPHIC_DATASET);
    }

    private DemographicDataset(Name alias, Table<DemographicDatasetRecord> aliased) {
        this(alias, aliased, null);
    }

    private DemographicDataset(Name alias, Table<DemographicDatasetRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.DEMOGRAPHIC_DATASET_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<DemographicDatasetRecord, Integer> getIdentity() {
        return Keys.IDENTITY_DEMOGRAPHIC_DATASET;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<DemographicDatasetRecord> getPrimaryKey() {
        return Keys.DEMOGRAPHIC_DATASET_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<DemographicDatasetRecord>> getKeys() {
        return Arrays.<UniqueKey<DemographicDatasetRecord>>asList(Keys.DEMOGRAPHIC_DATASET_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<DemographicDatasetRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<DemographicDatasetRecord, ?>>asList(Keys.DEMOGRAPHIC_DATASET__DEMOGRAPHIC_DATASET_DEMOGRAPHIC_SOURCE_FKEY, Keys.DEMOGRAPHIC_DATASET__DEMOGRAPHIC_DATASET_DEMOGRAPHIC_STATISTIC_TYPE_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DemographicDataset as(String alias) {
        return new DemographicDataset(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DemographicDataset as(Name alias) {
        return new DemographicDataset(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public DemographicDataset rename(String name) {
        return new DemographicDataset(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public DemographicDataset rename(Name name) {
        return new DemographicDataset(name, null);
    }
}
