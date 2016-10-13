package dclib.epf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dclib.util.CollectionUtils;

/**
 * Made up of parts that provide state for the entity.
 * There can only be one of each part type attached.
 * @author David Chen
 *
 */
public class Entity {

	private boolean isActive = false;
	private final Set<Enum<?>> attributes = new HashSet<Enum<?>>();
	private final Map<Class<?>, Object> parts = new HashMap<Class<?>, Object>();

	/**
	 * @return if the entity should be updated
	 */
	public final boolean isActive() {
		return isActive;
	}

	/**
	 * Sets the entity to be active or inactive.
	 * @param isActive true to make the entity active or false to make it inactive
	 */
	public final void setActive(final boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @param attributes unique descriptors
	 * @return if the entity has all the attributes
	 */
	public final boolean is(final Enum<?>...attributes) {
		return CollectionUtils.containsAll(this.attributes, Arrays.asList(attributes));
	}

	public final Set<Enum<?>> getAttributes() {
		return new HashSet<Enum<?>>(attributes);
	}

	/**
	 * adds attributes to the entity
	 * @param attributes unique descriptors
	 */
	public final void attribute(final Enum<?>...attributes) {
		Collections.addAll(this.attributes, attributes);
	}

	/**
	 * @param partClass the classes of the parts to check
	 * @return if there are parts attached to the entity
	 */
	public final boolean has(final Class<?>...partClasses) {
		for (Class<?> partClass : partClasses) {
			if (!parts.containsKey(partClass)) {
				return false;
			}
		}
		return true;
	}

	public final <T> T tryGet(final Class<T> partClass) {
		return has(partClass) ? get(partClass) : null;
	}

	/**
	 * @param partClass the class of the part to get
	 * @return the part attached to the entity of type T
	 * @throws IllegalArgumentException if there is no part of type T attached to the entity
	 */
	@SuppressWarnings("unchecked")
	public final <T> T get(final Class<T> partClass) {
		if (!has(partClass)) {
			throw new IllegalArgumentException("Part of type " + partClass.getName() + " could not be found.");
		}
		return (T)parts.get(partClass);
	}

	/**
	 * @return a copy of the list of all parts the entity is composed of
	 */
	public final List<Object> getAll() {
		List<Object> partObjects = new ArrayList<Object>();
		for (Object part : parts.values()) {
			partObjects.add(part);
		}
		return partObjects;
	}

	/**
	 * Adds a part.
	 * @param part the part
	 */
	public final void attach(final Object...parts) {
		for (Object part : parts) {
			if (has(part.getClass())) {
				throw new IllegalArgumentException("Part of type " + part.getClass().getName()
						+ " is already attached.");
			}
			this.parts.put(part.getClass(), part);
		}
	}

	/**
	 * Removes a part of type T if it exists.
	 * @param partClass the class of the part to remove
	 */
	public final void detach(final Class<?> partClass) {
		parts.remove(partClass);
	}

}
