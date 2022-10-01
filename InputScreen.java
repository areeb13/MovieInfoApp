package movie;
//fix title with spaces!!!!
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.json.JSONArray;
import org.json.JSONObject;
//USE THE IMDB ID TO GET MORE INFO
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.awt.BorderLayout;

public class InputScreen extends JFrame implements ActionListener{
    
    JButton button;
    JTextField textfield;
    JLabel errorMessage = new JLabel();
    JFrame frame;
    JPanel panel, title, errorPane;
    JLabel titleText;
    MovieInfo movieInfo[];
    
    InputScreen() {
        //WINDOW BOX
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Movie Search");
        frame.setSize(500,500);

        //TITLE BOX
        title = new JPanel();
        title.setBackground(Color.black);
        title.setPreferredSize(new Dimension(100,60));
        titleText = new JLabel("ENTER MOVIE NAME");
        titleText.setFont(new Font("Serif", Font.BOLD, 40));
        titleText.setForeground(Color.white);
        title.add(titleText);

        //SEARCH BAR
        panel = new JPanel();
        //panel.setLayout(new FlowLayout());
        panel.setBackground(Color.gray);

        //ERROR PANE WHEN INVALID
        errorPane = new JPanel();
        errorPane.setBackground(Color.black);
        errorPane.setPreferredSize(new Dimension(100,100));
        errorMessage.setBackground(Color.black);
        errorMessage.setForeground(Color.red);
        errorMessage.setText("");
        errorMessage.setOpaque(true);
        errorPane.add(errorMessage);
        

        //SEARCH BAR
        textfield = new JTextField();
        textfield.setPreferredSize(new Dimension(480,50));
        textfield.setHorizontalAlignment(JTextField.CENTER);
        panel.add(textfield);

        //SUBMIT BUTTON
        button = new JButton("Submit");
        button.addActionListener(this);
        panel.add(button);

        //LAYOUT OF ELEMENTS
        frame.add(title, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.add(errorPane, BorderLayout.SOUTH);
        this.frame.setVisible(true);
    }

    
    //when submit button is clicked do following
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button) {
            String name = textfield.getText();
            parse(getMovieData(name));
        }
    }
    

    //used to fetch movie from OMDb api
    private static HttpURLConnection connection;

    public String getMovieData(String movie){
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        //trime movie name and correct styling for url
        movie = movie.trim();
        movie = movie.replace(" ", "_");

        //request setup
        try {
            URL url = new URL("http://www.omdbapi.com/?apikey=ac647db4&s=" + movie);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();

            //when status is invalid/valid
            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }
            else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }
            

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }

        return responseContent.toString();
    }
    
    //parse through json
    public void parse(String responseBody) {
        JSONObject movieResponse = new JSONObject(responseBody);
        String response = movieResponse.getString("Response");


        //if valid then parse values
        if (response.equals("True")) {
            JSONArray movies = movieResponse.getJSONArray("Search");
            int counter = 0;
            movieInfo = new MovieInfo[6];

            //get up to 6 movie titles and poster
            while(counter < 6 && counter <= movies.length())
            {
                JSONObject movie = movies.getJSONObject(counter);
                movieInfo[counter] = getFullMovieData(movie.getString("imdbID"), movieInfo[counter]);
                counter++;
            }
            
            //open movie choice window
            new MovieChoice(movieInfo);
            //close current window
            frame.dispose();
        }
        else {
            errorMessage.setText("INVALID. TRY AGAIN!");
        }
    }


    private static HttpURLConnection connection2;

    public MovieInfo getFullMovieData(String imdbID, MovieInfo movieInfo) {
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        //request setup
        try {
            URL url = new URL("http://www.omdbapi.com/?plot=full&apikey=ac647db4&i=" + imdbID);
            connection2 = (HttpURLConnection) url.openConnection();
            connection2.setRequestMethod("GET");
            connection2.setRequestProperty("Accept", "*/*");
            connection2.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection2.setConnectTimeout(5000);
            connection2.setReadTimeout(5000);

            int status = connection2.getResponseCode();

            //when status is invalid/valid
            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection2.getErrorStream()));
                while((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }
            else {
                reader = new BufferedReader(new InputStreamReader(connection2.getInputStream()));
                while((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }
            
            String responseBody = responseContent.toString();

            JSONObject movie = new JSONObject(responseBody);

            movieInfo = new MovieInfo(movie.getString("Title"), movie.getString("Poster"), movie.getString("Released"),
                                            movie.getString("Director"), movie.getString("Writer"), movie.getString("Genre"),
                                            movie.getString("Actors"), movie.getString("BoxOffice"), movie.getString("Awards"),
                                            movie.getString("Plot"), movie.getString("imdbRating"));
            


        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection2.disconnect();
        }

        return movieInfo;
    }
}
