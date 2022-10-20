/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic;


/**
 *
 * @author gregorio
 * @Modified Tri Miyarno
 */
public class PSIGenerator {
    public String getAvalpaServiceID(int jumlahUser,int[] serviceId){
        String avalpa_service_id = "";
        for(int i = 1 ; i <= jumlahUser-1 ; ++i){
            avalpa_service_id += "avalpa"+(i)+"_service_id = "+serviceId[i-1]+"\n";
        }
        return avalpa_service_id;
    }

    public String getAvalpaPMTID(int jumlahUser,int[] valuePMTID){
        String avalpa_pmt_id = "";
        for(int i = 1 ; i <= jumlahUser-1 ; ++i){
            avalpa_pmt_id += "avalpa"+(i)+"_pmt_pid = "+valuePMTID[i-1]+"\n";
        }
        return avalpa_pmt_id;
    }

    public String getServiceDescriptor(int jumlahUser){
        String service_descriptor = "";
        for(int i = 1 ; i <= jumlahUser-1 ; ++i){
            service_descriptor +=   "\t\t\t\t\t\t service_descriptor_loop_item(\n" +
                                    "\t\t\t\t\t\t\t service_ID = avalpa"+(i)+"_service_id,\n" +
                                    "\t\t\t\t\t\t\t service_type = 1,\n" +
                                    "\t\t\t\t\t\t ),\n";
        }
        return service_descriptor;

    }
    public String getLogicalChannel(int jumlahUser,int[] serviceId){
        String lcn = "";
        for(int i = 1 ; i <= jumlahUser-1 ; ++i){
            lcn +=  "\t\t\t\t\t\t lcn_service_descriptor_loop_item(\n" +
                    "\t\t\t\t\t\t\t service_ID = avalpa"+(i)+"_service_id,\n" +
                    "\t\t\t\t\t\t\t visible_service_flag = 1,\n" +
                    "\t\t\t\t\t\t\t logical_channel_number = " + (100*i)+",\n" +
                    "\t\t\t\t\t\t ),\n";
        }
        return lcn;
    }
    public String getProgramLoopPAT(int jumlahUser){
        String program_loop = "";
        for(int i = 1 ; i <= jumlahUser-1 ; ++i){
            program_loop += "\t\t program_loop_item(\n"+
                            "\t\t\t program_number = avalpa"+(i)+"_service_id,\n" +
                            "\t\t\t PID = avalpa"+(i)+"_pmt_pid,\n" +
                            "\t\t ),\n";
        }
        return program_loop;
    }

    public String getServiceLoopSDT(int jumlahUser, String[] providerName, String[] serviceName){
        String service_loop = "";
        for(int i = 1 ; i <= jumlahUser-1 ; ++i){
            service_loop += "\t\t service_loop_item(\n" +
                            "\t\t\t service_ID = avalpa"+(i)+"_service_id,\n" +
                            "\t\t\t EIT_schedule_flag = 0,\n" +
                            "\t\t\t EIT_present_following_flag = 0,\n" +
                            "\t\t\t running_status = 4,\n" +
                            "\t\t\t free_CA_mode = 0,\n" +
                            "\t\t\t service_descriptor_loop = [\n" +
                            "\t\t\t\t service_descriptor(\n" +
                            "\t\t\t\t\t service_type = 1,\n" +
                            "\t\t\t\t\t service_provider_name = \""+providerName[i-1]+"\",\n" +
                            "\t\t\t\t\t service_name = \""+serviceName[i-1]+"\",\n" +
                            "\t\t\t\t ),\n" +
                            "\t\t\t ],\n" +
                            "\t\t ),\n";
        }
        return service_loop;
    }
    public String getPMT(int jumlahUser, int[] hasilPIDvideo, int[] hasilPIDaudio){
        String pmt = "";
        for(int i = 1 ; i <= jumlahUser-1 ; ++i){
            pmt +=  "pmt"+(i)+" = program_map_section(\n" +
                    "\t program_number = avalpa"+(i)+"_service_id,\n" +
                    "\t PCR_PID = 0x"+Integer.toHexString(hasilPIDvideo[i-1])+",\n" +
                    "\t program_info_descriptor_loop = [],\n" +
                    "\t stream_loop = [\n" +
                    "\t\t stream_loop_item(\n" +
                    "\t\t\t stream_type = 2,\n" +
                    "\t\t\t elementary_PID = 0x"+Integer.toHexString(hasilPIDvideo[i-1])+",\n" +
                    "\t\t\t element_info_descriptor_loop = []\n" +
                    "\t\t ),\n" +
                    "\t\t stream_loop_item(\n" +
                    "\t\t\t stream_type = 4,\n" +
                    "\t\t\t elementary_PID = 0x"+Integer.toHexString(hasilPIDaudio[i-1])+",\n" +
                    "\t\t\t element_info_descriptor_loop = []\n" +
                    "\t\t ),\n" +
                    "\t ],\n" +
                    "\t version_number = 1,\n" +
                    "\t section_number = 0,\n" +
                    "\t last_section_number = 0,\n" +
                    ")\n";
        }
        return pmt;
    }
    public String generatePMT(int jumlahUser, String folderUser){
        String output = "";
        for(int i = 1 ; i <= jumlahUser-1 ; ++i){
            output += "out = open('"+folderUser+"temps/pmt"+(i)+".sec', \"wb\")\n" +
                "out.write(pmt"+(i)+".pack())\n" +
                "out.close\n" +
                "out = open('"+folderUser+"temps/pmt"+(i)+".sec', \"wb\")\n" +
                "out.close\n" +
                "os.system('sec2ts ' + str(avalpa"+(i)+"_pmt_pid) + ' < "+folderUser+"temps/pmt"+(i)+".sec > "+folderUser+"temps/mptspmt"+(i)+".ts')\n";
        }
        return output;
    }

