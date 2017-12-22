package dclib.util

import kotlin.reflect.KClass

object CollectionUtils {
    fun <TParent : Any, TChild : TParent> getByType(iterable: Iterable<TParent>, childClass: KClass<TChild>)
            : List<TChild> {
        return iterable.filter { it.javaClass == childClass.java }.map {
            @Suppress("UNCHECKED_CAST")
            it as TChild
        }
    }

    fun <T> getOrAdd(set: MutableSet<T>, value: T): T {
        if (!set.contains(value)) {
            set.add(value)
        }
        return set.single { it == value }
    }
}
