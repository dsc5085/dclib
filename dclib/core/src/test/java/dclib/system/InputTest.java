package dclib.system;

import org.junit.Before;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglInput;

public final class InputTest {

	@Before
	public void setUp() {
		Gdx.input = new LwjglInput();
	}

	// TODO: Implement tests
//	@Test
//	public void addProcessor_InputProcessor_Exists() {
//		InputProcessor processor = mock(InputProcessor.class);
//		Input.addProcessor(processor);
//		assertEquals(processor, Input.getProcessor(0));
//	}
//
//	@Test
//	public void addProcessor_InputProcessorAtIndex_ExistsAtIndex() {
//		Input.addProcessor(mock(InputProcessor.class), 0);
//		InputProcessor processor2 = mock(InputProcessor.class);
//		int index = 1;
//		Input.addProcessor(processor2, index);
//		assertEquals(processor2, Input.getProcessor(index));
//	}

}
