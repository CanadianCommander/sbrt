## Spring Boot REST Template SBRT.
Get a Spring Boot based REST API up and running fast. This project allows quick and easy generation
of ready to code spring boot projects. Sort of a Spring Boot, Boot if you think about it.

### Prerequisites 
To run this template you need 
- **Jdk 17** check your package manager
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
This template contains many useful base classes. Checkout `AbstractBaseModel` which provides a nice 
set of default fields for every model. Furthermore `AbstractModelTransfer` and `AbstractModelInboundTransfer` 
provide transfer objects support. Combine those with the `AbstractModelToTransferConverter` and
`AbstractInboundTransferToAbstractModelConverter` to quickly build out your CRUD stack. Finally
`AbstractCRUDController` and similar classes provide auto magic CRUD REST operations for any model
implementing the aforementioned abstract classes. 