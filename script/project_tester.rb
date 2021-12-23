module ProjectTester

  # perform a quick test to make sure the project is working
  def self.test_project

    puts "\tRunning Integration tests... ".yellow

    # build the API spec file. This involves booting the server and hitting it to download the spec file. Basically a simple integration test.
    `../docker/docker.sh generate_api -R`

    unless $?.exitstatus == 0
      puts "Test Failed".red
      exit 1
    end

    puts "Ok".green
  end
end