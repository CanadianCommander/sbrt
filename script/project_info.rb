require_relative "util/user_input"

module ProjectInfo

  # keys with special meaning. Ignore for regular parsing
  SPECIAL_KEYS = %w[client openshift enabled]

  # collect project information
  # @param [Hash] configuration - the configuration hash. The hash will be filled out with "value" attributes according to user response
  def self.collect(configuration)
    self.collect_basic_info(configuration)
    self.collect_api_client_info(configuration)
    self.collect_open_shift_info(configuration)
    self.add_computed_configuration(configuration)
  end

  def self.collect_basic_info(configuration)
    self.collect_user_input(configuration)
  end

  # collect information about API client library setup from the user.
  # @param [Hash] configuration - project configuration hash to fill out
  def self.collect_api_client_info(configuration)
    if UserInput.yes_no_question("Setup API client libraries?")

      # TS
      if UserInput.yes_no_question("Setup TypeScript API?")
        configuration["client"]["ts"]["enabled"] = true
        self.collect_user_input(configuration["client"]["ts"])
      else
        puts "Skipping".yellow
      end

      # Java
      if UserInput.yes_no_question("Setup Java API?")
        configuration["client"]["java"]["enabled"] = true
        self.collect_user_input(configuration["client"]["java"])
      else
        puts "Skipping".yellow
      end

      # Ruby
      if UserInput.yes_no_question("Setup Ruby API?")
        configuration["client"]["ruby"]["enabled"] = true
        self.collect_user_input(configuration["client"]["ruby"])
      else
        puts "Skipping".yellow
      end

    else
      puts "Skipping".yellow
    end
  end

  # collect information about openshift setup from the user
  # @param [Hash] configuration - the configuration to fill out.
  def self.collect_open_shift_info(configuration)
    if UserInput.yes_no_question("Setup OpenShift? (production deployment)")

      self.collect_user_input(configuration["openshift"])
    else
      puts "Skipping".yellow
    end
  end

  # add computed configuration values
  # @param [Hash] configuration - project configuration
  def self.add_computed_configuration(configuration)

    # computed value for spring boot project class
    configuration["ProjectClass"] = {
      "input" => configuration["project-name"]["input"].split(/[_-]/).collect(&:capitalize).join
    }

    # lower case version of project name
    configuration["project-name-lower"] = {
      "input" => configuration["project-name"]["input"].downcase
    }
  end

  protected

  # Collect user input for the given configuration level.
  # @param [Hash] configuration - the configuration details to collect
  def self.collect_user_input(configuration)
    configuration.each_key do |key|
      unless SPECIAL_KEYS.include?(key)
        puts "> #{key}".blue
        puts "#{configuration[key]["desc"]}\n".green

        input_ok = false
        until input_ok
          if configuration[key]["default"].nil?
            print "> ".yellow
          else
            print "> [#{configuration[key]["default"]}] ".yellow
          end

          input = gets.chomp

          input_ok = self.validate_input(input, configuration[key])

          if input_ok
            if input.empty?
              configuration[key]["input"] = configuration[key]["default"]
            else
              configuration[key]["input"] = input
            end
          end
        end
      end
    end
  end

  # validate user input for a configuration item
  # @param [String] input - the users input
  # @param [Hash] configuration_item - the configuration item the input is for
  # @return true / false indicating if the input is valid.
  def self.validate_input(input, configuration_item)

    if !configuration_item["allow-space"] && input.include?(" ")
      puts "Input cannot contain spaces".red
      return false
    end

    if !configuration_item["default"] && input == ""
      puts "You must provide input for this option".red
      return false
    end

    return true
  end
end