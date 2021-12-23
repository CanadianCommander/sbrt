
module CodeFormatter

  # replace template tags in the source code
  # @param [Hash] configuration - the project configuration
  def self.replace_template_tags(configuration)

    puts "\tReplacing template tags".yellow

    configuration.each_key do |key|
      unless ProjectInfo::SPECIAL_KEYS.include?(key)
        `find ../* -type f -regextype posix-egrep -regex ".*pom.xml" -exec sed -i 's/{{#{key}}}/#{configuration[key]["input"]}/g' {} \\;`
        #`find ./* -type f -regextype posix-egrep -regex ".*(java|xml|sh|Dockerfile|yaml|yml|json)"`
      end
    end
  end
end