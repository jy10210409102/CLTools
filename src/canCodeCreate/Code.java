/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package canCodeCreate;

/**
 *
 * @author Administrator
 */
public class Code {
    
    public String carType="";//车型
    public String carBrand="";//车车品牌
    public String carCan="";//can公司
    public static String headFileTxt = "#ifndef _CANCOMMJILIXBS_H_\n"
            + "#define _CANCOMMJILIXBS_H_\n"
            + "\n"
            + "void XBS_JILI_ProcCanFun(S_MCU_DATA_CANINFO *pCanInfo,BYTE cmd,const BYTE *pData,VOID_SEND_MSG_TO_HOST cbfun);\n"
            + "void XBS_JILI_ProcCanCmd(E_CAN_ACTION eCanAction, const S_HOST_INFO* pHostInfo,int Rpt100ms,DWORD dwConfig,VOID_SEND_CMD_TO_SLAVER cbfun);\n"
            + "void XBS_JILI_ProcCanLoop(E_CAN_ACTION eCanAction, const S_HOST_INFO* pHostInfo,int Rpt100ms,DWORD dwConfig,VOID_SEND_CMD_TO_SLAVER cbfun);\n"
            + "void XBS_JILI_ProcCanSelId(VOID_SEND_CMD_TO_SLAVER cbfun);\n"
            + "#endif";
    
