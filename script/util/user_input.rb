module UserInput

  # ask the user a yes / no question
  # @return [Boolean] if the user said yes or no.
  def self.yes_no_question(question)
    while true
      puts question.blue
      print "[Y/n] > ".yellow
      answer = gets.chomp

      if answer.empty? || !%w[Y N].include?(answer.upcase)
        puts "please specify 'y' or 'n'".red
        next
      else
        return answer.upcase == "Y"
      end
    end
  end
end