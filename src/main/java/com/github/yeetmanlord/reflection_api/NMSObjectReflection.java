package com.github.yeetmanlord.reflection_api;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.yeetmanlord.reflection_api.exceptions.MappingsException;
import com.github.yeetmanlord.reflection_api.mappings.types.ClassNameMapping;
import com.github.yeetmanlord.reflection_api.mappings.types.PackageMapping;

/**
 * @implNote If you were to ever extend this class and create mappings for it, I recommend following this rule:
 * <ul>You should have a public static field called `staticClass` which is the NMS class that object refers to. This is for mappings testing.<br>
 * In general, it's also good practice since it makes code more concise.</ul>
 */
public class NMSObjectReflection {

    protected Object nmsObject;

    /**
     * For reflecting NMS objects such as packets, entities, etc. This constructor takes an actual nms object and wraps it
     *
     * @param nmsObject Object to wrap
     */

    public NMSObjectReflection(Object nmsObject) {

        this.nmsObject = nmsObject;

    }

    /**
     * Will create a reflection based getting the nms equivalent of a Bukkit object
     * Despite bukkitObject being an object it still must be an actual Bukkit object
     * with the method being the method to get the nms equivalent
     *
     * @param bukkitObject A Bukkit object that you can get an NMS equivalent with a
     *                     method
     * @param methodName   The name of the method to get an NMS equivalent of a
     *                     Bukkit object
     * @throws NoSuchMethodException throws when given method doesn't exist
     */
    public NMSObjectReflection(Object bukkitObject, String methodName) {

        try {
            this.nmsObject = bukkitObject.getClass().getMethod(methodName).invoke(bukkitObject);

        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            System.err.println(methodName + " is not a valid method to get an nms object!");
        }

    }

