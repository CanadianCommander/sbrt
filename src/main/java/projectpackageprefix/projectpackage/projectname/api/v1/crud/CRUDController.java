package {{project-package-prefix}}.{{project-package}}.{{project-name}}.api.v1.crud;

import {{project-package-prefix}}.{{project-package}}.{{project-name}}.transfer.AbstractModelInboundTransfer;
import {{project-package-prefix}}.{{project-package}}.{{project-name}}.transfer.AbstractModelTransfer;
import {{project-package-prefix}}.{{project-package}}.{{project-name}}.model.AbstractBaseModel;
import {{project-package-prefix}}.{{project-package}}.{{project-name}}.transfer.ApplicationResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

// controller that provides CRUD. Create, Read, Update, Delete
public interface CRUDController<Model extends AbstractBaseModel, Transfer extends AbstractModelTransfer, InboundTransfer extends AbstractModelInboundTransfer>
		extends CController<Model, Transfer, InboundTransfer>, RController<Model, Transfer, InboundTransfer>, UController<Model, Transfer, InboundTransfer>, DController<Model, Transfer, InboundTransfer>
{

}
