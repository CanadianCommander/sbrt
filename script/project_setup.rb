require_relative "util/terminal_colors"
require_relative "project_info"
require_relative "code_formatter"
require "yaml"

configuration = nil
dev_mode = ENV["DEV_MODE"]

puts "
========================================
=== SBRT - Spring Boot Rest Template ===
========================================
".green

if dev_mode
  puts "Booting in DEV MODE".red
end

# load configuration
puts "Loading configuration...\n".yellow
configuration = YAML.load(File.open("replacement_variables.yaml"))

puts "Please fill out the following configuration options. \nSome options have defaults. These are displayed in " + "[]".yellow + " brackets"
ProjectInfo.collect(configuration)

puts "Formatting project...\n".yellow
CodeFormatter.replace_template_tags(configuration)