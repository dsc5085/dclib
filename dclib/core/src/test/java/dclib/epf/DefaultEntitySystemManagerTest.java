package dclib.epf;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

public final class DefaultEntitySystemManagerTest {

	@Test
	public void dispose_WithSystems_DisposesSystems() {
		EntityManager entityManager = mock(EntityManager.class);
		EntitySystem entitySystem = mock(EntitySystem.class);
		EntitySystemManager entitySystemManager = createEntitySystemManager(entityManager, entitySystem);
		entitySystemManager.dispose();
		verify(entitySystem).dispose();
	}

	@Test
	public void update_WithEntities_UpdatesEntities() {
		Entity entity = new Entity();
		EntityManager entityManager = new DefaultEntityManager(entity);
		EntitySystem entitySystem = mock(EntitySystem.class);
		EntitySystemManager entitySystemManager = createEntitySystemManager(entityManager, entitySystem);
		float delta = 0.5f;
		entitySystemManager.update(delta);
		verify(entitySystem).updateEntity(delta, entity);
	}

	@Test
	public void entityAdded_WithSystems_InitializesEntity() {
		EntityManager entityManager = new DefaultEntityManager();
		Entity entity = new Entity();
		EntitySystem entitySystem = mock(EntitySystem.class);
		createEntitySystemManager(entityManager, entitySystem);
		entityManager.add(entity);
		verify(entitySystem).initializeEntity(entity);
	}

	@Test
	public void entityRemoved_WithSystems_DisposesEntity() {
		Entity entity = new Entity();
		EntityManager entityManager = new DefaultEntityManager(entity);
		EntitySystem entitySystem = mock(EntitySystem.class);
		createEntitySystemManager(entityManager, entitySystem);
		entityManager.remove(entity);
		verify(entitySystem).disposeEntity(entity);
	}
	
	private EntitySystemManager createEntitySystemManager(final EntityManager entityManager, 
			final EntitySystem entitySystem) {
		EntitySystemManager entitySystemManager = new DefaultEntitySystemManager(entityManager);
		entitySystemManager.add(entitySystem);
		return entitySystemManager;
	}
	
}
