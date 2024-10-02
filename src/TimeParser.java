import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// TimeParser class to handle time string conversions
class TimeParser {

    // Convert a 12-hour format time string like "4:30 pm" to 24-hour format "HH:mm"
    private String convert_to_24_hour_format(String time) throws ParseException {
        SimpleDateFormat in_12_hour_format = new SimpleDateFormat("h:mm a");
        SimpleDateFormat in_24_hour_format = new SimpleDateFormat("HH:mm");
        Date date = in_12_hour_format.parse(time.trim());
        return in_24_hour_format.format(date);
    }

    // Function to parse timing input in format "4:30 pm to 6 pm" and return start and end times in 24-hour format
    public String[] get_timings(String timing_input) {
        try {
            // Split the string by "to" to separate the start and end times
            String[] parts = timing_input.split("to");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid time format. Use 'HH:mm AM/PM to HH:mm AM/PM'.");
            }

            String start_time = convert_to_24_hour_format(parts[0].trim());
            String end_time = convert_to_24_hour_format(parts[1].trim());

            return new String[]{start_time, end_time};
        } catch (ParseException e) {
            e.printStackTrace();
            return new String[]{"Invalid time", "Invalid time"};
        }
    }
}