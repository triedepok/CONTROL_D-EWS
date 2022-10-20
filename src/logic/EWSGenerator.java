/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic;

/**
 *
 * @author trie
 */
public class EWSGenerator {

        public String generateWarningCode(int jumlahBencana, String[] WarningDisc){
        String ewswct = "";
        for(int i = 1 ; i <= jumlahBencana-1 ; ++i){
	    ewswct += "\t\t\t ews_wctt_loop_item( \n" +
                      "\t\t\t\t ews_warning_code = "+(i-1)+", \n" +
                      "\t\t\t\t ews_warning_code_descriptor =  \""+WarningDisc[i-1]+"\", \n" +
                      "\t\t\t ), \n" ;
            }
            return ewswct;
        }

        private String generateTsrcttable(int jumlahRegion, String[] Region) {
        String ewstsrct = "";
        for(int i = 1 ; i <= jumlahRegion ; ++i){
	    ewstsrct += "\t\t\t\t\t\t ews_tsrct_code_item( \n" +
			"\t\t\t\t\t\t ews_region_code = "+Region[i-1]+", \n" +
                        "\t\t\t\t\t\t ), \n" ;
            }
            return ewstsrct;
        }


        private String generateMsgtable(int jumlahRegion, String[] Region) {
        String ewsmsg = "";
        for(int i = 1 ; i <= jumlahRegion ; ++i){
	    ewsmsg += "\t\t\t\t\t\t ews_msgregion_code_item( \n" +
                      "\t\t\t\t\t\t\t ews_msgregion_code = "+Region[i-1]+", \n" +
                      "\t\t\t\t\t\t ), \n" ;
            }
            return ewsmsg;
        }

        private String generateTcttable(String[] RegionDisc, int rowRegion) {
        String ewsrct = "";
        int NumRegionDisc = RegionDisc.length;
        for(int i = 1 ; i <= NumRegionDisc ; ++i){
	    ewsrct += "\t\t\t\t ews_rct_loop_item( \n" +
                      "\t\t\t\t\t ews_region_code = "+ (i+rowRegion) +", \n" +
                      "\t\t\t\t\t ews_region_code_descriptor =  \""+RegionDisc[i-1]+"\", \n" +
                      "\t\t\t\t ), \n" ;
            }
            return ewsrct;
        }

