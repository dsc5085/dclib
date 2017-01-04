package dclib.physics.collision;

public final class CollidedEvent {

	private final Contacter source;
	private final Contacter target;

	public CollidedEvent(final Contacter source, final Contacter target) {
		this.source = source;
		this.target = target;
	}

	public final Contacter getSource() {
		return source;
	}

	public final Contacter getTarget() {
		return target;
	}

}
