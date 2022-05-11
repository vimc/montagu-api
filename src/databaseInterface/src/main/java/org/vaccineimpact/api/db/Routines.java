/*
 * This file is generated by jOOQ.
 */
package org.vaccineimpact.api.db;


import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.Result;
import org.vaccineimpact.api.db.routines.DisableTrigger;
import org.vaccineimpact.api.db.routines.EnableTrigger;
import org.vaccineimpact.api.db.tables.Connectby;
import org.vaccineimpact.api.db.tables.Crosstab;
import org.vaccineimpact.api.db.tables.Crosstab2;
import org.vaccineimpact.api.db.tables.Crosstab3;
import org.vaccineimpact.api.db.tables.Crosstab4;
import org.vaccineimpact.api.db.tables.NormalRand;
import org.vaccineimpact.api.db.tables.SelectBurdenData1;
import org.vaccineimpact.api.db.tables.SelectBurdenData2;
import org.vaccineimpact.api.db.tables.SelectBurdenData3;
import org.vaccineimpact.api.db.tables.SelectBurdenData4;
import org.vaccineimpact.api.db.tables.SelectBurdenData5;
import org.vaccineimpact.api.db.tables.SelectBurdenData6;
import org.vaccineimpact.api.db.tables.SelectBurdenData7;
import org.vaccineimpact.api.db.tables.SelectBurdenData8;
import org.vaccineimpact.api.db.tables.SelectBurdenDataCol;
import org.vaccineimpact.api.db.tables.records.ConnectbyRecord;
import org.vaccineimpact.api.db.tables.records.Crosstab2Record;
import org.vaccineimpact.api.db.tables.records.Crosstab3Record;
import org.vaccineimpact.api.db.tables.records.Crosstab4Record;
import org.vaccineimpact.api.db.tables.records.CrosstabRecord;
import org.vaccineimpact.api.db.tables.records.NormalRandRecord;
import org.vaccineimpact.api.db.tables.records.SelectBurdenData1Record;
import org.vaccineimpact.api.db.tables.records.SelectBurdenData2Record;
import org.vaccineimpact.api.db.tables.records.SelectBurdenData3Record;
import org.vaccineimpact.api.db.tables.records.SelectBurdenData4Record;
import org.vaccineimpact.api.db.tables.records.SelectBurdenData5Record;
import org.vaccineimpact.api.db.tables.records.SelectBurdenData6Record;
import org.vaccineimpact.api.db.tables.records.SelectBurdenData7Record;
import org.vaccineimpact.api.db.tables.records.SelectBurdenData8Record;
import org.vaccineimpact.api.db.tables.records.SelectBurdenDataColRecord;


