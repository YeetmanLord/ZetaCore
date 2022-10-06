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

public class NMSObjectReflection {

	protected Object nmsObject;
	

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
			
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
			e.printStackTrace();
		}
		catch (NoSuchMethodException e) {
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
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}

		}
		else {

			if (args != null) {

				try {
					constr = ReflectionApi.getNMSClass(className).getConstructor(classes);
					this.nmsObject = constr.newInstance(args);
					
				}
				catch (Exception e) {
					e.printStackTrace();
				}

			}

		}

	}

	public NMSObjectReflection(ClassNameMapping mapping, @Nullable Class<?>[] classes, Object[] args) {

		try {
			Class<?> clazz = mapping.getNMSClassMapping();
			String className = clazz.getName().replaceFirst(clazz.getPackage().getName() + ".", "");
			Constructor<?> constr;

			if (classes == null) {

				try {
					constr = ReflectionApi.getNMSClass(className).getConstructor();
					this.nmsObject = constr.newInstance();
					
				}
				catch (Exception e) {
					e.printStackTrace();
				}

			}
			else {

				if (args != null) {

					try {
						constr = ReflectionApi.getNMSClass(className).getConstructor(classes);
						this.nmsObject = constr.newInstance(args);
						
					}
					catch (Exception e) {
						e.printStackTrace();
					}

				}

			}

		}
		catch (MappingsException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param fieldName The name of a field
	 * @return Returns the <i>value</i> of a field not the field itself. To get a
	 *         {@link Field} object use {@link #getField(String)}
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
		}
		catch (IllegalArgumentException | SecurityException | IllegalAccessException e) {
			e.printStackTrace();
		}
		catch (NoSuchFieldException e) {
			throw (new NoSuchFieldException(fieldName + " is not a field in " + this.nmsObject.getClass()));
		}

		return null;

	}

	/**
	 * Runs a method and returns the return value of that method If the method is a
	 * void method will return null
	 * 
	 * @apiNote I do not believe that you can use this method to run final methods
	 *          because to my tests it does not work
	 * 
	 * @param methodName  The method to invoke
	 * @param argsClasses The classes of the arguments in the method in order.
	 * @param args        The actual arguments for the method in order.
	 * @throws NoSuchMethodException This exception is thrown when the given method
	 *                               name does not exist
	 * @return The output of the method or null if it is a void method
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
		}
		catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		catch (NoSuchMethodException e) {
			throw (new NoSuchMethodException(methodName + " is not a real method in " + this.nmsObject.getClass() + " check that the parameter classes and method name are correct."));
		}

		return null;

	}

	/**
	 * Runs a method from this reflection's associated NMS object
	 * 
	 * @apiNote I do not believe that you can use this method to run final methods
	 *          because to my tests it does not work
	 * 
	 * @param methodName methodName The method to invoke
	 * @return The output of the method or null if it is a void method
	 * @throws NoSuchMethodException This exception is thrown when the given method
	 *                               name does not exist
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
		}
		catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		catch (NoSuchMethodException e) {
			throw (new NoSuchMethodException(methodName + " is not a real method in " + this.nmsObject.getClass()));
		}

		return null;

	}

	/**
	 * @param fieldName The field you want to get
	 * @return Returns a {@link Field} object of the {@link #nmsObject}. To get the
	 *         <i>value</i> of the field use {@link #getFieldFromNmsObject(String)}
	 * @throws NoSuchFieldException This is thrown when the field doesn't exist
	 */
	public Field getField(String fieldName) throws NoSuchFieldException {

		try {
			Field field = this.nmsObject.getClass().getDeclaredField(fieldName);
			if (field == null) {
				throw (new NoSuchFieldException());
			}
			return field;
		}
		catch (IllegalArgumentException | SecurityException e) {
			e.printStackTrace();
		}
		catch (NoSuchFieldException e) {

			try {
				return this.nmsObject.getClass().getField(fieldName);
			}
			catch (NoSuchFieldException ex) {
				throw (new NoSuchFieldException(fieldName + " is not a field in " + this.nmsObject.getClass()));
			}

		}

		return null;

	}

	/**
	 * @param methodName The field you want to get
	 * @return Returns a {@link Method} object of the {@link #nmsObject}. To get the
	 *         <i>return value or run</i> this method use
	 *         {@link #invokeMethodForNmsObject(String)}
	 * @throws NoSuchMethodException This is thrown when the method doesn't exist
	 */
	public Method getMethod(String methodName) throws NoSuchMethodException {

		try {
			Method method = this.nmsObject.getClass().getDeclaredMethod(methodName);
			if (method == null) {
				throw (new NoSuchMethodException());
			}
			return method;
		}
		catch (IllegalArgumentException | SecurityException e) {
			e.printStackTrace();
		}
		catch (NoSuchMethodException e) {

			try {
				return this.nmsObject.getClass().getMethod(methodName);
			}
			catch (NoSuchMethodException ex) {
				throw (new NoSuchMethodException(methodName + " is not a method in " + this.nmsObject.getClass()));
			}

		}

		return null;

	}

	/**
	 * @param methodName The field you want to get
	 * @return Returns a {@link Method} object of the {@link #nmsObject}. To get the
	 *         <i>return value or run</i> this method use
	 *         {@link #invokeMethodForNmsObject(String, Class[], Object[])}
	 * @throws NoSuchMethodException This is thrown when the method doesn't exist
	 */
	public Method getMethod(String methodName, Class<?>[] classes) throws NoSuchMethodException {

		try {
			Method method = this.nmsObject.getClass().getDeclaredMethod(methodName, classes);
			if (method == null) {
				throw (new NoSuchMethodException());
			}
			return method;
		}
		catch (IllegalArgumentException | SecurityException e) {
			e.printStackTrace();
		}
		catch (NoSuchMethodException e) {

			try {
				return this.nmsObject.getClass().getMethod(methodName, classes);
			}
			catch (NoSuchMethodException ex) {
				throw (new NoSuchMethodException(methodName + " is not a method in " + this.nmsObject.getClass()));
			}

		}

		return null;

	}

	/**
	 * @return Returns the NMS object that is associated with this reflection
	 */
	public Object getNmsObject() {

		return nmsObject;

	}

	@Override
	public String toString() {

		HashMap<String, Object> values = new HashMap<>();
		values.put("type", this.nmsObject.getClass());
		values.put("object", nmsObject);
		return "ObjectReflection" + values.toString();

	}
	
	

}
