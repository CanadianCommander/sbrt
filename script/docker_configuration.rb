module DockerConfiguration

  # configure docker
  # @param [Hash] configuration - the project configuration
  def self.configure_docker(configuration)

    print "\tChecking static network... ".yellow

    # check for static network
    `sudo docker network inspect static-network`
    unless $?.exitstatus == 0
      print "Not present. Creating... ".yellow
      # static-network not present create
      `sudo docker network create --subnet=172.21.0.0/16 static-network`

      unless $?.exitstatus == 0
        print "Error\n".red
        exit 1
      end
    end

    print "Ok\n".green
  end

end