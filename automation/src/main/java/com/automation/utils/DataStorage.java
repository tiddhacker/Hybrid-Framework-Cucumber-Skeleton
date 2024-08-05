package com.automation.utils;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.concurrent.ConcurrentHashMap;

public class DataStorage {
    protected static final Logger log = LogManager.getLogger(DataStorage.class);

    private final Map<String, Object> storeValues = new ConcurrentHashMap<>();
    private final Map<String, List<HashMap<String, String>>> storeValuesList = new ConcurrentHashMap<>();
    private final Map<String, Map<String, Map<String, String>>> storeValuesMap = new ConcurrentHashMap<>();
    private final Map<Long, Map<String, Object>> valuesMap = new ConcurrentHashMap<>();
    private final Map<String, Map<String, String>> dataMap = new ConcurrentHashMap<>();
    private final Map<String, Map<String, Object>> dataMapObject = new ConcurrentHashMap<>();

    private final Map<Long, Map<String, List<HashMap<String, String>>>> paraListMaps = new ConcurrentHashMap<>();
    private final Map<Long, Map<String, Map<String, Map<String, String>>>> paraStoreValuesHashMap = new ConcurrentHashMap<>();
    private final Map<String, List<String>> storeMapOfList = new ConcurrentHashMap<>();
    private final Map<Long, Map<String, List<String>>> threadMapOfList = new ConcurrentHashMap<>();

    public void cleanStorage() {
        log.debug("Clean all the Stored values");
        storeValues.clear();
        storeValuesMap.clear();
        valuesMap.put(Thread.currentThread().getId(), new ConcurrentHashMap<>());

        paraListMaps.put(Thread.currentThread().getId(), new ConcurrentHashMap<>());
        paraStoreValuesHashMap.put(Thread.currentThread().getId(), new ConcurrentHashMap<>());
        threadMapOfList.put(Thread.currentThread().getId(), new ConcurrentHashMap<>());
    }

    public Set<String> getStoredKeys() {
        return storeValues.keySet();
    }

    public void put(String key, Object val) {
        Preconditions.checkNotNull(key, "Key cannot be null");
        storeValues.put(key, val);
        valuesMap.computeIfAbsent(Thread.currentThread().getId(), k -> new ConcurrentHashMap<>()).put(key, val);
    }

    public void putMap(String key, Map<String, Map<String, String>> val) {
        Preconditions.checkNotNull(key, "Key cannot be null");
        storeValuesMap.put(key, val);
        paraStoreValuesHashMap.computeIfAbsent(Thread.currentThread().getId(), k -> new ConcurrentHashMap<>()).put(key, val);
    }

    public void removeFromMemory(String key) {
        storeValues.remove(key);
        Map<String, Object> currentValues = valuesMap.get(Thread.currentThread().getId());
        if (currentValues != null) {
            currentValues.remove(key);
        }
    }

    public Object get(String key) {
        Map<String, Object> currentValues = valuesMap.get(Thread.currentThread().getId());
        return (currentValues != null) ? currentValues.get(key) : null;
    }

    public <T> T get(String key, Class<T> type) {
        Object val = get(key);
        Preconditions.checkArgument(type.isInstance(val), getTypeErrorMessage(key, val, type));
        return type.cast(val);
    }

    public Object parseVar(String v) {
        if (v == null) return null;

        if (v.startsWith("$")) return get(v.substring(1));
        if (v.startsWith("\\$")) return v.substring(1);

        return v;
    }

    public Object parseVar(String v, boolean nullable) {
        Object res = parseVar(v);
        return nullable ? res : Preconditions.checkNotNull(res, "Variable is null. Key: '%s'", v);
    }

    public Long parseLong(String v) {
        return parseVar(v, Long.class, o -> NumberUtils.toLong(String.valueOf(o)));
    }

    public Integer parseInteger(String v) {
        return parseVar(v, Integer.class, o -> NumberUtils.toInt(String.valueOf(o)));
    }

    public Double parseDouble(String v) {
        return parseVar(v, Double.class, o -> NumberUtils.toDouble(String.valueOf(o)));
    }

    public synchronized String parseString(String v) {
        return parseVar(v, String.class, String::valueOf);
    }

    public Boolean parseBoolean(String v) {
        return parseVar(v, Boolean.class, o -> Boolean.valueOf(String.valueOf(o)));
    }

    public <T> T parseVar(String v, Class<T> type) {
        return parseVar(v, type, false);
    }

    public <T> T parseVar(String v, Class<T> type, boolean nullable) {
        T val = parseVar(v, type, o -> {
            throw new IllegalArgumentException(getTypeErrorMessage(v, o, type));
        });
        return nullable ? val : Preconditions.checkNotNull(val, "Null variable for Key = " + v);
    }

    private String getTypeErrorMessage(String key, Object val, Class<?> expType) {
        return String.format("Wrong type passed. Actual: %s. Expected: %s. Var name: '%s'. Var val: '%s'",
                (val != null ? val.getClass() : "null"), expType, key, val);
    }

    public synchronized <T> T parseVar(String v, Class<T> type, Function<Object, T> converter) {
        Object o = parseVar(v);
        if (o == null) return null;
        if (ClassUtils.isAssignable(o.getClass(), type)) return type.cast(o);
        return converter.apply(o);
    }

    public Map<String, Map<String, String>> parseStringMap(String v) {
        return paraStoreValuesHashMap.getOrDefault(Thread.currentThread().getId(), new ConcurrentHashMap<>()).get(v);
    }

    public void putListMap(String key, List<HashMap<String, String>> val) {
        storeValuesList.put(key, val);
        paraListMaps.computeIfAbsent(Thread.currentThread().getId(), k -> new ConcurrentHashMap<>()).put(key, val);
    }

    public void putList(String key, List<String> val) {
        storeMapOfList.put(key, val);
        threadMapOfList.computeIfAbsent(Thread.currentThread().getId(), k -> new ConcurrentHashMap<>()).put(key, val);
    }

    public List<String> getList(String v) {
        return threadMapOfList.getOrDefault(Thread.currentThread().getId(), new ConcurrentHashMap<>()).get(v);
    }

    public List<HashMap<String, String>> getListMap(String v) {
        return paraListMaps.getOrDefault(Thread.currentThread().getId(), new ConcurrentHashMap<>()).get(v);
    }

    public void putData(String key, Map<String, String> val) {
        Preconditions.checkNotNull(key, "Key cannot be null");
        dataMap.put(key, val);
        valuesMap.computeIfAbsent(Thread.currentThread().getId(), k -> new ConcurrentHashMap<>()).put(key, val);
    }

    public synchronized Map<String, String> getDataMap(String v) {
        return dataMap.get(v);
    }

    public Map<String, Map<String, String>> getMapData(String v) {
        return storeValuesMap.get(v);
    }

    public void putDataObject(String key, Map<String, Object> val) {
        Preconditions.checkNotNull(key, "Key cannot be null");
        dataMapObject.put(key, val);
        valuesMap.computeIfAbsent(Thread.currentThread().getId(), k -> new ConcurrentHashMap<>()).put(key, val);
    }

    public Map<String, Object> getDataMapObject(String v) {
        return dataMapObject.get(v);
    }
}
