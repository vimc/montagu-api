/*
 * This file is generated by jOOQ.
*/
package org.vaccineimpact.api.db.tables.records;


import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;
import org.vaccineimpact.api.db.tables.User;

import javax.annotation.Generated;
import java.sql.Timestamp;


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
public class UserRecord extends UpdatableRecordImpl<UserRecord> implements Record6<String, String, String, String, String, Timestamp> {

    private static final long serialVersionUID = 1698882821;

    /**
     * Setter for <code>public.user.username</code>.
     */
    public void setUsername(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.user.username</code>.
     */
    public String getUsername() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.user.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.user.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.user.email</code>.
     */
    public void setEmail(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.user.email</code>.
     */
    public String getEmail() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.user.password_hash</code>.
     */
    public void setPasswordHash(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.user.password_hash</code>.
     */
    public String getPasswordHash() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.user.salt</code>.
     */
    public void setSalt(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.user.salt</code>.
     */
    public String getSalt() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.user.last_logged_in</code>.
     */
    public void setLastLoggedIn(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.user.last_logged_in</code>.
     */
    public Timestamp getLastLoggedIn() {
        return (Timestamp) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<String, String, String, String, String, Timestamp> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<String, String, String, String, String, Timestamp> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return User.USER.USERNAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return User.USER.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return User.USER.EMAIL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return User.USER.PASSWORD_HASH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return User.USER.SALT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return User.USER.LAST_LOGGED_IN;
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
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getEmail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getPasswordHash();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getSalt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value6() {
        return getLastLoggedIn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRecord value1(String value) {
        setUsername(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRecord value3(String value) {
        setEmail(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRecord value4(String value) {
        setPasswordHash(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRecord value5(String value) {
        setSalt(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRecord value6(Timestamp value) {
        setLastLoggedIn(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRecord values(String value1, String value2, String value3, String value4, String value5, Timestamp value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserRecord
     */
    public UserRecord() {
        super(User.USER);
    }

    /**
     * Create a detached, initialised UserRecord
     */
    public UserRecord(String username, String name, String email, String passwordHash, String salt, Timestamp lastLoggedIn) {
        super(User.USER);

        set(0, username);
        set(1, name);
        set(2, email);
        set(3, passwordHash);
        set(4, salt);
        set(5, lastLoggedIn);
    }
}
