package Service;

import DAO.NewDeviceRecordDAO;
import DAO.MachineNameDAO;
import model.NewDeviceMachine;
import model.NewDeviceRecord;
import model.NewDeviceStyle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListResourceBundle;

/**
 * Created by yxy on 9/8/17.
 */
public class NewDeviceRecordService {
    private Date firstDay=new Date(117,0,1);

    public List<NewDeviceMachine> GetMachineByDate(java.util.Date date1,java.util.Date date2){
        MachineNameDAO mnDAO=new MachineNameDAO();
        List<String> machineList=mnDAO.GetMachineName();
        List<NewDeviceMachine> ndmList=new ArrayList<>();
        for(int i=0;i<machineList.size();i++){
            NewDeviceMachine ndm=new NewDeviceMachine();
            ndm.setMachinename(machineList.get(i));
            NewDeviceRecordDAO ndrDAO=new NewDeviceRecordDAO();
            List<NewDeviceRecord> ndrList=ndrDAO.SelectByDateAndMachine(new java.sql.Date(date1.getTime()),
                    new java.sql.Date(date2.getTime()),machineList.get(i));
            int newnum=0;
            for(int j=0;j<ndrList.size();j++){
                newnum+=ndrList.get(j).getNumber();
            }
            ndm.setNewnum(newnum);
            NewDeviceRecordDAO ndrDAO2=new NewDeviceRecordDAO();
            List<NewDeviceRecord> ndrList2=ndrDAO2.SelectByDateAndMachine(new java.sql.Date(firstDay.getTime()),
                    new java.sql.Date(date1.getTime()),machineList.get(i));
            int oldnum=0;
            for(int j=0;j<ndrList2.size();j++){
                oldnum+=ndrList2.get(j).getNumber();
            }
            ndm.setOldnum(oldnum);
            ndm.setAllnum(newnum+oldnum);
            ndmList.add(ndm);
        }
        return ndmList;
    }

    public List<NewDeviceStyle> GetStyleByDateMachine(java.util.Date date1,java.util.Date date2,String machinename){
        NewDeviceRecordDAO ndrDAO=new NewDeviceRecordDAO();
        List<NewDeviceRecord> ndrList=ndrDAO.SelectByDateAndMachine(new java.sql.Date(date1.getTime()),
                new java.sql.Date(date2.getTime()),machinename);
        List<NewDeviceStyle> ndsList=new ArrayList<>();
        for(int i=0;i<ndrList.size();i++){
            NewDeviceStyle nds=new NewDeviceStyle();
            NewDeviceRecordDAO ndrDAO2=new NewDeviceRecordDAO();
            List<NewDeviceRecord> ndrList2=ndrDAO2.SelectByDateAndStyle(new java.sql.Date(firstDay.getTime()),
                    new java.sql.Date(date1.getTime()),ndrList.get(i).getStyle());
            int newnum=0;
            for(int j=0;j<ndrList2.size();j++){
                newnum+=ndrList2.get(j).getNumber();
            }
            NewDeviceRecordDAO ndrDAO3=new NewDeviceRecordDAO();
            List<NewDeviceRecord> ndrList3=ndrDAO3.SelectByDateAndStyle(new java.sql.Date(firstDay.getTime()),
                    new java.sql.Date(date1.getTime()),ndrList.get(i).getStyle());
            int oldnum=0;
            for(int j=0;j<ndrList3.size();j++){
                oldnum+=ndrList3.get(j).getNumber();
            }
            nds.setNewDeviceStyle(ndrList.get(i).getStyle().substring(machinename.length()),oldnum,newnum,oldnum+newnum);
            ndsList.add(nds);
        }
        return ndsList;

    }

    public static void main(String args[]){
        NewDeviceRecordService snrs=new NewDeviceRecordService();
        List<NewDeviceMachine> ndmList=snrs.GetMachineByDate(new Date(117,0,1),new Date(117,0,1));
        for(int i=0;i<ndmList.size();i++){
            System.out.println(ndmList.get(i));
        }
        List<NewDeviceStyle> ndsList=snrs.GetStyleByDateMachine(new Date(117,0,1),new Date(117,0,1),"小米");
        for(int i=0;i<ndmList.size();i++){
            System.out.println(ndsList.get(i));
        }
    }
}
