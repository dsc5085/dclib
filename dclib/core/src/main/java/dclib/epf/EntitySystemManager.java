package dclib.epf;

public interface EntitySystemManager {
	
	void add(final EntitySystem system);
	
	void dispose();
	
	void update(final float delta);
	
}
