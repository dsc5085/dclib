package dclib.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;

// TODO: Refactor to be an instance per screen
public final class Input {

	private static final InputMultiplexer multiplexer = new InputMultiplexer();
	
	private Input() {
	}
	
	public static final boolean containsProcessor(final InputProcessor processor) {
		return multiplexer.getProcessors().contains(processor, true);
	}
	
	public static final InputProcessor getProcessor(final int index) {
		return multiplexer.getProcessors().get(index);
	}
	
	public static final void addProcessor(final InputProcessor processor) {
		multiplexer.addProcessor(processor);
		Gdx.input.setInputProcessor(multiplexer);
	}
	
	public static final void addProcessor(final InputProcessor processor, final int index) {
		multiplexer.addProcessor(index, processor);
		Gdx.input.setInputProcessor(multiplexer);
	}
	
	public static final void removeProcessor(final InputProcessor processor) {
		multiplexer.removeProcessor(processor);
		Gdx.input.setInputProcessor(multiplexer);
	}
	
}