    public String generatePSI(int ini,int[] serviceId,String[] serviceName, int[] valuePMTID,String networkName,String[] providerName, String folderUser, int[] hasilPIDvideo, int[] hasilPIDaudio) throws Exception{
        String s = "";
        int jumlahUser = ini+1;
        s = "#! /usr/bin/env python\n" +
                "import os \n" +
                "from dvbobjects.PSI.PAT import *\n" +
                "from dvbobjects.PSI.NIT import *\n" +
                "from dvbobjects.PSI.SDT import *\n" +
                "from dvbobjects.PSI.PMT import *\n" +
                "from dvbobjects.PSI.TDT import *\n" +
                "from dvbobjects.DVB.Descriptors import *\n" +
                "from dvbobjects.MPEG.Descriptors import *\n" +
                "avalpa_transport_stream_id = 1\n" +
                "avalpa_original_transport_stream_id = 1\n" +
                getAvalpaServiceID(jumlahUser,serviceId)+
                getAvalpaPMTID(jumlahUser,valuePMTID) +
                "avalpa7_pmt_pid = 1037 \n" +
                "avalpa7_service_id = 7 \n" +
                "\n" +
                "nit = network_information_section(\n" +
                "\t network_id = 1,\n" +
                "\t network_descriptor_loop = [\n" +
                "\t\t network_descriptor(network_name = \""+networkName+"\",),\n" +
                "\t ],\n" +
                "\t transport_stream_loop = [\n" +
                "\t\t transport_stream_loop_item(\n" +
                "\t\t\t transport_stream_id = avalpa_transport_stream_id,\n" +
                "\t\t\t original_network_id = avalpa_original_transport_stream_id,\n" +
                "\t\t\t transport_descriptor_loop = [\n" +
                "\t\t\t\t service_list_descriptor(\n" +
                "\t\t\t\t\t dvb_service_descriptor_loop = [\n" +
                getServiceDescriptor(jumlahUser) +

                "\t\t\t\t\t\t service_descriptor_loop_item(\n" +
                "\t\t\t\t\t\t\t service_ID = avalpa7_service_id,\n" +
                "\t\t\t\t\t\t\t service_type = 128,\n" +
                "\t\t\t\t\t\t ),\n" + 
                "\t\t\t\t\t ],\n" +
                "\t\t\t\t ),\n" +
                "\t\t\t\t logical_channel_descriptor(\n" +
		"\t\t\t\t\t lcn_service_descriptor_loop = [\n" +
                getLogicalChannel(jumlahUser,serviceId)+

                "\t\t\t\t\t\t lcn_service_descriptor_loop_item(\n" +
                    "\t\t\t\t\t\t\t service_ID = avalpa7_service_id,\n" +
                    "\t\t\t\t\t\t\t visible_service_flag = 0,\n" +
                    "\t\t\t\t\t\t\t logical_channel_number = " + (700)+",\n" +
                    "\t\t\t\t\t\t ),\n" +
                "\t\t\t\t\t ],\n" +
		"\t\t\t\t ),\n" +
                "\t\t\t ],\n" +
                "\t\t ),\n" +
                "\t ],\n" +
                "\t version_number = 1,\n" +
                "\t section_number = 0,\n" +
                "\t last_section_number = 0,\n" +
                ")\n" +
                "\n" +
                "pat = program_association_section(\n" +
                "\t transport_stream_id = avalpa_transport_stream_id,\n" +
                "\t program_loop = [\n" +
                getProgramLoopPAT(jumlahUser) +

                "\t\t program_loop_item(\n"+
                "\t\t\t program_number = avalpa7_service_id,\n" +
                "\t\t\t PID = avalpa7_pmt_pid,\n" +
                "\t\t ),\n" +
                "\t\t program_loop_item(\n" +
                "\t\t\t program_number = 0,\n" +
                "\t\t\t PID = 16,\n" +
                "\t\t ),\n" +
                "\t ],\n" +
                "\t version_number = 1,\n" +
                "\t section_number = 0,\n" +
                "\t last_section_number = 0,\n" +
                ")\n" +
                "\n" +
                "sdt = service_description_section(\n" +
                "\t transport_stream_id = avalpa_transport_stream_id,\n" +
                "\t original_network_id = avalpa_original_transport_stream_id,\n" +
                "\t service_loop = [\n" +
                getServiceLoopSDT(jumlahUser,providerName,serviceName) +

                "\t\t service_loop_item(\n" +
                "\t\t\t service_ID = avalpa7_service_id,\n" +
                "\t\t\t EIT_schedule_flag = 0,\n" +
                "\t\t\t EIT_present_following_flag = 0,\n" +
                "\t\t\t running_status = 4,\n" +
                "\t\t\t free_CA_mode = 0,\n" +
                "\t\t\t service_descriptor_loop = [\n" +
                "\t\t\t\t service_descriptor(\n" +
                "\t\t\t\t\t service_type = 128,\n" +
                "\t\t\t\t\t service_provider_name = \""+providerName[6]+"\",\n" +
                "\t\t\t\t\t service_name = \""+serviceName[6]+"\",\n" +
                "\t\t\t\t ),\n" +
                "\t\t\t ],\n" +
                "\t\t ),\n" +

                "\t ],\n" +
                "\t version_number = 1,\n" +
                "\t section_number = 0,\n" +
                "\t last_section_number = 0,\n" +
                ")\n" +
                "\n" +
                getPMT(jumlahUser,hasilPIDvideo, hasilPIDaudio) +
                "\n" +
                "\n" +
                "out = open('"+folderUser+"temps/nit.sec\', \"wb\")\n" +
                "out.write(nit.pack())\n" +
                "out.close\n" +
                "out = open('"+folderUser+"temps/nit.sec', \"wb\")\n" +
                "out.close\n" +
                "os.system('sec2ts 16 < "+folderUser+"temps/nit.sec > "+folderUser+"temps/mptsnit.ts')\n" +
                "out = open('"+folderUser+"temps/pat.sec', \"wb\")\n" +
                "out.write(pat.pack())\n" +
                "out.close\n" +
                "out = open('"+folderUser+"temps/pat.sec', \"wb\")\n" +
                "out.close\n" +
                "os.system('sec2ts 0 < "+folderUser+"temps/pat.sec > "+folderUser+"temps/mptspat.ts')\n" +
                "out = open('"+folderUser+"temps/sdt.sec', \"wb\")\n" +
                "out.write(sdt.pack())\n" +
                "out.close\n" +
                "out = open('"+folderUser+"temps/sdt.sec', \"wb\")\n" +
                "out.close\n" +
                "os.system('sec2ts 17 < "+folderUser+"temps/sdt.sec > "+folderUser+"temps/mptssdt.ts')\n" +
                generatePMT(jumlahUser, folderUser) +
                "os.system('rm "+folderUser+"temps/*.sec') \n" ;
                
        return s;
    }


}
