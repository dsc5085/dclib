package dclib.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;

public final class StageUtils {

	private StageUtils() {
	}
	
	public static void resize(final Stage stage, final int width, final int height) {
	    stage.getViewport().update(width, height, true);
	    for (Actor child : stage.getActors()) {
	    	invalidateAll(child);
	    }
	}
	
	private static void invalidateAll(final Actor actor) {
		if (actor instanceof Layout) {
			((Layout)actor).invalidate();
		}
		if (actor instanceof Group) {
			for (Actor child : ((Group)actor).getChildren()) {
				invalidateAll(child);
			}
		}
	}
	
}