/**
 * Convenience access to all stored procedures and functions in public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Routines {

    /**
     * Call <code>public.disable_trigger</code>
     */
    public static void disableTrigger(
          Configuration configuration
        , String tableName
        , String triggerName
    ) {
        DisableTrigger p = new DisableTrigger();
        p.setTableName(tableName);
        p.setTriggerName(triggerName);

        p.execute(configuration);
    }

    /**
     * Call <code>public.enable_trigger</code>
     */
    public static void enableTrigger(
          Configuration configuration
        , String tableName
        , String triggerName
    ) {
        EnableTrigger p = new EnableTrigger();
        p.setTableName(tableName);
        p.setTriggerName(triggerName);

        p.execute(configuration);
    }

    /**
     * Call <code>public.connectby</code>.
     */
    public static Result<ConnectbyRecord> connectby(
          Configuration configuration
        , String __1
        , String __2
        , String __3
        , String __4
        , String __5
        , Integer __6
    ) {
        return configuration.dsl().selectFrom(org.vaccineimpact.api.db.tables.Connectby.CONNECTBY.call(
              __1
            , __2
            , __3
            , __4
            , __5
            , __6
        )).fetch();
    }

    /**
     * Get <code>public.connectby</code> as a table.
     */
    public static Connectby connectby(
          String __1
        , String __2
        , String __3
        , String __4
        , String __5
        , Integer __6
    ) {
        return org.vaccineimpact.api.db.tables.Connectby.CONNECTBY.call(
            __1,
            __2,
            __3,
            __4,
            __5,
            __6
        );
    }

    /**
     * Get <code>public.connectby</code> as a table.
     */
    public static Connectby connectby(
          Field<String> __1
        , Field<String> __2
        , Field<String> __3
        , Field<String> __4
        , Field<String> __5
        , Field<Integer> __6
    ) {
        return org.vaccineimpact.api.db.tables.Connectby.CONNECTBY.call(
            __1,
            __2,
            __3,
            __4,
            __5,
            __6
        );
    }

    /**
     * Call <code>public.crosstab</code>.
     */
    public static Result<CrosstabRecord> crosstab(
          Configuration configuration
        , String __1
    ) {
        return configuration.dsl().selectFrom(org.vaccineimpact.api.db.tables.Crosstab.CROSSTAB.call(
              __1
        )).fetch();
    }

    /**
     * Get <code>public.crosstab</code> as a table.
     */
    public static Crosstab crosstab(
          String __1
    ) {
        return org.vaccineimpact.api.db.tables.Crosstab.CROSSTAB.call(
            __1
        );
    }

    /**
     * Get <code>public.crosstab</code> as a table.
     */
    public static Crosstab crosstab(
          Field<String> __1
    ) {
        return org.vaccineimpact.api.db.tables.Crosstab.CROSSTAB.call(
            __1
        );
    }

    /**
     * Call <code>public.crosstab2</code>.
     */
    public static Result<Crosstab2Record> crosstab2(
          Configuration configuration
        , String __1
    ) {
        return configuration.dsl().selectFrom(org.vaccineimpact.api.db.tables.Crosstab2.CROSSTAB2.call(
              __1
        )).fetch();
    }

    /**
     * Get <code>public.crosstab2</code> as a table.
     */
    public static Crosstab2 crosstab2(
          String __1
    ) {
        return org.vaccineimpact.api.db.tables.Crosstab2.CROSSTAB2.call(
            __1
        );
    }

    /**
     * Get <code>public.crosstab2</code> as a table.
     */
    public static Crosstab2 crosstab2(
          Field<String> __1
    ) {
        return org.vaccineimpact.api.db.tables.Crosstab2.CROSSTAB2.call(
            __1
        );
    }

    /**
     * Call <code>public.crosstab3</code>.
     */
    public static Result<Crosstab3Record> crosstab3(
          Configuration configuration
        , String __1
    ) {
        return configuration.dsl().selectFrom(org.vaccineimpact.api.db.tables.Crosstab3.CROSSTAB3.call(
              __1
        )).fetch();
    }

    /**
     * Get <code>public.crosstab3</code> as a table.
     */
    public static Crosstab3 crosstab3(
          String __1
    ) {
        return org.vaccineimpact.api.db.tables.Crosstab3.CROSSTAB3.call(
            __1
        );
    }

    /**
     * Get <code>public.crosstab3</code> as a table.
     */
    public static Crosstab3 crosstab3(
          Field<String> __1
    ) {
        return org.vaccineimpact.api.db.tables.Crosstab3.CROSSTAB3.call(
            __1
        );
    }

    /**
     * Call <code>public.crosstab4</code>.
     */
    public static Result<Crosstab4Record> crosstab4(
          Configuration configuration
        , String __1
    ) {
        return configuration.dsl().selectFrom(org.vaccineimpact.api.db.tables.Crosstab4.CROSSTAB4.call(
              __1
        )).fetch();
    }

    /**
     * Get <code>public.crosstab4</code> as a table.
     */
    public static Crosstab4 crosstab4(
          String __1
    ) {
        return org.vaccineimpact.api.db.tables.Crosstab4.CROSSTAB4.call(
            __1
        );
    }

    /**
     * Get <code>public.crosstab4</code> as a table.
     */
    public static Crosstab4 crosstab4(
          Field<String> __1
    ) {
        return org.vaccineimpact.api.db.tables.Crosstab4.CROSSTAB4.call(
            __1
        );
    }

    /**
     * Call <code>public.normal_rand</code>.
     */
    public static Result<NormalRandRecord> normalRand(
          Configuration configuration
        , Integer __1
        , Double __2
        , Double __3
    ) {
        return configuration.dsl().selectFrom(org.vaccineimpact.api.db.tables.NormalRand.NORMAL_RAND.call(
              __1
            , __2
            , __3
        )).fetch();
    }

    /**
     * Get <code>public.normal_rand</code> as a table.
     */
    public static NormalRand normalRand(
          Integer __1
        , Double __2
        , Double __3
    ) {
        return org.vaccineimpact.api.db.tables.NormalRand.NORMAL_RAND.call(
            __1,
            __2,
            __3
        );
    }

    /**
     * Get <code>public.normal_rand</code> as a table.
     */
    public static NormalRand normalRand(
          Field<Integer> __1
        , Field<Double> __2
        , Field<Double> __3
    ) {
        return org.vaccineimpact.api.db.tables.NormalRand.NORMAL_RAND.call(
            __1,
            __2,
            __3
        );
    }

    /**
     * Call <code>public.select_burden_data1</code>.
     */
    public static Result<SelectBurdenData1Record> selectBurdenData1(
          Configuration configuration
        , Integer set1
        , Integer outcome1
    ) {
        return configuration.dsl().selectFrom(org.vaccineimpact.api.db.tables.SelectBurdenData1.SELECT_BURDEN_DATA1.call(
              set1
            , outcome1
        )).fetch();
    }

    /**
     * Get <code>public.select_burden_data1</code> as a table.
     */
    public static SelectBurdenData1 selectBurdenData1(
          Integer set1
        , Integer outcome1
    ) {
        return org.vaccineimpact.api.db.tables.SelectBurdenData1.SELECT_BURDEN_DATA1.call(
            set1,
            outcome1
        );
    }

    /**
     * Get <code>public.select_burden_data1</code> as a table.
     */
    public static SelectBurdenData1 selectBurdenData1(
          Field<Integer> set1
        , Field<Integer> outcome1
    ) {
        return org.vaccineimpact.api.db.tables.SelectBurdenData1.SELECT_BURDEN_DATA1.call(
            set1,
            outcome1
        );
    }

    /**
     * Call <code>public.select_burden_data2</code>.
     */
    public static Result<SelectBurdenData2Record> selectBurdenData2(
          Configuration configuration
        , Integer set1
        , Integer outcome1
        , Integer set2
        , Integer outcome2
    ) {
        return configuration.dsl().selectFrom(org.vaccineimpact.api.db.tables.SelectBurdenData2.SELECT_BURDEN_DATA2.call(
              set1
            , outcome1
            , set2
            , outcome2
        )).fetch();
    }

    /**
     * Get <code>public.select_burden_data2</code> as a table.
     */
    public static SelectBurdenData2 selectBurdenData2(
          Integer set1
        , Integer outcome1
        , Integer set2
        , Integer outcome2
    ) {
        return org.vaccineimpact.api.db.tables.SelectBurdenData2.SELECT_BURDEN_DATA2.call(
            set1,
            outcome1,
            set2,
            outcome2
        );
    }

    /**
     * Get <code>public.select_burden_data2</code> as a table.
     */
    public static SelectBurdenData2 selectBurdenData2(
          Field<Integer> set1
        , Field<Integer> outcome1
        , Field<Integer> set2
        , Field<Integer> outcome2
    ) {
        return org.vaccineimpact.api.db.tables.SelectBurdenData2.SELECT_BURDEN_DATA2.call(
            set1,
            outcome1,
            set2,
            outcome2
        );
    }

    /**
     * Call <code>public.select_burden_data3</code>.
     */
    public static Result<SelectBurdenData3Record> selectBurdenData3(
          Configuration configuration
        , Integer set1
        , Integer outcome1
        , Integer set2
        , Integer outcome2
        , Integer set3
        , Integer outcome3
    ) {
        return configuration.dsl().selectFrom(org.vaccineimpact.api.db.tables.SelectBurdenData3.SELECT_BURDEN_DATA3.call(
              set1
            , outcome1
            , set2
            , outcome2
            , set3
            , outcome3
        )).fetch();
    }

    /**
     * Get <code>public.select_burden_data3</code> as a table.
     */
    public static SelectBurdenData3 selectBurdenData3(
          Integer set1
        , Integer outcome1
        , Integer set2
        , Integer outcome2
        , Integer set3
        , Integer outcome3
    ) {
        return org.vaccineimpact.api.db.tables.SelectBurdenData3.SELECT_BURDEN_DATA3.call(
            set1,
            outcome1,
            set2,
            outcome2,
            set3,
            outcome3
        );
    }

    /**
     * Get <code>public.select_burden_data3</code> as a table.
     */
    public static SelectBurdenData3 selectBurdenData3(
          Field<Integer> set1
        , Field<Integer> outcome1
        , Field<Integer> set2
        , Field<Integer> outcome2
        , Field<Integer> set3
        , Field<Integer> outcome3
    ) {
        return org.vaccineimpact.api.db.tables.SelectBurdenData3.SELECT_BURDEN_DATA3.call(
            set1,
            outcome1,
            set2,
            outcome2,
            set3,
            outcome3
        );
    }

    /**
     * Call <code>public.select_burden_data4</code>.
     */
    public static Result<SelectBurdenData4Record> selectBurdenData4(
          Configuration configuration
        , Integer set1
        , Integer outcome1
        , Integer set2
        , Integer outcome2
        , Integer set3
        , Integer outcome3
        , Integer set4
        , Integer outcome4
    ) {
        return configuration.dsl().selectFrom(org.vaccineimpact.api.db.tables.SelectBurdenData4.SELECT_BURDEN_DATA4.call(
              set1
            , outcome1
            , set2
            , outcome2
            , set3
            , outcome3
            , set4
            , outcome4
        )).fetch();
    }

    /**
     * Get <code>public.select_burden_data4</code> as a table.
     */
    public static SelectBurdenData4 selectBurdenData4(
          Integer set1
        , Integer outcome1
        , Integer set2
        , Integer outcome2
        , Integer set3
        , Integer outcome3
        , Integer set4
        , Integer outcome4
    ) {
        return org.vaccineimpact.api.db.tables.SelectBurdenData4.SELECT_BURDEN_DATA4.call(
            set1,
            outcome1,
            set2,
            outcome2,
            set3,
            outcome3,
            set4,
            outcome4
        );
    }

    /**
     * Get <code>public.select_burden_data4</code> as a table.
     */
    public static SelectBurdenData4 selectBurdenData4(
          Field<Integer> set1
        , Field<Integer> outcome1
        , Field<Integer> set2
        , Field<Integer> outcome2
        , Field<Integer> set3
        , Field<Integer> outcome3
        , Field<Integer> set4
        , Field<Integer> outcome4
    ) {
        return org.vaccineimpact.api.db.tables.SelectBurdenData4.SELECT_BURDEN_DATA4.call(
            set1,
            outcome1,
            set2,
            outcome2,
            set3,
            outcome3,
            set4,
            outcome4
        );
    }

    /**
     * Call <code>public.select_burden_data5</code>.
     */
    public static Result<SelectBurdenData5Record> selectBurdenData5(
          Configuration configuration
        , Integer set1
        , Integer outcome1
        , Integer set2
        , Integer outcome2
        , Integer set3
        , Integer outcome3
        , Integer set4
        , Integer outcome4
        , Integer set5
        , Integer outcome5
    ) {
        return configuration.dsl().selectFrom(org.vaccineimpact.api.db.tables.SelectBurdenData5.SELECT_BURDEN_DATA5.call(
              set1
            , outcome1
            , set2
            , outcome2
            , set3
            , outcome3
            , set4
            , outcome4
            , set5
            , outcome5
        )).fetch();
    }

    /**
     * Get <code>public.select_burden_data5</code> as a table.
     */
    public static SelectBurdenData5 selectBurdenData5(
          Integer set1
        , Integer outcome1
        , Integer set2
        , Integer outcome2
        , Integer set3
        , Integer outcome3
        , Integer set4
        , Integer outcome4
        , Integer set5
        , Integer outcome5
    ) {
        return org.vaccineimpact.api.db.tables.SelectBurdenData5.SELECT_BURDEN_DATA5.call(
            set1,
            outcome1,
            set2,
            outcome2,
            set3,
            outcome3,
            set4,
            outcome4,
            set5,
            outcome5
        );
    }

    /**
     * Get <code>public.select_burden_data5</code> as a table.
     */
    public static SelectBurdenData5 selectBurdenData5(
          Field<Integer> set1
        , Field<Integer> outcome1
        , Field<Integer> set2
        , Field<Integer> outcome2
        , Field<Integer> set3
        , Field<Integer> outcome3
        , Field<Integer> set4
        , Field<Integer> outcome4
        , Field<Integer> set5
        , Field<Integer> outcome5
    ) {
        return org.vaccineimpact.api.db.tables.SelectBurdenData5.SELECT_BURDEN_DATA5.call(
            set1,
            outcome1,
            set2,
            outcome2,
            set3,
            outcome3,
            set4,
            outcome4,
            set5,
            outcome5
        );
    }

    /**
     * Call <code>public.select_burden_data6</code>.
     */
    public static Result<SelectBurdenData6Record> selectBurdenData6(
          Configuration configuration
        , Integer set1
        , Integer outcome1
        , Integer set2
        , Integer outcome2
        , Integer set3
        , Integer outcome3
        , Integer set4
        , Integer outcome4
        , Integer set5
        , Integer outcome5
        , Integer set6
        , Integer outcome6
    ) {
        return configuration.dsl().selectFrom(org.vaccineimpact.api.db.tables.SelectBurdenData6.SELECT_BURDEN_DATA6.call(
              set1
            , outcome1
            , set2
            , outcome2
            , set3
            , outcome3
            , set4
            , outcome4
            , set5
            , outcome5
            , set6
            , outcome6
        )).fetch();
    }

    /**
     * Get <code>public.select_burden_data6</code> as a table.
     */
    public static SelectBurdenData6 selectBurdenData6(
          Integer set1
        , Integer outcome1
        , Integer set2
        , Integer outcome2
        , Integer set3
        , Integer outcome3
        , Integer set4
        , Integer outcome4
        , Integer set5
        , Integer outcome5
        , Integer set6
        , Integer outcome6
    ) {
        return org.vaccineimpact.api.db.tables.SelectBurdenData6.SELECT_BURDEN_DATA6.call(
            set1,
            outcome1,
            set2,
            outcome2,
            set3,
            outcome3,
            set4,
            outcome4,
            set5,
            outcome5,
            set6,
            outcome6
        );
    }

    /**
     * Get <code>public.select_burden_data6</code> as a table.
     */
    public static SelectBurdenData6 selectBurdenData6(
          Field<Integer> set1
        , Field<Integer> outcome1
        , Field<Integer> set2
        , Field<Integer> outcome2
        , Field<Integer> set3
        , Field<Integer> outcome3
        , Field<Integer> set4
        , Field<Integer> outcome4
        , Field<Integer> set5
        , Field<Integer> outcome5
        , Field<Integer> set6
        , Field<Integer> outcome6
    ) {
        return org.vaccineimpact.api.db.tables.SelectBurdenData6.SELECT_BURDEN_DATA6.call(
            set1,
            outcome1,
            set2,
            outcome2,
            set3,
            outcome3,
            set4,
            outcome4,
            set5,
            outcome5,
            set6,
            outcome6
        );
    }

    /**
     * Call <code>public.select_burden_data7</code>.
     */
    public static Result<SelectBurdenData7Record> selectBurdenData7(
          Configuration configuration
        , Integer set1
        , Integer outcome1
        , Integer set2
        , Integer outcome2
        , Integer set3
        , Integer outcome3
        , Integer set4
        , Integer outcome4
        , Integer set5
        , Integer outcome5
        , Integer set6
        , Integer outcome6
        , Integer set7
        , Integer outcome7
    ) {
        return configuration.dsl().selectFrom(org.vaccineimpact.api.db.tables.SelectBurdenData7.SELECT_BURDEN_DATA7.call(
              set1
            , outcome1
            , set2
            , outcome2
            , set3
            , outcome3
            , set4
            , outcome4
            , set5
            , outcome5
            , set6
            , outcome6
            , set7
            , outcome7
        )).fetch();
    }

    /**
     * Get <code>public.select_burden_data7</code> as a table.
     */
    public static SelectBurdenData7 selectBurdenData7(
          Integer set1
        , Integer outcome1
        , Integer set2
        , Integer outcome2
        , Integer set3
        , Integer outcome3
        , Integer set4
        , Integer outcome4
        , Integer set5
        , Integer outcome5
        , Integer set6
        , Integer outcome6
        , Integer set7
        , Integer outcome7
    ) {
        return org.vaccineimpact.api.db.tables.SelectBurdenData7.SELECT_BURDEN_DATA7.call(
            set1,
            outcome1,
            set2,
            outcome2,
            set3,
            outcome3,
            set4,
            outcome4,
            set5,
            outcome5,
            set6,
            outcome6,
            set7,
            outcome7
        );
    }

    /**
     * Get <code>public.select_burden_data7</code> as a table.
     */
    public static SelectBurdenData7 selectBurdenData7(
          Field<Integer> set1
        , Field<Integer> outcome1
        , Field<Integer> set2
        , Field<Integer> outcome2
        , Field<Integer> set3
        , Field<Integer> outcome3
        , Field<Integer> set4
        , Field<Integer> outcome4
        , Field<Integer> set5
        , Field<Integer> outcome5
        , Field<Integer> set6
        , Field<Integer> outcome6
        , Field<Integer> set7
        , Field<Integer> outcome7
    ) {
        return org.vaccineimpact.api.db.tables.SelectBurdenData7.SELECT_BURDEN_DATA7.call(
            set1,
            outcome1,
            set2,
            outcome2,
            set3,
            outcome3,
            set4,
            outcome4,
            set5,
            outcome5,
            set6,
            outcome6,
            set7,
            outcome7
        );
    }

    /**
     * Call <code>public.select_burden_data8</code>.
     */
    public static Result<SelectBurdenData8Record> selectBurdenData8(
          Configuration configuration
        , Integer set1
        , Integer outcome1
        , Integer set2
        , Integer outcome2
        , Integer set3
        , Integer outcome3
        , Integer set4
        , Integer outcome4
        , Integer set5
        , Integer outcome5
        , Integer set6
        , Integer outcome6
        , Integer set7
        , Integer outcome7
        , Integer set8
        , Integer outcome8
    ) {
        return configuration.dsl().selectFrom(org.vaccineimpact.api.db.tables.SelectBurdenData8.SELECT_BURDEN_DATA8.call(
              set1
            , outcome1
            , set2
            , outcome2
            , set3
            , outcome3
            , set4
            , outcome4
            , set5
            , outcome5
            , set6
            , outcome6
            , set7
            , outcome7
            , set8
            , outcome8
        )).fetch();
    }

    /**
     * Get <code>public.select_burden_data8</code> as a table.
     */
    public static SelectBurdenData8 selectBurdenData8(
          Integer set1
        , Integer outcome1
        , Integer set2
        , Integer outcome2
        , Integer set3
        , Integer outcome3
        , Integer set4
        , Integer outcome4
        , Integer set5
        , Integer outcome5
        , Integer set6
        , Integer outcome6
        , Integer set7
        , Integer outcome7
        , Integer set8
        , Integer outcome8
    ) {
        return org.vaccineimpact.api.db.tables.SelectBurdenData8.SELECT_BURDEN_DATA8.call(
            set1,
            outcome1,
            set2,
            outcome2,
            set3,
            outcome3,
            set4,
            outcome4,
            set5,
            outcome5,
            set6,
            outcome6,
            set7,
            outcome7,
            set8,
            outcome8
        );
    }

    /**
     * Get <code>public.select_burden_data8</code> as a table.
     */
    public static SelectBurdenData8 selectBurdenData8(
          Field<Integer> set1
        , Field<Integer> outcome1
        , Field<Integer> set2
        , Field<Integer> outcome2
        , Field<Integer> set3
        , Field<Integer> outcome3
        , Field<Integer> set4
        , Field<Integer> outcome4
        , Field<Integer> set5
        , Field<Integer> outcome5
        , Field<Integer> set6
        , Field<Integer> outcome6
        , Field<Integer> set7
        , Field<Integer> outcome7
        , Field<Integer> set8
        , Field<Integer> outcome8
    ) {
        return org.vaccineimpact.api.db.tables.SelectBurdenData8.SELECT_BURDEN_DATA8.call(
            set1,
            outcome1,
            set2,
            outcome2,
            set3,
            outcome3,
            set4,
            outcome4,
            set5,
            outcome5,
            set6,
            outcome6,
            set7,
            outcome7,
            set8,
            outcome8
        );
    }

    /**
     * Call <code>public.select_burden_data_col</code>.
     */
    public static Result<SelectBurdenDataColRecord> selectBurdenDataCol(
          Configuration configuration
        , Integer setId
        , Integer outcomeId
    ) {
        return configuration.dsl().selectFrom(org.vaccineimpact.api.db.tables.SelectBurdenDataCol.SELECT_BURDEN_DATA_COL.call(
              setId
            , outcomeId
        )).fetch();
    }

    /**
     * Get <code>public.select_burden_data_col</code> as a table.
     */
    public static SelectBurdenDataCol selectBurdenDataCol(
          Integer setId
        , Integer outcomeId
    ) {
        return org.vaccineimpact.api.db.tables.SelectBurdenDataCol.SELECT_BURDEN_DATA_COL.call(
            setId,
            outcomeId
        );
    }

    /**
     * Get <code>public.select_burden_data_col</code> as a table.
     */
    public static SelectBurdenDataCol selectBurdenDataCol(
          Field<Integer> setId
        , Field<Integer> outcomeId
    ) {
        return org.vaccineimpact.api.db.tables.SelectBurdenDataCol.SELECT_BURDEN_DATA_COL.call(
            setId,
            outcomeId
        );
    }
}
