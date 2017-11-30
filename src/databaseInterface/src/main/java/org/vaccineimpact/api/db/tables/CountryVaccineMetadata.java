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
import org.vaccineimpact.api.db.tables.records.CountryVaccineMetadataRecord;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CountryVaccineMetadata extends TableImpl<CountryVaccineMetadataRecord> {

    private static final long serialVersionUID = -1410073488;

    /**
     * The reference instance of <code>public.country_vaccine_metadata</code>
     */
    public static final CountryVaccineMetadata COUNTRY_VACCINE_METADATA = new CountryVaccineMetadata();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CountryVaccineMetadataRecord> getRecordType() {
        return CountryVaccineMetadataRecord.class;
    }

    /**
     * The column <code>public.country_vaccine_metadata.id</code>.
     */
    public final TableField<CountryVaccineMetadataRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('country_vaccine_metadata_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.country_vaccine_metadata.touchstone</code>.
     */
    public final TableField<CountryVaccineMetadataRecord, String> TOUCHSTONE = createField("touchstone", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.country_vaccine_metadata.country</code>.
     */
    public final TableField<CountryVaccineMetadataRecord, String> COUNTRY = createField("country", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.country_vaccine_metadata.vaccine</code>.
     */
    public final TableField<CountryVaccineMetadataRecord, String> VACCINE = createField("vaccine", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.country_vaccine_metadata.year_vaccine_intro</code>.
     */
    public final TableField<CountryVaccineMetadataRecord, Integer> YEAR_VACCINE_INTRO = createField("year_vaccine_intro", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.country_vaccine_metadata.year_support_first</code>.
     */
    public final TableField<CountryVaccineMetadataRecord, Integer> YEAR_SUPPORT_FIRST = createField("year_support_first", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.country_vaccine_metadata.year_support_last</code>.
     */
    public final TableField<CountryVaccineMetadataRecord, Integer> YEAR_SUPPORT_LAST = createField("year_support_last", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * Create a <code>public.country_vaccine_metadata</code> table reference
     */
    public CountryVaccineMetadata() {
        this(DSL.name("country_vaccine_metadata"), null);
    }

    /**
     * Create an aliased <code>public.country_vaccine_metadata</code> table reference
     */
    public CountryVaccineMetadata(String alias) {
        this(DSL.name(alias), COUNTRY_VACCINE_METADATA);
    }

    /**
     * Create an aliased <code>public.country_vaccine_metadata</code> table reference
     */
    public CountryVaccineMetadata(Name alias) {
        this(alias, COUNTRY_VACCINE_METADATA);
    }

    private CountryVaccineMetadata(Name alias, Table<CountryVaccineMetadataRecord> aliased) {
        this(alias, aliased, null);
    }

    private CountryVaccineMetadata(Name alias, Table<CountryVaccineMetadataRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.COUNTRY_VACCINE_METADATA_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<CountryVaccineMetadataRecord, Integer> getIdentity() {
        return Keys.IDENTITY_COUNTRY_VACCINE_METADATA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<CountryVaccineMetadataRecord> getPrimaryKey() {
        return Keys.COUNTRY_VACCINE_METADATA_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<CountryVaccineMetadataRecord>> getKeys() {
        return Arrays.<UniqueKey<CountryVaccineMetadataRecord>>asList(Keys.COUNTRY_VACCINE_METADATA_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<CountryVaccineMetadataRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<CountryVaccineMetadataRecord, ?>>asList(Keys.COUNTRY_VACCINE_METADATA__COUNTRY_VACCINE_METADATA_TOUCHSTONE_FKEY, Keys.COUNTRY_VACCINE_METADATA__COUNTRY_VACCINE_METADATA_COUNTRY_FKEY, Keys.COUNTRY_VACCINE_METADATA__COUNTRY_VACCINE_METADATA_VACCINE_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CountryVaccineMetadata as(String alias) {
        return new CountryVaccineMetadata(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CountryVaccineMetadata as(Name alias) {
        return new CountryVaccineMetadata(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public CountryVaccineMetadata rename(String name) {
        return new CountryVaccineMetadata(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public CountryVaccineMetadata rename(Name name) {
        return new CountryVaccineMetadata(name, null);
    }
}
