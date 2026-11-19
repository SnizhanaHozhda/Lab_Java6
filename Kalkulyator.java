import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Kalkulyator implements Runnable {

    public static void main(String[] args) {
        // Інтерфейс Swing створюємо в окремому потоці
        SwingUtilities.invokeLater(new Kalkulyator());
    }

    public void run() {
        JFrame vikno = new JFrame("Депозитний калькулятор");
        vikno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel napysSuma = new JLabel("Сума вкладу (грн):");
        JLabel napysVidsotok = new JLabel("Річна ставка (%):");
        JLabel napysMisyatsi = new JLabel("Термін (місяців):");

        JTextField poleSuma = new JTextField();
        JTextField poleVidsotok = new JTextField();
        JTextField poleMisyatsi = new JTextField();

        JButton knopka = new JButton("Обчислити дохід");
        JLabel napysRezultat = new JLabel("Дохід: ");

        panel.add(napysSuma);
        panel.add(poleSuma);
        panel.add(napysVidsotok);
        panel.add(poleVidsotok);
        panel.add(napysMisyatsi);
        panel.add(poleMisyatsi);
        panel.add(knopka);
        panel.add(napysRezultat);

        knopka.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    double suma = Double.parseDouble(poleSuma.getText().replace(",", "."));
                    double vidsotok = Double.parseDouble(poleVidsotok.getText().replace(",", "."));
                    int misyatsi = Integer.parseInt(poleMisyatsi.getText());

                    if (suma <= 0 || vidsotok <= 0 || misyatsi <= 0) {
                        JOptionPane.showMessageDialog(vikno,
                                "Числа мають бути більші за нуль!",
                                "Помилка", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    double prybutok = suma * (vidsotok / 100) * (misyatsi / 12.0);

                    napysRezultat.setText(String.format("Дохід: %.2f грн", prybutok));
                } catch (NumberFormatException pomylka) {
                    JOptionPane.showMessageDialog(vikno,
                            "Введіть числа! Термін - ціле число.",
                            "Помилка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        vikno.add(panel);
        vikno.pack();
        vikno.setLocationRelativeTo(null);
        vikno.setVisible(true);
    }
}