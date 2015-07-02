package dclib.system;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.Screen;

public final class ScreenManagerTest {
	
	private ScreenManager screenManager;

	@Before
	public void setUp() {
		screenManager = new ScreenManager();
	}

	@Test(expected=UnsupportedOperationException.class)
	public void getCurrentScreen_NoScreens_ThrowsException() {
		screenManager.getCurrentScreen();
	}

	@Test
	public void getCurrentScreen_MultipleScreens_ReturnsFirstScreen() {
		Screen expected = mock(Screen.class);
		addScreens(expected, mock(Screen.class));
		assertEquals(expected, screenManager.getCurrentScreen());
	}
	
	@Test
	public void add_Screen_ShowsScreen() {
		Screen screen = mock(Screen.class);
		screenManager.add(screen);
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
	public void swap_DifferentScreens_ContainsNewScreen() {
		Screen newScreen = mock(Screen.class);
		screenManager.swap(mock(Screen.class), newScreen);
		screenManager.update();
		assertTrue(screenManager.contains(newScreen));
	}
	
	@Test
	public void swap_DifferentScreens_DoesNotContainCurrentScreen() {
		Screen currentScreen = mock(Screen.class);
		addScreens(currentScreen);
		screenManager.swap(currentScreen, mock(Screen.class));
		screenManager.update();
		assertFalse(screenManager.contains(currentScreen));
	}
	
	@Test
	public void update_RemovedScreen_DisposesScreen() {
		Screen screen = mock(Screen.class);
		addScreens(screen);
		removeScreens(screen);
		verify(screen).dispose();
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
			screenManager.add(screen);
		}
		screenManager.update();
	}
	
	private void removeScreens(final Screen... screens) {
		for (Screen screen : screens) {
			screenManager.remove(screen);
		}
		screenManager.update();
	}
	
}
