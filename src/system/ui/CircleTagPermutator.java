package system.ui;

import java.awt.BorderLayout;
import java.awt.Graphics2D;

import javax.swing.JFrame;

import io.CheckPointFile;
import io.ImageFileWriter;
import marker.CircleTag;
import system.ui.panels.HeaderPanel;
import system.ui.panels.PermutationPanel;
import system.ui.panels.PreviewPanel;

public class CircleTagPermutator extends JFrame{
	private static final long serialVersionUID = 6433441272110175611L;
	private HeaderPanel header_panel;
	private PreviewPanel preview_panel;
	private PermutationPanel permutation_panel;
	
	private ImageFileWriter img_fileWriter;
	private CheckPointFile checkPoint_file;
	
	public CircleTagPermutator() {
		setTitle("CircleTag - Generator");
		setLayout(new BorderLayout());
		
		header_panel = new HeaderPanel();
		preview_panel = new PreviewPanel();
		permutation_panel = new PermutationPanel() {
			private static final long serialVersionUID = 1375404505470477317L;
			private String folder;
			private int id;
			
			@Override
			public void onRun() {
				header_panel.setEnabled(false);
			}
			@Override
			public void onStop() {
				header_panel.setEnabled(true);
				checkPoint_file.save(header_panel.getURL(), header_panel.getCurrentTagCount(), getPermutationTable().getTableValues());
			}
			@Override
			public void onNextCircleTag(CircleTag circle_tag) {
				
				if(circle_tag.isMarkerValid()) {
					preview_panel.setCircleTag(circle_tag);
					
					id = circle_tag.getMarkerId();
					img_fileWriter.draw();
					if(id > 0)
						folder = "/positives/id_" + id + "/";
					else if (id < 0)
						folder = "/negatives/id_" + id + "/";
					else 
						folder = "/zeros/id_" + id + "/";
					img_fileWriter.saveImage(header_panel.getURL() + folder + "tag" + header_panel.getCurrentTagCount() + "_id" + id + ".png");
					
					header_panel.nextCount();
				}
			}
		};
		
		img_fileWriter = new ImageFileWriter(header_panel.getURL(), 120, 120) {
			@Override
			public void onDraw(Graphics2D g2d) {
				preview_panel.getCircleTag().draw(g2d, 0, 0);
			}
		};
		checkPoint_file = new CheckPointFile();

		add(header_panel, BorderLayout.NORTH);
		add(preview_panel, BorderLayout.CENTER);
		add(permutation_panel, BorderLayout.EAST);
		
	}
	
}
