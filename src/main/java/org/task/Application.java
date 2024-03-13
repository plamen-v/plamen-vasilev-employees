package org.task;

import org.task.model.PairKey;
import org.task.model.PairValue;
import org.task.repository.TeamLongestPeriodRepository;
import org.task.service.TeamLongestPeriodService;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.util.*;

public class Application extends JFrame {

    final static String TITLE = "Team Longest Period";
    final static  String[] COLUMNS = new String[] {
        "Employee ID #1",
        "Employee ID #2",
        "Project ID",
        "Days"
    };

    private TeamLongestPeriodRepository repo;
    private JPanel pnlMain;
    private JButton btnBrowse;
    private JLabel lblResult;
    private JTable tblDetails;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run(){
                try{
                    Application app = new Application();
                    app.initGUI();
                }catch(Exception ex){
                    System.out.println(ex.getMessage());
                }
            }
        });
    }

    public Application() {}

    private void initGUI() throws Exception {
        repo = new TeamLongestPeriodRepository();

        pnlMain = new JPanel();
        pnlMain.setLayout(null);

        btnBrowse = new JButton();
        btnBrowse.setText("Browse");
        btnBrowse.setBounds(5,5, 100,25);
        btnBrowse.addActionListener(event -> {
            btnBrowseClick();
        });
        pnlMain.add(btnBrowse);

        lblResult = new JLabel();
        lblResult.setBounds(5, 30, 435, 25);
        pnlMain.add(lblResult);

        tblDetails = new JTable( new Object[][] {}, COLUMNS);
        tblDetails.getTableHeader().setReorderingAllowed(false);
        tblDetails.setDefaultEditor(Object.class, null);
        tblDetails.setVisible(true);

        JScrollPane scroll = new JScrollPane(tblDetails);
        scroll.setBounds(5,55, 400,265);
        scroll.setVisible(true);
        pnlMain.add(scroll);

        setContentPane(pnlMain);

        setTitle(TITLE);
        setSize(410,350);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void btnBrowseClick(){
        try{
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("CSV", "csv", "txt"));
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

            int choice = fileChooser.showOpenDialog(null);
            if(choice == JFileChooser.APPROVE_OPTION){
                repo.loadData(fileChooser.getSelectedFile().getAbsolutePath());
                setTitle(TITLE + " - " + fileChooser.getSelectedFile().getName());

                Map.Entry<PairKey, PairValue> result = TeamLongestPeriodService.getTeamLongestPeriod(repo.getData());
                if(result != null){
                    lblResult.setText(result.getKey().getEmployeeId1() + ", " + result.getKey().getEmployeeId2() + ", " + result.getValue().getCommonDays());

                    DefaultTableModel model = new DefaultTableModel();
                    model.setRowCount(0);
                    model.setDataVector(TeamLongestPeriodService.prepareTblData(result), COLUMNS);
                    tblDetails.setModel(model);
                }
            }else{
                setTitle(TITLE + " - No file selected!");
            }
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(btnBrowse, ex.getMessage());
        }
    }
}
