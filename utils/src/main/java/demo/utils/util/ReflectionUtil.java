package demo.utils.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import lombok.experimental.UtilityClass;

/**
 * ReflectionUtil
 *
 * @author Wenzhou
 * @since 2023/6/16 11:06
 */
@UtilityClass
public final class ReflectionUtil {
    public static Class<?> getSupperClassGenericType(Class<?> clazz, int index) {
        Type genType = clazz.getGenericSuperclass();
        AssertUtil.assertTrue(genType instanceof ParameterizedType,
                String.format("Warn: %s's superclass not ParameterizedType .", clazz.getSimpleName()));
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        AssertUtil.assertTrue(index >= 0 && index < params.length,
                String.format("Warn: Index: %s, Size of %s's Parameterized Length: %s .",
                        index, clazz.getSimpleName(), params.length));
        AssertUtil.assertTrue(params[index] instanceof Class,
                String.format("Warn: %s not set the actual class on superclass generic parameter .",
                        clazz.getSimpleName()));
        return ((Class) params[index]);
    }

    public static Class<?> getSuperClassGenericType(Class<?> clazz, Class<?> findToParentClass, int index) {
        Class<?> currClass = clazz;
        for (Class parentClass = clazz.getSuperclass();
             !findToParentClass.equals(parentClass) && !Object.class.equals(parentClass);
             parentClass = parentClass.getSuperclass()) {
            currClass = parentClass;
        }
        return getSupperClassGenericType(currClass, index);
    }

}
