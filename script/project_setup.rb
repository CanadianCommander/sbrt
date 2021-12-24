require_relative "util/terminal_colors"
require_relative "project_info"
require_relative "code_formatter"
require_relative "docker_configuration"
require_relative "file_formatter"
require_relative "project_tester"
require "yaml"

configuration = nil
dev_mode = ENV["DEV_MODE"] == "true"
skip_tests = ENV["SKIP_TESTS"] == "true"

puts "
========================================
=== SBRT - Spring Boot Rest Template ===
========================================
".green

if dev_mode
  puts "Booting in DEV MODE".red
end

# load configuration
puts "\nLoading configuration...".yellow
configuration = YAML.load(File.open("replacement_variables.yaml"))

puts "Please fill out the following configuration options. \nSome options have defaults. These are displayed in " + "[]".yellow + " brackets"
ProjectInfo.collect(configuration)

puts "\nChecking docker configuration...".yellow
DockerConfiguration.configure_docker(configuration)

puts "\nFormatting project...".yellow
CodeFormatter.replace_template_tags(configuration)
FileFormatter.rename_files(configuration)

if dev_mode && !skip_tests
  puts "\nTesting project... ".yellow

  ProjectTester.test_project
end