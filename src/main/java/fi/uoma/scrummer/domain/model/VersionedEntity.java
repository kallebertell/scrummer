package fi.uoma.scrummer.domain.model;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * An identifable entity which is also versioned.
 *
 */
@MappedSuperclass
public abstract class VersionedEntity extends IdentifiableEntity implements Versioned {

	@Version
	private Integer version;

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
