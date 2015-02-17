package com.zxgj.logadmin.shared;

public class ZXGJParserHelper {
	public static final int NodeNumber=2;
	public static final String attributeEnd = "]";
    public static final String attributeBegin = "[";
    public static final String attributeEndAndSpace = "] ";
    public static final String IDField = "id";    
    public static final String lineValueField = "lineValue_s";
    
	public static final String timestampField = "ts_dt";
	
	public static final String secLineTimeStampField = "secTs_dt";
	public static final String secLineMessageKeyWordField = "secMsgKey_s";
	public static final String secLineMessageKeyCodeField = "secMsgCode_s";
	public static final String secLineMessageKeyCodeValueField = "secMsgCodeValue_s";	
	public static final String secLineLeftLineField = "secLineExtra_s";
	public static final String nodeNameField = "nodeName_s";
	
	public static final String fileTypeField = "fileType";
	public static final String lineNumField = "lineNum_l";
	public static final String recordNumField = "recordNum_l";
	public static final String lineTypeField = "lineType_s";
    public static final String logLevelField = "logLevel_s";
	public static final String normalLineEventField = "eventField_s";
	public static final String normalLineCommentField = "normalLineComment_s";
    
	public static final String lineBeginWithCommnets = "//";
	public static final String lineBeginWithXML = "<";
	public static final String attributeSpaceAndBegin=" [";
	public static final String ownAttributesSeporator = "---";
	public static final String space= " ";
	public static final String doubleDots = ":";
	
	public static final int executorPoolSize = 1;
	
	public static final String lineString = "lineString";
	public static final String lineTypeComments = "comments";
	public static final String lineTypeEAPNormal = "EAPNormal";
	
	public static final String SECLineTypeReceive = "secLineReceive";
	public static final String SECLineTypeSend = "secLineSend";
	public static final String SECLineTypeExtra = "secLineExtra";
	
	public static final String logLevel = "logLevel";
	
//    public static AtomicLong idGenerator = new AtomicLong(0);
	
	public static final String SEND = "SEND";
	public static final String RECEIVE = "RECV";

	public static final String facetSECrange=secLineTimeStampField;
	public static final String facetSECDate = "facet.date";
	public static final String facetSECDateStartField="facet.date.start";
	public static final String facetSECDateEndField = "facet.date.end";
	public static final String facetSECDateGapField = "facet.date.gap";
	
	public static final String queryGetAllSECMsgKeyValue="getAllSECFileMsgKeyValue";	
	public static final String queryGetMsgKeyValueAmountPerNode = "getMsgKeyValueAmountPerNode";
	public static final String queryGetTransactionTimeoutByTimeSeriesByNode1 = "getTransactionTimeoutByTimeSeriesByNode1";
	public static final String queryGetTransactionTimeoutByTimeSeriesByNode2 = "getTransactionTimeoutByTimeSeriesByNode2";
    public static final String queryGetTransactionTimeoutLinesOffsetInNode = "getTransactionTimeoutLinesOffsetInNode";
    public static final String queryGetMsgKeyValueLinesByNode = "getMsgKeyValueLinesByNode";
    public static final String queryLogEventsByTimeRange = "getLogEventsByTimeRange";
    public static final String queryTransactionTimeoutByTimeRangeByNode = "getTransactionTimeoutByTimeRangeByNode"; 
    public static final String queryTransactionTimeoutByTimeRange = "getTransactionTimeoutByTimeSeries";
    public static final String queryDetailLogEventsWithinSecond = "getDetailLogEventsWithinSecond";
    public static final String queryEAPGetAllComments = "getEAPAllComments";
    public static final String queryEAPGetAllEvents = "getEAPAllEvents";
    
    
    
    public static final String TransactionTimeoutMsgKeyValue = "TransactionTimerTimeout";
	
    public static final String NodeName1 = "node1";
	public static final String NodeName2 = "node2";
	
	public static final String paramOFFSET="offset";
	public static final String paramLINENUM = "lineNum";
	public static final String paramDate="UTCTime";
	public static final String paramMsgKeyValue = "msgKeyValue";

	
	public static boolean doesLineStartWithDigit(String line){
		return line.startsWith("0") || line.startsWith("1") ||
			   line.startsWith("2") || line.startsWith("3") ||
			   line.startsWith("4") || line.startsWith("5") ||
			   line.startsWith("6") || line.startsWith("7") ||
			   line.startsWith("8") || line.startsWith("9");
	}
	
}
