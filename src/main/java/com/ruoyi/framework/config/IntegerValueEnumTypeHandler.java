package com.ruoyi.framework.config;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class IntegerValueEnumTypeHandler <T extends IntegerValueEnum> extends BaseTypeHandler<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(IntegerValueEnumTypeHandler.class);

    private Class<T> type;

    private Map<Integer, T> valueEnumMap = new HashMap();

    public IntegerValueEnumTypeHandler(Class<T> type) {
        if (null == type) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;

        if (!type.isEnum()) {
            LOGGER.warn("{} is NOT an enum type. might lead to error in future", type.getName());
            return;
        }
        T[] enums = type.getEnumConstants();
        if (ArrayUtils.isEmpty(enums)) {
            throw new IllegalArgumentException(type.getSimpleName() + " did NOT declear any enum value.");
        }

        for (T e : enums) {
            valueEnumMap.put(e.getValue(), e);
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if (rs.wasNull()) {
            return null;
        }
        return getValueEnum(rs.getInt(columnName));
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if (rs.wasNull()) {
            return null;
        }
        return getValueEnum(rs.getInt(columnIndex));
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (cs.wasNull()) {
            return null;
        }
        return getValueEnum(cs.getInt(columnIndex));
    }

    private T getValueEnum(Integer value) {
        try {
            return valueEnumMap.get(value);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Cannot convert " + value + " to " + type.getSimpleName() + " by value.", ex);
        }
    }
}
