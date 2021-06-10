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
import org.vaccineimpact.api.db.tables.records.ResponsibilityCommentRecord;


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
public class ResponsibilityComment extends TableImpl<ResponsibilityCommentRecord> {

    private static final long serialVersionUID = 1120889702;

    /**
     * The reference instance of <code>public.responsibility_comment</code>
     */
    public static final ResponsibilityComment RESPONSIBILITY_COMMENT = new ResponsibilityComment();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ResponsibilityCommentRecord> getRecordType() {
        return ResponsibilityCommentRecord.class;
    }

    /**
     * The column <code>public.responsibility_comment.id</code>.
     */
    public final TableField<ResponsibilityCommentRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('responsibility_comment_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.responsibility_comment.responsibility</code>.
     */
    public final TableField<ResponsibilityCommentRecord, Integer> RESPONSIBILITY = createField("responsibility", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.responsibility_comment.comment</code>.
     */
    public final TableField<ResponsibilityCommentRecord, String> COMMENT = createField("comment", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.responsibility_comment.added_by</code>.
     */
    public final TableField<ResponsibilityCommentRecord, String> ADDED_BY = createField("added_by", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.responsibility_comment.added_on</code>.
     */
    public final TableField<ResponsibilityCommentRecord, Timestamp> ADDED_ON = createField("added_on", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * Create a <code>public.responsibility_comment</code> table reference
     */
    public ResponsibilityComment() {
        this(DSL.name("responsibility_comment"), null);
    }

    /**
     * Create an aliased <code>public.responsibility_comment</code> table reference
     */
    public ResponsibilityComment(String alias) {
        this(DSL.name(alias), RESPONSIBILITY_COMMENT);
    }

    /**
     * Create an aliased <code>public.responsibility_comment</code> table reference
     */
    public ResponsibilityComment(Name alias) {
        this(alias, RESPONSIBILITY_COMMENT);
    }

    private ResponsibilityComment(Name alias, Table<ResponsibilityCommentRecord> aliased) {
        this(alias, aliased, null);
    }

    private ResponsibilityComment(Name alias, Table<ResponsibilityCommentRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.RESPONSIBILITY_COMMENT_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ResponsibilityCommentRecord, Integer> getIdentity() {
        return Keys.IDENTITY_RESPONSIBILITY_COMMENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ResponsibilityCommentRecord> getPrimaryKey() {
        return Keys.RESPONSIBILITY_COMMENT_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ResponsibilityCommentRecord>> getKeys() {
        return Arrays.<UniqueKey<ResponsibilityCommentRecord>>asList(Keys.RESPONSIBILITY_COMMENT_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<ResponsibilityCommentRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ResponsibilityCommentRecord, ?>>asList(Keys.RESPONSIBILITY_COMMENT__RESPONSIBILITY_COMMENT_RESPONSIBILITY_FKEY, Keys.RESPONSIBILITY_COMMENT__RESPONSIBILITY_COMMENT_ADDED_BY_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponsibilityComment as(String alias) {
        return new ResponsibilityComment(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponsibilityComment as(Name alias) {
        return new ResponsibilityComment(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ResponsibilityComment rename(String name) {
        return new ResponsibilityComment(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ResponsibilityComment rename(Name name) {
        return new ResponsibilityComment(name, null);
    }
}
