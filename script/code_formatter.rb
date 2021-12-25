
module CodeFormatter

  # replace template tags in the source code
  # @param [Hash] configuration - the project configuration
  def self.replace_template_tags(configuration)

    print "\tReplacing template tags... ".yellow

    # normal options
    configuration.each_key do |key|
      unless ProjectInfo::SPECIAL_KEYS.include?(key)
        self.replace_tag(key, configuration[key]["input"])
      end
    end

    # api clients
    configuration["client"].each_key do |client_api|
      configuration["client"][client_api].each_key do |key|
        unless ProjectInfo::SPECIAL_KEYS.include?(key)
          self.replace_tag("client.#{client_api}.#{key}", configuration["client"][client_api][key]["input"])
        end
      end
    end

    print "Ok\n".green
  end

  protected

  # replace a tag in the project
  # @param [String] tag - the tag to replace
  # @param [String] value - the value to replace it with
  def self.replace_tag(tag, value)
    unless (value.nil? || value.empty?)
      # ya that's right. quick N dirty.
      `find ../* -type f -regextype posix-egrep -regex ".*\\.(java|xml|sh|Dockerfile|yaml|yml|json|properties)" -exec sed -i 's~{{#{tag}}}~#{value}~g' {} \\;`

      if $?.exitstatus != 0
        raise StandardError.new("tag replace failed for [#{tag}] value [#{value}]")
      end
    end
  end

end