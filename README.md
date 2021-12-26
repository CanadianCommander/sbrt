## Spring Boot REST Template SBRT.
Get a Spring Boot based REST API up and running fast. This project allows quick and easy generation
of ready to code spring boot projects. Sort of a Spring Boot, Boot if you think about it.

### Prerequisites 
To run this template you need
- **Ruby** likely already installed by your distro
- **Docker**  https://docs.docker.com/engine/install/
- **Git** I suspect you already have this ;)

### Usage 
To create a new project using this template simply paste this in your terminal and follow the prompts. 

`
curl https://raw.githubusercontent.com/CanadianCommander/sbrt/master/script/boot.sh | bash
`

After setup is complete enter the newly created directory `sbrt` (which you can rename) and run 

`
./docker/docker dev -R
`

Then in your browser navigate to `http://localhost:7080/api/v1/` you should see "Hello World"!
You're now ready to start writing your REST service. 

### Implementation advice

#### Running your new project
This template provides a docker script through which you can perform all actions.
- Running the dev environment `./docker/docker.sh dev [-R]` 🚀
- Generate the openapi spec for the API. After adding or removing endpoints use this 
  to update your openapi specification file. This file is used to generate client api code
  `./docker/docker.sh generate_api [-R]`
- Deploy client api code to remote registries. This will only include the apis you configured at setup.
  `./docker/docker.sh deploy_client_libraries [-R]` 
- Deploy to staging env (OpenShift) `./docker/docker.sh deploy_staging [-R]`
check `./docker/docker.sh --help` for a full list of options

#### Multi Tenant System
This template contains an automatic multi tenant system! This allows one database to be shared 
by multiple clients while insuring data isolation. All queries (excluding raw sql)
will be automatically filtered by the tenantId for the current request! This greatly reduces the chance 
of leaking one client's data to another. It is up to you to decide how tenantId will be set. Or perhaps 
you don't want a multi tenant system? In that case you can just leave it as a constant. 
See: `SecurityInterceptor` to customize.    

#### Soft Delete
This template features a soft delete system. This is controlled by the `deleted_at` column on each table.
If this column is not null the row will automatically become invisible to your app! If you want your app
to temporarily see deleted rows simply disable the `whereNotDeleted` filter on the JPA session.
Unfortunately due to limitations of JPA / Hibernate you do have to put the following annotation on your models
if you want them to soft delete (if you forget it they will hard delete). If anyone knows how to fix this that would be amazing!
```
@SQLDelete(sql = "UPDATE <table name> SET deleted_at = NOW() where id = ?")
```
#### JPA / Data models 
This template provides a base model which contains the following fields 
```
id - UUID
tenantId - UUID (for tenanting system)
createdByApiClientId - UUID
createdAt - Timestamp
updatedAt - Timestamp
deletedAt - Timestamp  (for soft delete)
```
These fields will be automatically managed for you if you extend the appropriate interfaces.

This template also provides it's own JPA repository implementation. All repositories should extend 
`BaseRepository` if you intend to use the features outlined here.

#### DB and Migrations
This template features a Postgres database running in a container. Migrations are handled through liquibase.
If you want to automatically generate migrations based on your JPA models see https://plugins.jetbrains.com/plugin/15075-jpa-buddy/features
In just a few clicks it will generate liquibase changelogs for your project. To support this the database is 
assigned a static IP `172.21.32.4` This allows JPA buddy to scan the database to generate a liquibase changelog. 

#### Important Interfaces / Abstract Classes
This template is specifically designed to provide robust interfaces to allow for rappid development 
while still following maintainable code practices. Of course using these abstractions is optional.
The general flow of data is as follows

```
# outbound 
AbstractModel -> convert: AbstractModelToTransferConverter -> produces: AbstractModelTransfer -> wrap: ApplicationResponse<AbstractModelTransfer> -> client
# inbound
client -> AbstractModelInboundTransfer -> convert: AbstractInboundTransferToAbstractModelConverter -> produces: AbstractModel -> DB 
```

If you chose to extend / implement these classes you will benefit from automatic conversion of common model
fields when transfers come in / out. You will also have hibernated persistence managed to allow for cascading 
creation / updates of nested transfer objects (if you so configure your associations).
Finally, you will be able to use the `CRUDController` interfaces(s) which 
allow for quick and easy addition of CRUD api endpoints. 
