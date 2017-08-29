package dclib.eventing

class Property<T>(defaultValue: T) {
    val changed = EventDelegate<PropertyChangedEvent<T>>()

    private var _value = defaultValue
    var value
        get() = _value
        set(value) {
            if (_value != value) {
                changed.notify(PropertyChangedEvent(value))
                _value = value
            }
        }
}