/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables.records;


import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;
import org.vaccineimpact.api.db.tables.UserGroupMembership;


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
public class UserGroupMembershipRecord extends UpdatableRecordImpl<UserGroupMembershipRecord> implements Record2<String, String> {

    private static final long serialVersionUID = 1631294716;

    /**
     * Setter for <code>public.user_group_membership.username</code>.
     */
    public void setUsername(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.user_group_membership.username</code>.
     */
    public String getUsername() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.user_group_membership.user_group</code>.
     */
    public void setUserGroup(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.user_group_membership.user_group</code>.
     */
    public String getUserGroup() {
        return (String) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record2<String, String> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<String, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<String, String> valuesRow() {
        return (Row2) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return UserGroupMembership.USER_GROUP_MEMBERSHIP.USERNAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return UserGroupMembership.USER_GROUP_MEMBERSHIP.USER_GROUP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component1() {
        return getUsername();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getUserGroup();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getUsername();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getUserGroup();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserGroupMembershipRecord value1(String value) {
        setUsername(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserGroupMembershipRecord value2(String value) {
        setUserGroup(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserGroupMembershipRecord values(String value1, String value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserGroupMembershipRecord
     */
    public UserGroupMembershipRecord() {
        super(UserGroupMembership.USER_GROUP_MEMBERSHIP);
    }

    /**
     * Create a detached, initialised UserGroupMembershipRecord
     */
    public UserGroupMembershipRecord(String username, String userGroup) {
        super(UserGroupMembership.USER_GROUP_MEMBERSHIP);

        set(0, username);
        set(1, userGroup);
    }
}