    public static  String cppFileTxt="#include \"StdAfx.h\"\n" +
"#include \"CanBase.h\"\n" +
"#include \"Cancomm.h\"\n" +
"#include \"myCanType.h\"\n" +
"#include \"CancommHondaCriderXBS.h\"\n" +
"#include \"mcuDataBase.h\"\n" +
"#include \"keyBase.h\"\n" +
"\n" +
"\n" +
"//根据协议不用定义\n" +
"//chenli start\n" +
"#define CarWheelKey 0x01 //方向盘\n" +
"#define	CAR_DOOR_INFO 0x02	//车门和状态信息\n" +
"#define	CAR_INFO	0X03	//原车车速信息\n" +
"#define OIL_INFO	0x05	//本次耗油里程信息\n" +
"#define HISTORY_OIL_INFO	0X06	//历史耗油历程信息\n" +
"#define VERSION_INFO	0x7f	//版本信息	若客户需要再添加\n" +
"#define START_END	0x81	//	start/end\n" +
"#define REQUEST	0xff	//请求信息  若客户需要再添加\n" +
"//chenli end\n" +
"\n" +
"typedef struct\n" +
"{\n" +
"	BYTE msgData;\n" +
"	BYTE cmdSource;\n" +
"	BYTE cmdMedia;\n" +
"}S_CAN_SOURCE;\n" +
"\n" +
"static const S_CAN_SOURCE XBS_CanSourceTab_CRV[] = \n" +
"{\n" +
"	//{SYS_SOURCE_RADIO,0X91,1},//0X91\n" +
"	//{SYS_SOURCE_CD,0X92,0x10},//0X92\n" +
"	//{SYS_SOURCE_USB,0X93,0x13},//0X93\n" +
"	//{SYS_SOURCE_SD,0X94,0x30},//0X94\n" +
"	//{SYS_SOURCE_CDC,0X97,0x30},//0X97\n" +
"	{SYS_SOURCE_TV,0X95,0x30},//0X95\n" +
"	{SYS_SOURCE_AUX,0X90,0x30},//0X90  //{SYS_SOURCE_AUX,0X96,0x30},//0X96    //add by pxz for crv   20121117    //原车usb/aux与主机aux不能使用同个元0X96\n" +
"	//{USER_STATE_CARINFO,0X96,0x30},//0X96当本机进入时间设置的时候会把小屏状态强制写为AUX\n" +
"	{SYS_SOURCE_IPOD,0X98,0x12},//0X98\n" +
"	{SYS_SOURCE_DVBT,0X9C,0x30},//0X9C\n" +
"	{SYS_SOURCE_CMMB,0X95,0x30},//0X95\n" +
"	{SYS_SOURCE_CARUSB,0X96,0x13},//0X96          //add by pxz for crv  ,原车usb看做AUX普元\n" +
"	{SYS_SOURCE_BTMUSIC,0X9E,0x40},//0X9E\n" +
"	//{USER_STATE_GPS,0X99,0x40},//0X99\n" +
"	//{USER_STATE_BT,0X9A,0x40},//0X9A\n" +
"	//{USER_STATE_BT_TALKING,0X9A,0x40},//0X9A   //remove by pxz   解决与SD元冲突\n" +
"\n" +
"	{250,0,0}\n" +
"};\n" +
"\n" +
"\n" +
"//	pCanKeyValTab:系统按键转换表\n" +
"static const S_CAN_KEY_VAL sCanKeyValTab[] = \n" +
"{\n" +
"	0, NO_KEY,\n" +
"	0x01,V_KEY_VOL_INC,	//音量加\n" +
"	0x02,V_KEY_VOL_DEC,//音量减\n" +
"	0x03,C_KEY_PRE, //>>|\n" +
"	0x04,C_KEY_NEXT,//|<<\n" +
"	0x07,C_KEY_MODE,//模式\n" +
"	0x08,C_KEY_TEL,//接听键\n" +
"	0x09,C_KEY_HANG_UP,//挂断\n" +
"	//0x0A,C_KEY_CARUSB,//语音 保留\n" +
"	//0x0B,C_KEY_MENU,//menu 保留\n" +
"	//0x0C,C_KEY_ESC,//页面切换 保留\n" +
"\n" +
"	-1, NO_KEY\n" +
"};\n" +
"\n" +
"\n" +
"//转换CAN按键值到系统按键\n" +
"//in: \n" +
"//	pCanKeyValTab:系统按键转换表\n" +
"//	CanData:can key data\n" +
"//out: \n" +
"//	系统按键值，错误返回NO_KEY\n" +
"inline int GetCanKeyVal(const S_CAN_KEY_VAL*pCanKeyValTab, BYTE CanData)\n" +
"{\n" +
"	if (pCanKeyValTab == NULL)\n" +
"	{\n" +
"		return NO_KEY;\n" +
"	}\n" +
"\n" +
"	RETAILMSG(1,(_T(\"\\r\\n [GetCanKeyVal] can data is %d \\r\\n\"), CanData));\n" +
"	for (int i=0; i<100; i++)\n" +
"	{\n" +
"		if (pCanKeyValTab[i].CanKeyData == -1)\n" +
"		{\n" +
"			return NO_KEY;\n" +
"		}\n" +
"\n" +
"		if (pCanKeyValTab[i].CanKeyData == CanData)\n" +
"		{\n" +
"			return pCanKeyValTab[i].KeyVal;\n" +
"		}\n" +
"	}\n" +
"\n" +
"	return NO_KEY;\n" +
"}\n" +
"\n" +
"static const S_CAN_KEY_STATE sCanKeyState[] = \n" +
"{\n" +
"	0, KEY_UP,\n" +
"	1, KEY_DOWN,\n" +
"	2, KEY_KEEP\n" +
"};\n" +
"\n" +
"//转换can 按键状态到 系统按键状态\n" +
"//in:\n" +
"//	pCanKeyState: CAN按键转换表\n" +
"//	CanData: CAN 按键状态\n" +
"//out:\n" +
"//	系统按键状态，错误返回KEY_UNKNOW\n" +
"inline int GetCanKeyState(const S_CAN_KEY_STATE* pCanKeyState, BYTE CanData)\n" +
"{\n" +
"	if (pCanKeyState == NULL)\n" +
"	{\n" +
"		return KEY_UNKNOW;\n" +
"	}\n" +
"\n" +
"	for (int i=0; i<3; i++)\n" +
"	{\n" +
"		if (pCanKeyState[i].CanKeyState == CanData)\n" +
"		{\n" +
"			return pCanKeyState[i].KeyState;\n" +
"		}\n" +
"	}\n" +
"\n" +
"	return KEY_UNKNOW;\n" +
"}\n" +
"\n" +
"//校验和是否正确\n" +
"bool GetCheckSumOK(const LPBYTE lpBuffer)\n" +
"{\n" +
"	int Length = lpBuffer[1];\n" +
"	BYTE Sum = 0x00;\n" +
"	for (int i=0;i<=(Length+1);i++)\n" +
"	{\n" +
"		Sum += lpBuffer[i];\n" +
"	}\n" +
"	if (((Sum^0xFF) == lpBuffer[Length+2]))\n" +
"	{\n" +
"		return TRUE;\n" +
"	}\n" +
"	else\n" +
"	{\n" +
"		RETAILMSG(1,(_T(\"\\r\\n Xp_Focus_ProcCanFun]CheckSum is Err %d\\r\\n\")));\n" +
"		return FALSE;\n" +
"	}\n" +
"}\n" +
"//接受并解析\n" +
"void XBS_HondaCrider_ProcCanFun(S_MCU_DATA_CANINFO *pCanInfo,BYTE cmd,const BYTE *pData,VOID_SEND_MSG_TO_HOST cbfun)\n" +
"{\n" +
"\n" +
"	\n" +
"	S_MSG_CAN sMsg;\n" +
"	if(cbfun == NULL)\n" +
"	{\n" +
"		RETAILMSG(1,(_T(\"\\r\\n [XP_Focus_ProcCanFun]cbfun is null \\r\\n\")));\n" +
"		return;\n" +
"	}\n" +
"	RETAILMSG(1,(_T(\"\\r\\n [XBS_Crider_ProcCanFun]Get MCU Data is  %x,%x    %x,%x    %x,%x   %x\\r\\n\"),pData[0],pData[1],pData[2],pData[3],pData[4],pData[5],pData[6]));\n" +
"	//验证校验和\n" +
"	if(!GetCheckSumOK((const LPBYTE)pData))\n" +
"	{\n" +
"		return;\n" +
"	}\n" +
"	cmd = pData[0];//消息类型\n" +
"	switch(cmd)\n" +
"	{\n" +
"	case CarWheelKey://方向盘按键		\n" +
"		sMsg.msgType = CAN_MSG_KEY_FUN;\n" +
"		sMsg.msgData2 = GetCanKeyVal(sCanKeyValTab, pData[2]);//key val\n" +
"		sMsg.msgData1 = GetCanKeyState(sCanKeyState, pData[3]); //key state		\n" +
"		(*cbfun)(sMsg);\n" +
"		break;\n" +
"	case CAR_DOOR_INFO://车门信息\n" +
"		{\n" +
"			BYTE Car_Info1 = pData[2];\n" +
"			pCanInfo->FrontRightDool = Car_Info1 & BIT6;\n" +
"			pCanInfo->FrontLeftDool = Car_Info1 & BIT7;\n" +
"			pCanInfo->RearRightDool = Car_Info1 & BIT4;\n" +
"			pCanInfo->RearLeftDool = Car_Info1 & BIT5;\n" +
"			pCanInfo->RearBox= Car_Info1 & BIT3;\n" +
"			//pData[3];//车灯信息 保留\n" +
"			//pCanInfo->\n" +
"			sMsg.msgType = CAN_MSG_CAR_DOOR;\n" +
"			if ((Car_Info1 & 0xF8)&&(pCanInfo->bCarBtState == FALSE))\n" +
"			{\n" +
"				sMsg.msgData1 = 1;	//车门开，进入车门显示\n" +
"			}\n" +
"			else\n" +
"			{\n" +
"				sMsg.msgData1 = 0;	//车门关，退出车门显示\n" +
"			}\n" +
"\n" +
"			(*cbfun)(sMsg);\n" +
"		}\n" +
"		break;\n" +
"	case CAR_INFO://原车车速信息	保留\n" +
"		{\n" +
"			\n" +
"	\n" +
"		}\n" +
"		break;\n" +
"	case OIL_INFO://本次油耗里程信息\n" +
"		{\n" +
"			\n" +
"			////S_MCU_DATA_NOW_IOL *iol;\n" +
"			DWORD instant = (pData[2]*256/*<<8*/ + pData[3])&(BIT15-1);\n" +
"		//	DWORD instant=(pData[2]<<8)||pData[3];\n" +
"			int thism=((pData[4]*256)+pData[5])&(BIT15-1);\n" +
"			int yet=((pData[6]*256)+pData[7])&(BIT15-1);\n" +
"			int can=((pData[8]*256)+pData[9])&(BIT15-1);\n" +
"\n" +
"			pCanInfo->iol.instans_unit=(pData[2]>>7)&0x01;	//瞬时油耗单位\n" +
"			pCanInfo->iol.this_unit=(pData[4]>>7)&0x01;	//本次油耗单位\n" +
"			pCanInfo->iol.yet_unit=(pData[6]>>7)&0x01;	//已行驶油耗单位\n" +
"			pCanInfo->iol.can_unit=(pData[8]>>7)&0x01;	//可续行耗单位\n" +
"\n" +
"			//瞬时\n" +
"			if(instant==0x7FF)\n" +
"			{\n" +
"				pCanInfo->iol.instans_show =1;\n" +
"			}\n" +
"			else if(instant==0xFE)\n" +
"			{\n" +
"				pCanInfo->iol.instans_show = 0;\n" +
"			}\n" +
"			else\n" +
"			{\n" +
"				 //(pData[2]&(BIT8-1))*256+ pData[3];\n" +
"				pCanInfo->iol.instans_show=instant/10.0;	\n" +
"			}\n" +
"			//本次\n" +
"			if(thism==0x7FF)\n" +
"			{\n" +
"				pCanInfo->iol.this_show=1;\n" +
"			}\n" +
"			else if(thism==0xFE)\n" +
"			{\n" +
"				pCanInfo->iol.this_show=0;\n" +
"			}\n" +
"			else{\n" +
"				pCanInfo->iol.this_show=(thism)/10.0;\n" +
"			}\n" +
"			//已行驶\n" +
"			if(yet==0x7FF){\n" +
"				pCanInfo->iol.yet_show=1;\n" +
"			}\n" +
"			else if(yet==0xFE)\n" +
"			{\n" +
"				pCanInfo->iol.yet_show=0;\n" +
"			}\n" +
"			else{\n" +
"				pCanInfo->iol.yet_show=(yet)/10.0;\n" +
"			}\n" +
"			\n" +
"			//可续航\n" +
"			if(can==0x7FFF){\n" +
"				pCanInfo->iol.can_show=1;\n" +
"			}\n" +
"			else if(can==0xFFE)\n" +
"			{\n" +
"				pCanInfo->iol.can_show=0;\n" +
"			}\n" +
"			else{\n" +
"				pCanInfo->iol.can_show=can;\n" +
"			}\n" +
"			pCanInfo->iol.time_hour=pData[10];//小时\n" +
"			pCanInfo->iol.time_min=pData[11];//分钟\n" +
"		}\n" +
"		break;\n" +
"	case HISTORY_OIL_INFO:\n" +
"		{\n" +
"		int mileage=((pData[2]*256*256/*<<16*/)+(pData[3]*256/*<<8*/)+pData[4])&(BIT23-1); //里程\n" +
"		int aveIol=((pData[5]*256/*<<8*/)+pData[6])&(BIT15-1);	//平均油耗\n" +
"		int aveSpeed=((pData[7]*256)+pData[8])&(BIT15-1);	//	平均车速  单位唯一 km/h\n" +
"		//单位 \n" +
"		pCanInfo->iol.mileage_unit=(pData[2]>>7)&BIT0;//里程单位\n" +
"		pCanInfo->iol.ave_iol_unit=(pData[5]>>7)&BIT0;//平均油耗单位\n" +
"		//显示和值\n" +
"		//里程\n" +
"		if(0x7FFFFF==mileage)\n" +
"		{\n" +
"			pCanInfo->iol.mileage_show=1;\n" +
"		}\n" +
"		else if(0xFFFFE==mileage)\n" +
"		{\n" +
"			pCanInfo->iol.mileage_show=0;\n" +
"		}\n" +
"		else{\n" +
"			pCanInfo->iol.mileage_show=(mileage)/10.0;\n" +
"		}\n" +
"		//平均油耗\n" +
"		if(0x7FF==aveIol){\n" +
"			pCanInfo->iol.ave_iol_show=1;\n" +
"		}\n" +
"		else if(0xFE==aveIol){\n" +
"			pCanInfo->iol.ave_iol_show=0;\n" +
"		}\n" +
"		else{\n" +
"			pCanInfo->iol.ave_iol_show=(aveIol)/10.0;\n" +
"		}\n" +
"		//平均车速\n" +
"		if(0xFF==aveSpeed){\n" +
"			pCanInfo->iol.ave_speed_show=1;\n" +
"		}\n" +
"		else if(0xFE==aveSpeed){\n" +
"			pCanInfo->iol.ave_speed_show=0;\n" +
"		}\n" +
"		else{\n" +
"			pCanInfo->iol.ave_speed_show=aveSpeed;\n" +
"		}\n" +
"		//RETAILMSG(1,(_T(\"chenli aveSpeed=[%d]\"),aveSpeed));\n" +
"		break;\n" +
"		}\n" +
"	//case VERSION_INFO: //版本信息	如果客户需要再添加\n" +
"\n" +
"	//	break;\n" +
"	//case START_END:	//\n" +
"	//	break;\n" +
"	default:\n" +
"		break;\n" +
"	}\n" +
"}\n" +
"\n" +
"//发送\n" +
"void XBS_HondaCrider_ProcCanCmd(E_CAN_ACTION eCanAction, const S_HOST_INFO* pHostInfo,int Rpt100ms, DWORD dwConfig,VOID_SEND_CMD_TO_SLAVER cbfun)\n" +
"{\n" +
"	static BYTE Can_CMD[100] = {0};	//can 命令数据buffer\n" +
"\n" +
"	if(cbfun == NULL)\n" +
"	{\n" +
"		RETAILMSG(1,(_T(\"\\r\\n [XBS_HondaCRV_ProcCanCmd]cbfun is null \\r\\n\")));\n" +
"		return;\n" +
"	}\n" +
"\n" +
"	Can_CMD[0] = 0xAA;\n" +
"	//switch(eCanAction)\n" +
"	//{\n" +
"\n" +
"	//}\n" +
"}\n" +
"void XBS_HondaCrider_ProcCanLoop(E_CAN_ACTION eCanAction, const S_HOST_INFO* pHostInfo,int Rpt100ms,  DWORD dwConfig,VOID_SEND_CMD_TO_SLAVER cbfun)\n" +
"{\n" +
"	static BYTE Can_CMD[100] = {0};	//can 命令数据buffer\n" +
"\n" +
"	if(cbfun == NULL)\n" +
"	{\n" +
"		RETAILMSG(1,(_T(\"\\r\\n [XBS_HondaCRV_ProcCanLoop]cbfun is null \\r\\n\")));\n" +
"		return;\n" +
"	}\n" +
"}\n" +
"//ZH_018 Add Start\n" +
"#define GetArraySize(tab)	(sizeof(tab)/sizeof(tab##[0]))\n" +
"\n" +
"\n" +
"void XBS_HondaCrider_ProcCanSelId(VOID_SEND_CMD_TO_SLAVER cbfun)\n" +
"{\n" +
"	////cmdTab[CAN_SAVE_TYPE,CMD Start,Cmd](CMD Start不算同步头)\n" +
"	//BYTE cmdTab[] = {CAN_SAVE_TYPE_0,0,UsbAuxPlayStatus,CarBasicInfo,TimeSetCtl};\n" +
"	//if(cbfun == NULL)\n" +
"	//{\n" +
"	//	RETAILMSG(1,(_T(\"\\r\\n [XBS_HondaCRV_ProcCanSelId]cbfun is null \\r\\n\")));\n" +
"	//	return;\n" +
"	//}\n" +
"	//(*cbfun)(cmdTab, GetArraySize(cmdTab), 0, CAN_SEND_TYPE_1);\n" +
"}//ZH_018 Add End";
}
