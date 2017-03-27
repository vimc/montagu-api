/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;
import org.vaccineimpact.api.db.Keys;
import org.vaccineimpact.api.db.Public;
import org.vaccineimpact.api.db.tables.records.VaccinationLevelRecord;


/**
 * Enum table. Possible values: No vaccination, Vaccination without GAVI support, 
 * Vaccination with GAVI support
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class VaccinationLevel extends TableImpl<VaccinationLevelRecord> {

    private static final long serialVersionUID = -94655657;

    /**
     * The reference instance of <code>public.vaccination_level</code>
     */
    public static final VaccinationLevel VACCINATION_LEVEL = new VaccinationLevel();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<VaccinationLevelRecord> getRecordType() {
        return VaccinationLevelRecord.class;
    }

    /**
     * The column <code>public.vaccination_level.id</code>.
     */
    public final TableField<VaccinationLevelRecord, String> ID = createField("id", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.vaccination_level.name</code>.
     */
    public final TableField<VaccinationLevelRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false).defaultValue(org.jooq.impl.DSL.field("'NULL'::character varying", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * Create a <code>public.vaccination_level</code> table reference
     */
    public VaccinationLevel() {
        this("vaccination_level", null);
    }

    /**
     * Create an aliased <code>public.vaccination_level</code> table reference
     */
    public VaccinationLevel(String alias) {
        this(alias, VACCINATION_LEVEL);
    }

    private VaccinationLevel(String alias, Table<VaccinationLevelRecord> aliased) {
        this(alias, aliased, null);
    }

    private VaccinationLevel(String alias, Table<VaccinationLevelRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "Enum table. Possible values: No vaccination, Vaccination without GAVI support, Vaccination with GAVI support");
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
    public UniqueKey<VaccinationLevelRecord> getPrimaryKey() {
        return Keys.VACCINATION_LEVEL_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<VaccinationLevelRecord>> getKeys() {
        return Arrays.<UniqueKey<VaccinationLevelRecord>>asList(Keys.VACCINATION_LEVEL_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VaccinationLevel as(String alias) {
        return new VaccinationLevel(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public VaccinationLevel rename(String name) {
        return new VaccinationLevel(name, null);
    }
}
