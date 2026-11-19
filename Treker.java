import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class Treker implements Runnable {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Treker());
    }

    private void onovytyBalans(DefaultTableModel model, JLabel napysBalans) {
        double balans = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            String typ = (String) model.getValueAt(i, 1);
            double suma = (Double) model.getValueAt(i, 3);
            if (typ.equals("Дохід")) {
                balans = balans + suma;
            } else {
                balans = balans - suma;
            }
        }
        napysBalans.setText(String.format("Поточний баланс: %.2f грн", balans));
        if (balans >= 0) {
            napysBalans.setForeground(new Color(0, 130, 0)); 
        } else {
            napysBalans.setForeground(Color.RED);           
        }
    }

    public void run() {
        JFrame vikno = new JFrame("Фінансовий трекер");
        vikno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vikno.setLayout(new BorderLayout(10, 10));

        // Меню 
        JMenuBar menuPanel = new JMenuBar();

        JMenu menuFayl = new JMenu("Файл");
        JMenuItem punktOchystyty = new JMenuItem("Очистити таблицю");
        JMenuItem punktVykhid = new JMenuItem("Вихід");
        menuFayl.add(punktOchystyty);
        menuFayl.add(punktVykhid);

        JMenu menuDovidka = new JMenu("Довідка");
        JMenuItem punktProProhramu = new JMenuItem("Про програму");
        menuDovidka.add(punktProProhramu);

        menuPanel.add(menuFayl);
        menuPanel.add(menuDovidka);
        vikno.setJMenuBar(menuPanel);

   
        String[] stovpchyky = {"Дата", "Тип", "Категорія", "Сума", "Коментар"};
        DefaultTableModel model = new DefaultTableModel(stovpchyky, 0);
        JTable tablytsya = new JTable(model);
        tablytsya.setDefaultEditor(Object.class, null);  // клітинки не редагуються
        tablytsya.setPreferredScrollableViewportSize(new Dimension(650, 200));

        //  Зверху: поля для нового запису 
        JTextField poleData = new JTextField(LocalDate.now().toString(), 8);
        JComboBox<String> comboTyp = new JComboBox<String>(new String[]{"Дохід", "Витрата"});
        JTextField poleKategoriya = new JTextField(8);
        JTextField poleSuma = new JTextField(6);
        JTextField poleKomentar = new JTextField(10);
        JButton knopkaDodaty = new JButton("Додати");

        JPanel panelVvedennya = new JPanel();
        panelVvedennya.add(new JLabel("Дата:"));
        panelVvedennya.add(poleData);
        panelVvedennya.add(comboTyp);
        panelVvedennya.add(new JLabel("Категорія:"));
        panelVvedennya.add(poleKategoriya);
        panelVvedennya.add(new JLabel("Сума:"));
        panelVvedennya.add(poleSuma);
        panelVvedennya.add(new JLabel("Коментар:"));
        panelVvedennya.add(poleKomentar);
        panelVvedennya.add(knopkaDodaty);

        //  Знизу: кнопка видалення і баланс -
        JButton knopkaVydalyty = new JButton("Видалити вибраний рядок");
        JLabel napysBalans = new JLabel("", JLabel.CENTER);
        napysBalans.setFont(new Font("Arial", Font.BOLD, 16));
        onovytyBalans(model, napysBalans);

        JPanel panelNyzu = new JPanel(new BorderLayout(10, 10));
        panelNyzu.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        panelNyzu.add(knopkaVydalyty, BorderLayout.WEST);
        panelNyzu.add(napysBalans, BorderLayout.CENTER);

        vikno.add(panelVvedennya, BorderLayout.NORTH);
        vikno.add(new JScrollPane(tablytsya), BorderLayout.CENTER);
        vikno.add(panelNyzu, BorderLayout.SOUTH);

        //  Кнопка "Додати" 
        knopkaDodaty.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String data = poleData.getText().trim();
                    String typ = (String) comboTyp.getSelectedItem();
                    String kategoriya = poleKategoriya.getText().trim();
                    double suma = Double.parseDouble(poleSuma.getText().trim().replace(",", "."));
                    String komentar = poleKomentar.getText().trim();

                    if (data.equals("") || kategoriya.equals("") || suma <= 0) {
                        JOptionPane.showMessageDialog(vikno,
                                "Заповніть дату і категорію, сума має бути більша за нуль!",
                                "Помилка", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    model.addRow(new Object[]{data, typ, kategoriya, suma, komentar});
                    onovytyBalans(model, napysBalans);

                    // Очищаємо поля для наступного запису
                    poleKategoriya.setText("");
                    poleSuma.setText("");
                    poleKomentar.setText("");
                } catch (NumberFormatException pomylka) {
                    // У полі суми ввели не число
                    JOptionPane.showMessageDialog(vikno,
                            "Сума має бути числом!",
                            "Помилка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //  Кнопка "Видалити вибраний рядок" 
        knopkaVydalyty.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int ryadok = tablytsya.getSelectedRow();  // -1, якщо нічого не вибрано
                if (ryadok == -1) {
                    JOptionPane.showMessageDialog(vikno,
                            "Спочатку виберіть рядок у таблиці!",
                            "Помилка", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                model.removeRow(ryadok);
                onovytyBalans(model, napysBalans);
            }
        });

        //  Файл  Очистити таблицю 
        punktOchystyty.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int vidpovid = JOptionPane.showConfirmDialog(vikno,
                        "Видалити всі записи з таблиці?",
                        "Підтвердження", JOptionPane.YES_NO_OPTION);
                if (vidpovid == JOptionPane.YES_OPTION) {
                    model.setRowCount(0);  // видаляємо всі рядки
                    onovytyBalans(model, napysBalans);
                }
            }
        });

        // Файл Вихід 
        punktVykhid.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        //  Довідка  Про програму
        punktProProhramu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(vikno,
                        "Особистий фінансовий трекер\n"
                                + "Лабораторна робота №6, завдання 3\n"
                                + "Автор: ПІБ студентки, група",
                        "Про програму", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Підганяємо розмір вікна і показуємо
        vikno.pack();
        vikno.setLocationRelativeTo(null);
        vikno.setVisible(true);
    }
}