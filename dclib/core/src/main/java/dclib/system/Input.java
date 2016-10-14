package dclib.system;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;

public final class Input {

	private static final InputMultiplexer multiplexer = new InputMultiplexer();

	private final Set<InputProcessor> processors = new HashSet<InputProcessor>();

	public static final boolean contains(final InputProcessor processor) {
		return multiplexer.getProcessors().contains(processor, true);
	}

	public static final InputProcessor getProcessor(final int index) {
		return multiplexer.getProcessors().get(index);
	}

	public final void add(final InputProcessor processor) {
		add(processor, 0);
	}

	public final void add(final InputProcessor processor, final int index) {
		processors.add(processor);
		multiplexer.addProcessor(index, processor);
		Gdx.input.setInputProcessor(multiplexer);
	}

	public final void dispose() {
		for (InputProcessor processor : processors) {
			multiplexer.removeProcessor(processor);
		}
		Gdx.input.setInputProcessor(multiplexer);
	}

}
