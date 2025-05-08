
package projekpbo.controllers;

import java.util.List;
import javax.swing.table.DefaultTableModel;
import projekpbo.helper.DBhelper;
import projekpbo.views.AdminPanelView;
import projekpbo.models.KataModel;


public class AdminController {
    private AdminPanelView v;
    private final DefaultTableModel modelKataModel;
    
    public AdminController(){
    String [] header = {"ID", "Kata"};
    modelKataModel = new DefaultTableModel(header, 0);
    refreshTabel();
    
    v = new AdminPanelView(this);
    v.getjTableKata().setModel(modelKataModel);
    v.setVisible(true);
    }
    
    public void hapusdata(int id){
        DBhelper helper = new DBhelper();
        if(helper.removeKata(id)){
            refreshTabel();
        }else{
            javax.swing.JOptionPane.showMessageDialog(v, "Gagal Menghapus Kata", "ERROR", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void refreshTabel(){
    modelKataModel.setRowCount(0);
    DBhelper helper=  new DBhelper();
    
    List<KataModel> data = helper.getAllData();
    data.forEach((m)->{
        modelKataModel.addRow(new Object[]{m.getId(), m.getKata()});
    });
    }
}