    /**
     * This creates a reflections using a classes constructor
     */
    public NMSObjectReflection(String className, @Nullable Class<?>[] classes, Object[] args) {

        Constructor<?> constr;

        if (classes == null) {

            try {
                constr = ReflectionApi.getNMSClass(className).getConstructor();
                this.nmsObject = constr.newInstance();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            if (args != null) {

                try {
                    constr = ReflectionApi.getNMSClass(className).getConstructor(classes);
                    this.nmsObject = constr.newInstance(args);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }

    }

    public NMSObjectReflection(PackageMapping mapping, String className, @Nullable Class<?>[] classes, Object[] args) {
        this(getClassName(mapping, className), classes, args);
    }

    private static String getClassName(PackageMapping mapping, String className) {
        try {
            return mapping.getNMSSubPackage() + className;
        } catch (MappingsException e) {
            e.printStackTrace();
        }
        return className;
    }

    public NMSObjectReflection(ClassNameMapping mapping, @Nullable Class<?>[] classes, Object[] args) {

        try {
            Class<?> clazz = mapping.getNMSClassMapping();
            Constructor<?> constr;

            if (classes == null) {

                try {
                    constr = clazz.getConstructor();
                    this.nmsObject = constr.newInstance();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {

                if (args != null) {

                    try {
                        constr = clazz.getConstructor(classes);
                        this.nmsObject = constr.newInstance(args);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

        } catch (MappingsException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param fieldName The name of a field
     * @return Returns the <i>value</i> of a field not the field itself. To get a
     * {@link Field} object use {@link #getField(String)}
     * @throws NoSuchFieldException This is thrown when there is no existing field
     *                              with that name
     */
    public Object getFieldFromNmsObject(String fieldName) throws NoSuchFieldException {

        try {
            Field field = getField(fieldName);
            field.setAccessible(true);
            Object value = field.get(nmsObject);
            field.setAccessible(false);
            return value;
        } catch (IllegalArgumentException | SecurityException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            throw (new NoSuchFieldException(fieldName + " is not a field in " + this.nmsObject.getClass()));
        }

        return null;

    }

    /**
     * Runs a method and returns the return value of that method If the method is a
     * void method will return null
     *
     * @param methodName  The method to invoke
     * @param argsClasses The classes of the arguments in the method in order.
     * @param args        The actual arguments for the method in order.
     * @return The output of the method or null if it is a void method
     * @throws NoSuchMethodException This exception is thrown when the given method
     *                               name does not exist
     * @apiNote I do not believe that you can use this method to run final methods
     * because to my tests it does not work
     */
    public Object invokeMethodForNmsObject(String methodName, @Nonnull Class<?>[] argsClasses, @Nonnull Object[] args) throws NoSuchMethodException {

        Method method;

        try {
            method = getMethod(methodName, argsClasses);
            method.setAccessible(true);

            if (method.getReturnType().equals(void.class)) {
                method.invoke(this.nmsObject, args);
                method.setAccessible(false);
                return null;
            }

            Object value = method.invoke(this.nmsObject, args);
            method.setAccessible(false);
            return value;
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            throw (new NoSuchMethodException(methodName + " is not a real method in " + this.nmsObject.getClass() + " check that the parameter classes and method name are correct."));
        }

        return null;

    }

    /**
     * Runs a method from this reflection's associated NMS object
     *
     * @param methodName methodName The method to invoke
     * @return The output of the method or null if it is a void method
     * @throws NoSuchMethodException This exception is thrown when the given method
     *                               name does not exist
     * @apiNote I do not believe that you can use this method to run final methods
     * because to my tests it does not work
     */
    public Object invokeMethodForNmsObject(String methodName) throws NoSuchMethodException {

        Method method;

        try {
            method = getMethod(methodName);
            method.setAccessible(true);

            if (method.getReturnType().equals(void.class)) {
                method.invoke(this.nmsObject);
                method.setAccessible(false);
                return null;
            }

            Object value = method.invoke(this.nmsObject);
            method.setAccessible(false);
            return value;
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            throw (new NoSuchMethodException(methodName + " is not a real method in " + this.nmsObject.getClass()));
        }

        return null;

    }

    /**
     * @param fieldName The field you want to get
     * @return Returns a {@link Field} object of the {@link #nmsObject}. To get the
     * <i>value</i> of the field use {@link #getFieldFromNmsObject(String)}
     * @throws NoSuchFieldException This is thrown when the field doesn't exist
     */
    public Field getField(String fieldName) throws NoSuchFieldException {

        try {
            return this.nmsObject.getClass().getDeclaredField(fieldName);
        } catch (IllegalArgumentException | SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {

            try {
                return this.nmsObject.getClass().getField(fieldName);
            } catch (NoSuchFieldException ex) {
                throw (new NoSuchFieldException(fieldName + " is not a field in " + this.nmsObject.getClass()));
            }

        }

        return null;

    }

    /**
     * @param methodName The field you want to get
     * @return Returns a {@link Method} object of the {@link #nmsObject}. To get the
     * <i>return value or run</i> this method use
     * {@link #invokeMethodForNmsObject(String)}
     * @throws NoSuchMethodException This is thrown when the method doesn't exist
     */
    public Method getMethod(String methodName) throws NoSuchMethodException {

        try {
            Method method = this.nmsObject.getClass().getDeclaredMethod(methodName);
            if (method == null) {
                throw (new NoSuchMethodException());
            }
            return method;
        } catch (IllegalArgumentException | SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {

            try {
                return this.nmsObject.getClass().getMethod(methodName);
            } catch (NoSuchMethodException ex) {
                throw (new NoSuchMethodException(methodName + " is not a method in " + this.nmsObject.getClass()));
            }

        }

        return null;

    }

    /**
     * @param methodName The field you want to get
     * @return Returns a {@link Method} object of the {@link #nmsObject}. To get the
     * <i>return value or run</i> this method use
     * {@link #invokeMethodForNmsObject(String, Class[], Object[])}
     * @throws NoSuchMethodException This is thrown when the method doesn't exist
     */
    public Method getMethod(String methodName, Class<?>[] classes) throws NoSuchMethodException {

        try {
            Method method = this.nmsObject.getClass().getDeclaredMethod(methodName, classes);
            if (method == null) {
                throw (new NoSuchMethodException());
            }
            return method;
        } catch (IllegalArgumentException | SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {

            try {
                return this.nmsObject.getClass().getMethod(methodName, classes);
            } catch (NoSuchMethodException ex) {
                throw (new NoSuchMethodException(methodName + " is not a method in " + this.nmsObject.getClass()));
            }

        }

        return null;

    }

    /**
     * @return Returns the NMS object that is associated with this reflection
     */
    public Object getNMSObject() {

        return nmsObject;

    }

    @Override
    public String toString() {

        HashMap<String, Object> values = new HashMap<>();
        values.put("type", this.nmsObject.getClass());
        values.put("object", nmsObject);
        return "ObjectReflection" + values.toString();

    }

    public static class ImplementationException extends Exception {

        public ImplementationException(String message) {
            super(message);
        }

        public ImplementationException(Class<? extends NMSObjectReflection> clazz, String message) {
            this(clazz.getSimpleName() + " - " + message);
        }

        public ImplementationException(Class<? extends NMSObjectReflection> clazz) {
            this(clazz.getSimpleName() + " - " + "This class does not follow the implementation rules. Please contact the developer or check the documentation.");
        }

    }

}
