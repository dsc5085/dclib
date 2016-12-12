package dclib.util

object CollectionUtils {
    fun <T> getOrAdd(set: MutableSet<T>, value: T): T {
        if (!set.contains(value)) {
            set.add(value)
        }
        return set.single { it == value }
    }
}
