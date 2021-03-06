package dclib.epf;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

// TODO: Implement commented out tests
public final class DefaultEntityManagerTest {

	private Entity entity;
	private EntityManager entityManager;

	@Test
	public void add_Entity_SetsEntityToActive() {
		setupAddTest();
		entityManager.add(entity);
		assertTrue(entity.isActive());
	}

	@Test
	public void add_Entity_ContainsEntity() {
		setupAddTest();
		entityManager.add(entity);
		assertTrue(entityManager.contains(entity));
	}

//	@Test
//	public void add_Entity_NotifiesEntityAdded() {
//		setupAddTest();
//		EntityAddedListener listener = mock(EntityAddedListener.class);
//		entityManager.listen(listener);
//		entityManager.add(entity);
//		verify(listener).added(entity);
//	}

	@Test(expected=IllegalArgumentException.class)
	public void add_AlreadyAddedEntity_ThrowsException() {
		setupAddTest();
		entityManager.add(entity);
		entityManager.add(entity);
	}

	@Test
	public void addAll_Entity_ContainsEntity() {
		setupAddTest();
		List<Entity> entities = Arrays.asList(entity);
		entityManager.addAll(entities);
		assertTrue(entityManager.contains(entity));
	}

	@Test
	public void remove_Entity_SetsEntityToInactive() {
		setupRemoveTest();
		entityManager.destroy(entity);
		assertFalse(entity.isActive());
	}

	@Test
	public void remove_Entity_DoesNotContainEntity() {
		setupRemoveTest();
		entityManager.destroy(entity);
		assertFalse(entityManager.contains(entity));
	}

//	@Test
//	public void remove_Entity_NotifiesEntityRemoved() {
//		setupRemoveTest();
//		EntityRemovedListener listener = mock(EntityRemovedListener.class);
//		entityManager.listen(listener);
//		entityManager.remove(entity);
//		verify(listener).removed(entity);
//	}

	private void setupAddTest() {
		entity = new Entity();
		entityManager = new DefaultEntityManager();
	}

	private void setupRemoveTest() {
		entity = new Entity();
		entityManager = new DefaultEntityManager();
		entityManager.add(entity);
	}

}
