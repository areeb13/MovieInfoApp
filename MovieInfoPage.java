package movie;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MovieInfoPage implements ActionListener {
    
    JFrame frame;
    JPanel title, posterPanel, infoPanel, summPanel;
    JLabel titleText, poster, summary, rating, info;
    JTextPane infoPane, summText;
    JButton newSearch;

    MovieInfoPage(MovieInfo movie) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Movie Information");
        frame.setSize(1000,800);

        //title at top 
        title = new JPanel();
        title.setBackground(Color.black);
        title.setPreferredSize(new Dimension(1000, 60));
        titleText = new JLabel(movie.getText());
        titleText.setFont(new Font("Serif", Font.BOLD, 40));
        titleText.setForeground(Color.white);
        title.add(titleText);

        //contains poster image and year
        posterPanel = new JPanel();
        posterPanel.setLayout(new FlowLayout());
        poster = new JLabel();

        BufferedImage image = null;
        URL url;
            
        //convert movie image 
        try {
            url = new URL(movie.getPoster());
            image = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image newImage = image.getScaledInstance(217, 325, Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(newImage);
        poster.setIcon(icon);
        poster.setText(movie.getDate());
        poster.setHorizontalTextPosition(JLabel.CENTER);
        poster.setVerticalTextPosition(JLabel.BOTTOM);
        poster.setFont(new Font("Serif", Font.BOLD, 18));
        poster.setForeground(Color.white);
        posterPanel.setBackground(Color.darkGray);
        posterPanel.setPreferredSize(new Dimension(400,200));
        posterPanel.add(poster);

        infoPanel = new JPanel();
        infoPanel.setLayout(new FlowLayout());
        infoPanel.setPreferredSize(new Dimension(400, 200));
        infoPanel.setBackground(Color.lightGray);


        summPanel = new JPanel();
        summPanel.setLayout(new FlowLayout());
        summPanel.setPreferredSize(new Dimension(800, 350));
        summPanel.setBackground(Color.white);

        //summary of movie
        summary = new JLabel();
        summary.setPreferredSize(new Dimension(400,300));
        summText = new JTextPane();
        SimpleAttributeSet summFontSize = new SimpleAttributeSet(); 
        StyleConstants.setFontSize(summFontSize, 15);
        summText.setCharacterAttributes(summFontSize, true);
        SimpleAttributeSet attribs = new SimpleAttributeSet();
        StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_CENTER);
        summText.setParagraphAttributes(attribs, true);
        summText.setPreferredSize(new Dimension(960,185));
        summText.setText("SUMMARY:\n\n" + movie.getSummary());
        summText.setEditable(false);
        summary.add(summText);
        summPanel.add(summText);

        //holds imdb rating
        rating = new JLabel();
        rating.setBackground(Color.yellow);
        rating.setFont(new Font("Sans Serif", Font.BOLD, 25));
        rating.setOpaque(true);
        rating.setPreferredSize(new Dimension(850,70));
        rating.setText("IMDb RATING: " + movie.getRating());
        rating.setHorizontalAlignment(JLabel.CENTER);
        summPanel.add(rating);

        //holds most info on movie
        infoPane = new JTextPane();
        infoPane.setPreferredSize(new Dimension(550,350));
        infoPane.setFont(new Font("Sans Serif", Font.BOLD, 70));
        SimpleAttributeSet infoFontSize = new SimpleAttributeSet(); 
        StyleConstants.setFontSize(infoFontSize, 15);
        infoPane.setCharacterAttributes(infoFontSize, true);
        infoPane.setBackground(Color.lightGray);
        infoPane.setEditable(false);
        infoPane.setParagraphAttributes(attribs, true);
        infoPane.setText("DIRECTOR:\n" + movie.getDirector() + "\n\n" +
                        "WRITER:\n" + movie.getWriter() + "\n\n" +
                        "ACTORS:\n" + movie.getActors() + "\n\n" +
                        "GENRE:\n" + movie.getGenre() + "\n\n" +
                        "BOX OFFICE:\n" + movie.getBoxOffice() + "\n\n" +
                        "AWARDS:\n" + movie.getAwards() + "\n\n");
        infoPanel.add(infoPane);

        //button for starting another search
        newSearch = new JButton();
        newSearch.setPreferredSize(new Dimension(150,75));
        newSearch.setFocusable(false);
        newSearch.setText("REDO SEARCH");
        newSearch.setOpaque(true);
        newSearch.setBackground(Color.black);
        newSearch.setForeground(Color.white);
        newSearch.setBorderPainted(false);
        newSearch.addActionListener(this);
        summPanel.add(newSearch);
        

        frame.add(infoPanel, BorderLayout.CENTER);
        frame.add(summPanel, BorderLayout.SOUTH);
        frame.add(posterPanel, BorderLayout.WEST);
        frame.add(title, BorderLayout.NORTH);
        frame.setVisible(true);
    }

    //if newSearch clicked, perform another search
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == newSearch) {
            frame.dispose();
            new InputScreen();
        }

    }
}
