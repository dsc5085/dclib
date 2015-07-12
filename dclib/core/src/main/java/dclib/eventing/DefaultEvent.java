package dclib.eventing;

public class DefaultEvent implements Event<DefaultListener> {

	@Override
	public void notify(DefaultListener listener) {
		listener.executed();
	}

}
