package dclib.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;

import java.util.ArrayList;
import java.util.List;

public final class Input {

	private static final InputMultiplexer multiplexer = new InputMultiplexer();

	private final List<InputProcessor> processors = new ArrayList<InputProcessor>();

	public static final boolean contains(final InputProcessor processor) {
		return multiplexer.getProcessors().contains(processor, true);
	}

	public final void add(final InputProcessor processor) {
		add(processor, 0);
	}

	public final void add(final InputProcessor processor, final int index) {
		processors.add(index, processor);
	}

	public final void enable() {
		for (InputProcessor processor : processors) {
			multiplexer.addProcessor(processor);
		}
		Gdx.input.setInputProcessor(multiplexer);
	}

	public final void disable() {
		for (InputProcessor processor : processors) {
			multiplexer.removeProcessor(processor);
		}
		Gdx.input.setInputProcessor(multiplexer);
	}

}
