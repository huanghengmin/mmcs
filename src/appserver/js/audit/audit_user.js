/**
 * Created by IntelliJ IDEA.
 * User: Ǯ����
 * Date: 12-6-19
 * Time: ����10:19
 * To change this template use File | Settings | File Templates.
 * �û���־���(�û�������Ʊ�)
 */
Ext.onReady(function() {
	Ext.BLANK_IMAGE_URL = '../../ext/resources/images/default/s.gif';
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';

    var userNameRecord = new Ext.data.Record.create([
        {name:'value',mapping:'value'},
        {name:'key',mapping:'key'}
    ]);
    var userNameStore = new Ext.data.Store({
        url:'../../AccountAction_queryKeyValueUserName.action',
        reader:new Ext.data.JsonReader({totalProperty:'total',root:"rows"},userNameRecord)
    });
    userNameStore.load();
    var start = 0;
    var pageSize = gridPageSize();
    var record = new Ext.data.Record.create([
        {name:'id',			mapping:'id'},
        {name:'userName',		mapping:'userName'},
        {name:'level',		    mapping:'level'},
        {name:'auditModule',	mapping:'auditModule'},
        {name:'auditInfo',	mapping:'auditInfo'},
        {name:'logTime',		mapping:'logTime'}
    ]);
    var proxy = new Ext.data.HttpProxy({
        url:'../../AuditAction_selectUserAudit.action'
    });
    var reader = new Ext.data.JsonReader({
        totalProperty:"total",
        root:"rows",
        id:'id'
    },record);
    var store = new Ext.data.GroupingStore({
        id:"store.info",
        proxy : proxy,
        reader : reader
    });

    var boxM = new Ext.grid.CheckboxSelectionModel();   //��ѡ��
    var rowNumber = new Ext.grid.RowNumberer();         //�Զ� ���
    var colM = new Ext.grid.ColumnModel([
//        boxM,
        rowNumber,
        {header:'���ʱ��',		dataIndex:'logTime',		   align:'center',sortable:true,width:120},
        {header:"��־�ȼ�",		dataIndex:"level",	       align:'center',sortable:true,width:50},
        {header:'�û��� ',			dataIndex:"userName",       align:'center',sortable:true,width:100 },
        {header:'���ģ��',	    dataIndex:'auditModule',	   align:'center',sortable:true,width:100},
        {header:'�����Ϣ',	    dataIndex:'auditInfo',      align:'center',sortable:true}

    ]);
    var page_toolbar = new Ext.PagingToolbar({
        pageSize : pageSize,
        store:store,
        displayInfo:true,
        displayMsg:"��ʾ��{0}����¼����{1}����¼,һ��{2}��",
        emptyMsg:"û�м�¼ ",
        beforePageText:"��ǰҳ ",
        afterPageText:"��{0}ҳ "
    });
    var grid_panel = new Ext.grid.GridPanel({
        id:'grid.info',
        plain:true,
        height:setHeight(),
        width:setWidth(),
        animCollapse:true,
        loadMask:{msg:'���ڼ������ݣ����Ժ�...'},
        border:false,
        collapsible:false,
        cm:colM,
        sm:boxM,
        store:store,
        stripeRows:true,
        disableSelection:true,
        bodyStyle:'width:100%',
        enableDragDrop: true,
        selModel:new Ext.grid.RowSelectionModel({singleSelect:true}),
        viewConfig:{
            forceFit:true,
            enableRowBody:true,
            getRowClass:function(record,rowIndex,p,store){
                return 'x-grid3-row-collapsed';
            }
        },
        tbar:['��ʼ����:', {
            id : 'startDate.tb.info',
            xtype : 'datefield',
            name : 'startDate',
            emptyText : '�����������',
            format : 'Y-m-d'
        }, {
            xtype : 'tbspacer',
            width : 10
        },'��������:', {
            id : 'endDate.tb.info',
            xtype : 'datefield',
            name : 'endDate',
            emptyText : '�����������',
            format : 'Y-m-d'
        }, {
            xtype : 'tbspacer',
            width : 10
        }, '�û���:',{
            id:'userName.tb.info',
            xtype:'combo',
            store:userNameStore,
            valueField : 'value',
            displayField : 'key',
            mode : 'local',
            forceSelection : true,
            triggerAction : 'all',
            emptyText : '--��ѡ��--',
            value : '',
            editable : true,
            selectOnFocus : true,
            width : 100
        },{
            xtype : 'tbspacer',
            width : 10
        },'��־�ȼ�:',{
            id : 'logLevel.tb.info',
            xtype : 'combo',
            store : new Ext.data.ArrayStore({
                autoDestroy : true,
                fields : ['value', 'key'],
                data : [
                    ['INFO', 'INFO'],
                    ['WARN', 'WARN'],
                    ['ERROR', 'ERROR'],
                    ['DEBUG', 'DEBUG']
                ]
            }),
            valueField : 'value',
            displayField : 'key',
            mode : 'local',
            forceSelection : true,
            triggerAction : 'all',
            emptyText : '--��ѡ��--',
            value : '',
            selectOnFocus : true,
            width : 100
        }, {
            xtype : 'tbspacer',
            width : 10
        }, {
            text : '��ѯ',
            iconCls:'query',
            listeners : {
                click : function() {
                    var logLevel = Ext.fly('logLevel.tb.info').dom.value == '--��ѡ��--'
                        ? null
                        : Ext.fly('logLevel.tb.info').dom.value;
                    var startDate = Ext.fly("startDate.tb.info").dom.value == '�����������'
                        ? null
                        : Ext.fly('startDate.tb.info').dom.value;
                    var endDate = Ext.fly('endDate.tb.info').dom.value == '�����������'
                        ? null
                        : Ext.fly('endDate.tb.info').dom.value;
                    var userName = Ext.fly('userName.tb.info').dom.value == '--��ѡ��--'
                        ? null
                        :Ext.getCmp('userName.tb.info').getValue();
                    store.setBaseParam('startDate', startDate);
                    store.setBaseParam('endDate', endDate);
                    store.setBaseParam('logLevel', logLevel);
                    store.setBaseParam('userName', userName);
                    store.load({
                        params : {
                            start : start,
                            limit : pageSize
                        }
                    });
                }
            }
        }],
        view:new Ext.grid.GroupingView({
            forceFit:true,
            groupingTextTpl:'{text}({[values.rs.length]}����¼)'
        }),
        bbar:page_toolbar
    });
    var port = new Ext.Viewport({
        layout:'fit',
        renderTo: Ext.getBody(),
        items:[grid_panel]
    });
    store.load({
        params:{
            start:start,limit:pageSize
        }
    });
});
function setHeight(){
	var h = document.body.clientHeight-8;
	return h;
}
function gridPageSize(){
    var size = setHeight()/25;
    return parseInt(size);
}
function setWidth(){
    return 991;
}
