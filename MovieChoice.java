package movie;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.FlowLayout;
import java.awt.BorderLayout;

public class MovieChoice implements ActionListener{
    
    JFrame frame;
    JPanel title, buttonPanel;
    JLabel titleText;
    MovieInfo movieInfo[];

    MovieChoice(MovieInfo movies[]){
        movieInfo = movies;

        //HOLDS ALL ELEMENTS
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Movie Choices");
        frame.setSize(1000,800);

        //TITLE
        title = new JPanel();
        title.setBackground(Color.black);
        //setpreferred size
        titleText = new JLabel("CHOOSE MOVIE");
        titleText.setFont(new Font("Serif", Font.BOLD, 40));
        titleText.setForeground(Color.white);
        title.add(titleText);

        //HOLDS MOVIE BUTTONS
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        //LOOP THROUGH TITLES AND POSTERS
        for (int i = 0; i < movieInfo.length; i++) {
            //button = new MovieButton();
            BufferedImage image = null;
            URL url;
            
            //convert image 
            try {
                url = new URL(movieInfo[i].getPoster());
                image = ImageIO.read(url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //alter size of image
            Image newImage = image.getScaledInstance(150, 225, Image.SCALE_DEFAULT);
            ImageIcon icon = new ImageIcon(newImage);
            
            //set button text and image
            movieInfo[i].setPreferredSize(new Dimension(325,350));
            movieInfo[i].setFocusable(false);
            movieInfo[i].setText(movieInfo[i].getTitle());
            movieInfo[i].setHorizontalTextPosition(JButton.CENTER);
            movieInfo[i].setVerticalTextPosition(JButton.BOTTOM);

            movieInfo[i].setIcon(icon);
            movieInfo[i].addActionListener(this);
            buttonPanel.add(movieInfo[i]);
        }

        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(title, BorderLayout.NORTH);
        frame.setVisible(true);

    }

    //open movie info based on movie clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < movieInfo.length; i++)
            if(e.getSource() == movieInfo[i]) {
                frame.dispose();
                new MovieInfoPage(movieInfo[i]);
            }
    }
}
