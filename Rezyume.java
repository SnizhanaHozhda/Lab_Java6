import javax.swing.*;
import java.awt.*;

public class Rezyume {

    public static void main(String[] args) {
        JFrame vikno = new JFrame("Конструктор резюме");
        vikno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vikno.setLayout(new BorderLayout(10, 10));

        JPanel panelDanyh = new JPanel(new GridLayout(4, 2, 5, 5));
        panelDanyh.setBorder(BorderFactory.createTitledBorder("Персональні дані"));

        JTextField polePib = new JTextField(15);
        JTextField polePosada = new JTextField(15);
        JTextField poleTelefon = new JTextField(15);
        JTextField polePoshta = new JTextField(15);

        panelDanyh.add(new JLabel("Прізвище та ім'я:"));
        panelDanyh.add(polePib);
        panelDanyh.add(new JLabel("Бажана посада:"));
        panelDanyh.add(polePosada);
        panelDanyh.add(new JLabel("Телефон:"));
        panelDanyh.add(poleTelefon);
        panelDanyh.add(new JLabel("Email:"));
        panelDanyh.add(polePoshta);

        JPanel panelVyboru = new JPanel(new GridLayout(2, 1, 5, 5));

        JPanel panelOsvita = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelOsvita.setBorder(BorderFactory.createTitledBorder("Рівень освіти"));

        JRadioButton knopkaBakalavr = new JRadioButton("Бакалавр", true);
        JRadioButton knopkaMagistr = new JRadioButton("Магістр");

        ButtonGroup grupaOsvita = new ButtonGroup();
        grupaOsvita.add(knopkaBakalavr);
        grupaOsvita.add(knopkaMagistr);

        panelOsvita.add(knopkaBakalavr);
        panelOsvita.add(knopkaMagistr);

        JPanel panelMovy = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelMovy.setBorder(BorderFactory.createTitledBorder("Володіння мовами"));

        JCheckBox praporetsUkr = new JCheckBox("Українська", true);
        JCheckBox praporetsAngl = new JCheckBox("Англійська");
        JCheckBox praporetsNim = new JCheckBox("Німецька");

        panelMovy.add(praporetsUkr);
        panelMovy.add(praporetsAngl);
        panelMovy.add(praporetsNim);

        panelVyboru.add(panelOsvita);
        panelVyboru.add(panelMovy);

        JPanel panelDosvid = new JPanel(new BorderLayout());
        panelDosvid.setBorder(BorderFactory.createTitledBorder("Досвід роботи"));

        JTextArea poleDosvid = new JTextArea(4, 25);
        panelDosvid.add(new JScrollPane(poleDosvid), BorderLayout.CENTER);

        JPanel panelKnopok = new JPanel();
        JButton knopkaSformuvaty = new JButton("Сформувати резюме");
        JButton knopkaSkynuty = new JButton("Скинути поля");

        panelKnopok.add(knopkaSformuvaty);
        panelKnopok.add(knopkaSkynuty);

        vikno.add(panelDanyh, BorderLayout.NORTH);
        vikno.add(panelVyboru, BorderLayout.CENTER);

        JPanel nyzhniyBlok = new JPanel(new BorderLayout());
        nyzhniyBlok.add(panelDosvid, BorderLayout.CENTER);
        nyzhniyBlok.add(panelKnopok, BorderLayout.SOUTH);
        vikno.add(nyzhniyBlok, BorderLayout.SOUTH);

        knopkaSformuvaty.addActionListener(e -> {
            String osvita = "Бакалавр";
            if (knopkaMagistr.isSelected()) {
                osvita = "Магістр";
            }

            String movy = "";
            if (praporetsUkr.isSelected()) movy = movy + "Українська ";
            if (praporetsAngl.isSelected()) movy = movy + "Англійська ";
            if (praporetsNim.isSelected()) movy = movy + "Німецька ";

            String tekst = "=== ГОТОВЕ РЕЗЮМЕ ===\n\n" +
                    "ПІБ: " + polePib.getText() + "\n" +
                    "Посада: " + polePosada.getText() + "\n" +
                    "Телефон: " + poleTelefon.getText() + "\n" +
                    "Email: " + polePoshta.getText() + "\n" +
                    "Освіта: " + osvita + "\n" +
                    "Мови: " + movy + "\n\n" +
                    "Досвід роботи:\n" + poleDosvid.getText();

            JDialog dialog = new JDialog(vikno, "Перегляд резюме", true);
            JTextArea tekstRezyume = new JTextArea(tekst, 14, 30);
            tekstRezyume.setEditable(false);

            dialog.add(new JScrollPane(tekstRezyume));
            dialog.pack();
            dialog.setLocationRelativeTo(vikno);
            dialog.setVisible(true);
        });

        knopkaSkynuty.addActionListener(e -> {
            polePib.setText("");
            polePosada.setText("");
            poleTelefon.setText("");
            polePoshta.setText("");
            knopkaBakalavr.setSelected(true);
            praporetsUkr.setSelected(false);
            praporetsAngl.setSelected(false);
            praporetsNim.setSelected(false);
            poleDosvid.setText("");
        });

        vikno.pack();
        vikno.setLocationRelativeTo(null);
        vikno.setVisible(true);
    }
}