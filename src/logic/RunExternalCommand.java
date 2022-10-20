package logic;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import javax.swing.JOptionPane;
/**
 *
 * @author trie
 */

public class RunExternalCommand {
        public String[] cmd = new String[3];
        public String s = null, ss = "";
        public void runcommand(){
        try {

            // run the command
      
            Process p = Runtime.getRuntime().exec(cmd);

            BufferedReader stdInput = new BufferedReader(new
                 InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                 InputStreamReader(p.getErrorStream()));


            // read the output from the command
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
                ss = ss + s + "\n";

            }

            if(ss!=""){
                 JOptionPane.showMessageDialog(null, "Terjadi kesalahan pada command:\n"+ss+"\nTrie");
                 ss = "";
            }

            // read any errors from the attempted command
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
                ss = ss + s + "\n";
            }

            if(ss!=""){
                 JOptionPane.showMessageDialog(null, "Terjadi kesalahan pada command:\n"+ss+"\nTrie");
                 ss = "";
            }
            System.out.println("========TRI MIYARNO========");
            stdInput.close();
            stdError.close();

        } catch (IOException e) {
            System.out.println("[IOException]. Printing Stack Trace");
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
