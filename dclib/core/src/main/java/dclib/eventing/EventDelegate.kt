package dclib.eventing

class EventDelegate<T> {
    var handlers = listOf<(T) -> Unit>()

    infix fun on(handler: (T) -> Unit) {
        handlers += handler
    }
	
	infix fun remove(handler: (T) -> Unit) {
		handlers -= handler
	}

    fun notify(event: T) {
        for (handler in handlers) {
            handler(event)
        }
    }
}