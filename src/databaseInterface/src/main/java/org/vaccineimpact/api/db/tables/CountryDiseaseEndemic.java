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
import org.vaccineimpact.api.db.tables.records.CountryDiseaseEndemicRecord;


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
public class CountryDiseaseEndemic extends TableImpl<CountryDiseaseEndemicRecord> {

    private static final long serialVersionUID = -1789929073;

    /**
     * The reference instance of <code>public.country_disease_endemic</code>
     */
    public static final CountryDiseaseEndemic COUNTRY_DISEASE_ENDEMIC = new CountryDiseaseEndemic();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CountryDiseaseEndemicRecord> getRecordType() {
        return CountryDiseaseEndemicRecord.class;
    }

    /**
     * The column <code>public.country_disease_endemic.id</code>.
     */
    public final TableField<CountryDiseaseEndemicRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('country_disease_endemic_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.country_disease_endemic.touchstone</code>.
     */
    public final TableField<CountryDiseaseEndemicRecord, String> TOUCHSTONE = createField("touchstone", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.country_disease_endemic.country</code>.
     */
    public final TableField<CountryDiseaseEndemicRecord, String> COUNTRY = createField("country", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.country_disease_endemic.disease</code>.
     */
    public final TableField<CountryDiseaseEndemicRecord, String> DISEASE = createField("disease", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * Create a <code>public.country_disease_endemic</code> table reference
     */
    public CountryDiseaseEndemic() {
        this(DSL.name("country_disease_endemic"), null);
    }

    /**
     * Create an aliased <code>public.country_disease_endemic</code> table reference
     */
    public CountryDiseaseEndemic(String alias) {
        this(DSL.name(alias), COUNTRY_DISEASE_ENDEMIC);
    }

    /**
     * Create an aliased <code>public.country_disease_endemic</code> table reference
     */
    public CountryDiseaseEndemic(Name alias) {
        this(alias, COUNTRY_DISEASE_ENDEMIC);
    }

    private CountryDiseaseEndemic(Name alias, Table<CountryDiseaseEndemicRecord> aliased) {
        this(alias, aliased, null);
    }

    private CountryDiseaseEndemic(Name alias, Table<CountryDiseaseEndemicRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.COUNTRY_DISEASE_ENDEMIC_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<CountryDiseaseEndemicRecord, Integer> getIdentity() {
        return Keys.IDENTITY_COUNTRY_DISEASE_ENDEMIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<CountryDiseaseEndemicRecord> getPrimaryKey() {
        return Keys.COUNTRY_DISEASE_ENDEMIC_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<CountryDiseaseEndemicRecord>> getKeys() {
        return Arrays.<UniqueKey<CountryDiseaseEndemicRecord>>asList(Keys.COUNTRY_DISEASE_ENDEMIC_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<CountryDiseaseEndemicRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<CountryDiseaseEndemicRecord, ?>>asList(Keys.COUNTRY_DISEASE_ENDEMIC__COUNTRY_DISEASE_ENDEMIC_TOUCHSTONE_FKEY, Keys.COUNTRY_DISEASE_ENDEMIC__COUNTRY_DISEASE_ENDEMIC_COUNTRY_FKEY, Keys.COUNTRY_DISEASE_ENDEMIC__COUNTRY_DISEASE_ENDEMIC_DISEASE_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CountryDiseaseEndemic as(String alias) {
        return new CountryDiseaseEndemic(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CountryDiseaseEndemic as(Name alias) {
        return new CountryDiseaseEndemic(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public CountryDiseaseEndemic rename(String name) {
        return new CountryDiseaseEndemic(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public CountryDiseaseEndemic rename(Name name) {
        return new CountryDiseaseEndemic(name, null);
    }
}
