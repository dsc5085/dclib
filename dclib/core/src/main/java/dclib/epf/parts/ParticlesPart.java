package dclib.epf.parts;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public final class ParticlesPart {

	private final List<Attachment<ParticleEffect>> attachments;
	
	public ParticlesPart(final List<Attachment<ParticleEffect>> attachments) {
		this.attachments = attachments;
	}
	
	public final List<Attachment<ParticleEffect>> getAttachments() {
		return new ArrayList<Attachment<ParticleEffect>>(attachments);
	}
	
}
