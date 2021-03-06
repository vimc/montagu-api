/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
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
import org.vaccineimpact.api.db.tables.records.UserGroupMembershipRecord;


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
public class UserGroupMembership extends TableImpl<UserGroupMembershipRecord> {

    private static final long serialVersionUID = 419267492;

    /**
     * The reference instance of <code>public.user_group_membership</code>
     */
    public static final UserGroupMembership USER_GROUP_MEMBERSHIP = new UserGroupMembership();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserGroupMembershipRecord> getRecordType() {
        return UserGroupMembershipRecord.class;
    }

    /**
     * The column <code>public.user_group_membership.username</code>.
     */
    public final TableField<UserGroupMembershipRecord, String> USERNAME = createField("username", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.user_group_membership.user_group</code>.
     */
    public final TableField<UserGroupMembershipRecord, String> USER_GROUP = createField("user_group", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * Create a <code>public.user_group_membership</code> table reference
     */
    public UserGroupMembership() {
        this(DSL.name("user_group_membership"), null);
    }

    /**
     * Create an aliased <code>public.user_group_membership</code> table reference
     */
    public UserGroupMembership(String alias) {
        this(DSL.name(alias), USER_GROUP_MEMBERSHIP);
    }

    /**
     * Create an aliased <code>public.user_group_membership</code> table reference
     */
    public UserGroupMembership(Name alias) {
        this(alias, USER_GROUP_MEMBERSHIP);
    }

    private UserGroupMembership(Name alias, Table<UserGroupMembershipRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserGroupMembership(Name alias, Table<UserGroupMembershipRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.USER_GROUP_MEMBERSHIP_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UserGroupMembershipRecord> getPrimaryKey() {
        return Keys.USER_GROUP_MEMBERSHIP_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UserGroupMembershipRecord>> getKeys() {
        return Arrays.<UniqueKey<UserGroupMembershipRecord>>asList(Keys.USER_GROUP_MEMBERSHIP_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<UserGroupMembershipRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<UserGroupMembershipRecord, ?>>asList(Keys.USER_GROUP_MEMBERSHIP__USER_GROUP_MEMBERSHIP_USERNAME_FKEY, Keys.USER_GROUP_MEMBERSHIP__USER_GROUP_MEMBERSHIP_USER_GROUP_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserGroupMembership as(String alias) {
        return new UserGroupMembership(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserGroupMembership as(Name alias) {
        return new UserGroupMembership(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserGroupMembership rename(String name) {
        return new UserGroupMembership(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public UserGroupMembership rename(Name name) {
        return new UserGroupMembership(name, null);
    }
}
