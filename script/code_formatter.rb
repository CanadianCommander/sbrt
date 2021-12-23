
module CodeFormatter

  # replace template tags in the source code
  # @param [Hash] configuration - the project configuration
  def self.replace_template_tags(configuration)

    print "\tReplacing template tags... ".yellow

    configuration.each_key do |key|
      unless ProjectInfo::SPECIAL_KEYS.include?(key)
        # ya that's right. quick N dirty.
        `find ../* -type f -regextype posix-egrep -regex ".*\\.(java|xml|sh|Dockerfile|yaml|yml|json|properties)" -exec sed -i 's/{{#{key}}}/#{configuration[key]["input"]}/g' {} \\;`
      end
    end

    print "Ok\n".green
  end
end