package dclib.epf.parts;

import java.util.Arrays;
import java.util.List;

import dclib.util.CollectionUtils;

public final class CollisionPart {

	private final List<Enum<?>> collisionGroups;

	public CollisionPart(final Enum<?>... collisionGroups) {
		this.collisionGroups = Arrays.asList(collisionGroups);
	}

	public final boolean containsAny(final Enum<?>... collisionGroups) {
		return containsAny(Arrays.asList(collisionGroups));
	}

	public final boolean containsAny(final List<Enum<?>> collisionGroups) {
		return CollectionUtils.containsAny(this.collisionGroups, collisionGroups);
	}
}
