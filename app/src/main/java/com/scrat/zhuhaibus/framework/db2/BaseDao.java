package com.scrat.zhuhaibus.framework.db2;

import android.database.sqlite.SQLiteDatabase;

import com.scrat.zhuhaibus.framework.db.SQLiteManager;
import com.scrat.zhuhaibus.framework.db2.annotation.Column;
import com.scrat.zhuhaibus.framework.db2.annotation.Ignore;
import com.scrat.zhuhaibus.framework.db2.annotation.PrimaryKey;
import com.scrat.zhuhaibus.framework.db2.annotation.Table;
import com.scrat.zhuhaibus.framework.db2.annotation.Unique;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class BaseDao<Bean> implements SQLiteManager.SQLiteTable {
    private static final Map<String, String> COLUMN_TYPE_MAP = new HashMap<String, String>(){{
        put("String", "text");
        put("int", "integer");
        put("Integer", "integer");
        put("long", "long");
        put("Long", "long");
        put("float", "float");
        put("Float", "float");
        put("double", "double");
        put("Double", "double");
        put("boolean", "integer");
        put("Boolean", "integer");
    }};

    private Class<Bean> entityClass;

    public BaseDao() {
        Type[] types = ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments();
        entityClass = (Class<Bean>) types[0];
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Table table = null;
        String tableName = null;
        if (entityClass.isAnnotationPresent(Table.class)) {
            table = entityClass.getAnnotation(Table.class);
            if (!"".equals(table.tableName())) {
                tableName = table.tableName();
            }
        }
        if (tableName == null) {
            tableName = entityClass.getSimpleName();
        }
        StringBuilder createTableSqlSb = new StringBuilder()
                .append("create table ").append(tableName).append('(');
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {

            if (field.isAnnotationPresent(Ignore.class)) {
                continue;
            }

            String columnName = field.getName();
            String type = null;
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                if (!"".equals(column.colName())) {
                    columnName = column.colName();
                }
                if (!"".equals(column.colType())) {
                    type = column.colType();
                }
            }
            if (type == null) {
                type = COLUMN_TYPE_MAP.get(field.getType().getSimpleName());
            }
            createTableSqlSb.append(columnName).append(' ').append(type);
            if (field.isAnnotationPresent(PrimaryKey.class)) {
                PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
                createTableSqlSb.append(" primary key");
                if (primaryKey.autoincrement()) {
                    createTableSqlSb.append(" autoincrement");
                }
            }
            createTableSqlSb.append(',');
        }

        if (table != null) {
            createTableSqlSb.append(' ');
            Unique[] uniqueArr = table.uniques();
            if (uniqueArr.length > 0) {
                for (Unique unique : uniqueArr) {
                    String[] columns = unique.columnNames();
                    if (columns.length == 0) {
                        continue;
                    }
                    createTableSqlSb.append("unique (");
                    for (String column : columns) {
                        createTableSqlSb.append(column).append(',');
                    }
                    createTableSqlSb.deleteCharAt(createTableSqlSb.lastIndexOf(","));
                    createTableSqlSb.append("),");
                }
            }
        }
        createTableSqlSb.deleteCharAt(createTableSqlSb.lastIndexOf(","));
        createTableSqlSb.append(')');
        String createTableSql = createTableSqlSb.toString();
        database.execSQL(createTableSql);
    }

    @Override
    public void onUpdate(SQLiteDatabase database, int oldVersion, int newVersion) {
        // TODO
    }
}
