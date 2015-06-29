package dclib.epf;

public interface Converter {

	boolean canConvert(Object object);
	Object convert(Object object);
	
}
