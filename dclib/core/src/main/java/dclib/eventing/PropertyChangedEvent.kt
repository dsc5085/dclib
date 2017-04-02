package dclib.eventing

data class PropertyChangedEvent<T>(val newValue: T)