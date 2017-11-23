/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables;


import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;
import org.vaccineimpact.api.db.Keys;
import org.vaccineimpact.api.db.Public;
import org.vaccineimpact.api.db.tables.records.UploadInfoRecord;


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
public class UploadInfo extends TableImpl<UploadInfoRecord> {

    private static final long serialVersionUID = -1158809555;

    /**
     * The reference instance of <code>public.upload_info</code>
     */
    public static final UploadInfo UPLOAD_INFO = new UploadInfo();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UploadInfoRecord> getRecordType() {
        return UploadInfoRecord.class;
    }

    /**
     * The column <code>public.upload_info.id</code>.
     */
    public final TableField<UploadInfoRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('upload_info_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.upload_info.uploaded_by</code>.
     */
    public final TableField<UploadInfoRecord, String> UPLOADED_BY = createField("uploaded_by", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.upload_info.uploaded_on</code>.
     */
    public final TableField<UploadInfoRecord, Timestamp> UPLOADED_ON = createField("uploaded_on", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.field("date_trunc('milliseconds'::text, now())", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>public.upload_info</code> table reference
     */
    public UploadInfo() {
        this("upload_info", null);
    }

    /**
     * Create an aliased <code>public.upload_info</code> table reference
     */
    public UploadInfo(String alias) {
        this(alias, UPLOAD_INFO);
    }

    private UploadInfo(String alias, Table<UploadInfoRecord> aliased) {
        this(alias, aliased, null);
    }

    private UploadInfo(String alias, Table<UploadInfoRecord> aliased, Field<?>[] parameters) {
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
    public Identity<UploadInfoRecord, Integer> getIdentity() {
        return Keys.IDENTITY_UPLOAD_INFO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UploadInfoRecord> getPrimaryKey() {
        return Keys.UPLOAD_INFO_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UploadInfoRecord>> getKeys() {
        return Arrays.<UniqueKey<UploadInfoRecord>>asList(Keys.UPLOAD_INFO_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UploadInfo as(String alias) {
        return new UploadInfo(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UploadInfo rename(String name) {
        return new UploadInfo(name, null);
    }
}
