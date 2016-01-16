package org.usfirst.frc4904.standard.commands.safety;

import java.util.Collection;

public interface Body {
	public void connectBodyParts(BodyPart a, BodyPart b);

	public Collection<BodyPart> getConnectedParts(BodyPart a);
}
