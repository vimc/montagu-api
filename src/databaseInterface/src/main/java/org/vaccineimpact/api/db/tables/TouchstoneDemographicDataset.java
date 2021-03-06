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
import org.vaccineimpact.api.db.tables.records.TouchstoneDemographicDatasetRecord;


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
public class TouchstoneDemographicDataset extends TableImpl<TouchstoneDemographicDatasetRecord> {

    private static final long serialVersionUID = -1120849796;

    /**
     * The reference instance of <code>public.touchstone_demographic_dataset</code>
     */
    public static final TouchstoneDemographicDataset TOUCHSTONE_DEMOGRAPHIC_DATASET = new TouchstoneDemographicDataset();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TouchstoneDemographicDatasetRecord> getRecordType() {
        return TouchstoneDemographicDatasetRecord.class;
    }

    /**
     * The column <code>public.touchstone_demographic_dataset.id</code>.
     */
    public final TableField<TouchstoneDemographicDatasetRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('touchstone_demographic_dataset_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.touchstone_demographic_dataset.touchstone</code>.
     */
    public final TableField<TouchstoneDemographicDatasetRecord, String> TOUCHSTONE = createField("touchstone", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.touchstone_demographic_dataset.demographic_dataset</code>.
     */
    public final TableField<TouchstoneDemographicDatasetRecord, Integer> DEMOGRAPHIC_DATASET = createField("demographic_dataset", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>public.touchstone_demographic_dataset</code> table reference
     */
    public TouchstoneDemographicDataset() {
        this(DSL.name("touchstone_demographic_dataset"), null);
    }

    /**
     * Create an aliased <code>public.touchstone_demographic_dataset</code> table reference
     */
    public TouchstoneDemographicDataset(String alias) {
        this(DSL.name(alias), TOUCHSTONE_DEMOGRAPHIC_DATASET);
    }

    /**
     * Create an aliased <code>public.touchstone_demographic_dataset</code> table reference
     */
    public TouchstoneDemographicDataset(Name alias) {
        this(alias, TOUCHSTONE_DEMOGRAPHIC_DATASET);
    }

    private TouchstoneDemographicDataset(Name alias, Table<TouchstoneDemographicDatasetRecord> aliased) {
        this(alias, aliased, null);
    }

    private TouchstoneDemographicDataset(Name alias, Table<TouchstoneDemographicDatasetRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.TOUCHSTONE_DEMOGRAPHIC_DATASET_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<TouchstoneDemographicDatasetRecord, Integer> getIdentity() {
        return Keys.IDENTITY_TOUCHSTONE_DEMOGRAPHIC_DATASET;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TouchstoneDemographicDatasetRecord> getPrimaryKey() {
        return Keys.TOUCHSTONE_DEMOGRAPHIC_DATASET_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TouchstoneDemographicDatasetRecord>> getKeys() {
        return Arrays.<UniqueKey<TouchstoneDemographicDatasetRecord>>asList(Keys.TOUCHSTONE_DEMOGRAPHIC_DATASET_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TouchstoneDemographicDatasetRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TouchstoneDemographicDatasetRecord, ?>>asList(Keys.TOUCHSTONE_DEMOGRAPHIC_DATASET__TOUCHSTONE_DEMOGRAPHIC_DATASET_TOUCHSTONE_FKEY, Keys.TOUCHSTONE_DEMOGRAPHIC_DATASET__TOUCHSTONE_DEMOGRAPHIC_DATASET_DEMOGRAPHIC_DATASET_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TouchstoneDemographicDataset as(String alias) {
        return new TouchstoneDemographicDataset(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TouchstoneDemographicDataset as(Name alias) {
        return new TouchstoneDemographicDataset(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public TouchstoneDemographicDataset rename(String name) {
        return new TouchstoneDemographicDataset(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public TouchstoneDemographicDataset rename(Name name) {
        return new TouchstoneDemographicDataset(name, null);
    }
}
