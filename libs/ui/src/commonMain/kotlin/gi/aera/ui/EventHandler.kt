package gi.aera.ui

interface EventHandler<T : Event> {
  fun obtainEvent(event: T)
}
