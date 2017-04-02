package dclib.eventing

class Property<T>(defaultValue: T) {
    val changed = EventDelegate<PropertyChangedEvent<T>>()

    private var _value = defaultValue
    var value
        get() = _value
        set(value) {
            _value = value
            changed.notify(PropertyChangedEvent(value))
        }
}