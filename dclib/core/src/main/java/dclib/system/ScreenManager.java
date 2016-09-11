package dclib.system;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public final class ScreenManager {
	
	private final List<Screen> screens = new ArrayList<Screen>();
	
	public final Screen getCurrentScreen() {
		if (screens.isEmpty()) {
			throw new UnsupportedOperationException("There are no screens");
		}
		return screens.get(0);
	}
	
	public final boolean contains(final Screen screen) {
		return screens.contains(screen);
	}
	
	public final void add(final Screen screen) {
		screens.add(screen);
		screen.show();
	}
	
	public final void remove(final Screen screen) {
		screen.hide();
		screen.dispose();
		screens.remove(screen);
	}
	
	public final void swap(final Screen newScreen) {
		for (Screen screen : getScreens()) {
			remove(screen);
		}
		add(newScreen);
	}
	
	public final void render() {
		for (Screen screen : getScreens()) {
			screen.render(Gdx.graphics.getDeltaTime());
		}
	}
	
	public final void resize(final int width, final int height) {
		for (Screen screen : screens) {
			screen.resize(width, height);
		}
	}

	public final void dispose() {
		for (Screen screen : screens) {
			screen.dispose();
		}
	}
	
	private List<Screen> getScreens() {
		return new ArrayList<Screen>(screens);
	}

}
