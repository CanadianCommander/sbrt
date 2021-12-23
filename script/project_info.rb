
module ProjectInfo

  # keys with special meaning. Ignore for regular parsing
  SPECIAL_KEYS = %w[client openshift]

  # collect project information
  # @param [Hash] configuration - the configuration hash. The hash will be filled out with "value" attributes according to user response
  def self.collect(configuration)
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