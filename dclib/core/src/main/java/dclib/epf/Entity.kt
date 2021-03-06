package dclib.epf

import kotlin.reflect.KClass

/**
 * Made up of parts that provide state for the entity.
 * There can only be one of each part type attached.
 * @author David Chen
 */
// TODO: Get rid of vararg methods from the whole project?
class Entity(vararg parts: Any) {
    /**
     * @return if the entity should be updated
     */
    /**
     * Sets the entity to be active or inactive.
     * @param isActive true to make the entity active or false to make it inactive
     */
    var isActive = false

    private val attributes = mutableSetOf<Enum<*>>()
    private val parts = mutableMapOf<KClass<*>, Any>()

    init {
        attach(*parts)
    }

    /**
     * @param attributes unique descriptors
     * *
     * @return if the entity has all the attributes
     */
    fun of(vararg attributes: Enum<*>): Boolean {
        return this.attributes.containsAll(listOf(*attributes))
    }

    fun <T : Enum<*>> getAttribute(attributeClass: KClass<T>): T? {
        return getAttributes(attributeClass).firstOrNull()
    }

    fun getAttributes(): Set<Enum<*>> {
        return attributes.toSet()
    }

    fun <T : Enum<*>> getAttributes(attributeClass: KClass<T>): List<T> {
        return attributes.filterIsInstance(attributeClass.java)
    }

    /**
     * Adds attributes to the entity
     * @param attributes unique descriptors
     */
    fun addAttributes(vararg attributes: Enum<*>) {
        this.attributes.addAll(listOf(*attributes))
    }

    /**
     * Removes attributes from the entity
     * @param attributes unique descriptors
     */
    fun <T : Enum<*>> removeAttributes(attributeClass: KClass<T>) {
        attributes.removeAll(getAttributes(attributeClass))
    }

    /**
     * @param partClass the classes of the parts to check
     * *
     * @return if the entity contains a part corresponding to all the part classes
     */
    fun has(vararg partClasses: Class<*>): Boolean {
        return has(*partClasses.map { it.kotlin }.toTypedArray())
    }

    /**
     * @param partClass the classes of the parts to check
     * *
     * @return if the entity contains a part corresponding to all the part classes
     */
    fun has(vararg partClasses: KClass<*>): Boolean {
        return partClasses.all { parts.containsKey(it) }
    }

    fun <T : Any> tryGet(partClass: Class<T>): T? {
        return tryGet(partClass.kotlin)
    }

    fun <T : Any> tryGet(partClass: KClass<T>): T? {
        return if (has(partClass)) get(partClass) else null
    }

    /**
     * @param partClass the class of the part to get
     * *
     * @return the part attached to the entity of type T
     * *
     * @throws IllegalArgumentException if there is no part of type T attached to the entity
     */
    operator fun <T : Any> get(partClass: Class<T>): T {
        return get(partClass.kotlin)
    }

    /**
     * @param partClass the class of the part to get
     * *
     * @return the part attached to the entity of type T
     * *
     * @throws IllegalArgumentException if there is no part of type T attached to the entity
     */
    @Suppress("UNCHECKED_CAST")
    operator fun <T : Any> get(partClass: KClass<T>): T {
        if (!has(partClass)) {
            throw IllegalArgumentException("Part of type ${partClass.simpleName} could not be found.")
        }
        return parts[partClass] as T
    }

    /**
     * @return a copy of the list of all parts the entity is composed of
     */
    fun getAll(): List<Any> {
        return parts.values.toList()
    }

    /**
     * Adds a part.
     * @param part the part
     */
    fun attach(vararg parts: Any) {
        for (part in parts) {
            if (has(part.javaClass.kotlin)) {
                throw IllegalArgumentException("Part of type " + part.javaClass.name + " is already attached.")
            }
            this.parts.put(part.javaClass.kotlin, part)
        }
    }

    /**
     * Removes a part of type T if it exists.
     * @param partClass the class of the part to remove
     */
    fun detach(partClass: Class<*>) {
        parts.remove(partClass.kotlin)
    }

    /**
     * Removes a part of type T if it exists.
     * @param partClass the class of the part to remove
     */
    fun detach(partClass: KClass<*>) {
        parts.remove(partClass)
    }
}