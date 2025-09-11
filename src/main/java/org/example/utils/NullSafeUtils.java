package org.example.utils;

import org.example.exception.AppException;
import org.example.exception.BaseErrorEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class NullSafeUtils extends ObjectUtils {
    private static final Logger log = LoggerFactory.getLogger(NullSafeUtils.class);

    private NullSafeUtils() {
        throw new UnsupportedOperationException("Support only static methods.");
    }

    public static boolean ifAmNotNull(Object object) {
        return !isAmNullOrEmpty(object);
    }

    public static List<Long> convertStringListToLong(List<String> strings) {
        if (ifAmNotNull(strings)) {
            return strings.stream().filter(StringUtils::isNumeric).map(Long::parseLong).collect(Collectors.toList());
        }
        return new ArrayList<>(0);
    }

    public static List<Integer> convertStringListToInteger(List<String> strings) {
        if (ifAmNotNull(strings)) {
            return strings.stream().filter(StringUtils::isNumeric).map(Integer::parseInt).collect(Collectors.toList());
        }
        return new ArrayList<>(0);
    }

    public static boolean isAmNullOrEmpty(Object object) {
        if (null == object) {
            return true;
        } else if (object instanceof MultipartFile) {
            return ((MultipartFile) object).isEmpty();
        } else if (object instanceof String) {
            return StringUtils.isBlank((String) object);
        } else {
            return ObjectUtils.isEmpty(object);
        }
    }

    public static int parseInt(String intStr, int defaultValue) {
        if (StringUtils.isBlank(intStr) || !StringUtils.isNumeric(intStr)) {
            return defaultValue;
        } else
            return Integer.parseInt(intStr);
    }

    public static int parseInt(String intStr) {
        if (StringUtils.isBlank(intStr) || !StringUtils.isNumeric(intStr)) {
            throw new AppException("please use numeric values only.");
        } else
            return Integer.parseInt(intStr);
    }

    public static long parseLong(String aLongStr) {
        if (StringUtils.isBlank(aLongStr) || !StringUtils.isNumeric(aLongStr)) {
            throw new AppException("please use numeric values only.");
        } else
            return Long.parseLong(aLongStr);
    }

    public static <T> T nullableSingleResult(@Nullable Collection<T> results) {
        if (isAmNullOrEmpty(results)) {
            return null;
        } else if (results.size() > 1) {
            throw new AppException("409", "More than one record found.");
        } else {
            return results.iterator().next();
        }
    }


    public static boolean parseBoolean(String boolValue, boolean defaultValue) {
        if (boolValue == null) {
            return defaultValue;
        }
        return Boolean.parseBoolean(boolValue);
    }

    public static String parseString(Object aObject, String defaultValue) {
        if (aObject == null) {
            return defaultValue;
        } else if (aObject instanceof String && StringUtils.isBlank((String) aObject)) {
            return defaultValue;
        } else if (aObject instanceof String && StringUtils.isNoneBlank((String) aObject)) {
            return ((String) aObject).trim();
        } else if (aObject instanceof Number) {
            return String.valueOf(aObject);
        } else if (aObject instanceof Date) {
            return DateUtils.dateInDmsFormatForReport((Date) aObject);
        } else return aObject.toString();
    }

    public static boolean parseBoolean(String boolValue) {
        return Boolean.parseBoolean(boolValue);
    }

    public static boolean isValidNaturalNo(Integer aInteger) {
        return ifAmNotNull(aInteger) && aInteger > 0;
    }

    public static boolean isValidWholeNo(Integer aInteger) {
        return ifAmNotNull(aInteger) && aInteger >= 0;
    }

    public static boolean isValidNaturalNo(Long aLong) {
        return ifAmNotNull(aLong) && aLong > 0L;
    }

    public static boolean isValidNaturalNo(String aInteger) {
        return isValidNaturalNo(parseInt(aInteger));
    }

    public static boolean isValidNaturalLongNo(String aInteger) {
        return isValidNaturalNo(parseLong(aInteger));
    }

}
