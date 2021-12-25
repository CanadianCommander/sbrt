module FileFormatter

  # rename files & directories
  # @param [Hash] configuration - the project configuration
  def self.rename_files(configuration)

    print "\tRenaming Files... ".yellow

    # rename application class file
    `mv ../src/main/java/projectpackageprefix/projectpackage/projectname/ProjectName.java ../src/main/java/projectpackageprefix/projectpackage/projectname/#{configuration["ProjectClass"]["input"]}.java`
    `mv ../src/test/java/projectpackageprefix/projectpackage/projectname/ProjectNameTests.java ../src/test/java/projectpackageprefix/projectpackage/projectname/#{configuration["ProjectClass"]["input"]}Tests.java`

    # rename package files
    `mv ../src/main/java/projectpackageprefix/projectpackage/projectname ../src/main/java/projectpackageprefix/projectpackage/#{configuration["project-name"]["input"]}`
    `mv ../src/main/java/projectpackageprefix/projectpackage ../src/main/java/projectpackageprefix/#{configuration["project-package"]["input"]}`
    `mv ../src/main/java/projectpackageprefix ../src/main/java/#{configuration["project-package-prefix"]["input"]}`

    `mv ../src/test/java/projectpackageprefix/projectpackage/projectname ../src/test/java/projectpackageprefix/projectpackage/#{configuration["project-name"]["input"]}`
    `mv ../src/test/java/projectpackageprefix/projectpackage ../src/test/java/projectpackageprefix/#{configuration["project-package"]["input"]}`
    `mv ../src/test/java/projectpackageprefix ../src/test/java/#{configuration["project-package-prefix"]["input"]}`

    # rename docker files
    `mv ../docker/projectname_deploy_client_libraries ../docker/#{configuration["project-name-lower"]["input"]}_deploy_client_libraries`
    `mv ../docker/projectname_dev ../docker/#{configuration["project-name-lower"]["input"]}_dev`
    `mv ../docker/projectname_generate_api ../docker/#{configuration["project-name-lower"]["input"]}_generate_api`
    `mv ../docker/projectname_prod ../docker/#{configuration["project-name-lower"]["input"]}_prod`

    # delete unused api scripts
    unless configuration["client"]["ts"]["enabled"]
      `rm ../docker/#{configuration["project-name-lower"]["input"]}_deploy_client_libraries/deploy_ts.sh`
    end
    unless configuration["client"]["java"]["enabled"]
      `rm ../docker/#{configuration["project-name-lower"]["input"]}_deploy_client_libraries/deploy_java.sh`
    end
    unless configuration["client"]["ruby"]["enabled"]
      `rm ../docker/#{configuration["project-name-lower"]["input"]}_deploy_client_libraries/deploy_ruby.sh`
    end

    print "Ok\n".green
  end
end