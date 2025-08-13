package system.ui.panels;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class HeaderPanel extends JPanel{
	private static final long serialVersionUID = -7398402002507511576L;
	private JTextField url_field, count_field;

	public HeaderPanel() {
		setLayout(new BorderLayout(10, 10));
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createRaisedBevelBorder(),
				BorderFactory.createEmptyBorder(10, 10, 10, 10))
		);
		
		url_field = new JTextField("C:/Users/user/Downloads/CircleTags");
		count_field = new JTextField(10);
		count_field.setText("0");
		
		add(new JLabel("URL"), BorderLayout.WEST);
		add(url_field, BorderLayout.CENTER);
		add(count_field, BorderLayout.EAST);
		
	}
	@Override
	public void setEnabled(boolean enabled) {
		url_field.setEnabled(enabled);
		count_field.setEnabled(enabled);
		super.setEnabled(enabled);
	}
	public String getURL() {
		return url_field.getText();
	}
	public void nextCount() {
		count_field.setText("" + (getCurrentTagCount() + 1));
	}
	public int getCurrentTagCount() {
		return Integer.parseInt(count_field.getText());
	}
}
