package com.github.yeetmanlord.reflection_api.entity;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import com.github.yeetmanlord.reflection_api.NMSObjectReflection;
import com.github.yeetmanlord.reflection_api.util.ReflectedField;
import com.github.yeetmanlord.reflection_api.ReflectionApi;
import com.github.yeetmanlord.reflection_api.exceptions.FieldReflectionExcpetion;
import com.google.common.collect.ImmutableMap;

public class NMSEntityReflection extends NMSObjectReflection {

	private NMSDataWatcherReflection dataWatcher;

	public ReflectedField<Double> locX;

	public ReflectedField<Double> locY;

	public ReflectedField<Double> locZ;

	public NMSEntityReflection(Entity entity) {

		super(entity, "getHandle");
		this.dataWatcher = new NMSDataWatcherReflection(this);

		try {
			locX = new ReflectedField<>("locX", false, false, this);
			locY = new ReflectedField<>("locY", false, false, this);
			locZ = new ReflectedField<>("locZ", false, false, this);
		}
		catch (FieldReflectionExcpetion e) {
			e.printStackTrace();
		}

	}

	public NMSEntityReflection(Object nmsEntity) {

		super(nmsEntity);

		if (staticClass.isInstance(nmsEntity)) {
			this.dataWatcher = new NMSDataWatcherReflection(this);

			try {

				try {
					locX = new ReflectedField<>("locX", false, false, this);
					locY = new ReflectedField<>("locY", false, false, this);
					locZ = new ReflectedField<>("locZ", false, false, this);
				}
				catch (FieldReflectionExcpetion e) {
					e.printStackTrace();
				}

			}
			catch (IllegalArgumentException | SecurityException e) {
				throw (new IllegalArgumentException(nmsEntity.toString() + " does not the correct data"));
			}

		}
		else {
			throw (new IllegalArgumentException(nmsEntity.toString() + " is not an instance of net.minecraft.server.Entity"));
		}

	}

	public void setLocation(double x, double y, double z, float yaw, float pitch) {

		try {
			nmsObject.getClass().getMethod("setLocation", double.class, double.class, double.class, float.class, float.class).invoke(nmsObject, x, y, z, yaw, pitch);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setLocation(Location location) {

		setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

	}

	public NMSDataWatcherReflection getDataWatcher() {

		return this.dataWatcher;

	}

	public Object getNmsEntity() {

		return this.nmsObject;

	}

	public int getId() {

		try {
			Method getId = nmsObject.getClass().getMethod("getId");
			return (int) getId.invoke(nmsObject);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return 0;

	}

	public String getName() {

		try {
			Method getName = nmsObject.getClass().getMethod("getName");
			return (String) getName.invoke(nmsObject);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	@Override
	public String toString() {

		HashMap<String, Object> values = new HashMap<>();
		values.put("type", nmsObject.getClass());
		values.put("entity", this.nmsObject);

		try {
			values.put("location", ImmutableMap.of("x", locX.get(), "y", locY.get(), "z", locZ.get()));
		}
		catch (FieldReflectionExcpetion e) {
			e.printStackTrace();
		}

		return "EntityReflection" + values.toString();

	}

	public static final Class<?> staticClass = ReflectionApi.getNMSClass("Entity");

	public static NMSEntityReflection cast(NMSObjectReflection refl) {

		if (staticClass.isInstance(refl.getNmsObject())) {
			return new NMSEntityReflection(refl.getNmsObject());
		}

		throw new ClassCastException("Cannot cast " + refl.toString() + " to NMSEntityReflection");

	}

	public NMSAxisAlignedBBReflection getBoundingBox() {

		try {
			return new NMSAxisAlignedBBReflection(this.invokeMethodForNmsObject("getBoundingBox"));
		}
		catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		return null;

	}

}
