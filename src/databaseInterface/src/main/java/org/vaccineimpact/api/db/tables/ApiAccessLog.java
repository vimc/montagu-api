/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables;


import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;
import org.vaccineimpact.api.db.Keys;
import org.vaccineimpact.api.db.Public;
import org.vaccineimpact.api.db.tables.records.ApiAccessLogRecord;


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
public class ApiAccessLog extends TableImpl<ApiAccessLogRecord> {

    private static final long serialVersionUID = 2118366871;

    /**
     * The reference instance of <code>public.api_access_log</code>
     */
    public static final ApiAccessLog API_ACCESS_LOG = new ApiAccessLog();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ApiAccessLogRecord> getRecordType() {
        return ApiAccessLogRecord.class;
    }

    /**
     * The column <code>public.api_access_log.id</code>.
     */
    public final TableField<ApiAccessLogRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('api_access_log_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.api_access_log.who</code>.
     */
    public final TableField<ApiAccessLogRecord, String> WHO = createField("who", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.api_access_log.when</code>.
     */
    public final TableField<ApiAccessLogRecord, Timestamp> WHEN = createField("when", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * The column <code>public.api_access_log.what</code>.
     */
    public final TableField<ApiAccessLogRecord, String> WHAT = createField("what", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.api_access_log.result</code>. The HTTP status code returned by the API
     */
    public final TableField<ApiAccessLogRecord, Integer> RESULT = createField("result", org.jooq.impl.SQLDataType.INTEGER, this, "The HTTP status code returned by the API");

    /**
     * Create a <code>public.api_access_log</code> table reference
     */
    public ApiAccessLog() {
        this("api_access_log", null);
    }

    /**
     * Create an aliased <code>public.api_access_log</code> table reference
     */
    public ApiAccessLog(String alias) {
        this(alias, API_ACCESS_LOG);
    }

    private ApiAccessLog(String alias, Table<ApiAccessLogRecord> aliased) {
        this(alias, aliased, null);
    }

    private ApiAccessLog(String alias, Table<ApiAccessLogRecord> aliased, Field<?>[] parameters) {
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
    public Identity<ApiAccessLogRecord, Integer> getIdentity() {
        return Keys.IDENTITY_API_ACCESS_LOG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ApiAccessLogRecord> getPrimaryKey() {
        return Keys.API_ACCESS_LOG_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ApiAccessLogRecord>> getKeys() {
        return Arrays.<UniqueKey<ApiAccessLogRecord>>asList(Keys.API_ACCESS_LOG_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<ApiAccessLogRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ApiAccessLogRecord, ?>>asList(Keys.API_ACCESS_LOG__API_ACCESS_LOG_WHO_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ApiAccessLog as(String alias) {
        return new ApiAccessLog(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ApiAccessLog rename(String name) {
        return new ApiAccessLog(name, null);
    }
}
