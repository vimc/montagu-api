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
import org.vaccineimpact.api.db.tables.records.SourceRecord;


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
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Source extends TableImpl<SourceRecord> {

    private static final long serialVersionUID = 231993929;

    /**
     * The reference instance of <code>public.source</code>
     */
    public static final Source SOURCE = new Source();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SourceRecord> getRecordType() {
        return SourceRecord.class;
    }

    /**
     * The column <code>public.source.id</code>.
     */
    public final TableField<SourceRecord, String> ID = createField("id", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.source.name</code>.
     */
    public final TableField<SourceRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * Create a <code>public.source</code> table reference
     */
    public Source() {
        this("source", null);
    }

    /**
     * Create an aliased <code>public.source</code> table reference
     */
    public Source(String alias) {
        this(alias, SOURCE);
    }

    private Source(String alias, Table<SourceRecord> aliased) {
        this(alias, aliased, null);
    }

    private Source(String alias, Table<SourceRecord> aliased, Field<?>[] parameters) {
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
    public UniqueKey<SourceRecord> getPrimaryKey() {
        return Keys.SOURCE_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<SourceRecord>> getKeys() {
        return Arrays.<UniqueKey<SourceRecord>>asList(Keys.SOURCE_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Source as(String alias) {
        return new Source(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Source rename(String name) {
        return new Source(name, null);
    }
}
