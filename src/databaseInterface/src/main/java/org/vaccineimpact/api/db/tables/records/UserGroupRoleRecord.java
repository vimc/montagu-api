/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables.records;


import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;
import org.vaccineimpact.api.db.tables.UserGroupRole;


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
public class UserGroupRoleRecord extends UpdatableRecordImpl<UserGroupRoleRecord> implements Record3<String, Integer, String> {

    private static final long serialVersionUID = 957790450;

    /**
     * Setter for <code>public.user_group_role.user_group</code>.
     */
    public void setUserGroup(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.user_group_role.user_group</code>.
     */
    public String getUserGroup() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.user_group_role.role</code>.
     */
    public void setRole(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.user_group_role.role</code>.
     */
    public Integer getRole() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>public.user_group_role.scope_id</code>.
     */
    public void setScopeId(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.user_group_role.scope_id</code>.
     */
    public String getScopeId() {
        return (String) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record3<String, Integer, String> key() {
        return (Record3) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<String, Integer, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<String, Integer, String> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return UserGroupRole.USER_GROUP_ROLE.USER_GROUP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return UserGroupRole.USER_GROUP_ROLE.ROLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return UserGroupRole.USER_GROUP_ROLE.SCOPE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component1() {
        return getUserGroup();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component2() {
        return getRole();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getScopeId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getUserGroup();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getRole();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getScopeId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserGroupRoleRecord value1(String value) {
        setUserGroup(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserGroupRoleRecord value2(Integer value) {
        setRole(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserGroupRoleRecord value3(String value) {
        setScopeId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserGroupRoleRecord values(String value1, Integer value2, String value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserGroupRoleRecord
     */
    public UserGroupRoleRecord() {
        super(UserGroupRole.USER_GROUP_ROLE);
    }

    /**
     * Create a detached, initialised UserGroupRoleRecord
     */
    public UserGroupRoleRecord(String userGroup, Integer role, String scopeId) {
        super(UserGroupRole.USER_GROUP_ROLE);

        set(0, userGroup);
        set(1, role);
        set(2, scopeId);
    }
}
