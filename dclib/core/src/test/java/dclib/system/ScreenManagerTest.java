package dclib.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public final class ScreenManagerTest {

	private ScreenManager screenManager;

	@Before
	public void setUp() {
		screenManager = new ScreenManager();
	}

	@Test
	public void add_Screen_ShowsScreen() {
		Screen screen = mock(Screen.class);
		screenManager.add(screen, true);
		verify(screen).show();
	}

	@Test
	public void remove_ExistingScreen_DoesNotContainScreen() {
		Screen screen = mock(Screen.class);
		addScreens(screen);
		removeScreens(screen);
		assertFalse(screenManager.contains(screen));
	}

	@Test
	public void remove_ExistingScreen_HidesScreen() {
		Screen screen = mock(Screen.class);
		addScreens(screen);
		removeScreens(screen);
		verify(screen).hide();
	}

	@Test
	public void update_RemovedScreen_DisposesScreen() {
		Screen screen = mock(Screen.class);
		addScreens(screen);
		removeScreens(screen);
		verify(screen).dispose();
	}

	@Test
	public void render_ExistingScreen_RendersScreen() {
		Gdx.graphics = mock(Graphics.class);
		float deltaTime = 0.5f;
		when(Gdx.graphics.getDeltaTime()).thenReturn(deltaTime);
		Screen screen = mock(Screen.class);
		addScreens(screen);
		screenManager.render();
		verify(screen).render(deltaTime);
	}

	@Test
	public void resize_ExistingScreen_ResizesScreen() {
		Screen screen = mock(Screen.class);
		addScreens(screen);
		int width = 800;
		int height = 600;
		screenManager.resize(width, height);
		verify(screen).resize(width, height);
	}

	@Test
	public void dispose_ContainsScreen_CallsDispose() {
		Screen screen = mock(Screen.class);
		addScreens(screen);
		screenManager.dispose();
		verify(screen).dispose();
	}

	private void addScreens(final Screen... screens) {
		for (Screen screen : screens) {
			screenManager.add(screen, false);
		}
	}

	private void removeScreens(final Screen... screens) {
		for (Screen screen : screens) {
			screenManager.remove(screen);
		}
	}

}
