/*
 * This file is generated by jOOQ.
 */
package org.vaccineimpact.api.db.tables;


import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row2;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.vaccineimpact.api.db.Keys;
import org.vaccineimpact.api.db.Public;
import org.vaccineimpact.api.db.tables.records.ModelVersionCountryRecord;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ModelVersionCountry extends TableImpl<ModelVersionCountryRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.model_version_country</code>
     */
    public static final ModelVersionCountry MODEL_VERSION_COUNTRY = new ModelVersionCountry();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ModelVersionCountryRecord> getRecordType() {
        return ModelVersionCountryRecord.class;
    }

    /**
     * The column <code>public.model_version_country.model_version</code>.
     */
    public final TableField<ModelVersionCountryRecord, Integer> MODEL_VERSION = createField(DSL.name("model_version"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.model_version_country.country</code>.
     */
    public final TableField<ModelVersionCountryRecord, String> COUNTRY = createField(DSL.name("country"), SQLDataType.CLOB.nullable(false), this, "");

    private ModelVersionCountry(Name alias, Table<ModelVersionCountryRecord> aliased) {
        this(alias, aliased, null);
    }

    private ModelVersionCountry(Name alias, Table<ModelVersionCountryRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.model_version_country</code> table
     * reference
     */
    public ModelVersionCountry(String alias) {
        this(DSL.name(alias), MODEL_VERSION_COUNTRY);
    }

    /**
     * Create an aliased <code>public.model_version_country</code> table
     * reference
     */
    public ModelVersionCountry(Name alias) {
        this(alias, MODEL_VERSION_COUNTRY);
    }

    /**
     * Create a <code>public.model_version_country</code> table reference
     */
    public ModelVersionCountry() {
        this(DSL.name("model_version_country"), null);
    }

    public <O extends Record> ModelVersionCountry(Table<O> child, ForeignKey<O, ModelVersionCountryRecord> key) {
        super(child, key, MODEL_VERSION_COUNTRY);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<ModelVersionCountryRecord> getPrimaryKey() {
        return Keys.MODEL_VERSION_COUNTRY_PKEY;
    }

    @Override
    public List<ForeignKey<ModelVersionCountryRecord, ?>> getReferences() {
        return Arrays.asList(Keys.MODEL_VERSION_COUNTRY__MODEL_VERSION_COUNTRY_MODEL_VERSION_FKEY, Keys.MODEL_VERSION_COUNTRY__MODEL_VERSION_COUNTRY_COUNTRY_FKEY);
    }

    private transient ModelVersion _modelVersion;
    private transient Country _country;

    /**
     * Get the implicit join path to the <code>public.model_version</code>
     * table.
     */
    public ModelVersion modelVersion() {
        if (_modelVersion == null)
            _modelVersion = new ModelVersion(this, Keys.MODEL_VERSION_COUNTRY__MODEL_VERSION_COUNTRY_MODEL_VERSION_FKEY);

        return _modelVersion;
    }

    /**
     * Get the implicit join path to the <code>public.country</code> table.
     */
    public Country country() {
        if (_country == null)
            _country = new Country(this, Keys.MODEL_VERSION_COUNTRY__MODEL_VERSION_COUNTRY_COUNTRY_FKEY);

        return _country;
    }

    @Override
    public ModelVersionCountry as(String alias) {
        return new ModelVersionCountry(DSL.name(alias), this);
    }

    @Override
    public ModelVersionCountry as(Name alias) {
        return new ModelVersionCountry(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ModelVersionCountry rename(String name) {
        return new ModelVersionCountry(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ModelVersionCountry rename(Name name) {
        return new ModelVersionCountry(name, null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<Integer, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
}
