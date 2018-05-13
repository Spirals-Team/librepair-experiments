/**
 * объявление пакета
 */
package kr.KR;
/**
 * Импорт библиотек классов, необходимых для работы.
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
/**
 * 
 * Объявление публичного класса, наследующего члены класса JFrame 
 */
public class MainForm extends JFrame {
	/**
	 * Значения для напряжения в сети
	 * 
	 */
	static int n1=220,n2=380;
	/**
	 * Загрузка инконки для главной формы
	 * 
	 */
	public static ImageIcon icon1 = new ImageIcon(MainForm.class.getResource("/main.png"));
	/**
	 * Метод, отвечающий за запуск фрейма
	 * 
	 */
	public static void main(String[] args) {
		/**
		 * Создание и вызов главной формы
		 */
					MainForm frame = new MainForm();
					frame.setVisible(true);
	}
	/**
	 * Объявление метода, в котором объявляются элементы экранной формы, ее события
	 */
	public MainForm() {
		ImageIcon icon2 = new ImageIcon(MainForm.class.getResource("/home.png"));
		ImageIcon icon3 = new ImageIcon(MainForm.class.getResource("/production.png"));
		/**
		 * Создание главной панели
		 */
		TitledBorder titledBorder = BorderFactory.createTitledBorder(null, "Выберите необходимый вам калькулятор:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("times new roman",Font.PLAIN,15), Color.black);
		JPanel contentPane = new JPanel();
		/**
		 * Отключение автоматичского размещения компонентов на панели
		 */
		contentPane.setLayout(null);
		contentPane.setBorder(titledBorder);
		JPanel Panel1 = new JPanel();
		/**
		 * указывает координаты верхней левой вершины окна, а также его ширину и высоту.
		 */
		Panel1.setBounds(50,15,500,650);
		JPanel Panel2 = new JPanel();
		/**
		 * Устанавливаем иконку для главной формы
		 * 
		 */
		setIconImage(icon1.getImage());
		/**
		 * Отключение автоматичского размещения компонентов на панели
		 */
		Panel2.setLayout(null);
		/**
		 * указывает координаты верхней левой вершины окна, а также его ширину и высоту.
		 */
		Panel2.setBounds(5,15,650,650);
		/**
		 * Задание шрифта и размера надписей для кнопок
		 */
		Font font = new Font("Times New Roman", Font.PLAIN, 25);
		/**
		 * Создание кнопок
		 */
		JButton btnNewButton1 = new JButton("Для жилых помещений");
		JButton btnNewButton2 = new JButton("Для производственных помещений");
		/**
		 * Задание шрифта для кнопки 
		 */
		btnNewButton1.setFont(font);
		btnNewButton1.setIcon(icon2);
		/**
		 * указывает координаты верхней левой вершины кнопки, а также его ширину и высоту.
		 */
		btnNewButton1.setBounds(1, 10, 620, 100);
		/**
		 * отключение первоначального фокусирования на кнопке
		 */
		btnNewButton1.setFocusable(isDisplayable());
		/**
		 * Задание шрифта для кнопки 
		 */
		btnNewButton2.setFont(font);
		btnNewButton2.setIcon(icon3);
		/**
		 * указывает координаты верхней левой вершины кнопки, а также его ширину и высоту.
		 */
		btnNewButton2.setBounds(1,110, 620, 100);
		/**
		 * отключение первоначального фокусирования на кнопке
		 */
		btnNewButton2.setFocusable(isDisplayable());
		/**
		 * Добавление элементов на панель для кнопки
		 */
		Panel2.add(btnNewButton1);
		Panel2.add(btnNewButton2);
		/**
		 * Добавление элементов на главную панель
		 */
		contentPane.add(Panel2);
		/**
		 * Запрет на изменения размера окна
		 */
		setResizable(false);
		/**
		 * Установка названия окна
		 */
		setTitle("Расчет номинала предохранителей в электрической цепи");
		/**
		 * Замена панели содержимого окна
		 */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(contentPane);
		setBounds(100, 100, 650, 280);
		/**
		 * Событие для кнопки "Для жилых помещений". 
		 */
		btnNewButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Для жилых помещений")) {
					new ComponentForm();
					/**
					 * Меняем цвет кнокпи. 
					 */
					ComponentForm.btnNewButton.setBackground(Color.green);
					ComponentForm.frame.setTitle("Для жилых помещений");
					ComponentForm.TxtBox2.setText(String.valueOf(n1));
				    }
			
			}
		});
		/**
		 * Событие для кнопки "Для производственных помещений". 
		 */
		btnNewButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Для производственных помещений")) {
					new ComponentForm();
					/**
					 * Меняем цвет кнокпи. 
					 */
					ComponentForm.btnNewButton.setBackground(Color.green);
					ComponentForm.frame.setTitle("Для производственных помещений");
					ComponentForm.TxtBox2.setText(String.valueOf(n2));
				    }
			}
		});
	}
}