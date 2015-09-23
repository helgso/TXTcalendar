import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

public class TXTcalendar {
    static JTextField locationTextField;
    static JTextField yearTextField;
    static JFrame frame;

    // post: Java browser viðmót er opnað til þess að skila slóð á ákveðna möppu
    public static void openChooser() {
        JFileChooser chooser = new JFileChooser();
        String choosertitle = "";
        chooser.setCurrentDirectory(new java.io.File(System.getProperty("user.home") + "\\Desktop"));
        chooser.setDialogTitle(choosertitle);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //
        // disable the "All files" option.
        //
        chooser.setAcceptAllFileFilterUsed(false);
        //    
        if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            // Tókst. Geymum upplýsingarnar í JTextField locationTextField
            locationTextField.setText(chooser.getSelectedFile().getPath());
        }
        else {
            System.out.println("No Selection ");
        }
    }

    // pre: url er löggild slóð á möppu í tölvunni
    // post: Býr til [year].txt sem er dagbókin og er að finna á slóðinni url
    public static void createDict(String url, int year) {
        try {
            // Setjum dagatalið hingað
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(url + "\\dagbok " + year + ".txt")));

            String[] dagar = {"Sunnu", "Mánu", "Þriðju", "Miðviku", "Fimmtu", "Föstu", "Laugar"};
            String[] manudir = {"janúar", "febrúar", "mars", "apríl", "maí", "júní",
                                "júlí", "ágúst", "september", "október", "nóvember", "desember"};

            SimpleDateFormat sdf = new SimpleDateFormat("d M yyyy");
            Date start = sdf.parse("1 1 " + year);
            Calendar c = Calendar.getInstance(); 
            c.setTime(start);

            for (int i = 0; i < c.getActualMaximum(Calendar.DAY_OF_YEAR); i++, c.add(Calendar.DATE, 1)) {

                // Í upphafi hvers mánaðar sé prentað út hvaða mánuður þetta sé
                if (c.get(Calendar.DAY_OF_MONTH) == 1) {
                    String manudur = manudir[c.get(Calendar.MONTH)];
                    writer.println(Character.toUpperCase(manudur.charAt(0)) + manudur.substring(1, manudur.length()));
                    writer.println();
                }

                writer.println(dagar[c.get(Calendar.DAY_OF_WEEK)-1] + "dagur, "
                    + c.get(Calendar.DAY_OF_MONTH) + ". "
                    + manudir[c.get(Calendar.MONTH)]);
                writer.println();
                writer.println();

                // Í lok hvers mánaðar komi \n\n
                if (c.get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)+1) {
                     writer.println();
                     writer.println();
                }
            }

            writer.close();
        } catch (Exception e) {
            System.out.println("Villa");
        }
    }

    // post: Viðmót er sýnt
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                createAndShowGUI();
            }
        });
    }

    // post: Viðmót er sýnt
    public static void createAndShowGUI() {
        // Búa til hluti
        frame = new JFrame("TXT Dagbók");

        JLabel locationLabel = new JLabel("Setja í:");
        locationTextField = new JTextField(System.getProperty("user.home") + "\\Desktop");
        JButton browseButton = new JButton("Finna");

        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                openChooser();
            }
        });
        
        JLabel yearLabel = new JLabel("Ár:");
        yearTextField = new JTextField();
        
        JButton createButton = new JButton("Búa til");

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!locationTextField.getText().equals("")
                    && !yearTextField.getText().equals("")) {
                    createDict(locationTextField.getText(), Integer.parseInt(yearTextField.getText()));
                }
                else {
                    System.out.println("villa");
                }
            }
        });

        // GroupLayout sem heldur utan um alla hlutina
        GroupLayout layout = new GroupLayout(frame.getContentPane());
        frame.getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(Alignment.LEADING)
                .addComponent(locationLabel)
                .addComponent(yearLabel))
            .addGroup(layout.createParallelGroup(Alignment.LEADING)
                .addComponent(locationTextField)
                .addComponent(yearTextField))
            .addGroup(layout.createParallelGroup(Alignment.LEADING)
                .addComponent(browseButton)
                .addComponent(createButton))
        );

        layout.linkSize(SwingConstants.HORIZONTAL, browseButton, createButton);

        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(locationLabel)
                .addComponent(locationTextField)
                .addComponent(browseButton))
            .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(yearLabel)
                .addComponent(yearTextField)
                .addComponent(createButton))
        );

        // Ganga frá og sýna gluggann
        frame.pack();
        // Focusum á yearTextField
        yearTextField.requestFocusInWindow(); 
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // Svo Enter takkinn ýti á Búa til takkann
        frame.getRootPane().setDefaultButton(createButton);
        frame.setSize(350, 110);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2,
            dim.height/2-frame.getSize().height/2);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}