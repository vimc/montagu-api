/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables;


import org.jooq.*;
import org.jooq.impl.TableImpl;
import org.vaccineimpact.api.db.Keys;
import org.vaccineimpact.api.db.Public;
import org.vaccineimpact.api.db.tables.records.ModelVersionRecord;

import javax.annotation.Generated;
import java.util.Arrays;
import java.util.List;


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
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class ModelVersion extends TableImpl<ModelVersionRecord> {

    private static final long serialVersionUID = -235149049;

    /**
     * The reference instance of <code>public.model_version</code>
     */
    public static final ModelVersion MODEL_VERSION = new ModelVersion();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ModelVersionRecord> getRecordType() {
        return ModelVersionRecord.class;
    }

    /**
     * The column <code>public.model_version.id</code>.
     */
    public final TableField<ModelVersionRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('model_version_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.model_version.model</code>.
     */
    public final TableField<ModelVersionRecord, String> MODEL = createField("model", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.model_version.version</code>.
     */
    public final TableField<ModelVersionRecord, String> VERSION = createField("version", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.model_version.note</code>.
     */
    public final TableField<ModelVersionRecord, String> NOTE = createField("note", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.model_version.fingerprint</code>.
     */
    public final TableField<ModelVersionRecord, String> FINGERPRINT = createField("fingerprint", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * Create a <code>public.model_version</code> table reference
     */
    public ModelVersion() {
        this("model_version", null);
    }

    /**
     * Create an aliased <code>public.model_version</code> table reference
     */
    public ModelVersion(String alias) {
        this(alias, MODEL_VERSION);
    }

    private ModelVersion(String alias, Table<ModelVersionRecord> aliased) {
        this(alias, aliased, null);
    }

    private ModelVersion(String alias, Table<ModelVersionRecord> aliased, Field<?>[] parameters) {
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
    public Identity<ModelVersionRecord, Integer> getIdentity() {
        return Keys.IDENTITY_MODEL_VERSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ModelVersionRecord> getPrimaryKey() {
        return Keys.MODEL_VERSION_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ModelVersionRecord>> getKeys() {
        return Arrays.<UniqueKey<ModelVersionRecord>>asList(Keys.MODEL_VERSION_PKEY, Keys.MODEL_VERSION_MODEL_VERSION_KEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<ModelVersionRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ModelVersionRecord, ?>>asList(Keys.MODEL_VERSION__MODEL_VERSION_MODEL_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelVersion as(String alias) {
        return new ModelVersion(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ModelVersion rename(String name) {
        return new ModelVersion(name, null);
    }
}
