package dclib.system;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.lwjgl.LwjglInput;

public final class InputTest {
	
	@Before
	public void setUp() {
		Gdx.input = new LwjglInput();
	}
	
	@Test
	public void addProcessor_InputProcessor_Exists() {
		InputProcessor processor = mock(InputProcessor.class);
		Input.addProcessor(processor);
		assertEquals(processor, Input.getProcessor(0));
	}
	
	@Test
	public void addProcessor_InputProcessorAtIndex_ExistsAtIndex() {
		Input.addProcessor(mock(InputProcessor.class), 0);
		InputProcessor processor2 = mock(InputProcessor.class);
		int index = 1;
		Input.addProcessor(processor2, index);
		assertEquals(processor2, Input.getProcessor(index));
	}
	
	@Test
	public void removeProcessor_InputProcessor_DoesNotExist() {
		InputProcessor processor = mock(InputProcessor.class);
		Input.addProcessor(processor);
		Input.removeProcessor(processor);
		assertFalse(Input.containsProcessor(processor));
	}
	
}
