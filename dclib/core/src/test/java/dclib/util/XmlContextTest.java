package dclib.util;

import com.badlogic.gdx.files.FileHandle;
import dclib.epf.Entity;
import dclib.epf.factory.EntityAdapted;
import dclib.epf.factory.EntityAdapter;
import dclib.system.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import test.dclib.io.ResourcePaths;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class XmlContextTest {

	private static XmlContext xmlContext;
	
	@BeforeClass
	public static void oneTimeSetUp() {
		Class<?>[] boundClasses = new Class<?>[] { EntityAdapted.class };
		xmlContext = new XmlContext(boundClasses);
	}
	
	@Test
	public void unmarshal_FileHandleOfSerializableObject_ReturnsNonNull() {
		FileHandle fileHandle = FileUtils.toFileHandle(ResourcePaths.ENTITY_XML);
		EntityAdapted entity = xmlContext.unmarshal(fileHandle);
		assertNotNull(entity);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void unmarshal_FileHandleOfUnknownObject_ThrowsException() {
		FileHandle fileHandle = FileUtils.toFileHandle(ResourcePaths.UNKNOWN_OBJECT_XML);
		xmlContext.unmarshal(fileHandle);
	}
	
	@Test
	public void unmarshal_InputStreamOfSerializablObject_ReturnsNonNull() {
		InputStream inputStream = FileUtils.toInputStream(ResourcePaths.ENTITY_XML);
		EntityAdapted entity = xmlContext.unmarshal(inputStream);
		assertNotNull(entity);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void unmarshal_InputStreamOfUnknownObject_ThrowsException() {
		InputStream inputStream = FileUtils.toInputStream(ResourcePaths.UNKNOWN_OBJECT_XML);
		xmlContext.unmarshal(inputStream);
	}
	
	@Test
	public void unmarshal_InputStreamAndAdapterOfSerializablObject_ReturnsNonNull() {
		InputStream inputStream = FileUtils.toInputStream(ResourcePaths.ENTITY_XML);
		Entity entity = xmlContext.unmarshal(inputStream, new EntityAdapter());
		assertNotNull(entity);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void unmarshal_InputStreamAndErroringAdapterOfSerializablObject_ThrowsException() throws Exception {
		InputStream inputStream = FileUtils.toInputStream(ResourcePaths.ENTITY_XML);
		@SuppressWarnings("unchecked")
		XmlAdapter<EntityAdapted, Entity> adapter = mock(XmlAdapter.class);
		when(adapter.unmarshal(any(EntityAdapted.class))).thenThrow(new Exception());
		xmlContext.unmarshal(inputStream, adapter);
	}
	
	@Test
	public void marshal_FileHandleAndSerializablObject_WritesExpected() {
		EntityAdapted entity = new EntityAdapted();
		FileHandle fileHandle = FileUtils.toFileHandle(ResourcePaths.OUTPUT_XML);
		xmlContext.marshal(entity, fileHandle);
		boolean containsObjectMarkup = fileHandle.readString().contains("<entity/>");
		assertTrue(containsObjectMarkup);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void marshal_FileHandleAndUnserializableObject_ThrowsException() {
		Entity entity = new Entity();
		FileHandle fileHandle = FileUtils.toFileHandle(ResourcePaths.OUTPUT_XML);
		xmlContext.marshal(entity, fileHandle);
	}
	
	@Test
	public void marshal_OutputStreamAndSerializablObject_ThrowsException() {
		EntityAdapted entity = new EntityAdapted();
		String path = ResourcePaths.OUTPUT_XML;
		OutputStream outputStream = FileUtils.toOutputStream(path);
		xmlContext.marshal(entity, outputStream);
		FileHandle fileHandle = FileUtils.toFileHandle(path);
		boolean containsObjectMarkup = fileHandle.readString().contains("<entity/>");
		assertTrue(containsObjectMarkup);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void marshal_OutputStreamAndUnserializableObject_ThrowsException() {
		Entity entity = new Entity();
		OutputStream outputStream = FileUtils.toOutputStream(ResourcePaths.OUTPUT_XML);
		xmlContext.marshal(entity, outputStream);
	}
	
}
