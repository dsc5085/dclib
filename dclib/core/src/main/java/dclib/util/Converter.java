package dclib.util;

public interface Converter {

	boolean canConvert(Object object);
	Object convert(Object object);
	
}
