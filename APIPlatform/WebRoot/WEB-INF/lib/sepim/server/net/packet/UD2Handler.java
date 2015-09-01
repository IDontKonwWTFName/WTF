package sepim.server.net.packet;

import java.util.Hashtable;

import net.sf.json.JSONObject;

import org.jboss.netty.channel.Channel;

import sepim.server.clients.Client;
import sepim.server.clients.World;
import sepim.server.net.packet.handled.ChatPacketHandler;
import sepim.server.net.packet.handled.CommandPacketHandler;
import sepim.server.net.packet.handled.DefaultPacketHandler;
import sepim.server.net.packet.handled.PackHandler;


public class UD2Handler {
	
 
public void handle(String leixing,String company, String ringId, String contentsLength, String contents) {
		String[] contentsStrings = contents.split(",");
		//����
		String data = contentsStrings[1];
		//ʱ��
		String time = contentsStrings[2];
		//�Ƿ�λ
		String location = contentsStrings[3];
		//γ��
		String lat = contentsStrings[4];
		//γ�ȱ�ʶ
		String latSign = contentsStrings[5];
		//����
		String lng = contentsStrings[6];
		//���ȱ�ʶ
		String lngSign = contentsStrings[7];
		//�ٶ�
		String speed = contentsStrings[8];
		//����
		String direction = contentsStrings[9];
		//����
		String altitude = contentsStrings[10];
		//���Ǹ���
		String satelliteNum = contentsStrings[11];
		//GSM�ź�ǿ��
		String gsmSignalStr = contentsStrings[12];
		//����
		String elecPower = contentsStrings[13];
		//�ǲ���
		String stepsNum = contentsStrings[14];
		//��������
		String turning = contentsStrings[15];
		//�ն�״̬
		String endingStatus = contentsStrings[16];
		//��վ����
		String baseStationNum = contentsStrings[17];
		//���ӻ�վ��
		String conBaseStation = contentsStrings[18];
		//MCC������
		String mccCountryCode = contentsStrings[19];
		//MNC����
		String mncNetNum = contentsStrings[20];
		
		//���ӻ�վλ��������
		String conBaseStationAreaCode = contentsStrings[21];
		//���ӻ�վ���
		String conBaseStationNum = contentsStrings[22];
		//���ӻ�վ�ź�ǿ��
		String conBaseStationSingalStr = contentsStrings[23];
		
		//������վ1λ��������
		String nearBaseStationAreaCode1 = contentsStrings[24];
		//������վ1���
		String nearBaseStationNum1 = contentsStrings[25];
		//������վ1�ź�ǿ��
		String nearBaseStationSingalStr1 = contentsStrings[26];

		//������վ2λ��������
		String nearBaseStationAreaCode2 = contentsStrings[27];
		//������վ2���
		String nearBaseStationNum2 = contentsStrings[28];
		//������վ2�ź�ǿ��
		String nearBaseStationSingalStr2 = contentsStrings[29];

		//������վ3λ��������
		String nearBaseStationAreaCode3 = contentsStrings[30];
		//������վ3���
		String nearBaseStationNum3 = contentsStrings[31];
		//������վ3�ź�ǿ��
		String nearBaseStationSingalStr3 = contentsStrings[32];
		
//		д�����ݿ�
}

}
