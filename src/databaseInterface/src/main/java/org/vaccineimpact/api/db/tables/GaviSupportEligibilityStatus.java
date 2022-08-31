/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
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
import org.vaccineimpact.api.db.tables.records.GaviSupportEligibilityStatusRecord;


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
public class GaviSupportEligibilityStatus extends TableImpl<GaviSupportEligibilityStatusRecord> {

    private static final long serialVersionUID = 1417067320;

    /**
     * The reference instance of <code>public.gavi_support_eligibility_status</code>
     */
    public static final GaviSupportEligibilityStatus GAVI_SUPPORT_ELIGIBILITY_STATUS = new GaviSupportEligibilityStatus();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GaviSupportEligibilityStatusRecord> getRecordType() {
        return GaviSupportEligibilityStatusRecord.class;
    }

    /**
     * The column <code>public.gavi_support_eligibility_status.id</code>.
     */
    public final TableField<GaviSupportEligibilityStatusRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.gavi_support_eligibility_status.name</code>.
     */
    public final TableField<GaviSupportEligibilityStatusRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * Create a <code>public.gavi_support_eligibility_status</code> table reference
     */
    public GaviSupportEligibilityStatus() {
        this(DSL.name("gavi_support_eligibility_status"), null);
    }

    /**
     * Create an aliased <code>public.gavi_support_eligibility_status</code> table reference
     */
    public GaviSupportEligibilityStatus(String alias) {
        this(DSL.name(alias), GAVI_SUPPORT_ELIGIBILITY_STATUS);
    }

    /**
     * Create an aliased <code>public.gavi_support_eligibility_status</code> table reference
     */
    public GaviSupportEligibilityStatus(Name alias) {
        this(alias, GAVI_SUPPORT_ELIGIBILITY_STATUS);
    }

    private GaviSupportEligibilityStatus(Name alias, Table<GaviSupportEligibilityStatusRecord> aliased) {
        this(alias, aliased, null);
    }

    private GaviSupportEligibilityStatus(Name alias, Table<GaviSupportEligibilityStatusRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.GAVI_SUPPORT_ELIGIBILITY_STATUS_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<GaviSupportEligibilityStatusRecord> getPrimaryKey() {
        return Keys.GAVI_SUPPORT_ELIGIBILITY_STATUS_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<GaviSupportEligibilityStatusRecord>> getKeys() {
        return Arrays.<UniqueKey<GaviSupportEligibilityStatusRecord>>asList(Keys.GAVI_SUPPORT_ELIGIBILITY_STATUS_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GaviSupportEligibilityStatus as(String alias) {
        return new GaviSupportEligibilityStatus(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GaviSupportEligibilityStatus as(Name alias) {
        return new GaviSupportEligibilityStatus(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public GaviSupportEligibilityStatus rename(String name) {
        return new GaviSupportEligibilityStatus(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public GaviSupportEligibilityStatus rename(Name name) {
        return new GaviSupportEligibilityStatus(name, null);
    }
}