        public String generateEWS(String ServerID, String ServerDesc, String CountryID, String WarningCode, String[] WarningDisc, String Message, String TotalRegion, String[] Region,String folderUser, String[] RegionDisc1, String[] RegionDisc2, String[] RegionDisc3, String[] RegionDisc4) {
        String p = "";
        int jumlahRegion = Region.length;
        int jumlahBencana = WarningDisc.length;
        int jumlahRegionDisc = RegionDisc1.length + RegionDisc2.length + RegionDisc3.length + RegionDisc4.length;
        int rowRegion1 = 0;
        int rowRegion2 = RegionDisc1.length;
        int rowRegion3 = RegionDisc1.length+RegionDisc2.length;
        int rowRegion4 = RegionDisc1.length+RegionDisc2.length+RegionDisc3.length;

        p = "#! /usr/bin/env python\n" +
                "import os \n" +
                "from dvbobjects.PSI.PAT import *\n" +
                "from dvbobjects.PSI.NIT import *\n" +
                "from dvbobjects.PSI.SDT import *\n" +
                "from dvbobjects.PSI.PMT import *\n" +
                "from dvbobjects.PSI.TDT import *\n" +
                "from dvbobjects.DVB.Descriptors import *\n" +
                "from dvbobjects.MPEG.Descriptors import *\n" +
                "from dvbobjects.INDEWS.EWSIT import * \n" +
                "from dvbobjects.INDEWS.EWSRCT import * \n" +
                "from dvbobjects.INDEWS.EWSWCTT import * \n" +
                "from dvbobjects.INDEWS.EWSTSRCT import * \n" +
                "from dvbobjects.INDEWS.EWSMSG import * \n" +
                "\n" +
                "\n" +
                "indews_pmt_pid =  1037 \n" +
                "ews_service_id = 7 \n" +
                "indews_data_pid =  1037 \n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "ewsit = ews_info_section( \n" +
                "\t ewsit_loop = [ \n" +
                "\t\t ews_info_loop_item( \n" +
		"\t\t\t ews_serverid = "+ServerID+", \n" +
		"\t\t\t ews_country_id = "+CountryID+", \n" +
		"\t\t\t transport_stream_id = 1, \n" +
		"\t\t\t ews_version_no = 1, \n" +
		"\t\t\t ews_owner_descriptor = \""+ServerDesc+"\", \n" +
                "\t\t ), \n" +
                "\t ], \n" +
                "\t version_number = 1,\n" +
                "\t section_number = 0,\n" +
                "\t last_section_number = 0,\n" +
                "\t ) \n" +
                "\n" +
                "\n" +
                "\n" +
                "ewswcttable = [ \n" +
                "\t ews_wctt_section (\n" +
                "\t\t ews_total_warning_code = "+jumlahBencana+", \n" +
                "\t\t ews_wctt_loop = [ \n" +
                generateWarningCode(jumlahBencana,WarningDisc) +
                "\t\t\t ews_wctt_loop_item ( \n" +
                "\t\t\t\t ews_warning_code = 0xFF, \n" +
                "\t\t\t\t ews_warning_code_descriptor =  \""+WarningDisc[jumlahBencana-1]+"\", \n" +
                "\t\t\t ), \n" +
                "\n" +
                "\t\t ], \n" +
                "\t\t \n" +
                "\t\t \n" +
                "\t version_number = 1,\n" +
                "\t section_number = 0,\n" +
                "\t last_section_number = 0,\n" +
                "\t ), \n" +
                "] \n" +
                "\n" +
                "\n" +
                "\n" +
                "ewstsrcttable = [ \n" +
                "\t ews_tsrct_section( \n" +
                "\t\t ews_total_map_item = 1, \n" +
                "\t\t\t ews_tsrct_loop = [ \n" +
                "\t\t\t\t ews_tsrct_loop_item ( \n" +
		"\t\t\t\t\t ews_tsstream_id = "+ServerID+", \n" +
		"\t\t\t\t\t ews_tsrct_code_loop = [ \n" +
                generateTsrcttable(jumlahRegion, Region) +
		"\t\t\t\t\t ] \n" +
                "\t\t\t\t ) \n" +
                "\t\t\t ],\n" +
                "\t version_number = 1,\n" +
                "\t section_number = 0,\n" +
                "\t last_section_number = 0,\n" +
                "\t ), \n" +
                "] \n" +
                "\n" +
                "\n" +
                "\n" +
                "ewsmsgtable = [ \n" +
                "\t ews_msg_section( \n" +
                "\t\t ews_total_msg_item = 1, \n" +
                "\t\t\t ews_msg_loop = [ \n" +
                "\t\t\t\t ews_msg_loop_item ( \n" +
		"\t\t\t\t\t ews_warning_code = "+WarningCode+", \n" +
		"\t\t\t\t\t ews_msg_text = \""+Message+"\", \n" +
		"\t\t\t\t\t ews_msgregion_code_loop = [ \n" +
                generateMsgtable(jumlahRegion, Region) +
		"\t\t\t\t\t ] \n" +
                "\t\t\t\t ), \n" +
                "\t\t\t ], \n" +
                "\n" +
                "\t version_number = 1,\n" +
                "\t section_number = 0,\n" +
                "\t last_section_number = 0,\n" +
                "\t ), \n" +
                "] \n" +
                "\n" +
                "\n" +
                "ewspmt = program_map_section(\n" +
                "\t program_number = ews_service_id,\n" +
                "\t PCR_PID = 0, \n" +
                "\t program_info_descriptor_loop = [],\n" +
                "\t stream_loop = [\n" +
                "\t\t stream_loop_item(\n" +
                "\t\t\t stream_type = 128,\n" +
                "\t\t\t elementary_PID = indews_data_pid,\n" +
                "\t\t\t element_info_descriptor_loop = []\n" +
                "\t\t ),\n" +
                "\t ],\n" +
                "\t version_number = 1,\n" +
                "\t section_number = 0,\n" +
                "\t last_section_number = 0,\n" +
                ")\n" +
                "\n" +
                "\n" +
                "ewsrctable = [ \n" +
                "\t ews_rct_section( \n" +
                "\t\t ews_total_region_code = "+jumlahRegionDisc+", \n" +
                "\t\t\t ews_rct_loop = [ \n" +
                generateTcttable(RegionDisc1,rowRegion1) +
                "\t\t\t ],\n" +
                "\t\t\t version_number = 1,\n" +
                "\t\t\t section_number = 0,\n" +
                "\t\t\t last_section_number = 3,\n" +
                "\t\t ),\n" +
                "\n" +
                "\n" +
                "\t ews_rct_section( \n" +
                "\t\t ews_total_region_code = "+jumlahRegionDisc+", \n" +
                "\t\t\t ews_rct_loop = \n" +
                "\t\t\t [ \n" +
                generateTcttable(RegionDisc2,rowRegion2) +
                "\t\t\t ],\n" +
                "\t\t\t version_number = 1,\n" +
                "\t\t\t section_number = 1,\n" +
                "\t\t\t last_section_number = 3,\n" +
                "\t\t ),\n" +
                "\n" +
                "\n" +
                "\t ews_rct_section( \n" +
                "\t\t ews_total_region_code = "+jumlahRegionDisc+", \n" +
                "\t\t\t ews_rct_loop = [ \n" +
                generateTcttable(RegionDisc3,rowRegion3) +
                "\t\t\t ],\n" +
                "\t\t\t version_number = 1,\n" +
                "\t\t\t section_number = 2,\n" +
                "\t\t\t last_section_number = 3,\n" +
                "\t\t ),\n" +
                "\n" +
                "\n" +
                "\t ews_rct_section( \n" +
                "\t\t ews_total_region_code = "+jumlahRegionDisc+", \n" +
                "\t\t\t ews_rct_loop = [ \n" +
                generateTcttable(RegionDisc4,rowRegion4) +
                "\t\t\t ],\n" +
                "\t\t\t version_number = 1,\n" +
                "\t\t\t section_number = 3,\n" +
                "\t\t\t last_section_number = 3,\n" +
                "\t\t ),\n" +
                "\n" +
                "] \n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "out = open('"+folderUser+"temps/pmtews.sec', \"wb\") \n" +
                "out.write(ewspmt.pack()) \n" +
                "out.close \n" +
                "out = open('"+folderUser+"temps/pmtews.sec', \"wb\") \n" +
                "out.close \n" +
                "os.system('sec2ts ' + str(indews_pmt_pid) + ' < "+folderUser+"temps/pmtews.sec > "+folderUser+"temps/mptspmtews.ts')\n" +
                "\n" +
                "\n" +
                "for index  in range (len(ewswcttable)): \n" +
                "\t out = open('"+folderUser+"temps/ewswct'+str(index)+'.sec', \"wb\") \n" +
                "\t out.write(ewswcttable[index].pack()) \n" +
                "\t out.close \n" +
                "\t out = open('"+folderUser+"temps/ewswct'+str(index)+'.sec', \"wb\") \n" +
                "\t out.close \n" +
                "\t os.system('sec2ts ' + str(indews_pmt_pid) + ' < "+folderUser+"temps/ewswct'+str(index)+'.sec > "+folderUser+"temps/ewswct.ts')\n" +
                "\n" +
                "\n" +
                "for index  in range (len(ewsmsgtable)): \n" +
                "\t out = open('"+folderUser+"temps/ewsmsg'+str(index)+'.sec', \"wb\") \n" +
                "\t out.write(ewsmsgtable[index].pack()) \n" +
                "\t out.close \n" +
                "\t out = open('"+folderUser+"temps/ewsmsg'+str(index)+'.sec', \"wb\") \n" +
                "\t out.close \n" +
                "\t os.system('sec2ts ' + str(indews_pmt_pid) + ' < "+folderUser+"temps/ewsmsg'+str(index)+'.sec > "+folderUser+"temps/ewsmsg.ts') \n" +
                "\n" +
                "\n" +
                "for index  in range (len(ewstsrcttable)): \n" +
                "\t out = open('"+folderUser+"temps/ewstsrct'+str(index)+'.sec', \"wb\") \n" +
                "\t out.write(ewstsrcttable[index].pack()) \n" +
                "\t out.close \n" +
                "\t out = open('"+folderUser+"temps/ewstsrct'+str(index)+'.sec', \"wb\") \n" +
                "\t out.close \n" +
                "\t os.system('sec2ts ' + str(indews_pmt_pid) + ' < "+folderUser+"temps/ewstsrct'+str(index)+'.sec > "+folderUser+"temps/ewstsrct.ts') \n" +
                "\n" +
                "\n" +
                "for index  in range (len(ewsrctable)): \n" +
                "\t out = open('"+folderUser+"temps/ewsrct'+str(index)+'.sec', \"wb\") \n" +
                "\t out.write(ewsrctable[index].pack()) \n" +
                "\t out.close \n" +
                "\t out = open('"+folderUser+"temps/ewsrct'+str(index)+'.sec', \"wb\") \n" +
                "\t out.close \n" +
                "\t os.system('sec2ts ' + str(indews_pmt_pid) + ' < "+folderUser+"temps/ewsrct'+str(index)+'.sec > "+folderUser+"temps/ewsrct'+str(index+1)+'.ts') \n" +
                "\n" +
                "\n" +
                "out = open('"+folderUser+"temps/ewsit.sec', \"wb\") \n" +
                "out.write(ewsit.pack()) \n" +
                "out.close \n" +
                "out = open('"+folderUser+"temps/ewsit.sec', \"wb\") \n" +
                "out.close \n" +
                "os.system('sec2ts ' + str(indews_pmt_pid) + ' < "+folderUser+"temps/ewsit.sec > "+folderUser+"temps/ewsit.ts') \n" +
                "os.system('rm "+folderUser+"temps/*.sec') \n" ;

        return p;
    }




}




