package projectpacakgeprefix.projectpackage.projectname.model;

import projectpacakgeprefix.projectpackage.projectname.context.SecurityContext;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@FilterDef(name = "tenantFilter", parameters = {@ParamDef(name = "tenantId", type = "org.hibernate.type.PostgresUUIDType")})
@Filter(name = "tenantFilter", condition = "tenant_id = :tenantId")
@FilterDef(name = "whereNotDeleted")
@Filter(name = "whereNotDeleted", condition = "deleted_at IS NULL")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public abstract class AbstractBaseModel
{
	@Id
	@Column(name = "id")
	@GeneratedValue
	protected UUID id;

	@Column(name = "tenant_id")
	private UUID tenantId;

	@Column(name = "created_at")
	protected ZonedDateTime createdAt;
	@Column(name = "updated_at")
	protected ZonedDateTime updatedAt;
	@Column(name = "deleted_at")
	protected ZonedDateTime deletedAt;
	@Column(name = "created_by_api_client_id")
	protected UUID creatingApiClientId;

	// ==========================================================================
	// Hooks
	// ==========================================================================

	@PrePersist
	public void prePersist()
	{
		this.createdAt = Optional.ofNullable(this.createdAt).orElse(ZonedDateTime.now());
		this.updatedAt = ZonedDateTime.now();
		this.tenantId = SecurityContext.getTenantId();
		this.creatingApiClientId = SecurityContext.getApiClientId();
	}

	@PreUpdate
	public void preUpdate()
	{
		this.updatedAt = ZonedDateTime.now();
	}

	// ==========================================================================
	// Public Methods
	// ==========================================================================

	public AbstractBaseModel(UUID tenantId) {
		this.tenantId = tenantId;
	}
}
